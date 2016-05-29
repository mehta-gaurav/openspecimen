package com.krishagni.catissueplus.core.dashboard.serivce.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.events.DataDetail;
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
	public DataDetail execute(Map<String, Object> input) {
		ExecuteQueryEventOp op = createExecuteQueryEventOp();
		RequestEvent<ExecuteQueryEventOp> queryReq = new RequestEvent<ExecuteQueryEventOp>(op);
		ResponseEvent<QueryExecResult> resp = querySvc.executeQuery(queryReq);
		resp.throwErrorIfUnsuccessful();

		QueryExecResult result = resp.getPayload();
		return (series == null || series.isEmpty()) ? getSimpleData(result) : getComboData(result);
	}

	private ExecuteQueryEventOp createExecuteQueryEventOp() {
		ExecuteQueryEventOp op = new ExecuteQueryEventOp();
		op.setCpId(-1L);
		op.setDrivingForm("Participant");
		op.setRunType("Data");
		op.setAql(getAql());
		op.setIndexOf("Specimen.label");
		op.setWideRowMode("DEEP");

		return op;
	}

	private DataDetail getSimpleData(QueryExecResult result) {
        try {
                List<Number> data = new ArrayList<>();
                List<String> categories = new ArrayList<>();

                Iterator<String[]> rowItr = result.getRows().iterator();
                while (rowItr.hasNext()) {
                        String[] row = rowItr.next();
                        data.add(Long.valueOf(row[0]));
                        categories.add(row[1]);
                }

                DataDetail detail = new DataDetail();
                detail.setSeriesData(Collections.singletonMap(metric.getTitle(), data));
                detail.setCategories(categories);

                return detail;
        } catch (NumberFormatException ex) {
        	throw OpenSpecimenException.userError(AqlDsErrorCode.NON_NUMERIC_DATA);
        }
	}

	@SuppressWarnings("unchecked")
    private DataDetail getComboData(QueryExecResult result) {
            try {
                    List<String> series = Arrays.asList(result.getColumnLabels());
                    /*
                     * First column is header of label column, Last column is total,
                     * So removing this columns
                     */
                    series = series.subList(1, series.size() - 1);

                    List<String> categories = new ArrayList<>();
                    List<List<Number>> data = new ArrayList<>();
                    for (int i = 0; i < series.size(); i++) {
                            data.add(new ArrayList<>());
                    }

                    Iterator<String[]> rowIterator = result.getRows().iterator();

                    //First row contains grant total, so removing it
                    String[] row = rowIterator.hasNext() ? rowIterator.next() : null;

                    while (rowIterator.hasNext()) {
                            int j = 0;
                            row = rowIterator.next();

                            //First column contain label
                            categories.add(row[j++]);
                            for (int i = 0; i < series.size(); i++) {
                                    data.get(i).add(Long.valueOf(row[j++]));
                            }
                    }

                    Map<String, List<Number>> seriesData = new HashMap<>();
                    for (int idx = 0; idx < series.size(); idx++) {
                        seriesData.put(series.get(idx), data.get(idx));
                    }

                    DataDetail detail = new DataDetail();
                    detail.setCategories(categories);
                    detail.setSeriesData(seriesData);

                    return detail;
            } catch (NumberFormatException ex) {
                    throw OpenSpecimenException.userError(AqlDsErrorCode.NON_NUMERIC_DATA);
            }
    }

	private String getAql() {
		StringBuilder selectList = null;
		if (series == null || series.isEmpty()) {
			selectList = new StringBuilder(metric.toString()).append(", ").append(category.toString());
		} else {
			selectList = new StringBuilder(metric.toString()).append(", ")
					.append(series.toString()).append(", ")
					.append(category.toString());
		}

		String rptExpr = series == null || series.isEmpty() ? "" : " crosstab((3), 2, (1) )";

		return "select " + selectList + " where " + criteria + " limit 0, 10000 " + rptExpr;
	}

}
