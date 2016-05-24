package com.krishagni.catissueplus.core.dashboard.serivce.impl;

import java.util.List;

import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.PlusTransactional;
import com.krishagni.catissueplus.core.common.errors.ErrorType;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashletConfigErrorCode;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashletConfigFactory;
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;
import com.krishagni.catissueplus.core.dashboard.serivce.DashletConfigService;

public class DashletConfigServiceImpl implements DashletConfigService {
	private DaoFactory daoFactory;

	private DashletConfigFactory dashletCfgFactory;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void setDashletCfgFactory(DashletConfigFactory dashletCfgFactory) {
		this.dashletCfgFactory = dashletCfgFactory;
	}

	@Override
	@PlusTransactional
	public ResponseEvent<List<DashletConfigDetail>> getDashletConfigs() {
		try {
			List<DashletConfig> dashletCfgs = daoFactory.getDashletConfigDao().getDashletConfigs();
			return ResponseEvent.response(DashletConfigDetail.from(dashletCfgs));
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashletConfigDetail> getDashletConfig(RequestEvent<Long> req) {
		try {
			DashletConfig dashletCfg = daoFactory.getDashletConfigDao().getById(req.getPayload());
			if (dashletCfg == null) {
				return ResponseEvent.userError(DashletConfigErrorCode.NOT_FOUND);
			}

			return ResponseEvent.response(DashletConfigDetail.from(dashletCfg));
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashletConfigDetail> createDashletConfig(RequestEvent<DashletConfigDetail> req) {
		try {
			DashletConfigDetail detail = req.getPayload();
			DashletConfig dashletCfg = dashletCfgFactory.createDashletConfig(detail);

			OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);
			ensureUniqueName(null, dashletCfg, ose);
			ose.checkAndThrow();

			daoFactory.getDashletConfigDao().saveOrUpdate(dashletCfg);
			return ResponseEvent.response(DashletConfigDetail.from(dashletCfg));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashletConfigDetail> updateDashletConfig(RequestEvent<DashletConfigDetail> req) {
		try {
			DashletConfigDetail detail = req.getPayload();
			DashletConfig existing = daoFactory.getDashletConfigDao().getById(detail.getId());
			if (existing == null) {
				return ResponseEvent.userError(DashletConfigErrorCode.NOT_FOUND);
			}

			DashletConfig dashletCfg = dashletCfgFactory.createDashletConfig(existing, detail);

			OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);
			ensureUniqueName(existing, dashletCfg, ose);
			ose.checkAndThrow();

			existing.update(dashletCfg);
			return ResponseEvent.response(DashletConfigDetail.from(dashletCfg));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashletConfigDetail> deleteDashletConfig(RequestEvent<DashletConfigDetail> req) {
		// TODO Auto-generated method stub
		return null;
	}

	private void ensureUniqueName(DashletConfig existing, DashletConfig newDashletCfg, OpenSpecimenException ose) {
		if (existing != null && existing.getName().equals(newDashletCfg.getName())) {
			return;
		}

		DashletConfig dashletConfig = daoFactory.getDashletConfigDao().getDashletConfigByName(newDashletCfg.getName());
		if (dashletConfig != null) {
			ose.addError(DashletConfigErrorCode.DUP_NAME, newDashletCfg.getName());
		}
	}

}
