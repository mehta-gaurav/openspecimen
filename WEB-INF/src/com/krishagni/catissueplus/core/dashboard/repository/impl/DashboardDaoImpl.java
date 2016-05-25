package com.krishagni.catissueplus.core.dashboard.repository.impl;

import java.util.List;

import org.hibernate.Criteria;

import com.krishagni.catissueplus.core.common.repository.AbstractDao;
import com.krishagni.catissueplus.core.dashboard.domain.Dashboard;
import com.krishagni.catissueplus.core.dashboard.repository.DashboardDao;

public class DashboardDaoImpl extends AbstractDao<Dashboard> implements DashboardDao {

	@Override
	public Class<Dashboard> getType() {
		return Dashboard.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dashboard> getDashboards() {
		Criteria query = getSessionFactory().getCurrentSession()
			.createCriteria(Dashboard.class, "dashboard");

		return query.list();
	}
}
