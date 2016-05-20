package com.krishagni.catissueplus.core.dashboard.service;


import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.events.ChartDetail;

public interface DashletDataSource {
	public String getType();
	
	public ChartDetail getData(DashletConfig dashlet);
}
