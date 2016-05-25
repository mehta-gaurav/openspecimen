package com.krishagni.catissueplus.core.dashboard.serivce;

public interface DataSourceRegistrar {
	public void register(DataSourceFactory factory);

	public DataSourceFactory getFactory(String type);
}
