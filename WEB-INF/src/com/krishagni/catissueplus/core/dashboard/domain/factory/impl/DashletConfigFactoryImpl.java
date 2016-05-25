package com.krishagni.catissueplus.core.dashboard.domain.factory.impl;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.krishagni.catissueplus.core.common.errors.ActivityStatusErrorCode;
import com.krishagni.catissueplus.core.common.errors.ErrorType;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.util.Status;
import com.krishagni.catissueplus.core.common.util.Utility;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashletConfigErrorCode;
import com.krishagni.catissueplus.core.dashboard.domain.factory.DashletConfigFactory;
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;
import com.krishagni.catissueplus.core.dashboard.serivce.DataSourceFactory;
import com.krishagni.catissueplus.core.dashboard.serivce.DataSourceRegistrar;

public class DashletConfigFactoryImpl implements DashletConfigFactory {
	private DataSourceRegistrar dataSourceRegistrar;

	public void setDataSourceRegistrar(DataSourceRegistrar dataSourceRegistrar) {
		this.dataSourceRegistrar = dataSourceRegistrar;
	}

	@Override
	public DashletConfig createDashletConfig(DashletConfigDetail detail) {
		return createDashletConfig(null, detail);
	}

	@Override
	public DashletConfig createDashletConfig(DashletConfig existing, DashletConfigDetail detail) {
		DashletConfig dashletCfg = new DashletConfig();
		OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);

		if (existing != null) {
			dashletCfg.setId(existing.getId());
		}

		setName(detail, existing, dashletCfg, ose);
		setTitle(detail, existing, dashletCfg, ose);
		setDataSource(detail, existing, dashletCfg, ose);
		setChartOpts(detail, existing, dashletCfg, ose);
		setActivityStatus(detail, existing, dashletCfg, ose);

		ose.checkAndThrow();
		return dashletCfg;
	}

	private void setName(DashletConfigDetail detail, DashletConfig dashletCfg, OpenSpecimenException ose) {
		String name = detail.getName();
		if (StringUtils.isBlank(name)) {
			ose.addError(DashletConfigErrorCode.NAME_REQ);
			return;
		}

		dashletCfg.setName(name);
	}

	private void setName(DashletConfigDetail detail, DashletConfig existing, DashletConfig dashletCfg, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("name")) {
			setName(detail, dashletCfg, ose);
		} else {
			dashletCfg.setName(existing.getName());
		}
	}

	private void setTitle(DashletConfigDetail detail, DashletConfig dashletCfg, OpenSpecimenException ose) {
		String title = detail.getTitle();
		if (StringUtils.isBlank(title)) {
			ose.addError(DashletConfigErrorCode.TITLE_REQ);
			return;
		}

		dashletCfg.setTitle(title);
	}

	private void setTitle(DashletConfigDetail detail, DashletConfig existing, DashletConfig dashletCfg, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("title")) {
			setTitle(detail, dashletCfg, ose);
		} else {
			dashletCfg.setTitle(existing.getTitle());
		}
	}

	private void setDataSource(DashletConfigDetail detail, DashletConfig dashletCfg, OpenSpecimenException ose) {
		Map<String, Object> dataSource = detail.getDataSource();
		if (dataSource == null) {
			ose.addError(DashletConfigErrorCode.DS_REQ);
			return;
		}

		String type = (String) dataSource.get("type");
		if (StringUtils.isBlank(type)) {
			ose.addError(DashletConfigErrorCode.DS_TYPE_REQ);
			return;
		}

		DataSourceFactory factory = dataSourceRegistrar.getFactory(type);
		if (factory == null) {
			ose.addError(DashletConfigErrorCode.INVALID_DS_TYPE, type);
			return;
		}

		Map<String, Object> options = (Map<String, Object>) dataSource.get("options");
		if (options == null) {
			options = Collections.emptyMap();
		}

		factory.createDataSource(options);
		dashletCfg.setDataSource(Utility.mapToJson(dataSource));
	}

	private void setDataSource(DashletConfigDetail detail, DashletConfig existing, DashletConfig dashletCfg, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("dataSource")) {
			setDataSource(detail, dashletCfg, ose);
		} else {
			dashletCfg.setDataSource(existing.getDataSource());
		}
	}

	private void setChartOpts(DashletConfigDetail detail, DashletConfig dashletCfg, OpenSpecimenException ose) {
		Map<String, Object> chartOpts = detail.getChartOpts();
		if (chartOpts == null) {
			chartOpts = Collections.emptyMap();
		}

		dashletCfg.setChartOpts(Utility.mapToJson(chartOpts));
	}

	private void setChartOpts(DashletConfigDetail detail, DashletConfig existing, DashletConfig dashletCfg, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("chartOpts")) {
			setChartOpts(detail, dashletCfg, ose);
		} else {
			dashletCfg.setChartOpts(existing.getChartOpts());
		}
	}

	private void setActivityStatus(DashletConfigDetail detail, DashletConfig dashletCfg, OpenSpecimenException ose) {
		String status = detail.getActivityStatus();
		if (StringUtils.isBlank(status)) {
			dashletCfg.setActivityStatus(Status.ACTIVITY_STATUS_ACTIVE.getStatus());
		} else if (Status.isValidActivityStatus(status)) {
			dashletCfg.setActivityStatus(status);
		} else {
			ose.addError(ActivityStatusErrorCode.INVALID);
		}
	}

	private void setActivityStatus(DashletConfigDetail detail, DashletConfig existing, DashletConfig dashletCfg, OpenSpecimenException ose) {
		if (existing == null || detail.isAttrModified("activityStatus")) {
			setActivityStatus(detail, dashletCfg, ose);
		} else {
			dashletCfg.setActivityStatus(existing.getActivityStatus());
		}
	}
}
