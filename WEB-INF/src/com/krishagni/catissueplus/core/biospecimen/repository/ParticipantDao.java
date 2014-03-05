package com.krishagni.catissueplus.core.biospecimen.repository;

import com.krishagni.catissueplus.core.biospecimen.domain.Participant;
import com.krishagni.catissueplus.core.common.repository.Dao;


public interface ParticipantDao extends Dao<Participant>{

	public Participant getParticipant(Long id);
	public void delete(Long participantId);
	public boolean checkActiveChildren(Long id);
	public boolean isSsnUnique(String socialSecurityNumber);
}
