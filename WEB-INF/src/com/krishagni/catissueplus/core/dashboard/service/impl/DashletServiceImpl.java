package com.krishagni.catissueplus.core.dashboard.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.PlusTransactional;
import com.krishagni.catissueplus.core.common.errors.ErrorType;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.domain.ViewDashlets;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashletErrorCode;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashletFactory;
import com.krishagni.catissueplus.core.dashboard.domain.factory.ViewDashletsErrorCode;
import com.krishagni.catissueplus.core.dashboard.domain.factory.ViewDashletsFactory;
import com.krishagni.catissueplus.core.dashboard.events.ChartDetail;
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;
import com.krishagni.catissueplus.core.dashboard.events.ViewDashletsDetail;
import com.krishagni.catissueplus.core.dashboard.service.DashletDataSource;
import com.krishagni.catissueplus.core.dashboard.service.DashletService;

public class DashletServiceImpl implements DashletService {
	private DaoFactory daoFactory;
	
	private DashletFactory dashletFactory;
	
	private ViewDashletsFactory viewDashletFactory;
	
	private Map<String, DashletDataSource> dataSources;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void setDashletFactory(DashletFactory dashletFactory) {
		this.dashletFactory = dashletFactory;
	}
	
	public void setViewDashletFactory(ViewDashletsFactory viewDashletFactory) {
		this.viewDashletFactory = viewDashletFactory;
	}

	public void setDataSources(Map<String, DashletDataSource> dataSources) {
		this.dataSources = dataSources;
	}

	@Override
	@PlusTransactional
	public ResponseEvent<List<DashletConfigDetail>> getDashlets() {
		try {
			List<DashletConfig> dashlets = daoFactory.getDashletDao().getDashlets();
			return ResponseEvent.response(DashletConfigDetail.from(dashlets));
		} catch (Exception ex) {
			return ResponseEvent.serverError(ex);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashletConfigDetail> getDashlet(RequestEvent<Long> req) {
		try {
			DashletConfig dashlet = daoFactory.getDashletDao().getById(req.getPayload());
			if (dashlet == null) {
				return ResponseEvent.userError(DashletErrorCode.NOT_FOUND);
			}
			
			return ResponseEvent.response(DashletConfigDetail.from(dashlet));
		} catch (Exception ex) {
			return ResponseEvent.serverError(ex);
		}
	}
	
	@Override
	@PlusTransactional
	public ResponseEvent<DashletConfigDetail> createDashlet(RequestEvent<DashletConfigDetail> req) {
		try {
			DashletConfigDetail detail = req.getPayload();
			DashletConfig dashlet = dashletFactory.createDashlet(detail);
			
			OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);
			ensureUniqueName(null, dashlet, ose);
			ose.checkAndThrow();
			
			daoFactory.getDashletDao().saveOrUpdate(dashlet);
			return ResponseEvent.response(DashletConfigDetail.from(dashlet));
		} catch(OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception ex) {
			return ResponseEvent.serverError(ex);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashletConfigDetail> updateDashlet(RequestEvent<DashletConfigDetail> req) {
		try {
			DashletConfigDetail detail = req.getPayload();
			DashletConfig existing = daoFactory.getDashletDao().getById(detail.getId());
			if (existing == null) {
				return ResponseEvent.userError(DashletErrorCode.NOT_FOUND); 
			}
			
			DashletConfig dashlet = dashletFactory.createDashlet(existing, detail);
			
			OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);
			ensureUniqueName(existing, dashlet, ose);
			ose.checkAndThrow();
			
			existing.update(dashlet);
			return ResponseEvent.response(DashletConfigDetail.from(dashlet));
		} catch(OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception ex) {
			return ResponseEvent.serverError(ex);
		}
	}
	
	@Override
	@PlusTransactional
	public ResponseEvent<ChartDetail> getChartDetail(RequestEvent<Long> req) {
		try {
			DashletConfig dashlet = daoFactory.getDashletDao().getById(req.getPayload());
			if (dashlet == null) {
				return ResponseEvent.userError(DashletErrorCode.NOT_FOUND);
			}
			
			ChartDetail chartDetail = dataSources.get(dashlet.getDataSourceType()).getData(dashlet);
			return ResponseEvent.response(chartDetail);
		} catch(OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception ex) {
			return ResponseEvent.serverError(ex);
		}
	}
	
	@Override
	@PlusTransactional
	public ResponseEvent<ViewDashletsDetail> getViewDashlets(RequestEvent<String> req) {
		try {
			ViewDashlets view = daoFactory.getDashletDao().getViewDashletsByName(req.getPayload());
			if (view == null) {
				return ResponseEvent.userError(ViewDashletsErrorCode.NOT_FOUND, req.getPayload());
			}
			
			return ResponseEvent.response(ViewDashletsDetail.from(view));
		} catch (Exception ex) {
			return ResponseEvent.serverError(ex);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<ViewDashletsDetail> createViewDashlets(RequestEvent<ViewDashletsDetail> req) {
		try {
			ViewDashletsDetail detail = req.getPayload();
			ViewDashlets view = viewDashletFactory.createViewDashlets(detail);
			
			OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);
			ensureUniqueViewDashletsName(null, view, ose);
			ose.checkAndThrow();
			
			daoFactory.getDashletDao().saveViewDashlets(view);
			return ResponseEvent.response(ViewDashletsDetail.from(view));
		} catch(OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception ex) {
			return ResponseEvent.serverError(ex);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<ViewDashletsDetail> updateViewDashlets(RequestEvent<ViewDashletsDetail> req) {
		try {
			ViewDashletsDetail detail = req.getPayload();
			ViewDashlets existing = daoFactory.getDashletDao().getViewDashletsById(detail.getId());
			if (existing == null) {
				return ResponseEvent.userError(ViewDashletsErrorCode.NOT_FOUND, detail.getId());
			}
			
			ViewDashlets view = viewDashletFactory.createViewDashlets(existing, detail);
			
			OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);
			ensureUniqueViewDashletsName(existing, view, ose);
			ose.checkAndThrow();
			
			existing.update(view);
			return ResponseEvent.response(ViewDashletsDetail.from(view));
		} catch(OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception ex) {
			return ResponseEvent.serverError(ex);
		}
	}
	
	private void ensureUniqueName(DashletConfig existing, DashletConfig newDashlet, OpenSpecimenException ose) {
		if (existing != null && existing.getName().equals(newDashlet.getName())) {
			return;
		}
		
		DashletConfig dashlet = daoFactory.getDashletDao().getDashletByName(newDashlet.getName());
		if (dashlet != null) {
			ose.addError(DashletErrorCode.DUP_NAME, newDashlet.getName());
		}
	}
	
	private void ensureUniqueViewDashletsName(ViewDashlets existing, ViewDashlets newView, OpenSpecimenException ose) {
		if (existing != null && existing.getName().equals(newView.getName())) {
			return;
		}
		
		ViewDashlets view = daoFactory.getDashletDao().getViewDashletsByName(newView.getName());
		if (view != null) {
			ose.addError(ViewDashletsErrorCode.DUP_NAME, view.getName());
		}
	}
}
