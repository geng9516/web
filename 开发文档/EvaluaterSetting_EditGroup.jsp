<%
//$Id: EvaluaterSetting_EditGroup.jsp 6809 2010-10-12 07:58:11Z acsyamaguchi.k $
//$Revision: 6809 $
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<%--  エラーページを定義 --%>
<%@page language="java" errorPage="/jsp/pages/Hrm_pi_ErrorPage.jsp"
	contentType="text/html;charset=Windows-31J"%>
<%
	/*==============================================================================
	 *	system		Progress@Site HR
	 *	version		Ver3.7.3
	 *	id			EvaluaterSetting_EditGroup.jsp
	 *  title		グループ編集画面
	 *	author		Y.Moriai
	 *	create		2007/03/06
	 *				更新日		更新者			更新内容
	 *	update		2008/01/28  H.kawabata      #339 キャンセルボタン押下時のConfirmメッセージが誤解を生むので削除
	 *              2009/12/21	isoltakahashi	#阪大対応	宿直上限値、日直上限値、休日出勤警告値の項目欄を追加
	 *				2010/03/05	isolshoji.y		#2527 イントラマートの見た目対応
	 *				2010/03/18	isolshoji.y		#2571 「グループ」の文言を「組織」に変更
	 *				2010/10/08	yamaguchi.k		#1412 超勤警告値の設定が夜間連携を実行すると消える(Ajax経由処理を通常サブミットに修正)
	 *				2010/12/02  ogawa           tmd#37 宿日直部分をコメントアウト化
	 *				2010/12/21  ogawa           tmd#62 グループ作成・グループ編集処理をDBへ
	 *				2011/01/31	yamaguchi.k		tmd#142 桁チェックのメッセージ修正
	 *				2011/02/17	yamaguchi.k		tmd#159 画面表示時処理にウィンドウスクロールの位置初期設定処理を追加
	 *				2019/07/02	Ns.chou		    ------- 月次と年次のオレンジ、ピンク設定、超勤実績の複数月平均時間：赤色の設定を追加する
	 *	remark
	 =============================================================================*/
%>
<%-- キャッシュのコントロール --%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%@page import="ps.c01.tmg.EvaluaterSetting.EvaluaterSettingConst"%>
<%@page import="ps.c01.tmg.EvaluaterSetting.EvaluaterSettingBean"%>
<%@page import="ps.c01.tmg.util.TmgUtil"%>
<%@page import="java.util.*"%>
<%--  使用するBeanを宣言 --%>
<jsp:useBean id="EvaluaterSettingBean" type="ps.c01.tmg.EvaluaterSetting.EvaluaterSettingBean" scope="request" />
<%-- タグライブラリの宣言 --%>
<%@taglib uri="/WEB-INF/tlds/custom.tld" prefix="prop"%>
<%-- アプリケーション名 --%>
<prop:getPropertyAll prop="ps.c01.tmg.EvaluaterSetting.EvaluaterSetting" />
<%
	request.setAttribute("Application",
			"ps.c01.tmg.EvaluaterSetting.EvaluaterSetting");
%>
<%!// 結果セット番号
	private final int IDX_GROUPNAME = 0; // グループ名称%>
<%
	EvaluaterSettingBean bean = EvaluaterSettingBean;

	// 画面属性を取得
	Hashtable hash = (Hashtable) application.getAttribute("AppObject");
	String custId = "";
	custId = (String) ((Hashtable) session.getAttribute("UserData")).get("CustID");
	ps.util.Customize customize = null;
	customize = (ps.util.Customize) hash.get("Customize");
	String bgcolor = customize.getCustomizeProperty("01", "bk_color");//背景色
	String sMainHeaderBgColor = customize.getMainHeaderBgColor(custId);//メインタイトル背景色
	String sHeaderFirstBgColor = customize.getHeaderFirstBgColor(custId);//ヘッダ背景色1
	String sHeaderSecondBgColor = customize.getHeaderSecondBgColor(custId);//ヘッダ背景色2
	String sResultDisplayBgColor = customize.getResultDisplayBgColor(custId);//結果背景色

	String sContextPath = request.getContextPath();

	// 超過勤務制限値編集項目の編集可能項目の有無を確認
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

	// 休日勤務制限値編集項目の編集可能項目の有無を確認
	boolean bEditHolidaytimeAnyItem = false;
	if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_01_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_02_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_03_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_04_MGD, 0).equals("TMG_ONOFF|1")
			|| bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_05_MGD, 0).equals("TMG_ONOFF|1")) {
		bEditHolidaytimeAnyItem = true;
	}

	// 選択グループが組織既定のグループであるか確認
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
<title><%--権限設定--%><%=PROP_TITLE%></title>
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

	var gbDoubleClick   = false; // ボタン２度打ち防止用フラグ

	<%-- 変更 --%>
	function updateGroup() {
		if( !errorCheck() ) {
			// エラーがあれば終了する。
			return;
		}
		if( !confirm("<%=PROP_MSG_EDITSECTION%>") ) {
			return;
		}
		if (document.fm.postcount){
			// 削除チェックボックス(hidden項目)の設定
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

	<%-- キャンセル --%>
	function cancel(){
		// 2008/01/28 H.kawabata   #339 キャンセルボタン押下時のConfirmメッセージが誤解を生むので削除
		// if( !confirm("<%=PROP_MSG_CANCEL%>") ) { return; }
		document.fm.txtAction.value = "<%=EvaluaterSettingConst.ACT_DISP_REVALLIST%>";
	    document.fm.target = "_self";
		document.fm.submit();
	}

	<%-- 削除--%>
	function deleteGroup(){
		if( !confirm("<%=PROP_MSG_DELETESECTION%>") ) { return; }
		document.fm.txtAction.value = "<%=EvaluaterSettingConst.ACT_EDITGROUP_DGROUP%>";
	    document.fm.target = "_self";
		document.fm.submit();
	}

	<%-- エラーチェック --%>
	function errorCheck(){
		if (document.fm.txtGroupName) {
			<%-- グループ名称が未入力 --%>
			if( document.fm.txtGroupName.value == "" ) {
				alert("<%=PROP_ERROR_SECTION_INPUT%>");
				document.fm.txtGroupName.focus();
				return false;
			}
			<%-- グループ名称が100文字を超えている --%>
			if( getLengthB(document.fm.txtGroupName.value) > '<%=PROP_ALERT_MSG_ERR_HAN_LIMIT%>' ) {
				alert(
					replaceMessage(
							'<%=TmgUtil.getPropertyValue("ERR_MSG_LENCHK")%>',	// 桁チェックのベースメッセージ
							{'@1@':'<%=PROP_ALERT_MSG_ERR_ITEM_SECTION%>',		// 項目名
							 '@2@':'<%=PROP_ALERT_MSG_ERR_ZEN_LIMIT%>',			// 全角・半角カナの限界文字数
							 '@3@':'<%=PROP_ALERT_MSG_ERR_HAN_LIMIT%>'}			// 半角英数の限界文字数
					)
				);
				document.fm.txtGroupName.focus();
				return false;
			}
		}

        if (document.fm.txtOvertimeDaily01) {
            if (document.fm.txtOvertimeDaily01.value != null && document.fm.txtOvertimeDaily01.value != "") {
                <%-- 数字以外が入力されている --%>
                if (!numeric_check(document.fm.txtOvertimeDaily01.value)) {
                    alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
                    document.fm.txtOvertimeDaily01.focus();
                    return false;
                }
                <%-- 入力文字数が、数字で8桁を超えている --%>
                if( getLengthB(document.fm.txtOvertimeDaily01.value) > 8 ) {
                    alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
                    document.fm.txtOvertimeDaily01.focus();
                    return false;
                }
            }
        }
		if (document.fm.txtOvertimeMontly01) {
			if (document.fm.txtOvertimeMontly01.value != null && document.fm.txtOvertimeMontly01.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtOvertimeMontly01.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeMontly01.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtOvertimeMontly01.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeMontly01.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeMontly02) {
			if (document.fm.txtOvertimeMontly02.value != null && document.fm.txtOvertimeMontly02.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtOvertimeMontly02.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeMontly02.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtOvertimeMontly02.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeMontly02.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeMontly03) {
			if (document.fm.txtOvertimeMontly03.value != null && document.fm.txtOvertimeMontly03.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtOvertimeMontly03.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeMontly03.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtOvertimeMontly03.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeMontly03.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeMontly04) {
			if (document.fm.txtOvertimeMontly04.value != null && document.fm.txtOvertimeMontly04.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtOvertimeMontly04.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeMontly04.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtOvertimeMontly04.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeMontly04.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeMontly05) {
			if (document.fm.txtOvertimeMontly05.value != null && document.fm.txtOvertimeMontly05.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtOvertimeMontly05.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeMontly05.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtOvertimeMontly05.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeMontly05.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeYearly01) {
			if (document.fm.txtOvertimeYearly01.value != null && document.fm.txtOvertimeYearly01.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtOvertimeYearly01.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeYearly01.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtOvertimeYearly01.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeYearly01.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeYearly02) {
			if (document.fm.txtOvertimeYearly02.value != null && document.fm.txtOvertimeYearly02.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtOvertimeYearly02.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeYearly02.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtOvertimeYearly02.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeYearly02.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeYearly03) {
			if (document.fm.txtOvertimeYearly03.value != null && document.fm.txtOvertimeYearly03.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtOvertimeYearly03.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeYearly03.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtOvertimeYearly03.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeYearly03.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeYearly04) {
			if (document.fm.txtOvertimeYearly04.value != null && document.fm.txtOvertimeYearly04.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtOvertimeYearly04.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeYearly04.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtOvertimeYearly04.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeYearly04.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeYearly05) {
			if (document.fm.txtOvertimeYearly05.value != null && document.fm.txtOvertimeYearly05.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtOvertimeYearly05.value)) {
					alert("<%=PROP_ERR_LIMIT_HOUR_NUMBER%>");
					document.fm.txtOvertimeYearly05.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtOvertimeYearly05.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_HOUR_DIGITS%>");
					document.fm.txtOvertimeYearly05.focus();
					return false;
				}
			}
		}
		if (document.fm.txtOvertimeMontlyCount) {
			if (document.fm.txtOvertimeMontlyCount.value != null && document.fm.txtOvertimeMontlyCount.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtOvertimeMontlyCount.value)) {
					alert("<%=PROP_ERR_LIMIT_COUNT_NUMBER%>");
					document.fm.txtOvertimeMontlyCount.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtOvertimeMontlyCount.value) > 8 ) {
					alert("<%=PROP_ERR_LIMIT_COUNT_DIGITS%>");
					document.fm.txtOvertimeMontlyCount.focus();
					return false;
				}
			}
		}

		<%-- ====== 【阪大対応】エラーチェックを追加 ====== --%>
		if (document.fm.txtHolidaytimeMontly01) {
			if (document.fm.txtHolidaytimeMontly01.value != null && document.fm.txtHolidaytimeMontly01.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtHolidaytimeMontly01.value)) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_NUMBER%>");
					document.fm.txtHolidaytimeMontly01.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtHolidaytimeMontly01.value) > 8 ) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_DIGITS%>");
					document.fm.txtHolidaytimeMontly01.focus();
					return false;
				}
			}
		}

		if (document.fm.txtHolidaytimeMontly02) {
			if (document.fm.txtHolidaytimeMontly02.value != null && document.fm.txtHolidaytimeMontly02.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtHolidaytimeMontly02.value)) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_NUMBER%>");
					document.fm.txtHolidaytimeMontly02.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtHolidaytimeMontly02.value) > 8 ) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_DIGITS%>");
					document.fm.txtHolidaytimeMontly02.focus();
					return false;
				}
			}
		}

		if (document.fm.txtHolidaytimeMontly03) {
			if (document.fm.txtHolidaytimeMontly03.value != null && document.fm.txtHolidaytimeMontly03.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtHolidaytimeMontly03.value)) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_NUMBER%>");
					document.fm.txtHolidaytimeMontly03.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtHolidaytimeMontly03.value) > 8 ) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_DIGITS%>");
					document.fm.txtHolidaytimeMontly03.focus();
					return false;
				}
			}
		}

		if (document.fm.txtHolidaytimeMontly04) {
			if (document.fm.txtHolidaytimeMontly04.value != null && document.fm.txtHolidaytimeMontly04.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtHolidaytimeMontly04.value)) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_NUMBER%>");
					document.fm.txtHolidaytimeMontly04.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtHolidaytimeMontly04.value) > 8 ) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_DIGITS%>");
					document.fm.txtHolidaytimeMontly04.focus();
					return false;
				}
			}
		}

		if (document.fm.txtHolidaytimeMontly05) {
			if (document.fm.txtHolidaytimeMontly05.value != null && document.fm.txtHolidaytimeMontly05.value != "") {
				<%-- 数字以外が入力されている --%>
				if (!numeric_check(document.fm.txtHolidaytimeMontly05.value)) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_NUMBER%>");
					document.fm.txtHolidaytimeMontly05.focus();
					return false;
				}
				<%-- 入力文字数が、数字で8桁を超えている --%>
				if( getLengthB(document.fm.txtHolidaytimeMontly05.value) > 8 ) {
					alert("<%=PROP_ERR_HOLIDAY_LIMIT_HOUR_DIGITS%>");
					document.fm.txtHolidaytimeMontly05.focus();
					return false;
				}
			}
		}
		return true;
	}

	<%-- オンロード処理 --%>
	function onLoadEvent() {

		<%-- ウィンドウスクロール位置初期化 --%>
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
	// 編集可能な項目が存在しない場合に変更ボタンを使用させない
	if (bEditGroupAnyItem) {
%> <input type="button"
	name="btnChangeGroupName" value="<%--変更--%><%=PROP_BTN_CHANGE%>"
	onClick="updateGroup();" /> <%
 	}
 %> <input type="button" name="btnCancel"
	value="<%--キャンセル--%><%=PROP_BTN_CANCEL%>" onClick="cancel();" /> <%
 	// 組織(組織既定のグループ)の場合はグループの削除は行わない
 	if (bEditGroupName) {
 %> <span style="margin-left: 20px;"><input
	type="button" name="btnDeleteGroup"
	value="<%--削除--%><%=PROP_BTN_DELETE%>" onClick="deleteGroup();" /></span> <%
 	}
 %>
</div>

<div style="width: 100%;">
<div style="width: 40%; float: left;"><%-- 所属 --%> <%=TmgUtil.getTmgHeader(bean.getEvaluaterSettingParam()
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
	// 組織(組織既定のグループ)の場合はグループ名称の編集は行わない
	if (bEditGroupName) {
%>
<table class="table_basic PS_dataLine" width="300" border="0"
	cellspacing="0" cellpadding="1">
	<tr>
		<th class="table_title" colspan="2" align="left"><%--グループ名変更--%><%=PROP_EDITSECTION_EDIT%></th>
	</tr>
	<tr height="20">
		<th width="100" class="th_basic PS_group1"><%--グループ--%><%=PROP_SECTION%></th>
		<td class="td_basic" id="cellGroupName"><input type="text"
			name="txtGroupName" size="35"
			value="<prop:getData query='<%=IDX_GROUPNAME%>' row='<%=0%>' column='<%=EvaluaterSettingConst.COL_GROUP_GROUPNAME%>'/>" />
		<input type="text" name== "txtDummy" style="display: none;" /> <%-- エンターでsubmitされてしまうIEバグ対策用 --%>
		</td>
	</tr>
</table>
<%
	}
%>
<br />

<%
	// デフォルトグループの場合のみ、夜間連携のデフォルト承認者自動設定フラグ入力欄を表示
	if (	bean.isDefaultGroup(bean.getEvaluaterSettingParam().getGroup()) ){
%>

<table class="table_basic PS_dataLine" width="510" border="0" cellspacing="0" cellpadding="1">
	<thead>
		<tr>
			<th class="table_title" align="left" colspan="2">
				<%--夜間連携--%><%=PROP_WRD_NIGHTJOB_TITLE %>
			</th>
		</tr>
	</thead>
	<tbody>
		<tr height="20">
			<td class="td_basic" style="width:350px;">
					<b><%--デフォルト承認者の自動設定を行う--%><%=PROP_WRD_NIGHTJOB_AUTOSET_EVA %></b>
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
	}	// end if デフォルトグループの場合のみ

 	if (bEditOvertimeAnyItem) {
 %>
<table class="table_basic PS_dataLine" width="510" border="0"
	cellspacing="0" cellpadding="1">
	<thead>
		<tr>
			<th class="table_title" align="left" width="350px"><%--超勤実績の警告値の設定--%><%=PROP_WRD_LIMIT_SETTING%>
			</th>
			<th class="table_title" align="center" width="100px"><%--現在値(時間)--%><%=PROP_WRD_LIMIT_NOW%>
			</th>
		</tr>
	</thead>
	<tbody>
        <%
            if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_OverTimeLimit, bean.COL_OVERTIMELIMIT_OT_DAILY_01_MGD, 0).equals("TMG_ONOFF|1")) {
        %>
        <tr height="20">
            <td class="td_basic" align="center">
            <div style="width: 60%; float: left;"><b><%--超勤実績の警告値(時間)(日次:レベル1)--%><%=PROP_WRD_LIMIT_DAILY_01%></b>
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
			<div style="width: 60%; float: left;"><b><%--超勤実績の警告値(時間)(月次:黄色)--%><%=PROP_WRD_LIMIT_MONTLY_01%></b>
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
			<div style="width: 60%; float: left;"><b ><%--超勤実績の警告値(時間)(月次:オレンジ)--%><%=PROP_WRD_LIMIT_MONTLY_02%></b>
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
			<div style="width: 60%; float: left;"><b><%--超勤実績の警告値(時間)(月次:ピンク)--%><%=PROP_WRD_LIMIT_MONTLY_03%></b>
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
			<div style="width: 60%; float: left;"><b><%--超勤実績の警告値(時間)(月次:赤色)--%><%=PROP_WRD_LIMIT_MONTLY_04%></b>
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
			<div style="width: 60%; float: left;"><b><%--超勤実績の警告値(時間)(月次:？？)--%><%=PROP_WRD_LIMIT_MONTLY_05%></b>
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
			<div style="width: 60%; float: left;"><b><%--超勤実績の警告値(時間)(年次:黄色)--%><%=PROP_WRD_LIMIT_YEARLY_01%></b>
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
			<div style="width: 60%; float: left;"><b><%--超勤実績の警告値(時間)(年次:オレンジ)--%><%=PROP_WRD_LIMIT_YEARLY_02%></b>
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
			<div style="width: 60%; float: left;"><b><%--超勤実績の警告値(時間)(年次:ピンク)--%><%=PROP_WRD_LIMIT_YEARLY_03%></b>
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
			<div style="width: 60%; float: left;"><b><%--超勤実績の警告値(時間)(年次:赤色)--%><%=PROP_WRD_LIMIT_YEARLY_04%></b>
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
			<div style="width: 60%; float: left;"><b><%--超勤実績の警告値(時間)(年次:？？)--%><%=PROP_WRD_LIMIT_YEARLY_05%></b>
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
			<div style="width: 60%; float: left;"><b><%--超勤実績の月次警告値超過回数--%><%=PROP_WRD_LIMIT_MONTHLY_COUNT%></b>
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
			<div style="width: 60%; float: left;"><b><%--超勤実績の月平均時間--%><%=PROP_WRD_LIMIT_MONTHLY_AVG%></b>
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
<%-- ======  【阪大対応】  休日勤務日数と宿日直回数の警告値の設定項目を追加  ====== --%>
<%
 	if (bEditHolidaytimeAnyItem) {
 %>
<table class="table_basic PS_dataLine" width="510" border="0"
	cellspacing="0" cellpadding="1">
	<thead>
		<tr>
			<th class="table_title" align="left" width="350px"><%--休日勤務日数警告値の設定--%><%=PROP_WRD_HOLIDAY_ALERT%>
			</th>
			<th class="table_title" align="center" width="100px"><%--現在値(時間)--%><%=PROP_WRD_LIMIT_DAY %>
			</th>
		</tr>
	</thead>
	<tbody>
		<%
			if (bean.valueAtColumnRow(bean.QUERY_SHOWEDITGROUP_HolidayTimeLimit, bean.COL_OVERTIMELIMIT_HT_MONTLY_01_MGD, 0).equals("TMG_ONOFF|1")) {
		%>
		<tr height="20">
			<td class="td_basic" align="center">
			<div style="width: 60%; float: left;"><b><%--警告値(日数)(月次:レベル1)--%><%=PROP_WRD_ALERT_LEVEL1%></b>
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
			<div style="width: 60%; float: left;"><b><%--警告値(日数)(月次:レベル2)--%><%=PROP_WRD_ALERT_LEVEL2%></b>
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
			<div style="width: 60%; float: left;"><b><%--警告値(日数)(月次:レベル3)--%><%=PROP_WRD_ALERT_LEVEL3%></b>
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
			<div style="width: 60%; float: left;"><b><%--警告値(日数)(月次:レベル4)--%><%=PROP_WRD_ALERT_LEVEL4%></b>
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
			<div style="width: 60%; float: left;"><b><%--警告値(日数)(月次:レベル5)--%><%=PROP_WRD_ALERT_LEVEL5%></b>
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
