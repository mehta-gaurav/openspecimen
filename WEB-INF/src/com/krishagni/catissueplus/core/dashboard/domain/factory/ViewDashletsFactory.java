package com.krishagni.catissueplus.core.dashboard.domain.factory;

import com.krishagni.catissueplus.core.dashboard.domain.ViewDashlets;
import com.krishagni.catissueplus.core.dashboard.events.ViewDashletsDetail;

public interface ViewDashletsFactory {
	public ViewDashlets createViewDashlets(ViewDashletsDetail detail);

	public ViewDashlets createViewDashlets(ViewDashlets existing, ViewDashletsDetail detail);
}
