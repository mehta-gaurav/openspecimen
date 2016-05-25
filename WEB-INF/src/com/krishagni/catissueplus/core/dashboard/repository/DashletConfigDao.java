package com.krishagni.catissueplus.core.dashboard.repository;

import java.util.List;

import com.krishagni.catissueplus.core.common.repository.Dao;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;

public interface DashletConfigDao extends Dao<DashletConfig> {
	public List<DashletConfig> getDashletConfigs();

	public DashletConfig getByName(String name);
}
