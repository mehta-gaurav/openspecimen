package com.krishagni.catissueplus.core.dashboard.serivce.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.krishagni.catissueplus.core.dashboard.serivce.DataSourceFactory;
import com.krishagni.catissueplus.core.dashboard.serivce.DataSourceRegistrar;

public class DefaultDataSourceRegistrar implements DataSourceRegistrar {
	Map<String, DataSourceFactory> factoriesMap = new HashMap<>();

	@Override
	public void register(DataSourceFactory factory) {
		factoriesMap.put(factory.getType(), factory);
	}

	@Override
	public DataSourceFactory getFactory(String type) {
		return factoriesMap.get(type);
	}

	public void setFactories(List<DataSourceFactory> factories) {
		factoriesMap.clear();
		factories.forEach(this::register);
	}
}
