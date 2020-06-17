package jp.smartcompany.job.modules.tmg.paidholiday;

import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.service.ITmgPaidHolidayService;
import jp.smartcompany.job.modules.core.service.ITmgTriggerService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgPaidHolidayDto;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayDispVO;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;
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


    /**
     * PsDBBean
     */
    private PsDBBean psDBBean;

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
    public void actDispRlist(ModelMap modelMap){
        //転送項目取得
        showDisp(modelMap);

    }
    /**
     * 登録ボタン
     * ACT_UADJUST
     */
    public void actUadjust(ModelMap modelMap, String empSql){
        //更新処理をする
        execUpdate();
        //転送項目の取得
        List<PaidHolidayInitVO> listPaidHolidayInit= iMastEmployeesService.listPaidHolidayInit(empSql);
        modelMap.addAttribute("listPaidHolidayInit",listPaidHolidayInit);
    }
    /** 更新プログラムID */
    private static final String MOD_PROGRAM_ID = "PaidHoliday_ACT_UADJUST";
    /** プログラムID */
    private static final String PROGRAM_ID	  = "PaidHoliday";
    /**
     * 更新処理をする
     */
    private void execUpdate(){
        TmgPaidHolidayDto tmgPaidHolidayDto= new TmgPaidHolidayDto();
        tmgPaidHolidayDto.setTphCmodifieruserid(psDBBean.getUserCode());
        tmgPaidHolidayDto.setTphCmodifierprogramid(MOD_PROGRAM_ID);
        tmgPaidHolidayDto.setTphNinvest(psDBBean.getReqParam("txtTPH_NINVEST"));
        tmgPaidHolidayDto.setTphNadjust(psDBBean.getReqParam("txtTPH_NADJUST"));
        tmgPaidHolidayDto.setTphNadjustHours(psDBBean.getReqParam("txtTPH_NADJUST_HOURS")) ;
        tmgPaidHolidayDto.setTphNadjustTo(psDBBean.getReqParam("txtTPH_NADJUST_TO"));
        tmgPaidHolidayDto.setTphNadjustHoursTo(psDBBean.getReqParam("txtTPH_NADJUST_HOURS_TO"));
        tmgPaidHolidayDto.setTphDexpireAdjust(psDBBean.getReqParam("txtTPH_DEXPIRE_ADJUST"));
        tmgPaidHolidayDto.setTphDexpireAdjustTo(psDBBean.getReqParam("txtTPH_DEXPIRE_ADJUST_TO"));
        tmgPaidHolidayDto.setTphCcomment(psDBBean.getReqParam("txtTPH_CCOMMENT"));

        tmgPaidHolidayDto.setTphCemployeeid(getDispUserCode()) ;
        tmgPaidHolidayDto.setTphDyyyymmdd(baseDate) ;
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
     * 検索する社員番号をセットする
     * @param sValue 社員番号
     */
    private void setDispUserCode(String sValue){
        this.sDispUser= sValue;
    }

    /**
     * 検索する社員番号を返す
     * @return 社員番号
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
     * @throws Exception
     */
    private void showDisp(ModelMap modelMap) {

        if(psDBBean.getReqParam("txtDATE") == null || psDBBean.getReqParam("txtDATE").equals("") == true){
            baseDate = getSysdate();
        }else{
            baseDate = psDBBean.getReqParam("txtDATE");
        }

        if(psDBBean.getReqParam("txtUserCode") != null && psDBBean.getReqParam("txtUserCode").equals("") == false){
            setDispUserCode( psDBBean.getReqParam("txtUserCode") );
            getReferList().setTargetEmployee( psDBBean.getReqParam("txtUserCode") );
        }else{
            setDispUserCode( getReferList().getTargetEmployee() );
        }
        //年次休暇付与状況一覧
        List<PaidHolidayDispVO> paidHolidayDispVOList = iTmgPaidHolidayService.buildSQLForSelectPaidHoliday(psDBBean.getCustID(), psDBBean.getCompCode(),getDispUserCode(), baseDate, null);
        modelMap.addAttribute("paidHolidayDispVOList",paidHolidayDispVOList);
        // 年次休暇付与状況
        PaidHolidayDispVO paidHolidayDispVO = iTmgPaidHolidayService.buildSQLForSelectPaidHoliday(psDBBean.getCustID(), psDBBean.getCompCode(), getDispUserCode(), baseDate, baseDate).get(0);
        modelMap.addAttribute("paidHolidayDispVO",paidHolidayDispVO);
    }


    private String getSysdate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(new Date());
    }


    /**
     * 日付の妥当性チェックを行います。
     * 指定した日付文字列（yyyy/MM/dd or yyyy-MM-dd）が
     * カレンダーに存在するかどうかを返します。
     * @param strDate チェック対象の文字列
     * @return 存在する日付の場合true
     */
    public static boolean checkDate(String strDate) {
        if (strDate == null) {
            return false;
        }
        strDate = strDate.replace('-', '/');
        DateFormat format = DateFormat.getDateInstance();
        // 日付/時刻解析を厳密に行うかどうかを設定する。
        format.setLenient(false);
        try {
            format.parse(strDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** システムプロパティ：年次休暇管理画面において付与日数の上限チェック(40日)を行うか制御します */
    public static final String SYSPROP_TMG_CHECK_40_INVEST_LIMIT = "TMG_CHECK_40_INVEST_LIMIT";

    /**
     * システムプロパティから値を取得後、年次休暇管理画面において付与日数の上限チェック(40日)を行うか判定し値を返却します
     * true:表示する、false:表示しない
     * @return boolean
     */
    public boolean isCheck40InvestLimit(){

        String sCheck40InvestLimit = psDBBean.getSystemProperty(SYSPROP_TMG_CHECK_40_INVEST_LIMIT);
        if (sCheck40InvestLimit != null && "yes".equals(sCheck40InvestLimit)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 組織選択メッセージを表示するか判定し返却する
     * @return boolean
     */
    public boolean isSelectedSecOrEmp(){

        if(getReferList().getTargetSec() == null || "".equals(getReferList().getTargetSec())
                || "null".equals(getReferList().getTargetSec()) && !getReferList().isEmployeeList()){
            return true;
        } else {
            return false;
        }

    }
}
