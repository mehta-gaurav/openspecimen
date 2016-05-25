package com.krishagni.catissueplus.core.dashboard.repository.impl;

import java.util.List;

import com.krishagni.catissueplus.core.common.repository.AbstractDao;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.repository.DashletConfigDao;

public class DashletConfigDaoImpl extends AbstractDao<DashletConfig> implements DashletConfigDao {

	@Override
	public Class<DashletConfig> getType() {
		return DashletConfig.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DashletConfig> getDashletConfigs() {
		return getSessionFactory().getCurrentSession()
			.createCriteria(DashletConfig.class, "cfg")
			.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public DashletConfig getByName(String name) {
		List<DashletConfig> result = getSessionFactory().getCurrentSession()
			.getNamedQuery(GET_BY_NAME)
			.setString("name", name)
			.list();

		return result.isEmpty() ? null : result.iterator().next();
	}

	private static final String FQN = DashletConfig.class.getName();

	private static final String GET_BY_NAME = FQN + ".getByName";
}
