package jp.smartcompany.job.modules.tmg.paidholiday;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.service.ITmgPaidHolidayService;
import jp.smartcompany.job.modules.core.service.ITmgTriggerService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgPaidHolidayDto;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayDispVO;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayUpdateVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 年次休假管理处理bean -> 对应旧就业的ps.c01.tmg.PaidHoliday.PaidHolidayBean
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaidholidayBean {


    private final IMastEmployeesService iMastEmployeesService;

    private final ITmgPaidHolidayService iTmgPaidHolidayService;

    private final ITmgTriggerService iTmgTriggerService;

    /**
     * 一覧の検索
     */
    public List<PaidHolidayInitVO> actionInitHandler(String empSql) {
        return iMastEmployeesService.listPaidHolidayInit(empSql);
}

    /**
     * 年次休暇付与状況表示
     * ACT_DISP_RLIST
     */
    public void actDispRlist(PsDBBean psDBBean){
        //転送項目取得
       // showDisp(psDBBean);

    }
    /**
     * 登録ボタン
     * ACT_UADJUST
     */
    public void actUadjust(PsDBBean psDBBean, String empSql) {
//        //更新処理をする
//        execUpdate(psDBBean);
        //転送項目の取得
        List<PaidHolidayInitVO> listPaidHolidayInit = iMastEmployeesService.listPaidHolidayInit(empSql);
       // modelMap.addAttribute("listPaidHolidayInit",listPaidHolidayInit);
    }
    /** 更新プログラムID */
    private static final String MOD_PROGRAM_ID = "PaidHoliday_ACT_UADJUST";
    /** プログラムID */
    private static final String PROGRAM_ID	  = "PaidHoliday";
    /**
     * 更新処理をする
     */
    public void execUpdate(PsDBBean psDBBean, PaidHolidayUpdateVO paidHolidayUpdateVO, String txtDate){
        baseDate = txtDate;

        TmgPaidHolidayDto tmgPaidHolidayDto= new TmgPaidHolidayDto();
        tmgPaidHolidayDto.setTphCmodifieruserid(psDBBean.getUserCode());
        tmgPaidHolidayDto.setTphCmodifierprogramid(MOD_PROGRAM_ID);
        //txtTPH_NINVEST
        tmgPaidHolidayDto.setTphNinvest(Double.valueOf(paidHolidayUpdateVO.getNinvest()));
        //txtTPH_NADJUST
        tmgPaidHolidayDto.setTphNadjust(Double.valueOf(paidHolidayUpdateVO.getNadjust()));
        //txtTPH_NADJUST_HOURS
        tmgPaidHolidayDto.setTphNadjustHours(Double.valueOf(paidHolidayUpdateVO.getNadjustHours())) ;
        // txtTPH_NADJUST_TO
        tmgPaidHolidayDto.setTphNadjustTo(Double.valueOf(paidHolidayUpdateVO.getNadjustTo()));
        // txtTPH_NADJUST_HOURS_TO
        tmgPaidHolidayDto.setTphNadjustHoursTo(Double.valueOf(paidHolidayUpdateVO.getNadjustHoursTo()));
        //txtTPH_DEXPIRE_ADJUST
        if ( tmgPaidHolidayDto.getTphNadjust()==0.0 && tmgPaidHolidayDto.getTphNadjustHours()==0.0){
        }else{
            tmgPaidHolidayDto.setTphDexpireAdjust(DateUtil.parse(paidHolidayUpdateVO.getDexpireAdjust()));
        }
        //txtTPH_DEXPIRE_ADJUST_TO
        if ( tmgPaidHolidayDto.getTphNadjustTo()==0.0 && tmgPaidHolidayDto.getTphNadjustHoursTo()==0.0){
        }else{
            tmgPaidHolidayDto.setTphDexpireAdjustTo(DateUtil.parse(paidHolidayUpdateVO.getDexpireAdjustTo()));
        }
        // txtTPH_CCOMMENT
        tmgPaidHolidayDto.setTphCcomment(paidHolidayUpdateVO.getCcomment());

        tmgPaidHolidayDto.setTphCemployeeid(paidHolidayUpdateVO.getCemployeeid()) ;
        tmgPaidHolidayDto.setTphDyyyymmdd(DateUtil.parse(paidHolidayUpdateVO.getDyyyymmdd())) ;
        tmgPaidHolidayDto.setTphCcompanyid(psDBBean.getCompCode());
        tmgPaidHolidayDto.setTphCcustomerid(psDBBean.getCustID());

        // [勤怠]年次休暇情報を更新する
        iTmgPaidHolidayService.buildSQLForUpdatePaidHolyday(tmgPaidHolidayDto);
        // トリガーテーブルにデータを入れる
        iTmgTriggerService.buildSQLForInsertTriggerTable(psDBBean.getCustID(), psDBBean.getCompCode(), getDispUserCode(), psDBBean.getUserCode(), baseDate);
        // トリガーテーブルのデータを削除する
        iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID", psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode())
                .eq("TTR_CEMPLOYEEID", getDispUserCode())
                .eq("TTR_CPROGRAMID", PROGRAM_ID)
                .eq("TTR_CMODIFIERUSERID", psDBBean.getUserCode()));

    }

    public String baseDate = null;
    private String sDispUser ="";

    /**
     * 検索する職員番号をセットする
     * @param sValue 職員番号
     */
    private void setDispUserCode(String sValue){
        this.sDispUser= sValue;
    }

    /**
     * 検索する職員番号を返す
     * @return 職員番号
     */

    public String getDispUserCode(){
        return this.sDispUser;
    }

    private TmgReferList referList = null;

    public TmgReferList getReferList(){
        return referList;
    }

    /**
     * 画面表示項目を取り出す
     *
     * @throws Exception
     */
    public PaidHolidayDispVO showDispDetail(PsDBBean psDBBean, String txtUserCode, String txtDate) {

        baseDate = txtDate;
        setDispUserCode(txtUserCode);

        // 年次休暇付与状況
        PaidHolidayDispVO paidHolidayDispVO = iTmgPaidHolidayService.buildSQLForSelectPaidHoliday(psDBBean.getCustID(), psDBBean.getCompCode(), getDispUserCode(), baseDate, baseDate).get(0);

        return paidHolidayDispVO;
    }

    /**
     * 画面表示項目を取り出す
     *
     * @throws Exception
     */
    public List<PaidHolidayDispVO> showDispList(PsDBBean psDBBean, String txtUserCode, String txtDate) {

        baseDate = txtDate;
        setDispUserCode(txtUserCode);

        //年次休暇付与状況一覧
        List<PaidHolidayDispVO> paidHolidayDispVOList = iTmgPaidHolidayService.buildSQLForSelectPaidHoliday(psDBBean.getCustID(), psDBBean.getCompCode(), getDispUserCode(), baseDate, null);
        return paidHolidayDispVOList;

    }

    /** システムプロパティ：年次休暇管理画面において付与日数の上限チェック(40日)を行うか制御します */
    public static final String SYSPROP_TMG_CHECK_40_INVEST_LIMIT = "TMG_CHECK_40_INVEST_LIMIT";

    /**
     * システムプロパティから値を取得後、年次休暇管理画面において付与日数の上限チェック(40日)を行うか判定し値を返却します
     * true:表示する、false:表示しない
     * @return boolean
     */
    public boolean isCheck40InvestLimit(PsDBBean psDBBean){

        String sCheck40InvestLimit = psDBBean.getSystemProperty(SYSPROP_TMG_CHECK_40_INVEST_LIMIT);
        if (sCheck40InvestLimit != null && "yes".equals(sCheck40InvestLimit)) {
            return true;
        } else {
            return false;
        }
    }


}
