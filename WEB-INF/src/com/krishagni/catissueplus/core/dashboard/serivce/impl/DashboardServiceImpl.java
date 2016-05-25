package com.krishagni.catissueplus.core.dashboard.serivce.impl;

import java.util.List;

import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.PlusTransactional;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.domain.Dashboard;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashboardErrorCode;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashboardFactory;
import com.krishagni.catissueplus.core.dashboard.events.DashboardDetail;
import com.krishagni.catissueplus.core.dashboard.serivce.DashboardService;

public class DashboardServiceImpl implements DashboardService {
	private DaoFactory daoFactory;

	private DashboardFactory dashboardFactory;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void setDashboardFactory(DashboardFactory dashboardFactory) {
		this.dashboardFactory = dashboardFactory;
	}

	@Override
	@PlusTransactional
	public ResponseEvent<List<DashboardDetail>> getDashboards() {
		try {
			List<Dashboard> dashboards = daoFactory.getDashboardDao().getDashboards();
			return ResponseEvent.response(DashboardDetail.from(dashboards));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashboardDetail> getDashboard(RequestEvent<Long> req) {
		try {
			Dashboard dashboard = daoFactory.getDashboardDao().getById(req.getPayload());
			if (dashboard == null) {
				return ResponseEvent.userError(DashboardErrorCode.NOT_FOUND, req.getPayload());
			}

			return ResponseEvent.response(DashboardDetail.from(dashboard));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashboardDetail> createDashboard(RequestEvent<DashboardDetail> req) {
		try {
			DashboardDetail detail = req.getPayload();
			Dashboard dashboard = dashboardFactory.createDashboard(detail);

			daoFactory.getDashboardDao().saveOrUpdate(dashboard);
			return ResponseEvent.response(DashboardDetail.from(dashboard));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashboardDetail> updateDashboard(RequestEvent<DashboardDetail> req) {
		try {
			DashboardDetail detail = req.getPayload();
			Dashboard existing = daoFactory.getDashboardDao().getById(detail.getId());
			if (existing == null) {
				return ResponseEvent.userError(DashboardErrorCode.NOT_FOUND, detail.getId());
			}

			Dashboard dashboard = dashboardFactory.createDashboard(detail);

			existing.update(dashboard);
			return ResponseEvent.response(DashboardDetail.from(dashboard));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashboardDetail> deleteDashboard(RequestEvent<Long> req) {
		// TODO Auto-generated method stub
		return null;
	}
}
