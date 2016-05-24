package com.krishagni.catissueplus.core.dashboard.events;

import java.util.List;
import java.util.Map;

public class DataDetail {
	private List<String> categories;

	private Map<String, List<Number>> seriesData;

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Map<String, List<Number>> getSeriesData() {
		return seriesData;
	}

	public void setSeriesData(Map<String, List<Number>> seriesData) {
		this.seriesData = seriesData;
	}

}
