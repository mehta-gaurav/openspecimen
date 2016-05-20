package com.krishagni.catissueplus.core.dashboard.repository.impl;

import java.util.List;

import com.krishagni.catissueplus.core.common.repository.AbstractDao;
import com.krishagni.catissueplus.core.dashboard.domain.DashletConfig;
import com.krishagni.catissueplus.core.dashboard.domain.ViewDashlets;
import com.krishagni.catissueplus.core.dashboard.repository.DashletDao;

public class DashletDaoImpl extends AbstractDao<DashletConfig> implements DashletDao {

	@Override
	public Class<?> getType() {
		return DashletConfig.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DashletConfig> getDashlets() {
		return sessionFactory.getCurrentSession()
				.getNamedQuery(GET_DASHLETS)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DashletConfig getDashletByName(String name) {
		List<DashletConfig> result = sessionFactory.getCurrentSession()
				.getNamedQuery(GET_DASHLET_BY_NAME)
				.setString("name", name)
				.list();
		
		return result.isEmpty() ? null : result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ViewDashlets getViewDashletsById(Long id) {
		List<ViewDashlets> result = sessionFactory.getCurrentSession()
				.getNamedQuery(GET_VIEW_DASHLETS_BY_ID)
				.setLong("id", id)
				.list();
		
		return result.isEmpty() ? null : result.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ViewDashlets getViewDashletsByName(String name) {
		List<ViewDashlets> result = sessionFactory.getCurrentSession()
				.getNamedQuery(GET_VIEW_DASHLETS_BY_NAME)
				.setString("name", name)
				.list();
		
		return result.isEmpty() ? null : result.get(0);
	}
	
	@Override
	public void saveViewDashlets(ViewDashlets view) {
		sessionFactory.getCurrentSession().saveOrUpdate(view);
	}
	
	private static final String FQN = DashletConfig.class.getName();

	private static final String GET_DASHLETS = FQN + ".getDashlets";
	
	private static final String GET_DASHLET_BY_NAME = FQN + ".getDashletByName";
	
	private static final String GET_VIEW_DASHLETS_BY_ID = ViewDashlets.class.getName() + ".getViewDashletsById";
	
	private static final String GET_VIEW_DASHLETS_BY_NAME = ViewDashlets.class.getName() + ".getViewDashletsByName";

}
