package com.krishagni.catissueplus.core.dashboard.domain.factory;

import com.krishagni.catissueplus.core.common.errors.ErrorCode;

public enum DashletConfigErrorCode implements ErrorCode {
	NOT_FOUND,

	DUP_NAME,

	NAME_REQ,

	TITLE_REQ,

	DS_REQ,

	DS_TYPE_REQ,

	INVALID_DS_TYPE;

	@Override
	public String code() {
		return "DASHLET_CFG_" + this.name();
	}
}
