package com.krishagni.catissueplus.core.dashboard.domain.factory.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.krishagni.catissueplus.core.administrative.domain.User;
import com.krishagni.catissueplus.core.administrative.domain.factory.UserErrorCode;
import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.errors.ActivityStatusErrorCode;
import com.krishagni.catissueplus.core.common.errors.ErrorType;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.events.UserSummary;
import com.krishagni.catissueplus.core.common.util.AuthUtil;
import com.krishagni.catissueplus.core.common.util.Status;
import com.krishagni.catissueplus.core.dashboard.domain.Dashboard;
import com.krishagni.catissueplus.core.dashboard.domain.Dashlet;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashboardErrorCode;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashboardFactory;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashletConfigErrorCode;
import com.krishagni.catissueplus.core.dashboard.events.DashboardDetail;
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;
import com.krishagni.catissueplus.core.dashboard.events.DashletDetail;

public class DashboardFactoryImpl implements DashboardFactory {
	private DaoFactory daoFactory;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public Dashboard createDashboard(DashboardDetail detail) {
		return createDashboard(null, detail);
	}

	@Override
	public Dashboard createDashboard(Dashboard existing, DashboardDetail detail) {
		OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);

		Dashboard dashboard = new Dashboard();
		if (existing != null) {
			dashboard.setId(existing.getId());
		}

		setUser(detail, existing, dashboard, ose);
		setDashlets(detail, existing, dashboard, ose);
		setActivityStatus(detail, existing, dashboard, ose);

		ose.checkAndThrow();
		return dashboard;
	}

	private void setUser(DashboardDetail detail, Dashboard dashboard, OpenSpecimenException ose) {
		UserSummary inputUser = detail.getUser();
		if (inputUser == null) {
			dashboard.setUser(AuthUtil.getCurrentUser());
			return;
		}

		User user = null;
		Object key = null;
		if (inputUser.getId() != null) {
			user = daoFactory.getUserDao().getById(inputUser.getId());
			key = inputUser.getId();
		} else if (StringUtils.isNotBlank(inputUser.getEmailAddress())) {
			user = daoFactory.getUserDao().getUserByEmailAddress(inputUser.getEmailAddress());
			key = inputUser.getEmailAddress();
		} else if (StringUtils.isNotBlank(inputUser.getLoginName()) && StringUtils.isNotBlank(inputUser.getDomain())) {
			user = daoFactory.getUserDao().getUser(inputUser.getLoginName(), inputUser.getDomain());
			key = inputUser.getLoginName() + "(" + inputUser.getDomain() + ")";
		} else {
			user = AuthUtil.getCurrentUser();
		}

		if (user == null) {
			ose.addError(UserErrorCode.NOT_FOUND, key);
			return;
		}

		dashboard.setUser(user);
	}

	private void setUser(DashboardDetail detail, Dashboard existing, Dashboard dashboard, OpenSpecimenException ose) {
		if (existing == null  || detail.isAttrModified("user")) {
			setUser(detail, dashboard, ose);
		} else {
			dashboard.setUser(existing.getUser());
		}
	}

	private void setDashlets(DashboardDetail detail, Dashboard dashboard, OpenSpecimenException ose) {
		List<DashletDetail> dashletDetails = detail.getDashlets();
		if (CollectionUtils.isEmpty(dashletDetails)) {
			ose.addError(DashboardErrorCode.NO_DASHLETS);
			return;
		}

		Set<Dashlet> dashlets = new LinkedHashSet<>();
		for (DashletDetail dashletDetail : dashletDetails) {
			Dashlet dashlet = getDashlet(dashletDetail, dashboard, ose);
			if (dashlet == null) {
				continue;
			}

			dashlets.add(dashlet);
		}

		if (dashletDetails.size() != dashlets.size()) {
			return;
		}

		dashboard.setDashlets(dashlets);
	}

	private void setDashlets(DashboardDetail detail, Dashboard existing, Dashboard dashboard, OpenSpecimenException ose) {
		if (existing == null  || detail.isAttrModified("dashlets")) {
			setDashlets(detail, dashboard, ose);
		} else {
			dashboard.setDashlets(existing.getDashlets());
		}
	}

	private void setActivityStatus(DashboardDetail detail, Dashboard dashboard, OpenSpecimenException ose) {
		String status = detail.getActivityStatus();
		if (StringUtils.isBlank(status)) {
			dashboard.setActivityStatus(Status.ACTIVITY_STATUS_ACTIVE.getStatus());
		} else if (Status.isValidActivityStatus(status)) {
			dashboard.setActivityStatus(status);
		} else {
			ose.addError(ActivityStatusErrorCode.INVALID);
		}
	}

	private void setActivityStatus(DashboardDetail detail, Dashboard existing, Dashboard dashboard, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("activityStatus")) {
			setActivityStatus(detail, dashboard, ose);
		} else {
			dashboard.setActivityStatus(existing.getActivityStatus());
		}
	}

	private Dashlet getDashlet(DashletDetail dashletDetail, Dashboard dashboard, OpenSpecimenException ose) {
		DashletConfig config = getConfig(dashletDetail.getConfig(), ose);
		if (config == null) {
			return null;
		}

		//
		// TODO: Verification of row/column and height/width values
		//
		Dashlet dashlet = new Dashlet();
		dashlet.setDashboard(dashboard);
		dashlet.setDashletConfig(config);
		dashlet.setHeight(dashletDetail.getHeight());
		dashlet.setWidth(dashletDetail.getWidth());
		dashlet.setRow(dashletDetail.getRow());
		dashlet.setColumn(dashletDetail.getColumn());
		return dashlet;
	}

	private DashletConfig getConfig(DashletConfigDetail detail, OpenSpecimenException ose) {
		if (detail == null || (detail.getId() == null && StringUtils.isBlank(detail.getName()))) {
			ose.addError(DashletConfigErrorCode.NAME_OR_ID_REQ);
			return null;
		}

		DashletConfig config = null;
		Object key = null;
		if (detail.getId() != null) {
			config = daoFactory.getDashletConfigDao().getById(detail.getId());
			key = detail.getId();
		} else {
			config = daoFactory.getDashletConfigDao().getByName(detail.getName());
			key = detail.getName();
		}

		if (config == null) {
			ose.addError(DashletConfigErrorCode.NOT_FOUND, key);
		}

		return config;
	}
}
