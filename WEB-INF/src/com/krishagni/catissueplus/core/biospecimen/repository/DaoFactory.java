
package com.krishagni.catissueplus.core.biospecimen.repository;

import com.krishagni.catissueplus.core.administrative.repository.DistributionOrderDao;
import com.krishagni.catissueplus.core.administrative.repository.DistributionProtocolDao;
import com.krishagni.catissueplus.core.administrative.repository.DpRequirementDao;
import com.krishagni.catissueplus.core.administrative.repository.InstituteDao;
import com.krishagni.catissueplus.core.administrative.repository.PermissibleValueDao;
import com.krishagni.catissueplus.core.administrative.repository.ScheduledJobDao;
import com.krishagni.catissueplus.core.administrative.repository.ShipmentDao;
import com.krishagni.catissueplus.core.administrative.repository.SiteDao;
import com.krishagni.catissueplus.core.administrative.repository.SpecimenRequestDao;
import com.krishagni.catissueplus.core.administrative.repository.StorageContainerDao;
import com.krishagni.catissueplus.core.administrative.repository.ContainerTypeDao;
import com.krishagni.catissueplus.core.administrative.repository.UserDao;
import com.krishagni.catissueplus.core.audit.repository.AuditDao;
import com.krishagni.catissueplus.core.auth.repository.AuthDao;
import com.krishagni.catissueplus.core.common.repository.ConfigSettingDao;
import com.krishagni.catissueplus.core.common.repository.UniqueIdGenerator;
import com.krishagni.catissueplus.core.common.repository.UpgradeLogDao;
import com.krishagni.catissueplus.core.dashboard.repository.DashletDao;

public interface DaoFactory {
	public CollectionProtocolDao getCollectionProtocolDao();

	public ParticipantDao getParticipantDao();

	public CollectionProtocolRegistrationDao getCprDao();

	public SiteDao getSiteDao();

	public SpecimenDao getSpecimenDao();
	
	public SpecimenRequirementDao getSpecimenRequirementDao();

	public VisitsDao getVisitsDao();

	public UserDao getUserDao();
	
	public AuthDao getAuthDao();

	public UniqueIdGenerator getUniqueIdGenerator();

	public InstituteDao getInstituteDao();

	public StorageContainerDao getStorageContainerDao();
	
	public ContainerTypeDao getContainerTypeDao();

	public DistributionProtocolDao getDistributionProtocolDao();

	public SpecimenListDao getSpecimenListDao();

	public PermissibleValueDao getPermissibleValueDao();
	
	public ScheduledJobDao getScheduledJobDao();
	
	public DistributionOrderDao getDistributionOrderDao();
	
	public ConfigSettingDao getConfigSettingDao();
	
	public LabelPrintJobDao getLabelPrintJobDao();
	
	public SpecimenUnitDao getSpecimenUnitDao();
	
	public AuditDao getAuditDao();

	public DpRequirementDao getDistributionProtocolRequirementDao();
	
	public ShipmentDao getShipmentDao();

	public SpecimenRequestDao getSpecimenRequestDao();
	
	public DashletDao getDashletDao();
	
	public UpgradeLogDao getUpgradeLogDao();
} 
