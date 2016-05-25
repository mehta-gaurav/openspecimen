package com.krishagni.catissueplus.core.dashboard.repository;

import java.util.List;

import com.krishagni.catissueplus.core.common.repository.Dao;
import com.krishagni.catissueplus.core.dashboard.domain.Dashboard;

public interface DashboardDao extends Dao<Dashboard> {
	public List<Dashboard> getDashboards();
}
