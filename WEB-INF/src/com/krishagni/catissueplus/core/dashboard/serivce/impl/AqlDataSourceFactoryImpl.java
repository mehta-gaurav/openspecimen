package com.krishagni.catissueplus.core.dashboard.serivce.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.krishagni.catissueplus.core.common.errors.ErrorType;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.dashboard.serivce.AqlDataSourceErrorCode;
import com.krishagni.catissueplus.core.dashboard.serivce.DataSource;
import com.krishagni.catissueplus.core.dashboard.serivce.DataSourceFactory;

public class AqlDataSourceFactoryImpl implements DataSourceFactory {

	@Override
	public String getType() {
		return "AQL";
	}

	@Override
	public DataSource createDataSource(Map<String, Object> dataSourceOpts) {
		AqlDataSource dataSource = new AqlDataSource();
		OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);

		setCategory(dataSource, dataSourceOpts, ose);
		setSeries(dataSource, dataSourceOpts, ose);
		setMetric(dataSource, dataSourceOpts, ose);
		setCriteria(dataSource, dataSourceOpts, ose);

		ose.checkAndThrow();
		return dataSource;
	}

	private void setCategory(AqlDataSource dataSource, Map<String, Object> dataSourceOpts, OpenSpecimenException ose) {
		Object object = dataSourceOpts.get("category");
		if (object == null) {
			ose.addError(AqlDataSourceErrorCode.CATEGORY_REQUIRED);
		}

		if (!(object instanceof Map)) {
			ose.addError(AqlDataSourceErrorCode.INVALID_CATEGORY_OPT);
			return;
		}

		Map<String, String> category = (Map<String, String>) object;
		if (StringUtils.isBlank(category.get("expr"))) {
			ose.addError(AqlDataSourceErrorCode.CATEGORY_EXPR_REQUIRED, category);
		}

		if (StringUtils.isBlank(category.get("title"))) {
			ose.addError(AqlDataSourceErrorCode.CATEGORY_TITLE_REQUIRED, category);
		}

		dataSource.setCategory(category);
	}

	private void setSeries(AqlDataSource dataSource, Map<String, Object> dataSourceOpts, OpenSpecimenException ose) {
		Object object = dataSourceOpts.get("series");
		if (object == null) {
			return;
		}

		if (!(object instanceof Map)) {
			ose.addError(AqlDataSourceErrorCode.INVALID_SERIES_OPT);
			return;
		}

		Map<String, String> series = (Map<String, String>) object;
		if (StringUtils.isBlank(series.get("expr"))) {
			ose.addError(AqlDataSourceErrorCode.SERIES_EXPR_REQUIRED, series);
		}

		if (StringUtils.isBlank(series.get("title"))) {
			ose.addError(AqlDataSourceErrorCode.SERIES_TITLE_REQUIRED, series);
		}

		dataSource.setSeries(series);
	}

	private void setMetric(AqlDataSource dataSource, Map<String, Object> dataSourceOpts, OpenSpecimenException ose) {
		Object object = dataSourceOpts.get("metric");
		if (object == null) {
			ose.addError(AqlDataSourceErrorCode.METRIC_REQUIRED);
		}

		if (!(object instanceof Map)) {
			ose.addError(AqlDataSourceErrorCode.INVALID_METRIC_OPT);
			return;
		}

		Map<String, String> metric = (Map<String, String>) object;
		if (StringUtils.isBlank(metric.get("expr"))) {
			ose.addError(AqlDataSourceErrorCode.METRIC_EXPR_REQUIRED, metric);
		}

		if (StringUtils.isBlank(metric.get("title"))) {
			ose.addError(AqlDataSourceErrorCode.METRIC_TITLE_REQUIRED, metric);
		}

		dataSource.setSeries(metric);
	}

	private void setCriteria(AqlDataSource dataSource, Map<String, Object> options, OpenSpecimenException ose) {
		String criteria = (String) options.get("criteria");
		if (StringUtils.isBlank(criteria)) {
			ose.addError(AqlDataSourceErrorCode.CRITERIA_REQUIRED);
			return;
		}

		dataSource.setCriteria(criteria);
	}

}
