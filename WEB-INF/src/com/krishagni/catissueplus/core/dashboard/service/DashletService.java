package com.krishagni.catissueplus.core.dashboard.service;

import java.util.List;

import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.events.ChartDetail;
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;
import com.krishagni.catissueplus.core.dashboard.events.ViewDashletsDetail;

public interface DashletService {
	public ResponseEvent<List<DashletConfigDetail>> getDashlets();

	public ResponseEvent<DashletConfigDetail> getDashlet(RequestEvent<Long> req);

	public ResponseEvent<DashletConfigDetail> createDashlet(RequestEvent<DashletConfigDetail> req);

	public ResponseEvent<DashletConfigDetail> updateDashlet(RequestEvent<DashletConfigDetail> req);

	public ResponseEvent<ChartDetail> getChartDetail(RequestEvent<Long> req);

	public ResponseEvent<ViewDashletsDetail> getViewDashlets(RequestEvent<String> req);

	public ResponseEvent<ViewDashletsDetail> createViewDashlets(RequestEvent<ViewDashletsDetail> req);

	public ResponseEvent<ViewDashletsDetail> updateViewDashlets(RequestEvent<ViewDashletsDetail> req);
}
