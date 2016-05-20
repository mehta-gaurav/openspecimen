package com.krishagni.catissueplus.core.dashboard.domain.factory.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.errors.ErrorType;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.domain.ViewDashlets;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashletErrorCode;
import com.krishagni.catissueplus.core.dashboard.domain.factory.ViewDashletsErrorCode;
import com.krishagni.catissueplus.core.dashboard.domain.factory.ViewDashletsFactory;
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;
import com.krishagni.catissueplus.core.dashboard.events.ViewDashletsDetail;

public class ViewDashletsFactoryImpl implements ViewDashletsFactory {
	private DaoFactory daoFactory;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public ViewDashlets createViewDashlets(ViewDashletsDetail detail) {
		return createViewDashlets(null, detail);
	}
	
	public ViewDashlets createViewDashlets(ViewDashlets existing, ViewDashletsDetail detail) {
		ViewDashlets view = new ViewDashlets();
		OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);
		
		if (existing != null) {
			view.setId(existing.getId());
		}
		
		setName(detail, existing, view, ose);
		setViewName(detail, existing, view, ose);
		setDashlets(detail, existing, view, ose);

		ose.checkAndThrow();
		return view;
	}
	
	private void setName(ViewDashletsDetail detail, ViewDashlets view, OpenSpecimenException ose) {
		String name = detail.getName();
		if (StringUtils.isBlank(name)) {
			ose.addError(ViewDashletsErrorCode.NAME_REQUIRED);
		}
		
		view.setName(name);
	}
	
	private void setName(ViewDashletsDetail detail, ViewDashlets existing, ViewDashlets view, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("viewName")) {
			setName(detail, view, ose);
		} else {
			view.setName(existing.getName());
		}
	}
	
	private void setViewName(ViewDashletsDetail detail, ViewDashlets view, OpenSpecimenException ose) {
		String viewName = detail.getViewName();
		if (StringUtils.isBlank(viewName)) {
			ose.addError(ViewDashletsErrorCode.VIEW_NAME_REQUIRED);
		}
		
		view.setViewName(viewName);
	}
	
	private void setViewName(ViewDashletsDetail detail, ViewDashlets existing, ViewDashlets view, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("viewName")) {
			setViewName(detail, view, ose);
		} else {
			view.setViewName(existing.getViewName());
		}
	}
	
	private void setDashlets(ViewDashletsDetail detail, ViewDashlets view, OpenSpecimenException ose) {
		List<DashletConfigDetail> dashletDetails = detail.getDashlets();
		if (CollectionUtils.isEmpty(dashletDetails)) {
			return;
		}
		
		Set<DashletConfig> dashlets = new HashSet<>();
		for (DashletConfigDetail dashletDetail : dashletDetails) {
			DashletConfig dashlet = daoFactory.getDashletDao().getById(dashletDetail.getId());
			if (dashlet == null) {
				ose.addError(DashletErrorCode.NOT_FOUND, dashletDetail.getId());
				return;
			}
			
			dashlets.add(dashlet);
		}
		
		view.setDashlets(dashlets);
	}
	
	private void setDashlets(ViewDashletsDetail detail, ViewDashlets existing, ViewDashlets view, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("dashlets")) {
			setDashlets(detail, view, ose);
		} else {
			view.setDashlets(existing.getDashlets());
		}
	}
}
