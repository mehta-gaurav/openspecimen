package com.krishagni.catissueplus.core.dashboard.domain.factory;

import com.krishagni.catissueplus.core.common.errors.ErrorCode;

public enum DashletConfigErrorCode implements ErrorCode {
	NOT_FOUND,

	DUP_NAME,

	NAME_REQUIRED,

	TITLE_REQUIRED,

	DATA_SOURCE_REQUIRED,

	DS_TYPE_REQUIRED,

	INVALID_DS_TYPE,

	OPTIONS_REQUIRED,

	CHART_OPTS_REQUIRED;

	@Override
	public String code() {
		return "DASHLET_CFG_" + this.name();
	}
}
