package com.krishagni.catissueplus.core.dashboard.domain.factory;

import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;

public interface DashletFactory {
	public DashletConfig createDashlet(DashletConfigDetail detail);
	
	public DashletConfig createDashlet(DashletConfig existing, DashletConfigDetail detail);
}
