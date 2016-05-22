package com.krishagni.catissueplus.core.dashboard.domain;

import com.krishagni.catissueplus.core.biospecimen.domain.BaseEntity;

public class Dashlet extends BaseEntity {
	private Dashboard dashboard;

	private DashletConfig dashletConfig;

	private Integer height;

	private Integer width;

	private Integer row;

	private Integer column;

	public Dashboard getDashboard() {
		return dashboard;
	}

	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}

	public DashletConfig getDashletConfig() {
		return dashletConfig;
	}

	public void setDashletConfig(DashletConfig dashletConfig) {
		this.dashletConfig = dashletConfig;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public void update(Dashlet other) {
		setDashboard(other.getDashboard());
		setDashletConfig(other.getDashletConfig());
		setHeight(other.getHeight());
		setWidth(other.getWidth());
		setRow(other.getRow());
		setColumn(other.getColumn());
	}
}
