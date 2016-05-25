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
	public ResponseEvent<List<DashletConfigDetail>> getConfigs() {
		try {
			List<DashletConfig> cfgs = daoFactory.getDashletConfigDao().getDashletConfigs();
			return ResponseEvent.response(DashletConfigDetail.from(cfgs));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashletConfigDetail> getConfig(RequestEvent<Long> req) {
		//
		// TODO: We should be able to support getConfig by both ID and name
		//

		try {
			DashletConfig cfg = daoFactory.getDashletConfigDao().getById(req.getPayload());
			if (cfg == null) {
				return ResponseEvent.userError(DashletConfigErrorCode.NOT_FOUND, req.getPayload());
			}

			return ResponseEvent.response(DashletConfigDetail.from(cfg));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashletConfigDetail> createConfig(RequestEvent<DashletConfigDetail> req) {
		try {
			DashletConfig cfg = dashletCfgFactory.createDashletConfig(req.getPayload());

			OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);
			ensureUniqueName(null, cfg, ose);
			ose.checkAndThrow();

			daoFactory.getDashletConfigDao().saveOrUpdate(cfg);
			return ResponseEvent.response(DashletConfigDetail.from(cfg));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashletConfigDetail> updateConfig(RequestEvent<DashletConfigDetail> req) {
		try {
			DashletConfigDetail detail = req.getPayload();
			DashletConfig existing = daoFactory.getDashletConfigDao().getById(detail.getId());
			if (existing == null) {
				return ResponseEvent.userError(DashletConfigErrorCode.NOT_FOUND, detail.getId());
			}

			DashletConfig cfg = dashletCfgFactory.createDashletConfig(existing, detail);

			OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);
			ensureUniqueName(existing, cfg, ose);
			ose.checkAndThrow();

			existing.update(cfg);
			return ResponseEvent.response(DashletConfigDetail.from(cfg));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<DashletConfigDetail> deleteConfig(RequestEvent<DashletConfigDetail> req) {
		// TODO Auto-generated method stub
		return null;
	}

	private void ensureUniqueName(DashletConfig existing, DashletConfig newCfg, OpenSpecimenException ose) {
		if (existing != null && existing.getName().equals(newCfg.getName())) {
			return;
		}

		DashletConfig dbCfg = daoFactory.getDashletConfigDao().getByName(newCfg.getName());
		if (dbCfg != null) {
			ose.addError(DashletConfigErrorCode.DUP_NAME, newCfg.getName());
		}
	}

}
