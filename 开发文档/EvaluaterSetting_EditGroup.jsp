<%
//$Id: EvaluaterSetting_EditGroup.jsp 6809 2010-10-12 07:58:11Z acsyamaguchi.k $
//$Revision: 6809 $
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<%--  �G���[�y�[�W���` --%>
<%@page language="java" errorPage="/jsp/pages/Hrm_pi_ErrorPage.jsp"
	contentType="text/html;charset=Windows-31J"%>
<%
	/*==============================================================================
	 *	system		Progress@Site HR
	 *	version		Ver3.7.3
	 *	id			EvaluaterSetting_EditGroup.jsp
	 *  title		�O���[�v�ҏW���
	 *	author		Y.Moriai
	 *	create		2007/03/06
	 *				�X�V��		�X�V��			�X�V���e
	 *	update		2008/01/28  H.kawabata      #339 �L�����Z���{�^����������Confirm���b�Z�[�W������𐶂ނ̂ō폜
	 *              2009/12/21	isoltakahashi	#���Ή�	�h������l�A��������l�A�x���o�Όx���l�̍��ڗ���ǉ�
	 *				2010/03/05	isolshoji.y		#2527 �C���g���}�[�g�̌����ڑΉ�
	 *				2010/03/18	isolshoji.y		#2571 �u�O���[�v�v�̕������u�g�D�v�ɕύX
	 *				2010/10/08	yamaguchi.k		#1412 ���Όx���l�̐ݒ肪��ԘA�g�����s����Ə�����(Ajax�o�R������ʏ�T�u�~�b�g�ɏC��)
	 *				2010/12/02  ogawa           tmd#37 �h�����������R�����g�A�E�g��
	 *				2010/12/21  ogawa           tmd#62 �O���[�v�쐬�E�O���[�v�ҏW������DB��
	 *				2011/01/31	yamaguchi.k		tmd#142 ���`�F�b�N�̃��b�Z�[�W�C��
	 *				2011/02/17	yamaguchi.k		tmd#159 ��ʕ\���������ɃE�B���h�E�X�N���[���̈ʒu�����ݒ菈����ǉ�
	 *				2019/07/02	Ns.chou		    ------- �����ƔN���̃I�����W�A�s���N�ݒ�A���Ύ��т̕��������ώ��ԁF�ԐF�̐ݒ��ǉ�����
	 *	remark
	 =============================================================================*/
%>
<%-- �L���b�V���̃R���g���[�� --%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%@page import="ps.c01.tmg.EvaluaterSetting.EvaluaterSettingConst"%>
<%@page import="ps.c01.tmg.EvaluaterSetting.EvaluaterSettingBean"%>
<%@page import="ps.c01.tmg.util.TmgUtil"%>
<%@page import="java.util.*"%>
<%--  �g�p����Bean��錾 --%>
<jsp:useBean id="EvaluaterSettingBean" type="ps.c01.tmg.EvaluaterSetting.EvaluaterSettingBean" scope="request" />
<%-- �^�O���C�u�����̐錾 --%>
<%@taglib uri="/WEB-INF/tlds/custom.tld" prefix="prop"%>
<%-- �A�v���P�[�V������ --%>
<prop:getPropertyAll prop="ps.c01.tmg.EvaluaterSetting.EvaluaterSetting" />
<%
	request.setAttribute("Application",
			"ps.c01.tmg.EvaluaterSetting.EvaluaterSetting");
%>
<%!// ���ʃZ�b�g�ԍ�
	private final int IDX_GROUPNAME = 0; // �O���[�v����%>
<%
	EvaluaterSettingBean bean = EvaluaterSettingBean;

	// ��ʑ������擾
	Hashtable hash = (Hashtable) application.getAttribute("AppObject");
	String custId = "";
	custId = (String) ((Hashtable) session.getAttribute("UserData")).get("CustID");
	ps.util.Customize customize = null;
	customize = (ps.util.Customize) hash.get("Customize");
	String bgcolor = customize.getCustomizeProperty("01", "bk_color");//�w�i�F
	String sMainHeaderBgColor = customize.getMainHeaderBgColor(custId);//���C���^�C�g���w�i�F
	String sHeaderFirstBgColor = customize.getHeaderFirstBgColor(custId);//�w�b�_�w�i�F1
	String sHeaderSecondBgColor = customize.getHeaderSecondBgColor(custId);//�w�b�_�w�i�F2
	String sResultDisplayBgColor = customize.getResultDisplayBgColor(custId);//���ʔw�i�F

	String sContextPath = request.getContextPath();

	// ���ߋΖ������l�ҏW���ڂ̕ҏW�\���ڂ̗L�����m�F
	boolean bEditOvertimeAnyItem = false;
	if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTLY_01_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTLY_02_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTLY_03_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTLY_04_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTLY_05_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_YEARLY_01_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_YEARLY_02_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_YEARLY_03_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_YEARLY_04_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_YEARLY_05_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTHLY_COUNT_MGD, 0).equals("TMG_ONOFF|1")
            || bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_DAILY_01_MGD, 0).equals("TMG_ONOFF|1")) {
		bEditOvertimeAnyItem = true;
	}

	// �x���Ζ������l�ҏW���ڂ̕ҏW�\���ڂ̗L�����m�F
	boolean bEditHolidaytimeAnyItem = false;
	if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_01_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_02_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_03_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_04_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_05_MGD, 0).equals("TMG_ONOFF|1")) {
		bEditHolidaytimeAnyItem = true;
	}

	// �I���O���[�v���g�D����̃O���[�v�ł��邩�m�F
	boolean bEditGroupName = false;
	if (!bean.getEvaluaterSettingParam().getGroup().equals(
			bean.getEvaluaterSettingParam().getRootGroup())) {
		bEditGroupName = true;
	}
	boolean bEditGroupAnyItem = false;
	if (bEditGroupName || bEditOvertimeAnyItem || bEditHolidaytimeAnyItem) {
		bEditGroupAnyItem = true;
	}
%>
<head>
<title><%--�����ݒ�--%><%=PROP_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=Shift_JIS">
<meta http-equiv="content-style-type" content="text/css">
<link type="text/css" rel="stylesheet"
	href="<%=sContextPath%>/jsp/pages/tmg/util/TmgCommon.css">
	<prop:getCssLink />
	<%@include file="../util/TmgImCss.jsp" %>
<%@include file="../util/TmgAjaxErrorCheck.jsp"%>
<script type="text/javascript" src="<%=sContextPath%>/jsp/pages/tmg/util/jquery-1.3.2.js" charset="Shift_JIS"></script>
<script type="text/javascript" src="<%=sContextPath%>/jsp/pages/CommonFunc.js"></script>
<script type="text/javascript">

	var gbDoubleClick   = false; // �{�^���Q�x�ł��h�~�p�t���O

	<%-- �ύX --%>
	function updateGroup() {
		if( !errorCheck() ) {
			// �G���[������ΏI������B
			return;
		}
		if( !confirm("<%=PROP_MSG_EDITSECTION%>") ) {
			return;
		}
		if (document.fm.postcount){
			// �폜�`�F�b�N�{�b�N�X(hidden����)�̐ݒ�
			for (i = 0; i < Number(document.fm.postcount.value); i++) {
				if (document.getElementById('chkpost'+i).checked) {
					document.getElementById("txtChkpost"+i).value = "true";
				} else {
					document.getElementById("txtChkpost"+i).value = "false";
				}
			}
		}
		document.fm.txtAction.value = "<%=EvaluaterSettingConst.ACT_EDITGROUP_UGROUP%>";
	    document.fm.target = "_self";
		document.fm.submit();
	}

	<%-- �L�����Z�� --%>
	function cancel(){
		// 2008/01/28 H.kawabata   #339 �L�����Z���{�^����������Confirm���b�Z�[�W������𐶂ނ̂ō폜
		// if( !confirm("<%=PROP_MSG_CANCEL%>") ) { return; }
		document.fm.txtAction.value = "<%=EvaluaterSettingConst.ACT_DISP_REVALLIST%>";
	    document.fm.target = "_self";
		document.fm.submit();
	}

	<%-- �폜--%>
	function deleteGroup(){
		if( !confirm("<%=PROP_MSG_DELETESECTION%>") ) { return; }
		document.fm.txtAction.value = "<%=EvaluaterSettingConst.ACT_EDITGROUP_DGROUP%>";
	    document.fm.target = "_self";
		document.fm.submit();
	}

	<%-- �G���[�`�F�b�N --%>
	function errorCheck(){
		if (document.fm.txtGroupName) {
			<%-- �O���[�v���̂������� --%>
			if( document.fm.txtGroupName.value == "" ) {
				alert("<%=PROP_ERROR_SECTION_INPUT%>");
				document.fm.txtGroupName.focus();
				return false;
			}
			<%-- �O���[�v���̂�100�����𒴂��Ă��� --%>
			if( getLengthB(document.fm.txtGroupName.value) > '<%=PROP_ALERT_MSG_ERR_HAN_LIMIT%>' ) {
				alert(
					replaceMessage(
							'<%=TmgUtil.getPropertyValue("ERR_MSG_LENCHK")%>',	// ���`�F�b�N�̃x�[�X���b�Z�[�W
							{'@1@':'<%=PROP_ALERT_MSG_ERR_ITEM_SECTION%>',		// ���ږ�
							 '@2@':'<%=PROP_ALERT_MSG_ERR_ZEN_LIMIT%>',			// �S�p�E���p�J�i�̌��E������
							 '@3@':'<%=PROP_ALERT_MSG_ERR_HAN_LIMIT%>'}			// ���p�p���̌��E������
					)
				);
				document.fm.txtGroupName.focus();
				return false;
			}
		}

        if (document.fm.txtOvertimeDaily01) {
            if (document.fm.txtOvertimeDaily01.value != null && document.fm.txtOvertimeDaily01.value != "") {
                <%-- �����ȊO�����͂���Ă��� --%>
                if (!numeric_check(document.fm.txtOvertimeDaily01.value)) {
                    alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
                    document.fm.txtOvertimeDaily01.focus();
                    return false;
                }
                <%-- ���͕��������A������8���𒴂��Ă��� --%>
                if( getLengthB(document.fm.txtOvertimeDaily01.value) > 8 ) {
                    alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
                    document.fm.txtOvertimeDaily01.focus();
                    return false;
                }
            }
        }
		if (document.fm.txtOvertimeMontly01) {
			if (document.fm.txtOvertimeMontly01.value != null && document.fm.txtOvertimeMontly01.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtOvertimeMontly01.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeMontly01.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtOvertimeMontly01.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeMontly01.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeMontly02) {
			if (document.fm.txtOvertimeMontly02.value != null && document.fm.txtOvertimeMontly02.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtOvertimeMontly02.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeMontly02.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtOvertimeMontly02.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeMontly02.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeMontly03) {
			if (document.fm.txtOvertimeMontly03.value != null && document.fm.txtOvertimeMontly03.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtOvertimeMontly03.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeMontly03.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtOvertimeMontly03.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeMontly03.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeMontly04) {
			if (document.fm.txtOvertimeMontly04.value != null && document.fm.txtOvertimeMontly04.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtOvertimeMontly04.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeMontly04.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtOvertimeMontly04.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeMontly04.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeMontly05) {
			if (document.fm.txtOvertimeMontly05.value != null && document.fm.txtOvertimeMontly05.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtOvertimeMontly05.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeMontly05.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtOvertimeMontly05.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeMontly05.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeYearly01) {
			if (document.fm.txtOvertimeYearly01.value != null && document.fm.txtOvertimeYearly01.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtOvertimeYearly01.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeYearly01.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtOvertimeYearly01.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeYearly01.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeYearly02) {
			if (document.fm.txtOvertimeYearly02.value != null && document.fm.txtOvertimeYearly02.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtOvertimeYearly02.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeYearly02.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtOvertimeYearly02.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeYearly02.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeYearly03) {
			if (document.fm.txtOvertimeYearly03.value != null && document.fm.txtOvertimeYearly03.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtOvertimeYearly03.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeYearly03.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtOvertimeYearly03.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeYearly03.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeYearly04) {
			if (document.fm.txtOvertimeYearly04.value != null && document.fm.txtOvertimeYearly04.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtOvertimeYearly04.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeYearly04.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtOvertimeYearly04.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeYearly04.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeYearly05) {
			if (document.fm.txtOvertimeYearly05.value != null && document.fm.txtOvertimeYearly05.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtOvertimeYearly05.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeYearly05.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtOvertimeYearly05.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeYearly05.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeMontlyCount) {
			if (document.fm.txtOvertimeMontlyCount.value != null && document.fm.txtOvertimeMontlyCount.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtOvertimeMontlyCount.value)) {
					alert("<%=PROP_ERR_LIMIT_COUNT_NUMBER%>");
					document.fm.txtOvertimeMontlyCount.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtOvertimeMontlyCount.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_COUNT_DIGITS%>");
					document.fm.txtOvertimeMontlyCount.focus();
					return false;
				}
			}
		}

		<%-- ====== �y���Ή��z�G���[�`�F�b�N��ǉ� ====== --%>
		if (document.fm.txtHolidaytimeMontly01) {
			if (document.fm.txtHolidaytimeMontly01.value != null && document.fm.txtHolidaytimeMontly01.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtHolidaytimeMontly01.value)) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_NUMBER%>");
					document.fm.txtHolidaytimeMontly01.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtHolidaytimeMontly01.value) > 8 ) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_DIGITS%>");
					document.fm.txtHolidaytimeMontly01.focus();
					return false;
				}
			}
		}

		if (document.fm.txtHolidaytimeMontly02) {
			if (document.fm.txtHolidaytimeMontly02.value != null && document.fm.txtHolidaytimeMontly02.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtHolidaytimeMontly02.value)) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_NUMBER%>");
					document.fm.txtHolidaytimeMontly02.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtHolidaytimeMontly02.value) > 8 ) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_DIGITS%>");
					document.fm.txtHolidaytimeMontly02.focus();
					return false;
				}
			}
		}

		if (document.fm.txtHolidaytimeMontly03) {
			if (document.fm.txtHolidaytimeMontly03.value != null && document.fm.txtHolidaytimeMontly03.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtHolidaytimeMontly03.value)) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_NUMBER%>");
					document.fm.txtHolidaytimeMontly03.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtHolidaytimeMontly03.value) > 8 ) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_DIGITS%>");
					document.fm.txtHolidaytimeMontly03.focus();
					return false;
				}
			}
		}

		if (document.fm.txtHolidaytimeMontly04) {
			if (document.fm.txtHolidaytimeMontly04.value != null && document.fm.txtHolidaytimeMontly04.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtHolidaytimeMontly04.value)) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_NUMBER%>");
					document.fm.txtHolidaytimeMontly04.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtHolidaytimeMontly04.value) > 8 ) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_DIGITS%>");
					document.fm.txtHolidaytimeMontly04.focus();
					return false;
				}
			}
		}

		if (document.fm.txtHolidaytimeMontly05) {
			if (document.fm.txtHolidaytimeMontly05.value != null && document.fm.txtHolidaytimeMontly05.value != "") {
				<%-- �����ȊO�����͂���Ă��� --%>
				if (!numeric_check(document.fm.txtHolidaytimeMontly05.value)) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_NUMBER%>");
					document.fm.txtHolidaytimeMontly05.focus();
					return false;
				}
				<%-- ���͕��������A������8���𒴂��Ă��� --%>
				if( getLengthB(document.fm.txtHolidaytimeMontly05.value) > 8 ) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_DIGITS%>");
					document.fm.txtHolidaytimeMontly05.focus();
					return false;
				}
			}
		}
		return true;
	}

	<%-- �I�����[�h���� --%>
	function onLoadEvent() {

		<%-- �E�B���h�E�X�N���[���ʒu������ --%>
		initWindowScroll();
	}
	window.onload=onLoadEvent;

</script>
</head>

<body oncontextmenu="return true" marginwidth="0" marginheight="0"
	leftmargin="0" topmargin="0" bgcolor="<%=bgcolor%>" class="PS_NoVSpace">
<form name="fm" action="<%=sContextPath%>/servlet/ps.core.PsServlet" method="post">
<input type="hidden" name="txtAction" />
<input type="hidden" name="PageName" value="ps.c01.tmg.EvaluaterSetting.EvaluaterSetting" />
<input type="hidden" name="<%=EvaluaterSettingConst.REQUEST_KEY_YYYYMMDD%>" value="<%=bean.getEvaluaterSettingParam().getYYYYMMDD()%>" />
<input type="hidden" name="txtTargetGroupId" value="<%=bean.getReqParm("txtTargetGroupId")%>" />
<input type="hidden" name="txtTargetSecId" value="<%=bean.getEvaluaterSettingParam().getSection()%>" />

<%@include file="../util/TmgRequestParams.jsp"%>

<%@include file="../../Ps_Sites.jsp"%>

<div id="main" class="maintable tmg_background" style="width:970px">

<div style="margin: 10px 3px 10px 3px">
<%
	// �ҏW�\�ȍ��ڂ����݂��Ȃ��ꍇ�ɕύX�{�^�����g�p�����Ȃ�
	if (bEditGroupAnyItem) {
%> <input type="button"
	name="btnChangeGroupName" value="<%--�ύX--%><%=PROP_BTN_CHANGE%>"
	onClick="updateGroup();" /> <%
 	}
 %> <input type="button" name="btnCancel"
	value="<%--�L�����Z��--%><%=PROP_BTN_CANCEL%>" onClick="cancel();" /> <%
 	// �g�D(�g�D����̃O���[�v)�̏ꍇ�̓O���[�v�̍폜�͍s��Ȃ�
 	if (bEditGroupName) {
 %> <span style="margin-left: 20px;"><input
	type="button" name="btnDeleteGroup"
	value="<%--�폜--%><%=PROP_BTN_DELETE%>" onClick="deleteGroup();" /></span> <%
 	}
 %>
</div>

<div style="width: 100%;">
<div style="width: 40%; float: left;"><%-- ���� --%> <%=TmgUtil.getTmgHeader(bean.getEvaluaterSettingParam()
							.getSectionName())%>
</div>
<div style="width: 10%; float: left;">&nbsp;</div>
<div style="width: 40%; float: left;">
<table class='TmgGeneralHeader PS_dataLine'>
	<tr class='TmgGeneralHeader'>
		<th class='TmgGeneralHeader'><%=PROP_WRD_REVISION%></th>
	</tr>
	<tr class='TmgGeneralHeader'>
		<td class='TmgGeneralHeader' id='tmgGeneralHeader0' align="center">
		<%=bean.getYYYYMMDD_JP(bean.getEvaluaterSettingParam()
							.getYYYYMMDD())%>
		</td>
	</tr>
	<br />
	<div style="margin:10px"></div>
</table>
<br />
</div>
</div>

<div style="margin: 10px"></div>

<%
	// �g�D(�g�D����̃O���[�v)�̏ꍇ�̓O���[�v���̂̕ҏW�͍s��Ȃ�
	if (bEditGroupName) {
%>
<table class="table_basic PS_dataLine" width="300" border="0"
	cellspacing="0" cellpadding="1">
	<tr>
		<th class="table_title" colspan="2" align="left"><%--�O���[�v���ύX--%><%=PROP_EDITSECTION_EDIT%></th>
	</tr>
	<tr height="20">
		<th width="100" class="th_basic PS_group1"><%--�O���[�v--%><%=PROP_SECTION%></th>
		<td class="td_basic" id="cellGroupName"><input type="text"
			name="txtGroupName" size="35"
			value="<prop:getData query='<%=IDX_GROUPNAME%>' row='<%=0%>' column='<%=EvaluaterSettingConst.COL_GROUP_GROUPNAME%>'/>" />
		<input type="text" name== "txtDummy" style="display: none;" /> <%-- �G���^�[��submit����Ă��܂�IE�o�O�΍��p --%>
		</td>
	</tr>
</table>
<%
	}
%>
<br />

<%
	// �f�t�H���g�O���[�v�̏ꍇ�̂݁A��ԘA�g�̃f�t�H���g���F�Ҏ����ݒ�t���O���͗���\��
	if (	bean.isDefaultGroup(bean.getEvaluaterSettingParam().getGroup()) ){
%>

<table class="table_basic PS_dataLine" width="510" border="0" cellspacing="0" cellpadding="1">
	<thead>
		<tr>
			<th class="table_title" align="left" colspan="2">
				<%--��ԘA�g--%><%=PROP_WRD_NIGHTJOB_TITLE %>
			</th>
		</tr>
	</thead>
	<tbody>
		<tr height="20">
			<td class="td_basic" style="width:350px;">
					<b><%--�f�t�H���g���F�҂̎����ݒ���s��--%><%=PROP_WRD_NIGHTJOB_AUTOSET_EVA %></b>
			</td>
			<td class="td_basic" style="width:100px;text-align:center;">
<%
	String sAutosetChecked = "";
	if (	bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_GroupAttribute, bean.COL_GroupAttribute_AutoSet_Eva, 0) == null ||
			TmgUtil.Cs_MGD_ONOFF_1.equals(bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_GroupAttribute, bean.COL_GroupAttribute_AutoSet_Eva, 0)) ){
		sAutosetChecked = " checked ";
	}
%>
					<input type="checkbox" name="chkCautosetEva" value="1" <%=sAutosetChecked %>/>
			</td>
		</tr>
    </tbody>
</table>
<br />
<%
	}	// end if �f�t�H���g�O���[�v�̏ꍇ�̂�

 	if (bEditOvertimeAnyItem) {
 %>
<table class="table_basic PS_dataLine" width="510" border="0"
	cellspacing="0" cellpadding="1">
	<thead>
		<tr>
			<th class="table_title" align="left" width="350px"><%--���Ύ��т̌x���l�̐ݒ�--%><%=PROP_WRD_LIMIT_SETTING%>
			</th>
			<th class="table_title" align="center" width="100px"><%--���ݒl(����)--%><%=PROP_WRD_LIMIT_NOW%>
			</th>
		</tr>
	</thead>
	<tbody>
        <%
            if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_DAILY_01_MGD, 0).equals("TMG_ONOFF|1")) {
        %>
        <tr height="20">
            <td class="td_basic" align="center">
            <div style="width: 60%; float: left;"><b><%--���Ύ��т̌x���l(����)(����:���x��1)--%><%=PROP_WRD_LIMIT_DAILY_01%></b>
            </div>
            <div style="width: 40%; float: right; text-align: center"><input
                type="text" name="txtOvertimeDaily01"
                value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_DAILY_01%>' input='true'/>" />
            </div>
            </td>
            <td class="td_basic" align="right"><b><prop:getData
                query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_DAILY_01%>' /></b></td>
        </tr>
        <%
            }
        %>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTLY_01_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--���Ύ��т̌x���l(����)(����:���F)--%><%=PROP_WRD_LIMIT_MONTLY_01%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtOvertimeMontly01"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_MONTLY_01%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_MONTLY_01%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTLY_02_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b ><%--���Ύ��т̌x���l(����)(����:�I�����W)--%><%=PROP_WRD_LIMIT_MONTLY_02%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtOvertimeMontly02"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_MONTLY_02%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_MONTLY_02%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTLY_03_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--���Ύ��т̌x���l(����)(����:�s���N)--%><%=PROP_WRD_LIMIT_MONTLY_03%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtOvertimeMontly03"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_MONTLY_03%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_MONTLY_03%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTLY_04_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--���Ύ��т̌x���l(����)(����:�ԐF)--%><%=PROP_WRD_LIMIT_MONTLY_04%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtOvertimeMontly04"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_MONTLY_04%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_MONTLY_04%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTLY_05_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--���Ύ��т̌x���l(����)(����:�H�H)--%><%=PROP_WRD_LIMIT_MONTLY_05%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtOvertimeMontly05"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_MONTLY_05%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_MONTLY_05%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_YEARLY_01_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--���Ύ��т̌x���l(����)(�N��:���F)--%><%=PROP_WRD_LIMIT_YEARLY_01%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtOvertimeYearly01"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_YEARLY_01%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_YEARLY_01%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_YEARLY_02_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--���Ύ��т̌x���l(����)(�N��:�I�����W)--%><%=PROP_WRD_LIMIT_YEARLY_02%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtOvertimeYearly02"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_YEARLY_02%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_YEARLY_02%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_YEARLY_03_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--���Ύ��т̌x���l(����)(�N��:�s���N)--%><%=PROP_WRD_LIMIT_YEARLY_03%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtOvertimeYearly03"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_YEARLY_03%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_YEARLY_03%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_YEARLY_04_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--���Ύ��т̌x���l(����)(�N��:�ԐF)--%><%=PROP_WRD_LIMIT_YEARLY_04%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtOvertimeYearly04"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_YEARLY_04%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_YEARLY_04%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_YEARLY_05_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--���Ύ��т̌x���l(����)(�N��:�H�H)--%><%=PROP_WRD_LIMIT_YEARLY_05%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtOvertimeYearly05"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_YEARLY_05%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_YEARLY_05%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTHLY_COUNT_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--���Ύ��т̌����x���l���߉�--%><%=PROP_WRD_LIMIT_MONTHLY_COUNT%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtOvertimeMontlyCount"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_MONTHLY_COUNT%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_MONTHLY_COUNT%>' /></b></td>
		</tr>
		<%
			}
		%>
	<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_MONTHLY_COUNT_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--���Ύ��т̌����ώ���--%><%=PROP_WRD_LIMIT_MONTHLY_AVG%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtOvertimeMontlyAvg"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_OverTimeLimitSelf_OT_MONTHLY_AVG%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_OverTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_OT_MONTHLY_AVG%>' /></b></td>
		</tr>
		<%
			}
		%>
	<%
		}
	%>
	</tbody>
</table>
<br />
<%-- ======  �y���Ή��z  �x���Ζ������Əh�����񐔂̌x���l�̐ݒ荀�ڂ�ǉ�  ====== --%>
<%
 	if (bEditHolidaytimeAnyItem) {
 %>
<table class="table_basic PS_dataLine" width="510" border="0"
	cellspacing="0" cellpadding="1">
	<thead>
		<tr>
			<th class="table_title" align="left" width="350px"><%--�x���Ζ������x���l�̐ݒ�--%><%=PROP_WRD_HOLIDAY_ALERT%>
			</th>
			<th class="table_title" align="center" width="100px"><%--���ݒl(����)--%><%=PROP_WRD_LIMIT_DAY %>
			</th>
		</tr>
	</thead>
	<tbody>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_01_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--�x���l(����)(����:���x��1)--%><%=PROP_WRD_ALERT_LEVEL1%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtHolidaytimeMontly01"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_HolidayTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_HolidayTimeLimitSelf_HT_MONTLY_01%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_HT_MONTLY_01%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_02_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--�x���l(����)(����:���x��2)--%><%=PROP_WRD_ALERT_LEVEL2%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtHolidaytimeMontly02"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_HolidayTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_HolidayTimeLimitSelf_HT_MONTLY_02%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_HT_MONTLY_02%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_03_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--�x���l(����)(����:���x��3)--%><%=PROP_WRD_ALERT_LEVEL3%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtHolidaytimeMontly03"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_HolidayTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_HolidayTimeLimitSelf_HT_MONTLY_03%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_HT_MONTLY_03%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_04_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--�x���l(����)(����:���x��4)--%><%=PROP_WRD_ALERT_LEVEL4%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtHolidaytimeMontly04"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_HolidayTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_HolidayTimeLimitSelf_HT_MONTLY_04%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_HT_MONTLY_04%>' /></b></td>
		</tr>
		<%
			}
		%>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_05_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--�x���l(����)(����:���x��5)--%><%=PROP_WRD_ALERT_LEVEL5%></b>
			</div>
			<div style="width: 40%; float: right; text-align: center"><input
				type="text" name="txtHolidaytimeMontly05"
				value="<prop:getData query='<%=bean.QUERY_SHOWEDITGROUP_HolidayTimeLimitSelf%>' row='<%=0%>' column='<%=bean.COL_HolidayTimeLimitSelf_HT_MONTLY_05%>' input='true'/>" />
			</div>
			</td>
			<td class="td_basic" align="right"><b><prop:getData
				query='<%=bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit%>' row='<%=0%>' column='<%=bean.COL_OVERTIMELIMIT_HT_MONTLY_05%>' /></b></td>
		</tr>
		<%
			}
		%>
	<%
		}
	%>
	</tbody>
</table>
<br />
<%
 	if (!bEditGroupAnyItem) {
 %>
<div class="maintable tmg_background" style="color: red"><%=TmgUtil.getPropertyValue("MSG_NO_SELECT_SECTION")%>
</div>
<%
	}
%>
<!-- </div> -->

</form>
</body>
</html>
