package com.krishagni.catissueplus.core.dashboard.events;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.krishagni.catissueplus.core.common.AttributeModifiedSupport;
import com.krishagni.catissueplus.core.common.ListenAttributeChanges;
import com.krishagni.catissueplus.core.common.util.Utility;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;

@ListenAttributeChanges
public class DashletConfigDetail extends AttributeModifiedSupport {
	private Long id;

	private String name;

	private String title;

	private Map<String, Object> dataSource;

	private Map<String, Object> chartOpts;

	private String activityStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Map<String, Object> getDataSource() {
		return dataSource;
	}

	public void setDataSource(Map<String, Object> dataSource) {
		this.dataSource = dataSource;
	}

	public Map<String, Object> getChartOpts() {
		return chartOpts;
	}

	public void setChartOpts(Map<String, Object> chartOpts) {
		this.chartOpts = chartOpts;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public static DashletConfigDetail from(DashletConfig dashletCfg) {
		DashletConfigDetail detail = new DashletConfigDetail();
		detail.setId(dashletCfg.getId());
		detail.setName(dashletCfg.getName());
		detail.setTitle(dashletCfg.getTitle());
		detail.setDataSource(Utility.jsonToMap(dashletCfg.getDataSource()));
		detail.setChartOpts(Utility.jsonToMap(dashletCfg.getChartOpts()));
		detail.setActivityStatus(dashletCfg.getActivityStatus());

		return detail;
	}

	public static List<DashletConfigDetail> from(Collection<DashletConfig> dashletCfgs) {
		return dashletCfgs.stream().map(DashletConfigDetail::from).collect(Collectors.toList());
	}
}
