package com.krishagni.catissueplus.core.dashboard.serivce.impl;

import java.util.Map;

import com.krishagni.catissueplus.core.dashboard.events.DataDetail;
import com.krishagni.catissueplus.core.dashboard.serivce.DataSource;

public class AqlDataSource implements DataSource {

	private Map<String, String> category;

	private Map<String, String> series;

	private Map<String, String> metric;

	private String criteria;

	public Map<String, String> getCategory() {
		return category;
	}

	public void setCategory(Map<String, String> category) {
		this.category = category;
	}

	public Map<String, String> getSeries() {
		return series;
	}

	public void setSeries(Map<String, String> series) {
		this.series = series;
	}

	public Map<String, String> getMetric() {
		return metric;
	}

	public void setMetric(Map<String, String> metric) {
		this.metric = metric;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	@Override
	public DataDetail execute(Map<String, Object> input) {
		return null;
	}

}
