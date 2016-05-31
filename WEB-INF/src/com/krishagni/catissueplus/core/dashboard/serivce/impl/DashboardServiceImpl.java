package com.krishagni.catissueplus.core.dashboard.serivce.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.PlusTransactional;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.domain.Dashboard;
import com.krishagni.catissueplus.core.dashboard.domain.Dashlet;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashboardErrorCode;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashboardFactory;
import com.krishagni.catissueplus.core.dashboard.events.DashboardDetail;
import com.krishagni.catissueplus.core.dashboard.events.DashletData;
import com.krishagni.catissueplus.core.dashboard.events.GetDashletDataOp;
import com.krishagni.catissueplus.core.dashboard.serivce.DashboardService;
import com.krishagni.catissueplus.core.dashboard.serivce.DataSourceRegistrar;

public class DashboardServiceImpl implements DashboardService {
	private DaoFactory daoFactory;

	private DashboardFactory dashboardFactory;

	private DataSourceRegistrar dataSourceRegistrar;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void setDashboardFactory(DashboardFactory dashboardFactory) {
		this.dashboardFactory = dashboardFactory;
	}

	public void setDataSourceRegistrar(DataSourceRegistrar dataSourceRegistrar) {
		this.dataSourceRegistrar = dataSourceRegistrar;
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

			Dashboard dashboard = dashboardFactory.createDashboard(existing, detail);

			existing.update(dashboard);
			return ResponseEvent.response(DashboardDetail.from(existing));
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

	@SuppressWarnings("unchecked")
	@Override
	@PlusTransactional
	public ResponseEvent<DashletData> getData(RequestEvent<GetDashletDataOp> req) {
		try {
			GetDashletDataOp op = req.getPayload();

			Dashboard dashboard = daoFactory.getDashboardDao().getById(op.getDashboardId());
			if (dashboard == null) {
				return ResponseEvent.userError(DashboardErrorCode.NOT_FOUND, op.getDashboardId());
			}

			if (StringUtils.isBlank(op.getDashletName())) {
				return ResponseEvent.userError(DashboardErrorCode.DASHLET_NAME_REQ);
			}

			Dashlet dashlet = dashboard.getDashlets().stream()
				.filter(d -> d.getDashletConfig().getName().equals(op.getDashletName()))
				.findFirst().orElse(null);
			if (dashlet == null) {
				return ResponseEvent.userError(DashboardErrorCode.DASHLET_NOT_FOUND, op.getDashletName());
			}

			return ResponseEvent.response(executeDashlet(dashlet));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	private DashletData executeDashlet(Dashlet dashlet) {
		DashletConfig cfg = dashlet.getDashletConfig();
		String type = cfg.getDataSourceType();
		Map<String, Object> options = cfg.getDataSourceOptions();
		return dataSourceRegistrar.getFactory(type).createDataSource(options).execute(Collections.emptyMap());
	}
}
