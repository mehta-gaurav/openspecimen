package com.krishagni.catissueplus.core.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.impl.XSStringImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.RequestMethod;

import com.krishagni.catissueplus.core.administrative.domain.User;
import com.krishagni.catissueplus.core.administrative.domain.factory.UserErrorCode;
import com.krishagni.catissueplus.core.auth.events.LoginDetail;
import com.krishagni.catissueplus.core.auth.services.impl.UserAuthenticationServiceImpl;
import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.PlusTransactional;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;

public class OsSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private static final String OS_AUTH_TOKEN = "osAuthToken";

	private DaoFactory daoFactory;
	
	private UserAuthenticationServiceImpl userAuthService;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void setUserAuthService(UserAuthenticationServiceImpl userAuthService) {
		this.userAuthService = userAuthService;
	}

	@Override
	@PlusTransactional
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String email = null;
		SAMLCredential credentials = (SAMLCredential) authentication.getCredentials();
		for (Attribute attr : credentials.getAttributes()) {
			if (attr.getName().contains("mail")) {
				for (XMLObject val : attr.getAttributeValues()) {
					email = ((XSStringImpl) val).getValue();
					break;
				}
			}
			
			if (email != null) {
				break;
			}
		}
		
		User user = daoFactory.getUserDao().getUserByEmailAddress(email);
		if (user == null) {
			throw OpenSpecimenException.userError(UserErrorCode.NOT_FOUND, email);
		}
		
		LoginDetail loginDetail = new LoginDetail();
		loginDetail.setIpAddress(request.getRemoteAddr());
		loginDetail.setApiUrl(request.getRequestURI());
		loginDetail.setRequestMethod(RequestMethod.POST.name());
		
		String encodedToken = userAuthService.generateToken(user, loginDetail);
		Cookie cookie = new Cookie(OS_AUTH_TOKEN, encodedToken);
		cookie.setMaxAge(-1);
		cookie.setPath("/" + request.getRequestURI().split("/")[0]);
		response.addCookie(cookie);
		
		getRedirectStrategy().sendRedirect(request, response, "/#/home");
	}

}
