<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="edu.wustl.catissuecore.util.HelpXMLPropertyHandler"%>
<%@ page language="java" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="edu.wustl.catissuecore.dto.SpecimenDTO"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
    var imgsrc="images/";
    window.dhx_globalImgPath = "dhtmlxSuite_v35/dhtmlxWindows/codebase/imgs/";
</script>
<style>
.errorStyleOn{
    border: 1px solid #FF0000;
}
.textarea { height: auto; }
</style>
<style type="text/css">
    #myoutercontainer { text-align: center;display:block;float: left; }
    #myinnercontainer { display: block; vertical-align: middle;*overflow: hidden;}    
</style>
<link rel="STYLESHEET" type="text/css" href="dhtmlxSuite_v35/dhtmlxCalendar/codebase/dhtmlxcalendar.css" />
<link rel="STYLESHEET" type="text/css" href="dhtmlxSuite_v35/dhtmlxCalendar/codebase/skins/dhtmlxcalendar_dhx_skyblue.css" />

<script type="text/javascript" src="dhtmlxSuite_v35/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="dhtmlxSuite_v35/dhtmlxCalendar/codebase/dhtmlxcalendar.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtmlxSuite_v35/dhtmlxTabbar/codebase/dhtmlxcontainer.js"></script>
<link href="css/catissue_suite.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="css/styleSheet.css" />
<link rel="stylesheet" type="text/css" href="css/alretmessages.css"/>
<link rel="stylesheet" type="text/css" href="css/tag-popup.css"/>
<link rel="STYLESHEET" type="text/css" href="dhtmlxSuite_v35/dhtmlxWindows/codebase/dhtmlxwindows.css"/>
<link rel="STYLESHEET" type="text/css" href="dhtmlxSuite_v35/dhtmlxWindows/codebase/skins/dhtmlxwindows_dhx_skyblue.css"/>
<link rel="STYLESHEET" type="text/css" href="dhtmlxSuite_v35/dhtmlxCombo/codebase/dhtmlxcombo.css"/>
<link rel="STYLESHEET" type="text/css" href="dhtmlxSuite_v35/dhtmlxTree/codebase/dhtmlxtree.css"/>
<link rel="STYLESHEET" type="text/css" href="dhtmlxSuite_v35/dhtmlxGrid/codebase/dhtmlxgrid.css"/>
<link rel="STYLESHEET" type="text/css" href="dhtmlxSuite_v35/dhtmlxGrid/codebase/skins/dhtmlxgrid_dhx_skyblue.css"/>
<link rel="STYLESHEET" type="text/css" href="dhtmlxSuite_v35/dhtmlxGrid/codebase/ext/dhtmlxgrid_pgn_bricks.css"/>
<link rel="STYLESHEET" type="text/css" href="dhtmlxSuite_v35/dhtmlxToolbar/codebase/skins/dhtmlxtoolbar_dhx_blue.css"/>
<link rel="STYLESHEET" type="text/css" href="dhtmlxSuite_v35/dhtmlxTabbar/codebase/dhtmlxtabbar.css"/>


<script language="JavaScript"  type="text/javascript" src="dhtmlxSuite_v35/dhtmlxTabbar/codebase/dhtmlxtabbar.js"></script>
<script language="JavaScript"  type="text/javascript" src="dhtmlxSuite_v35/dhtmlxTree/codebase/dhtmlxtree.js"></script>
<script language="JavaScript"  type="text/javascript" src="dhtmlxSuite_v35/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script language="JavaScript"  type="text/javascript" src="dhtmlxSuite_v35/dhtmlxCombo/codebase/dhtmlxcombo.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtmlxSuite_v35/dhtmlxCombo/codebase/ext/dhtmlxcombo_whp.js"></script>

<script language="JavaScript" type="text/javascript" src="dhtmlxSuite_v35/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtmlxSuite_v35/dhtmlxTree/codebase/ext/dhtmlxtree_li.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtmlxSuite_v35/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtmlxSuite_v35/dhtmlxTreeGrid/codebase/dhtmlxtreegrid.js"></script>
<script language="JavaScript"type="text/javascript" src="dhtmlxSuite_v35/dhtmlxDataView/codebase/connector/connector.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtmlxSuite_v35/dhtmlxGrid/codebase/ext/dhtmlxgrid_filter.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtmlxSuite_v35/dhtmlxGrid/codebase/ext/dhtmlxgrid_pgn.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtmlxSuite_v35/dhtmlxToolbar/codebase/dhtmlxtoolbar.js"></script>


<script type="text/javascript" src="jss/tag-popup.js"></script>
<script language="JavaScript" type="text/javascript" src="jss/newSpecimen.js"></script>
<script src="jss/script.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript"    src="jss/javaScript.js"></script>
<script language="JavaScript" type="text/javascript"    src="jss/caTissueSuite.js"></script>
<script src="jss/ajax.js" type="text/javascript"></script>
<script src="jss/json2.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="jss/specimen.js"></script>
<script language="JavaScript" type="text/javascript" src="jss/dhtmlDropDown.js"></script>





<style>
.noEffect{
    border:4px solid blue;
    color:red;
}
</style>
<script>
    var imgsrc="images/";
    window.dhx_globalImgPath = "dhtmlxSuite_v35/dhtmlxWindows/codebase/imgs/";
		var activityStatusCombo={};
        var aliquotDateErr = false;
        var aliquotGrid;
        var aliquotPopUpParam = {};
        var aliquotNameSpace = {};
		var userListCombo;
        function showDisableSpecimenWindow(){
            if(aliquotNameSpace.dhxWins == undefined){
                aliquotNameSpace.dhxWins = new dhtmlXWindows();
                aliquotNameSpace.dhxWins.setSkin("dhx_skyblue");
                aliquotNameSpace.dhxWins.enableAutoViewport(true);
            }
        //    aliquotNameSpace.dhxWins.setImagePath("");
            if(aliquotNameSpace.dhxWins.window("containerPositionPopUp")==null){
                var w =500;
                var h =360;
                var x = (screen.width / 3) - (w / 2);
                var y = 150;
                aliquotNameSpace.dhxWins.createWindow("containerPositionPopUp", x, y, w, h);
                //aliquotNameSpace.dhxWins.setPosition(x, y);
                //aliquotNameSpace.dhxWins.window("containerPositionPopUp").center();
                aliquotNameSpace.dhxWins.window("containerPositionPopUp").allowResize();
                aliquotNameSpace.dhxWins.window("containerPositionPopUp").setModal(true);
                aliquotNameSpace.dhxWins.window("containerPositionPopUp").setText("Dispose Specimen");
                aliquotNameSpace.dhxWins.window("containerPositionPopUp").button("minmax1").hide();
                aliquotNameSpace.dhxWins.window("containerPositionPopUp").button("park").hide();
                aliquotNameSpace.dhxWins.window("containerPositionPopUp").button("close").hide();
                aliquotNameSpace.dhxWins.window("containerPositionPopUp").setIcon("images/terms-conditions.png", "images/terms-conditions.png");
             
			 if(document.getElementById("disposalPop")==null){
				  var div = document.createElement("div");
            
                div.id="disposalPop";
				div.innerHTML = windowDivStr;
				  document.body.appendChild(div);
              
			 }else{
				windowDivStr = document.getElementById("disposalPop").innerHTML;
			 }
			 doInitCal('disposalDate',false,'${uiDatePattern}');
			 userListCombo = dhtmlXComboFromSelect("userId");
		//classNameCombo.setOptionWidth(203);
	    userListCombo.setSize(203);
	    userListCombo.attachEvent("onOpen",onComboClick);
	    userListCombo.attachEvent("onKeyPressed",onComboKeyPress);
				aliquotNameSpace.dhxWins.window("containerPositionPopUp").attachObject("disposalPop");
            }
			  
        }
		var windowDivStr;
		function submitEvent(){
            var specimenStatus = getCheckedRadioId("aliquotRadio");
			var reason = document.getElementById('reason').value;
			var spId = document.getElementById("id").value;
			var userId = userListCombo.getSelectedValue();
			var disposalDate = document.getElementById("disposalDate").value;
			var disposalHours = document.getElementById("disposalHours").value;
			var disposalMins = document.getElementById("disposalMins").value;
			
			tabDataJSON["comments"]= reason;
			tabDataJSON["activityStatus"]= specimenStatus;
			tabDataJSON["id"] = spId;
			tabDataJSON["userId"]= userId;
			tabDataJSON["disposalDate"]= disposalDate;
			tabDataJSON["disposalHours"]= disposalHours;
			tabDataJSON["disposalMins"]= disposalMins;
            createRestCall(tabDataJSON,spId);
        }
		function createRestCall(specimenData ,spId){
			var req = createRequest(); // defined above
// Create the callback:
document.getElementById("specimenSubmitButton").disabled = true;
req.onreadystatechange = function() {
  if (req.readyState != 4) return; // Not there yet
  if (req.status != 201) {
    // Handle request failure here...
	var errorMsg=req.getResponseHeader("errorMsg");
	
	document.getElementById('pop-error').innerHTML = errorMsg;
	document.getElementById('pop-error').style.display='block';
	//showErrorMessage(errorMsg);
	document.getElementById("specimenSubmitButton").disabled = false;
    return;
  }
  document.getElementById("specimenSubmitButton").disabled = false;
  // Request successful, read the response
  var resp = req.responseText;
  var updatedSpecimenDTO = eval('('+resp+')')
  var contSpan = document.getElementById('containerSpan');
	  var contPopSpan = document.getElementById('containerPopSpan');
	  if(contPopSpan){
	    document.getElementById('containerPopSpan').innerHTML = ''; 
	  }
	  if(contSpan){
	    document.getElementById('containerSpan').innerHTML = 'Virtually Located'; 
	  }
	if(updatedSpecimenDTO.activityStatus == 'Disabled'){
	closeTermWindow();
	  parent.handleCpView(null, updatedSpecimenDTO.specimenCollectionGroupId, null);
	  parent.handleCpViewForSubCP(updatedSpecimenDTO.specimenCollectionGroupId,'Complete','','');
	  
	  document.getElementById('error').style.display='none';
	  document.getElementById('success').style.display='block';
	  var divStyle = document.getElementById('specListDiv').style.display;
	  divStyle='block';
	  document.getElementById('specListDiv').style.display='none';
	  document.getElementById('deleteButtonDiv').style.display='none';
	  document.getElementById('specimenSubmitButton').style.display='none';
	  document.getElementById('available').disabled = false;
	  document.getElementById('available').checked = updatedSpecimenDTO.available;
	  
	  var activeSpan = document.getElementById('activitySpan');
	  if(activeSpan){
		document.getElementById('activitySpan').innerHTML = updatedSpecimenDTO.activityStatus;
		}else{
	  
	  
	  activityStatusCombo.setComboValue("Disabled");
			activityStatusCombo.setComboText("Disabled");
			}
			tabDataJSON={};
		
			scrollToTop();
	  return;
	  
	}
	
		parent.handleCpView(null, updatedSpecimenDTO.specimenCollectionGroupId, updatedSpecimenDTO.id);
		parent.handleCpViewForSubCP(updatedSpecimenDTO.id,updatedSpecimenDTO.collectionStatus,updatedSpecimenDTO.label,'');
	
				document.getElementById('available').disabled = false;
				document.getElementById('available').checked = updatedSpecimenDTO.available;
				var actSpan = document.getElementById('activitySpan');
				if(actSpan){
				document.getElementById('activitySpan').innerHTML = updatedSpecimenDTO.activityStatus;
				}
				document.getElementById('error').style.display='none';
				document.getElementById('success').style.display='block';
				//forwardToChildSpecimen(operation);
			
			var divStyle = document.getElementById('specListDiv').style.display;
			
			if(divStyle == 'none')
			{
				divStyle='block';
				document.getElementById('specListDiv').style.display='block';
			}
			tabDataJSON={};
		/*	if(operation == 'add')
			{
				LoadSCGTabBar('edit');
			}*/
			scrollToTop();
			closeTermWindow();
		}
		
		
		
			req.open("PUT", "rest/specimens/"+spId+"/updateStatus", false);
		
		req.setRequestHeader("Content-Type",
							 "application/json");
		req.send(JSON.stringify(specimenData));

		}
        function getCheckedRadioId(name) {
            var elements = document.getElementsByName(name);
            for (var i=0, len=elements.length; i<len; ++i)
                if (elements[i].checked) return elements[i].value;
        }
        function closeTermWindow(){
            aliquotNameSpace.dhxWins.window("containerPositionPopUp").close();
        }
</script>
<!----------------------------------------------------------------------->
<body onload="LoadSCGTabBar('${requestScope.operation}');"> 
<html:form action="NewSpecimenEdit.do">

<html:hidden name="specimenDTO" property="generateLabel"/>
<html:hidden name="specimenDTO" property="operation"/>
<html:hidden name="specimenDTO" property="parentSpecimenId" styleId="parentSpecimenId"/>
<html:hidden name="specimenDTO" property="id" styleId="id"/>
<html:hidden name="specimenDTO" property="requirementId" styleId="requirementId"/>
<html:hidden name="specimenDTO" property="specimenCollectionGroupId" styleId="scgId"/>
                                
    <table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%">
      <tr>
        <td>
            <div id="specimen_tabbar" style="width:99%; height:98%;position:absolute;">
            <div id='specimenDetailsDiv' style="overflow:auto;height:100%">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="whitetable_bg">
            <tr>
            <td>
                <div id="mainTable"style="display:block">
                <table width="100%"  border="0" cellpadding="3" cellspacing="0" >
                <tr>
                    <td>
                        <%@ include file="/pages/content/common/ActionErrors.jsp"%>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <div id="error" class="alert alert-error" style="display:none">
                            <strong>Error!</strong> <span id="errorMsg">Change a few things up and try submitting again.</span>
                        </div>
                        <div id="print-error" class="alert alert-error" style="display:none">
                            Change a few things up and try submitting again.
                        </div>
                        <div class="alert alert-success" id="success" style="display:none">
                           Specimen Updated Sucessfully.
                        </div>
                        <div class="alert alert-success" id="print-success" style="display:none">
                           Specimen Label Printed successfully.
                        </div>
                        
                    </td>
                </tr>
                <tr>
                  <td align="left" class="showhide">
                    <table width="100%" border="0" cellpadding="3" cellspacing="0" >
                        <!-- NEW SPECIMEN REGISTRATION BEGINS-->
                        <tr class="tr_alternate_color_lightGrey">
						<logic:equal name="specimenDTO"  property='lineage' value="New">
										
                         <td width="20%" class="black_ar align_right_style">
                            <label for="specimenCollectionGroupName">
                                <bean:message key="newSpecimen.groupName"/>
                            </label>
                         </td>
                         <td width="30%" align="left" class="black_ar">
                            <html:hidden name="specimenDTO" property="specimenCollectionGroupName" styleId="specimenCollectionGroupName"/>
                                <label id ="specimenCollectionGroupNameLable" for="specimenCollectionGroupName">
                                    <bean:write name="specimenDTO" property="specimenCollectionGroupName" scope="request"/>
                                </label>
                         </td>
                </logic:equal>
                
				<logic:notEqual name="specimenDTO"  property='lineage' value="New">
						
                          <td width="20%" class="black_ar align_right_style">
                                <label for="parentSpecimenId">
                                    <bean:message key="newSpecimen.parentLabel"/>
                                </label>
                          </td>
                          <td width="30%" align="left" class="black_ar">
                                
                                <html:hidden name="specimenDTO" property="parentSpecimenName"/>
                                <label for="parentSpecimenId">
                                    <bean:write name="specimenDTO" property="parentSpecimenName" scope="request"/>
                                </label>

                          </td>
                </logic:notEqual>

                          <td width="20%"  class="black_ar align_right_style">
                                <label for="lineage">
                                    <bean:message key="specimen.lineage"/>
                                </label>
                          </td>
                          <td width="30%" align="left" class="black_ar">
                            <label for="lineage">
                                <bean:write name="specimenDTO" property="lineage" scope="request"/>
                                <html:hidden name="specimenDTO" property="lineage" styleId="lineage"/>
                            </label>
                          </td>
                    </tr>
					<tr class="tr_alternate_color_white">
                            <td width="20%" class="black_ar align_right_style">
                               
                                <label for="label">
                                    ASIG ID
                                </label>
                            </td>
                            <td align="left" width="30%"  class="black_new">
                               <bean:write name="specimenDTO" property="asigID" scope="request"/>
                            </td>

                            <td width="20%" class="black_ar align_right_style">
                                <img src="images/uIEnhancementImages/star.gif" alt="Mandatory" width="6" height="6" hspace="0" vspace="0" />    
                                <label for="type">
                                     <bean:message key="specimen.subType"/>
                                </label>
                            </td>
                            <td width="30%" align="left" class="black_new">
                            <html:select property="type" name="specimenDTO" 
                            styleClass="formFieldSized19" styleId="type" size="1"
                            onmouseover="showTip(this.id)" onmouseout="hideTip(this.id)">
                            <html:options collection="specimenTypeList"
                                labelProperty="name" property="value" />
                            </html:select>
                        
                              </td>
                        </tr>
						
						
						
						
                        <tr class="tr_alternate_color_lightGrey">
                            <td width="20%" class="black_ar align_right_style">
                                <label for="label">
                                    <bean:message key="specimen.label"/>
                                </label>
                            </td>
                            <td align="left" width="30%">
                                <html:text styleClass="black_ar" size="30" maxlength="255"  styleId="label" name="specimenDTO" property="label" onmouseover="showTip(this.id)" onmouseout="hideTip(this.id)" onblur="processData(this)" disabled="false" />
                            </td>

                             <td width="20%" class="black_ar align_right_style">
                                    <label for="createdDate">
									 <c:choose>
									 	 <c:when test="${specimenDTO.lineage=='New'}">
											Sample Date 
										 </c:when>
										 <c:otherwise>
											 Created On
										 </c:otherwise>
									 </c:choose>
                                        
                                    </label>
                                </td>
                                <td width="30%" class="black_ar" >
                                <input type="text" name="createdDate" class="black_ar"
                                   id="createdDate" size="10" onblur="processData(this)" value='<fmt:formatDate value="${specimenDTO.createdDate}" pattern="${datePattern}" />'/>
                                <span id="dateId" class="grey_ar_s capitalized">[${datePattern}]</span>&nbsp;
                                </td>
                        </tr>
						
		        <tr class="tr_alternate_color_white">
			  <c:if test="${specimenDTO.lineage=='New'}">
                            <td width="20%" class="black_ar align_right_style">
			      <label for="createdDate">	Lab Number </label>
			    </td>
			   			    
			    <td width="30%" class="black_ar">
			      <html:text styleClass="black_ar" size="30" maxlength="255"  styleId="labNumber" name="specimenDTO" 
		                property="labNumber" onmouseover="showTip(this.id)" onmouseout="hideTip(this.id)" onblur="processData(this)" />
                            </td>
		          </c:if>
		          
								
                                <td width="20%" class="black_ar align_right_style">
                                 
                                    <label for="tissueSite">
                                        Site
                                    </label>
                                </td>
							  <td  class="black_ar" >
                               		<bean:write name="specimenDTO" property="siteName" scope="request"/>
                              
                                </td>

                            </tr>
							<c:if test="${specimenDTO.lineage=='New' and specimenDTO.type=='Serum' or specimenDTO.type=='Buffy Coat'}">
							<tr class="tr_alternate_color_white">
								<td width="20%" class="black_ar align_right_style">
                                    <label for="createdDate">
									Quality
                                    </label>
                                </td>
                             
								<td width="30%" class="black_ar" >
									<c:choose>
										<c:when test="${specimenDTO.quality=='Good'}">
											<input type="radio" value="Good" id="dnaQualityGood" name="specimenQuality" onclick="onCheckboxButtonClick(this)" style="vertical-align: middle;" checked="true"/>
										</c:when>
										<c:otherwise>
											<input type="radio" value="Good" id="dnaQualityGood" name="specimenQuality" onclick="onCheckboxButtonClick(this)" style="vertical-align: middle;"/>
										</c:otherwise>
									</c:choose>
											<span class="black_ar" style="vertical-align: middle;">
											<bean:message key="participant.dnaQuality.good"/>&nbsp;
											</span>
									<c:choose>
										<c:when test="${specimenDTO.quality!='Good'}">
											<input type="radio" value="Poor" id="dnaQualityPoor" name="specimenQuality" onclick="onCheckboxButtonClick(this)" style="vertical-align: middle;" checked="true"/>
										</c:when>
										<c:otherwise>
											<input type="radio" value="Poor" id="dnaQualityPoor" name="specimenQuality" onclick="onCheckboxButtonClick(this)" style="vertical-align: middle;" />
										</c:otherwise>
									</c:choose>
										<span class="black_ar" style="vertical-align: middle;">
											<bean:message key="participant.dnaQuality.poor"/>&nbsp;
										</span>
                                </td>
								
                                <td width="20%" class="black_ar align_right_style">
                                 
                                    <label for="tissueSite">
                                        Venesection Date
                                    </label>
                                </td>
							  <td  class="black_ar" >
									<input type="text" name="venesectionDate" class="black_ar"
                                   id="venesectionDate" size="10" onblur="processData(this)" value='<fmt:formatDate value="${specimenDTO.venesectionDate}" pattern="${datePattern}" />'/>
                               		&nbsp;
		
								<html:select property="venesectionHours" name="specimenDTO" styleClass="black_ar" styleId="venesectionHours" size="1">
										<logic:iterate name="hourList" id="listhoursId">
											<html:option  value="${listhoursId}"> ${listhoursId} </html:option>
										</logic:iterate>
								</html:select> &nbsp;
				
								<html:select property="venesectionMins" name="specimenDTO" styleClass="black_ar" styleId="venesectionMins" size="1">
										<logic:iterate name="minutesList" id="listminutesId">
											<html:option  value="${listminutesId}"> ${listminutesId} </html:option>
										</logic:iterate>
								</html:select>
                              
                                </td>

                            </tr>
							</c:if>
							<c:if test="${!(specimenDTO.lineage=='Aliquot') and specimenDTO.type=='DNA'}">
								<tr class="tr_alternate_color_lightGrey">
									<td width="20%" class="black_ar align_right_style">
										<label for="createdDate">
										Method
										</label>
									</td>
								 
									<td width="30%" class="black_ar" >
										<select id="dnaMethod">
										
										
										
											<logic:iterate id="DNAMethodList" name="DNAMethodList">
												
													<logic:notEqual name="DNAMethodList"  property='name' value="${specimenDTO.dnaMethod}">
											<option value="<bean:write name='DNAMethodList' property='value'/>"><bean:write name="DNAMethodList" property="name"/></option>
										</logic:notEqual>
										<logic:equal name="DNAMethodList"  property='name' value="${specimenDTO.dnaMethod}">
											<option value="<bean:write name='DNAMethodList' property='value'/>"  selected><bean:write name="DNAMethodList" property="name"/></option>
										</logic:equal>
											</logic:iterate>
												
										</select>
									</td>
									
									<td width="20%" class="black_ar align_right_style">
									 
										<label for="tissueSite">
											260/280
										</label>
									</td>
									<td  class="black_ar" >
										<input id="260-280" onblur="chkeEmptyNumber(this);processData(this)" value="${specimenDTO.dna260}" class="black_ar" type="text" onblur="processData(this)" onmouseout="hideTip(this.id)" onmouseover="showTip(this.id)" size="30" maxlength="255"/>
										<div id="dna260ErrMsg" style="display:none; color:red;">
									</td>

								</tr>
								
								<tr class="tr_alternate_color_white">
							 <td width="20%" class="black_ar align_right_style">
                                    <label for="concentration">
                                        <bean:message key="specimen.concentration"/>
                                    </label>
                                </td>
                                <td  width="30%" align="left" class="black_ar">
                                        <html:text styleClass="black_ar" maxlength="10"  size="10" styleId="concentration" property="concentration" style="text-align:right" name="specimenDTO" onblur="chkeEmptyNumber(this);processData(this)"
                                         disabled="false"/>
                                        <bean:message key="specimen.concentrationUnit"/>
                                        <div id="concentrationErrorMsg" style="display:none; color:red;">
                                     </div>
                                </td>
									
									<td width="20%" class="black_ar align_right_style">
									 
										
									</td>
									<td  class="black_ar" >
										
									</td>

								</tr>
							
							</c:if>
							
							
						
						    <tr class="tr_alternate_color_lightGrey">
                                <td width="20%" class="black_ar align_right_style">
                                    <img src="images/uIEnhancementImages/star.gif" alt="Mandatory" width="6" height="6" hspace="0" vspace="0" />    
                                    <label for="quantity">
                                        <bean:message key="specimen.quantity"/>
                                    </label>
                                </td>
                                <td width="30%" align="left" class="black_ar">
                                    <html:text styleClass="black_ar" size="10" maxlength="10"  styleId="quantity" property="quantity" name="specimenDTO"  style="text-align:right" onblur="processData(this)"/>
                                     <span id="unitSpan">
										
                                     </span>
                                     <html:hidden property="unit"/>
                                     <div id="quantityErrorMsg" style="display:none; color:red;">
                                     </div>
									 <div style="display:none;">
										<html:text styleClass="black_ar" maxlength="10"  size="10" styleId="concentration" property="concentration" style="text-align:right" name="specimenDTO" onblur="chkeEmptyNumber(this);processData(this)"
                                         disabled="false"/>
									 </div>
                                </td>
								  <td width="20%" class="black_ar align_right_style">
                                    <label for="className">
                                        Sample Box Name
                                    </label>
                                </td>
                                                                
                                <td colspan="3" class="black_ar">
                    <!-------Select Box Begins----->
                    
                                <logic:equal name="specimenDTO" property="isVirtual" value="true">
                                    
                                    <input type="text" size="30" maxlength="255"  class="black_ar tr_alternate_color_lightGrey"  value='Virtually Located' readonly style="border:0px;" id="storageContainerPosition" title="Virtually Located"/>
                                </logic:equal>
                                <logic:equal name="specimenDTO" property="isVirtual" value="false">
                                <input type="text" size="30" maxlength="255"  class="black_ar tr_alternate_color_lightGrey"  value='<bean:write name="specimenDTO" property="containerName" scope="request"/>:(<bean:write name="specimenDTO" property="pos1" scope="request"/>,<bean:write name="specimenDTO" property="pos2" scope="request"/>)' readonly style="border:0px" id="storageContainerPosition" title='<bean:write name="specimenDTO" property="containerName" scope="request"/>:(<bean:write name="specimenDTO" property="pos1" scope="request"/>,<bean:write name="specimenDTO" property="pos2" scope="request"/>)'/>
                                    
                                </logic:equal>
                                <a href="#" onclick="javascript:loadDHTMLXWindowForTransferEvent();return false">
                            <img src="images/uIEnhancementImages/grid_icon.png" alt="Displays the positions for the selected container"  width="16" height="16" border="0" style="vertical-align: middle" title="Displays the positions for the selected container"></a>
                                    
                                
                                <span style="left-padding:5px">
                                <a href="#" onclick="javascript:openViewMap();return false">
            <img src="images/uIEnhancementImages/Tree.gif" border="0" width="16" height="16" style="vertical-align: bottom" title="select positions from hierarchical view"/>
        </a>
                                </span>
                                <html:hidden name="specimenDTO" property="isVirtual" styleId="isVirtual"/>
                                <html:hidden name="specimenDTO" property="containerName" styleId="containerName"/>
                                <html:hidden name="specimenDTO" property="pos1" styleId="pos1"/>
                                <html:hidden name="specimenDTO" property="pos2" styleId="pos2"/>
                                <html:hidden name="specimenDTO" property="containerId" styleId="containerId" />
                                </td>
                            </tr>
            
                            <tr class="tr_alternate_color_white">
                                 <td width="20% class="black_ar">&nbsp;
                                 </td>
                                 <td width="30%" align="left" valign="top" >
                                 <logic:equal name="operation" value="add">
                                    <input type="checkbox" name="available" id="available" onblur="processData(this)" disabled checked="checked"/>
                                </logic:equal>
                                <logic:equal name="operation" value="edit">
                                        <html:checkbox property="available" styleId="available" onclick="processData(this)" >
                                    </html:checkbox>
                                </logic:equal>
                                    
                                    <span class="black_ar" style="padding-bottom:7px">
                                        <label for="available">
                                            <bean:message key="specimen.available" />
                                        </label>
                                    </span>
                                </td>
								 <td width="20%" class="black_ar align_right_style">
                                    <label for="availableQuantity">
                                     Shelf
                                    </label>
                                </td>
                                <td width="30%" align="left" class="black_ar">
									<bean:write name="specimenDTO" property="shelf" scope="request"/>
                                </td>
                            </tr>
							<tr class="tr_alternate_color_lightGrey">
							  <td width="20%" class="black_ar align_right_style">
                                    <label for="availableQuantity">
                                            <bean:message key="specimen.availableQuantity" />
                                    </label>
                                </td>
                                <td width="30%" align="left" class="black_ar">
                                <logic:equal name="operation" value="add">
                                    <html:text styleClass="black_ar" maxlength="10"  size="10" styleId="availableQuantity" readonly="true" property="availableQuantity"     name="specimenDTO" style="text-align:right" onblur="processData(this)"/>
                                </logic:equal>
                                <logic:equal name="operation" value="edit">
                                    <html:text styleClass="black_ar" maxlength="10"  size="10" styleId="availableQuantity" property="availableQuantity"     name="specimenDTO" style="text-align:right" onblur="processData(this)"/>
                                </logic:equal>
                                    <span id="unitSpan1"></span>
                                    <div id="avlQuantityErrorMsg" style="display:none; color:red;">
                                     </div>
                                </td>
								<td width="20%" class="black_ar align_right_style">
                                    <label for="availableQuantity">
                                     Drawer
                                    </label>
                                </td>
                                <td width="30%" align="left" class="black_ar">
									<bean:write name="specimenDTO" property="drawer" scope="request"/>
                                </td>
							</tr>
                            <tr class="tr_alternate_color_white">
                                <td width="20%" class="black_ar align_right_style">
                                    <img src="images/uIEnhancementImages/star.gif" alt="Mandatory" width="6" height="6" hspace="0" vspace="0" />    
                                    <label for="collectionStatus">
                                    <bean:message key="specimenCollectionGroup.collectionStatus" />

                                    </label>
                                </td>
                                <td width="30%" class="black_new">
                                <logic:equal name="operation" value="edit">
                                <div id="" style="display:block"/>
                                </logic:equal>
                                <logic:equal name="operation" value="add">
                                <div id="" style="display:none">
                                    <bean:write name="specimenDTO" property="collectionStatus"/>                        </div>                        
                                    
                                </logic:equal>
                                <html:select property="collectionStatus" name="specimenDTO" 
                                             styleClass="formFieldSized19" styleId="collectionStatus" size="1"
                                             onmouseover="showTip(this.id)" onmouseout="hideTip(this.id)">
                                <html:options collection="collectionStatusList"
                                              labelProperty="name" property="value" />
                                </html:select>
                                
                                </td>

								<td width="20%" class="black_ar align_right_style">
								    <label for="comments">
										Consent To Use Of blood
                                    </label>
                                
                                </td>
                                <td width="30%" align="left" class="black_ar">
									<bean:write name="specimenDTO" property="consentBlood" scope="request"/>
                                </td>
								
                              
                            </tr>
                        
								<tr class="tr_alternate_color_lightGrey">
                                 <td width="20%" class="black_ar align_right_style">
                                    <label for="activityStatus">
                                        <bean:message key="participant.activityStatus" />
                                    </label>
                                </td>
                                <td width="30%" align="left" class="black_ar">
                                    <c:choose>
                                        <c:when test="${specimenDTO.activityStatus == 'Active'}">
                                            <label for="activityStatus"><span id="activitySpan">
                                                <bean:write name="specimenDTO" property="activityStatus" scope="request"/>
												</span>
                                            </label>
                                        </c:when>
                                        <c:otherwise>
                                            <html:select property="activityStatus" name="specimenDTO" 
                                                     styleClass="formFieldSized19" styleId="activityStatus" size="1"
                                                     onmouseover="showTip(this.id)" onmouseout="hideTip(this.id)">
                                            <html:options collection="activityStatusList"
                                                      labelProperty="name" property="value" />
                                            </html:select>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
								<td width="20%" class="black_ar align_right_style">
								    <label for="comments">
										Freeze Thaw Cycles
                                    </label>
                                </td>
                                <td width="30%" align="left" class="black_ar">
										<input id="thawCycle" onblur="chkeEmptyNumber(this);processData(this)" value="${specimenDTO.thawCycle}" class="black_ar" type="text" onblur="processData(this)" onmouseout="hideTip(this.id)" onmouseover="showTip(this.id)" size="30" maxlength="255"/>
                                </td>
                            </tr>
						   
						   
						   
                            <tr class="">
                                <td width="20%" valign="top" class="black_ar align_right_style">
                                    <label for="comments">
                                        <bean:message key="specimen.comments"/>
                                    </label>
                                </td>
                                <td align="left" valign="top">
                                    <html:textarea styleClass="black_ar_s"  rows="3" cols="60" name="specimenDTO" styleId="comments" property="comments" onblur="processData(this)"/>
                                </td>
								 
                            </tr>
                            
							
                        <tr>
                          <td colspan="4" valign="middle" class="tr_bg_blue1">
                            <span class="blue_ar_b">&nbsp;<bean:message key="childSpecimen.label" /></span>
                          </td>
                        </tr>
                                
                            </table>
                          </td>
                        </tr>
                        
                    
                        
                            <!-- collectionstatus -->
                        </table>
                    </div>
                </td>
            </tr>
            
            <tr>
                                <!--
          <td valign="middle" class="black_ar" >
          -->
          <td valign="top">
          <table width="100%" border="0" cellpadding="3" cellspacing="0">
               <tr>
                  <td width="17%" align="left" nowrap class="black_ar" colspan="2"><div id="myinnercontainer">
                        <input type="radio" value="1" id="aliquotCheck" name="specimenChild" onclick="onCheckboxButtonClick(this)" checked="true" style="vertical-align: middle;"/>
                                <span class="black_ar" style="vertical-align: middle;">
                                <bean:message key="app.none" />&nbsp;
                                </span>
                        <input type="radio" value="2" id="aliquotChk" name="specimenChild" onclick="onCheckboxButtonClick(this)" style="vertical-align: middle;"/>
                        <span class="black_ar" style="vertical-align: middle;">
                                <bean:message key="aliquots.title"/>
                                &nbsp;
                                </span>
                        <input type="radio" value="3" id="deriveChk" name="specimenChild" onclick="onCheckboxButtonClick(this)" style="vertical-align: middle;"/>
                                <span class="black_ar" style="vertical-align: middle;">
                                <bean:message key="specimen.derivative" />
                                &nbsp;</span>
                        <!-- 11706 S Desctiption : Remove equal check for Edit operation only....-->
                        <input type="radio" value="4" id="createCpChildCheckBox" name="specimenChild" onclick="onCheckboxButtonClick(this)" style="vertical-align: middle;"/>
                        <span class="black_ar" style="vertical-align: middle;">
                                <bean:message key="create.childAsperCP"/>
                                </span></div>
                        <!-- 11706 E -->
                    </td>
                </tr>

                <!--specimenPageButton-->
                <tr><td colspan="2"></td></tr>
                <tr>
                    <td class="black_ar" width="18%" nowrap colspan="2">
                             <div style="display:none" id="derivedDiv">
                             
                             <bean:message key="derive.noOfSpecimens"/>&nbsp;
                            <html:text styleClass="black_ar" styleId="numberOfSpecimens" size="10" property="numberOfSpecimens" value="1" style="text-align:right"/>
                        </div>
                        <div style="display:none" id="aliquotDiv">
                            
                            <bean:message key="aliquots.noOfAliquots" />&nbsp;
                            <html:text styleClass="black_ar" styleId="noOfAliquots" size="10" property="noOfAliquots" disabled="true" style="text-align:right"/>
                            &nbsp;
                            <bean:message key="aliquots.qtyPerAliquot"/>&nbsp;

                            <html:text styleClass="black_ar" styleId="quantityPerAliquot" size="10" property="quantityPerAliquot" disabled="true" style="text-align:right"/>
                            </div>
                    </td>
                    
                            
                            
                </tr>
                 <tr>
                                <td class="dividerline" colspan="3"><span class="black_ar"></td>
                                </tr>
                                <tr>
                                

                                            <td colspan="1" valign="center">
                                                    <html:checkbox styleId="printCheckbox" property="printCheckbox" value="true">
                                                        <span class="black_ar">
                                                            <bean:message key="print.checkboxLabel"/>
                                                        </span>
                                                        </html:checkbox>
                                            </td>

                                
    <!--  Added for displaying  printer type and location -->
                                  <td>
                                    </td>

                            </tr>
                                                
                <tr>
                    <td align="left" colspan="2" class="buttonbg">
                        <table cellpadding="0" cellspacing="0" border="0" id="specimenPageButton" width="100%"> 
                            <tr>
                                <td class="buttonbg">
                                <table><tr><td>
                                    <input type="button" id="specimenSubmitButton" value="Submit" onclick="submitTabData('${requestScope.operation}')" class="blue_ar_b"/>
                                    </td><td>
                                    <div id="specListDiv" style="display:none">
                                    | <input type="button" value="Add To Specimen List"
                                            onclick="organizeTarget()" class="blue_ar_b" />
    
                                    </div>
                                    </td><td>
										<div id="deleteButtonDiv" style="display:none">
          									| <input type="button" value="Dispose"
                                            onclick="showDisableSpecimenWindow()" class="blue_ar_b" />
                                    
                                    </div>
									</td></tr></table>
                                    <input type="checkbox" name="objCheckbox"  id="objCheckbox" style="display:none" value="${specimenDTO.id}" checked/>
                                </td>
                            </tr>
                        </table>
                         <input type="hidden" id="assignTargetCall" name="assignTargetCall" value="giveCall('AssignTagAction.do?entityTag=SpecimenListTag&entityTagItem=SpecimenListTagItem','Select at least one existing list or create a new list.','No specimen has been selected to assign.','${specimenDTO.id}')"/>
                        <%@ include file="/pages/content/manageBioSpecimen/SpecimenTagPopup.jsp" %>
                    </td>
                </tr>

<!-- NEW SPECIMEN REGISTRATION ends-->
                </table>
            </td>
        </tr>
        <tr>
            <td height="*">&nbsp;</td>
        </tr>
        </table>
        </div> </div>
     </td>
   </tr>
</table>
<div id="disposalPop" style="display:none">
	<table width='100%' border='0' cellpadding='5' cellspacing='3'>
		<tr><td colspan="2">
			<div id="pop-error" class="alert alert-error" style="display:none">
                            <strong>Error!</strong> <span id="errorMsg">Change a few things up and try submitting again.</span>
                        </div>
		</td></tr>
		
		<tr><td class='black_ar_b' width='120px'>User</td><td class='black_ar'><html:select property='userId' name='specimenDTO' styleClass='black_ar' styleId='userId' size='1'><html:options collection='userList' labelProperty='name' property='value' /></html:select></td></tr>
				
		<tr><td class='black_ar_b'>Disposal Date</td><td class='black_ar'>   
			<table style='border-collapse:collapse;'><tr><td><input type="text" name="disposalDate" class="black_ar"
                              id="disposalDate" size="10" value='<fmt:formatDate value="${specimenDTO.disposalDate}" pattern="${datePattern}" />'/>
				</td><td  style='padding-left:4px'><html:select property='disposalHours' name='specimenDTO' styleClass='black_ar' styleId='disposalHours' size='1'>
				<logic:iterate name='hourList' id='listhoursId'>
				<html:option  value='${listhoursId}'> ${listhoursId} </html:option>
				</logic:iterate>
				</html:select> 
				</td>
				<td  style='padding-left:4px'>
				<html:select property='disposalMins' name='specimenDTO' styleClass='black_ar' styleId='disposalMins' size='1'>
				<logic:iterate name='minutesList' id='listminutesId'>
				<html:option  value="${listminutesId}"> ${listminutesId} </html:option>
				</logic:iterate>
				</html:select> 
				</td>
				</tr>
				</table>
		</td></tr>
				
		<tr><td class='black_ar_b' width='120px'>Activity Status</td><td class='black_ar aliquot_details aliquot_form_label'><input type='radio' name='aliquotRadio' value='Disabled' style='vertical-align: middle;' checked onclick="activityStatusChange(this)"><span class='black_ar' style='vertical-align: middle;margin-left:2px;'>Disabled</span> &nbsp;
         <input type='radio' name='aliquotRadio' value='Closed' style='vertical-align: middle;' onclick="activityStatusChange(this)"><span class='black_ar' style='vertical-align: middle;margin-left:2px;'>Closed</span></td></tr>
		 
		 <tr><td></td><td class="black_ar" style="color:#FF0000"><div id="disbDiv" style="display:block">Disabling a specimen will delete the specimen forever. Are you sure?</div>
		 
		 </td></tr>
				
				<tr><td class='black_ar_b'>Reason</td><td class='black_ar'><textarea style='height:50px;' class='black_ar' cols='30' rows='10' id='reason' name='reason' ></textarea></td></tr>
				<tr><td></td><td class='black_ar'><input type='button' name='Ok' onClick='submitEvent()' value='Ok' ><input type='button'  value='Cancel' name='Cancel' onClick='closeTermWindow()'style='margin-left:6px'></td></tr>
	</table>
</div>
</html:form>

<script>
var specimenId='${specimenDTO.id}';
var reportId='${identifiedReportId}';
var entityId='${specimenRecordEntryEntityId}';
var staticEntityName='${entityName}';
var participantId = '${specimenDTO.participantID}';
var cprId = '${specimenDTO.cprID}';
var cpId = '${specimenDTO.cpId}';
var userName = '${specimenDTO.userName}';
var selectedDNAMethod = '${specimenDTO.dnaMethod}';
var value_260 = '${specimenDTO.dna260}';


<logic:equal name="operation" value="edit">
var hasConsents = ${hasConsents};
</logic:equal>
<logic:equal name="hideButton" value="true">
document.getElementById('specimenSubmitButton').disabled=true;
</logic:equal>
<logic:equal name="showScgErr" value="true">
	
	document.getElementById('success').style.display='none';
	document.getElementById('errorMsg').innerHTML = '<bean:message key="errors.parent.scg.collect"/>';
	document.getElementById('error').style.display='block';
</logic:equal>
var isImageEnabled = ${isImageEnabled};

var showViewSPRTab="ViewSurgicalPathologyReport.do?operation=viewSPR&pageOf=pageOfNewSpecimenCPQuery&reportId="+reportId+"&id="+specimenId;

var showAnnotationTab="LoadAnnotationDataEntryPage.do?entityId="+entityId+"&entityRecordId="+specimenId+"&pageOf=pageOfNewSpecimenCPQuery&operation=viewAnnotations&staticEntityName="+staticEntityName+"&id="+specimenId;

var showDESpecimenEventsTab="LoadAnnotationDataEntryPage.do?entityId="+entityId+"&entityRecordId="+specimenId+"&pageOf=pageOfNewSpecimenCPQuery&operation=viewAnnotations&staticEntityName=SpecimenEvent&id="+specimenId;

var showConsentsTab="FetchConsents.do?consentLevelId="+specimenId+"&consentLevel=specimen&reportId="+reportId+"&pageof=pageOfNewSpecimenCPQuery&entityId="+entityId+"&staticEntityName="+staticEntityName;

var showImagesTab="EditSpecimenImage.do?id="+specimenId;

// alert(showViewSPRTab);

setLabelBarcodeVisibility('${isSpecimenLabelGeneratorAvl}', '${isSpecimenBarcodeGeneratorAvl}', '${specimenDTO.collectionStatus}','${requestScope.operation}');
doInitCal('createdDate',false,'${uiDatePattern}');
<c:if test="${specimenDTO.lineage=='New' and specimenDTO.type=='Serum' or specimenDTO.type=='Buffy Coat'}">
doInitCal('venesectionDate',false,'${uiDatePattern}');
</c:if>
var nodeId= "Specimen_"+document.getElementById("id").value;
//refreshTree(null,null,null,null,nodeId);
                
var tabDataJSON = {};
var spId = document.getElementById("id").value;
if(spId != null && spId != "")
{
    tabDataJSON["id"] = document.getElementById("id").value; 
}

tabDataJSON["participantID"] =participantId;
tabDataJSON["cprID"] =cprId;
tabDataJSON["cpId"] =cpId;
tabDataJSON["userName"] = userName;

if(document.getElementById("scgId").value!=null && document.getElementById("scgId").value!= ""){
tabDataJSON["specimenCollectionGroupId"] = document.getElementById("scgId").value; 
}

//initialization of clinicalstudytab page
function initialize(startDateObj,endDateObj)
{
    var startDate = calendar.init(startDateObj,"%m-%d-%Y");
    var endDate = calendar.init(endDateObj,"%m-%d-%Y");
}

function loadDHTMLXWindowForTransferEvent()
{
//alert(document.getElementById('barcode'));
var w =700;
var h =450;
var x = (screen.width / 3) - (w / 2);
var y = 0;
var containerName = document.getElementById('containerName').value;
var isVirtual = document.getElementById('isVirtual').value;


if(isVirtual == 'true')
{
containerName='';
}

dhxWins = new dhtmlXWindows(); 
dhxWins.createWindow("containerPositionPopUp", x, y, w, h);
var pos1 = document.getElementById('pos1').value;
var pos2 = document.getElementById('pos2').value;
var className = getClassName();
var type = typeCombo.getSelectedText();



<logic:equal name="operation" value="edit">
var collStatus="<bean:write name='specimenDTO' property='collectionStatus' scope='request'/>";
</logic:equal>
<logic:equal name="operation" value="add">
var collStatus="Pending";
</logic:equal>

var url = "ShowStoragePositionGridView.do?pageOf=pageOfSpecimen&forwardTo=gridView&pos1="+pos1+"&pos2="+pos2+"&holdSpecimenClass="+className+"&holdSpecimenType="+type+"&containerName="+containerName+"&collectionProtocolId=${requestScope.cpId}&collStatus="+collStatus+"&isVirtual="+isVirtual;

dhxWins.window("containerPositionPopUp").attachURL(url);                     
//url : either an action class or you can specify jsp page path directly here
dhxWins.window("containerPositionPopUp").button("park").hide();
dhxWins.window("containerPositionPopUp").allowResize();
dhxWins.window("containerPositionPopUp").setModal(true);
dhxWins.window("containerPositionPopUp").setText("");    //it's the title for the popup
}
initSpecimenCombo();
initializeSpecimenPage('${biohazardTypeNameListJSON}');
prepareSpecimenTypeOptions('${cellTypeListJSON}','${molecularTypeListJSON}','${tissueTypeListJSON}','${fluidTypeListJSON}');

<c:if test="${specimenDTO.collectionStatus=='Collected' and operation=='edit'}">
{
    document.getElementById('specListDiv').style.display='block';
}
</c:if>
<c:if test="${operation=='edit'}">
{
    document.getElementById('deleteButtonDiv').style.display='block';
}
</c:if>

function chkeEmptyNumber(obj)
{
    if(obj.value.trim()=="" || obj.value == null)
    {
        obj.value=0;
    }
}
function openViewMap()
{
//var className=classNameCombo.getSelectedText();
    var sptype=typeCombo.getSelectedText();
    var frameUrl="ShowFramedPage.do?pageOf=pageOfEditSpecimen&selectedContainerName=containerName&pos1=pos1&pos2=pos2&containerId=containerId"
                        + "&holdSpecimenClass="+getClassName()+"&holdSpecimenType="+sptype  + "&holdCollectionProtocol=${requestScope.cpId}&collStatus=<bean:write name='specimenDTO' property='collectionStatus' scope='request'/>";
        mapButtonClickedOnSpecimen(frameUrl,'SpecimenPage',"containerName");
}

function updateHelpURL()
{
	var URL="";
	var activeTab = specimenTabbar.getActiveTab();
	if("annotationTab"==activeTab)
		{
			URL="<%=HelpXMLPropertyHandler.getValue("FormDataEntry")%>";
		}
		else if("eventsTab"==activeTab)
		{
			URL="<%=HelpXMLPropertyHandler.getValue("edu.wustl.catissuecore.actionForm.ListSpecimenEventParametersForm")%>";
		}
		else if("specimenDetailsTab"==activeTab)
		{
			URL="<%=HelpXMLPropertyHandler.getValue("edu.wustl.catissuecore.actionForm.NewSpecimenForm")%>";
		}
		
	return URL;
}

function activityStatusChange(radio){
if(radio.value == 'Closed'){
document.getElementById('disbDiv').style.display ="none";
}else{
document.getElementById('disbDiv').style.display ="block";
}

}

</script>
</body>
