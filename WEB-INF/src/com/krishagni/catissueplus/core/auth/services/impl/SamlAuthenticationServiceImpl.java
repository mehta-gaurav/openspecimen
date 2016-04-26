package com.krishagni.catissueplus.core.auth.services.impl;

import java.util.Map;

import com.krishagni.catissueplus.core.auth.SAMLBootstrap;
import com.krishagni.catissueplus.core.auth.services.AuthenticationService;

public class SamlAuthenticationServiceImpl implements AuthenticationService {

	public SamlAuthenticationServiceImpl() {
		
	}
	
	public SamlAuthenticationServiceImpl(Map<String, String> props) {
		SAMLBootstrap samlBootStrap = new SAMLBootstrap(props);
		samlBootStrap.initialize();
	}
	
	@Override
	public void authenticate(String username, String password) {
		// TODO Auto-generated method stub
	}

}
