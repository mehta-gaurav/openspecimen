package com.krishagni.catissueplus.core.dashboard.serivce.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.events.DashletData;
import com.krishagni.catissueplus.core.dashboard.serivce.AqlDsErrorCode;
import com.krishagni.catissueplus.core.dashboard.serivce.DataSource;
import com.krishagni.catissueplus.core.de.events.ExecuteQueryEventOp;
import com.krishagni.catissueplus.core.de.events.QueryExecResult;
import com.krishagni.catissueplus.core.de.services.QueryService;

@Configurable
public class AqlDataSource implements DataSource {
	public static class ExprTitle {
		private String expr;

		private String title;

		public String getExpr() {
			return expr;
		}

		public void setExpr(String expr) {
			this.expr = expr;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public boolean isEmpty() {
			return StringUtils.isBlank(expr) && StringUtils.isBlank(title);
		}

		public String toString() {
			return isEmpty() ? "" : getExpr() + " as \"" + getTitle() + "\"";
		}
	}

	private ExprTitle category;

	private ExprTitle series;

	private ExprTitle metric;

	private String criteria;

	@Autowired
	private QueryService querySvc;

	public ExprTitle getCategory() {
		return category;
	}

	public void setCategory(ExprTitle category) {
		this.category = category;
	}

	public ExprTitle getSeries() {
		return series;
	}

	public void setSeries(ExprTitle series) {
		this.series = series;
	}

	public ExprTitle getMetric() {
		return metric;
	}

	public void setMetric(ExprTitle metric) {
		this.metric = metric;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	@Override
	public DashletData execute(Map<String, Object> input) {
		QueryExecResult rawData = getRawData(input);
		return getDashletData(rawData);
	}

	private QueryExecResult getRawData(Map<String, Object> input) {
		Long cpId = (Long)input.get("cpId");
		if (cpId == null) {
			cpId = -1L;
		}

		ExecuteQueryEventOp op = new ExecuteQueryEventOp();
		op.setCpId(cpId);
		op.setDrivingForm("Participant");
		op.setRunType("Data");
		op.setAql(getAql());

		RequestEvent<ExecuteQueryEventOp> req = new RequestEvent<>(op);
		ResponseEvent<QueryExecResult> resp = querySvc.executeQuery(req);
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}

	private String getAql() {
		StringBuilder selectList = new StringBuilder(category.toString());
		StringBuilder crosstabExpr = new StringBuilder();

		if (series != null && !series.isEmpty()) {
			selectList.append(", ").append(series.toString());
			crosstabExpr.append("crosstab((1), 2, (3))");
		}

		selectList.append(", ").append(metric.toString());

		return new StringBuilder()
			.append("select ").append(selectList).append(" ")
			.append("where ").append(criteria).append(" ")
			.append(crosstabExpr)
			.toString();
	}

	private DashletData getDashletData(QueryExecResult rawData) {
		if (series == null || series.isEmpty()) {
			return transformSimpleTabToDashletData(rawData);
		} else {
			return transformCrossTabToDashletData(rawData);
		}
	}

	private DashletData transformSimpleTabToDashletData(QueryExecResult rawData) {
		try {
			List<Number> values = new ArrayList<>();
			List<String> categories = new ArrayList<>();

			Iterator<String[]> rowIter = rawData.getRows().iterator();
			while (rowIter.hasNext()) {
				String[] row = rowIter.next();
				categories.add(row[0]);
				values.add(new BigDecimal(row[1]));
			}

			DashletData data = new DashletData();
			data.setCategories(categories);
			data.setSeriesData(Collections.singletonMap(metric.getTitle(), values));
			return data;
		} catch (NumberFormatException nfe) {
			throw OpenSpecimenException.userError(AqlDsErrorCode.NON_NUM_METRIC_VALUE, nfe.getMessage());
		}
	}

	private DashletData transformCrossTabToDashletData(QueryExecResult result) {
		try {
			//
			// first and last element are category label and total header respectively
			// between these 2 elements are series labels
			//
			List<String> seriesLabels = Arrays.asList(result.getColumnLabels());
			seriesLabels = seriesLabels.subList(1, seriesLabels.size() - 1);

			List<String> categories = new ArrayList<>();
			Map<String, List<Number>> seriesData = new LinkedHashMap<>();

			Iterator<String[]> rowIter = result.getRows().iterator();
			if (rowIter.hasNext()) {
				rowIter.next();
			}

			while (rowIter.hasNext()) {
				int columnIdx = 0;
				String[] row = rowIter.next();

				//
				// First column of each row contains category name/label
				//
				categories.add(row[columnIdx++]);

				for (String seriesLabel : seriesLabels) {
					List<Number> values = seriesData.get(seriesLabel);
					if (values == null) {
						values = new ArrayList<>();
						seriesData.put(seriesLabel, values);
					}

					values.add(new BigDecimal(row[columnIdx++]));
				}
			}

			DashletData data = new DashletData();
			data.setCategories(categories);
			data.setSeriesData(seriesData);
			return data;
		} catch (NumberFormatException nfe) {
			throw OpenSpecimenException.userError(AqlDsErrorCode.NON_NUM_METRIC_VALUE, nfe.getMessage());
		}
	}
}
