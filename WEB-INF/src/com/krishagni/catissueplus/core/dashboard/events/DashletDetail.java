package com.krishagni.catissueplus.core.dashboard.events;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.krishagni.catissueplus.core.dashboard.domain.Dashlet;

public class DashletDetail {
	private Long id;

	private String name;

	private Integer height;

	private Integer width;

	private Integer row;

	private Integer column;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public static DashletDetail from(Dashlet dashlet) {
		DashletDetail detail = new DashletDetail();
		detail.setId(dashlet.getId());
		detail.setName(dashlet.getDashletConfig().getName());
		detail.setHeight(dashlet.getHeight());
		detail.setWidth(dashlet.getWidth());
		detail.setRow(dashlet.getRow());
		detail.setColumn(dashlet.getColumn());

		return detail;
	}

	public static List<DashletDetail> from(Collection<Dashlet> dashlets) {
		return dashlets.stream().map(DashletDetail::from).collect(Collectors.toList());
	}
}
