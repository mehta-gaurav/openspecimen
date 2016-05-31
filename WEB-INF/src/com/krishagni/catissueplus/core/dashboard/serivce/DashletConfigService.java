package com.krishagni.catissueplus.core.dashboard.serivce;

import java.util.List;

import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;

public interface DashletConfigService {
	public ResponseEvent<List<DashletConfigDetail>> getConfigs();

	public ResponseEvent<DashletConfigDetail> getConfig(RequestEvent<Long> req);

	public ResponseEvent<DashletConfigDetail> createConfig(RequestEvent<DashletConfigDetail> req);

	public ResponseEvent<DashletConfigDetail> updateConfig(RequestEvent<DashletConfigDetail> req);

	public ResponseEvent<DashletConfigDetail> deleteConfig(RequestEvent<DashletConfigDetail> req);
}
