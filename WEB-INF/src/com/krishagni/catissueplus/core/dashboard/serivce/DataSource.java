package com.krishagni.catissueplus.core.dashboard.serivce;

import java.util.Map;

import com.krishagni.catissueplus.core.dashboard.events.DataDetail;

public interface DataSource {
	DataDetail execute(Map<String, Object> input);
}
