package com.krishagni.catissueplus.core.dashboard.events;

import java.util.List;

public class ChartDetail {
	private Object Data;
	
	private List<String> labels;
	
	private List<String> series;
	
	private String type;

	public Object getData() {
		return Data;
	}

	public void setData(Object data) {
		Data = data;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<String> getSeries() {
		return series;
	}

	public void setSeries(List<String> series) {
		this.series = series;
	}

	public String getType() {
		return type;
	}

	public void setType(String chartType) {
		this.type = chartType;
	}
	
}
