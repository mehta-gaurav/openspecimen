package com.krishagni.catissueplus.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.events.DashboardDetail;
import com.krishagni.catissueplus.core.dashboard.serivce.DashboardService;

@Controller
@RequestMapping("/dashboards")
public class DashboardController {

	@Autowired
	private DashboardService dashboardSvc;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<DashboardDetail> getDashboards() {
		return response(dashboardSvc.getDashboards());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DashboardDetail getDashboard(@PathVariable("id") Long id) {
		return response(dashboardSvc.getDashboard(request(id)));
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DashboardDetail createDashboard(@RequestBody DashboardDetail detail) {
		return response(dashboardSvc.createDashboard(request(detail)));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DashboardDetail updateDashboard(
		@PathVariable("id")
		Long id,

		@RequestBody
		DashboardDetail detail) {

		detail.setId(id);
		return response(dashboardSvc.updateDashboard(request(detail)));
	}

	private <T> RequestEvent<T> request(T payload) {
		return new RequestEvent<T>(payload);
	}

	private <T> T response(ResponseEvent<T> resp) {
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}
}
