package com.krishagni.catissueplus.core.dashboard.domain;

import com.krishagni.catissueplus.core.biospecimen.domain.BaseEntity;

public class DashletConfig extends BaseEntity {
	private String name;

	private String title;

	private String dataSource;

	private String chartOpts;

	private String activityStatus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getChartOpts() {
		return chartOpts;
	}

	public void setChartOpts(String chartOpts) {
		this.chartOpts = chartOpts;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public void update(DashletConfig other) {
		setName(other.getName());
		setTitle(other.getTitle());
		setDataSource(other.getDataSource());
		setChartOpts(other.getChartOpts());
	}
}
