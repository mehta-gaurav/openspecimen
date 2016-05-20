package com.krishagni.catissueplus.core.dashboard.domain.factory;

import com.krishagni.catissueplus.core.common.errors.ErrorCode;

public enum DashletErrorCode implements ErrorCode {

	NAME_REQUIRED,

	CAPTION_REQUIRED,

	DATA_SOURCE_TYPE_REQUIRED,

	INVALID_DATA_SOURCE_TYPE,

	CHART_TYPE_REQUIRED,

	NOT_FOUND,

	DUP_NAME,

	INVALID_CHART_TYPE,

	INVALID_SEL_COL_COUNT,

	NON_NUMERIC_DATA;

	@Override
	public String code() {
		return "DASHLET_" + this.name();
	}
}
