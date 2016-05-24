package com.krishagni.catissueplus.core.dashboard.serivce;

import com.krishagni.catissueplus.core.common.errors.ErrorCode;

public enum AqlDataSourceErrorCode implements ErrorCode {
	CATEGORY_REQUIRED,

	INVALID_CATEGORY_OPT,

	CATEGORY_EXPR_REQUIRED,

	CATEGORY_TITLE_REQUIRED,

	INVALID_SERIES_OPT,

	SERIES_EXPR_REQUIRED,

	SERIES_TITLE_REQUIRED,

	METRIC_REQUIRED,

	INVALID_METRIC_OPT,

	METRIC_EXPR_REQUIRED,

	METRIC_TITLE_REQUIRED,

	CRITERIA_REQUIRED;

	@Override
	public String code() {
		return "AQL_DS_" + this.name();
	}

}
