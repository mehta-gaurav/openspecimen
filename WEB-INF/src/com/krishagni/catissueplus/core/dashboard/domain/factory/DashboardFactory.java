package com.krishagni.catissueplus.core.dashboard.domain.factory;

import com.krishagni.catissueplus.core.dashboard.domain.Dashboard;
import com.krishagni.catissueplus.core.dashboard.events.DashboardDetail;

public interface DashboardFactory {
	public Dashboard createDashboard(DashboardDetail detail);

	public Dashboard createDashboard(Dashboard existing, DashboardDetail detail);
}
