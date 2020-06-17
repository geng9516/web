package jp.smartcompany.job.modules.tmg.tmgnotification;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.pojo.entity.*;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.calendarDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.dateDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.paramNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.*;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 休暇・休出申請bean -> 对应旧就业的ps.c01.tmg.PaidHoliday.TmgNotificationBean
 *
 * @author Wang Ziyue
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgNotificationBean {

    public final TmgReferList referList = null; // 汎用参照リスト;
    public  PsDBBean psDBBean;
    public final IMastGenericDetailService iMastGenericDetailService;
    public final ITmgNotificationService iTmgNotificationService;
    public final ITmgPaidHolidayService iTmgPaidHolidayService;
    public final ITmgMonthlyService iTmgMonthlyService;
    public final ITmgErrmsgService iTmgErrmsgService;
    public final ITmgNotificationCheckService iTmgNotificationCheckService;
    public final ITmgTriggerService iTmgTriggerService;
    private final IHistDesignationService iHistDesignationService;
    private final ITmgNtfAttachedfileService iTmgNtfAttachedfileService;
    private final ITmgNtfactionlogService iTmgNtfactionlogService;
    private final ITmgCalendarService iTmgCalendarService;
    private final ITmgEmployeesService iTmgEmployeesService;

    // アクション
    public static final String ACT_DISPINP_RLIST = "ACT_DispInp_RList";            // 一覧表示(本人)
    public static final String ACT_MAKEAPPLY_RDETAIL = "ACT_MakeApply_RDetail";        // 新規申請表示
    public static final String ACT_MAKEAPPLY_CAPPLY = "ACT_MakeApply_CAppl";          // 新規申請
    public static final String ACT_EDITAPPLY_RDETAIL = "ACT_EditApply_RDetail";        // 取下表示(本人)
    public static final String ACT_EDITPERMAPPLY_RDETAIL = "ACT_EditPermApply_RDetail";    // 取下表示(承認者)
    public static final String ACT_EDITAPPLY_UWITHDRAW = "ACT_EditApply_UWithdraw";      // 申請取下(本人)
    public static final String ACT_EDITPERMAPPLY_UWITHDRAW = "ACT_EditPermApply_UWithdraw";  // 申請取下(承認者)
    public static final String ACT_EDITAPPLY_UDEL = "ACT_EditApply_UDel";           // 申請全取消
    public static final String ACT_DISPPERM_RLIST = "ACT_DispPerm_RList";           // 一覧表示(承認者)
    public static final String ACT_ALTERAPPLY_RDETAIL = "ACT_AlterApply_RDetail";       // 代理申請表示
    public static final String ACT_ALTERAPPLY_CAPPLY = "ACT_AlterApply_CAppl";         // 代理申請
    public static final String ACT_EDITPERM_RDETAIL = "ACT_EditPerm_RDetail";         // 承認却下表示
    public static final String ACT_EDITPERM_UPERMIT = "ACT_EditPerm_UPermit";         // 承認
    public static final String ACT_EDITPERM_UREJECT = "ACT_EditPerm_UReject";         // 申請却下
    public static final String ACT_EDITCANCEL_RDETAIL = "ACT_EditCancel_RDetail";       // 申請解除表示
    public static final String ACT_EDITCANCEL_UCANCEL = "ACT_EditCancel_UCancel";       // 申請解除
    public static final String ACT_DISPDETAIL_RDETAIL = "ACT_DispDetail_RDetail";       // 詳細表示
    public static final String ACT_DISPDETAIL_RDETAILCANCEL = "ACT_DispDetail_RDetailCancel"; // 申請解除(本人)
    public static final String ACT_REMAKEAPPLY_CAPPLY = "ACT_ReMakeApply_CAppl";        // 再申請
    public static final String ACT_ALTERLISTAPPLY_CAPPLY = "ACT_ALTERLISTAPPLY_CAPPLY";    // 一括
    public static final String ACT_IM_REDIRECT = "ACT_IM_REDIRECT";              // イントラマート側へのリダイレクト用JSP
    public static final String ACT_EDITPERM_USENDBACK = "ACT_EditPerm_USendBack";       // 差戻し
    //public static final String ACT_CHANGE_DISASTER          = "ACT_CHANGE_DISASTER";          // 労災申請ステータス変更
    public static final String ACT_UPDATE_ITEM = "ACT_UPDATE_ITEM";              // 承認後更新

    /**
     * 職種/申請リレーションが有効な状態
     */
    public static final String FLG_NTF_RELATION_ENABLE = "1";

    public static final String BEAN_DESC = "TmgNotification"; // LOG出力用ディスクリプタ
    private static final String DATA_SEPARATOR01 = "_";               // データ区切り文字(_)
    // 日付フォーマット
    public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final String DATETIME_FORMAT = "yyyy/MM/dd hh24:mi";
    public static final String DATE_FORMAT_DISP = "yyyy\"年\"MM\"月\"dd\"日\"";

    // ステータス
    public static final String STATUS_WITHDRAW = "TMG_NTFSTATUS|0";  // 取下
    public static final String STATUS_WAIT = "TMG_NTFSTATUS|2";  // 承認待ち
    public static final String STATUS_REJECT = "TMG_NTFSTATUS|3";  // 差戻
    public static final String STATUS_PERM = "TMG_NTFSTATUS|5";  // 承認済


    /**
     * 初期処理判定フラグ
     */
    private static final int INITIAL_TREATMENT = 0;

    /**
     * リクエストキー - 再表示ボタン使用判定用
     */
    private static final String TREEVIEW_KEY_REFRESH_FLG = "txtTmgReferListTreeViewRefreshFlg";


    /**
     * 全局参数使用
     */
    paramNotificationListDto param = new paramNotificationListDto();

    /**
     * 参数设置
     * setSysControl
     * setParamSearch
     * setTargetUser
     * setSearchNtfTerm
     * execute部分
     */
    private void paramSetting() {

        //基本信息
        param.setCompId(psDBBean.getCustID());
        param.setCustId(psDBBean.getCompCode());
        param.setTargetUser(psDBBean.getTargetUser());
        param.setUserCode(psDBBean.getUserCode());
        param.setSiteId(psDBBean.getSiteId());
        param.setLang(psDBBean.getLanguage());

        /**
         * 今日の日付を返す(基準日があればその日付)
         */

        if (referList != null) {
            param.setToday(referList.getRecordDate());
        } else {
            param.setToday(TmgUtil.getSysdate());
        }
        param.setTodayD(DateUtil.parse(param.getToday()));
        //アクション
        param.setAction(psDBBean.getReqParam("txtAction"));
        // パラメータ
        //getParam(0);

        //カレンダー関連情報を取得するメソッド
        getCalender();

        // リクエストパラメータ取得
        setParamSearch();

        //setSearchNtfTerm();

        // 参照権限チェック仕様変更対応
        // ■初期表示時：
        //   　選択した組織、(もしくはグループ)の対象年月(デフォルトでは現在日付時点の年月)時点での
        //   休暇・休出申請コンテンツの参照権限をチェックする。
        //   参照権限がある場合は、問題なく休暇・休出申請を表示する。
        //   (決裁レベルをピンポイントで引き当てるたい為に過去日、未来日での参照権限判定は行わない)
        //   メッセージを画面へ表示する。
        // ■初期表示以外：
        //   選択した組織、(もしくはグループ)の対象年月時点での休暇・休出申請コンテンツの参照権限をチェックする。
        //   権限があれば問題なく休暇・休出申請を表示する。
        //   権限が無い場合は画面に「参照できる職員が存在しません」(文言変更有り)
        //   メッセージを画面へ表示する。
        //   ※また、権限はあるが選択している組織(もしくはグループ)に所属している職員が存在しない場合も
        //     権限が無いのと同じ扱いとする。

        // 勤怠承認サイト、もしくは勤怠管理サイトの場合に以下の処理を実行する
        if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(param.getSiteId()) || TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(param.getSiteId())) {

            String sAction = param.getAction();
            String sTargetSec = referList.getTargetSec();
            String sStartDateSysdate = TmgUtil.getSysdate();

            // 勤怠承認サイトは初期表示時、勤怠管理サイトは初期表示+(組織選択時or組織選択済)の場合
            // ※勤怠管理サイトの場合、初期表示時でも組織が選択されていない状態なら権限チェックを行わない
            if ((TmgUtil.Cs_SITE_ID_TMG_PERM.equals(param.getSiteId()) && (sAction == null || sAction.length() == 0))
                    || (TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(param.getSiteId()) && !(sTargetSec == null || sTargetSec.length() == 0) && (sAction == null || sAction.length() == 0))) {

                // 参照権限チェック(現在時点での年度)
                //TODO
                /*if (referList.existsAnyone(sStartDateSysdate) && referList.isThereSomeEmployees(sStartDateSysdate)) {
                    param.setAuthorityYear(true);
                    param.setAuthorityNextYear(true);
                    // 参照権限が無い場合
                } else {
                    param.setAuthorityYear(false);
                    param.setAuthorityNextYear(false);
                }*/
                // 初期表示時以外
            } else {
                // 組織未選択時は権限チェックを行わない
                //TODO
                /*if(!(sTargetSec == null || sTargetSec.length() == 0)) {
                    if((referList.existsAnyone(sStartDateSysdate) && referList.isThereSomeEmployees(sStartDateSysdate))) {
                        param.setAuthorityYear(true);
                        param.setAuthorityNextYear(true);
                    } else {
                        param.setAuthorityYear(false);
                        param.setAuthorityNextYear(false);
                    }

                }*/
            }
        }
        //todo
        //param.setEmployeeListSql(referList.buildSQLForSelectEmployees());
        param.setEmployeeListSql(null);
        param.setSiteId(TmgUtil.Cs_SITE_ID_TMG_INP);
        //
        // todo
        //param.setNtfNo(psDBBean.getReqParam("ntfNo"));
        param.setNtfNo("46402406|11631");
    }


    /**
     * 表示対象期間パラメータを設定する。
     *
     * @return なし
     */
    private void setSearchNtfTerm() {

        // 検索条件・申請期間（申請期間検索機能が使用できる場合）
        //todo
        //if (isNtfTermUseCond()) {
        if (true) {
            String sBegin = psDBBean.getReqParam("txtNtfTermBegin");
            String sEnd = psDBBean.getReqParam("txtNtfTermEnd");
            /*
             * 初期表示時、または組織ツリー再検索を行った場合は、
             * 検索条件として、基準日年度の年度開始日と年度終了日を条件として指定する。
             * ※初期表示はリクエストパラメータがNULLかどうかで判断する。（開始日、終了日が空は検索条件として認めているので、空（""）では判定しない。）
             * 　また、再表示ボタン押下時は「getReqParm(TREEVIEW_KEY_REFRESH_FLG)」にtrueが設定（再表示以外は空（""））されるので、リクエストパラメータで再表示処理の判定
             */
            if ((sBegin == null && sEnd == null)
                    || StringUtils.isNotEmpty(psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG))) {

                try {
                    sBegin = param.getGsStartDate();
                    sEnd = param.getGsEndDate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 申請期間検索条件を設定
            param.setNtfTermBegin(sBegin);
            param.setNtfTermEnd(sEnd);
        }

    }

    /**
     * 承認一覧画面表示対象となる申請範囲が条件指定かどうかを判定します。
     *
     * @return boolean 判定結果（true：条件指定、false：条件指定以外（年度毎））
     */
    public boolean isNtfTermUseCond() {
        Boolean bNtfTermCondition = null;
        // 申請期間指定範囲の表示設定の場合、trueを返す。
        if (bNtfTermCondition == null) {
            //TODO
            //bNtfTermCondition = PARAM_DIRECT.equals(getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_NTF_TERM_CONDITION).toLowerCase());
        }

        return bNtfTermCondition;
    }


    /**
     * 検索パラメータを取得するメソッド
     *
     * @return なし
     */
    private void setParamSearch() {

        // 検索条件・申請内容
        String sType = psDBBean.getReqParam("search_type");
        if (sType == null) {
            sType = "";
        }
        param.setType(sType);

        // 検索条件・申請選択状態
        String sSearchTypeIdx = psDBBean.getReqParam("searchTypeIdx");
        if (sSearchTypeIdx == null) {
            sSearchTypeIdx = "";
        }
        param.setSearchTypeIdx(sSearchTypeIdx);

        // 検索条件・状態
        String sStatus = psDBBean.getReqParam("search_status");

        // 申請一覧（本人）用検索パラメータを取得するメソッド(返すものは常に承認済・取下・却下(選択不可))
        if (param.getAction() == ACT_DISPINP_RLIST && sStatus == null) {
            sStatus = STATUS_WAIT;
        } else if (sStatus == null) {
            sStatus = STATUS_WAIT;
        }
        param.setStatus(sStatus);

        // 検索条件・氏名
        String sEmp = psDBBean.getReqParam("search_emp");
        if (sEmp == null) {
            sEmp = "";
        }
        param.setSearchEmp(sEmp);

        // 承認者
        String sEvaluater = psDBBean.getReqParam("search_evaluater");
        if (sEvaluater == null) {
            sEvaluater = "";
        }
        param.setEvaluator(sEvaluater);

        // ページ
        int iPage = 1;
        String sPage = psDBBean.getReqParam("page");
        if (sPage != null) {
            try {
                iPage = Integer.parseInt(sPage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        param.setPage(iPage);

        String sTargetEmpId = psDBBean.getReqParam("targetEmpId");
        param.setSTargetEmpId(sTargetEmpId);

    }


    /**
     * カレンダー関連情報を取得するメソッド
     *
     * @return int    年度
     */
    private void getCalender() {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        // 検索
        // 年度開始・終了日
        dateDto dateDto = iMastGenericDetailService.selectDate(param.getCustId(), param.getCompId(), param.getYear(), param.getToday());
        // 前翌年度有無判定
        calendarDto calendarDto = iTmgCalendarService.selectCalendar(param.getCustId(), param.getCompId(), param.getYear(), param.getToday());

        try {

            // 年度開始日・終了日
            param.setGsStartDate(dateDto.getStartDate());
            param.setGsEndDate(dateDto.getEndDate());
            param.setToday(dateDto.getBaseDate());
            param.setTodayD(sdf.parse(dateDto.getBaseDate()));
            // 前翌年度
            Date dMin = sdf.parse(calendarDto.getMinMonth());
            Date dMax = sdf.parse(calendarDto.getMaxMonth());
            Date dStart = sdf.parse(calendarDto.getStartYearDate());
            Date dEnd = sdf.parse(calendarDto.getEndYearDate());

            if (dMin.before(dStart)) {
                param.setGbPreviousYear(true);
            }
            if (dMax.after(dEnd)) {
                param.setGbNextYear(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * パラメータを取得するメソッド
     *
     * @param piPBranchingProcess 0:初期処理、1:組織ツリー情報使用処理
     * @return なし
     */
    private void getParam(int piPBranchingProcess,int yearNow) {
        int year = iMastGenericDetailService.selectYear(param.getCustId(), param.getCompId());
        // 初期処理
        if (piPBranchingProcess == INITIAL_TREATMENT) {
            // 今年度
            param.setThisYear(year);
            // 年度
            try {
                param.setYear(yearNow);
            } catch (Exception e) {
                // 取得出来なかったらDBより取得
                param.setYear(year);
            }
        }
        // 組織ツリー情報取得後再構築を行う
        else {
            // 組織ツリー基準日情報チェック
            if (referList.getRecordDate() != null) {
                param.setThisYear(Integer.parseInt(referList.getRecordDate().substring(0, 4)));
                //TODO 再表示ボタン使用判定
                if (psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG) != null && !"".equals(psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG))) {
                    param.setYear(Integer.parseInt(referList.getRecordDate().substring(0, 4)));
                }
            }
        }
    }


    /**
     * 申請ステータスマスタ
     * @return
     */
    public List<StutasFlgVo>  getStutas(PsDBBean psDBBean){
        param.setCompId(psDBBean.getCustID());
        param.setCustId(psDBBean.getCompCode());
        param.setTargetUser(psDBBean.getTargetUser());
        param.setUserCode(psDBBean.getUserCode());

        param.setSiteId(psDBBean.getSiteId());

        param.setLang(psDBBean.getLanguage());
        param.setToday(TmgUtil.getSysdate());
        param.setTodayD(DateUtil.parse(param.getToday()));


        List<StutasFlgVo> stutasFlgVos = new ArrayList<StutasFlgVo>();
        List<Map<String, Object>> mgdList = iMastGenericDetailService.selectGenericDetail(buildSQLForSelectGenericDetail(TmgUtil.Cs_MGD_NTFSTATUS, "asc"));

        for(Map<String, Object> map:mgdList){
            StutasFlgVo stutasFlgVo=new StutasFlgVo();
            stutasFlgVo.setStutasId(String.valueOf(map.get("MGD_CMASTERCODE")));
            stutasFlgVo.setStutasName(String.valueOf(map.get("MGD_CGENERICDETAILDESC")));
            stutasFlgVos.add(stutasFlgVo);
        }
        return stutasFlgVos;
    }

    //一覧
    public NotificationDispVo getNotificationList(String statusFlg,String ntfTypeId,String year,int page,PsDBBean psDBBean){

        //基本信息
        param.setCompId(psDBBean.getCustID());
        param.setCustId(psDBBean.getCompCode());
        param.setTargetUser(psDBBean.getTargetUser());
        param.setUserCode(psDBBean.getUserCode());
        param.setSiteId(psDBBean.getSiteId());
        param.setLang(psDBBean.getLanguage());
        param.setPage(page);
        //基准日取得 入力site为系统日期
        param.setToday(TmgUtil.getSysdate());
        param.setTodayD(DateUtil.parse(param.getToday()));
        //アクション
        param.setAction(psDBBean.getReqParam("txtAction"));

        getParam(0,Integer.valueOf(year));

        // 検索条件・申請内
        param.setType(ntfTypeId);

        // 申請一覧（本人）用検索パラメータを取得するメソッド(返すものは常に承認済・取下・却下(選択不可))
        if (param.getAction() == ACT_DISPINP_RLIST && statusFlg == null) {
            statusFlg = STATUS_WAIT;
        } else if (statusFlg == null) {
            statusFlg = STATUS_WAIT;
        }
        //申请状态取得
        param.setStatus(statusFlg);
        //申请typesql取得
        param.setMgdSql(buildSQLForSelectGenericDetail("TMG_NTFTYPE", null, "MGD_CMASTERCODE"));
        //默认page
        if(StrUtil.hasEmpty(String.valueOf(param.getPage()))){
            param.setPage(1);
        }
        //数据取得
        List<notificationListVo> notificationListVoList = iTmgNotificationService.selectNotificationList(param);
        //数据处理
        List<NotificationDispDetailVo> dispVo=new ArrayList<NotificationDispDetailVo>();
        for(notificationListVo nlVo:notificationListVoList){
            NotificationDispDetailVo nddVo = new NotificationDispDetailVo();
            nddVo.setTntfCemployeeid(nlVo.getTntfCemployeeid());
            nddVo.setTntfCemployeeidName(nlVo.getTntfCemployeeidName());
            nddVo.setTntfCtype(nlVo.getTntfCtype());
            nddVo.setTntfDbegin(nlVo.getTntfDbegin());
            nddVo.setTntfDend(nlVo.getTntfDend());
            nddVo.setTntfDcancel(nlVo.getTntfDcancel());
            nddVo.setTntfDnotification(nlVo.getTntfDnotification());
            nddVo.setTntfDmodifieddate(nlVo.getTntfDmodifieddate());
            nddVo.setTntfCstatusflg(nlVo.getTntfCstatusflg());
            nddVo.setTntfCalteremployeeid(nlVo.getTntfCalteremployeeid());
            nddVo.setTntfCalteremployeeidName(nlVo.getTntfCalteremployeeidName());
            nddVo.setTntfCntfNo(nlVo.getTntfCntfNo());
            nddVo.setTntfDcancel2(nlVo.getTntfDcancel2());
            nddVo.setRemakeApply(nlVo.getRemakeApply());
            nddVo.setTntfCtypeChar5(nlVo.getTntfCtypeChar5());
            nddVo.setTntfCtypeCode(nlVo.getTntfCtypeCode());
            if(nlVo.getNtfapprover().indexOf(",")>-1){
                nddVo.setNtfapprover(nlVo.getNtfapprover().split(","));
            }
            nddVo.setAllCancellation(nlVo.getAllCancellation());
            nddVo.setTntfNtimezoneOpen(nlVo.getTntfNtimezoneOpen());
            nddVo.setTntfNtimezoneClose(nlVo.getTntfNtimezoneClose());
            nddVo.setDayOfWeek(nlVo.getDayOfWeek());
            nddVo.setFinalApprovelLevel(nlVo.getFinalApprovelLevel());
            nddVo.setTntfCowncomment(nlVo.getTntfCowncomment());
            //数据取消文本处理
            if(nddVo.getTntfCstatusflg().equals(STATUS_WITHDRAW)||nddVo.getTntfCstatusflg().equals(STATUS_REJECT)){

            }else if(nddVo.getTntfDbegin().equals(nddVo.getTntfDend())){
                if(!StrUtil.hasEmpty(nddVo.getTntfDcancel2())&&nddVo.getTntfDcancel2().equals(nddVo.getTntfDbegin())){
                    nddVo.setTntfCstatusflg(STATUS_REJECT);//全取消
                }
            }else{
                if(!StrUtil.hasEmpty(nddVo.getTntfDcancel()) && DateUtil.parse(nddVo.getTntfDbegin())
                        .after(DateUtil.parse(nddVo.getTntfDcancel()))){
                    nddVo.setTntfCstatusflg(STATUS_REJECT);//全取消
                }else if(!StrUtil.hasEmpty(nddVo.getTntfDcancel()) && DateUtil.parse(nddVo.getTntfDend())
                        .after(DateUtil.parse(nddVo.getTntfDcancel()))){
                    nddVo.setTntfDend(nddVo.getTntfDcancel());//部分取消
                }
            }
            if(!StrUtil.hasEmpty(nlVo.getTntfCntfNo())){
                param.setNtfNo(nlVo.getTntfCntfNo());
                // 4 申請区分略称を取得
                nddVo.setNtfName(iTmgNotificationService.selectNtfName(param.getCustId(), param.getCompId(), param.getNtfNo()));
                // 5 添付ファイル
                nddVo.setTmgNtfAttachedfileDoList(iTmgNtfAttachedfileService.selectFileDisp(param.getCustId(), param.getCompId(), param.getNtfNo()));
                // 7 申請ログ
                nddVo.setTmgNtfactionlogDOList(iTmgNtfactionlogService.selectNtfActionLog(param.getTodayD(), param.getLang(), param.getCustId(), param.getCompId(), param.getNtfNo()));
            }

            dispVo.add(nddVo);

        }
        NotificationDispVo notificationDispVo= new NotificationDispVo();
        notificationDispVo.setList(dispVo);
        notificationDispVo.setCount(iTmgNotificationService.selectNotificationListCount(param));
        return notificationDispVo;
    }

    /**
     * 申請区分マスタ　全て　新規画面用
     * @param psDBBean
     * @return
     */
    public List<TypeGroupVo> getMgdNtfTypeList(PsDBBean psDBBean){

        String workType = iTmgEmployeesService.selectWorkerType(psDBBean.getCustID(),psDBBean.getCompCode(),psDBBean.getTargetUser(),DateTime.now());

        List<mgdTmgNtfTypeVo> mgdTmgNtfTypeVoS = iMastGenericDetailService.selectMasterTmgNtfType(psDBBean.getCustID(),
                psDBBean.getCompCode(), TmgUtil.getSysdate(), psDBBean.getTargetUser(), psDBBean.getLanguage(), psDBBean.getSiteId(),workType);

        List<TypeGroupVo> typeGroupVoList=new ArrayList<TypeGroupVo>();
        //显示type处理
        int viewType;
        String viewflg = null;
        for(mgdTmgNtfTypeVo vo:mgdTmgNtfTypeVoS){
            TypeGroupVo typeGroupVo=new TypeGroupVo();
            typeGroupVo.setGroupId(vo.getGMgdCmastercode());// 0 グループの区分
            typeGroupVo.setGroupName(vo.getGMgdCgenericdetaildesc());// 1 グループの名称
            typeGroupVoList.add(typeGroupVo);
        }

        typeGroupVoList = CollUtil.distinct(typeGroupVoList);
        List<TypeChildrenVo> typeChildrenVos = new ArrayList<TypeChildrenVo>();
        for(mgdTmgNtfTypeVo voChild:mgdTmgNtfTypeVoS){
            for(TypeGroupVo voGroup:typeGroupVoList){
                if(voChild.getGMgdCmastercode().equals(voGroup.getGroupId())){
                    TypeChildrenVo tc =new TypeChildrenVo();
                    tc.setNtfId(voChild.getT1MgdCmastercode());
                    tc.setNtfName(voChild.getT1MgdCgenericdetaildesc());
                    tc.setViewType(voChild.getT1MgdNsparenum2());
                    tc.setConfirmComment(voChild.getT1MgdCsparechar3());
                    tc.setBiko(voChild.getT2MgdCsparechar3());
                    tc.setConfirmFile(voChild.getT2MgdNsparenum2());

                    viewflg="";
                    viewType=0;
                    if(!StrUtil.hasEmpty(voChild.getT1MgdNsparenum2())){
                        viewType = Integer.valueOf(voChild.getT1MgdNsparenum2());
                        for(int i=0;i<14;i++){
                            viewflg  += (viewType%2);
                            viewType = viewType/2;
                        }
                        if (viewflg.length()==14){
                            byte[] bytes;
                            bytes=viewflg.getBytes();
                            if (bytes[0]=='1'){ tc.setTransfer(true); }//振替先・元
                            if (bytes[1]=='1'){ tc.setTimeZone(true); }//時間帯
                            if (bytes[2]=='1'){ tc.setWorkTime(true); }//始業・終業
                            if (bytes[3]=='1'){ tc.setSickName(true); }//傷病名
                            if (bytes[4]=='1'){ tc.setSickApply(true); }//労災申請有無
                            if (bytes[5]=='1'){ tc.setPeriod(true); }//起算日
                            if (bytes[6]=='1'){ tc.setAddDate(true); }//加算日数
                            if (bytes[7]=='1'){ tc.setLabel(true); }//勤務時間ラベル
                            if (bytes[8]=='1'){ tc.setRestTime(true); }//休憩時間
                            if (bytes[9]=='1'){ tc.setName(true); }//氏名
                            if (bytes[10]=='1'){ tc.setRelation(true); }//続柄
                            if (bytes[11]=='1'){ tc.setBirthday(true); }//生年月日
                            if (bytes[12]=='1'){ tc.setDaysOfWeek(true); }//曜日
                            if (bytes[13]=='1'){ tc.setTargetNumber(true); }//対象者の人数
                        }
                    }
                    voGroup.getNtfTypeValue().add(tc);
                }
            }
        }

        return typeGroupVoList;
    }


    /**
     * 申請区分マスタ　全て　一覧画面用
     * @param psDBBean
     * @return
     */
    public List<TypeGroupVo> getMgdNtfTypeDispAppList(PsDBBean psDBBean){

        List<mgdNtfTypeDispAppVo> mgdNtfTypeDispAppVoList = iMastGenericDetailService.selectMasterTmgNtfTypeDispAppList(psDBBean.getCustID(),
                psDBBean.getCompCode(), DateTime.now(), psDBBean.getLanguage());
        List<TypeGroupVo> typeGroupVoList=new ArrayList<TypeGroupVo>();

        for(mgdNtfTypeDispAppVo vo:mgdNtfTypeDispAppVoList){
            TypeGroupVo typeGroupVo=new TypeGroupVo();
            typeGroupVo.setGroupId(vo.getGroupId());// 0 グループの区分
            typeGroupVo.setGroupName(vo.getGroupName());// 1 グループの名称
            typeGroupVoList.add(typeGroupVo);
        }
        typeGroupVoList = CollUtil.distinct(typeGroupVoList);
        List<TypeChildrenVo> typeChildrenVos = new ArrayList<TypeChildrenVo>();
        for(mgdNtfTypeDispAppVo voChild:mgdNtfTypeDispAppVoList){
            for(TypeGroupVo voGroup:typeGroupVoList){
                if(voChild.getGroupId().equals(voGroup.getGroupId())){
                    TypeChildrenVo tc =new TypeChildrenVo();
                    tc.setNtfId(voChild.getNtfId());/**2 申請区分*/
                    tc.setNtfName(voChild.getNtfName()); /**3 申請区分名称*/
                    //tc.setViewType(voChild.getViewType());/**4 表示項目タイプ*/
                    //tc.setConfirmComment(voChild.getConfirmComment());/**5 申請事由必須有無*/
                    //tc.setBiko(voChild.getBiko());/**6 注釈*/
                    //tc.setConfirmFile(voChild.getConfirmFile());/**7 添付ファイル必須有無*/
                    voGroup.getNtfTypeValue().add(tc);
                }
            }
        }
        return typeGroupVoList;
    }





    /**
     * 年次休暇残日数及び時間
     * @param psDBBean
     * @return
     */
    public List<restYearVo> getRestYear(PsDBBean psDBBean){
        param.setCustId(psDBBean.getCustID());
        param.setCompId(psDBBean.getCompCode());
        //无
        param.setNtfNo(null);
        //当前登陆用
        param.setTargetUser(psDBBean.getUserCode());
        //今日の日付
        param.setTodayD(DateTime.now());
        List<restYearPaidHolidayVo> restYearPaidHolidayVoList = iTmgPaidHolidayService.selectNenjikyukazannissu(param, 0);
        List<restYearVo> restYearVoList = new ArrayList<restYearVo>();
        for(restYearPaidHolidayVo ryphVo:restYearPaidHolidayVoList){
            restYearVo restYearVo=new restYearVo();
            restYearVo.setTypeName(ryphVo.getCdesc());
            restYearVo.setTypeId(ryphVo.getCtype());
            restYearVoList.add(restYearVo);
        }
        //去重
        restYearVoList = CollUtil.distinct(restYearVoList);
        for(restYearPaidHolidayVo ryphVo:restYearPaidHolidayVoList){
            for(restYearVo restYearVo:restYearVoList){
                if(ryphVo.getCtype().equals(restYearVo.getTypeId())){
                    restYearVo.getTimeList().add(TmgUtil.Mintue2HHmi(ryphVo.getNrestHours()));
                    restYearVo.getDayList().add(ryphVo.getNrestDays()+"日");
                    restYearVo.getTimeRange().add(getTxtTImeScope(ryphVo.getDbegin(), ryphVo.getDend()));
                }
            }
        }

        return restYearVoList;
    }





    //文言処理
    private String getTxtTImeScope(String begin,String end){
        if(DateUtil.parse(end).before(DateTime.now())){
            return end+"まで";
        }else{
            return begin+"から";
        }
    }


    /**
     * 一覧表示(本人)
     *
     * @param modelMap showDispInp
     */
    public void actionDispInp(ModelMap modelMap) {

        paramSetting();


        //0 一覧
        /*List<notificationListVo> notificationListVoList = iTmgNotificationService.selectNotificationList(param);
        modelMap.addAttribute("notificationListVoList", notificationListVoList);*/
        //2 申請ステータスマスタ
        /*List<Map<String, Object>> mgdList = iMastGenericDetailService.selectGenericDetail(buildSQLForSelectGenericDetail(TmgUtil.Cs_MGD_NTFSTATUS, "asc"));
        modelMap.addAttribute("mgdList", mgdList);*/
        //1 申請区分マスタ
        /*List<mgdNtfTypeDispAppVo> mgdNtfTypeDispAppVoList = iMastGenericDetailService.selectMasterTmgNtfTypeDispAppList(param.getCompId(), param.getCustId(), DateTime.now(), param.getLang());
        modelMap.addAttribute("mgdNtfTypeDispAppVoList", mgdNtfTypeDispAppVoList);*/
        //3年次休暇残日数及び時間
        /*List<restYearPaidHolidayVo> restYearPaidHolidayVoList = iTmgPaidHolidayService.selectNenjikyukazannissu(param, 0);
        modelMap.addAttribute("restYearPaidHolidayVoList", restYearPaidHolidayVoList);*/
        //4 今月の月中有給付与の情報
        List<paidHolidayThisMonthInfoVo> paidHolidayThisMonthInfoVoList = iTmgMonthlyService.selectPaidHolidayThisMonthInfo(param.getCompId(), param.getCustId(), param.getTargetUser());
        modelMap.addAttribute("paidHolidayThisMonthInfoVoList", paidHolidayThisMonthInfoVoList);
    }

    /**
     * 詳細表示の処理をするメソッド
     *
     * @return なし
     * showDetail
     */
    public void actionShowDetail(ModelMap modelMap) {
        paramSetting();


        param.setNtfNo("46402406|12967");

        if (StrUtil.hasEmpty(param.getNtfNo())) {
            return;
        }
        // 0 一覧
        notificationDetailVo notificationDetailVo = iTmgNotificationService.selectNotificationDetail(param);
        modelMap.addAttribute("notificationDetailVo", notificationDetailVo);
        // 1 申請区分マスタ
        //List<mgdTmgNtfTypeVo> mgdTmgNtfTypeVos = iMastGenericDetailService.selectMasterTmgNtfType(param.getCustId(),
                //param.getCompId(), param.getToday(), param.getTargetUser(), param.getLang(), param.getSiteId());
        //modelMap.addAttribute("mgdTmgNtfTypeVos", mgdTmgNtfTypeVos);
        // 2 年次休暇残日数及び時間
        List<restYearPaidHolidayVo> restYearPaidHolidayVoList = iTmgPaidHolidayService.selectNenjikyukazannissu(param, 1);
        modelMap.addAttribute("restYearPaidHolidayVoList", restYearPaidHolidayVoList);
        // 3 今月の月中有給付与に関する情報を返すSQL
        List<paidHolidayThisMonthInfoVo> paidHolidayThisMonthInfoVoList = iTmgMonthlyService.selectPaidHolidayThisMonthInfo(param.getCustId(), param.getCompId(), param.getTargetUser());
        modelMap.addAttribute("paidHolidayThisMonthInfoVoList", paidHolidayThisMonthInfoVoList);
        // 4 申請区分略称を取得
        String ntfName = iTmgNotificationService.selectNtfName(param.getCustId(), param.getCompId(), param.getNtfNo());
        modelMap.addAttribute("ntfName", ntfName);
        // 5 添付ファイル
        List<TmgNtfAttachedfileDO> tmgNtfAttachedfileDoList = iTmgNtfAttachedfileService.selectFileDisp(param.getCustId(), param.getCompId(), param.getNtfNo());
        modelMap.addAttribute("tmgNtfAttachedfileDoList", tmgNtfAttachedfileDoList);
        // 6 SYSDATE取得
        String sysdate = iMastGenericDetailService.selectSysdate();
        modelMap.addAttribute("sysdate", sysdate);
        // 7 申請ログ
        List<ntfActionLogVo> tmgNtfactionlogDOList = iTmgNtfactionlogService.selectNtfActionLog(param.getTodayD(), param.getLang(), param.getCustId(), param.getCompId(), param.getNtfNo());
        modelMap.addAttribute("tmgNtfactionlogDOList", tmgNtfactionlogDOList);
        // 8 労災申請更新アクション
        // 9 画面項目名称の設定マスタ
        mgdNtfPropVo mgdNtfPropVo = iMastGenericDetailService.selectMasterNtfProp(param.getCustId(), param.getCompId(), param.getLang());
        modelMap.addAttribute("mgdNtfPropVo", mgdNtfPropVo);
    }

    /**
     * 新規申請表示の処理をするメソッド
     *
     * @return なし
     * showMakeApply
     */
    public void actionShowMakeApply(ModelMap modelMap) {
        paramSetting();

        // 0 申請区分マスタ
        //List<mgdTmgNtfTypeVo> mgdTmgNtfTypeVoS = iMastGenericDetailService.selectMasterTmgNtfType(param.getCustId(),
                //param.getCompId(), param.getToday(), param.getTargetUser(), param.getLang(), param.getSiteId());
        //modelMap.addAttribute("mgdTmgNtfTypeVoS", mgdTmgNtfTypeVoS);
        //1 年次休暇残日数及び時間
        List<restYearPaidHolidayVo> restYearPaidHolidayVoList = iTmgPaidHolidayService.selectNenjikyukazannissu(param, 0);
        modelMap.addAttribute("restYearPaidHolidayVoList", restYearPaidHolidayVoList);
        //2 今月の月中有給付与に関する情報を返すSQL
        List<paidHolidayThisMonthInfoVo> paidHolidayThisMonthInfoVoList = iTmgMonthlyService.selectPaidHolidayThisMonthInfo(param.getCompId(), param.getCustId(), param.getTargetUser());
        modelMap.addAttribute("paidHolidayThisMonthInfoVoList", paidHolidayThisMonthInfoVoList);
        // 3 シーケンス採番
        String seq = iTmgNotificationService.selectNotificationSeq();
        modelMap.addAttribute("seq", seq);
        // 4 ヘッダ情報(新規申請用)
        employeeDetailVo employeeDetailVo = iHistDesignationService.selectemployeeDetail(param.getCustId(), param.getCompId(), param.getTargetUser(), param.getLang());
        modelMap.addAttribute("employeeDetailVo", employeeDetailVo);
        // 5 画面項目名称の設定マスタ
        mgdNtfPropVo mgdNtfPropVo = iMastGenericDetailService.selectMasterNtfProp(param.getCustId(), param.getCompId(), param.getLang());
        modelMap.addAttribute("mgdNtfPropVo", mgdNtfPropVo);
    }

    /**
     * 代理申請(再申請)表示の処理をするメソッド
     *
     * @return なし
     * showAlterReApply
     */
    public void actionShowAlterReApply(ModelMap modelMap) {
        paramSetting();

        // 組織が選択されているか
        if (referList.getTargetSec() == null) {
            return;
        }
        // 参照できる職員が存在するかチェックする
        if (param.getTargetUser() == null) {
            return;
        }
        // 0 申請詳細
        notificationDetailVo notificationDetailVo = iTmgNotificationService.selectNotificationDetail(param);
        modelMap.addAttribute("notificationDetailVo", notificationDetailVo);
        // 1 申請区分マスタ
        //List<mgdTmgNtfTypeVo> mgdTmgNtfTypeVoS = iMastGenericDetailService.selectMasterTmgNtfType(param.getCustId(),
                //param.getCompId(), param.getToday(), param.getTargetUser(), param.getLang(), param.getSiteId());
        //modelMap.addAttribute("mgdTmgNtfTypeVoS", mgdTmgNtfTypeVoS);
        //2 年次休暇残日数及び時間]]
        List<restYearPaidHolidayVo> restYearPaidHolidayVoList = iTmgPaidHolidayService.selectNenjikyukazannissu(param, 0);
        modelMap.addAttribute("restYearPaidHolidayVoList", restYearPaidHolidayVoList);
        // 3 今月の月中有給付与に関する情報を返すSQL
        List<paidHolidayThisMonthInfoVo> paidHolidayThisMonthInfoVoList = iTmgMonthlyService.selectPaidHolidayThisMonthInfo(param.getCompId(), param.getCustId(), param.getTargetUser());
        modelMap.addAttribute("paidHolidayThisMonthInfoVoList", paidHolidayThisMonthInfoVoList);
        // 4 職員情報
        employeeDetailVo employeeDetailVo = iHistDesignationService.selectemployee(param.getCustId(), param.getCompId(), param.getTargetUser(), param.getLang(), referList.getTargetSec());
        modelMap.addAttribute("employeeDetailVo", employeeDetailVo);
        // 5 シーケンス採番
        String seq = iTmgNotificationService.selectNotificationSeq();
        modelMap.addAttribute("seq", seq);
        // 6 添付ファイル
        List<TmgNtfAttachedfileDO> tmgNtfAttachedfileDoList = iTmgNtfAttachedfileService.selectFileDisp(param.getCustId(), param.getCompId(), param.getNtfNo());
        modelMap.addAttribute("tmgNtfAttachedfileDoList", tmgNtfAttachedfileDoList);
        // 7 SYSDATE取得(yyyy/mm/dd)
        String sysdate = iMastGenericDetailService.selectSysdate();
        modelMap.addAttribute("sysdate", sysdate);
        // 8 申請ログ
        List<ntfActionLogVo> tmgNtfactionlogDOList = iTmgNtfactionlogService.selectNtfActionLog(param.getTodayD(), param.getLang(), param.getCustId(), param.getCompId(), param.getNtfNo());
        modelMap.addAttribute("tmgNtfactionlogDOList", tmgNtfactionlogDOList);
        // 9 画面項目名称の設定マスタ
        mgdNtfPropVo mgdNtfPropVo = iMastGenericDetailService.selectMasterNtfProp(param.getCustId(), param.getCompId(), param.getLang());
        modelMap.addAttribute("mgdNtfPropVo", mgdNtfPropVo);
    }

    /**
     * 新規申請・再申請・代理申請
     *
     * @param modelMap makeApply
     */
    public void actionMakeApply(ModelMap modelMap) {
        paramSetting();

        if (param.getAction().equals(ACT_ALTERAPPLY_CAPPLY)) {
            // 代理申請
            param.setTargetUser(param.getSearchEmp());
        } else if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)) {
            param.setTargetUser(param.getUserCode());
        }

        // TMG_ERRMSGテーブルを使用する前に一度きれいに削除する
        int deleteErrMsg = deleteErrMsg();
        int deleteNotificationCheck = deleteNotificationnCheck();

        if (param.getAction().equals(ACT_REMAKEAPPLY_CAPPLY)) {
            // 再申請の場合は、再申請用
            int insertNotificationCheckUpdate = insertNotificationCheckUpdate();
            //int insertErrMsgUpdate=insertErrMsgUpdate(param);
        }

        if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY) || param.getAction().equals(ACT_ALTERAPPLY_CAPPLY)) {
            // 新規申請の場合は、新規申請用
            int insertNotificationCheckUpdate = insertNotificationCheckNew();
            //int insertErrMsgUpdate=deleteNotificationnCheck(param);
        }

        int insertErrmsg = insertErrMsg();
        int insertTrigger = insertTrigger();
        String selectErrMsg = selectErrCode();
        int deleteTrigger = deleteTrigger();
        int deleteErrMsgAfter = deleteErrMsg();
        int deleteNotificationCheckAfter = deleteNotificationnCheck();
    }

    /**
     * 申請取下の処理をするメソッド
     *
     * @return なし
     * editWithdraw
     */
    public void actionEditWithdraw(ModelMap modelMap) {
        paramSetting();

        // 申請取下
        int updateNotificationWithdraw = updateNotificationWithdraw();
        int insertTrigger = insertTrigger();
        int deleteTrigger = deleteTrigger();
    }

    /**
     * 申請取消時、データ更新処理を行います。
     *
     * @return なし
     * updateApply
     */
    public void actionUpdateApply(ModelMap modelMap) {
        paramSetting();

        String tempTargetUser = param.getTargetUser();
        String tempUserCode = param.getUserCode();
        param.setTargetUser("");

        // TMG_ERRMSGテーブルにゴミが残っているといつまでも正常に処理されなくなる可能性があるため
        // TMG_ERRMSGテーブルを使用する前に一度きれいに削除する
        int deleteErrMsg = deleteErrMsg();
        int deleteNotificationCheck = deleteNotificationnCheck();
        int insertNotificationCheckUpdate = insertNotificationCheckUpdate();
        int insertErrmsg = insertErrMsg();
        int insertTrigger = insertTrigger();
        String selectErrMsg = selectErrCode();
        int deleteTrigger = deleteTrigger();
        int deleteErrMsgAfter = deleteErrMsg();
        int deleteNotificationCheckAfter = deleteNotificationnCheck();


        if (!isPartOfReserveApplication()) {

            //requestHash.put(PARAMERTER_KEY_ACTION, ACT_MAKEAPPLY_CAPPLY);
            //TODO
            // setTargetUser(getReqParm("empid"));
            //TODO
            // setUserCode(getReqParm("empid"));
            // 開始日を登録
            //requestHash.put("begin", doCalcForTypeStringOfDate(getReqParm("txtDCancelEnd"), 1, Calendar.DATE));
            // end は部分解除した元レコードの終了日をendとして持っているので設定しない
            deleteErrMsg = deleteErrMsg();
            deleteNotificationCheck = deleteNotificationnCheck();

            int insertNotificationCheckPartOfReApp = insertNotificationCheckPartOfReApp(tempUserCode);
            int insertErrMsgNew = insertErrMsgNew();

            insertTrigger = insertTrigger();
            selectErrMsg = selectErrCode();
            deleteTrigger = deleteTrigger();
            deleteErrMsgAfter = deleteErrMsg();
            deleteNotificationCheckAfter = deleteNotificationnCheck();

        }
        param.setTargetUser(tempTargetUser);
        param.setUserCode(tempUserCode);
    }

    /**
     * 一覧表示(承認者)の処理をするメソッド
     *
     * @return なし
     * showDispPerm
     */
    public void actionShowDispPerm(ModelMap modelMap) {
        paramSetting();
        //todo
       /* if(StrUtil.hasEmpty(param.getEmployeeListSql())){
            return;
        }
        if(StrUtil.hasEmpty(referList.getTargetSec())){
            return;
        }
        */
        checkEmp();
        //0 一覧
        param.setMgdSql(buildSQLForSelectGenericDetail("TMG_NTFTYPE", null, "MGD_CMASTERCODE"));
        List<notificationListVo> notificationListVoList = iTmgNotificationService.selectNotificationList(param);
        modelMap.addAttribute("notificationListVoList", notificationListVoList);
        // 1 申請区分マスタ
        //List<mgdTmgNtfTypeVo> mgdTmgNtfTypeVoS = iMastGenericDetailService.selectMasterTmgNtfType(param.getCustId(),
                //param.getCompId(), param.getToday(), param.getTargetUser(), param.getLang(), param.getSiteId());
        //modelMap.addAttribute("mgdTmgNtfTypeVoS", mgdTmgNtfTypeVoS);
        //2 申請ステータスマスタ
        List<Map<String, Object>> mgdList = iMastGenericDetailService.selectGenericDetail(buildSQLForSelectGenericDetail(TmgUtil.Cs_MGD_NTFSTATUS, "asc"));
        modelMap.addAttribute("mgdList", mgdList);
        // TODO 3 職員一覧
        //List<employeeListVo> employeeListVos=iHistDesignationService.selectemployeeList(param.getCustId(),param.getCompId(),param.getToday(),param.getEmployeeListSql());
        //modelMap.addAttribute("employeeListVos", employeeListVos);
        // TODO 4 所属名
        //String sectionNAme=iHistDesignationService.selectSectionNAme(param.getCustId(),param.getCompId(),param.getTodayD(),referList.getTargetSec());
        //modelMap.addAttribute("sectionNAme", sectionNAme);
        // TODO 5 件数
        int selectNotificationCount=iTmgNotificationService.selectNotificationCount(param);;
        //modelMap.addAttribute("selectNotificationCount", selectNotificationCount);
        // 6 遡り期限
        String selectBackLimit = iTmgNotificationService.selectBackLimit(param.getCustId(), param.getCompId(), param.getTargetUser());
        ;
        //modelMap.addAttribute("selectBackLimit", selectBackLimit);
        // 7 SYSDATE取得
        String nowDate = iMastGenericDetailService.selectSysdate();
        //modelMap.addAttribute("nowDate", nowDate);

    }

    /**
     * 承認後更新
     * updateItem
     *
     * @throws
     */
    private void actionUpdateItem(ModelMap modelMap) {
        paramSetting();

        int updateNotificationItem = iTmgNotificationService.updateNotificationItem(param);
    }


    /**
     * 本人取下処理の後画面表示処理をするメソッド
     *
     * @return なし
     * showWithdraw
     */
    private void actionShowWithdraw(ModelMap modelMap) {
        paramSetting();

        //0 一覧
        param.setMgdSql(buildSQLForSelectGenericDetail("TMG_NTFTYPE", null, "MGD_CMASTERCODE"));
        List<notificationListVo> notificationListVoList = iTmgNotificationService.selectNotificationList(param);
        modelMap.addAttribute("notificationListVoList", notificationListVoList);
        //2 申請区分マスタ
        List<Map<String, Object>> mgdTypeList = iMastGenericDetailService.selectGenericDetail(buildSQLForSelectGenericDetail(TmgUtil.Cs_MGD_NTFTYPE, "asc"));
        modelMap.addAttribute("mgdTypeList", mgdTypeList);
        //2 申請ステータスマスタ
        List<Map<String, Object>> mgdStatusList = iMastGenericDetailService.selectGenericDetail(buildSQLForSelectGenericDetail(TmgUtil.Cs_MGD_NTFSTATUS, "asc"));
        modelMap.addAttribute("mgdStatusList", mgdStatusList);
        //3 年次休暇残日数及び時間
        List<restYearPaidHolidayVo> restYearPaidHolidayVoList = iTmgPaidHolidayService.selectNenjikyukazannissu(param, 0);
        modelMap.addAttribute("restYearPaidHolidayVoList", restYearPaidHolidayVoList);
        //4 今月の月中有給付与の情報
        List<paidHolidayThisMonthInfoVo> paidHolidayThisMonthInfoVoList = iTmgMonthlyService.selectPaidHolidayThisMonthInfo(param.getCompId(), param.getCustId(), param.getTargetUser());
        modelMap.addAttribute("paidHolidayThisMonthInfoVoList", paidHolidayThisMonthInfoVoList);

    }


    /**
     * 職員リストにEmpの職員番号があるかをチェックし、なければEmpをクリアする。
     */
    private void checkEmp() {
        if (param.getSearchEmp() == null || param.getSearchEmp().equals("")) {
            return;
        }
        // 検索
        List<employeeListVo> employeeListVos = iHistDesignationService.selectemployeeList(param.getCustId(), param.getCompId(), param.getToday(), param.getEmployeeListSql());
        for (employeeListVo vo : employeeListVos) {
            if (param.getSearchEmp().equals(vo.getTntfCemployeeid())) {
                return;
            }
        }
        param.setSearchEmp("");
        //requestHash.remove("search_emp");
    }


    /**
     * 申請区分略称を取得するSQLを返す
     *
     * @return String
     */
    private String selectNtfName() {
        return iTmgNotificationService.selectNtfName(param.getCustId(), param.getCompId(), param.getNtfNo());
    }

    /**
     * 指定された日付、計算値をjava.util.Calendarで算出し文字列で返します。
     *
     * @param date   指定された日付
     * @param mvVal  計算値
     * @param mvMode {@link java.util.Calendar}の定数値
     * @return 「yyyy/MM/dd」形式の日付を表す文字列
     */
    private String doCalcForTypeStringOfDate(String date, int mvVal, int mvMode) {
        if (date == null) {
            return null;
        }
        GregorianCalendar cal = (GregorianCalendar) getCalendarOfTypeStringOfDate(date);
        cal.add(mvMode, mvVal);
        return new SimpleDateFormat(DATE_FORMAT).format(cal.getTime());
    }


    /**
     * 「yyyy/MM/dd」形式の日付文字列を{@link java.util.Calendar}型に設定して返します。
     *
     * @param date 「yyyy/MM/dd」形式の日付文字列
     * @return {@link java.util.Calendar}オブジェクトに変換された「yyyy/MM/dd」形式の日付文字列
     */
    private Calendar getCalendarOfTypeStringOfDate(String date) {
        int[] dates = new int[3];
        GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
        dates = divideDate(date);
        // 指定された日付でカレンダーオブジェクトを生成
        cal.set(dates[0], (dates[1] - 1), dates[2]);
        return cal;
    }

    /**
     * yyyy/MM/dd形式の文字列を「yyyy」,「MM」,「dd」分割し数値型配列に格納します。
     */
    private int[] divideDate(String date) throws NumberFormatException {
        int[] dates = new int[3];
        try {
            StringTokenizer st = new StringTokenizer(date, "/");
            for (int i = 0; st.hasMoreTokens(); i++) {
                dates[i] = Integer.parseInt(st.nextToken());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return dates;
    }


    /**
     * 申請状態の更新をするSQLを返す
     * <p>
     * buildSQLForUpdateNotificationWithdraw
     *
     * @return String      SQL文
     */
    private int updateNotificationWithdraw() {

        TmgNotificationDO tmgNotificationDO = new TmgNotificationDO();
        tmgNotificationDO.setTntfCmodifieruserid(param.getUserCode());
        tmgNotificationDO.setTntfDmodifieddate(DateTime.now());
        tmgNotificationDO.setTntfCmodifierprogramid(BEAN_DESC + "_" + param.getAction());
        tmgNotificationDO.setTntfCstatusflg(STATUS_WITHDRAW);
        tmgNotificationDO.setTntfCapprovalLevel("");
        tmgNotificationDO.setTntfCsiteid(param.getSiteId());
        tmgNotificationDO.setTntfCntfaction(param.getNtfAction());


        QueryWrapper<TmgNotificationDO> tnDo = new QueryWrapper<TmgNotificationDO>();
        tnDo.eq("TNTF_CCUSTOMERID", param.getCustId());
        tnDo.eq("TNTF_CCOMPANYID", param.getCompId());
        tnDo.eq("TNTF_CNTFNO", param.getNtfNo());

        return iTmgNotificationService.getBaseMapper().update(tmgNotificationDO, tnDo);
    }

    /**
     * 新規申請時にチェック処理を行うSQL文を返す
     * 登録時は、イントラマート側の更新が必要になるが、チェック結果によって更新の可否が分かれるため
     * 内容は、就業側のチェック処理のみ
     *
     * @return Vector    SQL
     */
    public String getSQLVecForAjaxNewCheck() throws IOException {

        if (param.getAction().equals(ACT_ALTERAPPLY_CAPPLY)) {
            // 代理申請
            param.setTargetUser(param.getSearchEmp());
        } else if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)) {
            param.setTargetUser(param.getUserCode());
        }

        // TMG_ERRMSGテーブルを使用する前に一度きれいに削除する
        int deleteErrMsg = deleteErrMsg();
        int deleteNotificationCheck = deleteNotificationnCheck();

        if (param.getAction().equals(ACT_REMAKEAPPLY_CAPPLY)) {
            // 再申請の場合は、再申請用
            int insertNotificationCheckUpdate = insertNotificationCheckUpdate();
            int insertErrMsgUpdate = insertErrMsgUpdate();
        }

        if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY) || param.getAction().equals(ACT_ALTERAPPLY_CAPPLY)) {
            // 新規申請の場合は、新規申請用
            int insertNotificationCheckUpdate = insertNotificationCheckNew();
            int insertErrMsgUpdate = deleteNotificationnCheck();
        }

        int insertErrmsg = insertErrMsg();

        String selectErrMsg = selectErrCode();

        int deleteErrMsgAfter = deleteErrMsg();
        int deleteNotificationCheckAfter = deleteNotificationnCheck();

        return selectErrMsg;
    }


    /**
     * エラーメッセージを削除するSQLを返す
     * buildSQLForDeleteNotificationnCheck
     *
     * @return String SQL文
     */
    private int deleteNotificationnCheck() {
        // アクション識別子の取得
        String sAction = param.getAction();
        // 起動時
        if (sAction == null) {
            if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)) {
                sAction = ACT_DISPINP_RLIST;    // 一覧表示(本人)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            }
        }

        Map errMsg = new HashMap();
        errMsg.put("TNTF_CMODIFIERUSERID", param.getUserCode());
        errMsg.put("TNTF_CMODIFIERPROGRAMID", BEAN_DESC + "_" + sAction);

        return iTmgNotificationCheckService.getBaseMapper().deleteByMap(errMsg);
    }

    /**
     * エラーメッセージを削除するSQLを返す
     * buildSQLForDeleteErrMsg
     *
     * @return String SQL文
     */
    private int deleteErrMsg() {
        // アクション識別子の取得
        String sAction = param.getAction();
        // 起動時
        if (sAction == null) {
            if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)) {
                sAction = ACT_DISPINP_RLIST;    // 一覧表示(本人)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            }
        }
        Map errMsg = new HashMap();
        errMsg.put("TER_CCUSTOMERID", param.getCustId());
        errMsg.put("TER_CCOMPANYID", param.getCompId());
        errMsg.put("TER_CMODIFIERUSERID", param.getUserCode());
        errMsg.put("TER_CMODIFIERPROGRAMID", BEAN_DESC + "_" + sAction);

        return iTmgErrmsgService.getBaseMapper().deleteByMap(errMsg);
    }


    /**
     * エラーチェックに追加するSQLを返す
     * buildSQLForInsertNotificationCheckNew
     *
     * @return int   SQL文
     */
    private int insertNotificationCheckNew() {
        // 初期化
        boolean bLevelCheckFlg = false;
        boolean bSubstitute = isSubstituted(); // 申請が振替タイプか判定

        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();

        //基本情報
        tncDo.setTntfCcustomerid(param.getCustId());
        tncDo.setTntfCcompanyid(param.getCompId());
        tncDo.setTntfCemployeeid(param.getTargetUser());
        tncDo.setTntfDstartdate(TmgUtil.minDate);
        tncDo.setTntfDenddate(TmgUtil.maxDate);
        //申請について
        tncDo.setTntfCmodifieruserid(param.getUserCode());
        tncDo.setTntfDmodifieddate(DateTime.now());
        //action
        if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)) {    // 新規申請
            tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_MAKEAPPLY_CAPPLY);
        } else {    // 代理申請
            tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_ALTERAPPLY_CAPPLY);
        }
        //シーケンス
        tncDo.setTntfNseq(Long.parseLong(param.getSeq()));
        tncDo.setTntfCntfno(param.getUserCode() + "|" + param.getSeq());


        if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)) {// 新規申請
            tncDo.setTntfCstatusflg(STATUS_WAIT);
        } else {// 代理申請
            // 決裁レベル判定用
            bLevelCheckFlg = hasCheckApprovelLevel(param.getFinalApprovalLevel(), param.getApprovalLevel(), param.getSiteId());
            // 承認済判定 管理サイトからの承認は即承認済に
            if (bLevelCheckFlg) {
                tncDo.setTntfCstatusflg(STATUS_PERM); // 承認済
            } else {
                tncDo.setTntfCstatusflg(STATUS_WAIT);// 承認待ち
            }
        }
        tncDo.setTntfCalteremployeeid(param.getUserCode());
        tncDo.setTntfDnotification(DateTime.now());

        tncDo.setTntfDbegin(param.getBegin());
        // 代休の場合は休出日(開始)を終了日にセットし、代休(終了日)を代休カラムにセットする
        if (bSubstitute) {
            tncDo.setTntfDend(param.getBegin());
        } else {
            tncDo.setTntfDend(param.getEnd());
        }

        //非取り消しの場合
        tncDo.setTntfDcancel(null);

        if (!StrUtil.hasEmpty(param.getTimeOpen())) {
            tncDo.setTntfNtimeOpen(Long.parseLong(param.getTimeOpen()));
        }
        if (!StrUtil.hasEmpty(param.getTimeClose())) {
            tncDo.setTntfNtimeOpen(Long.parseLong(param.getTimeClose()));
        }
        if (!StrUtil.hasEmpty(param.getTimezoneOpen())) {
            tncDo.setTntfNtimezoneOpen(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTimezoneOpen()));
        }
        if (!StrUtil.hasEmpty(param.getTimezoneClose())) {
            tncDo.setTntfNtimezoneClose(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTimezoneClose()));
        }


        tncDo.setTntfNnoreserved(Long.parseLong(param.getNoreserved()));
        tncDo.setTntfNmon(Long.parseLong(param.getMon()));
        tncDo.setTntfNtue(Long.parseLong(param.getTue()));
        tncDo.setTntfNwed(Long.parseLong(param.getWed()));
        tncDo.setTntfNthu(Long.parseLong(param.getThu()));
        tncDo.setTntfNfri(Long.parseLong(param.getFri()));
        tncDo.setTntfNsat(Long.parseLong(param.getSat()));
        tncDo.setTntfNsun(Long.parseLong(param.getSun()));
        if (StrUtil.hasEmpty(param.getNoreserved())) {
            tncDo.setTntfNdayofweek(iMastGenericDetailService.toDayofWeek(param.getMon(),
                    param.getTue(),
                    param.getWed(),
                    param.getThu(),
                    param.getFri(),
                    param.getSat(),
                    param.getSun()));
        } else {
            tncDo.setTntfNdayofweek(iMastGenericDetailService.toDayofWeek(null, null, null, null, null, null, null));
        }

        tncDo.setTntfCtype(param.getTypeNew());
        tncDo.setTntfCowncomment(param.getOwncomment());

        if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)) {// 新規申請

        } else {// 代理申請
            tncDo.setTntfCboss(param.getUserCode());
            tncDo.setTntfCbosscomment(param.getBosscomment());
            tncDo.setTntfDboss(DateTime.now());
        }

        tncDo.setTntfCcancel(null);
        tncDo.setTntfCcancelcomment(null);

        // 代休の場合は休出日(開始)を終了日にセットし、代休(終了日)を代休カラムにセットする
        if (bSubstitute) {
            tncDo.setTntfDdaikyu(param.getEnd());
        }

        // 傷病名
        if (!StrUtil.hasEmpty(param.getTxtSickName())) {
            tncDo.setTntfCsickName(param.getTxtSickName());
        }
        // 労災申請
        if (!StrUtil.hasEmpty(param.getSickApply())) {
            tncDo.setTntfCdisaster(param.getSickApply());
        }
        //起算日
        if (param.getTxtPeriod() != null) {
            tncDo.setTntfDperiodDate(param.getTxtPeriod());
        }
        //加算日数
        if (!StrUtil.hasEmpty(param.getTxtAddDate())) {
            tncDo.setTntfNuapperAddition(Long.parseLong(param.getTxtAddDate()));
        }


        tncDo.setTntfCntfnoIm(null);
        // 休憩時間From
        tncDo.setTntfNrestopen(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTxtRestOpen()));
        // 休憩時間To
        tncDo.setTntfNrestclose(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTxtRestClose()));
        // 氏名
        tncDo.setTntfCkanjiname(param.getTxtName());
        // 続柄
        tncDo.setTntfCrelation(param.getTxtRelation());
        // 生年月日
        tncDo.setTntfDdateofbirth(param.getTxtBirthday());
        // 対象の人数
        tncDo.setTntfNnumberOfTarget(Long.parseLong(StringUtils.defaultIfEmpty(param.getTxtTargetNumber(), "NULL")));
        tncDo.setTntfCntfnoMoto(null);


        if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)) {// 新規申請
            tncDo.setTntfCapprovalLevel(getApprovelLevel(1));
        } else {// 代理申請
            // 承認済判定 管理サイトからの承認は即承認済に
            if (bLevelCheckFlg) {
                tncDo.setTntfCapprovalLevel(null);
            } else {
                tncDo.setTntfCapprovalLevel(getApprovelLevel(0));
            }
        }

        tncDo.setTntfCsiteid(param.getSiteId());
        tncDo.setTntfCntfaction(param.getNtfAction());

        //更新処理
        return iTmgNotificationCheckService.getBaseMapper().insert(tncDo);
    }


    /**
     * エラーチェックに追加するSQLを返す
     * buildSQLForInsertNotificationCheckUpdate
     *
     * @return int   SQL文
     */
    private int insertNotificationCheckUpdate() {

        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();

        //元データを取り
        QueryWrapper<TmgNotificationDO> queryWrapper = new QueryWrapper<TmgNotificationDO>();
        queryWrapper.eq("TNTF_CCUSTOMERID", param.getCustId());
        queryWrapper.eq("TNTF_CCOMPANYID", param.getCompId());
        queryWrapper.eq("TNTF_CNTFNO", param.getNtfNo());
        TmgNotificationDO tnDo = iTmgNotificationService.getBaseMapper().selectOne(queryWrapper);
        //更新データ
        // 再申請
        if (param.getAction().equals(ACT_REMAKEAPPLY_CAPPLY)) {
            tncDo = reApplySetDo(tnDo);
            //全取消
        } else if (param.getAction().equals(ACT_EDITAPPLY_UDEL)) {
            tncDo = delApplu(tnDo);
            // 承認・他
        } else {
            tncDo = applyOrConfirm(tnDo);
        }

        // 承認か解除(部分取り消し、全取り消し)か？
        if (!(param.getAction().equals(ACT_EDITPERM_UPERMIT) ||
                param.getAction().equals(ACT_EDITCANCEL_UCANCEL) ||
                param.getAction().equals(ACT_EDITAPPLY_UDEL))) {
            tncDo.setTntfDcancel(null);
            tncDo.setTntfDcancelend(null);
        }
        // 承認・管理からの再申請に対応する為
        if (!(param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN) || param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM))) {
            // 承認か解除であれば、承認者コメント、取消コメントのカラムをINSERTに指定する
            tncDo.setTntfCboss(null);
            tncDo.setTntfCbosscomment(null);
            tncDo.setTntfDboss(null);
            tncDo.setTntfCcancel(null);
            tncDo.setTntfCcancelcomment(null);
        }
        return iTmgNotificationCheckService.getBaseMapper().insert(tncDo);
    }

    /**
     * 部分申請解除.エラーチェックに追加するSQLを返す
     *
     * @param employeeId 承認者職員番号
     * @return int       SQL文
     */
    private int insertNotificationCheckPartOfReApp(String employeeId) {

        //元データを取り
        QueryWrapper<TmgNotificationDO> queryWrapper = new QueryWrapper<TmgNotificationDO>();
        queryWrapper.eq("TNTF_CCUSTOMERID", param.getCustId());
        queryWrapper.eq("TNTF_CCOMPANYID", param.getCompId());
        queryWrapper.eq("TNTF_CNTFNO", param.getNtfNo());
        TmgNotificationDO tnDo = iTmgNotificationService.getBaseMapper().selectOne(queryWrapper);

        int seq = Integer.parseInt(iTmgNotificationService.selectNotificationSeq());
        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();

        tncDo.setTntfCcustomerid(param.getCustId());
        tncDo.setTntfCcompanyid(param.getCompId());
        tncDo.setTntfCemployeeid(param.getTargetUser());
        tncDo.setTntfDstartdate(TmgUtil.minDate);
        tncDo.setTntfDenddate(TmgUtil.maxDate);
        tncDo.setTntfCmodifieruserid(param.getUserCode());
        tncDo.setTntfDmodifieddate(DateTime.now());
        tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_MAKEAPPLY_CAPPLY);
        tncDo.setTntfNseq((long) seq);
        tncDo.setTntfCntfno(param.getUserCode() + "|" + seq);
        tncDo.setTntfCstatusflg(STATUS_PERM);
        tncDo.setTntfCalteremployeeid(param.getUserCode());
        tncDo.setTntfDnotification(DateTime.now());
        tncDo.setTntfDbegin(param.getBegin());
        tncDo.setTntfDend(param.getEnd());
        tncDo.setTntfDcancel(null);

        tncDo.setTntfNtimeOpen(tnDo.getTntfNtimeOpen());
        tncDo.setTntfNtimeClose(tnDo.getTntfNtimeClose());
        tncDo.setTntfNtimezoneOpen(tnDo.getTntfNtimezoneOpen());
        tncDo.setTntfNtimezoneClose(tnDo.getTntfNtimezoneClose());
        tncDo.setTntfNnoreserved(tnDo.getTntfNnoreserved());
        tncDo.setTntfNmon(tnDo.getTntfNmon());
        tncDo.setTntfNtue(tnDo.getTntfNtue());
        tncDo.setTntfNwed(tnDo.getTntfNwed());
        tncDo.setTntfNthu(tnDo.getTntfNthu());
        tncDo.setTntfNfri(tnDo.getTntfNfri());
        tncDo.setTntfNsat(tnDo.getTntfNsat());
        tncDo.setTntfNsun(tnDo.getTntfNsun());
        tncDo.setTntfNdayofweek(tnDo.getTntfNdayofweek());
        tncDo.setTntfCtype(tnDo.getTntfCtype());

        tncDo.setTntfCowncomment(param.getOwncomment());

        tncDo.setTntfCboss(employeeId);

        tncDo.setTntfCbosscomment(null);
        tncDo.setTntfDboss(DateTime.now());
        tncDo.setTntfCcancel(null);
        tncDo.setTntfCcancelcomment(null);
        //tncDo.setTntfDdaikyu();
        tncDo.setTntfCsickName(tnDo.getTntfCsickName());
        tncDo.setTntfCdisaster(tnDo.getTntfCdisaster());
        tncDo.setTntfDperiodDate(tnDo.getTntfDperiodDate());
        tncDo.setTntfNuapperAddition(tnDo.getTntfNuapperAddition());
        tncDo.setTntfCntfnoIm(tnDo.getTntfCntfnoIm());
        tncDo.setTntfNrestopen(tnDo.getTntfNrestopen());
        tncDo.setTntfNrestclose(tnDo.getTntfNrestclose());
        tncDo.setTntfCkanjiname(tnDo.getTntfCkanjiname());
        tncDo.setTntfCrelation(tnDo.getTntfCrelation());
        tncDo.setTntfDdateofbirth(tnDo.getTntfDdateofbirth());
        tncDo.setTntfNnumberOfTarget(tnDo.getTntfNnumberOfTarget());
        tncDo.setTntfCntfnoMoto(tnDo.getTntfCntfnoMoto());
        tncDo.setTntfCapprovalLevel(tnDo.getTntfCapprovalLevel());
        tncDo.setTntfCsiteid(tnDo.getTntfCsiteid());
        tncDo.setTntfCntfaction(tnDo.getTntfCntfaction());

        return iTmgNotificationCheckService.getBaseMapper().insert(tncDo);
    }

    /**
     * 再申請
     * update用entity処理
     */
    public TmgNotificationCheckDO reApplySetDo(TmgNotificationDO tnDo) {

        boolean bSubstitute = isSubstituted(); // 申請が振替タイプか判定

        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();

        tncDo.setTntfCcustomerid(tnDo.getTntfCcustomerid());
        tncDo.setTntfCcompanyid(tnDo.getTntfCcompanyid());
        tncDo.setTntfCemployeeid(tnDo.getTntfCemployeeid());
        tncDo.setTntfDstartdate(tncDo.getTntfDstartdate());
        tncDo.setTntfDenddate(tncDo.getTntfDenddate());

        tncDo.setTntfCmodifieruserid(param.getUserCode());

        tncDo.setTntfDmodifieddate(DateTime.now());
        tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_REMAKEAPPLY_CAPPLY);

        tncDo.setTntfNseq(tnDo.getTntfNseq());
        tncDo.setTntfCntfno(tnDo.getTntfCntfno());

        // 入力サイトは承認済みにする
        if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)) {
            tncDo.setTntfCstatusflg(STATUS_WAIT);//承認待ち
        } else {
            // レベル判定
            if (Boolean.valueOf(param.getFinalApprovalLevel())) {
                tncDo.setTntfCstatusflg(STATUS_PERM);
            } else {
                tncDo.setTntfCstatusflg(STATUS_WAIT); //承認待ち
            }
        }

        tncDo.setTntfCalteremployeeid(tnDo.getTntfCalteremployeeid());
        tncDo.setTntfDnotification(tnDo.getTntfDnotification());

        tncDo.setTntfDbegin(param.getBegin());


        // 代休の場合は休出日(開始)を終了日にセットし、代休(終了日)を代休カラムにセットする
        if (bSubstitute) {
            tncDo.setTntfDend(param.getBegin());
        } else {
            tncDo.setTntfDend(param.getEnd());
        }

        //tncDo.setTntfDcancel();

        if (!StrUtil.hasEmpty(param.getTimeOpen())) {
            tncDo.setTntfNtimeOpen(Long.parseLong(param.getTimeOpen()));
        }
        if (!StrUtil.hasEmpty(param.getTimeClose())) {
            tncDo.setTntfNtimeOpen(Long.parseLong(param.getTimeClose()));
        }
        if (!StrUtil.hasEmpty(param.getTimezoneOpen())) {
            tncDo.setTntfNtimezoneOpen(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTimezoneOpen()));
        }
        if (!StrUtil.hasEmpty(param.getTimezoneClose())) {
            tncDo.setTntfNtimezoneClose(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTimezoneClose()));
        }

        tncDo.setTntfNnoreserved(Long.parseLong(param.getNoreserved()));
        tncDo.setTntfNmon(Long.parseLong(param.getMon()));
        tncDo.setTntfNtue(Long.parseLong(param.getTue()));
        tncDo.setTntfNwed(Long.parseLong(param.getWed()));
        tncDo.setTntfNthu(Long.parseLong(param.getThu()));
        tncDo.setTntfNfri(Long.parseLong(param.getFri()));
        tncDo.setTntfNsat(Long.parseLong(param.getSat()));
        tncDo.setTntfNsun(Long.parseLong(param.getSun()));
        if (StrUtil.hasEmpty(param.getNoreserved())) {
            tncDo.setTntfNdayofweek(iMastGenericDetailService.toDayofWeek(param.getMon(),
                    param.getTue(),
                    param.getWed(),
                    param.getThu(),
                    param.getFri(),
                    param.getSat(),
                    param.getSun()));
        } else {
            tncDo.setTntfNdayofweek(iMastGenericDetailService.toDayofWeek(null, null, null, null, null, null, null));
        }


        tncDo.setTntfCtype(param.getTypeNew());
        tncDo.setTntfCowncomment(param.getOwncomment());

        // 承認・管理からの再申請に対応する為
        if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN) || param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)) {
            //画面情報
            tncDo.setTntfCboss(param.getUserCode());
            tncDo.setTntfCbosscomment(param.getBosscomment());
            tncDo.setTntfDboss(DateTime.now());
            tncDo.setTntfCcancel(null);
            tncDo.setTntfCcancelcomment(null);
        }
        // 代休の場合は休出日(開始)を終了日にセットし、代休(終了日)を代休カラムにセットする
        if (bSubstitute) {
            tncDo.setTntfDdaikyu(param.getEnd());
        } else {
            tncDo.setTntfDdaikyu(null);
        }

        tncDo.setTntfCsickName(param.getTxtSickName());
        tncDo.setTntfCdisaster(param.getSickApply());
        tncDo.setTntfDperiodDate(param.getTxtPeriod());
        tncDo.setTntfNuapperAddition(Long.parseLong(param.getTxtAddDate()));

        tncDo.setTntfCntfnoIm(param.getNtfNo());// 申請番号

        // 休憩時間From
        tncDo.setTntfNrestopen(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTxtRestOpen()));
        // 休憩時間To
        tncDo.setTntfNrestclose(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTxtRestClose()));
        // 氏名
        tncDo.setTntfCkanjiname(param.getTxtName());
        // 続柄
        tncDo.setTntfCrelation(param.getTxtRelation());
        // 生年月日
        tncDo.setTntfDdateofbirth(param.getTxtBirthday());
        // 対象の人数
        tncDo.setTntfNnumberOfTarget(Long.parseLong(StringUtils.defaultIfEmpty(param.getTxtTargetNumber(), "NULL")));

        tncDo.setTntfCntfnoMoto(tnDo.getTntfCntfnoMoto());// 分割前申請番号


        // レベル判定
        if (Boolean.valueOf(param.getFinalApprovalLevel())) {
            tncDo.setTntfCapprovalLevel(null);
        } else {
            // 承認・管理からの再申請に対応する為
            if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN) || param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                tncDo.setTntfCapprovalLevel(getApprovelLevel(0));
            } else {
                tncDo.setTntfCapprovalLevel(getApprovelLevel(1));
            }
        }

        tncDo.setTntfCsiteid(param.getSiteId());
        tncDo.setTntfCntfaction(param.getNtfAction());

        return tncDo;
    }

    /**
     * 全取消
     * update用entity処理
     */
    public TmgNotificationCheckDO delApplu(TmgNotificationDO tnDo) {
        //update用entity処理
        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();

        tncDo.setTntfCcustomerid(tnDo.getTntfCcustomerid());
        tncDo.setTntfCcompanyid(tnDo.getTntfCcompanyid());
        tncDo.setTntfCemployeeid(tnDo.getTntfCemployeeid());
        tncDo.setTntfDstartdate(tnDo.getTntfDstartdate());
        tncDo.setTntfDenddate(tnDo.getTntfDenddate());

        tncDo.setTntfCmodifieruserid(param.getUserCode());
        tncDo.setTntfDmodifieddate(DateTime.now());
        tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_EDITAPPLY_UDEL);

        tncDo.setTntfNseq(tnDo.getTntfNseq());
        tncDo.setTntfCntfno(tnDo.getTntfCntfno());
        tncDo.setTntfCstatusflg(tnDo.getTntfCstatusflg());
        tncDo.setTntfCalteremployeeid(tnDo.getTntfCalteremployeeid());
        tncDo.setTntfDnotification(tnDo.getTntfDnotification());
        tncDo.setTntfDbegin(tnDo.getTntfDbegin());
        tncDo.setTntfDend(tnDo.getTntfDend());

        tncDo.setTntfDcancel(param.getCancel());

        tncDo.setTntfDcancelend(tnDo.getTntfDend());
        tncDo.setTntfNtimeOpen(tnDo.getTntfNtimeOpen());
        tncDo.setTntfNtimeClose(tnDo.getTntfNtimeClose());
        tncDo.setTntfNtimezoneOpen(tnDo.getTntfNtimezoneOpen());
        tncDo.setTntfNtimezoneClose(tnDo.getTntfNtimezoneClose());
        tncDo.setTntfNnoreserved(tnDo.getTntfNnoreserved());
        tncDo.setTntfNmon(tnDo.getTntfNmon());
        tncDo.setTntfNtue(tnDo.getTntfNtue());
        tncDo.setTntfNwed(tnDo.getTntfNwed());
        tncDo.setTntfNthu(tnDo.getTntfNthu());
        tncDo.setTntfNfri(tnDo.getTntfNfri());
        tncDo.setTntfNsat(tnDo.getTntfNsat());
        tncDo.setTntfNsun(tnDo.getTntfNsun());
        tncDo.setTntfNdayofweek(tnDo.getTntfNdayofweek());
        tncDo.setTntfCtype(tnDo.getTntfCtype());
        tncDo.setTntfCowncomment(tnDo.getTntfCowncomment());
        tncDo.setTntfCboss(tnDo.getTntfCboss());
        tncDo.setTntfCbosscomment(tnDo.getTntfCbosscomment());
        tncDo.setTntfDboss(tnDo.getTntfDboss());

        tncDo.setTntfCcancel(param.getUserCode());
        tncDo.setTntfCcancelcomment(param.getCancelcomment());

        tncDo.setTntfDdaikyu(tnDo.getTntfDdaikyu());
        // 傷病種類は不要の為除去 //
        tncDo.setTntfCsickName(tnDo.getTntfCsickName());
        tncDo.setTntfCdisaster(tnDo.getTntfCdisaster());
        tncDo.setTntfDperiodDate(tnDo.getTntfDperiodDate());
        tncDo.setTntfNuapperAddition(tnDo.getTntfNuapperAddition());
        tncDo.setTntfCntfnoIm(tnDo.getTntfCntfnoIm());
        tncDo.setTntfNrestopen(tnDo.getTntfNrestopen());
        tncDo.setTntfNrestclose(tnDo.getTntfNrestclose());
        tncDo.setTntfCkanjiname(tnDo.getTntfCkanjiname());
        tncDo.setTntfCrelation(tnDo.getTntfCrelation());
        tncDo.setTntfDdateofbirth(tnDo.getTntfDdateofbirth());
        tncDo.setTntfNnumberOfTarget(tnDo.getTntfNnumberOfTarget());
        tncDo.setTntfCntfnoMoto(tnDo.getTntfCntfnoMoto());

        tncDo.setTntfCapprovalLevel(null);
        tncDo.setTntfCsiteid(param.getSiteId());
        tncDo.setTntfCntfaction(param.getNtfAction());

        return tncDo;
    }

    /**
     * 承認・他
     * update用entity処理
     */
    public TmgNotificationCheckDO applyOrConfirm(TmgNotificationDO tnDo) {
        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();

        tncDo.setTntfCcustomerid(tnDo.getTntfCcustomerid());
        tncDo.setTntfCcompanyid(tnDo.getTntfCcompanyid());
        tncDo.setTntfCemployeeid(tnDo.getTntfCemployeeid());
        tncDo.setTntfDstartdate(tnDo.getTntfDstartdate());
        tncDo.setTntfDenddate(tnDo.getTntfDenddate());

        tncDo.setTntfCmodifieruserid(param.getUserCode());
        tncDo.setTntfDmodifieddate(DateTime.now());

        if (param.getAction().equals(ACT_EDITCANCEL_UCANCEL)) {    // 解除
            tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_EDITCANCEL_UCANCEL);
        } else {    // 承認
            tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_EDITPERM_UPERMIT);
        }

        tncDo.setTntfNseq(tnDo.getTntfNseq());
        tncDo.setTntfCntfno(tnDo.getTntfCntfno());

        //ステータス
        if (param.getAction().equals(ACT_EDITCANCEL_UCANCEL)) { // 解除日=開始日の場合は取消となる
            tncDo.setTntfCstatusflg(tnDo.getTntfCstatusflg());
        } else {
            // レベル判定
            if (Boolean.valueOf(param.getFinalApprovalLevel())) {
                tncDo.setTntfCstatusflg(STATUS_PERM);
            } else {
                tncDo.setTntfCstatusflg(tnDo.getTntfCstatusflg());
            }
        }

        tncDo.setTntfCalteremployeeid(tnDo.getTntfCalteremployeeid());
        tncDo.setTntfDnotification(tnDo.getTntfDnotification());
        tncDo.setTntfDbegin(tnDo.getTntfDbegin());
        tncDo.setTntfDend(tnDo.getTntfDend());

        if (param.getAction().equals(ACT_EDITPERM_UPERMIT)) {
            tncDo.setTntfDcancel(tnDo.getTntfDcancel());
            tncDo.setTntfDcancelend(tnDo.getTntfDcancelend());
        }
        if (param.getAction().equals(ACT_EDITCANCEL_UCANCEL)) {
            tncDo.setTntfDcancel(param.getCancel());
            tncDo.setTntfDcancelend(param.getTxtDCancelEnd());
        }

        tncDo.setTntfNtimeOpen(tnDo.getTntfNtimeOpen());
        tncDo.setTntfNtimeClose(tnDo.getTntfNtimeClose());
        tncDo.setTntfNtimezoneOpen(tnDo.getTntfNtimezoneOpen());
        tncDo.setTntfNtimezoneClose(tnDo.getTntfNtimezoneClose());
        tncDo.setTntfNnoreserved(tnDo.getTntfNnoreserved());
        tncDo.setTntfNmon(tnDo.getTntfNmon());
        tncDo.setTntfNtue(tnDo.getTntfNtue());
        tncDo.setTntfNwed(tnDo.getTntfNwed());
        tncDo.setTntfNthu(tnDo.getTntfNthu());
        tncDo.setTntfNfri(tnDo.getTntfNfri());
        tncDo.setTntfNsat(tnDo.getTntfNsat());
        tncDo.setTntfNsun(tnDo.getTntfNsun());
        tncDo.setTntfNdayofweek(tnDo.getTntfNdayofweek());
        tncDo.setTntfCtype(tnDo.getTntfCtype());
        tncDo.setTntfCowncomment(tnDo.getTntfCowncomment());


        if (param.getAction().equals(ACT_EDITPERM_UPERMIT)) {
            tncDo.setTntfCboss(param.getUserCode());
            tncDo.setTntfCbosscomment(param.getBosscomment());
            tncDo.setTntfDboss(DateTime.now());
            tncDo.setTntfCcancel(tnDo.getTntfCcancel());
            tncDo.setTntfCcancelcomment(tnDo.getTntfCcancelcomment());
        }
        if (param.getAction().equals(ACT_EDITCANCEL_UCANCEL)) {
            tncDo.setTntfCboss(tnDo.getTntfCboss());
            tncDo.setTntfCbosscomment(tnDo.getTntfCbosscomment());
            tncDo.setTntfDboss(tnDo.getTntfDboss());
            tncDo.setTntfCcancel(param.getUserCode());
            tncDo.setTntfCcancelcomment(param.getCancelcomment());
        }


        tncDo.setTntfDdaikyu(tnDo.getTntfDdaikyu());
        tncDo.setTntfCsickName(tnDo.getTntfCsickName());
        tncDo.setTntfCdisaster(tnDo.getTntfCdisaster());
        tncDo.setTntfDperiodDate(tnDo.getTntfDperiodDate());
        tncDo.setTntfNuapperAddition(tnDo.getTntfNuapperAddition());
        tncDo.setTntfCntfnoIm(tnDo.getTntfCntfnoIm());
        tncDo.setTntfNrestopen(tnDo.getTntfNrestopen());
        tncDo.setTntfNrestclose(tnDo.getTntfNrestclose());
        tncDo.setTntfCkanjiname(tnDo.getTntfCkanjiname());
        tncDo.setTntfCrelation(tnDo.getTntfCrelation());
        tncDo.setTntfDdateofbirth(tnDo.getTntfDdateofbirth());
        tncDo.setTntfNnumberOfTarget(tnDo.getTntfNnumberOfTarget());
        tncDo.setTntfCntfnoMoto(tnDo.getTntfCntfnoMoto());

        if (param.getAction().equals(ACT_EDITPERM_UPERMIT)) {   // 承認
            // レベル判定
            if (Boolean.valueOf(param.getFinalApprovalLevel())) {
                tncDo.setTntfCapprovalLevel(null);
            } else {
                tncDo.setTntfCapprovalLevel(getApprovelLevel(0));
            }
        } else {
            tncDo.setTntfCapprovalLevel(null);
        }

        tncDo.setTntfCsiteid(param.getSiteId());
        tncDo.setTntfCntfaction(param.getNtfAction());

        return tncDo;
    }

    /**
     * 指定された申請種類の表示タイプを取得する
     * buildSQLForNtfDispType
     *
     * @return int inputCtl
     */
    private int selectNtfDispType() {

        int inputCtl = iMastGenericDetailService.selectNtfDispType(param.getCustId(), param.getCompId(), param.getLang(), param.getTypeNew());
        return inputCtl;
    }

    /**
     * 「振替」タイプの申請か判定を行う。
     *
     * @return boolean 振替タイプの場合、trueを返却、以外の場合、falseを返却
     */
    public boolean isSubstituted() {

        int sDispType = selectNtfDispType();

        // 取得した表示タイプを２進数変換
        String sBinaryDispType = Integer.toBinaryString(sDispType);

        // １桁目に１が立っていたら「振替」タイプと判定する。
        if ("1".equals(sBinaryDispType.substring(sBinaryDispType.length() - 1))) {

            // 振替タイプの場合、trueを返却
            return true;
        } else {

            // 振替タイプ以外の場合、falseを返却
            return false;
        }
    }

    /**
     * エラーメッセージ(更新)に追加する
     * buildSQLForInsertErrMsgUpdate
     *
     * @return int
     */
    private int insertErrMsgUpdate() {

        // 処理モードに合致したプログラムＩＤを設定する
        String sProgramId = "";

        // 申請解除、または申請取消
        if (ACT_EDITCANCEL_UCANCEL.equals(param.getAction()) || ACT_EDITAPPLY_UDEL.equals(param.getAction())) {
            sProgramId = BEAN_DESC + "_" + param.getAction();

            // 再申請
        } else if (ACT_REMAKEAPPLY_CAPPLY.equals(param.getAction())) {
            sProgramId = BEAN_DESC + "_" + ACT_REMAKEAPPLY_CAPPLY;

            // 承認
        } else {
            sProgramId = BEAN_DESC + "_" + ACT_EDITPERM_UPERMIT;
        }

        TmgErrmsgDO tmgErrmsgDO = new TmgErrmsgDO();
        tmgErrmsgDO.setTerCcustomerid(param.getCustId());
        tmgErrmsgDO.setTerCcompanyid(param.getCompId());
        tmgErrmsgDO.setTerDstartdate(TmgUtil.minDate);
        tmgErrmsgDO.setTerDenddate(TmgUtil.maxDate);
        tmgErrmsgDO.setTerCmodifieruserid(param.getUserCode());
        tmgErrmsgDO.setTerDmodifieddate(DateTime.now());
        tmgErrmsgDO.setTerCmodifierprogramid(sProgramId);
        tmgErrmsgDO.setTerCerrcode(iTmgNotificationCheckService.tmgFCheckNotification(param.getNtfNo(),
                param.getCustId(), param.getCompId(), param.getSiteId()));
        tmgErrmsgDO.setTerClanguage(param.getLang());
        return iTmgErrmsgService.getBaseMapper().insert(tmgErrmsgDO);
    }


    /**
     * エラーメッセージ(新規)に追加するS
     * buildSQLForInsertErrMsgNe
     *
     * @return int
     */
    private int insertErrMsgNew() {

        // 処理モードに合致したプログラムＩＤを設定する
        String sProgramId = "";

        if (ACT_MAKEAPPLY_CAPPLY.equals(param.getAction())) {
            // 本人申請
            sProgramId = BEAN_DESC + "_" + ACT_MAKEAPPLY_CAPPLY;
        } else {
            // 代理申請
            sProgramId = BEAN_DESC + "_" + ACT_ALTERAPPLY_CAPPLY;
        }

        TmgErrmsgDO tmgErrmsgDO = new TmgErrmsgDO();
        tmgErrmsgDO.setTerCcustomerid(param.getCustId());
        tmgErrmsgDO.setTerCcompanyid(param.getCompId());
        tmgErrmsgDO.setTerDstartdate(TmgUtil.minDate);
        tmgErrmsgDO.setTerDenddate(TmgUtil.maxDate);
        tmgErrmsgDO.setTerCmodifieruserid(param.getUserCode());
        tmgErrmsgDO.setTerDmodifieddate(DateTime.now());
        tmgErrmsgDO.setTerCmodifierprogramid(sProgramId);

        QueryWrapper<TmgNotificationCheckDO> tncDo = new QueryWrapper<TmgNotificationCheckDO>();
        tncDo.eq("TNTF_CMODIFIERUSERID", param.getUserCode());
        tncDo.eq("TNTF_CMODIFIERPROGRAMID", sProgramId);
        TmgNotificationCheckDO tmgNotificationCheckDO = iTmgNotificationCheckService.getBaseMapper().selectOne(tncDo);

        tmgErrmsgDO.setTerCerrcode(iTmgNotificationCheckService.tmgFCheckNotification(tmgNotificationCheckDO.getTntfCntfno(),
                tmgNotificationCheckDO.getTntfCcustomerid(),
                tmgNotificationCheckDO.getTntfCcompanyid(),
                param.getSiteId()));

        tmgErrmsgDO.setTerClanguage(param.getLang());


        return iTmgErrmsgService.getBaseMapper().insert(tmgErrmsgDO);
    }

    /**
     * エラーメッセージテーブルへ登録する
     * buildSQLForInsertErrMsg
     *
     * @return int
     */
    private int insertErrMsg() {

        String sProgramId = BEAN_DESC + "_" + param.getAction();

        TmgErrmsgDO tmgErrmsgDO = new TmgErrmsgDO();
        tmgErrmsgDO.setTerCcustomerid(param.getCustId());
        tmgErrmsgDO.setTerCcompanyid(param.getCompId());
        tmgErrmsgDO.setTerDstartdate(TmgUtil.minDate);
        tmgErrmsgDO.setTerDenddate(TmgUtil.maxDate);
        tmgErrmsgDO.setTerCmodifieruserid(param.getUserCode());
        tmgErrmsgDO.setTerDmodifieddate(DateTime.now());
        tmgErrmsgDO.setTerCmodifierprogramid(sProgramId);
        tmgErrmsgDO.setTerCerrcode("0");
        tmgErrmsgDO.setTerClanguage(param.getLang());

        return iTmgErrmsgService.getBaseMapper().insert(tmgErrmsgDO);
    }

    /**
     * エラーメッセージを取得する
     * buildSQLForSelectErrMsg
     *
     * @return String SQL文
     */
    private String selectErrCode() {
        // アクション識別子の取得
        String sAction = param.getAction();
        // 起動時
        if (sAction == null) {
            if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)) {
                sAction = ACT_DISPINP_RLIST;    // 一覧表示(本人)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            }
        }
        QueryWrapper<TmgErrmsgDO> teDo = new QueryWrapper<TmgErrmsgDO>();
        teDo.eq("TER_CMODIFIERPROGRAMID", BEAN_DESC + "_" + sAction);
        teDo.eq("TER_CMODIFIERUSERID", param.getUserCode());
        return iTmgErrmsgService.getBaseMapper().selectOne(teDo).getTerCerrcode();
    }

    /**
     * トリガーを削除する
     * buildSQLForDeleteTrigger
     *
     * @return String SQL文
     */
    private int deleteTrigger() {
        QueryWrapper<TmgTriggerDO> ttDo = new QueryWrapper<TmgTriggerDO>();
        ttDo.eq("TTR_CMODIFIERPROGRAMID", BEAN_DESC + "_" + param.getAction());
        ttDo.eq("TTR_CMODIFIERUSERID", param.getUserCode());
        return iTmgTriggerService.getBaseMapper().delete(ttDo);
    }

    /**
     * トリガー用テーブルへInsertする
     * buildSQLForInsertTmgTrigger
     *
     * @return String SQL文
     */
    private int insertTrigger() {
        String paramNftNo;
        //アクション識別子の取得
        String sAction = param.getAction();
        // 起動時
        if (sAction == null) {
            if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)) {
                sAction = ACT_DISPINP_RLIST;    // 一覧表示(本人)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            }
        }

        if (isPartOfReserveApplication()) {
            paramNftNo = "NULL";
        } else {
            paramNftNo = param.getNtfNo();
        }

        TmgTriggerDO tmgTriggerDO = new TmgTriggerDO();
        tmgTriggerDO.setTtrCcustomerid(param.getCustId());
        tmgTriggerDO.setTtrCcompanyid(param.getCompId());
        tmgTriggerDO.setTtrCemployeeid(param.getTargetUser());
        tmgTriggerDO.setTtrDstartdate(TmgUtil.minDate);
        tmgTriggerDO.setTtrDenddate(TmgUtil.maxDate);

        tmgTriggerDO.setTtrCmodifieruserid(param.getUserCode());
        tmgTriggerDO.setTtrDmodifieddate(DateTime.now());
        tmgTriggerDO.setTtrCmodifierprogramid(BEAN_DESC + DATA_SEPARATOR01 + sAction);
        tmgTriggerDO.setTtrCprogramid(BEAN_DESC + DATA_SEPARATOR01 + sAction);
        tmgTriggerDO.setTtrCparameter1(sAction);
        tmgTriggerDO.setTtrCparameter2(paramNftNo);

        return iTmgTriggerService.getBaseMapper().insert(tmgTriggerDO);
    }

    /**
     * 長期間有効な申請の部分解除か判定し真偽を返します。
     *
     * @return 長期間有効な申請の部分解除であればtrue、それ以外はfalse
     */
    private boolean isPartOfReserveApplication() {
        if (ACT_EDITCANCEL_UCANCEL.equals(param.getAction())
                && param.getTxtDCancelEnd() != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * マスタを取得するSQLを返す
     * <p>
     * グループIDが「申請種類」かつ、アクションが「新規申請表示」か「代理申請表示」の場合は、
     * 抽出条件として検索対象職員の職種/申請リレーションを指定する。
     * 「職種/申請リレーション」は職種別に申請可能な「申請区分」を定義したマスタです。
     * </p>
     *
     * @param sGroupID :グループID
     * @param sOrder   :ソート順
     * @param sColum   :取得項目
     * @return String          :SQL
     */
    private String buildSQLForSelectGenericDetail(String sGroupID, String sOrder, String sColum) {

        StringBuffer sSQL = new StringBuffer();
        sSQL.append(" SELECT  ");
        if (sColum == null) {
            sSQL.append("     D1.MGD_CMASTERCODE, ");
            sSQL.append("     D1.MGD_CGENERICDETAILDESC, ");
            sSQL.append("     D1.MGD_NSPARENUM2, ");
            sSQL.append("     D1.MGD_CSPARECHAR3 ");
        } else {
            sSQL.append(sColum);
        }
        sSQL.append(" FROM  ");
        sSQL.append("     MAST_GENERIC_DETAIL D1");
        sSQL.append(" WHERE  ");
        sSQL.append("     D1.MGD_CCUSTOMERID          = '" + param.getCustId() + "'");
        sSQL.append(" AND D1.MGD_CCOMPANYID_CK_FK     = '" + param.getCompId() + "'");
        sSQL.append(" AND D1.MGD_CLANGUAGE_CK         = '" + param.getLang() + "'");
        sSQL.append(" AND D1.MGD_CGENERICGROUPID      = '" + sGroupID + "'");
        sSQL.append(" AND D1.MGD_DSTART_CK           <= TO_DATE('" + param.getToday() + "', '" + DATE_FORMAT + "') ");
        sSQL.append(" AND D1.MGD_DEND                >= TO_DATE('" + param.getToday() + "', '" + DATE_FORMAT + "') ");

        String sAction = "";//getReqParm("txtAction");
        if (sGroupID.equals(TmgUtil.Cs_MGD_NTFTYPE) &&
                (ACT_MAKEAPPLY_RDETAIL.equals(sAction) || ACT_ALTERAPPLY_RDETAIL.equals(sAction) || ACT_DISPDETAIL_RDETAIL.equals(sAction)) || ACT_ALTERLISTAPPLY_CAPPLY.equals(sAction)) {
            sSQL.append(" AND MGD_CMASTERCODE IN ( ");
            sSQL.append("         SELECT ");
            sSQL.append("             D2.MGD_CSPARECHAR2 ");
            sSQL.append("         FROM ");
            sSQL.append("             MAST_GENERIC_DETAIL D2");
            sSQL.append("         WHERE ");
            sSQL.append("             D2.MGD_CCUSTOMERID          = '" + param.getCustId() + "'");
            sSQL.append("         AND D2.MGD_CCOMPANYID_CK_FK     = '" + param.getCompId() + "'");
            sSQL.append("         AND D2.MGD_CGENERICGROUPID      = '" + TmgUtil.Cs_MGD_NTF_RELATION + "'");
            sSQL.append("         AND D2.MGD_CLANGUAGE_CK         = '" + param.getLang() + "'");
            sSQL.append("         AND D2.MGD_DSTART_CK           <= TO_DATE('" + param.getToday() + "', '" + DATE_FORMAT + "') ");
            sSQL.append("         AND D2.MGD_DEND                >= TO_DATE('" + param.getToday() + "', '" + DATE_FORMAT + "') ");
            sSQL.append("         AND D2.MGD_CSPARECHAR1 = TMG_F_GET_WORKERTYPE( ");
            sSQL.append("                                 '" + param.getTargetUser() + "', TO_DATE('" + param.getToday() + "', '" + DATE_FORMAT + "'), '"
                    + param.getCustId() + "' , '" + param.getCompId() + "'");
            sSQL.append("                             )  "); //-- 対象職員の勤怠職種
            sSQL.append("         AND D2.MGD_NSPARENUM1 = " + FLG_NTF_RELATION_ENABLE); //-- 職種/申請リレーションフラグ
            sSQL.append(" ) ");
        }
        // 現在のサイトで申請可能な休暇を取得する(一覧は除く)
        if (sGroupID.equals(TmgUtil.Cs_MGD_NTFTYPE)
                && sAction != null
                && !sAction.equals(ACT_DISPINP_RLIST)
        ) {
            sSQL.append(" AND instr(D1.MGD_CSPARECHAR5,'" + param.getSiteId() + "') > 0 ");
        }
        if (sOrder != null) {
            sSQL.append(" ORDER BY  ");
            sSQL.append("     D1.MGD_CGENERICDETAILID_CK " + sOrder);
        }

        return new String(sSQL);
    }

    /**
     * マスタを取得するSQLを返す
     * <p>
     * グループIDが「申請種類」かつ、アクションが「新規申請表示」か「代理申請表示」の場合は、
     * 抽出条件として検索対象職員の職種/申請リレーションを指定する。
     * 「職種/申請リレーション」は職種別に申請可能な「申請区分」を定義したマスタです。
     * </p>
     *
     * @param sGroupID :グループID
     * @param sOrder   :ソート順
     * @return String          :SQL
     */
    private String buildSQLForSelectGenericDetail(String sGroupID, String sOrder) {
        StringBuffer sSQL = new StringBuffer();
        sSQL.append(buildSQLForSelectGenericDetail(sGroupID, sOrder, null));
        return new String(sSQL);
    }


    /**
     * 決裁レベル取得SQL返却メソッド
     * getApprovelLevelSQL
     *
     * @param piMode 0:次の決裁レベル取得用、1:一番小さい決裁レベル取得用
     * @return String SQL文
     */
    private String getApprovelLevel(int piMode) {

        return iMastGenericDetailService.selectApprovelLevel(param.getCustId(), param.getCompId(), param.getLang(), param.getToday(), param.getApprovalLevel(), piMode);

    }

    /**
     * 決裁レベルチェック
     *
     * @param psFinalApprovalLevel
     * @param psApprovalLevel
     * @return boolean true/false
     */
    public boolean hasCheckApprovelLevel(String psFinalApprovalLevel, String psApprovalLevel, String siteId) {

        if (siteId.equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
            return true;
        }

        if (Integer.valueOf(psFinalApprovalLevel).intValue() <= Integer.valueOf(psApprovalLevel).intValue()) {
            return true;
        } else {
            return false;
        }
    }

}
