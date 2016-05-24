package com.krishagni.catissueplus.core.dashboard.repository.impl;

import java.util.List;

import org.hibernate.Criteria;

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
		Criteria query = getSessionFactory().getCurrentSession().createCriteria(DashletConfig.class, "dashletCfg");

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public DashletConfig getDashletConfigByName(String name) {
		List<DashletConfig> result = getSessionFactory().getCurrentSession().getNamedQuery(GET_DASHLET_CFG_BY_NAME)
				.setString("name", name).list();

		return result.isEmpty() ? null : result.iterator().next();
	}

	private static final String FQN = DashletConfig.class.getName();

	private static final String GET_DASHLET_CFG_BY_NAME = FQN + ".getDashletCfgByName";
}
