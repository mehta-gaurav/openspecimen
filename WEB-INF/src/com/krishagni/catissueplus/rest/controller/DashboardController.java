package com.krishagni.catissueplus.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.dashboard.events.ChartDetail;
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;
import com.krishagni.catissueplus.core.dashboard.events.ViewDashletsDetail;
import com.krishagni.catissueplus.core.dashboard.service.DashletService;

@Controller
@RequestMapping("/dashboards")
public class DashboardController {
	
	@Autowired
	private DashletService dashletSvc;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<DashletConfigDetail> getDashlets() {
		ResponseEvent<List<DashletConfigDetail>> resp = dashletSvc.getDashlets();
		resp.throwErrorIfUnsuccessful();
		
		return resp.getPayload();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DashletConfigDetail getDashlet(@PathVariable("id") Long id) {
		ResponseEvent<DashletConfigDetail> resp = dashletSvc.getDashlet(getRequest(id));
		resp.throwErrorIfUnsuccessful();

		return resp.getPayload();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DashletConfigDetail createDashlet(@RequestBody DashletConfigDetail detail) {
		ResponseEvent<DashletConfigDetail> resp = dashletSvc.createDashlet(getRequest(detail));
		resp.throwErrorIfUnsuccessful();
		
		return resp.getPayload();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DashletConfigDetail updateDashlet(@PathVariable Long id, @RequestBody DashletConfigDetail detail) {
		detail.setId(id);
		
		ResponseEvent<DashletConfigDetail> resp = dashletSvc.updateDashlet(getRequest(detail));
		resp.throwErrorIfUnsuccessful();
		
		return resp.getPayload();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{id}/chartDetail")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ChartDetail getChartDetail(@PathVariable("id") Long id) {
		ResponseEvent<ChartDetail> resp = dashletSvc.getChartDetail(getRequest(id));
		resp.throwErrorIfUnsuccessful();

		return resp.getPayload();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/view-dashlets/byname")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ViewDashletsDetail getViewDashlets(@RequestParam(value = "name", required = true) String name) {
		ResponseEvent<ViewDashletsDetail> resp = dashletSvc.getViewDashlets(getRequest(name));
		resp.throwErrorIfUnsuccessful();

		return resp.getPayload();
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/view-dashlets")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ViewDashletsDetail createViewDashlets(@RequestBody ViewDashletsDetail detail) {
		ResponseEvent<ViewDashletsDetail> resp = dashletSvc.createViewDashlets(getRequest(detail));
		resp.throwErrorIfUnsuccessful();
		
		return resp.getPayload();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/view-dashlets/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ViewDashletsDetail updateViewDashlets(@PathVariable Long id, @RequestBody ViewDashletsDetail detail) {
		detail.setId(id);
		
		ResponseEvent<ViewDashletsDetail> resp = dashletSvc.updateViewDashlets(getRequest(detail));
		resp.throwErrorIfUnsuccessful();
		
		return resp.getPayload();
	}
	
	private <T> RequestEvent<T> getRequest(T payload) {
		return new RequestEvent<T>(payload);
	}
}
