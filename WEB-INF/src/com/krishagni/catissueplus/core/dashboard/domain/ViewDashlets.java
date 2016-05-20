package com.krishagni.catissueplus.core.dashboard.domain;

import java.util.HashSet;
import java.util.Set;

import com.krishagni.catissueplus.core.biospecimen.domain.BaseEntity;
import com.krishagni.catissueplus.core.common.CollectionUpdater;

public class ViewDashlets extends BaseEntity {
	private String name;

	private String viewName;

	private Set<DashletConfig> dashlets = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public Set<DashletConfig> getDashlets() {
		return dashlets;
	}

	public void setDashlets(Set<DashletConfig> dashlets) {
		this.dashlets = dashlets;
	}

	public void update(ViewDashlets other) {
		setName(other.getName());
		setViewName(other.getViewName());
		CollectionUpdater.update(getDashlets(), other.getDashlets());
	}
}
