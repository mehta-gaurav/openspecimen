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
import com.krishagni.catissueplus.core.dashboard.events.DashletConfigDetail;
import com.krishagni.catissueplus.core.dashboard.serivce.DashletConfigService;

@Controller
@RequestMapping("/dashlet-configs")
public class DashletConfigController {

	@Autowired
	private DashletConfigService dashletCfgSvc;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<DashletConfigDetail> getDashletConfigs() {
		return response(dashletCfgSvc.getConfigs());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DashletConfigDetail getDashletConfig(@PathVariable("id") Long id) {
		return response(dashletCfgSvc.getConfig(request(id)));
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DashletConfigDetail createDashletConfig(@RequestBody DashletConfigDetail cfg) {
		return response(dashletCfgSvc.createConfig(request(cfg)));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DashletConfigDetail updateDashletConfig(
		@PathVariable("id")
		Long id,

		@RequestBody
		DashletConfigDetail cfg) {

		cfg.setId(id);
		return response(dashletCfgSvc.updateConfig(request(cfg)));
	}

	private <T> RequestEvent<T> request(T payload) {
		return new RequestEvent<T>(payload);
	}

	private <T> T response(ResponseEvent<T> resp) {
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}
}
