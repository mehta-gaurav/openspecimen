package com.krishagni.catissueplus.core.dashboard.domain.factory.impl;

import org.apache.commons.lang.StringUtils;

import com.krishagni.catissueplus.core.common.errors.ActivityStatusErrorCode;
import com.krishagni.catissueplus.core.common.errors.ErrorType;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.util.Status;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashletErrorCode;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashletFactory;
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;

public class DashletFactoryImpl implements DashletFactory {

	@Override
	public DashletConfig createDashlet(DashletConfigDetail detail) {
		return createDashlet(null, detail);
	}

	@Override
	public DashletConfig createDashlet(DashletConfig existing, DashletConfigDetail detail) {
		DashletConfig dashlet = new DashletConfig();
		OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);
		
		if (existing != null) {
			dashlet.setId(existing.getId());
		}
		
		setName(detail, existing, dashlet, ose);
		setCaption(detail, existing, dashlet, ose);
		setDataSourceType(detail, existing, dashlet, ose);
		setDataSourceOpts(detail, existing, dashlet, ose);
		setChartType(detail, existing, dashlet, ose);
		setChartOpts(detail, existing, dashlet, ose);
		setActivityStatus(detail, existing, dashlet, ose);
		
		ose.checkAndThrow();
		return dashlet;
	}

	private void setName(DashletConfigDetail detail, DashletConfig dashlet, OpenSpecimenException ose) {
		String name = detail.getName();
		if (StringUtils.isBlank(name)) {
			ose.addError(DashletErrorCode.NAME_REQUIRED);
			return;
		}
		
		dashlet.setName(name);
	}

	private void setName(DashletConfigDetail detail, DashletConfig existing, DashletConfig dashlet, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("name")) {
			setName(detail, dashlet, ose);
		} else {
			dashlet.setName(existing.getName());
		}
	}
	
	private void setCaption(DashletConfigDetail detail, DashletConfig dashlet, OpenSpecimenException ose) {
		String caption = detail.getCaption();
		if (StringUtils.isBlank(caption)) {
			ose.addError(DashletErrorCode.CAPTION_REQUIRED);
			return;
		}
		
		dashlet.setCaption(caption);
	}

	private void setCaption(DashletConfigDetail detail, DashletConfig existing, DashletConfig dashlet, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("caption")) {
			setCaption(detail, dashlet, ose);
		} else {
			dashlet.setCaption(existing.getCaption());
		}
	}
	
	private void setDataSourceType(DashletConfigDetail detail, DashletConfig dashlet, OpenSpecimenException ose) {
		String dataSourceType = detail.getDataSourceType();
		if (StringUtils.isBlank(dataSourceType)) {
			ose.addError(DashletErrorCode.DATA_SOURCE_TYPE_REQUIRED);
			return;
		}
		
		dashlet.setDataSourceType(dataSourceType);
	}
	
	private void setDataSourceType(DashletConfigDetail detail, DashletConfig existing, DashletConfig dashlet, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("dataSourceType")) {
			setDataSourceType(detail, dashlet, ose);
		} else {
			dashlet.setDataSourceType(existing.getDataSourceType());
		}
	}
	
	private void setDataSourceOpts(DashletConfigDetail detail, DashletConfig dashlet, OpenSpecimenException ose) {
		dashlet.setDataSourceOpts(detail.getDataSourceOpts());
	}
	
	private void setDataSourceOpts(DashletConfigDetail detail, DashletConfig existing, DashletConfig dashlet, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("dataSourceOpts")) {
			setDataSourceOpts(detail, dashlet, ose);
		} else {
			dashlet.setDataSourceOpts(existing.getDataSourceOpts());
		}
	}
	
	private void setChartType(DashletConfigDetail detail, DashletConfig dashlet, OpenSpecimenException ose) {
		String chartType = detail.getChartType();
		if (StringUtils.isBlank(chartType)) {
			ose.addError(DashletErrorCode.CHART_TYPE_REQUIRED);
			return;
		}
		
		dashlet.setChartType(detail.getChartType());
	}
	
	private void setChartType(DashletConfigDetail detail, DashletConfig existing, DashletConfig dashlet, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("chartType")) {
			setChartType(detail, dashlet, ose);
		} else {
			dashlet.setChartType(existing.getChartType());
		}
	}
	
	private void setChartOpts(DashletConfigDetail detail, DashletConfig dashlet, OpenSpecimenException ose) {
		dashlet.setChartOpts(detail.getChartOpts());
	}
	
	private void setChartOpts(DashletConfigDetail detail, DashletConfig existing, DashletConfig dashlet, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("chartOpts")) {
			setChartOpts(detail, dashlet, ose);
		} else {
			dashlet.setChartOpts(existing.getChartOpts());
		}
	}
	
	private void setActivityStatus(DashletConfigDetail detail, DashletConfig dashlet, OpenSpecimenException ose) {
		String status = detail.getActivityStatus();
		if (StringUtils.isBlank(status)) {
			dashlet.setActivityStatus(Status.ACTIVITY_STATUS_ACTIVE.getStatus());
		} else if (Status.isValidActivityStatus(status)) {
			dashlet.setActivityStatus(status);
		} else {
			ose.addError(ActivityStatusErrorCode.INVALID);
		}
	}
	
	private void setActivityStatus(DashletConfigDetail detail, DashletConfig existing, DashletConfig dashlet, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("activityStatus")) {
			setActivityStatus(detail, dashlet, ose);
		} else {
			dashlet.setActivityStatus(existing.getActivityStatus());
		}
	}
	
}
