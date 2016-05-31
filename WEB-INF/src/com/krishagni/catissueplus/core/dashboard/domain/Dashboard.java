package com.krishagni.catissueplus.core.dashboard.domain;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.krishagni.catissueplus.core.administrative.domain.User;
import com.krishagni.catissueplus.core.biospecimen.domain.BaseEntity;

public class Dashboard extends BaseEntity {
	private User user;

	private Set<Dashlet> dashlets = new LinkedHashSet<>();

	private String activityStatus;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Dashlet> getDashlets() {
		return dashlets;
	}

	public void setDashlets(Set<Dashlet> dashlets) {
		this.dashlets = dashlets;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public void update(Dashboard other) {
		setUser(other.getUser());
		updateDashlets(other);
	}

	public void updateDashlets(Dashboard other) {
		Map<DashletConfig, Dashlet> existingDashlets = new HashMap<>();
		for (Dashlet dashlet : getDashlets()) {
			existingDashlets.put(dashlet.getDashletConfig(), dashlet);
		}

		for (Dashlet newDashlet : other.getDashlets()) {
			Dashlet oldDashlet = existingDashlets.remove(newDashlet.getDashletConfig());
			if (oldDashlet == null) {
				newDashlet.setDashboard(this);
				getDashlets().add(newDashlet);
			} else {
				oldDashlet.update(newDashlet);
			}
		}

		getDashlets().removeAll(existingDashlets.values());
	}
}
