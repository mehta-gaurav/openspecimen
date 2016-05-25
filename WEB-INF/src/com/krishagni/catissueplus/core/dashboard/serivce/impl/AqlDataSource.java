package com.krishagni.catissueplus.core.dashboard.serivce.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.krishagni.catissueplus.core.dashboard.events.DataDetail;
import com.krishagni.catissueplus.core.dashboard.serivce.DataSource;

public class AqlDataSource implements DataSource {
	public static class ExprTitle {
		private String expr;

		private String title;

		public String getExpr() {
			return expr;
		}

		public void setExpr(String expr) {
			this.expr = expr;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public boolean isEmpty() {
			return StringUtils.isBlank(expr) && StringUtils.isBlank(title);
		}
	}

	private ExprTitle category;

	private ExprTitle series;

	private ExprTitle metric;

	private String criteria;

	public ExprTitle getCategory() {
		return category;
	}

	public void setCategory(ExprTitle category) {
		this.category = category;
	}

	public ExprTitle getSeries() {
		return series;
	}

	public void setSeries(ExprTitle series) {
		this.series = series;
	}

	public ExprTitle getMetric() {
		return metric;
	}

	public void setMetric(ExprTitle metric) {
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
