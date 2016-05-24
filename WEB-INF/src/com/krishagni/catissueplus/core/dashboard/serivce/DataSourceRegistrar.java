package com.krishagni.catissueplus.core.dashboard.serivce;

public interface DataSourceRegistrar {
	public void register(DataSourceFactory dataSourceFactory);

	public DataSourceFactory getDataSourceFactory(String type);
}
