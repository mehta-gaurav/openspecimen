package com.krishagni.catissueplus.core.dashboard.serivce.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.krishagni.catissueplus.core.dashboard.serivce.DataSourceFactory;
import com.krishagni.catissueplus.core.dashboard.serivce.DataSourceRegistrar;

public class DefaultDataSourceRegistrar implements DataSourceRegistrar {
	Map<String, DataSourceFactory> dataSourceReg = new HashMap<>();

	@Override
	public void register(DataSourceFactory dataSourceFactory) {
		dataSourceReg.put(dataSourceFactory.getType(), dataSourceFactory);
	}

	@Override
	public DataSourceFactory getDataSourceFactory(String type) {
		return dataSourceReg.get(type);
	}

	public void setDataSourceFactories(List<DataSourceFactory> factories) {
		dataSourceReg.clear();

		factories.forEach(factory -> {
			register(factory);
		});
	}
}
