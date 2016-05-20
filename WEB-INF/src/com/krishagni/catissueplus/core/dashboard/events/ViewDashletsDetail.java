package com.krishagni.catissueplus.core.dashboard.events;

import java.util.List;

import com.krishagni.catissueplus.core.common.AttributeModifiedSupport;
import com.krishagni.catissueplus.core.common.ListenAttributeChanges;
import com.krishagni.catissueplus.core.dashboard.domain.ViewDashlets;

@ListenAttributeChanges
public class ViewDashletsDetail extends AttributeModifiedSupport {
	private Long id ;
	
	private String name;
	
	private String viewName;
	
	private List<DashletConfigDetail> dashlets;

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

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public List<DashletConfigDetail> getDashlets() {
		return dashlets;
	}

	public void setDashlets(List<DashletConfigDetail> dashlets) {
		this.dashlets = dashlets;
	}
	
	public static ViewDashletsDetail from(ViewDashlets view) {
		ViewDashletsDetail detail = new ViewDashletsDetail();
		detail.setId(view.getId());
		detail.setName(view.getName());
		detail.setViewName(view.getViewName());
		detail.setDashlets(DashletConfigDetail.from(view.getDashlets()));
		
		return detail;
	}
}
