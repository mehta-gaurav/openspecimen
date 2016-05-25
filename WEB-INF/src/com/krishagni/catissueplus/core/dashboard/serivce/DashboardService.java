package com.krishagni.catissueplus.core.dashboard.serivce;

import java.util.List;

import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.events.DashboardDetail;

public interface DashboardService {
	public ResponseEvent<List<DashboardDetail>> getDashboards();

	public ResponseEvent<DashboardDetail> getDashboard(RequestEvent<Long> req);

	public ResponseEvent<DashboardDetail> createDashboard(RequestEvent<DashboardDetail> req);

	public ResponseEvent<DashboardDetail> updateDashboard(RequestEvent<DashboardDetail> req);

	public ResponseEvent<DashboardDetail> deleteDashboard(RequestEvent<Long> req);
}
