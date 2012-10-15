<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/nlevelcombo.tld" prefix="ncombo" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="java.util.List,java.util.ListIterator"%>
<%@ page import="edu.wustl.catissuecore.util.global.Constants"%>
<%@ page import="edu.wustl.catissuecore.actionForm.CreateSpecimenForm"%>
<%@ page import="edu.wustl.common.beans.NameValueBean"%>
<%@ page import="edu.wustl.catissuecore.util.global.AppUtility"%>
<%@ page import="edu.wustl.common.util.global.CommonServiceLocator"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="edu.wustl.common.util.tag.ScriptGenerator" %>
<%@ page import="edu.wustl.catissuecore.util.global.Variables"%>
<%@ include file="/pages/content/common/SpecimenCommonScripts.jsp" %>
<%@ include file="/pages/content/common/AutocompleterCommon.jsp" %>
<%@ page language="java" isELIgnored="false" %>
<% CreateSpecimenForm form = (CreateSpecimenForm)request.getAttribute("createSpecimenForm");%>
<head>

<link rel="STYLESHEET" type="text/css"	href="dhtmlx_suite/css/dhtmlxcombo.css">
<link rel="stylesheet" type="text/css"	href="dhtmlx_suite/css/dhtmlxtree.css">\
<link rel="STYLESHEET" type="text/css" href="css/dhtmlDropDown.css">
<link rel="STYLESHEET" type="text/css"	href="dhtmlx_suite/ext/dhtmlxgrid_pgn_bricks.css">
<link rel="STYLESHEET" type="text/css"	href="dhtmlx_suite/skins/dhtmlxtoolbar_dhx_blue.css">

<script language="JavaScript" type="text/javascript" src="jss/dhtmlDropDown.js"></script>
<script src="dhtmlx_suite/js/dhtmlxcommon.js"></script>
<script src="dhtmlx_suite/js/dhtmlxcombo.js"></script>
<script src="dhtmlx_suite/js/dhtmlxtree.js"></script>
<script src="dhtmlx_suite/ext/dhtmlxtree_li.js"></script>
<script type="text/javascript" src="dhtmlx_suite/js/dhtmlxgrid.js"></script>
<script type="text/javascript" src="dhtmlx_suite/js/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="dhtmlx_suite/js/connector.js"></script>
<script type="text/javascript" src="dhtmlx_suite/ext/dhtmlxgrid_filter.js"></script>
<script type="text/javascript" src="dhtmlx_suite/ext/dhtmlxgrid_pgn.js"></script>

<link rel="stylesheet" type="text/css" href="dhtmlx_suite/css/dhtmlxwindows.css">
<link rel="stylesheet" type="text/css" href="dhtmlx_suite/skins/dhtmlxwindows_dhx_skyblue.css">
<script src="dhtmlx_suite/js/dhtmlxcontainer.js"></script>
<script src="dhtmlx_suite/js/dhtmlxwindows.js"></script>
<script type="text/javascript" src="dhtmlx_suite/js/dhtmlxtoolbar.js"></script>


<script src="jss/script.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript"	src="jss/javaScript.js"></script>
<script language="JavaScript" type="text/javascript"	src="jss/caTissueSuite.js"></script>
<script src="jss/calendarComponent.js" language="JavaScript"	type="text/javascript"></script>
<script>var imgsrc="images/de/";</script>
<script language="JavaScript" type="text/javascript"	src="javascripts/de/prototype.js"></script>
<script language="JavaScript" type="text/javascript"	src="javascripts/de/scr.js"></script>
<script language="JavaScript" type="text/javascript"	src="javascripts/de/combobox.js"></script>
<script language="JavaScript" type="text/javascript"	src="jss/ext-base.js"></script>
<script language="JavaScript" type="text/javascript"	src="jss/ext-all.js"></script>
<script language="JavaScript" type="text/javascript"	src="javascripts/de/ajax.js"></script>
<script language="JavaScript" type="text/javascript"	src="/jss/multiselectUsingCombo.js"></script>
<LINK href="css/catissue_suite.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/styleSheet.css" />
<link rel="stylesheet" type="text/css"	href="css/clinicalstudyext-all.css" />
<link rel="STYLESHEET" type="text/css"	href="dhtmlx_suite/css/dhtmlxgrid.css">

<script src="jss/script.js"></script>
<script src="jss/calendarComponent.js"></script>
<SCRIPT>var imgsrc="images/";</SCRIPT>
<link href="css/catissue_suite.css" rel="stylesheet" type="text/css" />
<LINK href="css/calanderComponent.css" type=text/css rel=stylesheet>
<script language="JavaScript" type="text/javascript" src="jss/Hashtable.js"></script>
<script language="JavaScript" type="text/javascript" src="jss/createSpecimen.js"></script>



<%

String collectionProtocolId ="";
		if (request.getAttribute(Constants.COLLECTION_PROTOCOL_ID)==null)
			collectionProtocolId="";
		else
		 collectionProtocolId =(String) request.getAttribute(Constants.COLLECTION_PROTOCOL_ID);
String className = form.getClassName();
		String sptype = form.getType();
			if (className==null)
				className="";
String frameUrl="";
%>

<script language="JavaScript">
//declaring DHTMLX Drop Down controls required variables
var containerDropDownInfo, scGrid;
var scGridVisible = false;
var dhxWins;

function initWindow()
{
    dhxWins = new dhtmlXWindows();
    dhxWins.enableAutoViewport(true);
    dhxWins.setImagePath("dhtmlx_suite/imgs/");
    dhxWins.setSkin("dhx_skyblue");
}



function loadDHTMLXWindow()
{
	var w = 400;
    var h = 400;
    var x = (screen.width / 2) - (w / 2);
    var y = 0;
    dhxWins.createWindow("containerPositionPopUp", x, y, w, h);
	var storageContainer =document.getElementById("storageContainerDropDown").value;
    var url = "ShowStoragePositionGridView.do?pageOf=pageOfSpecimen&forwardTo=gridView&pos1=pos1&pos2=pos2&&containerName="+storageContainer;
    dhxWins.window("containerPositionPopUp").attachURL(url);                      //url : either an action class or you can specify jsp page path directly here
    dhxWins.window("containerPositionPopUp").button("park").hide();
    dhxWins.window("containerPositionPopUp").button("minmax1").hide();
    dhxWins.window("containerPositionPopUp").allowResize();
	dhxWins.window("containerPositionPopUp").setModal(true);
    dhxWins.window("containerPositionPopUp").setText("Container Positions");    //it's the title for the popup
}
function showPopUp() 
{
	var storageContainer =document.getElementById("storageContainerDropDown").value;
    if(storageContainer!="")
	{
		loadDHTMLXWindow();
	}
	else
	{
	<%
		 frameUrl = "ShowFramedPage.do?pageOf=pageOfSpecimen&selectedContainerName=storageContainerDropDown&pos1=pos1&pos2=pos2&containerId=containerId"
											+ "&" + Constants.CAN_HOLD_SPECIMEN_CLASS+"="+className
											+ "&" + Constants.CAN_HOLD_SPECIMEN_TYPE+"="+sptype
											+ "&" + Constants.CAN_HOLD_COLLECTION_PROTOCOL +"=" + collectionProtocolId;
	%>
	mapButtonClickedOnNewSpecimen('<%=frameUrl%>','newSpecimenPage');
	}
}

//will be called whenever a participant is selected from the participant grid/dropdown
function containerOnRowSelect(id,ind)
{	
	document.getElementsByName('selectedContainerName')[0].value = id;
	document.getElementById(containerDropDownInfo['dropDownId']).value = scGrid.cellById(id,ind).getValue();
	hideGrid(containerDropDownInfo['gridDiv']);
	scGridVisible = false;
	document.getElementById("pos1").value="";
	document.getElementById("pos2").value="";
}



var gridDivObject ;


function loadGrid()
{
//alert(containerDropDownInfo['actionToDo']+"&containerName="+document.getElementById("storageContainerDropDown").value);
//alert(containerDropDownInfo['callBackAction']);
gridDivObject.load(containerDropDownInfo['actionToDo']+"&containerName="+document.getElementById("storageContainerDropDown").value, containerDropDownInfo['callBackAction']);
}



function setValue(e,gridDivId, dropDownId)
{
		document.getElementById(dropDownId).focus();
		noEventPropogation(e);
}

function showHideStorageContainerGrid(e,gridDivId, dropDownId)
{		
		setValue(e,containerDropDownInfo['gridDiv'], containerDropDownInfo['dropDownId']);
		if(containerDropDownInfo['visibilityStatusVariable'])
		{
			hideGrid(containerDropDownInfo['gridDiv']);
			containerDropDownInfo['visibilityStatusVariable'] = false;
		}
		else 
		 {	
			showGrid(containerDropDownInfo['gridDiv'],containerDropDownInfo['dropDownId']);
			containerDropDownInfo['visibilityStatusVariable'] = true;
			scGrid.load(containerDropDownInfo['actionToDo'],"");
		 }
}


function onContainerListReady()
	{
		var containerName = '${createSpecimenForm.selectedContainerName}';
		//if(containerName != "" && containerName != 0 && containerName != null)
			//containerOnRowSelect(containerName,0);
	}
	
	function setContainerValues()
{
<%if(!"".equalsIgnoreCase(form.getSelectedContainerName())) {%>
	document.getElementById(containerDropDownInfo['dropDownId']).value='${createSpecimenForm.selectedContainerName}';
	document.getElementById("pos1").value='${createSpecimenForm.pos1}';
	document.getElementById("pos2").value='${createSpecimenForm.pos2}';
<%}%>	
}
	


function doOnLoad()
{
var className=document.getElementById("className").value;
var sptype=document.getElementById("type").value;
var collectionProtocolId="<%=collectionProtocolId%>";
//var containerName=document.getElementById("storageContainerDropDown").value;
var url="CatissueCommonAjaxAction.do?type=getStorageContainerList&<%=Constants.CAN_HOLD_SPECIMEN_CLASS%>="
+className+"&specimenType="+sptype+ "&<%=Constants.CAN_HOLD_COLLECTION_PROTOCOL%>=" + collectionProtocolId+"&stContSelection="+"<%=form.getStContSelection()%>";

	//Drop Down components information
	containerDropDownInfo = {gridObj:"storageContainerGrid", gridDiv:"storageContainer", dropDownId:"storageContainerDropDown", pagingArea:"storageContainerPagingArea", infoArea:"storageContainerInfoArea", onOptionSelect:"containerOnRowSelect", actionToDo:url, callBackAction:onContainerListReady,visibilityStatusVariable:scGridVisible, propertyId:'selectedContainerName'};
	// initialising grid
	scGrid = initDropDownGrid(containerDropDownInfo); 
}

</script>
<script language="JavaScript" >

		//Set last refresh time
		if(window.parent!=null)
		{
			if(window.parent.lastRefreshTime!=null)
			{
				window.parent.lastRefreshTime = new Date().getTime();
			}
		}

		function isLabelBarcodeOrClassChange()
		{
					var parentLabelElement = document.getElementById("parentSpecimenLabel");
					var parentBarcodeElement = document.getElementById("parentSpecimenBarcode");
					var classNameElement = document.getElementById("className");

					if((parentLabelElement.value != "-1" || parentBarcodeElement.value != "-1") && classNameElement.value != "-1")
					{
						var action = "${requestScope.actionToCall2}";
						var temp = "${requestScope.frdTo}";
						document.forms[0].action = action + "&isLabelBarcodeOrClassChange=true"+"&forwardTo="+temp;
						document.forms[0].submit();
					}
		}

	  	function onClassOrLabelOrBarcodeChange(multipleSpecimen,element)
		{
			if(multipleSpecimen == "1")
			{
			   classChangeForMultipleSpecimen();
			}
		    var radioArray = document.getElementsByName("radioButton");
		 	var flag = "0";
 			if (radioArray[0].checked)
			{
			  if(document.getElementById("parentSpecimenLabel").value!= "")
			  {
				   flag = "1";
			  }
			}
		     else
		     {
				if (document.getElementById("parentSpecimenBarcode").value != "")
				{
	     	    	 flag = "1";
				}
			}
 	    	var classNameElement = document.getElementById("className");
			if(flag=="1" && classNameElement.value != "-1")
			{
				document.forms[0].action = "${requestScope.actionToCall1}";
				document.forms[0].submit();
			}
			else
			{
				alert("Please enter Parent Label/Barcode and Specimen Class");
				element.checked=true;
			}
		}

		function deleteExternalIdentifiers()
		{
			${requestScope.deleteChecked}
		}

		function onNormalSubmit()
		{
			var checked = document.forms[0].aliCheckedButton.checked;
			var actionToCall='${requestScope.actionToCall}'+"&pageOf=pageOfCreateDerivative";
			if(checked)
			{
				setSubmitted('ForwardTo','${requestScope.printAction}','pageOfCreateAliquot');
				confirmDisable(actionToCall,document.forms[0].activityStatus);
			}
			else
			{
				var temp = "${requestScope.frdTo}";
				if(temp == "orderDetails")
				{
					setSubmitted('ForwardTo','${requestScope.printAction}','orderDetails');
			     }
			     else
			    {
				   setSubmitted('ForwardTo','${requestScope.printAction}','newSpecimenEdit');
			     }
				confirmDisable(actionToCall,document.forms[0].activityStatus);
			}
		}

		function onAddToCart()
		{

			var printFlag = document.getElementById("printCheckbox");
			var actionToCall='${requestScope.actionToCall}'+"&pageOf=pageOfCreateDerivative";
			if(printFlag.checked)
		    {
				setSubmittedForAddToMyList("ForwardTo",'addSpecimenToCartAndPrint','pageOfNewSpecimen');
		    }
            else
            {
               // setSubmittedForAddToMyList("ForwardTo",'addSpecimenToCartForwardtoDerive','success');
            	setSubmittedFor("ForwardTo","addSpecimenToCart");
            }
			confirmDisable(actionToCall,document.forms[0].activityStatus);

		}

		</script>
	<logic:equal name="showRefreshTree" value="true">
		<script language="javascript">
			${requestScope.refreshTree}
		</script>
	</logic:equal>
	</head>

<body onload="doOnLoad();initWindow();setContainerValues()">
<!-- Code was outside body start -->
	<html:form action="${requestScope.action}">
		<input type="hidden" id="specimenAttributeKey" name="specimenAttributeKey" value="${requestScope.specimenAttributeKey}" />
		<input type="hidden" id="derivedSpecimenCollectionGroup" name="derivedSpecimenCollectionGroup" value="${requestScope.derivedSpecimenCollectionGroup}" />
		<input type="hidden" id="rowSelected" name="rowSelected" value="${requestScope.rowSelected}" />
		<table summary="" cellpadding="0" cellspacing="0" border="0"  width="100%">
			<tr>
				<td class="td_color_bfdcf3">
					<table summary="" cellpadding="3" cellspacing="0" border="0" width="100%">
					<logic:notEmpty name="dataList">
						<tr>
							<td width="100%" align="right">
								<html:button property="addNewDerived" styleClass="actionButton" onclick="onAddNewButtonClicked();">
									<bean:message key="buttons.addNew"/>
								</html:button>
							</td>
						</tr>
						<tr>
							<td class="formTitle" height="20" >
								Derived Specimens For This Parent Specimen
							</td>
						</tr>
						<tr>
							<td>
								<script>
								var specimenAttributeKey = document.getElementById("specimenAttributeKey");
								if(specimenAttributeKey!=null)
								{
								parent.window.opener.document.applets[0].setButtonCaption(specimenAttributeKey.value,"");
								}

								function derivedSpecimenGrid(id)
								{
								var cl = mygrid.cells(id,4);
								var rowSelected = cl.getValue();
								var c2 = mygrid.cells(id,0);
								var eventId = c2.getValue();
								var url = "NewMultipleSpecimenAction.do?method=showDerivedSpecimenDialog&rowSelected=" + rowSelected +"&specimenAttributeKey="+document.getElementById("specimenAttributeKey").value;
								document.forms[0].action = url;
								document.forms[0].submit();
								}
								var useDefaultRowClickHandler =2;
								var useFunction = "derivedSpecimenGrid";
								var gridFor="derivedSpecimen";
								</script>
								<%@ include file="/pages/content/search/GridPage.jsp" %>
							</td>
						</tr>
						<tr>
							<td width="100%" align="right">
								<html:button property="closebutton" styleClass="actionButton" onclick="closeWindow();">
									<bean:message key="buttons.close"/>
								</html:button>
							</td>
						</tr>
					</logic:notEmpty>	<%-- datalist not empty. to check if this block is required any more --%>
					</table>
				</td>
			</tr>
		</table>
<!-- code outside body end -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="maintable" >
			<tr>
				<td>
					<logic:notEqual name="<%=Constants.PAGE_OF%>" value="<%=Constants.PAGE_OF_CREATE_SPECIMEN_CP_QUERY%>">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="td_table_head">
								<span class="wh_ar_b"><bean:message key="specimen.derived.title"/> </span>
							</td>
							<td>
								<img src="images/uIEnhancementImages/table_title_corner2.gif" alt="Page Title - Specimen Derive" width="31" height="24"/>
							</td>
						</tr>
					</table>
					</logic:notEqual>
				</td>
			</tr>
<!-- Mandar13Nov08 row containing actual data -->
			<tr>
				<td class="tablepadding">
					<logic:empty name="showDerivedPage">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="4%" class="td_tab_bg">
								<img src="images/spacer.gif" alt="spacer" width="50" height="1">
							</td>

							<logic:notEqual name="<%=Constants.PAGE_OF%>" value="<%=Constants.PAGE_OF_CREATE_SPECIMEN_CP_QUERY%>">
							<td valign="bottom"><html:link page="/SimpleQueryInterface.do?pageOf=pageOfNewSpecimen&aliasName=Specimen"><img src="images/uIEnhancementImages/tab_edit_user.jpg"  border="0"alt="Edit" width="59" height="22" /></html:link></td>
							<td valign="bottom"><img src="images/uIEnhancementImages/tab_derive.gif" alt="Derive" width="56" height="22" border="0" /></td>
							 <td valign="bottom"><html:link page="/Aliquots.do?pageOf=pageOfAliquot"><img src="images/uIEnhancementImages/tab_aliquot2.gif" alt="Aliquot" width="66" height="22" /></html:link></td>
							<td valign="bottom"><html:link page="/QuickEvents.do?operation=add"><img src="images/uIEnhancementImages/tab_events2.gif" alt="Events" width="56" height="22" border="0"/></html:link></td>
							<td align="left" valign="bottom" class="td_color_bfdcf3" ><html:link page="/MultipleSpecimenFlexInitAction.do?pageOf=pageOfMultipleSpWithMenu"><img src="images/uIEnhancementImages/tab_multiple2.gif" alt="Multiple" width="66" height="22" border="0" /></html:link></td>
							</logic:notEqual>

							<logic:equal name="<%=Constants.PAGE_OF%>" value="<%=Constants.PAGE_OF_CREATE_SPECIMEN_CP_QUERY%>">
							<td valign="bottom">
								<img src="images/uIEnhancementImages/tab_derive.gif" alt="Derive" width="56" height="22" border="0"/>
							</td>
							</logic:equal>
							<td width="90%" align="left" valign="bottom" class="td_tab_bg">&nbsp;</td>
						</tr>
						<html:hidden property="${requestScope.oper}" value="${requestScope.operation}"/>
						<html:hidden property="submittedFor" value="ForwardTo"/>
						<html:hidden property="forwardTo" value="${requestScope.frdTo}"/>
						<html:hidden property="multipleSpecimen" value="${requestScope.multipleSpecimen}"/>
						<html:hidden property="containerId" styleId="containerId"/>
						<html:hidden property="generateLabel" />
						<html:hidden property="nextForwardTo" />
						 <html:hidden property="virtuallyLocated" styleId="virtuallyLocated"/>

						<html:hidden property="positionInStorageContainer" />

						<logic:equal name="pageOf" value="${requestScope.query}">
							<html:hidden property="sysmtemIdentifier"/>
						</logic:equal>
					</table>
<!-- Mandar 13Nov08 -->
					<table width="100%"  border="0" cellpadding="3" cellspacing="0" class="whitetable_bg">
						<tr>
							<td colspan="3" align="left" class="bottomtd"><%@ include file="/pages/content/common/ActionErrors.jsp" %></td>
						</tr>
						<tr>
							<td colspan="3" align="left" class="tr_bg_blue1">
								<span class="blue_ar_b">&nbsp;<bean:message key="parent.specimen.details.label"/></span>
							</td>
						</tr>
						<tr>
							<td colspan="3" align="left" valign="top" class="showhide">
								<table width="100%" border="0" cellpadding="3" cellspacing="0">
									<tr>
										<td width="1%" align="center" class="black_ar">
											<span class="blue_ar_b"><img src="images/uIEnhancementImages/star.gif" alt="Mandatory Field" width="6" height="6" hspace="0" vspace="0" /></span>
										</td>
										<td width="17%" align="left" class="black_ar">
											<bean:message key="parent.specimen.details.label"/>
										</td>
										<td colspan="4" align="left" valign="middle" nowrap>
											<table width="55%" border="0" cellspacing="0" cellpadding="0" >
												<tr class="groupElements">
													<td valign="middle" nowrap>
														<html:radio styleClass="" styleId="checkedButton" property="radioButton" value="1"
														onclick="onRadioButtonClick(this)">
														</html:radio>
														<span class="black_ar">
															<bean:message key="specimen.label"/>&nbsp;
															<logic:equal name="createSpecimenForm" property="radioButton" value="1">
															<html:text styleClass="black_ar"  maxlength="50"  size="20" styleId="parentSpecimenLabel"
															property="parentSpecimenLabel" disabled="false" />
															</logic:equal>
															<logic:equal name="createSpecimenForm" property="radioButton" value="2">
															<html:text styleClass="black_ar"  maxlength="50"  size="20" styleId="parentSpecimenLabel"
															property="parentSpecimenLabel" disabled="true" />
															</logic:equal>
															&nbsp;&nbsp;
														</span>
													</td>
													<td align="left" valign="middle" nowrap="nowrap">
														<html:radio styleClass="" styleId="checkedButton" property="radioButton" value="2" 		onclick="onRadioButtonClick(this)">
														</html:radio>
														<span class="black_ar">
														<bean:message key="storageContainer.barcode"/>&nbsp;
														<logic:equal name="createSpecimenForm" property="radioButton" value="1">
														<html:text styleClass="black_ar"  maxlength="50"  size="20" styleId="parentSpecimenBarcode"
														property="parentSpecimenBarcode" disabled="true" />
														</logic:equal>
														<logic:equal name="createSpecimenForm" property="radioButton" value="2">
														<html:text styleClass="black_ar"  maxlength="50"  size="20" styleId="parentSpecimenBarcode"
														property="parentSpecimenBarcode" disabled="false" />
														</logic:equal>
														</span>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td align="center" class="black_ar">
											<span class="blue_ar_b"><img src="images/uIEnhancementImages/star.gif" alt="Mandatory Field" width="6" height="6" hspace="0" vspace="0" /></span>
										</td>
										<td align="left" class="black_ar">
											<bean:message key="specimen.type"/>
										</td>
										<td class="black_ar" align="left">
											<autocomplete:AutoCompleteTag property="className"
											optionsList = "${requestScope.specClassList}"
											styleClass="black_ar"
											size="27"
											initialValue=""
											onChange="onTypeChange(this)"
											readOnly="${requestScope.readOnlyForAll}" />
										</td>
										<td width="1%" align="center">
											<span class="blue_ar_b"><img src="images/uIEnhancementImages/star.gif" alt="Mandatory Field" width="6" height="6" hspace="0" vspace="0" /></span>
										</td>
										<td width="17%" align="left">
											<span class="black_ar"><bean:message key="specimen.subType"/></span>
										</td>
											<td align="left" class="black_ar" >
											<div id="specimenTypeId">
											<autocomplete:AutoCompleteTag property="type"
											styleClass="black_ar"
											size="27"
											optionsList = "${requestScope.specimenTypeMap}"
											initialValue=""
											onChange="onSubTypeChangeUnit('className',this,'unitSpan')"
											readOnly="false"
											dependsOn="${createSpecimenForm.className}"  />
											</div>
										</td>
									</tr>
									<tr>

									<%
									if(!form.isGenerateLabel())
									{
									%>
										<td align="center" class="black_ar">
										<logic:notEqual name="oper" value="${requestScope.view}">
											<span class="blue_ar_b"><img src="images/uIEnhancementImages/star.gif" alt="Mandatory Field" width="6" height="6" hspace="0" vspace="0" />
											</span>
										</logic:notEqual>
										</td>
										<td align="left" class="black_ar">
											<bean:message key="specimen.label"/>
										</td>
										<td class="black_ar" align="left">
											<html:text styleClass="black_ar" size="30" maxlength="50"  styleId="label" property="label" readonly="${requestScope.readOnlyForAll}"/>
										</td>
										<%}%>
<%-- if(!Variables.isSpecimenLabelGeneratorAvl) --%>
									<logic:notEqual name="isSpecimenBarcodeGeneratorAvl" value="true">
										<td width="1%" align="center">
											<span class="blue_ar_b"></span>
										</td>
										<td width="17%" align="left">
											<span class="black_ar"><bean:message key="specimen.barcode"/></span>
										</td>
										<td align="left" class="black_ar">
											<html:text styleClass="black_ar"  maxlength="50" size="30" styleId="barcode" property="barcode" readonly="${requestScope.readOnlyForAll}"/>
										</td>
									</logic:notEqual> <!-- if(!Variables.isSpecimenBarcodeGeneratorAvl ) -->
									</tr>
									<tr>
										<td align="center" class="black_ar">&nbsp;</td>
										<td align="left" class="black_ar"><bean:message key="specimen.concentration"/></td>
										<td align="left">
											<span class="grey_ar">
											<logic:equal name="unitSpecimen" value="${requestScope.UNIT_MG}">
												<html:text styleClass="black_ar" size="10" styleId="concentration" style="text-align:right" property="concentration"
												readonly="${requestScope.readOnlyForAll}" disabled="false"/>
												<bean:message key="specimen.concentrationUnit"/>
											</logic:equal>	<!-- if(unitSpecimen.equals(Constants.UNIT_MG)) -->
											<logic:notEqual name="unitSpecimen" value="${requestScope.UNIT_MG}">
												<html:text styleClass="black_ar" size="10" maxlength="10"  styleId="concentration"
												property="concentration" readonly="${requestScope.readOnlyForAll}" style="text-align:right" disabled="false"/>
												<bean:message key="specimen.concentrationUnit"/>
											</logic:notEqual>
											</span>
										</td>
										<td align="left">
											<span class="blue_ar_b"><img src="images/uIEnhancementImages/star.gif" alt="Mandatory Field" width="6" height="6" hspace="0" vspace="0" /></span>
										</td>
										<td align="left">
											<span class="black_ar"><bean:message key="specimen.quantity"/></span>
										</td>
										<td align="left">
											<html:text styleClass="black_ar" size="8" maxlength="10"  styleId="quantity" property="quantity"
											readonly="${requestScope.readOnlyForAll}" style="text-align:right"/>
											<span id="unitSpan" class="black_ar">${requestScope.unitSpecimen}</span>
											<html:hidden property="unit"/>
										</td>
									</tr>
									<tr>
										<td align="center" class="black_ar">&nbsp;</td>
										<td align="left" class="black_ar">
											<bean:message key="specimen.createdDate"/>
										</td>
										<td align="left">
											<ncombo:DateTimeComponent name="createdDate"
											id="createdDate"
											formName="createSpecimenForm"
											pattern="<%=CommonServiceLocator.getInstance().getDatePattern()%>"
											value='${requestScope.createdDate}'
											styleClass="black_ar" />
											<span class="grey_ar_s">
												<bean:message key="page.dateFormat"/>
											</span>
										</td>
										<td align="left">&nbsp;</td>
										<td align="left">&nbsp;</td>
										<td align="left">&nbsp;</td>
									</tr>
									<logic:notEqual name="multipleSpecimen" value="1">	<!-- to verify for valid case 2 -->
									<tr height="33">
										<td align="center" class="black_ar">
											<span class="blue_ar_b"><img src="images/uIEnhancementImages/star.gif" alt="Mandatory Field" width="6" height="6" hspace="0" vspace="0" /></span>
										</td>
										<td align="left" class="black_ar"><bean:message key="specimen.positionInStorageContainer"/>
										</td>
										<script language="JavaScript" type="text/javascript" src="jss/CustomListBox.js"></script>
										<td class="black_ar" align = "left" colspan="4">
											<logic:equal name="${requestScope.oper}" value="${requestScope.ADD}">
											<table cellpadding="0" cellspacing="0" border="0" >
						<tr>
							<td class="groupelements" size="48">
								
								<td width="50%" align="left" class="black_ar">
						<html:hidden property="selectedContainerName" styleId="selectedContainerName" />
						<div>
							<table border="0" width="29%" id="outerTable2" cellspacing="0" cellpadding="0">
								<tr>
									<td align="left" width="88%" height="100%" >
										<div id="scDropDownIddiv" class="x-form-field-wrap" >
											<input id="storageContainerDropDown"
													onkeydown="keyNavigation(event,containerDropDownInfo,scGrid,scGridVisible);"
													onKeyUp="autoCompleteControl(event,containerDropDownInfo,scGrid);"
													onClick="noEventPropogation(event)"
													autocomplete="off"
													size="30"
													class="black_ar_new x-form-text x-form-field x-form-focus"/><img id="scDropDownId" style="top : 0px !important;" class="x-form-trigger x-form-arrow-trigger" 
												onclick="showHideStorageContainerGrid(event,'storageContainer','storageContainerDropDown');"
												src="images/uIEnhancementImages/s.gif"/>
										</div>
									</td>
								</tr>
								<tr>
									<td>
									<div id="storageContainer" style="z-index: 100"
										onClick="noEventPropogation(event)">
									<div id="storageContainerGrid" style="height: 40px;"
										onClick="noEventPropogation(event)"></div>
									<div id="storageContainerPagingArea" onClick="noEventPropogation(event)"></div>
									<div id="storageContainerInfoArea" onClick="noEventPropogation(event)"></div>
									</div>
									</td>
								</tr>
							</table>
					</td>
					</td>

							</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td class="groupelements"  width="10%">
								<html:text styleClass="black_ar"  size="2" styleId="pos1" property="pos1"  disabled= "false" style="display:block"/>
							</td>
							<td class="groupelements" width="10%">
								<html:text styleClass="black_ar"  size="2" styleId="pos2" property="pos2" disabled= "false" style="display:block"/>
							</td>
							<td class="groupelements">
								<html:button styleClass="black_ar" property="containerMap" onclick="showPopUp()">
											<bean:message key="buttons.map"/>
								</html:button>
							</td>
						</tr>
					</table>
											</logic:equal>
										</td>
									</tr>
									</logic:notEqual>	<%-- if(!multipleSpecimen.equals("1")) case 2 --%>
									<logic:equal name="exceedsMaxLimit" value="true">
									<tr>
										<td colspan="3">
										<bean:message key="container.maxView"/>
										</td>
									</tr>
									</logic:equal>
									<tr>
										<td align="center" class="black_ar">&nbsp;</td>
										<td align="left" valign="top" class="black_ar_t"><bean:message key="specimen.comments"/></td>
										<td colspan="4" align="left"><html:textarea styleClass="black_ar" cols="67" rows="4" styleId="comments" property="comments"
										readonly="${requestScope.readOnlyForAll}"/></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr onclick="javascript:showHide('add_ei')">
							<td colspan="2" align="left" class="tr_bg_blue1">
								<span class="blue_ar_b">&nbsp;<bean:message key="specimen.externalIdentifier"/></span>
							</td>
							<td width="10%" align="right" class="tr_bg_blue1">
								<a href="#" id="imgArrow_add_ei"><img src="images/uIEnhancementImages/dwn_arrow1.gif" width="80" height="9" hspace="10" border="0" alt="Show Details" /></a>
							</td>
						</tr>
						<tr>
							<td colspan="3" class="showhide1">
								<div id="add_ei" style="display:none">
								<table width="100%" border="0" cellspacing="0" cellpadding="3">
									<tr class="tableheading">
										<td width="8%" align="left" class="black_ar_b">
											<bean:message key="app.select"/>
										</td>
										<td width="25%" align="left" class="black_ar_b">
											<bean:message key="externalIdentifier.name"/>
										</td>
										<td width="65%" class="black_ar_b">
											<bean:message key="externalIdentifier.value"/>
										</td>
									</tr>
									<tbody id="addExternalIdentifier">
										<html:hidden property="exIdCounter"/>
										<logic:iterate id="xi" name="exIdList">
										<tr>
											<td align="left" class="black_ar">
												<input type=checkbox name="${xi.check}" id="${xi.check}" ${xi.exCondition} onClick="enableButton(document.forms[0].deleteExId,document.forms[0].exIdCounter,'chk_ex_')">
											</td>
											<html:hidden property="${xi.exIdentifier}" />
											<td class="black_ar">
												<label>
													<html:text styleClass="black_ar" maxlength="255"  styleId="${xi.exName}" property="${xi.exName}" size="25" />
												</label>
											</td>
											<td class="black_ar">
												<html:text styleClass="black_ar" maxlength="255"  styleId="${xi.exValue}" property="${xi.exValue}" size="15"/>
											</td>
										</tr>
										</logic:iterate>
									</tbody>
									<tr>
										<td colspan="3" align="left" class="black_ar" >
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td><html:button property="addExId" styleClass="black_ar" styleId="addExId"		onclick="insExIdRow('addExternalIdentifier')">
													<bean:message key="buttons.addMore"/>
													</html:button>&nbsp; <html:button property="deleteExId" styleClass="black_ar" onclick='${requestScope.delExtIds}' disabled="true">
													<bean:message key="buttons.delete"/>
													</html:button>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="3" valign="middle" class="bottomtd"></td>
						</tr>
						<tr>
							<td colspan="3" valign="middle" class="tr_bg_blue1">
								<span class="blue_ar_b">&nbsp;<bean:message key="cpbasedentry.aliquots"/></span>
							</td>
						</tr>
						<tr>
						<logic:notEqual name="multipleSpecimen" value="1">	<!-- to verify for valid case 3 -->
							<logic:notEqual name="pageOf" value="${requestScope.query}">
								<td colspan="3" valign="middle">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="35%" class="black_ar">
												<input type="checkbox" name="aliCheckedButton" onclick="onCheckboxButtonClick(this)" />
												<bean:message key="specimen.aliquot.message"/>
											</td>
											<td width="25%" class="black_ar">
												<bean:message key="aliquots.noOfAliquots"/>
												<input type="text" id="noOfAliquots" name="noOfAliquots" class = "formFieldSized5" style="text-align:right"disabled="true" />
											</td>
											<td width="40%" class="black_ar">
												<bean:message key="specimenArrayAliquots.qtyPerAliquot"/>
												<input type="text" id="quantityPerAliquot" name="quantityPerAliquot" class = "formFieldSized5" disabled="true" style="text-align:right"/>
											</td>
										</tr>
									</table>
								</td>
							</logic:notEqual>
						</logic:notEqual>
						</tr>
						<tr>
							<td colspan="3" class="formLabelNoBackGround" width="40%">
								<span class="black_ar">
								<html:checkbox property="disposeParentSpecimen">
								&nbsp;<bean:message key="aliquots.disposeParentSpecimen" />
								</html:checkbox>
								</span>
							</td>
						</tr>
						 <tr>
			<tr>
								<td colspan="3" align="left" class="dividerline">
								<span class="black_ar">
								</td>
								</tr>
								<tr>
								<td colspan="3" valign="middle">
								<table>
								 <tr>
								  <td  nowrap  width="20%" colspan="1">
								   <logic:notEqual name="<%=Constants.PAGE_OF%>" value="<%=Constants.QUERY%>">
								 		<html:checkbox styleId="printCheckbox" property="printCheckbox" value="true" onclick="showPriterTypeLocation()">
										<span class="black_ar">
											<bean:message key="print.checkboxLabel"/>
										</span>
										</html:checkbox>
									</logic:notEqual>
                                 </td>

	<!--  Added for displaying  printer type and location -->
							    <td colspan="2">
					   			   <%@ include file="/pages/content/common/PrinterLocationTypeComboboxes.jsp" %>
			 				    </td>
							    </tr>
								</table>
        					   </td>
							</tr>
			<!--  End : Displaying   printer type and location -->

						<tr>
							<td colspan="3" class="bottomtd"></td>
						</tr>
						<logic:notEqual name="${requestScope.oper}" value="${requestScope.view}">
						<tr>
							<td colspan="3" align="left" class="buttonbg">
							<logic:notEqual name="multipleSpecimen" value="1">	<!-- to verify for valid case 4 -->
								<html:button styleClass="blue_ar_b" property="submitButton" onclick="updateStorageContainerValue();onNormalSubmit()">
									<bean:message key="buttons.create"/>
								</html:button>&nbsp;|&nbsp;
								<html:button styleClass="blue_ar_c" property="moreButton" 		title="${requestScope.SPECIMEN_BUTTON_TIPS}"			value="${requestScope.SPECIMEN_FORWARD_TO_LIST}" onclick="${requestScope.addMoreSubmit}" >
								</html:button>&nbsp;|&nbsp;
								<html:submit styleClass="blue_ar_c"  styleId="addToCart" onclick="onAddToCart()">
									<bean:message key="buttons.addToCart"/>
								</html:submit>
								</logic:notEqual> <!-- to verify for valid case 4 -->
							<logic:equal name="multipleSpecimen" value="1">
								<html:submit styleClass="blue_ar_b" onclick="javaScript:${requestScope.changeAction3}">
								<bean:message key="buttons.submit"/>
									</html:submit>
							</logic:equal>
							</td>
						</tr>
						</logic:notEqual>
						<tr height="*">
							<td>&nbsp;</td>
						</tr>
					</table>
					</logic:empty> <%-- if(request.getAttribute("showDerivedPage")==null) --%>
				</td>
			</tr>
		</table>
	</html:form>
<script language="JavaScript" type="text/javascript">
showPriterTypeLocation();

function updateStorageContainerValue()
	{
		var containerName=document.getElementById(containerDropDownInfo['dropDownId']).value;
		document.getElementById("selectedContainerName").value=containerName;
		if("Virtual"==containerName)
		{
			document.getElementById("virtuallyLocated").value="true";
		}
		else
		{
			document.getElementById("virtuallyLocated").value="false";
		}
	}
</script>
</body>