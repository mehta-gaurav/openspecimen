package com.krishagni.catissueplus.core.dashboard.events;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.krishagni.catissueplus.core.common.AttributeModifiedSupport;
import com.krishagni.catissueplus.core.common.ListenAttributeChanges;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;

@ListenAttributeChanges
public class DashletConfigDetail extends AttributeModifiedSupport {
	private Long id;
	
	private String name;
	
	private String caption;
	
	private String dataSourceType;
	
	private Map<String, String> dataSourceOpts;
	
	private String chartType;
	
	private Map<String, String> chartOpts;
	
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

	public static DashletConfigDetail from(DashletConfig dashlet) {
		DashletConfigDetail detail = new DashletConfigDetail();
		detail.setId(dashlet.getId());
		detail.setName(dashlet.getName());
		detail.setCaption(dashlet.getCaption());
		detail.setDataSourceType(dashlet.getDataSourceType());
		detail.setDataSourceOpts(new HashMap<String, String>(dashlet.getDataSourceOpts())); 
		detail.setChartType(dashlet.getChartType());
		detail.setChartOpts(new HashMap<String, String>(dashlet.getChartOpts()));
		detail.setActivityStatus(dashlet.getActivityStatus());
		
		return detail;
	}
	
	public static List<DashletConfigDetail> from(Collection<DashletConfig> dashlets) {
		return dashlets.stream()
				.map(dashlet -> from(dashlet))
				.collect(Collectors.toList());
	}
}
