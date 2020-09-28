package jp.smartcompany.job.modules.tmg.permStatList;

import cn.hutool.core.bean.BeanUtil;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.CalenderVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.OneMonthDetailVo;
import jp.smartcompany.job.modules.tmg.permStatList.dto.ColNameDto;
import jp.smartcompany.job.modules.tmg.permStatList.vo.TmgMonthlyInfoVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.ItemVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import jp.smartcompany.boot.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 承認状況一覧クラス
 * ps.c01.tmg.PermStatList.PermStatListBean
 *
 * @author Nie Wanqun
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermStatListBean {

    /**
     * IMastGenericDetailService
     */
    private final IMastGenericDetailService iMastGenericDetailService;

    /**
     * ITmgMonthlyService
     */
    private final ITmgMonthlyService iTmgMonthlyService;

    /**
     * ITmgDailyService
     */
    private final ITmgDailyService iTmgDailyService;

    /**
     * ITmgDailyDetailService
     */
    private final ITmgDailyDetailService iTmgDailyDetailService;

    /**
     * IMastOrganisationService
     */
    private final IMastOrganisationService iMastOrganisationService;

    /**
     * ITmgTriggerService
     */
    private final ITmgTriggerService iTmgTriggerService;

    /**
     * ITmgMonthlyInfoService
     */
    private final ITmgMonthlyInfoService iTmgMonthlyInfoService;

    /**
     * ITmgMonthlyInfoService
     */
    private final ITmgCalendarService iTmgCalendarService;

    /**
     * IMastEmployeesService
     */
    private final IMastEmployeesService iMastEmployeesService;

    public static final String APPLICATION_ID = "PermStatList";

    private TmgReferList referList;
    /**
     * リクエストキー - アクション
     */
    public static final String REQ_ACTION = "txtAction";
    /**
     * リクエストキー - 対象組織
     */
    public static final String REQ_SECTION_ID = "txtSectionId";
    /**
     * リクエストキー - 対象勤務年月日
     */
    public static final String REQ_DYYYYMMDD = "txtDYYYYMMDD";
    /**
     * リクエストキー - 対象年月
     */
    public static final String REQ_DYYYYMM = "txtDYYYYMM";
    /**
     * リクエストキー - 対象職員番号
     */
    public static final String REQ_CEMPLOYEEID = "txtCEMPLOYEEID";
    /**
     * リクエストキー - リダイレクトBean名
     */
    public static final String REQ_REDIRECT_BEAN = "txtRedirectBean";
    /**
     * リクエストキー - リダイレクト先アクション値
     */
    public static final String REQ_REDIRECT_ACTION = "txtRedirectACTION";
    /**
     * リクエストキー - コールバック先アクション値
     */
    public static final String REQ_CALL_ACTION = "txtCallBeanAction";
    /**
     * リクエストキー - 処理対象職員番号
     */
    public static final String REQ_EXECUTEEMPID = "txtExecuteEmpId";
    /**
     * リクエストキー - 再表示ボタン使用判定用
     */
    private static final String TREEVIEW_KEY_REFRESH_FLG = "txtTmgReferListTreeViewRefreshFlg";

    /**
     * アクション - 月別一覧画面表示)
     */
    public static final String ACT_DISP_MONTHLY = "ACT_DISP_MONTHLY";
    /**
     * アクション - 月別一括承認処理
     */
    public static final String ACT_MONTHLY_PERMIT = "ACT_MONTHLY_PERMIT";
    /**
     * アクション - 日別一括承認画面表示
     */
    public static final String ACT_EDIT_DAIRY = "ACT_EDIT_DAIRY";
    /**
     * アクション - 日別一括承認処理
     */
    public static final String ACT_PERMIT = "ACT_PERMIT";

    /** 勤怠承認権限を持っていない場合の一文字ステータス */
    public static final String STATUS_UNAPPLYAUTHORITY = "不";

    /**
     * 日付形式1
     */
    private static final String FORMAT_DATE_TYPE1 = "yyyy/MM/dd";

    private static final String FORMAT_SLASH = "/";
    private static final String FORMAT_ZERO = "00";

    /**
     * 1ヵ月後
     */
    private static final int PARAM_NEXT_MONTH = 0;
    /**
     * 今月
     */
    private static final int PARAM_THIS_MONTH = -1;
    /**
     * 1ヶ月前
     */
    private static final int PARAM_PREV_MONTH = -2;

    /**
     * アクション
     */
    private String _sAction = null;

    /**
     * 対象組織
     */
    private String _reqSectionId = null;
    /**
     * 対象勤務年月日
     */
    private String _reqDYYYYMMDD = null;
    /**
     * 対象年月(検索対象年月)
     */
    private String _reqDYYYYMM = null;

//    /**
//     * 対象年月の前月
//     */
//    private String _prevMonth = null;
//    /**
//     * 対象年月の翌月
//     */
//    private String _nextMonth = null;
    /**
     * 今月
     */
    private String _thisMonth = null;

//    /**
//     * 対象組織が選択されているかどうかを表します。
//     * <p>
//     * true(選択あり) / false(選択なし) <br>
//     * 初回遷移時などでは汎用参照コンポーネントで組織が選択されていない状態
//     * のためfalseとなります。
//     * </p>
//     */
//    private boolean _isSelectSection = false;

    /**
     * 勤怠シートの参照権限(基準日の翌月)
     */
    boolean _authorityNextMonth = false;
    /**
     * 勤怠シートの参照権限(基準月)
     */
    boolean _authorityMonth = false;
//    /**
//     * 参照権限：参照可能
//     */
//    private static final boolean CB_CAN_REFER = true;
//    /**
//     * 参照権限：参照不可能
//     */
//    private static final boolean CB_CANT_REFER = false;

    /**
     * 検索対象年月を返す
     *
     * @return String 検索対象年月
     */
    public String getReqDYYYYMM() {
        return this._reqDYYYYMM;
    }

    /**
     * 承認サイト・管理サイト
     * 月別一覧画面表示
     * <p>
     * ACT_DISP_MONTHLY
     *
     * @param modelMap
     */
    public void actDispMonthly(ModelMap modelMap) throws Exception {
//        execute(modelMap);

        // 月別一覧表示の為のプロセスを実行します。
//       executeReadMonthlyList(modelMap,);
    }


    /**
     * 承認サイト・管理サイト
     * 日別一括承認画面表示
     * <p>
     * ACT_EDIT_DAIRY
     *
     * @param modelMap
     */
    public void actEditDairy(ModelMap modelMap) throws Exception {
//        execute(modelMap);
//       executeReadTmgDaily(modelMap);

    }

    /**
     * 承認サイト・管理サイト
     * 月別一括承認処理
     * <p>
     * ACT_MONTHLY_PERMIT
     *
     * @param modelMap
     */
    public void actMonthlyPermit(ModelMap modelMap) throws Exception {
//        execute(modelMap);
//        executeUpdateTmgMonthly();
//        executeReadMonthlyList(modelMap);
    }

    /**
     * 承認サイト・管理サイト
     * 日別一括承認処理
     * <p>
     * ACT_PERMIT
     *
     * @param modelMap
     */
    public void actPermit(ModelMap modelMap) throws Exception {
//        execute(modelMap);
//        executeUpdateTmgDaily();
////        executeReadMonthlyList(modelMap);
    }

    /**
     * 承認サイト・管理サイト
     * 別コンテンツへのリダイレクト
     * <p>
     * ACT_REDIRECT
     *
     * @param modelMap
     */
    public void actRedirect(ModelMap modelMap) {
//        executeRedirect(modelMap);
    }

    /**
     * 別コンテンツへのリダイレクトを実行します。
     *
     * @see TmgReferList#setTargetEmployee(String)
     */
    private void executeRedirect(ModelMap modelMap, PsDBBean psDBBean) {

        String reqEmployeeId = String.valueOf(psDBBean.getRequestHash().get(REQ_CEMPLOYEEID));
        String sBean = psDBBean.getReqParam(REQ_REDIRECT_BEAN);
        Hashtable htRequest = new Hashtable();
        htRequest.put(REQ_ACTION, psDBBean.getReqParam(REQ_REDIRECT_ACTION));
        htRequest.put(REQ_SECTION_ID, _reqSectionId);
        htRequest.put(REQ_DYYYYMMDD, _reqDYYYYMMDD);
        htRequest.put(REQ_DYYYYMM, _reqDYYYYMM);
        htRequest.put(REQ_CEMPLOYEEID, reqEmployeeId);

        htRequest.put(REQ_REDIRECT_BEAN, APPLICATION_ID);

        if (psDBBean.getReqParam(REQ_CALL_ACTION).equals(ACT_EDIT_DAIRY)) {
            htRequest.put(REQ_REDIRECT_ACTION, ACT_EDIT_DAIRY);
        } else if (psDBBean.getReqParam(REQ_CALL_ACTION).equals(ACT_DISP_MONTHLY)) {
            htRequest.put(REQ_REDIRECT_ACTION, ACT_DISP_MONTHLY);
        } else { // いずれでもない場合は月別一覧に戻る
            htRequest.put(REQ_REDIRECT_ACTION, ACT_DISP_MONTHLY);
        }

        referList.setTargetEmployee(reqEmployeeId);
        modelMap.addAttribute("htRequest", htRequest);
//        // リダイレクト
//        TmgUtil.getTmgUtil().setRedirect(sBean, htRequest, this);

    }


    /**
     * システムプロパティ：月次就業実績承認機能を使用するか判定し制御します
     */
    private final String SYSPROP_TMG_BULK_MONTHLY_APPROVAL = "TMG_BULK_MONTHLY_APPROVAL";
    private final String Cs_YES = "yes";
    private Boolean gbMonthlyApproval = null;

    /**
     * システムプロパティから値を取得後、月次就業実績承認機能を使用するか判定し値を返却します
     *
     * @return boolean(true : 使用する 、 false : 使用しない)
     */
    public boolean isMonthlyApproval(PsDBBean psDBBean) {

        if (gbMonthlyApproval == null) {

            String sMonthlyApproval = psDBBean.getSystemProperty(SYSPROP_TMG_BULK_MONTHLY_APPROVAL);


            if (sMonthlyApproval != null && Cs_YES.equalsIgnoreCase(sMonthlyApproval)) {
                gbMonthlyApproval = true;
            } else {
                gbMonthlyApproval = false;
            }
        }

        return gbMonthlyApproval;
    }


    /**
     * 月次承認エラーチェックを行う。
     *
     * @param psCustId  顧客コード
     * @param psCompId  法人コード
     * @param psEmpId   職員番号
     * @param psDyyyyMm 対象年月
     * @return boolean  チェック結果（チェック結果ＯＫ：true、チェック結果ＮＧ：false）
     */
    private boolean isCheckMonthly(String psCustId, String psCompId, String psEmpId, String psDyyyyMm) {

        // 月次承認エラーチェックを行う。
        String sChkRes = iTmgMonthlyService.checkMonthly(psEmpId, psDyyyyMm, psCustId, psCompId);

        // チェック結果エラーがなければ、チェック結果ＯＫとする。
        if ("0".equals(sChkRes)) {
            return true;
        }

        return false;
    }

    /**
     * 指定の職員の指定年月における、月次承認エラーチェック結果を取得する。
     *
     * @param psCustId  顧客コード
     * @param psCompId  法人コード
     * @param psEmpId   職員番号
     * @param psDyyyyMm 対象年月
     * @return boolean  チェック結果（チェック結果ＯＫ：true、チェック結果ＮＧ：false）
     */
    private boolean isErrForCheckMonthlyApproval(String psCustId, String psCompId, String psEmpId, String psDyyyyMm) {
        boolean bRes = true;
        // 月次承認エラーチェックを行う。
        String resData = iTmgMonthlyService.checkMonthly(psEmpId, psDyyyyMm, psCustId, psCompId);

        // チェック結果エラーがなければ、チェック結果ＯＫとする。
        if ("0".equals(resData)) {
            bRes = false;
        }

        return bRes;
    }


    private final String Cs_TRUE = "true";

    /**
     * 「超勤命令権限が無い、かつ申請中の超勤命令が登録されている時」のアラート表示対象者か判定する。
     *
     * @param empId             対象職員番号
     * @param targetDate        対象日
     * @param nonAppOverhourFlg 対象職員氏名（※対象日に未承認の超過勤務命令が無い場合はNULLを指定してください）
     * @return 判定結果（true：対象者、false：対象外者）
     * @throws Exception
     */
    public boolean isDisableNoOverHourpermApprovalEmp(String empId, String targetDate, String nonAppOverhourFlg, PsDBBean psDBBean) throws Exception {
        // 機能の使用可否設定
        String gDisableNoOverhourpermApproval = psDBBean.getSystemProperty(TmgUtil.Cs_CYC_PROP_NAME_TMG_DISABLE_NO_OVERHOURPERM_APPROVAL);
        // 設定を返す
        boolean flg = gDisableNoOverhourpermApproval != null && Cs_TRUE.equalsIgnoreCase(gDisableNoOverhourpermApproval) // システムプロパティ「TMG_DISABLE_NO_OVERHOURPERM_APPROVAL」がtrue、かつ
                && !referList.hasAuthorityAtEmployee(targetDate, empId, TmgUtil.Cs_AUTHORITY_OVERTIME)  // 承認者が超勤命令権限を持っていない、かつ
                && "1".equals(nonAppOverhourFlg);                                                        // 対象日に申請中の超勤命令がある場合は、アラート表示対象者と判定する（=trueを返す）
        return flg;
    }


    /**
     * 一括承認を出来ない『承認状態が「承認待」状態で、超勤命令を有する未終業打刻』状態であるか判定する。
     *
     * @return boolean(true : 一括承認不可 、 false : 一括承認可能)
     */
    public boolean isNoClosetpWithOvertime(PsDBBean psDBBean, String empName) {

        // 「「承認待」の超勤命令を有する未終業打刻者をチェックする機能」の使用可否設定がオフの場合、判定不要の為、一律falseを返す。
        if (!isCheckNoClosetpWithOvertime(psDBBean)) {
            return false;
        }

        // 『承認状態が「承認待」状態で、超勤命令を有する未終業打刻』状態の職員氏名を取得する。（条件に該当しない場合、空文字となる）

        // 該当者氏名が取得できた場合、trueを返す。
        if (StringUtils.isNotEmpty(empName)) {
            return true;
        }

        return false;
    }

    /**
     * システムプロパティ：「「承認待」の超勤命令を有する未終業打刻者をチェックする機能」の使用可否設定
     */
    private Boolean gsCheckNoClosetpWithOvertime = null;

    /**
     * システムプロパティから「「承認待」の超勤命令を有する未終業打刻者をチェックする機能」の使用可否設定を取得します。
     *
     * @return boolean(true : 使用する 、 false : 使用しない)
     */
    public boolean isCheckNoClosetpWithOvertime(PsDBBean psDBBean) {

        // 一度も判定を行っていない場合のみ、システムプロパティを参照し、判定を行う。
        if (gsCheckNoClosetpWithOvertime == null) {

            String sCheckNoClosetpWithOvertime = psDBBean.getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_CHECK_NO_CLOSETP_WITH_OVERTIMEINST4PERMSTATLIST);

            // 設定値「yes」の場合、判定結果をtrue(使用する)とする。
            if (sCheckNoClosetpWithOvertime != null && Cs_YES.equalsIgnoreCase(sCheckNoClosetpWithOvertime)) {
                gsCheckNoClosetpWithOvertime = true;
            } else {
                gsCheckNoClosetpWithOvertime = false;
            }

        }

        return gsCheckNoClosetpWithOvertime;
    }


    /**
     * 日付形式「yyyy/mm/dd」のString型文字列をDate型にキャストします。
     *
     * @param strDate
     * @return Dateオブジェクト
     */
    private Date toDate(String strDate) {

        Date date = new Date();
        try {
            DateFormat format = new SimpleDateFormat(FORMAT_DATE_TYPE1);
            date = format.parse(strDate);
        } catch (ParseException e) {

        }

        return date;
    }


    private final String DISABLED = "true";

    /**
     * 月次就業実績対象者選択チェックボックスの表示制御を行う
     *
     * @param psDailyCount 表示月の日数
     * @param psEmployeeId 対象者職員番号
     * @param psStatus     承認状態
     * @param psStartDay   対象期間開始日
     * @param psEndDay     対象期間終了日
     * @return String(表示しない ： disabled 、 表示する ： 空白)
     */
    public String getEnableCheckBox(PsDBBean psDBBean,String psDailyCount, String psEmployeeId, String psStatus, String psStartDay, String psEndDay) {
        if (isFlex( psDBBean,psDBBean.getCustID()
                , psDBBean.getTargetComp()
                , psEmployeeId
                , psDBBean.toDBDate(psEndDay)
        ) ){
            if(!getNeedTime4Flex(
                    psDBBean,
                    psDBBean.getCustID()
                    , psDBBean.getTargetComp()
                    , psEmployeeId
                    , psDBBean.toDBDate(psEndDay)
            ).equals("0:00")){
                return DISABLED;
            }
        }
        if (CsApprovedDailyCount.equals(psDailyCount) && !TmgUtil.Cs_MGD_DATASTATUS_5.equals(psStatus) &&
                existsDispMonthlyApproval(psEmployeeId, psStartDay, psEndDay)) {
            return "";
        }

        return DISABLED;
    }

    // フレックス対象者の判定
    private Boolean judgeFlex = false;
    // フレックス対象者の判定
    private String needTime4Flex = null;

    /**
     * フレックス対象者getter
     *
     * @return 状態
     */
    public boolean isFlex(
            PsDBBean psDBBean,
            String custId
            , String compCode
            , String employeeCode
            , String baseDate
    ) {
        judgeFlex = TmgUtil.isFlex(psDBBean
                , custId
                , compCode
                , employeeCode
                , baseDate);

        return judgeFlex.booleanValue();
    }
    /**
     * 月間勤務時間に対して、足りない時間数を取得
     *
     * @return 状態
     */
    public String getNeedTime4Flex(
            PsDBBean psDBBean,
            String custId
            , String compCode
            , String employeeCode
            , String baseDate
    ) {
        needTime4Flex = TmgUtil.getNeedTime4Flex(psDBBean
                , custId
                , compCode
                , employeeCode
                , baseDate);

        return needTime4Flex;
    }

    private final String CsApprovedDailyCount = "0";

    /**
     * 日別承認状況一覧画面の一括承認ボタンの表示可能か判定します。
     * <p>
     * 以下の条件を満たしている場合に表示可能になります。
     * <ol>
     *  <li>一覧で表示されている職員の中に該当月の日次情報が全て「承認済」の職員が存在する。</li>
     *  <li>一覧で表示されている職員の中に該当月のステータスが「未承認」の職員が存在する。</li>
     *  <li>勤怠管理サイトの場合は1．と2．の条件が真であれば真を返す。</li>
     *  <li>勤怠承認サイトの場合は、1．と 2．の条件が真でかつ、
     *      一覧に表示される職員の中に「勤怠承認権限」を持った職員が存在する。</li>
     * </ol>
     * </p>
     *
     * @return boolean 表示制御値
     */
    public boolean isDispEnableBatchApproval(PsDBBean psDBBean, List<TmgMonthlyInfoVO> tmgMonthlyInfoVOList) {

        if (!isMonthlyApproval(psDBBean)) {
            return false;
        }

        for (TmgMonthlyInfoVO tmgMonthlyInfoVO : tmgMonthlyInfoVOList) {

            if (CsApprovedDailyCount.equals(tmgMonthlyInfoVO.getDailyCount()) &&
                    TmgUtil.Cs_MGD_DATASTATUS_0.equals(tmgMonthlyInfoVO.getTmoCstatusflg())) {

                return existsDispMonthlyApproval(tmgMonthlyInfoVO.getEmpid(),
                        getReqDYYYYMM(), tmgMonthlyInfoVO.getLastBaseDate());
            }
        }

        return false;
    }

    /**
     * 勤怠承認権限(月次)があるかどうか判定を行い返却する
     *
     * @return boolean(true : 権限あり 、 false : 権限なし)
     */
    private Boolean existsDispMonthlyApproval(String psEmployeeId, String psStartDay, String psEndDay) {

        try {

            if (!referList.hasAuthorityAtEmployee(psStartDay, psEndDay, psEmployeeId, TmgUtil.Cs_AUTHORITY_MONTHLYAPPROVAL)) {
                return false;
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 月次一覧、また日次承認画面表示時の打刻反映処理
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void execReflectionTimePunch(String empSql, PsDBBean psDBBean) {

        // アクション
        String action = _sAction;

        // メニューから初期の月次一覧画面表示時はアクションが未設定なので、月次一覧画面表示アクションを設定する。
        if (StrUtil.hasEmpty(_sAction)) {

            action = ACT_DISP_MONTHLY;
        }

        // 打刻反映処理対象の期間を取得
        String stDate;  // 開始日
        String endDate; // 終了日

        // 月次一覧画面表示時
        if (action.equals(ACT_DISP_MONTHLY)) {

            // 表示月のカレンダークラス
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(getReqDYYYYMM().split("/")[0]),
                    Integer.parseInt(getReqDYYYYMM().split("/")[1]) - 1, // カレンダークラスでは月が０から始まるので１引いてあげる
                    Integer.parseInt(getReqDYYYYMM().split("/")[2]), 0, 0);

            // 日付を月末日にする。
            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

            // 運用月のカレンダークラス
            Calendar sysCal = Calendar.getInstance();
            sysCal.set(Integer.parseInt(TmgUtil.getSysdate().split("/")[0]),
                    Integer.parseInt(TmgUtil.getSysdate().split("/")[1]) - 1, // カレンダークラスでは月が０から始まるので１引いてあげる
                    Integer.parseInt(TmgUtil.getSysdate().split("/")[2]), 0, 0);

            // 運用日 <= 表示月末日の場合、表示月が運用月なので運用日を打刻反映処理の期間終了日とする。（未来については打刻反映処理対象外なので）
            if (sysCal.compareTo(cal) <= 0) {

                // 運用月の月初～運用日までを対象期間とする。
                stDate = sysCal.get(Calendar.YEAR) + "/" + (sysCal.get(Calendar.MONTH) + 1) + "/" + sysCal.getMinimum(Calendar.DATE);
                endDate = sysCal.get(Calendar.YEAR) + "/" + (sysCal.get(Calendar.MONTH) + 1) + "/" + sysCal.get(Calendar.DATE);
            } else {

                // 表示月の月初～月末までを対象期間とする。
                stDate = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.getMinimum(Calendar.DATE);
                endDate = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.getActualMaximum(Calendar.DATE);
            }

            // 日次承認画面表示時
        } else {

            // 日次画面を表示する場合は打刻反映処理の対象が表示日のみなので開始、終了日共に表示日とする。
            stDate = _reqDYYYYMMDD;
            endDate = _reqDYYYYMMDD;
        }

        // 打刻反映処理
        iTmgTriggerService.buildSQLForInsertTmgTriggerByTimePunch(psDBBean.getUserCode(), action, stDate, endDate, empSql);
        iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query().eq("TTR_CCUSTOMERID", psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode())
                .eq("TTR_CMODIFIERUSERID", psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID", APPLICATION_ID + "_" + action));

        // TODO
        //TmgUtil.checkInsertErrors(setInsertValues(vQuery, BEANDESC), session, BEANDESC);
    }

    /**
     * 対象月の日数を返す
     *
     * @param targetDate 対象年月データ
     * @return int 対象月の日数
     */
    public int getActualMaximumOfMonth(String targetDate) {

        String sYear = "";
        String sMonth = "";

        StringTokenizer st = new StringTokenizer(targetDate, FORMAT_SLASH, false);

        sYear = st.nextToken();
        sMonth = st.nextToken();

        // 対象月の日数を求める
        Calendar cal = Calendar.getInstance();
        try {
            // 対象年をセット
            cal.set(Calendar.YEAR, Integer.parseInt(sYear));
            // 対象月をセット
            cal.set(Calendar.MONTH, Integer.parseInt(sMonth) - 1);
        } catch (NumberFormatException e) {
            throw e;
        }

        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        return days;
    }
//
//    /**
//     * メインメソッド。
//     */
//    public void execute(PsDBBean psDBBean) throws Exception {
//
//        setExecuteParameter(psDBBean);
//
//        // ■初期表示時：
//        //   　選択した組織、(もしくはグループ)の対象年月(デフォルトでは現在日付時点の年月)時点での
//        //   評価状況一覧コンテンツの参照権限をチェックする。
//        //   参照権限がある場合は、問題なく評価状況一覧を表示する。
//        //   　しかし、参照権限が無い場合は1ヶ月遡った月の参照権限をチェックする。
//        //   1ヶ月遡った月の参照権限があればその月の評価状況一覧を表示し、
//        //   1ヶ月遡った月の参照権限も無い場合は画面に「参照できる職員が存在しません」(文言変更有り)
//        //   メッセージを画面へ表示する。
//        // ■初期表示以外：
//        //   選択した組織、(もしくはグループ)の対象年月時点での評価状況一覧コンテンツの参照権限をチェックする。
//        //   権限があれば問題なく評価状況一覧を表示する。
//        //   権限が無い場合は画面に「参照できる職員が存在しません」(文言変更有り)
//        //   メッセージを画面へ表示する。
//        //   ※また、権限はあるが選択している組織(もしくはグループ)に所属している職員が存在しない場合も
//        //     権限が無いのと同じ扱いとする。
//        // 勤怠承認サイト、もしくは勤怠管理サイトの場合に以下の処理を実行する
//        if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) || TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId())) {
//            // 勤怠承認サイトは初期表示時、勤怠管理サイトは初期表示+(組織選択時or組織選択済)の場合
//            // ※勤怠管理サイトの場合、初期表示時でも組織が選択されていない状態なら権限チェックを行わない
//            if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) && StrUtil.isEmpty(_sAction) ||
//                    TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId()) && StrUtil.isEmpty(_sAction) && isSelectSection()
//            ) {
//                // 参照権限チェック(現在時点での年月)
//                if (referList.existsAnyone(getFirstDayOfSysdate()) && referList.isThereSomeEmployees(getFirstDayOfSysdate())) {
//                    setAuthorityMonth(CB_CAN_REFER);
//
//                    // 参照権限が無い場合は、1ヶ月過去のシートの権限をチェックする。
//                } else {
//                    String sPrevMonth = TmgUtil.getFirstDayOfMonth(getFirstDayOfSysdate(), PARAM_PREV_MONTH);
//
//                    // 汎用参照コンポーネントの基準日を基準日の前月(過去)に設定しなおす
//                    referList = new TmgReferList(psDBBean, "PermStatList", sPrevMonth, TmgReferList.TREEVIEW_TYPE_LIST, true);
//
//                    // 参照権限の設定:
//                    // 初期表示時の対象年月の時点の参照権限が無い場合に、
//                    // 1ヶ月過去の参照権限を判定し参照権限がある場合は1ヶ月過去のシートを参照する。
//                    // 権限が無い場合は、参照できない。
//                    if (referList.existsAnyone(sPrevMonth) && referList.isThereSomeEmployees(sPrevMonth)) {
//
//                        // 対象年月が現在の年月の場合、1ヶ月過去の年月を対象年月に設定します
//                        // このif文は、現在「部署A」を選択していて対象年月が変更された状態で「組織B」を選択しなおすと
//                        // 「組織B」の現在日付時点の年月と、その年月-1ヶ月時点での参照権限をチェックします。
//                        // その際に、変更後対象年月が現在年月でない場合にも現在年月-1ヶ月を設定されるのを防ぐ為
//                        // 「対象年月が現在の年月の場合」という条件を実装しています。
//                        if (getFirstDayOfSysdate().equals(getReqDYYYYMM())) {
//                            // 対象年月を1ヶ月過去に設定します
//                            setReqDYYYYMM(sPrevMonth);
//                            // 検索対象年月の前月
//                            _prevMonth = TmgUtil.getFirstDayOfMonth(_reqDYYYYMM, PARAM_PREV_MONTH);
//                            // 検索対象年月の翌月
//                            _nextMonth = TmgUtil.getFirstDayOfMonth(_reqDYYYYMM, PARAM_NEXT_MONTH);
//                        }
//                        setAuthorityMonth(CB_CAN_REFER);
//                    } else {
//                        // 対象年月を元に戻します
//
//                        referList = new TmgReferList(psDBBean, "PermStatList", getReqDYYYYMM(), TmgReferList.TREEVIEW_TYPE_LIST, true);
//
//                        setAuthorityMonth(CB_CANT_REFER);
//                    }
//                }
//
//                // 2007/08/07 	H.Kawabata		参照権限チェック仕様変更対応
//                // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
//                // 翌月の権限があればリンク「>」を画面に表示する。
//                // 権限が無い場合は「>」を表示しない。
//                // ※また、権限はあるが選択している組織(もしくはグループ)に所属している職員が存在しない場合も
//                //   権限が無いのと同じ扱いとする。
//                if (referList.existsAnyone(getNextMonth()) && referList.isThereSomeEmployees(getNextMonth())) {
//                    setAuthorityNextMonth(CB_CAN_REFER);
//                } else {
//                    setAuthorityNextMonth(CB_CANT_REFER);
//                }
//
//                // 初期表示時以外
//                // ※組織を選択していないときは権限チェックを行わない。
//                // 　勤怠管理サイトで組織未選択時に権限チェックを行うとえらーで落ちてしまうので
//                // 　それを防ぐ為に「組織を選択しているとき」という条件を実装しています。
//            } else if (isSelectSection()) {
//                // 参照権限の判定：設定(当月分)
//                // 当月もしくは、先月どちらかの権限が有効な場合は過去に関しては常に表示する(シートがある限り)
//                String sPrevMonth = TmgUtil.getFirstDayOfMonth(getFirstDayOfSysdate(), PARAM_PREV_MONTH);
//                if (referList.existsAnyone(getFirstDayOfSysdate()) && referList.isThereSomeEmployees(getFirstDayOfSysdate()) ||
//                        referList.existsAnyone(sPrevMonth) && referList.isThereSomeEmployees(sPrevMonth)
//                ) {
//                    setAuthorityMonth(CB_CAN_REFER);
//                } else {
//                    setAuthorityMonth(CB_CANT_REFER);
//                }
//
//                // 2007/08/07 	H.Kawabata		参照権限チェック仕様変更対応
//                // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
//                // 翌月の権限があればリンク「>」を画面に表示する。
//                // 権限が無い場合は「>」を表示しない。
//                // ※また、権限はあるが選択している組織(もしくはグループ)に所属している職員が存在しない場合も
//                //   権限が無いのと同じ扱いとする。
//                if (referList.existsAnyone(getNextMonth()) && referList.isThereSomeEmployees(getNextMonth())) {
//                    setAuthorityNextMonth(CB_CAN_REFER);
//                } else {
//                    setAuthorityNextMonth(CB_CANT_REFER);
//                }
//            }
//            // その他のサイトの場合
//        } else {
//            setAuthorityMonth(CB_CAN_REFER);
//        }
//
//
//    }

    /**
     * 処理実行用パラメータの設定を行います。
     *
     * @throws Exception
     */
    public void setExecuteParameter(PsDBBean psDBBean,String txtTmgReferListTreeViewRecordDate) throws Exception {

        _sAction = (String) psDBBean.getRequestHash().get(REQ_ACTION);
        _reqDYYYYMM = (String) psDBBean.getRequestHash().get(REQ_DYYYYMM);
        _reqDYYYYMMDD = (String) psDBBean.getRequestHash().get(REQ_DYYYYMMDD);
        String recordDate = txtTmgReferListTreeViewRecordDate;

        if(recordDate == null|| recordDate.length() == 0){
            recordDate = psDBBean.getSysDate();
        }

        // 検索対象年月の入力がなければ、現在日付月初を検索対象年月する。
        if (_reqDYYYYMM == null || _reqDYYYYMM.length() == 0) {
            _reqDYYYYMM = getFirstDayOfSysdate();
        }

        // TmgReferListの生成
       referList = new TmgReferList(psDBBean, "PermStatList", recordDate, TmgReferList.TREEVIEW_TYPE_LIST, true);

        // 組織コードの取得
        _reqSectionId = referList.getTargetSec();

//        // 組織が選択されているか確認
//        if (_reqSectionId == null || _reqSectionId.length() == 0) {
//            _isSelectSection = false;
//        } else {
//            _isSelectSection = true;
//        }

//        // 検索対象年月の前月
//        _prevMonth = TmgUtil.getFirstDayOfMonth(_reqDYYYYMM, PARAM_PREV_MONTH);
//        // 検索対象年月の翌月
//        _nextMonth = TmgUtil.getFirstDayOfMonth(_reqDYYYYMM, PARAM_NEXT_MONTH);

//        /*
//         * 初期表示時、または組織ツリー再表示時の表示対象日付設定を行う。
//         * 初期表示時は「getReqParm(REQ_ACTION)」がNULLとなる。
//         * 再表示ボタン押下時は「getReqParm(TREEVIEW_KEY_REFRESH_FLG)」に値が設定される。
//         */
        // 組織ツリー基準日情報チェック
        if (referList.getRecordDate() == null) {
            // 今月の月初
            _thisMonth = TmgUtil.getFirstDayOfMonth(psDBBean.getSysDate(), PARAM_THIS_MONTH);
        } else {
            // 組織ツリー基準日
            _thisMonth = TmgUtil.getFirstDayOfMonth(referList.getRecordDate(), PARAM_THIS_MONTH);
//
//            // 初期表示、再表示ボタン使用時処理
//            if (StrUtil.isEmpty(_sAction) || StrUtil.isNotEmpty(psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG))) {
//
//                // 表示日付変更
//                _reqDYYYYMM = TmgUtil.getFirstDayOfMonth(referList.getRecordDate(), PARAM_THIS_MONTH);
//                // 検索対象年月の前月
//                _prevMonth = TmgUtil.getFirstDayOfMonth(referList.getRecordDate(), PARAM_PREV_MONTH);
//                // 検索対象年月の翌月
//                _nextMonth = TmgUtil.getFirstDayOfMonth(referList.getRecordDate(), PARAM_NEXT_MONTH);
//            }
        }

    }

    /**
     * システム日付の月初日(1日)を返却します。
     * <p>
     * [例] "2007/03/23" --> "2007/03/01"
     * </p>
     *
     * @return 基準日
     */
    private String getFirstDayOfSysdate() {

        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TYPE1);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        Date date = calendar.getTime();

        return sdf.format(date);
    }

//    /**
//     * 対象組織有無フラグを返却します。
//     *
//     * @return 対象組織有無フラグ
//     */
//    public boolean isSelectSection() {
//        return _isSelectSection;
//    }

    /**
     * 勤怠シートの参照権限(基準日の翌月)設定メソッド
     */
    public void setAuthorityMonth(boolean bValue) {
        _authorityMonth = bValue;
    }

    /**
     * 対象勤務年月日を返却します。
     */
    public String getReqDYYYYMMDD() {
        return _reqDYYYYMMDD;
    }

    public void setReqDYYYYMM(String sValue) {
        this._reqDYYYYMM = sValue;
    }

//    /**
//     * 対象年月の前月を返却します。
//     */
//    public String getPrevMonth() {
//        return _prevMonth;
//    }
//
//    /**
//     * 対象年月の翌月を返却します。
//     */
//    public String getNextMonth() {
//        return _nextMonth;
//    }

    /**
     * 今月の月初を返却します。
     */
    public String getThisMonth() {
        return _thisMonth;
    }

    /**
     * 今日の日付を返す(基準日があればその日付)
     *
     * @return String 今日の日付
     */
    private String getToDay() {

        if (referList != null) {
            return referList.getRecordDate();
        } else {
            return getSysdate();
        }
    }

    /**
     * システム日付を返す
     *
     * @return String システム日付
     */
    private String getSysdate() {

        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TYPE1);
        return sdf.format(new Date());
    }

    /**
     * 勤怠シートの参照権限(基準日の翌月)設定メソッド
     */
    public void setAuthorityNextMonth(boolean bValue) {
        _authorityNextMonth = bValue;
    }

    /**
     * 勤怠シートの参照権限(基準日の翌月)取得メソッド
     */
    public boolean getAuthorityNextMonth() {
        return _authorityNextMonth;
    }

    /*********************************************executeReadMonthlyList***********************************************/

    /**
     * 表示月遷移リスト情報を取得する
     *
     * @param psDBBean  PsDBBean
     * @return 表示月遷移リスト情報
     * @throws Exception
     */
    public List<DispMonthlyVO> dispMonthlyList(PsDBBean psDBBean) throws Exception {

        String empSql = referList.buildSQLForSelectEmployees();
        // 打刻反映処理を行う。
        execReflectionTimePunch(empSql, psDBBean);
        // 6 表示月遷移リスト情報取得
        return iTmgMonthlyService.buildSQLForSelectDispTmgMonthlyList(getThisMonth(), empSql);
    }

    /**
     * 前月リンクを作成する為の勤怠データ件数を取得する
     *
     * @return int
     */
    public int dispMonthlyPrev() {

        String empSql = referList.buildSQLForSelectEmployees();
        // 3 表示対象月の前月データを持つ職員数
        int tmgMonthlyInfoPrevCount = iTmgMonthlyInfoService.buildSQLForSelectTmgMonthlyInfoCount(empSql, TmgUtil.getFirstDayOfMonth(getReqDYYYYMM(), PARAM_PREV_MONTH));

        return tmgMonthlyInfoPrevCount;
    }

    /**
     * 翌月リンクを作成する為の勤怠データ件数を取得する
     *
     * @return int
     */
    public int dispMonthlyNext() {

        String empSql = referList.buildSQLForSelectEmployees();

        // 4 表示対象月の翌月データを持つ職員数
        int tmgMonthlyInfoNextCount = iTmgMonthlyInfoService.buildSQLForSelectTmgMonthlyInfoCount(empSql, TmgUtil.getFirstDayOfMonth(getReqDYYYYMM(), PARAM_NEXT_MONTH));
        return tmgMonthlyInfoNextCount;
    }


    /**
     * カレンダーテーブルより休日フラグを取得する
     *
     * @param psDBBean  PsDBBean
     * @return CalenderVo
     */
    public CalenderVo selectGetCalendarList(PsDBBean psDBBean) {
        // 1 カレンダー情報の取得
        CalenderVo calenderVo = iTmgCalendarService.selectGetCalendarList(psDBBean.getCustID(),
                psDBBean.getCompCode(), referList.getTargetSec(), referList.getTargetGroup(), getReqDYYYYMM().substring(0, 4), getReqDYYYYMM());
        return calenderVo;
    }

    /**
     * 一覧のタイトル取得して、編集する
     *
     * @param psDBBean  PsDBBean
     * @return List<OneMonthDetailVo>
     */
    public List<OneMonthDetailVo> selectDayCount(PsDBBean psDBBean) {
        // 2 対象勤務年月の1ヶ月間の日付・曜日を取得
        List<OneMonthDetailVo> oneMonthDetailVoList = iTmgCalendarService.selectDayCount(getReqDYYYYMM());
        // 1 カレンダー情報の取得
        CalenderVo calenderVo = iTmgCalendarService.selectGetCalendarList(psDBBean.getCustID(),
                psDBBean.getCompCode(), referList.getTargetSec(), referList.getTargetGroup(), getReqDYYYYMM().substring(0, 4), getReqDYYYYMM());

        Map map = BeanUtil.beanToMap(calenderVo);

        //List<String> tcaCholflgList = new ArrayList<>();
        DecimalFormat nDayFormat = new DecimalFormat(FORMAT_ZERO);

        int i = 1;
        for (OneMonthDetailVo oneMonthDetailVo : oneMonthDetailVoList) {

            oneMonthDetailVo.setTcaCholflg(String.valueOf(map.get("tcaCholflg" + nDayFormat.format(i))));
            i++;
        }

        return oneMonthDetailVoList;
    }

    /**
     * 職員氏名と、承認ステータス状態を取得する
     *
     * @param psDBBean
     * @return Map
     */
    public Map getTmgMonthlyInfoVOList(PsDBBean psDBBean) {

        String empSql = referList.buildSQLForSelectEmployees();

        // 月次一覧表示データを取得する。
        // 0 表示月情報の取得
        List<ColNameDto> colNameList = new ArrayList<>();

        int monthLen = getActualMaximumOfMonth(getReqDYYYYMM());
        DecimalFormat nDayFormat = new DecimalFormat(FORMAT_ZERO);

        for (int i = 1; i <= monthLen; i++) {
            ColNameDto dto = new ColNameDto();
            dto.setColName("TMI_CINFO" + nDayFormat.format(i));
            dto.setDisppermStatus("DISPPERM_STATUS" + i);
            dto.setDisppermStatusName("DISPPERM_STATUS_NAME" + i);
            colNameList.add(dto);
        }

        List<TmgMonthlyInfoVO> tmgMonthlyInfoVOList = iTmgMonthlyInfoService.buildSQLForSelectTmgMonthlyInfo(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                getReqDYYYYMM(),
                psDBBean.getLanguage(),
                getToDay(),
                empSql,
                colNameList
        );

        for (TmgMonthlyInfoVO tmgMonthlyInfoVO : tmgMonthlyInfoVOList) {

            // チェックボックスの表示状態を取得（disabledかどうか）
            String sChkBoxStatus = this.getEnableCheckBox(psDBBean,tmgMonthlyInfoVO.getDailyCount(),
                    tmgMonthlyInfoVO.getEmpid(),
                    tmgMonthlyInfoVO.getTmoCstatusflg(),
                    getReqDYYYYMM(),
                    tmgMonthlyInfoVO.getLastBaseDate());

            // 月次承認対象の場合、対象職員を月次承認エラーチェックする。
            if ("".equals(sChkBoxStatus)) {

                // エラーチェック結果、ＮＧの場合、チェックボックスをdisabled化設定する。
                if (isErrForCheckMonthlyApproval(psDBBean.getCustID(), psDBBean.getCompCode(), tmgMonthlyInfoVO.getEmpid(), getReqDYYYYMM())) {
                    sChkBoxStatus = "true";

                    // エラーチェックＮＧだった職員をエラー氏名リストへ追加
                    // vecErrEmpname.add(tmgMonthlyInfoVO.getEmpname());
                }
            }
            if(!existsDispMonthlyApproval(tmgMonthlyInfoVO.getEmpid(),getReqDYYYYMM(),tmgMonthlyInfoVO.getLastBaseDate())){
                tmgMonthlyInfoVO.setStatusName("不");
            }
            tmgMonthlyInfoVO.set_disabled(sChkBoxStatus);
        }

        Map map = MapUtil.newHashMap();
        map.put("tmgMonthlyInfoVOList", tmgMonthlyInfoVOList);
        boolean approval = isDispEnableBatchApproval(psDBBean, tmgMonthlyInfoVOList);
        map.put("approval", approval);
        return map;
    }


    /*******終了**************************************executeReadMonthlyList***********************************************/

    /******開始***************************************executeReadTmgDaily***********************************************/

    /**
     * 所属情報を取得する
     *
     * @param psDBBean PsDBBean
     * @return 所属情報
     */
    public String getSectionName(PsDBBean psDBBean) {

        // 組織の職員取得ｓｑｌ
        String empSql = referList.buildSQLForSelectEmployees();

        // 打刻反映処理
        execReflectionTimePunch(empSql, psDBBean);

        // 所属情報
        String sectionName = iMastOrganisationService.buildSQLForSelectEmployeeDetail(_reqSectionId, getToDay(), psDBBean.getCustID(), psDBBean.getCompCode());
        return sectionName;
    }

    /**
     * 日別一覧表示の為のプロセスを実行します。
     *
     * @return Map
     */
    public Map getReadTmgDaily(PsDBBean psDBBean) throws Exception {

        Map resultMap = MapUtil.newHashMap();

        // 組織の職員取得ｓｑｌ
        String empSql = referList.buildSQLForSelectEmployees();

        // 承認状況表示項目を取得しセット
        List<ItemVO> itemVOList = iMastGenericDetailService.buildSQLForSelectTmgDisppermstatlist(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getLanguage());
        resultMap.put("itemVOList", itemVOList);

        List<String> monthlyItems = new ArrayList<String>();

        for (ItemVO itemVO : itemVOList) {
            monthlyItems.add(itemVO.getMgdCsql() + " AS " + itemVO.getTempColumnid());
        }

        // 承認状況欄情報
        List<HashMap> tmgDailyMapList = iTmgDailyService.buildSQLForSelectTmgDaily(_reqDYYYYMMDD, empSql, monthlyItems);

        // 承認ボタン表示制御
        boolean approval = false;

        String sysdate = psDBBean.getSysDate();

        // 未来日については、一括承認ボタンを表示させない。
        if (0 <= toDate(sysdate).compareTo(toDate(_reqDYYYYMMDD))) {

            /*
             * 表示対象の職員について、判定を行う。
             * だれか一人でも条件を満たせば、一括承認ボタンを表示とする。
             */
            for (HashMap map : tmgDailyMapList) {

                /*
                 * 承認対象となる承認状態「承認待」でなければ、次の職員を処理する。
                 * また、『承認状態が「承認待」状態で、超勤命令を有する未終業打刻』状態をチェックする。
                 */
                if (TmgUtil.Cs_MGD_DATASTATUS_3.equals(map.get("TRANS_DISPPERMSTATUS")) && !isNoClosetpWithOvertime(psDBBean, String.valueOf(map.get("NOTCLOSETPWITHOVERTIME_EMPNAME")))) {

                    // 承認サイトの場合は、一覧に表示される職員の中に「勤怠承認権限」を持った職員が存在する場合のみ、一括承認ボタンを表示させる。
                    if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId())) {
                        if (referList.hasAuthorityAtEmployee(_reqDYYYYMMDD, String.valueOf(map.get("TDA_CEMPLOYEEID")), TmgUtil.Cs_AUTHORITY_RESULT)) {
                            approval = true;
                            break;
                        }

                        // 勤怠管理サイトの場合、一律、一括承認ボタンを表示させる。
                    } else {
                        approval = true;
                        break;
                    }
                }
            }
        }

        resultMap.put("approval", approval);

        for (HashMap map : tmgDailyMapList) {

            /*
             * 対象職員の勤怠承認権限チェック
             * 承認権限があり、且つ承認状態が「承認待」の場合、チェックボックスにチェックを設定
             * それ以外の場合は、チェックボックスを無効（disabled）にする。
             */
            // 対象職員番号、日次ステータス
            String sEmpId = String.valueOf(map.get("TDA_CEMPLOYEEID"));
            String sStatus = String.valueOf(map.get("TRANS_DISPPERMSTATUS"));

            // 対象職員への承認権限状態
            boolean bAuthorityEmp = referList.hasAuthorityAtEmployee(getReqDYYYYMMDD(), sEmpId, TmgUtil.Cs_AUTHORITY_RESULT);

            /*
             * チェックボックスの制御処理
             *
             * ・日次ステータスが「承認待」以外はチェックボックスを無効化する。
             * ・終業打刻が無く、予定終業時刻以降の超過勤務申請・命令がある場合はチェックボックスを無効化する。
             * ・システムプロパティ「TMG_DISABLE_NO_OVERHOURPERM_APPROVAL」がオン設定で、
             *   且つ、対象者への超過勤務命令権限が無く、未承認の超過勤務命令の登録がある場合はチェックボックスを無効化する。
             */
            String sChkOption = "";

            boolean flg = bAuthorityEmp
                    && TmgUtil.Cs_MGD_DATASTATUS_3.equals(sStatus)
                    && !isNoClosetpWithOvertime(psDBBean, String.valueOf(map.get("NOTCLOSETPWITHOVERTIME_EMPNAME")))
                    && !isDisableNoOverHourpermApprovalEmp(sEmpId, getReqDYYYYMMDD(), String.valueOf(map.get("NOTCLOSETPWITHOVERTIME_EMPNAME")), psDBBean);
            if (!flg) {
                // チェックボックスを無効設定
                sChkOption = "true";
            }

            map.put("_disabled", sChkOption);

            // 承認権限が有る場合は、画面遷移リンク付で現在の承認状態を表示してあげる。
            if (bAuthorityEmp) {
                // 承認権限が無い場合は、「不」と表示する。
            } else {
                map.put("COLUMNID1",STATUS_UNAPPLYAUTHORITY);
            }
        }

        resultMap.put("tmgDailyMapList", tmgDailyMapList);

        return resultMap;
    }
    /******終了***************************************executeReadTmgDaily***********************************************/

    /******開始****************************************executeUpdateTmgDaily*********************************************/

    /**
     * 一括承認処理の為のプロセスを実行します。
     * <p>
     * 	<ul>
     * 		<li>更新対象職員のROWIDを取得するSQL文を取得する。</li>
     * 		<li>結果セットからROWIDを取得し編集する。</li>
     * 		<li>一括承認対象者を取得するSQL文を取得する。</li>
     * 		<li>日別テーブルから更新対象者のみ検出し、
     * 			勤怠トリガーテーブルにレコードを挿入するSQL文を取得する。</li>
     * 		<li>勤怠トリガーテーブルにレコードを削除するSQL文を取得する。</li>
     * 		<li>クエリを実行する。</li>
     * 	</ul>
     * </p>
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void executeUpdateTmgDaily(PsDBBean psDBBean) {

        String[] empIds = ((String) psDBBean.getRequestHash().get(REQ_EXECUTEEMPID)).split(",");

        String empSql = referList.buildSQLForSelectEmployees();

        // サイトIDを判定し更新対象の職員番号
        List<String> empIdList = iMastEmployeesService.selectEmpIdListForTmgDaily(psDBBean.getSiteId(), _reqDYYYYMMDD, empSql, empIds);

        // 更新対象職員のROWIDを取得する
        List<String> rowIdList = iTmgDailyService.buildSQLForSelectObjEmpForUpdate(empIdList, getReqDYYYYMMDD());

        //  一括承認データを更新する
        String sProgramId = APPLICATION_ID + "_" + _sAction;
        iTmgDailyService.buildSQLForUpdateTmgDaily(psDBBean.getUserCode(), sProgramId, getReqDYYYYMMDD(), empIdList);

        // 承認時に超過勤務でステータスが申請中のものがある場合に確認済へ変更
        // 一括承認データを更新する
        iTmgDailyDetailService.buildSQLForUpdateTmgDailyDetail(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getUserCode(), sProgramId, getReqDYYYYMMDD(), empIdList, "TMG_ITEMS|Overhours");

        iTmgTriggerService.buildSQLForInsertTmgTrigger(psDBBean.getUserCode(), sProgramId, _sAction, _reqDYYYYMMDD, rowIdList);

        iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query().eq("TTR_CCUSTOMERID", psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode())
                .eq("TTR_CMODIFIERUSERID", psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID", APPLICATION_ID + "_" + _sAction));

        // TODO
        //TmgUtil.checkInsertErrors(setInsertValues(vQuery,BEANDESC), session, BEANDESC);

    }

    /******終了****************************************executeUpdateTmgDaily*********************************************/


    /******開始****************************************executeUpdateTmgMonthly*********************************************/
    /**
     * 月次就業実績一括承認処理の為のプロセスを実行します。
     * <p>
     *  <ul>
     *      <li>月次情報(ステータス)を更新するSQL文を取得する。</li>
     *      <li>クエリを実行する。</li>
     *  </ul>
     * </p>
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void executeUpdateTmgMonthly(PsDBBean psDBBean) {

        String sCustId = psDBBean.getCustID();
        String sCompId = psDBBean.getCompCode();
        String sDyyyyMm = getReqDYYYYMM();

        String sModifierProgramId = APPLICATION_ID + "_" + _sAction;

        // 更新処理は職員単位で行う。
        String[] sEmpId = ((String) psDBBean.getRequestHash().get(REQ_EXECUTEEMPID)).split(",");

        for (int i = 0; i < sEmpId.length; i++) {

            /*
             * 月次承認機能がオンの場合、
             * 月次承認エラーチェック結果がＯＫの職員のみ更新を行う。
             */
            if (isMonthlyApproval(psDBBean) && !isCheckMonthly(sCustId, sCompId, sEmpId[i], sDyyyyMm)) {
                continue;
            }

            // 月次情報の更新
            iTmgMonthlyInfoService.buildSQLForUpdateTmgMonthly(sCustId, sCompId, sEmpId[i], sDyyyyMm, psDBBean.getUserCode(), sModifierProgramId);
        }

        // TODO
        // TmgUtil.checkInsertErrors(setInsertValues(vQuery,BEANDESC), session, BEANDESC);
    }

    /******終了****************************************executeUpdateTmgMonthly*********************************************/
}
