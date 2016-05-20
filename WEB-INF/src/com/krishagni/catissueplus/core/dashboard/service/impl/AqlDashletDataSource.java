package com.krishagni.catissueplus.core.dashboard.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashletErrorCode;
import com.krishagni.catissueplus.core.dashboard.events.ChartDetail;
import com.krishagni.catissueplus.core.de.events.ExecuteQueryEventOp;
import com.krishagni.catissueplus.core.de.events.QueryExecResult;
import com.krishagni.catissueplus.core.de.services.QueryService;

public class AqlDashletDataSource extends AbstractDashletDataSource {
	private static final List<String> comboChartType = Arrays.asList("Bar", "Line", "Radar");
	
	private QueryService querySvc;
	
	public void setQuerySvc(QueryService querySvc) {
		this.querySvc = querySvc;
	}

	@Override
	public String getType() {
		return "AQL";
	}

	@Override
	public ChartDetail getData(DashletConfig dashlet) {
		Map<String, String> dataSourceOpts = dashlet.getDataSourceOpts();
		Map<String, String> chartOpts = dashlet.getChartOpts();
		String chartType = dashlet.getChartType();
		
		String aql = dataSourceOpts.get("aql");
		boolean isPivotQuery = isPivotQuery(aql);
		if (isPivotQuery && comboChartType.indexOf(chartType) == -1) {
			throw OpenSpecimenException.userError(DashletErrorCode.INVALID_CHART_TYPE, dashlet.getName(), chartType);
		}
		
		dataSourceOpts.put("aql", recreateAql(chartOpts, aql));
		
		ExecuteQueryEventOp op = createExecuteQueryEventOp(dataSourceOpts);
		
		RequestEvent<ExecuteQueryEventOp> queryReq = new RequestEvent<ExecuteQueryEventOp>(op);
		ResponseEvent<QueryExecResult> resp = querySvc.executeQuery(queryReq);
		resp.throwErrorIfUnsuccessful();
		
		QueryExecResult result = resp.getPayload();
		
		ChartDetail chartDetail = isPivotQuery ? getComboData(result) : getSimpleData(result);
		chartDetail.setType(chartType);
		
		return chartDetail;
	}	
	
	private ExecuteQueryEventOp createExecuteQueryEventOp(Map<String, String> dataSourceOpts) {
		ExecuteQueryEventOp op = new ExecuteQueryEventOp();
		op.setCpId(dataSourceOpts.get("cpId") == null ? null : Long.valueOf(dataSourceOpts.get("cpId")));
		op.setDrivingForm(dataSourceOpts.get("drivingForm"));
		op.setRunType(dataSourceOpts.get("runType"));
		op.setAql(dataSourceOpts.get("aql"));
		op.setIndexOf(dataSourceOpts.get("indexOf"));
		op.setSavedQueryId(dataSourceOpts.get("savedQueryId") == null ? null : Long.valueOf(dataSourceOpts.get("savedQueryId")));
		op.setWideRowMode(dataSourceOpts.get("wideRowMode"));
		
		return op;
	}
	
	private ChartDetail getSimpleData(QueryExecResult result) {
		try {
			List<Long> data = new ArrayList<>();
			List<String> labels = new ArrayList<>();
			
			Iterator<String[]> rowItr = result.getRows().iterator();
			while (rowItr.hasNext()) {
				String[] row = rowItr.next();
				data.add(Long.valueOf(row[0]));
				labels.add(row[1]);
			}
			
			ChartDetail detail = new ChartDetail();
			detail.setData(data);
			detail.setLabels(labels);
			
			return detail;
		} catch (NumberFormatException ex) {
			throw OpenSpecimenException.userError(DashletErrorCode.NON_NUMERIC_DATA);
		}
	}
	
	@SuppressWarnings("unchecked")
	private ChartDetail getComboData(QueryExecResult result) {
		try {
			List<String> series = Arrays.asList(result.getColumnLabels());
			
			/*
			 * First column is header of label column, Last column is total,
			 * So removing this columns
			 */
			series = series.subList(1, series.size() - 1);
			
			List<String> labels = new ArrayList<>();
			List<List<Long>> data = new ArrayList<>();
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
				labels.add(row[j++]);
				for (int i = 0; i < series.size(); i++) {
					data.get(i).add(Long.valueOf(row[j++]));
				}
			}
			
			ChartDetail detail = new ChartDetail();
			detail.setData(data);
			detail.setLabels(labels);
			detail.setSeries(series);
			
			return detail;
		} catch (NumberFormatException ex) {
			throw OpenSpecimenException.userError(DashletErrorCode.NON_NUMERIC_DATA);
		}
	}
	
	/*
	 * Recreating aql by placing select column to correct position as per chart opts
	 *  Eg. 
	 *   simple aql: select Participant.ppid, count(distinct Specimen.id) as \"Identifier Count \" where  
	 *                 Participant.id exists  and  Specimen.id exists   limit 0, 10000
	 *           
	 *   modified aql: select count(distinct Specimen.id) as \"Identifier Count \", Participant.ppid where  
	 *                   Participant.id exists  and  Specimen.id exists   limit 0, 10000
	 *   it will give me result in data, label table format.
	 */
	private String recreateAql(Map<String, String> chartOpts, String aql) {
		List<String> selectColumns = Arrays.asList(getSelectColumns(aql));
		if (selectColumns.size() != 2 && selectColumns.size() != 3) {
			throw OpenSpecimenException.userError(DashletErrorCode.INVALID_SEL_COL_COUNT);
		}
		
		boolean isPivotQuery = selectColumns.size() == 3;
		
		String dataCol = selectColumns.get(0);
		String seriesCol = chartOpts.get("seriesCol");
		String labelCol = chartOpts.get("labelCol");
		
		if (!isPivotQuery) {
			labelCol = StringUtils.isBlank(labelCol) ? selectColumns.get(1) : labelCol;
			int idx = selectColumns.indexOf(labelCol);
			dataCol = selectColumns.get(idx == 1 ? 0 : 1);
		} else if (StringUtils.isBlank(seriesCol) || StringUtils.isBlank(labelCol)) {
			seriesCol = selectColumns.get(1);
			labelCol = selectColumns.get(2);
		}
		
		seriesCol = StringUtils.isNotBlank(seriesCol) ? seriesCol + ", " : "";
		
		return new StringBuilder("select ")
				.append(dataCol).append(", ")
				.append(seriesCol)
				.append(labelCol).append(" ")
				.append(aql.substring(aql.indexOf("where"))).toString();
	}
	
	private boolean isPivotQuery(String aql) {
		return getSelectColumns(aql).length == 3;
	}
	
	private String[] getSelectColumns(String aql) {
		String[] selectColumns = aql.substring(aql.indexOf("select") + 6, aql.indexOf("where")).split(",");
		for (int i = 0; i < selectColumns.length; i++) {
			selectColumns[i] = selectColumns[i].trim();
		}
		
		return selectColumns;
	}
}
