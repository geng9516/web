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
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.UpdateAcquired5DaysVO;
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

    /**
     * TmgReferList
     */
    private TmgReferList referList = null;

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
     * 日付フォーマット
     */
    public static final String DATE_FORMAT = "yyyy/MM/dd";

    /**
     * 一覧の検索
     *
     * @param userCode 　対象者
     * @param psDBBean 　PsDBBean
     * @return
     */
    public List<Acquired5DaysListVO> selectList(String userCode, PsDBBean psDBBean) {

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
        List<PaidHolidayVO> paidHolidayVOList = iTmgDailyService.buildSQLForSelectPaidHoliday(psDBBean.getCustID(),
                psDBBean.getCompCode(), dispUserCode, searchStart, searchEnd);

        return paidHolidayVOList;

    }

    /**
     * 画面表示項目を取り出す
     *
     * @throws Exception
     */
    public void update(PsDBBean psDBBean, UpdateAcquired5DaysVO updateAcquired5DaysVO) {

        iTmgPaiduseinfoFixService.getBaseMapper().delete(SysUtil.<TmgPaiduseinfoFixDO>query()
                .eq("TPF_CCUSTOMERID", psDBBean.getCustID())
                .eq("TPF_CCOMPANYID", psDBBean.getCompCode())
                .eq("TPF_CEMPLOYEEID", updateAcquired5DaysVO.getTxtUserCode())
                .eq("TPF_NYYYY", updateAcquired5DaysVO.getTxtYear()));


        String updateFlag = updateAcquired5DaysVO.getTxtUpdateflg();

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
            tmgPaiduseinfoFixDO.setTpfCemployeeid(updateAcquired5DaysVO.getTxtUserCode());
            tmgPaiduseinfoFixDO.setTpfDpaidHoliday(DateUtil.parse(updateAcquired5DaysVO.getKijunbi()));
            tmgPaiduseinfoFixDO.setTpfNyyyy(Long.parseLong(updateAcquired5DaysVO.getTxtYear()));
            tmgPaiduseinfoFixDO.setTpfDpaidHolidayFix(DateUtil.parse(updateAcquired5DaysVO.getKijunbiEdit()));
            tmgPaiduseinfoFixDO.setTpfDkikanbiFix(null);
            tmgPaiduseinfoFixDO.setTpfNusedaysFix(null);
            tmgPaiduseinfoFixDO.setTpfNmustdaysFix(null);
            tmgPaiduseinfoFixDO.setTpfNusedaysAjdust(Long.parseLong(updateAcquired5DaysVO.getUsedDaysEdit()));
            tmgPaiduseinfoFixDO.setTpfNusehoursAjdust(null);
            iTmgPaiduseinfoFixService.getBaseMapper().insert(tmgPaiduseinfoFixDO);
        }

        // クリアボタンを押下時、もしくは 修正基準日が空白の場合、プロシージャは実行しない
        if ("1".equals(updateFlag) && !StrUtil.isBlank(updateAcquired5DaysVO.getKijunbiEdit())) {
            // プロシージャ実行SQLを作成する
            iTmgAcquired5daysholidayService.buildSQLTmgAcquired5daykikanbi(
                    updateAcquired5DaysVO.getTxtUserCode(),
                    updateAcquired5DaysVO.getKijunbi(),
                    updateAcquired5DaysVO.getTxtYear(),
                    updateAcquired5DaysVO.getKijunbiEdit(),
                    psDBBean.getUserCode(),
                    PROGRAM_ID,
                    psDBBean.getCustID(),
                    psDBBean.getCompCode()
            );
        }
    }

    public String baseDate = null;


    /**
     * メインメソッド
     */
    public void execute(PsDBBean psDBBean, String recordDate, String txtYear) throws Exception {

        // 今年度
        int year = iMastGenericDetailService.selectYear(psDBBean.getCustID(), psDBBean.getCompCode());

        this.setThisYear(year);

        // 年度
        try {
            this.setYear(Integer.parseInt(txtYear));
        } catch (Exception e) {
            // 取得出来なかったらDBより取得
            setYear(getThisYear());
        }

        baseDate = String.valueOf(this.getYear()) + recordDate.substring(4);


        referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);

    }


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
    private boolean gbPreviousYear = false; // 前年度ボタン
    private boolean gbNextYear = false; // 翌年度ボタン

    public void setPreviousYear(boolean previousYear) {
        gbPreviousYear = previousYear;
    }

    public boolean isNextYear() {
        return gbNextYear;
    }

    public void setNextYear(boolean nextYear) {
        gbNextYear = nextYear;
    }



    /**
     * 初期処理判定フラグ
     */
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

}
