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
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

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
     * PsDBBean
     */
    private PsDBBean psDBBean;

    /**
     * ITmgDailyService
     */
    private final ITmgDailyService iTmgDailyService;

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
    public void actDisp(ModelMap modelMap) throws Exception {

        selectList(modelMap, null);
    }


    /**
     * 承認サイト・管理サイト
     * 年5日時季指定取得編集画面
     * <p>
     * ACT_EDIT
     */
    public void actEdit(ModelMap modelMap) {
        String userCode = psDBBean.getReqParam("txtUserCode");
        selectList(modelMap, userCode);
    }

    /**
     * 承認サイト・管理サイト
     * 詳細画面
     * <p>
     * ACT_DETAIL
     */
    public void actDetail(ModelMap modelMap) {
        //転送項目取得
        showDisp(modelMap);
    }

    /**
     * 承認サイト・管理サイト
     * 編集画面の登録ボタン
     * <p>
     * ACT_UPDTE
     */
    public void actUpdte(ModelMap modelMap) {

        // 編集内容を更新する
        update();
        // 転送項目の取得
        selectList(modelMap, null);
    }

    /**
     * 一覧の検索
     */
    private void selectList(ModelMap modelMap, String userCode) {
        String baseDate = psDBBean.getReqParam("txtYear") + referList.getRecordDate().substring(4);
        String empsql = referList.buildSQLForSelectEmployees();

        List<Acquired5DaysListVO> acquired5DaysVOList = iTmgAcquired5daysholidayService.buildSQLforList(baseDate, empsql, userCode);
        modelMap.addAttribute("acquired5DaysVOList", acquired5DaysVOList);
    }

    /**
     * 画面表示項目を取り出す
     *
     * @throws Exception
     */
    private void showDisp(ModelMap modelMap) {
        String dispUserCode;
        if (psDBBean.getReqParam("txtUserCode") != null && psDBBean.getReqParam("txtUserCode").equals("") == false) {
            dispUserCode = psDBBean.getReqParam("txtUserCode");

        } else {
            dispUserCode = referList.getTargetEmployee();
        }

        // 一覧画面転送データをセット
        String kijunbi = psDBBean.getReqParam("kijunbi");
        String syuRyobi = psDBBean.getReqParam("syuRyobi");
        String useddays = psDBBean.getReqParam("useddays");

        String searchStart = psDBBean.getReqParam("pdSearchStart");
        String searchEnd = psDBBean.getReqParam("pdSearchEnd");
        if (StrUtil.isBlank(searchStart) || "null".equals(searchStart)) {
            searchStart = kijunbi;
        }
        if (StrUtil.isBlank(searchEnd) || "null".equals(searchEnd)) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            searchEnd = sdf.format(new Date());
        }
        // 詳細画面検索用SQLを作成
        List<PaidHolidayVO> paidHolidayVOList = iTmgDailyService.buildSQLForSelectPaidHoliday(psDBBean.getCustID(), psDBBean.getCompCode(), dispUserCode, searchStart, searchEnd);

        modelMap.addAttribute("paidHolidayVOList", paidHolidayVOList);
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
    private void update() {

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

}
