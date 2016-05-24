package com.krishagni.catissueplus.core.dashboard.domain.factory;

import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;

public interface DashletConfigFactory {
	public DashletConfig createDashletConfig(DashletConfigDetail detail);

	public DashletConfig createDashletConfig(DashletConfig existing, DashletConfigDetail detail);
}
