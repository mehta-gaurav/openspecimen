package com.krishagni.catissueplus.core.dashboard.serivce;

import com.krishagni.catissueplus.core.common.errors.ErrorCode;

public enum AqlDsErrorCode implements ErrorCode {
	INVALID_OPTS,

	CATEGORY_REQ,

	CATEGORY_EXPR_REQ,

	CATEGORY_TITLE_REQ,

	SERIES_EXPR_REQ,

	SERIES_TITLE_REQ,

	METRIC_REQ,

	METRIC_EXPR_REQ,

	METRIC_TITLE_REQ;

	@Override
	public String code() {
		return "DS_AQL_" + this.name();
	}

}
