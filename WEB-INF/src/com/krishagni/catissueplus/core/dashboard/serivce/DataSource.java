package com.krishagni.catissueplus.core.dashboard.serivce;

import java.util.Map;

import com.krishagni.catissueplus.core.dashboard.events.DashletData;

public interface DataSource {
	DashletData execute(Map<String, Object> input);
}
