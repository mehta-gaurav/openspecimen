package com.krishagni.catissueplus.core.dashboard.events;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.krishagni.catissueplus.core.common.AttributeModifiedSupport;
import com.krishagni.catissueplus.core.common.ListenAttributeChanges;
import com.krishagni.catissueplus.core.common.events.UserSummary;
import com.krishagni.catissueplus.core.dashboard.domain.Dashboard;

@ListenAttributeChanges
public class DashboardDetail extends AttributeModifiedSupport {
	private Long id;

	private UserSummary user;

	private List<DashletDetail> dashlets;

	private String activityStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserSummary getUser() {
		return user;
	}

	public void setUser(UserSummary user) {
		this.user = user;
	}

	public List<DashletDetail> getDashlets() {
		return dashlets;
	}

	public void setDashlets(List<DashletDetail> dashlets) {
		this.dashlets = dashlets;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public static DashboardDetail from(Dashboard dashboard) {
		DashboardDetail detail = new DashboardDetail();
		detail.setId(dashboard.getId());
		detail.setUser(UserSummary.from(dashboard.getUser()));
		detail.setDashlets(DashletDetail.from(dashboard.getDashlets()));
		detail.setActivityStatus(dashboard.getActivityStatus());

		return detail;
	}

	public static List<DashboardDetail> from(Collection<Dashboard> dashboards) {
		return dashboards.stream().map(DashboardDetail::from).collect(Collectors.toList());
	}
 }
