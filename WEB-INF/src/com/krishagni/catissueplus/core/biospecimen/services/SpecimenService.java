
package com.krishagni.catissueplus.core.biospecimen.services;

import java.util.List;
import java.util.Map;

import com.krishagni.catissueplus.core.biospecimen.domain.Specimen;
import com.krishagni.catissueplus.core.biospecimen.events.LabelPrintJobSummary;
import com.krishagni.catissueplus.core.biospecimen.events.PrintSpecimenLabelDetail;
import com.krishagni.catissueplus.core.biospecimen.events.SpecimenAliquotsSpec;
import com.krishagni.catissueplus.core.biospecimen.events.SpecimenDeleteCriteria;
import com.krishagni.catissueplus.core.biospecimen.events.SpecimenDetail;
import com.krishagni.catissueplus.core.biospecimen.events.SpecimenInfo;
import com.krishagni.catissueplus.core.common.events.EntityStatusDetail;
import com.krishagni.catissueplus.core.common.events.DependentEntityDetail;
import com.krishagni.catissueplus.core.common.events.EntityQueryCriteria;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;
import com.krishagni.catissueplus.core.common.service.LabelPrinter;

public interface SpecimenService {
	public ResponseEvent<SpecimenDetail> getSpecimen(RequestEvent<EntityQueryCriteria> req);
	
	public ResponseEvent<List<SpecimenInfo>> getSpecimens(RequestEvent<List<String>> req);

	public ResponseEvent<List<SpecimenInfo>> getPrimarySpecimensByCp(RequestEvent<Long> req);
	
	public ResponseEvent<SpecimenDetail> createSpecimen(RequestEvent<SpecimenDetail> req);
	
	public ResponseEvent<SpecimenDetail> updateSpecimen(RequestEvent<SpecimenDetail> req);
	
	public ResponseEvent<List<SpecimenDetail>> updateSpecimensStatus(RequestEvent<List<EntityStatusDetail>> req);

	public ResponseEvent<List<SpecimenDetail>> deleteSpecimens(RequestEvent<List<SpecimenDeleteCriteria>> req);

	public ResponseEvent<List<DependentEntityDetail>> getDependentEntities(RequestEvent<EntityQueryCriteria> req);
	
	public ResponseEvent<List<SpecimenDetail>> collectSpecimens(RequestEvent<List<SpecimenDetail>> req);
	
	public ResponseEvent<List<SpecimenDetail>> createAliquots(RequestEvent<SpecimenAliquotsSpec> req);

	public ResponseEvent<SpecimenDetail> createDerivative(RequestEvent<SpecimenDetail> derivedReq);

	public ResponseEvent<Boolean> doesSpecimenExists(RequestEvent<String> label);
	
	public ResponseEvent<LabelPrintJobSummary> printSpecimenLabels(RequestEvent<PrintSpecimenLabelDetail> req);
	
	/** Used mostly by plugins **/
	public LabelPrinter<Specimen> getLabelPrinter();
	
	/** Mostly present for UI **/
	public ResponseEvent<Map<String, Object>> getCprAndVisitIds(RequestEvent<Long> req);

	public List<Specimen> getSpecimensByLabel(List<String> labels);

	public List<Specimen> getSpecimensById(List<Long> ids);
}
