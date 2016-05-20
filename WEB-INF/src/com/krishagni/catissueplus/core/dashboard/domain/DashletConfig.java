package com.krishagni.catissueplus.core.dashboard.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.krishagni.catissueplus.core.biospecimen.domain.BaseEntity;

public class DashletConfig extends BaseEntity {
	private String name;

	private String caption;

	private String dataSourceType;

	private Map<String, String> dataSourceOpts  = new HashMap<>();

	private String chartType;

	private Map<String, String> chartOpts = new HashMap<>();

	private String activityStatus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public Map<String, String> getDataSourceOpts() {
		return dataSourceOpts;
	}

	public void setDataSourceOpts(Map<String, String> dataSourceOpts) {
		this.dataSourceOpts = dataSourceOpts;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public Map<String, String> getChartOpts() {
		return chartOpts;
	}

	public void setChartOpts(Map<String, String> chartOpts) {
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
		setCaption(other.getCaption());
		setDataSourceType(other.getDataSourceType());
		setChartType(other.getChartType());

		updateOpts(getDataSourceOpts(), other.getDataSourceOpts());
		updateOpts(getChartOpts(), other.getChartOpts());
	}

	private void updateOpts(Map<String, String> oldOpts, Map<String, String> newOpts) {
		List<String> oldNames = new ArrayList<String>(oldOpts.keySet());

		for (Map.Entry<String, String> entry: newOpts.entrySet()) {
			oldNames.remove(entry.getKey());
			oldOpts.put(entry.getKey(), entry.getValue());
		}

		for (String name: oldNames) {
			oldOpts.remove(name);
		}
	}
}
