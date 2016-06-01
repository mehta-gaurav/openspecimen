
package com.krishagni.catissueplus.core.administrative.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.krishagni.catissueplus.core.administrative.domain.Site;
import com.krishagni.catissueplus.core.administrative.domain.User;
import com.krishagni.catissueplus.core.common.AttributeModifiedSupport;
import com.krishagni.catissueplus.core.common.ListenAttributeChanges;

@ListenAttributeChanges
public class UserDetail extends AttributeModifiedSupport {

	private Long id;

	private String lastName;

	private String firstName;

	private String domainName;

	private String emailAddress;

	private String phoneNumber;

	private String loginName;

	private List<String> siteNames = new ArrayList<String>();

	private Date creationDate;

	private String activityStatus;

	private String instituteName;

	private String deptName;

	private boolean admin;

	private boolean manageForms;

	private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Boolean getManageForms() {
		return manageForms;
	}

	public void setManageForms(Boolean manageForms) {
		this.manageForms = manageForms;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getSiteNames() {
		return siteNames;
	}

	public void setSiteNames(List<String> sitesName) {
		this.siteNames = sitesName;
	}

	public static UserDetail from(User user) {
		UserDetail detail = new UserDetail();
		detail.setId(user.getId());
		detail.setLoginName(user.getLoginName());
		detail.setFirstName(user.getFirstName());
		detail.setLastName(user.getLastName());
		detail.setCreationDate(user.getCreationDate());
		detail.setActivityStatus(user.getActivityStatus());
		detail.setEmailAddress(user.getEmailAddress());
		detail.setPhoneNumber(user.getPhoneNumber());
		detail.setDomainName(user.getAuthDomain().getName());
		detail.setDeptName(user.getDepartment().getName());
		detail.setInstituteName(user.getInstitute().getName());
		detail.setAdmin(user.isAdmin());
		detail.setAddress(user.getAddress());
		detail.setManageForms(user.getManageForms());
		setUserSiteNames(detail, user.getSites());	

		return detail;
	}
	
	public static List<UserDetail> from(Collection<User> users) {
		List<UserDetail> result = new ArrayList<UserDetail>();
		for (User user: users) {
			result.add(from(user));
		}
		
		return result;
	}

	private static void setUserSiteNames(UserDetail detail, Set<Site> sites) {
		List<String> siteNames = new ArrayList<String>();
		for (Site site : sites) {
			siteNames.add(site.getName());
		}
		
		detail.setSiteNames(siteNames);
	}
}
