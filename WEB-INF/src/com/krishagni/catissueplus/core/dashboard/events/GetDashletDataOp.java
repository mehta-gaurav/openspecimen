package com.krishagni.catissueplus.core.dashboard.events;

public class GetDashletDataOp {
	private Long dashboardId;

	private String dashletName;

	public Long getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(Long dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getDashletName() {
		return dashletName;
	}

	public void setDashletName(String dashletName) {
		this.dashletName = dashletName;
	}
}
