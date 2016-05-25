package com.krishagni.catissueplus.core.dashboard.serivce.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishagni.catissueplus.core.common.errors.ErrorType;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.dashboard.serivce.AqlDsErrorCode;
import com.krishagni.catissueplus.core.dashboard.serivce.DataSource;
import com.krishagni.catissueplus.core.dashboard.serivce.DataSourceFactory;

public class AqlDataSourceFactoryImpl implements DataSourceFactory {

	@Override
	public String getType() {
		return "AQL";
	}

	@Override
	public DataSource createDataSource(Map<String, Object> opts) {
		OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);

		AqlDataSource dataSource = null;
		try {
			dataSource = new ObjectMapper().convertValue(opts, AqlDataSource.class);
			ensureValidCategory(dataSource.getCategory(), ose);
			ensureValidSeries(dataSource.getSeries(), ose);
			ensureValidMetric(dataSource.getMetric(), ose);
		} catch (Exception e) {
			ose.addError(AqlDsErrorCode.INVALID_OPTS);
		}

		ose.checkAndThrow();
		return dataSource;
	}

	private void ensureValidCategory(AqlDataSource.ExprTitle expr, OpenSpecimenException ose) {
		AqlDsErrorCode[] errorCodes = {AqlDsErrorCode.CATEGORY_REQ, AqlDsErrorCode.CATEGORY_EXPR_REQ, AqlDsErrorCode.CATEGORY_TITLE_REQ};
		ensureValidity(expr, false, errorCodes, ose);
	}

	private void ensureValidSeries(AqlDataSource.ExprTitle expr, OpenSpecimenException ose) {
		AqlDsErrorCode[] errorCodes = {AqlDsErrorCode.SERIES_EXPR_REQ, AqlDsErrorCode.SERIES_TITLE_REQ};
		ensureValidity(expr, true, errorCodes, ose);
	}

	private void ensureValidMetric(AqlDataSource.ExprTitle expr, OpenSpecimenException ose) {
		AqlDsErrorCode[] errorCodes = {AqlDsErrorCode.METRIC_REQ, AqlDsErrorCode.METRIC_EXPR_REQ, AqlDsErrorCode.METRIC_TITLE_REQ};
		ensureValidity(expr, false, errorCodes, ose);
	}

	private void ensureValidity(AqlDataSource.ExprTitle expr, boolean optional, AqlDsErrorCode[] errorCodes, OpenSpecimenException ose) {
		if (optional && (expr == null || expr.isEmpty())) {
			return;
		}

		if (expr == null) {
			ose.addError(errorCodes[0]);
			return;
		}

		if (StringUtils.isBlank(expr.getExpr())) {
			ose.addError(errorCodes[1]);
		}

		if (StringUtils.isBlank(expr.getTitle())) {
			ose.addError(errorCodes[2]);
		}
	}
}
