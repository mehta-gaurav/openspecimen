package com.krishagni.catissueplus.core.dashboard.repository;

import java.util.List;

import com.krishagni.catissueplus.core.common.repository.Dao;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.domain.ViewDashlets;

public interface DashletDao extends Dao<DashletConfig> {
	public List<DashletConfig> getDashlets();
	
	public DashletConfig getDashletByName(String name);
	
	public ViewDashlets getViewDashletsById(Long id);
	
	public ViewDashlets getViewDashletsByName(String name);
	
	public void saveViewDashlets(ViewDashlets view);
}
