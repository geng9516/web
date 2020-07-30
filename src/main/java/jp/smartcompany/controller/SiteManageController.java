package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsResult;
import jp.smartcompany.job.modules.tmg.evaluatersetting.EvaluatorSettingBean;
import jp.smartcompany.job.modules.tmg.evaluatersetting.EvaluatorSettingConst;
import jp.smartcompany.job.modules.tmg.evaluatersetting.EvaluatorSettingParam;
import jp.smartcompany.job.modules.tmg.permStatList.PermStatListBean;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Vector;

/**
 * @author Xiao Wenpeng
 * 就業承認・管理Controller
 */
@Controller
@RequestMapping("sys/manage")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SiteManageController {

    private final PermStatListBean permStatListBean;

    /**
     * 跳转到部署別統計情報確認界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("wsum")
    public String toManageWSum(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam("moduleIndex") Integer moduleIndex,
            @RequestParam("menuId") Long menuId, ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        modelMap.addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_EMP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_GROUP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);
        return "sys/manage/wsum";
    }

    /**
     * 跳转到勤務パターン設定界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
/*    @GetMapping("wpattern")
    public String toManageAddWork(@RequestParam("moduleIndex") Integer moduleIndex,
                                  @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId);
        return "sys/manage/wpattern";
    }*/

    /**
     * 跳转到年5日時季指定取得確認界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @RequestMapping("require5days")
    public String toTmgAcquired5DaysHoliday(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestParam(value = "menuId") Long menuId,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_EMP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_GROUP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);
        return "sys/manage/require5days";
    }

    @GetMapping("attendancebook")
    public String toAttendanceBook(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam("moduleIndex") Integer moduleIndex,
            @RequestParam("menuId") Long menuId, ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute("targetGroup", referList.getTargetGroup())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_EMP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_GROUP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);
        return "sys/manage/attendancebook";
    }

    @GetMapping("tmgschedule")
    public String toTmgSchedule(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam("moduleIndex") Integer moduleIndex,
            @RequestParam("menuId") Long menuId, ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute("targetGroup", referList.getTargetGroup())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_EMP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_GROUP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);
        return "sys/manage/tmgschedule";
    }

    /**
     * 跳转到承認状況一覧界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("permstatlist")
    public String toManagePermstatList(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam("moduleIndex") Integer moduleIndex,
            @RequestParam("menuId") Long menuId, ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap.addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_EMP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_GROUP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);
        return "sys/manage/permstatlist";
    }

    /**
     * 跳转到休暇承認界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("tmgnotification")
    public String toTmgNotification(@RequestParam("moduleIndex") Integer moduleIndex,
                                    @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId);
        return "sys/manage/tmgnotification";
    }


    /**
     * 跳转到就業承認界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("tmgresults")
    public String toTmgResults(@RequestParam("moduleIndex") Integer moduleIndex,
                                    @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId);
        return "sys/manage/addwork";
    }



    /**
     * 跳转到权限设定
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("perms")
    public String toPerms(
                          @RequestAttribute("BeanName") PsDBBean psDBBean,
                          @RequestParam("moduleIndex") Integer moduleIndex,
                          @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId);
        boolean haveAuthority = false; // ログインユーザーが、表示グループに対して権限を持っているか
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        // 汎用参照リストを生成する
        try {
            params.setAction((String)psDBBean.getRequestHash().get(EvaluatorSettingConst.REQUEST_KEY_ACTION));
            params.setSite(psDBBean.getSiteId());
            params.setLanguage(psDBBean.getLanguage());
            params.setCustomerID(psDBBean.getCustID());
            params.setCompanyId(psDBBean.getCompCode());
            params.setEmployee(psDBBean.getUserCode());
            /*
             * 初期表示の際に用いる基準日を取得する際に、システム日付時点で有効な歴の開始日を用いる
             * 但し、この際に顧客・法人・組織の値が特定される必要があり、組織は汎用ツリーの挙動に合わせる必要があり、汎用ツリーを用いるには基準日特定する必要がある
             * この為、汎用ツリーが用いる組織の特定方法を用いて汎用ツリーより先に組織を取得する。
             */
            String adminTargetSection = psDBBean.getReqParam(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
            String permTargetSection = psDBBean.getReqParam(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION);
            if (params.isSiteTa() && StrUtil.isNotBlank(adminTargetSection)) {
                params.setSection(adminTargetSection);
            } else if (params.isSiteTp() && StrUtil.isNotBlank(permTargetSection)) {
                params.setSection(permTargetSection);
            }
            // REQUEST:基準日
            String YYYYMMDD = psDBBean.getReqParam(EvaluatorSettingConst.REQUEST_KEY_YYYYMMDD);
            // 承認サイト、管理サイトで基準日設定を振り分ける。
            if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId())) {
                // 初期表示時、承認サイトの場合はシステム年月初日を設定
                if (StrUtil.isBlank(YYYYMMDD)) {
                    YYYYMMDD = TmgUtil.getSysdate().substring(0, 8).concat("01");
                    params.setYYYYMMDD(YYYYMMDD);
                } else {
                    params.setYYYYMMDD(YYYYMMDD);
                }
            } else {
                if (StrUtil.isBlank(YYYYMMDD)) {
                    params.setYYYYMMDD(psDBBean.getCreterialDate1().substring(0, 10).replaceAll("-", "/"));
                    Vector vecSQL = new Vector();
                    vecSQL.add(EvaluatorSettingBean.buildSQLForTransitionDate(params));
                    PsResult psResult = psDBBean.getValuesforMultiquery(vecSQL, EvaluatorSettingConst.BEAN_DESC);
                    String baseDate = psDBBean.valueAtColumnRow(psResult,0, 1, 0);
                    if (StrUtil.isNotBlank(baseDate)) {
                        params.setYYYYMMDD(baseDate);
                    }
                } else {
                    params.setYYYYMMDD(YYYYMMDD);
                }
            }
        }  catch (Exception e) {
            haveAuthority = false;
        }
        return "sys/manage/perms";
    }

    /**
     * 跳转到 勤務パターン
     *
     * @param moduleIndex
     * @param menuId
     * @return
     */
    @RequestMapping("wpattern")
    public String patternSetting(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestParam(value = "menuId") Long menuId,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap.addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_EMP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_GROUP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);
        return "sys/manage/patternsetting";
    }


    /**
     * 跳转到 超過勤務命令
     *
     * @param moduleIndex
     * @param menuId
     * @return
     */
    @RequestMapping("overtimeinstruct")
    public String toManageOvertimeInstruct(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestParam(value = "menuId") Long menuId,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_EMP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_GROUP, TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);
        return "sys/manage/overtimeinstruct";
    }


}
