package com.krishagni.catissueplus.core.dashboard.serivce;

import java.util.List;

import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;

public interface DashletConfigService {
	public ResponseEvent<List<DashletConfigDetail>> getDashletConfigs();

	public ResponseEvent<DashletConfigDetail> getDashletConfig(RequestEvent<Long> req);

	public ResponseEvent<DashletConfigDetail> createDashletConfig(RequestEvent<DashletConfigDetail> req);

	public ResponseEvent<DashletConfigDetail> updateDashletConfig(RequestEvent<DashletConfigDetail> req);

	public ResponseEvent<DashletConfigDetail> deleteDashletConfig(RequestEvent<DashletConfigDetail> req);
}
