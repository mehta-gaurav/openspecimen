package com.krishagni.catissueplus.core.dashboard.domain.factory;

import com.krishagni.catissueplus.core.common.errors.ErrorCode;

public enum ViewDashletsErrorCode implements ErrorCode {
	NAME_REQUIRED,

	DUP_NAME,

	VIEW_NAME_REQUIRED,

	NOT_FOUND;

	@Override
	public String code() {
		return "VIEW_DASHLETS_" + this.name();
	}
}
