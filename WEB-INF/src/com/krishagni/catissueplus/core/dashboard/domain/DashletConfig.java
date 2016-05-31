package com.krishagni.catissueplus.core.dashboard.domain;

import java.util.Collections;
import java.util.Map;

import com.krishagni.catissueplus.core.biospecimen.domain.BaseEntity;
import com.krishagni.catissueplus.core.common.util.Utility;

public class DashletConfig extends BaseEntity {
	private String name;

	private String title;

	private String dataSource;

	private String chartOpts;

	private String activityStatus;

	private transient Map<String, Object> dataSourceMap;

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

	public Map<String, Object> getDataSourceMap() {
		if (dataSourceMap == null) {
			dataSourceMap = Utility.jsonToMap(dataSource);
		}

		return dataSourceMap;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
		this.dataSourceMap = null;
	}

	public String getChartOpts() {
		return chartOpts;
	}

	public Map<String, Object> getChartOptsMap() {
		return Utility.jsonToMap(chartOpts);
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

	public String getDataSourceType() {
		return (String)getDataSourceMap().get("type");
	}

	public Map<String, Object> getDataSourceOptions() {
		Map<String, Object> options = (Map<String, Object>)getDataSourceMap().get("options");
		return options == null ? Collections.emptyMap() : options;
	}

	public void update(DashletConfig other) {
		setName(other.getName());
		setTitle(other.getTitle());
		setDataSource(other.getDataSource());
		setChartOpts(other.getChartOpts());
	}
}
