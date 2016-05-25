package com.krishagni.catissueplus.core.dashboard.serivce;

import java.util.Map;

public interface DataSourceFactory {
	public String getType();

	public DataSource createDataSource(Map<String, Object> opts);
}
