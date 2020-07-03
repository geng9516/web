package jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPaiduseinfoFixDO;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.Acquired5DaysListVO;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.PaidHolidayVO;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.CalendarDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.DateDto;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

/**
 * 年5日時季指定取得確認
 * ps.c01.tmg.TmgAcquired5DaysHoliday.TmgAcquired5DaysHolidayBean
 *
 * @author Nie Wanqun
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgAcquired5DaysHolidayBean {

    /**
     * IMastGenericDetailService
     */
    public final IMastGenericDetailService iMastGenericDetailService;
    /**
     * ITmgDailyService
     */
    private final ITmgDailyService iTmgDailyService;
    /**
     * ITmgCalendarService
     */
    private final ITmgCalendarService iTmgCalendarService;

    /**
     * ITmgAcquired5daysholidayService
     */
    private final ITmgAcquired5daysholidayService iTmgAcquired5daysholidayService;
    /**
     * ITmgPaiduseinfoFixService
     */
    private final ITmgPaiduseinfoFixService iTmgPaiduseinfoFixService;


    private TmgReferList referList = null;


    /**
     * 承認サイト・管理サイト
     * 年5日時季指定取得確認一覧画面
     * <p>
     * ACT_DISP
     */
    public void actDisp(PsDBBean psDBBean) throws Exception {

        selectList(null, psDBBean);
    }


    /**
     * 承認サイト・管理サイト
     * 年5日時季指定取得編集画面
     * <p>
     * ACT_EDIT
     */
    public void actEdit(String year, String userCode, PsDBBean psDBBean) {

        selectList(userCode, psDBBean);
    }

    /**
     * 承認サイト・管理サイト
     * 詳細画面
     * <p>
     * ACT_DETAIL
     */
    public List<PaidHolidayVO> actDetail(String txtUserCode, String kijunbi, String pdSearchStart, String pdSearchEnd, PsDBBean psDBBean) {
        //転送項目取得
        return showDisp(txtUserCode, kijunbi, pdSearchStart, pdSearchEnd, psDBBean);
    }

    /**
     * 承認サイト・管理サイト
     * 編集画面の登録ボタン
     * <p>
     * ACT_UPDTE
     */
    public void actUpdte(String year, String userCode, PsDBBean psDBBean) {

        // 編集内容を更新する
        update(psDBBean);
        // 転送項目の取得
        selectList(userCode, psDBBean);
    }

    /**
     * 一覧の検索
     *
     * @param userCode 　対象者
     * @param psDBBean 　PsDBBean
     * @return
     */
    public List<Acquired5DaysListVO> selectList(String userCode, PsDBBean psDBBean) {
//        String baseDate = String.valueOf(this.getYear()) + referList.getRecordDate().substring(4);
        String empsql = referList.buildSQLForSelectEmployees();

        List<Acquired5DaysListVO> acquired5DaysVOList = iTmgAcquired5daysholidayService.buildSQLforList(baseDate, empsql, userCode);

        return acquired5DaysVOList;
    }

    /**
     * 画面表示項目を取り出す
     *
     * @param txtUserCode   対象者
     * @param kijunbi       　基準日
     * @param pdSearchStart 　年休調査期間（開始日）
     * @param pdSearchEnd   　年休調査期間（終了日）
     * @param psDBBean      　PsDBBean
     */
    public List<PaidHolidayVO> showDisp(String txtUserCode, String kijunbi, String pdSearchStart, String pdSearchEnd, PsDBBean psDBBean) {
        String dispUserCode;
        if (txtUserCode != null && !txtUserCode.equals("")) {
            dispUserCode = txtUserCode;

        } else {
            dispUserCode = referList.getTargetEmployee();
        }

        // 年休調査期間設定
        String searchStart = pdSearchStart;
        String searchEnd = pdSearchEnd;
        if (StrUtil.isBlank(searchStart) || "null".equals(searchStart)) {
            searchStart = kijunbi;
        }
        if (StrUtil.isBlank(searchEnd) || "null".equals(searchEnd)) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            searchEnd = sdf.format(new Date());
        }
        // 詳細画面検索用SQLを作成
        List<PaidHolidayVO> paidHolidayVOList = iTmgDailyService.buildSQLForSelectPaidHoliday(psDBBean.getCustID(), psDBBean.getCompCode(), dispUserCode, searchStart, searchEnd);

        return paidHolidayVOList;

    }

    /**
     * システムの最小日付
     */
    private final String MIN_DATE = "1900/01/01";
    /**
     * システムの最大日付
     */
    private final String MAX_DATE = "2222/12/31";
    /**
     * プログラムID
     */
    private static final String PROGRAM_ID = "TmgAcquired5DaysHoliday";

    /**
     * 画面表示項目を取り出す
     *
     * @throws Exception
     */
    public void update(PsDBBean psDBBean) {

        iTmgPaiduseinfoFixService.getBaseMapper().delete(SysUtil.<TmgPaiduseinfoFixDO>query()
                .eq("TPF_CCUSTOMERID", psDBBean.getCustID())
                .eq("TPF_CCOMPANYID", psDBBean.getCompCode())
                .eq("TPF_CEMPLOYEEID", psDBBean.getReqParam("txtUserCode"))
                .eq("TPF_NYYYY", psDBBean.getReqParam("txtYear")));


        String updateFlag = psDBBean.getReqParam("txtUpdateflg");

        // クリアボタンを押下時に、編集データを保存しない
        if ("1".equals(updateFlag)) {
            // 作成SQLを作成する
            TmgPaiduseinfoFixDO tmgPaiduseinfoFixDO = new TmgPaiduseinfoFixDO();

            tmgPaiduseinfoFixDO.setTpfCcustomerid(psDBBean.getCustID());
            tmgPaiduseinfoFixDO.setTpfCcompanyid(psDBBean.getCompCode());
            tmgPaiduseinfoFixDO.setTpfDstartdate(DateUtil.parse(MIN_DATE));
            tmgPaiduseinfoFixDO.setTpfDenddate(DateUtil.parse(MAX_DATE));
            tmgPaiduseinfoFixDO.setTpfCmodifieruserid(psDBBean.getUserCode());
            tmgPaiduseinfoFixDO.setTpfDmodifierdate(DateTime.now());
            tmgPaiduseinfoFixDO.setTpfCmodifierprogramid(PROGRAM_ID);
            tmgPaiduseinfoFixDO.setTpfCemployeeid(psDBBean.getReqParam("txtUserCode"));
            tmgPaiduseinfoFixDO.setTpfDpaidHoliday(DateUtil.parse(psDBBean.getReqParam("kijunbi")));
            tmgPaiduseinfoFixDO.setTpfNyyyy(Long.parseLong(psDBBean.getReqParam("txtYear")));
            tmgPaiduseinfoFixDO.setTpfDpaidHolidayFix(DateUtil.parse(psDBBean.getReqParam("kijunbiEdit")));
            tmgPaiduseinfoFixDO.setTpfDkikanbiFix(null);
            tmgPaiduseinfoFixDO.setTpfNusedaysFix(null);
            tmgPaiduseinfoFixDO.setTpfNmustdaysFix(null);
            tmgPaiduseinfoFixDO.setTpfNusedaysAjdust(Long.parseLong(psDBBean.getReqParam("usedDaysEdit")));
            tmgPaiduseinfoFixDO.setTpfNusehoursAjdust(null);
            iTmgPaiduseinfoFixService.getBaseMapper().insert(tmgPaiduseinfoFixDO);
        }

        // クリアボタンを押下時、もしくは 修正基準日が空白の場合、プロシージャは実行しない
        if ("1".equals(updateFlag) && !StrUtil.isBlank(psDBBean.getReqParam("kijunbiEdit"))) {
            // プロシージャ実行SQLを作成する
            iTmgAcquired5daysholidayService.buildSQLTmgAcquired5daykikanbi(
                    psDBBean.getReqParam("txtUserCode"),
                    psDBBean.getReqParam("kijunbi"),
                    psDBBean.getReqParam("txtYear"),
                    psDBBean.getReqParam("kijunbiEdit"),
                    psDBBean.getUserCode(),
                    PROGRAM_ID,
                    psDBBean.getCustID(),
                    psDBBean.getCompCode()
            );
        }
    }

    public String baseDate = null;

    private String getSysdate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(new Date());
    }

//    public void setReferList(int pnTreeViewType, PsDBBean psDBBean) {
//        try {
//            referList = new TmgReferList(psDBBean, "TmgSample", baseDate, pnTreeViewType, true,
//                    true, false, false, true
//            );
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    /**
//     * 表示対象者の職員番号格納処理
//     *
//     * @param psSite サイトID
//     *               psUserCode ログインユーザの職員番号
//     *               psEmp 検索条件の職員番号
//     *               psTargetEmpId 申請者の職員番号
//     * @return なし
//     */
//    private void setTargetUser(String psSite, String psUserCode, PsDBBean psDBBean, String txtYear) {
//
//        // 汎用参照リスト
//        setReferList(TmgReferList.TREEVIEW_TYPE_LIST_SEC, psDBBean);
//
//        psDBBean.setTargetUser(referList.getTargetEmployee());
//        // 組織ツリー情報でパラメータの再構築を行う
//        getParam(1, psDBBean, txtYear);
//
//    }

    public TmgReferList getReferList() {
        return referList;
    }

    /**
     * 参照権限：参照可能
     */
    private static final boolean CB_CAN_REFER = true;
    /**
     * 参照権限：参照不可能
     */
    private static final boolean CB_CANT_REFER = false;

    /**
     * 勤怠シートの参照権限(基準日の翌月)
     */
    boolean _authorityNextYear = false;

    /**
     * 勤怠シートの参照権限(基準日の翌年)設定メソッド
     */
    public void setAuthorityNextYear(boolean bValue) {
        _authorityNextYear = bValue;
    }

    /**
     * 勤怠シートの参照権限(基準日の翌年)取得メソッド
     */
    public boolean getAuthorityNextYear() {
        return _authorityNextYear;
    }

    /**
     * メインメソッド
     */
    public void execute(PsDBBean psDBBean, String txtYear) throws Exception {

        // 今年度
        int year = iMastGenericDetailService.selectYear(psDBBean.getCustID(), psDBBean.getCompCode());

        setThisYear(year);

        // 年度
        try {
            setYear(Integer.parseInt(txtYear));
        } catch (Exception e) {
            // 取得出来なかったらDBより取得
            setYear(getThisYear());
        }

        // 組織ツリーの基準日の設定ここから
        String sBaseDate = getReferList().getRecordDate();

        baseDate = String.valueOf(this.getYear()) + sBaseDate.substring(4);

        referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);

//
//        // 一覧画面 → ツリータイプ：組織単位
//        setReferList(TmgReferList.TREEVIEW_TYPE_LIST_SEC, psDBBean);



//        // パラメータ
//        getParam(0, psDBBean, txtYear);

//        // 表示対象日付をセット
//        try {
//            if (psDBBean.getReqParam("txtDATE") == null || psDBBean.getReqParam("txtDATE").equals("") == true) {
//                baseDate = getSysdate();
//            } else {
//                baseDate = psDBBean.getReqParam("txtDATE");
//            }
//        } catch (Exception e) {
//            baseDate = getSysdate();
//        }
//
//        // 表示対象者の職員番号格納処理
//        setTargetUser(psDBBean.getSiteId(), psDBBean.getUserCode(), psDBBean, txtYear);


//        // カレンダー関連
//        getCalender(sBaseDate, psDBBean);

//        // 勤怠承認サイト、もしくは勤怠管理サイトの場合に以下の処理を実行する
//        if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) || TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId())) {
//
//            String sAction = psDBBean.getReqParam("txtAction");
//            String sTargetSec = referList.getTargetSec();
//            String sStartDateSysdate = TmgUtil.getSysdate();
//
//            // 勤怠承認サイトは初期表示時、勤怠管理サイトは初期表示+(組織選択時or組織選択済)の場合
//            // ※勤怠管理サイトの場合、初期表示時でも組織が選択されていない状態なら権限チェックを行わない
//            if ((TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) && (sAction == null || sAction.length() == 0))
//                    || (TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId()) && !(sTargetSec == null || sTargetSec.length() == 0) && (sAction == null || sAction.length() == 0))) {
//
//                // 参照権限チェック(現在時点での年度)
//                if (referList.existsAnyone(sStartDateSysdate) && referList.isThereSomeEmployees(sStartDateSysdate)) {
//                    //setAuthorityYear(CB_CAN_REFER);
//                    setAuthorityNextYear(CB_CAN_REFER);
//                    // 参照権限が無い場合
//                } else {
//                    //setAuthorityYear(CB_CANT_REFER);
//                    setAuthorityNextYear(CB_CANT_REFER);
//                }
//                // 初期表示時以外
//            } else {
//                // 組織未選択時は権限チェックを行わない
//                if (!(sTargetSec == null || sTargetSec.length() == 0)) {
//                    if ((referList.existsAnyone(sStartDateSysdate) && referList.isThereSomeEmployees(sStartDateSysdate))) {
//                        //setAuthorityYear(CB_CAN_REFER);
//                        setAuthorityNextYear(CB_CAN_REFER);
//                    } else {
//                        //setAuthorityYear(CB_CANT_REFER);
//                        setAuthorityNextYear(CB_CANT_REFER);
//                    }
//
//                }
//            }
//        }
//        // 一覧画面 → ツリータイプ：組織単位
//        setReferList(TmgReferList.TREEVIEW_TYPE_LIST_SEC, psDBBean);

    }

    /**
     * 日付フォーマット
     */
    public static final String DATE_FORMAT = "yyyy/MM/dd";

    /**
     * カレンダー関連情報を取得するメソッド
     *
     * @return int    年度
     */
    private void getCalender(String psDate, PsDBBean psDBBean) {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        // 検索
        // 年度開始・終了日
        DateDto dateDto = iMastGenericDetailService.selectDate(psDBBean.getCustID(), psDBBean.getCompCode(), getYear(), psDate);
        // 前翌年度有無判定
        CalendarDto calendarDto = iTmgCalendarService.selectCalendar(psDBBean.getCustID(), psDBBean.getCompCode(), getYear(), psDate);

        try {

            // 年度開始日・終了日
            setStartDate(dateDto.getStartDate());
            setEndDate(dateDto.getEndDate());
            setToday(dateDto.getBaseDate());

            // 前翌年度
            Date dMin = sdf.parse(calendarDto.getMinMonth());
            Date dMax = sdf.parse(calendarDto.getMaxMonth());
            Date dStart = sdf.parse(calendarDto.getStartYearDate());
            Date dEnd = sdf.parse(calendarDto.getEndYearDate());

            if (dMin.before(dStart) && this.getYear() > START_YEAR) {
                setPreviousYear(true);
            }
            if (dMax.after(dEnd)) {
                setNextYear(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String gsStartDate = null;  // 年度開始日
    private String gsEndDate = null;  // 年度終了日
    private String gsToday = null;  // 今日

    public String getStartDate() {
        return gsStartDate;
    }

    public void setStartDate(String startDate) {
        gsStartDate = startDate;
    }

    public String getEndDate() {
        return gsEndDate;
    }

    public void setEndDate(String endDate) {
        gsEndDate = endDate;
    }

    public String getToday() {
        return gsToday;
    }

    public void setToday(String today) {
        gsToday = today;
    }

    /**
     * 開始年度
     */
    public static final int START_YEAR = 2019;

    public boolean isPreviousYear() {
        return gbPreviousYear;
    }

    public void setPreviousYear(boolean previousYear) {
        gbPreviousYear = previousYear;
    }

    public boolean isNextYear() {
        return gbNextYear;
    }

    public void setNextYear(boolean nextYear) {
        gbNextYear = nextYear;
    }

    private boolean gbPreviousYear = false; // 前年度ボタン
    private boolean gbNextYear = false; // 翌年度ボタン

    /**
     * 初期処理判定フラグ
     */
    private static final int INITIAL_TREATMENT = 0;
    private int giThisYear = 0;     // 今年度

    public int getThisYear() {
        return giThisYear;
    }

    public void setThisYear(int thisYear) {
        giThisYear = thisYear;
    }

    // 変数
    private int giYear = 0;     // 年度

    public int getYear() {
        return giYear;
    }

    public void setYear(int year) {
        giYear = year;
    }
//
//    /**
//     * パラメータを取得するメソッド
//     *
//     * @param piPBranchingProcess 0:初期処理、1:組織ツリー情報使用処理
//     * @return なし
//     */
//    private void getParam(int piPBranchingProcess, PsDBBean psDBBean, String txtYear) {
//
//        // 初期処理
//        if (piPBranchingProcess == INITIAL_TREATMENT) {
//            // 今年度
//            int year = iMastGenericDetailService.selectYear(psDBBean.getCustID(), psDBBean.getCompCode());
//            setThisYear(year);
//            // 年度
//            try {
//                setYear(Integer.parseInt(txtYear));
//            } catch (Exception e) {
//                // 取得出来なかったらDBより取得
//                setYear(getThisYear());
//            }
//        }
//        // 組織ツリー情報取得後再構築を行う
//        else {
//            // 組織ツリー基準日情報チェック
//            if (referList.getRecordDate() != null) {
//                setThisYear(Integer.parseInt(referList.getRecordDate().substring(0, 4)));
//            }
//        }
//    }

}
