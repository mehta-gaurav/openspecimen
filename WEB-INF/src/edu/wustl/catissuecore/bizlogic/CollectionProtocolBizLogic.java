/**
 * <p>Title: CollectionProtocolBizLogic Class>
 * <p>Description:	CollectionProtocolBizLogic is used to add CollectionProtocol information into the database using Hibernate.</p>
 * Copyright:    Copyright (c) year
 * Company: Washington University, School of Medicine, St. Louis.
 * @author Mandar Deshmukh
 * @version 1.00
 * Created on Aug 09, 2005
 */

package edu.wustl.catissuecore.bizlogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import net.sf.hibernate.HibernateException;
import edu.wustl.catissuecore.domain.CollectionProtocol;
import edu.wustl.catissuecore.domain.CollectionProtocolEvent;
import edu.wustl.catissuecore.domain.SpecimenRequirement;
import edu.wustl.catissuecore.domain.User;
import edu.wustl.catissuecore.util.Roles;
import edu.wustl.catissuecore.util.global.Constants;
import edu.wustl.catissuecore.util.global.Utility;
import edu.wustl.common.beans.SecurityDataBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.cde.CDEManager;
import edu.wustl.common.dao.DAO;
import edu.wustl.common.domain.AbstractDomainObject;
import edu.wustl.common.security.SecurityManager;
import edu.wustl.common.security.exceptions.SMException;
import edu.wustl.common.security.exceptions.UserNotAuthorizedException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.global.Validator;
import edu.wustl.common.util.logger.Logger;

/**
 * CollectionProtocolBizLogic is used to add CollectionProtocol information into the database using Hibernate.
 * @author Mandar Deshmukh
 */
public class CollectionProtocolBizLogic extends SpecimenProtocolBizLogic implements Roles
{

	/**
	 * Saves the CollectionProtocol object in the database.
	 * @param obj The CollectionProtocol object to be saved.
	 * @param session The session in which the object is saved.
	 * @throws DAOException 
	 * @throws HibernateException Exception thrown during hibernate operations.
	 */
	protected void insert(Object obj, DAO dao, SessionDataBean sessionDataBean)
			throws DAOException, UserNotAuthorizedException
	{
		CollectionProtocol collectionProtocol = (CollectionProtocol) obj;

		checkStatus(dao, collectionProtocol.getPrincipalInvestigator(), "Principal Investigator");

		setPrincipalInvestigator(dao, collectionProtocol);
		setCoordinatorCollection(dao, collectionProtocol);

		dao.insert(collectionProtocol, sessionDataBean, true, true);

		Iterator it = collectionProtocol.getCollectionProtocolEventCollection().iterator();
		while (it.hasNext())
		{
			CollectionProtocolEvent collectionProtocolEvent = (CollectionProtocolEvent) it.next();
			collectionProtocolEvent.setCollectionProtocol(collectionProtocol);
			dao.insert(collectionProtocolEvent, sessionDataBean, true, true);

			Iterator srIt = collectionProtocolEvent.getSpecimenRequirementCollection().iterator();
			while (srIt.hasNext())
			{
				SpecimenRequirement specimenRequirement = (SpecimenRequirement) srIt.next();
				specimenRequirement.getCollectionProtocolEventCollection().add(
						collectionProtocolEvent);
				dao.insert(specimenRequirement, sessionDataBean, true, true);
			}
		}

		HashSet protectionObjects = new HashSet();
		protectionObjects.add(collectionProtocol);

		try
		{
			SecurityManager.getInstance(this.getClass()).insertAuthorizationData(
					getAuthorizationData(collectionProtocol), protectionObjects,
					getDynamicGroups(collectionProtocol));
		}
		catch (SMException e)
		{
			throw handleSMException(e);
		}
	}

	/**
	 * Updates the persistent object in the database.
	 * @param obj The object to be updated.
	 * @param session The session in which the object is saved.
	 * @throws DAOException 
	 */
	protected void update(DAO dao, Object obj, Object oldObj, SessionDataBean sessionDataBean)
			throws DAOException, UserNotAuthorizedException
	{
		CollectionProtocol collectionProtocol = (CollectionProtocol) obj;
		CollectionProtocol collectionProtocolOld = (CollectionProtocol) oldObj;
		Logger.out.debug("PI OB*****************8" + collectionProtocol.getPrincipalInvestigator());
		Logger.out.debug("PI Identifier................."
				+ collectionProtocol.getPrincipalInvestigator().getId());
		Logger.out.debug("Email Address*****************8"
				+ collectionProtocol.getPrincipalInvestigator().getEmailAddress());
		Logger.out.debug("Principal Investigator*****************8"
				+ collectionProtocol.getPrincipalInvestigator().getCsmUserId());
		if (!collectionProtocol.getPrincipalInvestigator().getId().equals(
				collectionProtocolOld.getPrincipalInvestigator().getId()))
			checkStatus(dao, collectionProtocol.getPrincipalInvestigator(),
					"Principal Investigator");

		Iterator it = collectionProtocol.getUserCollection().iterator();
		while (it.hasNext())
		{
			User coordinator = (User) it.next();
			if (!coordinator.getId().equals(collectionProtocol.getPrincipalInvestigator().getId()))
			{
				if (!hasCoordinator(coordinator, collectionProtocolOld))
					checkStatus(dao, coordinator, "Coordinator");
			}
			else
				it.remove();
		}

		checkForChangedStatus(collectionProtocol, collectionProtocolOld);
		dao.update(collectionProtocol, sessionDataBean, true, true, false);

		//Audit of Collection Protocol.
		dao.audit(obj, oldObj, sessionDataBean, true);

		Collection oldCollectionProtocolEventCollection = collectionProtocolOld
				.getCollectionProtocolEventCollection();
		Logger.out
				.debug("collectionProtocol.getCollectionProtocolEventCollection Size................ : "
						+ collectionProtocol.getCollectionProtocolEventCollection().size());

		it = collectionProtocol.getCollectionProtocolEventCollection().iterator();
		while (it.hasNext())
		{
			CollectionProtocolEvent collectionProtocolEvent = (CollectionProtocolEvent) it.next();
			Logger.out.debug("CollectionProtocolEvent Id ............... : "
					+ collectionProtocolEvent.getId());
			collectionProtocolEvent.setCollectionProtocol(collectionProtocol);
			dao.update(collectionProtocolEvent, sessionDataBean, true, true, false);

			//Audit of collectionProtocolEvent
			CollectionProtocolEvent oldCollectionProtocolEvent = (CollectionProtocolEvent) getCorrespondingOldObject(
					oldCollectionProtocolEventCollection, collectionProtocolEvent.getId());
			dao.audit(collectionProtocolEvent, oldCollectionProtocolEvent, sessionDataBean, true);

			Iterator srIt = collectionProtocolEvent.getSpecimenRequirementCollection().iterator();
			while (srIt.hasNext())
			{
				SpecimenRequirement specimenRequirement = (SpecimenRequirement) srIt.next();

				Logger.out.debug("specimenRequirement " + specimenRequirement);

				specimenRequirement.getCollectionProtocolEventCollection().add(
						collectionProtocolEvent);
				dao.update(specimenRequirement, sessionDataBean, true, true, false);
			}
		}

		//Disable related Objects
		Logger.out.debug("collectionProtocol.getActivityStatus() "
				+ collectionProtocol.getActivityStatus());
		if (collectionProtocol.getActivityStatus().equals(Constants.ACTIVITY_STATUS_DISABLED))
		{
			Logger.out.debug("collectionProtocol.getActivityStatus() "
					+ collectionProtocol.getActivityStatus());
			Long collectionProtocolIDArr[] = {collectionProtocol.getId()};

			CollectionProtocolRegistrationBizLogic bizLogic = (CollectionProtocolRegistrationBizLogic) BizLogicFactory
					.getInstance().getBizLogic(Constants.COLLECTION_PROTOCOL_REGISTRATION_FORM_ID);
			bizLogic.disableRelatedObjectsForCollectionProtocol(dao, collectionProtocolIDArr);
		}

		try
		{
			updatePIAndCoordinatorGroup(dao, collectionProtocolOld, true);

			Long csmUserId = getCSMUserId(dao, collectionProtocol.getPrincipalInvestigator());
			if (csmUserId != null)
			{
				collectionProtocol.getPrincipalInvestigator().setCsmUserId(csmUserId);
				Logger.out.debug("PI ....."
						+ collectionProtocol.getPrincipalInvestigator().getCsmUserId());
				updatePIAndCoordinatorGroup(dao, collectionProtocol, false);
			}
		}
		catch (SMException smExp)
		{
			throw handleSMException(smExp);
		}
	}

	private void updatePIAndCoordinatorGroup(DAO dao, CollectionProtocol collectionProtocol,
			boolean operation) throws SMException, DAOException
	{
		Long principalInvestigatorId = collectionProtocol.getPrincipalInvestigator().getCsmUserId();
		Logger.out.debug("principalInvestigatorId.........................."
				+ principalInvestigatorId);
		String userGroupName = Constants.getCollectionProtocolPIGroupName(collectionProtocol
				.getId());
		Logger.out.debug("userGroupName.........................." + userGroupName);
		if (operation)
		{
			SecurityManager.getInstance(CollectionProtocolBizLogic.class).removeUserFromGroup(
					userGroupName, principalInvestigatorId.toString());
		}
		else
		{
			SecurityManager.getInstance(CollectionProtocolBizLogic.class).assignUserToGroup(
					userGroupName, principalInvestigatorId.toString());
		}

		userGroupName = Constants.getCollectionProtocolCoordinatorGroupName(collectionProtocol
				.getId());

		UserBizLogic userBizLogic = (UserBizLogic) BizLogicFactory.getInstance().getBizLogic(
				Constants.USER_FORM_ID);
		Iterator iterator = collectionProtocol.getUserCollection().iterator();
		while (iterator.hasNext())
		{
			User user = (User) iterator.next();
			if (operation)
			{
				SecurityManager.getInstance(CollectionProtocolBizLogic.class).removeUserFromGroup(
						userGroupName, user.getCsmUserId().toString());
			}
			else
			{
				Long csmUserId = getCSMUserId(dao, user);
				if (csmUserId != null)
				{
					Logger.out.debug("Co-ord ....." + csmUserId);
					SecurityManager.getInstance(CollectionProtocolBizLogic.class)
							.assignUserToGroup(userGroupName, csmUserId.toString());
				}
			}
		}
	}

	/**
	 * @param collectionProtocol
	 * @return
	 * @throws DAOException
	 */
	private Long getCSMUserId(DAO dao, User user) throws DAOException
	{
		String[] selectColumnNames = {Constants.CSM_USER_ID};
		String[] whereColumnNames = {Constants.SYSTEM_IDENTIFIER};
		String[] whereColumnCondition = {"="};
		String[] whereColumnValues = {user.getId().toString()};
		List csmUserIdList = dao.retrieve(User.class.getName(), selectColumnNames,
				whereColumnNames, whereColumnCondition, whereColumnValues,
				Constants.AND_JOIN_CONDITION);
		Logger.out.debug("csmUserIdList##########################Size........."
				+ csmUserIdList.size());

		if (csmUserIdList.isEmpty() == false)
		{
			Long csmUserId = (Long) csmUserIdList.get(0);

			return csmUserId;
		}

		return null;
	}

	/**
	 * This method returns collection of UserGroupRoleProtectionGroup objects that speciefies the 
	 * user group protection group linkage through a role. It also specifies the groups the protection  
	 * elements returned by this class should be added to.
	 * @return
	 */
	private Vector getAuthorizationData(AbstractDomainObject obj) throws SMException
	{
		Logger.out.debug("--------------- In here ---------------");

		Vector authorizationData = new Vector();
		Set group = new HashSet();

		CollectionProtocol collectionProtocol = (CollectionProtocol) obj;

		String userId = String
				.valueOf(collectionProtocol.getPrincipalInvestigator().getCsmUserId());
		Logger.out.debug(" PI ID: " + userId);
		gov.nih.nci.security.authorization.domainobjects.User user = SecurityManager.getInstance(
				this.getClass()).getUserById(userId);
		Logger.out.debug(" PI: " + user.getLoginName());
		group.add(user);

		// Protection group of PI
		String protectionGroupName = new String(Constants
				.getCollectionProtocolPGName(collectionProtocol.getId()));
		SecurityDataBean userGroupRoleProtectionGroupBean = new SecurityDataBean();
		userGroupRoleProtectionGroupBean.setUser(userId);
		userGroupRoleProtectionGroupBean.setRoleName(PI);
		userGroupRoleProtectionGroupBean.setGroupName(Constants
				.getCollectionProtocolPIGroupName(collectionProtocol.getId()));
		userGroupRoleProtectionGroupBean.setProtectionGroupName(protectionGroupName);
		userGroupRoleProtectionGroupBean.setGroup(group);
		authorizationData.add(userGroupRoleProtectionGroupBean);

		// Protection group of coordinators
		Collection coordinators = collectionProtocol.getUserCollection();
		group = new HashSet();
		for (Iterator it = coordinators.iterator(); it.hasNext();)
		{
			User aUser = (User) it.next();
			userId = String.valueOf(aUser.getCsmUserId());
			Logger.out.debug(" COORDINATOR ID: " + userId);
			user = SecurityManager.getInstance(this.getClass()).getUserById(userId);
			Logger.out.debug(" COORDINATOR: " + user.getLoginName());
			group.add(user);
		}

		protectionGroupName = new String(Constants.getCollectionProtocolPGName(collectionProtocol
				.getId()));
		userGroupRoleProtectionGroupBean = new SecurityDataBean();
		userGroupRoleProtectionGroupBean.setUser(userId);
		userGroupRoleProtectionGroupBean.setRoleName(READ_ONLY);
		userGroupRoleProtectionGroupBean.setGroupName(Constants
				.getCollectionProtocolCoordinatorGroupName(collectionProtocol.getId()));
		userGroupRoleProtectionGroupBean.setProtectionGroupName(protectionGroupName);
		userGroupRoleProtectionGroupBean.setGroup(group);
		authorizationData.add(userGroupRoleProtectionGroupBean);

		Logger.out.debug(authorizationData.toString());
		return authorizationData;
	}

	//    public Set getProtectionObjects(AbstractDomainObject obj)
	//    {
	//        Set protectionObjects = new HashSet();
	//        CollectionProtocolEvent collectionProtocolEvent;
	//        SpecimenRequirement specimenRequirement;
	//        
	//        CollectionProtocol collectionProtocol = (CollectionProtocol)obj;
	//        protectionObjects.add(collectionProtocol);
	//        Collection collectionProtocolEventCollection = collectionProtocol.getCollectionProtocolEventCollection();
	//        if(collectionProtocolEventCollection != null)
	//        {
	//           for(Iterator it = collectionProtocolEventCollection.iterator(); it.hasNext();)
	//           {
	//               collectionProtocolEvent = (CollectionProtocolEvent) it.next();
	//               if(collectionProtocolEvent !=null)
	//               {
	//                   protectionObjects.add(collectionProtocolEvent);
	//                   for(Iterator it2=collectionProtocolEvent.getSpecimenRequirementCollection().iterator();it2.hasNext();)
	//                   {
	//                       protectionObjects.add(it2.next());
	//                   }
	//               }
	//               
	//           }
	//        }
	//        Logger.out.debug(protectionObjects.toString());
	//        return protectionObjects;
	//    }

	private String[] getDynamicGroups(AbstractDomainObject obj)
	{
		String[] dynamicGroups = null;
		return dynamicGroups;
	}

	//This method sets the Principal Investigator.
	private void setPrincipalInvestigator(DAO dao, CollectionProtocol collectionProtocol)
			throws DAOException
	{
		//List list = dao.retrieve(User.class.getName(), "id", collectionProtocol.getPrincipalInvestigator().getId());
		//if (list.size() != 0)
		Object obj = dao.retrieve(User.class.getName(), collectionProtocol
				.getPrincipalInvestigator().getId());
		if (obj != null)
		{
			User pi = (User) obj;//list.get(0);
			collectionProtocol.setPrincipalInvestigator(pi);
		}
	}

	//This method sets the User Collection.
	private void setCoordinatorCollection(DAO dao, CollectionProtocol collectionProtocol)
			throws DAOException
	{
		Long piID = collectionProtocol.getPrincipalInvestigator().getId();
		Logger.out.debug("Coordinator Size " + collectionProtocol.getUserCollection().size());
		Collection coordinatorColl = new HashSet();

		Iterator it = collectionProtocol.getUserCollection().iterator();
		while (it.hasNext())
		{
			User aUser = (User) it.next();
			if (!aUser.getId().equals(piID))
			{
				Logger.out.debug("Coordinator ID :" + aUser.getId());
				Object obj = dao.retrieve(User.class.getName(), aUser.getId());
				if (obj != null)
				{
					User coordinator = (User) obj;//list.get(0);

					checkStatus(dao, coordinator, "coordinator");

					coordinatorColl.add(coordinator);
					coordinator.getCollectionProtocolCollection().add(collectionProtocol);
				}
			}
		}
		collectionProtocol.setUserCollection(coordinatorColl);
	}

	public void setPrivilege(DAO dao, String privilegeName, Class objectType, Long[] objectIds,
			Long userId, String roleId, boolean assignToUser, boolean assignOperation)
			throws SMException, DAOException
	{
		super.setPrivilege(dao, privilegeName, objectType, objectIds, userId, roleId, assignToUser,
				assignOperation);

		//		CollectionProtocolRegistrationBizLogic bizLogic = (CollectionProtocolRegistrationBizLogic)BizLogicFactory.getBizLogic(Constants.COLLECTION_PROTOCOL_REGISTRATION_FORM_ID);
		//		bizLogic.assignPrivilegeToRelatedObjectsForCP(dao,privilegeName,objectIds,userId, roleId, assignToUser);
	}

	private boolean hasCoordinator(User coordinator, CollectionProtocol collectionProtocol)
	{
		Iterator it = collectionProtocol.getUserCollection().iterator();
		while (it.hasNext())
		{
			User coordinatorOld = (User) it.next();
			if (coordinator.getId().equals(coordinatorOld.getId()))
			{
				return true;
			}
		}
		return false;
	}

	//Added by Ashish
/*	Map values = null;
	Map innerLoopValues = null;
	int outerCounter = 1;
	long protocolCoordinatorIds[];
	boolean aliqoutInSameContainer = false;

	public void setAllValues(Object obj)
	{

		CollectionProtocol cProtocol = (CollectionProtocol) obj;
		Collection protocolEventCollection = cProtocol.getCollectionProtocolEventCollection();

		if (protocolEventCollection != null)
		{
			List eventList = new ArrayList(protocolEventCollection);
			Collections.sort(eventList);
			protocolEventCollection = eventList;

			values = new HashMap();
			innerLoopValues = new HashMap();

			int i = 1;
			Iterator it = protocolEventCollection.iterator();
			while (it.hasNext())
			{
				CollectionProtocolEvent cpEvent = (CollectionProtocolEvent) it.next();

				String keyClinicalStatus = "CollectionProtocolEvent:" + i + "_clinicalStatus";
				String keyStudyCalendarEventPoint = "CollectionProtocolEvent:" + i
						+ "_studyCalendarEventPoint";
				String keyCPEId = "CollectionProtocolEvent:" + i + "_id";

				values.put(keyClinicalStatus, Utility.toString(cpEvent.getClinicalStatus()));
				values.put(keyStudyCalendarEventPoint, Utility.toString(cpEvent
						.getStudyCalendarEventPoint()));
				values.put(keyCPEId, Utility.toString(cpEvent.getId()));
				Logger.out.debug("In Form keyCPEId..............." + values.get(keyCPEId));
				Collection specimenRequirementCollection = cpEvent
						.getSpecimenRequirementCollection();

				populateSpecimenRequirement(specimenRequirementCollection, i);

				i++;
			}

			outerCounter = protocolEventCollection.size();
		}

		//At least one outer row should be displayed in ADD MORE therefore
		if (outerCounter == 0)
			outerCounter = 1;

		//Populating the user-id array
		Collection userCollection = cProtocol.getUserCollection();

		if (userCollection != null)
		{
			protocolCoordinatorIds = new long[userCollection.size()];
			int i = 0;

			Iterator it = userCollection.iterator();
			while (it.hasNext())
			{
				User user = (User) it.next();
				protocolCoordinatorIds[i] = user.getId().longValue();
				i++;
			}
		}
		if (cProtocol.getAliqoutInSameContainer() != null)
		{
			aliqoutInSameContainer = cProtocol.getAliqoutInSameContainer().booleanValue();
		}
	}

	private void populateSpecimenRequirement(Collection specimenRequirementCollection, int counter)
	{
		int innerCounter = 0;
		if (specimenRequirementCollection != null)
		{
			int i = 1;

			Iterator iterator = specimenRequirementCollection.iterator();
			while (iterator.hasNext())
			{
				SpecimenRequirement specimenRequirement = (SpecimenRequirement) iterator.next();
				String key[] = {
						"CollectionProtocolEvent:" + counter + "_SpecimenRequirement:" + i
								+ "_specimenClass",
						"CollectionProtocolEvent:" + counter + "_SpecimenRequirement:" + i
								+ "_unitspan",
						"CollectionProtocolEvent:" + counter + "_SpecimenRequirement:" + i
								+ "_specimenType",
						"CollectionProtocolEvent:" + counter + "_SpecimenRequirement:" + i
								+ "_tissueSite",
						"CollectionProtocolEvent:" + counter + "_SpecimenRequirement:" + i
								+ "_pathologyStatus",
						"CollectionProtocolEvent:" + counter + "_SpecimenRequirement:" + i
								+ "_quantity_value",
						"CollectionProtocolEvent:" + counter + "_SpecimenRequirement:" + i + "_id"};
				this.values = setSpecimenRequirement(key, specimenRequirement);
				i++;
			}
			innerCounter = specimenRequirementCollection.size();
		}

		//At least one inner row should be displayed in ADD MORE therefore
		if (innerCounter == 0)
			innerCounter = 1;

		String innerCounterKey = String.valueOf(counter);
		innerLoopValues.put(innerCounterKey, String.valueOf(innerCounter));
	}

	//END
	/**
	 * Overriding the parent class's method to validate the enumerated attribute values
	 */
	protected boolean validate(Object obj, DAO dao, String operation) throws DAOException
	{
		//Added by Ashish
		//setAllValues(obj);
		//END

		CollectionProtocol protocol = (CollectionProtocol) obj;
		Collection eventCollection = protocol.getCollectionProtocolEventCollection();
		//Added by Ashish
		
		/*Validator validator = new Validator();
		if (protocol == null)
			throw new DAOException("domain.object.null.err.msg",
					new String[]{"Collection Protocol"});*/
		//END
		//Added by Ashish
/*
		if (values.isEmpty())
		{
			String message = ApplicationProperties.getValue("collectionprotocol.eventtitle");
			throw new DAOException("errors.one.item.required", new String[]{message});

		}
		// check for atleast 1 specimen requirement per CollectionProtocol Event
		for (int i = 1; i <= outerCounter; i++)
		{
			String className = "CollectionProtocolEvent:" + i
					+ "_SpecimenRequirement:1_specimenClass";
			Object object = getValue(className);
			if (object == null)
			{

				String message = ApplicationProperties.getValue("collectionprotocol.specimenreq");
				throw new DAOException("errors.one.item.required", new String[]{message});
			}
		}
		// ---------END-----------------------------------------

		// Error messages should be in the same sequence as the sequence of fields on the page.

		//Check for PI can not be coordinator of the protocol.
		if (this.protocolCoordinatorIds != null
				&& protocol.getPrincipalInvestigator().getId() != -1)
		{
			for (int ind = 0; ind < protocolCoordinatorIds.length; ind++)
			{
				if (protocolCoordinatorIds[ind] == protocol.getPrincipalInvestigator().getId())
				{

					throw new DAOException("errors.pi.coordinator.same");

				}
			}
		}

		Logger.out.debug("Protocol Coordinators : " + protocolCoordinatorIds);

		boolean bClinicalStatus = false;
		boolean bStudyPoint = false;
		boolean bSpecimenClass = false;
		boolean bSpecimenType = false;
		boolean bTissueSite = false;
		boolean bPathologyStatus = false;

		Iterator it = this.values.keySet().iterator();
		while (it.hasNext())
		{
			String key = (String) it.next();
			String value = (String) values.get(key);

			if (!bClinicalStatus)
			{
				if (key.indexOf("clinicalStatus") != -1 && !validator.isValidOption(value))
				{
					bClinicalStatus = true;
					String message = ApplicationProperties
							.getValue("collectionprotocol.clinicalstatus");
					throw new DAOException("errors.item.selected", new String[]{message});

				}
			}
			if (!bStudyPoint)
			{
				if (key.indexOf("studyCalendarEventPoint") != -1)
				{
					//As study Calendar Event Point can be an empty value
					if (validator.isEmpty(value))
					{
						bStudyPoint = true;
						String message = ApplicationProperties
								.getValue("collectionprotocol.studycalendartitle");
						throw new DAOException("errors.item.required", new String[]{message});

					}
					else
					{
						//Allow study Calendar Event Point as -ve value
						if (!validator.isDouble(value, false))
						{
							bStudyPoint = true;
							String message = ApplicationProperties
									.getValue("collectionprotocol.studycalendartitle");
							throw new DAOException("errors.studycalendarpoint",
									new String[]{message});

						}
					}
				}
			}

			if (!bSpecimenClass)
			{
				if (key.indexOf("specimenClass") != -1 && !validator.isValidOption(value))
				{
					bSpecimenClass = true;
					String message = ApplicationProperties
							.getValue("collectionprotocol.specimenclass");
					throw new DAOException("errors.item.selected", new String[]{message});

				}
			}

			if (!bSpecimenType)
			{
				if (key.indexOf("specimenType") != -1 && !validator.isValidOption(value))
				{
					bSpecimenType = true;
					String message = ApplicationProperties
							.getValue("collectionprotocol.specimetype");
					throw new DAOException("errors.item.selected", new String[]{message});

				}
			}

			if (!bTissueSite)
			{
				if (key.indexOf("tissueSite") != -1 && !validator.isValidOption(value))
				{
					bTissueSite = true;
					String message = ApplicationProperties
							.getValue("collectionprotocol.specimensite");
					throw new DAOException("errors.item.selected", new String[]{message});

				}
			}

			if (!bPathologyStatus)
			{
				if (key.indexOf("pathologyStatus") != -1 && !validator.isValidOption(value))
				{
					bPathologyStatus = true;
					String message = ApplicationProperties
							.getValue("collectionprotocol.specimenstatus");
					throw new DAOException("errors.item.selected", new String[]{message});

				}
			}

			if (key.indexOf("quantityIn") != -1)
			{
				String classKey = key.substring(0, key.lastIndexOf("_"));
				classKey = classKey + "_specimenClass";
				String classValue = (String) getValue(classKey);
				if (classValue.trim().equals("Cell"))
				{
					if (validator.isEmpty(value))
					{
						String message = ApplicationProperties
								.getValue("collectionprotocol.quantity");
						throw new DAOException("errors.item.required", new String[]{message});

					}
					else if (!validator.isNumeric(value))
					{
						String message = ApplicationProperties
								.getValue("collectionprotocol.quantity");
						throw new DAOException("errors.item.format", new String[]{message});

					}
				}
				else
				{
					// -------Mandar: 19-12-2005
					String typeKey = key.substring(0, key.lastIndexOf("_"));
					typeKey = typeKey + "_specimenType";
					String typeValue = (String) getValue(typeKey);
					Logger.out.debug("TypeKey : " + typeKey + " : Type Value : " + typeValue);
					if (typeValue.trim().equals(Constants.FROZEN_TISSUE_SLIDE)
							|| typeValue.trim().equals(Constants.FIXED_TISSUE_BLOCK)
							|| typeValue.trim().equals(Constants.FROZEN_TISSUE_BLOCK)
							|| typeValue.trim().equals(Constants.FIXED_TISSUE_SLIDE))
					{
						if (validator.isEmpty(value))
						{
							String message = ApplicationProperties
									.getValue("collectionprotocol.quantity");
							throw new DAOException("errors.item.required", new String[]{message});

						}
						else if (!validator.isNumeric(value))
						{
							String message = ApplicationProperties
									.getValue("collectionprotocol.quantity");
							throw new DAOException("errors.item.format", new String[]{message});

						}
					}
					else
					{
						if (validator.isEmpty(value))
						{
							String message = ApplicationProperties
									.getValue("collectionprotocol.quantity");
							throw new DAOException("errors.item.required", new String[]{message});

						}
						else if (!validator.isDouble(value))
						{
							String message = ApplicationProperties
									.getValue("collectionprotocol.quantity");
							throw new DAOException("errors.item.format", new String[]{message});

						}
					}
				}
			}
		}//END
*/
		if (eventCollection != null && eventCollection.size() != 0)
		{
			List specimenClassList = CDEManager.getCDEManager().getPermissibleValueList(
					Constants.CDE_NAME_SPECIMEN_CLASS, null);

			//	    	NameValueBean undefinedVal = new NameValueBean(Constants.UNDEFINED,Constants.UNDEFINED);
			List tissueSiteList = CDEManager.getCDEManager().getPermissibleValueList(
					Constants.CDE_NAME_TISSUE_SITE, null);

			List pathologicalStatusList = CDEManager.getCDEManager().getPermissibleValueList(
					Constants.CDE_NAME_PATHOLOGICAL_STATUS, null);
			List clinicalStatusList = CDEManager.getCDEManager().getPermissibleValueList(
					Constants.CDE_NAME_CLINICAL_STATUS, null);

			Iterator eventIterator = eventCollection.iterator();

			while (eventIterator.hasNext())
			{
				CollectionProtocolEvent event = (CollectionProtocolEvent) eventIterator.next();

				if (event == null)
				{
					throw new DAOException(ApplicationProperties
							.getValue("collectionProtocol.eventsEmpty.errMsg"));
				}
				else
				{
					if (!Validator.isEnumeratedValue(clinicalStatusList, event.getClinicalStatus()))
					{
						throw new DAOException(ApplicationProperties
								.getValue("collectionProtocol.clinicalStatus.errMsg"));
					}

					Collection reqCollection = event.getSpecimenRequirementCollection();

					if (reqCollection != null && reqCollection.size() != 0)
					{
						Iterator reqIterator = reqCollection.iterator();

						while (reqIterator.hasNext())
						{
							SpecimenRequirement requirement = (SpecimenRequirement) reqIterator
									.next();

							if (requirement == null)
							{
								throw new DAOException(ApplicationProperties
										.getValue("protocol.spReqEmpty.errMsg"));
							}
							else
							{
								String specimenClass = requirement.getSpecimenClass();

								if (!Validator.isEnumeratedValue(specimenClassList, specimenClass))
								{
									throw new DAOException(ApplicationProperties
											.getValue("protocol.class.errMsg"));
								}

								if (!Validator.isEnumeratedValue(Utility
										.getSpecimenTypes(specimenClass), requirement
										.getSpecimenType()))
								{
									throw new DAOException(ApplicationProperties
											.getValue("protocol.type.errMsg"));
								}

								if (!Validator.isEnumeratedValue(tissueSiteList, requirement
										.getTissueSite()))
								{
									throw new DAOException(ApplicationProperties
											.getValue("protocol.tissueSite.errMsg"));
								}

								if (!Validator.isEnumeratedValue(pathologicalStatusList,
										requirement.getPathologyStatus()))
								{
									throw new DAOException(ApplicationProperties
											.getValue("protocol.pathologyStatus.errMsg"));
								}
							}
						}
					}
					else
					{
						throw new DAOException(ApplicationProperties
								.getValue("protocol.spReqEmpty.errMsg"));
					}
				}
			}
		}
		else
		{
			throw new DAOException(ApplicationProperties
					.getValue("collectionProtocol.eventsEmpty.errMsg"));
		}

		if (operation.equals(Constants.ADD))
		{
			if (!Constants.ACTIVITY_STATUS_ACTIVE.equals(protocol.getActivityStatus()))
			{
				throw new DAOException(ApplicationProperties
						.getValue("activityStatus.active.errMsg"));
			}
		}
		else
		{
			if (!Validator.isEnumeratedValue(Constants.ACTIVITY_STATUS_VALUES, protocol
					.getActivityStatus()))
			{
				throw new DAOException(ApplicationProperties.getValue("activityStatus.errMsg"));
			}
		}

		return true;
	}
}