package com.krishagni.catissueplus.core.dashboard.domain.factory;

import com.krishagni.catissueplus.core.common.errors.ErrorCode;

public enum DashboardErrorCode implements ErrorCode {
	NOT_FOUND,

	NO_DASHLETS,

	DASHLET_NAME_REQ;

	@Override
	public String code() {
		return "DASHBOARD_" + this.name();
	}

}
