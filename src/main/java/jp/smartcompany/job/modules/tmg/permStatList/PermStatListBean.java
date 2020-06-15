package jp.smartcompany.job.modules.tmg.permStatList;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.text.DecimalFormat;
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
     * PsDBBean
     */
    private final PsDBBean _psDBBean;

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

    /** リクエストキー - アクション */
    public static final String REQ_ACTION          = "txtAction";
    /** リクエストキー - 対象組織 */
    public static final String REQ_SECTION_ID      = "txtSectionId";
    /** リクエストキー - 対象勤務年月日 */
    public static final String REQ_DYYYYMMDD       = "txtDYYYYMMDD";
    /** リクエストキー - 対象年月 */
    public static final String REQ_DYYYYMM         = "txtDYYYYMM";
    /** リクエストキー - 対象職員番号 */
    public static final String REQ_CEMPLOYEEID     = "txtCEMPLOYEEID";
    /** リクエストキー - リダイレクトBean名 */
    public static final String REQ_REDIRECT_BEAN   = "txtRedirectBean";
    /** リクエストキー - リダイレクト先アクション値 */
    public static final String REQ_REDIRECT_ACTION = "txtRedirectACTION";
    /** リクエストキー - コールバック先アクション値 */
    public static final String REQ_CALL_ACTION     = "txtCallBeanAction";
    /** リクエストキー - 処理対象職員番号 */
    public static final String REQ_EXECUTEEMPID    = "txtExecuteEmpId";
    /** リクエストキー - 再表示ボタン使用判定用 */
    private static final String TREEVIEW_KEY_REFRESH_FLG  = "txtTmgReferListTreeViewRefreshFlg";

    /** アクション - 月別一覧画面表示) */
    public static final String ACT_DISP_MONTHLY   = "ACT_DISP_MONTHLY";
    /** アクション - 月別一括承認処理 */
    public static final String ACT_MONTHLY_PERMIT = "ACT_MONTHLY_PERMIT";
    /** アクション - 日別一括承認画面表示 */
    public static final String ACT_EDIT_DAIRY     = "ACT_EDIT_DAIRY";
    /** アクション - 日別一括承認処理 */
    public static final String ACT_PERMIT         = "ACT_PERMIT";


    /** 汎用参照リスト */
    private TmgReferList referList = null;

    /** 日付形式1 */
    private static final String FORMAT_DATE_TYPE1 = "yyyy/MM/dd";

    private static final String FORMAT_SLASH      = "/";
    private static final String FORMAT_ZERO       = "00";

    /** 1ヵ月後 */
    private static final int PARAM_NEXT_MONTH =  0;
    /** 今月 */
    private static final int PARAM_THIS_MONTH = -1;
    /** 1ヶ月前 */
    private static final int PARAM_PREV_MONTH = -2;

    /** アクション */
    private String _sAction           = null;
    /** サイトID */
    private String _reqSiteId         = null;
    /** システム年月日 */
    private String _sysdate           = null;
    /** 対象組織 */
    private String _reqSectionId      = null;
    /** 対象勤務年月日 */
    private String _reqDYYYYMMDD      = null;
    /** 対象年月(検索対象年月) */
    private String _reqDYYYYMM        = null;
    /** 対象職員番号 */
    private String _reqEmployeeId     = null;
    /** 登録対象職員番号 */
    private String _reqExecuteEmpId   = null;
    /** ログイン顧客コード */
    private String _loginCustId   = null;
    /** ログイン法人コード */
    private String _loginCompCode = null;
    /** ログインユーザーコード */
    private String _loginUserCode = null;
    /** 対象年月の前月 */
    private String _prevMonth = null;
    /** 対象年月の翌月 */
    private String _nextMonth = null;
    /** 今月 */
    private String _thisMonth = null;

    /**
     * 対象組織が選択されているかどうかを表します。
     * <p>
     * 	true(選択あり) / false(選択なし) <br>
     * 	初回遷移時などでは汎用参照コンポーネントで組織が選択されていない状態
     * 	のためfalseとなります。
     * </p>
     */
    private boolean _isSelectSection = false;

    /** 勤怠シートの参照権限(基準日の翌月) */
    boolean _authorityNextMonth       = false;
    /** 勤怠シートの参照権限(基準月) */
    boolean _authorityMonth           = false;
    /** 参照権限：参照可能 */
    private static final boolean CB_CAN_REFER = true;
    /** 参照権限：参照不可能  */
    private static final boolean CB_CANT_REFER = false;

    /**
     * 検索対象年月を返す
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
    public void actDispMonthly(ModelMap modelMap) throws Exception{
//        execute(modelMap);

        // 月別一覧表示の為のプロセスを実行します。
        executeReadMonthlyList(modelMap);
    }


    /**
     * 承認サイト・管理サイト
     * 日別一括承認画面表示
     * <p>
     * ACT_EDIT_DAIRY
     *
     * @param modelMap
     */
    public void actEditDairy(ModelMap modelMap) throws Exception{
//        execute(modelMap);
        executeReadTmgDaily(modelMap);

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
        executeUpdateTmgMonthly();
        executeReadMonthlyList(modelMap);
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
        executeUpdateTmgDaily();
        executeReadMonthlyList(modelMap);
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
        executeRedirect(modelMap);
    }


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
    private void executeUpdateTmgDaily() {

        String[] empIds = this.getExecuteEmpId();

        String empSql = referList.buildSQLForSelectEmployees();

        // サイトIDを判定し更新対象の職員番号
        List<String> empIdList = iMastEmployeesService.selectEmpIdListForTmgDaily(_reqSiteId, _reqDYYYYMMDD, empSql, empIds);

        // 更新対象職員のROWIDを取得する
        List<String> rowIdList = iTmgDailyService.buildSQLForSelectObjEmpForUpdate(empIdList, getReqDYYYYMMDD());

        //  一括承認データを更新する
        String sProgramId = APPLICATION_ID + "_" + ACT_PERMIT;
        iTmgDailyService.buildSQLForUpdateTmgDaily(_loginUserCode, sProgramId, getReqDYYYYMMDD(), empIdList);

        // 承認時に超過勤務でステータスが申請中のものがある場合に確認済へ変更
        // 一括承認データを更新する
        iTmgDailyDetailService.buildSQLForUpdateTmgDailyDetail(_psDBBean.getCustID(), _psDBBean.getCompCode(),_loginUserCode,sProgramId, getReqDYYYYMMDD(), empIdList, "TMG_ITEMS|Overhours");

        iTmgTriggerService.buildSQLForInsertTmgTrigger(_loginUserCode, sProgramId, _sAction, _reqDYYYYMMDD, rowIdList);

        iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query().eq("TTR_CCUSTOMERID", _psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", _psDBBean.getCompCode())
                .eq("TTR_CMODIFIERUSERID", _loginUserCode)
                .eq("TTR_CMODIFIERPROGRAMID", APPLICATION_ID + "_" + _sAction ));

        // TODO
        //TmgUtil.checkInsertErrors(setInsertValues(vQuery,BEANDESC), session, BEANDESC);

    }

    /**
     * 別コンテンツへのリダイレクトを実行します。
     * @see TmgReferList#setTargetEmployee(String)
     */
    private void executeRedirect(ModelMap modelMap) {

        String sBean = _psDBBean.getReqParam(REQ_REDIRECT_BEAN);
        Hashtable htRequest = new Hashtable();
        htRequest.put(REQ_ACTION, _psDBBean.getReqParam(REQ_REDIRECT_ACTION));
        htRequest.put(REQ_SECTION_ID, _reqSectionId);
        htRequest.put(REQ_DYYYYMMDD, _reqDYYYYMMDD);
        htRequest.put(REQ_DYYYYMM, _reqDYYYYMM);
        htRequest.put(REQ_CEMPLOYEEID, _reqEmployeeId);

        htRequest.put(REQ_REDIRECT_BEAN, APPLICATION_ID);

        if (_psDBBean.getReqParam(REQ_CALL_ACTION).equals(ACT_EDIT_DAIRY)) {
            htRequest.put(REQ_REDIRECT_ACTION, ACT_EDIT_DAIRY);
        } else if (_psDBBean.getReqParam(REQ_CALL_ACTION).equals(ACT_DISP_MONTHLY)) {
            htRequest.put(REQ_REDIRECT_ACTION, ACT_DISP_MONTHLY);
        } else { // いずれでもない場合は月別一覧に戻る
            htRequest.put(REQ_REDIRECT_ACTION, ACT_DISP_MONTHLY);
        }

        referList.setTargetEmployee(_reqEmployeeId);
        modelMap.addAttribute("htRequest", htRequest);
//        // リダイレクト
//        TmgUtil.getTmgUtil().setRedirect(sBean, htRequest, this);

    }


    /**
     * 月次就業実績一括承認処理の為のプロセスを実行します。
     * <p>
     *  <ul>
     *      <li>月次情報(ステータス)を更新するSQL文を取得する。</li>
     *      <li>クエリを実行する。</li>
     *  </ul>
     * </p>
     * @exception Exception
     */
    private void executeUpdateTmgMonthly() {


        String sCustId        = _psDBBean.getCustID();
        String sCompId        = _psDBBean.getCompCode();
        String sDyyyyMm       = getReqDYYYYMM();
        String sloginUserCode = _loginUserCode;
        String sModifierProgramId = APPLICATION_ID + "_" + _sAction;

        /*
         * 更新処理は職員単位で行う。
         */
        String[] sEmpId = getExecuteEmpId();
        for (int i = 0; i < sEmpId.length; i++) {

            /*
             * 月次承認機能がオンの場合、
             * 月次承認エラーチェック結果がＯＫの職員のみ更新を行う。
             */
            if (isMonthlyApproval(_psDBBean) && !isCheckMonthly(sCustId, sCompId, sEmpId[i], sDyyyyMm)) {
                continue;
            }

            // 月次情報の更新
            iTmgMonthlyInfoService.buildSQLForUpdateTmgMonthly(sCustId, sCompId, sEmpId[i], sDyyyyMm, sloginUserCode, sModifierProgramId);
        }

        // TODO
        // TmgUtil.checkInsertErrors(setInsertValues(vQuery,BEANDESC), session, BEANDESC);


    }


    /** システムプロパティ：月次就業実績承認機能を使用するか判定し制御します */
    private final String SYSPROP_TMG_BULK_MONTHLY_APPROVAL = "TMG_BULK_MONTHLY_APPROVAL";
    private final String Cs_YES = "yes";
    private Boolean gbMonthlyApproval = null;

    /**
     * システムプロパティから値を取得後、月次就業実績承認機能を使用するか判定し値を返却します
     *
     * @return boolean(true:使用する、false:使用しない)
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
     * 処理対象となる職員の番号を加工して返却
     *
     * @return 対象職員番号
     */
    private String[] getExecuteEmpId() {


        String[] sWorkEmpId = _reqExecuteEmpId.split(",");

        return sWorkEmpId;
    }

    /**
     * 日別一覧表示の為のプロセスを実行します。
     * @param modelMap
     */
    private void executeReadTmgDaily(ModelMap modelMap) {
        // TODO
        _reqDYYYYMMDD ="2020/01/01";

        // 組織の職員取得ｓｑｌ
        String empSql = getReferList().buildSQLForSelectEmployees();

        // 打刻反映処理
        execReflectionTimePunch(empSql);

        // 承認状況表示項目を取得しセット
        List<ItemVO> itemVOList = iMastGenericDetailService.buildSQLForSelectTmgDisppermstatlist(_psDBBean.getCustID(), _psDBBean.getCompCode(), _psDBBean.getLanguage());
        List<String> monthlyItems = new ArrayList<String>();

        for (ItemVO itemVO : itemVOList) {
            monthlyItems.add(itemVO.getMgdCsql() + " AS " + itemVO.getTempColumnid());
        }
        // TODO　タイトル未実装

        // 承認状況欄情報
        List<HashMap> tmgDailyMapList = iTmgDailyService.buildSQLForSelectTmgDaily(_reqDYYYYMMDD,empSql,monthlyItems);
        modelMap.addAttribute("tmgDailyMapList",tmgDailyMapList);

        // 所属情報
        String sectionName = iMastOrganisationService.buildSQLForSelectEmployeeDetail(_reqSectionId, getToDay(), _loginCustId, _loginCompCode);
        modelMap.addAttribute("sectionName",sectionName);
    }

    /**
     * 月別一覧表示の為のプロセスを実行します。
     * <p>
     * 実行するSQL生成プロセスは以下になります。
     * 	<ol>
     * 		<li>職員氏名と、承認ステータス状態を取得する。</li>
     * 		<li>カレンダーテーブルより休日フラグを取得する。</li>
     * 		<li>対象勤務年月の1ヶ月間の日付・曜日を取得する。</li>
     * 		<li>翌月リンクを作成する為の勤怠データ件数を取得する。</li>
     * 		<li>前月リンクを作成する為の勤怠データ件数を取得する。</li>
     * 		<li>職員情報を取得する。</li>
     * 		<li>表示月遷移リスト情報を取得する。</li>
     * 	</ol>
     * </p>
     * <p>
     * 	結果セット編集プロセスは以下になります。
     * 	<ol>
     * 		<li>カレンダーリストを設定する。</li>
     * 		<li>承認状況一覧を設定する。</li>
     * 	</ol>
     * </p>
     *    @exception Exception
     */
    private void executeReadMonthlyList(ModelMap modelMap) {

        String empSql = getReferList().buildSQLForSelectEmployees();

        // 打刻反映処理を行う。
        execReflectionTimePunch(empSql);


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
                _psDBBean.getCustID(),
                _psDBBean.getCompCode(),
                getReqDYYYYMM(),
                _psDBBean.getLanguage(),
                getToDay(),
                empSql,
                colNameList
        );
        modelMap.addAttribute("tmgMonthlyInfoVOList", tmgMonthlyInfoVOList);

        // 1 カレンダー情報の取得
        CalenderVo calenderVo = iTmgCalendarService.selectGetCalendarList(_psDBBean.getCustID(),
                _psDBBean.getCompCode(), referList.getTargetSec(), _psDBBean.escDBString(referList.getTargetGroup()), getReqDYYYYMM().substring(0, 4), getReqDYYYYMM()).get(0);
        modelMap.addAttribute("calenderVo", calenderVo);

        // 2 対象勤務年月の1ヶ月間の日付・曜日を取得
        List<OneMonthDetailVo> oneMonthDetailVoList = iTmgCalendarService.selectDayCount(getReqDYYYYMM());
        modelMap.addAttribute("oneMonthDetailVoList", oneMonthDetailVoList);

        // 3 表示対象月の前月データを持つ職員数
        int tmgMonthlyInfoPrevCount = iTmgMonthlyInfoService.buildSQLForSelectTmgMonthlyInfoCount(empSql, TmgUtil.getFirstDayOfMonth(getReqDYYYYMM(), PARAM_PREV_MONTH));
        modelMap.addAttribute("tmgMonthlyInfoPrevCount", tmgMonthlyInfoPrevCount);

        // 4 表示対象月の翌月データを持つ職員数
        int tmgMonthlyInfoNextCount = iTmgMonthlyInfoService.buildSQLForSelectTmgMonthlyInfoCount(empSql, TmgUtil.getFirstDayOfMonth(getReqDYYYYMM(), PARAM_NEXT_MONTH));
        modelMap.addAttribute("tmgMonthlyInfoNextCount", tmgMonthlyInfoNextCount);

        // 5 選択組織名称の取得
        String sectionName = iMastOrganisationService.buildSQLForSelectEmployeeDetail(_reqSectionId, getToDay(), _loginCustId, _loginCompCode);
        modelMap.addAttribute("sectionName", sectionName);

        // 6 表示月遷移リスト情報取得
        List<DispMonthlyVO> cispMonthlyVOList = iTmgMonthlyService.buildSQLForSelectDispTmgMonthlyList(getThisMonth(), empSql);
        modelMap.addAttribute("cispMonthlyVOList", cispMonthlyVOList);

    }

    /*************************************************************/
    //一覧表示ステップ　１
    public List<DispMonthlyVO> dispMonthlyList(PsDBBean psDBBean) throws Exception {

        String empSql = referList.buildSQLForSelectEmployees();
        // 打刻反映処理を行う。
        execReflectionTimePunch(empSql);
        // 6 表示月遷移リスト情報取得
        return iTmgMonthlyService.buildSQLForSelectDispTmgMonthlyList(getThisMonth(), empSql);

    }

    /*************************************************************/
    //一覧表示ステップ　２
    public int dispMonthlyPrev() {

        String empSql = referList.buildSQLForSelectEmployees();
        // 3 表示対象月の前月データを持つ職員数
        int tmgMonthlyInfoPrevCount = iTmgMonthlyInfoService.buildSQLForSelectTmgMonthlyInfoCount(empSql, TmgUtil.getFirstDayOfMonth(getReqDYYYYMM(), PARAM_PREV_MONTH));

        return tmgMonthlyInfoPrevCount;

    }

    /*************************************************************/
    //一覧表示ステップ　３
    public int dispMonthlyNext() {

        String empSql = referList.buildSQLForSelectEmployees();

        // 4 表示対象月の翌月データを持つ職員数
        int tmgMonthlyInfoNextCount = iTmgMonthlyInfoService.buildSQLForSelectTmgMonthlyInfoCount(empSql, TmgUtil.getFirstDayOfMonth(getReqDYYYYMM(), PARAM_NEXT_MONTH));
        return tmgMonthlyInfoNextCount;

    }


    /*************************************************************/
    //一覧表示ステップ　４
    public List<TmgMonthlyInfoVO> getTmgMonthlyInfoVOList() {

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
                _psDBBean.getCustID(),
                _psDBBean.getCompCode(),
                getReqDYYYYMM(),
                _psDBBean.getLanguage(),
                getToDay(),
                empSql,
                colNameList
        );

        return tmgMonthlyInfoVOList;
    }

    /*************************************************************/
    //一覧表示ステップ　5
    public CalenderVo selectGetCalendarList() {
        // 1 カレンダー情報の取得
        CalenderVo calenderVo = iTmgCalendarService.selectGetCalendarList(_psDBBean.getCustID(),
                _psDBBean.getCompCode(), referList.getTargetSec(), _psDBBean.escDBString(referList.getTargetGroup()), getReqDYYYYMM().substring(0, 4), getReqDYYYYMM()).get(0);
        return calenderVo;
    }

    //一覧表示ステップ　5
    public List<OneMonthDetailVo> selectDayCount() {
        // 2 対象勤務年月の1ヶ月間の日付・曜日を取得
        List<OneMonthDetailVo> oneMonthDetailVoList = iTmgCalendarService.selectDayCount(getReqDYYYYMM());
        return oneMonthDetailVoList;
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
     * @return boolean 表示制御値
     *
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
     * @return boolean(true:権限あり、false:権限なし)
     *
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
//
//
//    /**
//     * 指定の職員の指定年月における、月次承認エラーチェック結果を取得する。
//     *
//     * @param  sEmpid  職員番号
//     * @param  sYYYYMM 年月
//     * @return boolean チェック結果(true：エラー、false：正常)
//     */
//    public boolean isErrForCheckMonthlyApproval(String sEmpid, String sYYYYMM) {
//
//        boolean bRes = true;
//
//        Vector vQry = new Vector();
//
//        vQry.add(buildSelectDataForTMG_F_CHECK_MONTHLY(
//                escDBString(getCustID()),
//                escDBString(getCompCode()),
//                escDBString(sEmpid),
//                toDBDate(sYYYYMM)));
//
//        try {
//
//            // 検索結果を取得する。
//            PsResult res = (PsResult)getValuesforMultiquery(vQry, BEANDESC);
//            Vector   resList     = res.getResult();
//            Vector   resRecList  = (Vector)resList.get(0);     // ０番目クエリ検索結果
//            Vector   resDataList = (Vector)resRecList.get(0);  // ０レコード目データ
//            String   resData     = (String)resDataList.get(0); // ０項目目データ
//
//            // エラーチェック結果が"0"の場合、チェック結果「正常」となる
//            if ("0".equals(resData)) {
//                bRes = false;
//            }
//
//        } catch(Exception e) {
//            return true;
//        }
//
//        return bRes;
//    }


    /************************************************************/

    /**
     * 月次一覧、また日次承認画面表示時の打刻反映処理
     */
    private void execReflectionTimePunch(String empSql) {

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
        iTmgTriggerService.buildSQLForInsertTmgTriggerByTimePunch(_psDBBean.getUserCode(), action, stDate, endDate, empSql);
        iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query().eq("TTR_CCUSTOMERID", _psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", _psDBBean.getCompCode())
                .eq("TTR_CMODIFIERUSERID", _loginUserCode)
                .eq("TTR_CMODIFIERPROGRAMID", APPLICATION_ID + "_" + action));

        // TODO
        //TmgUtil.checkInsertErrors(setInsertValues(vQuery, BEANDESC), session, BEANDESC);
    }

    /**
     * 対象月の日数を返す
     * @param targetDate 対象年月データ
     * @return int 対象月の日数
     */
    public int getActualMaximumOfMonth(String targetDate) {


        String sYear  = "";
        String sMonth = "";

        StringTokenizer st = new StringTokenizer(targetDate,FORMAT_SLASH,false);

        sYear  = st.nextToken();
        sMonth = st.nextToken();

        // 対象月の日数を求める
        Calendar cal = Calendar.getInstance();
        try {
            // 対象年をセット
            cal.set(Calendar.YEAR,  Integer.parseInt(sYear));
            // 対象月をセット
            cal.set(Calendar.MONTH, Integer.parseInt(sMonth)-1);
        } catch (NumberFormatException e) {
            throw e;
        }

        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        return days;
    }

    /**
     * メインメソッド。
     */
    public void execute(PsDBBean psDBBean) throws Exception {

        setExecuteParameter(psDBBean);

        // 2007/08/03 	H.Kawabata		参照権限チェック仕様変更対応
        // ■初期表示時：
        //   　選択した組織、(もしくはグループ)の対象年月(デフォルトでは現在日付時点の年月)時点での
        //   評価状況一覧コンテンツの参照権限をチェックする。
        //   参照権限がある場合は、問題なく評価状況一覧を表示する。
        //   　しかし、参照権限が無い場合は1ヶ月遡った月の参照権限をチェックする。
        //   1ヶ月遡った月の参照権限があればその月の評価状況一覧を表示し、
        //   1ヶ月遡った月の参照権限も無い場合は画面に「参照できる職員が存在しません」(文言変更有り)
        //   メッセージを画面へ表示する。
        // ■初期表示以外：
        //   選択した組織、(もしくはグループ)の対象年月時点での評価状況一覧コンテンツの参照権限をチェックする。
        //   権限があれば問題なく評価状況一覧を表示する。
        //   権限が無い場合は画面に「参照できる職員が存在しません」(文言変更有り)
        //   メッセージを画面へ表示する。
        //   ※また、権限はあるが選択している組織(もしくはグループ)に所属している職員が存在しない場合も
        //     権限が無いのと同じ扱いとする。
        // 勤怠承認サイト、もしくは勤怠管理サイトの場合に以下の処理を実行する
        if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) || TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId())) {
            // 勤怠承認サイトは初期表示時、勤怠管理サイトは初期表示+(組織選択時or組織選択済)の場合
            // ※勤怠管理サイトの場合、初期表示時でも組織が選択されていない状態なら権限チェックを行わない
            if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId())  && StrUtil.isEmpty(_sAction) ||
                    TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId()) && StrUtil.isEmpty(_sAction) && isSelectSection()
            ) {
                // 参照権限チェック(現在時点での年月)
                if (getReferList().existsAnyone(getFirstDayOfSysdate()) && getReferList().isThereSomeEmployees(getFirstDayOfSysdate())) {
                    setAuthorityMonth(CB_CAN_REFER);

                    // 参照権限が無い場合は、1ヶ月過去のシートの権限をチェックする。
                } else {
                    String sPrevMonth = TmgUtil.getFirstDayOfMonth(getFirstDayOfSysdate(), PARAM_PREV_MONTH);

                    // 汎用参照コンポーネントの基準日を基準日の前月(過去)に設定しなおす
                    setReferList(sPrevMonth);

                    // 参照権限の設定:
                    // 初期表示時の対象年月の時点の参照権限が無い場合に、
                    // 1ヶ月過去の参照権限を判定し参照権限がある場合は1ヶ月過去のシートを参照する。
                    // 権限が無い場合は、参照できない。
                    if (getReferList().existsAnyone(sPrevMonth) && getReferList().isThereSomeEmployees(sPrevMonth)) {

                        // 対象年月が現在の年月の場合、1ヶ月過去の年月を対象年月に設定します
                        // このif文は、現在「部署A」を選択していて対象年月が変更された状態で「組織B」を選択しなおすと
                        // 「組織B」の現在日付時点の年月と、その年月-1ヶ月時点での参照権限をチェックします。
                        // その際に、変更後対象年月が現在年月でない場合にも現在年月-1ヶ月を設定されるのを防ぐ為
                        // 「対象年月が現在の年月の場合」という条件を実装しています。
                        if (getFirstDayOfSysdate().equals(getReqDYYYYMM())) {
                            // 対象年月を1ヶ月過去に設定します
                            setReqDYYYYMM(sPrevMonth);
                            // 検索対象年月の前月
                            _prevMonth = TmgUtil.getFirstDayOfMonth(_reqDYYYYMM, PARAM_PREV_MONTH);
                            // 検索対象年月の翌月
                            _nextMonth = TmgUtil.getFirstDayOfMonth(_reqDYYYYMM, PARAM_NEXT_MONTH);
                        }
                        setAuthorityMonth(CB_CAN_REFER);
                    } else {
                        // 対象年月を元に戻します
                        setReferList(getReqDYYYYMM());

                        setAuthorityMonth(CB_CANT_REFER);
                    }
                }

                // 2007/08/07 	H.Kawabata		参照権限チェック仕様変更対応
                // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
                // 翌月の権限があればリンク「>」を画面に表示する。
                // 権限が無い場合は「>」を表示しない。
                // ※また、権限はあるが選択している組織(もしくはグループ)に所属している職員が存在しない場合も
                //   権限が無いのと同じ扱いとする。
                if (getReferList().existsAnyone(getNextMonth()) && getReferList().isThereSomeEmployees(getNextMonth())) {
                    setAuthorityNextMonth(CB_CAN_REFER);
                } else {
                    setAuthorityNextMonth(CB_CANT_REFER);
                }

                // 初期表示時以外
                // ※組織を選択していないときは権限チェックを行わない。
                // 　勤怠管理サイトで組織未選択時に権限チェックを行うとえらーで落ちてしまうので
                // 　それを防ぐ為に「組織を選択しているとき」という条件を実装しています。
            } else if (isSelectSection()) {
                // 参照権限の判定：設定(当月分)
                // 当月もしくは、先月どちらかの権限が有効な場合は過去に関しては常に表示する(シートがある限り)
                String sPrevMonth = TmgUtil.getFirstDayOfMonth(getFirstDayOfSysdate(), PARAM_PREV_MONTH);
                if (getReferList().existsAnyone(getFirstDayOfSysdate()) && getReferList().isThereSomeEmployees(getFirstDayOfSysdate()) ||
                        getReferList().existsAnyone(sPrevMonth)             && getReferList().isThereSomeEmployees(sPrevMonth)
                ) {
                    setAuthorityMonth(CB_CAN_REFER);
                } else {
                    setAuthorityMonth(CB_CANT_REFER);
                }

                // 2007/08/07 	H.Kawabata		参照権限チェック仕様変更対応
                // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
                // 翌月の権限があればリンク「>」を画面に表示する。
                // 権限が無い場合は「>」を表示しない。
                // ※また、権限はあるが選択している組織(もしくはグループ)に所属している職員が存在しない場合も
                //   権限が無いのと同じ扱いとする。
                if (getReferList().existsAnyone(getNextMonth()) && getReferList().isThereSomeEmployees(getNextMonth())) {
                    setAuthorityNextMonth(CB_CAN_REFER);
                } else {
                    setAuthorityNextMonth(CB_CANT_REFER);
                }
            }
            // その他のサイトの場合
        } else {
            setAuthorityMonth(CB_CAN_REFER);
        }

    }


    /**
     * 処理実行用パラメータの設定を行います。
     *
     * @throws Exception
     */
    public void setExecuteParameter(PsDBBean psDBBean) throws Exception {

        _sysdate = psDBBean.getSysDate();
        _reqSiteId = psDBBean.getSiteId();
        _loginCustId = psDBBean.getCustID();
        _loginCompCode = psDBBean.getCompCode();
        _loginUserCode = psDBBean.getUserCode();
        _sAction = psDBBean.getReqParam(REQ_ACTION);
        _reqDYYYYMM = psDBBean.getReqParam(REQ_DYYYYMM);
        _reqDYYYYMMDD = psDBBean.getReqParam(REQ_DYYYYMMDD);
        _reqEmployeeId = psDBBean.getReqParam(REQ_CEMPLOYEEID);
        _reqExecuteEmpId = psDBBean.getReqParam(REQ_EXECUTEEMPID);

        // 検索対象年月の入力がなければ、現在日付月初を検索対象年月する。
        if (_reqDYYYYMM == null || _reqDYYYYMM.length() == 0) {
            _reqDYYYYMM = getFirstDayOfSysdate();
        }


        // TmgReferListの生成
        referList = new TmgReferList(psDBBean, "PermStatList", _reqDYYYYMM, TmgReferList.TREEVIEW_TYPE_LIST, true);
//        referList.putReferList(modelMap);
        // 組織コードの取得
        _reqSectionId = referList.getTargetSec();

        // 組織が選択されているか確認
        if (_reqSectionId == null || _reqSectionId.length() == 0) {
            _isSelectSection = false;
        } else {
            _isSelectSection = true;
        }


        // 検索対象年月の前月
        _prevMonth = TmgUtil.getFirstDayOfMonth(_reqDYYYYMM, PARAM_PREV_MONTH);
        // 検索対象年月の翌月
        _nextMonth = TmgUtil.getFirstDayOfMonth(_reqDYYYYMM, PARAM_NEXT_MONTH);

        /*
         * 初期表示時、または組織ツリー再表示時の表示対象日付設定を行う。
         * 初期表示時は「getReqParm(REQ_ACTION)」がNULLとなる。
         * 再表示ボタン押下時は「getReqParm(TREEVIEW_KEY_REFRESH_FLG)」に値が設定される。
         */
        // 組織ツリー基準日情報チェック
        if (referList.getRecordDate() == null) {
            // 今月の月初
            _thisMonth = TmgUtil.getFirstDayOfMonth(psDBBean.getSysDate(), PARAM_THIS_MONTH);
        } else {
                       // 組織ツリー基準日
            _thisMonth = TmgUtil.getFirstDayOfMonth(referList.getRecordDate(), PARAM_THIS_MONTH);

            // 初期表示、再表示ボタン使用時処理
            if (StrUtil.isEmpty(psDBBean.getReqParam(REQ_ACTION)) || StrUtil.isNotEmpty(psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG))) {

                // 表示日付変更
                _reqDYYYYMM = TmgUtil.getFirstDayOfMonth(referList.getRecordDate(), PARAM_THIS_MONTH);
                // 検索対象年月の前月
                _prevMonth = TmgUtil.getFirstDayOfMonth(referList.getRecordDate(), PARAM_PREV_MONTH);
                // 検索対象年月の翌月
                _nextMonth = TmgUtil.getFirstDayOfMonth(referList.getRecordDate(), PARAM_NEXT_MONTH);
            }
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

    /**
     * 汎用リンクオブジェクトを生成します。
     *
     * @param pDate 対象年月日
     */
    private void setReferList(String pDate)  throws Exception{

        referList = new TmgReferList(_psDBBean, "PermStatList", pDate, TmgReferList.TREEVIEW_TYPE_LIST, true);
//        referList.putReferList(modelMap);

    }


    /** 対象組織有無フラグを返却します。
     * @return 対象組織有無フラグ
     */
    public boolean isSelectSection() {
        return _isSelectSection;
    }

    /**
     * 生成したReferListを返す
     * @return referList
     */
    public TmgReferList getReferList() {
        return referList;
    }

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

    /**
     * 対象年月の前月を返却します。
     */
    public String getPrevMonth() {
        return _prevMonth;
    }

    /**
     * 対象年月の翌月を返却します。
     */
    public String getNextMonth() {
        return _nextMonth;
    }

    /**
     * 今月の月初を返却します。
     */
    public String getThisMonth() {
        return _thisMonth;
    }

    /**
     * 今日の日付を返す(基準日があればその日付)
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


}
