package jp.smartcompany.job.modules.core.business;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.CoreError;
import jp.smartcompany.job.modules.core.pojo.bo.*;
import jp.smartcompany.job.modules.core.service.IMastGroupbasesectionService;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 基点组织Logic层
 * @author Xiao Wenpeng
 */
@Service(CoreBean.Business.BASE_SECTION)
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BaseSectionBusiness {
    private final IMastGroupbasesectionService iMastGroupbasesectionService;
    private final IMastOrganisationService iMastOrganisationService;

    public void getBaseSectionList(HttpSession httpSession) {
        PsSession psSession = (PsSession)httpSession.getAttribute(Constant.PS_SESSION);
        if (MapUtil.isEmpty(psSession.getLoginBaseSection())) {
            String date = DateUtil.format(DateUtil.date(),"yyyy/MM/dd");
            BaseSectionBO baseSection = getBaseSection(date,httpSession);
            psSession.setLoginBaseSection(baseSection.getHmCompany());
            psSession.setLoginGroupBaseSection(baseSection.getHmGroup());
        }
    }

    /**
     * 基準日時点の基点組織情報を取得します
     * @param date 基準日(yyyy/mm/dd)
     * @return 基点組織情報(法人別とグループ別)
     */
    public BaseSectionBO getBaseSection(String date,HttpSession httpSession) {
        PsSession psSession = (PsSession)httpSession.getAttribute(Constant.PS_SESSION);
        Map<String, List<LoginGroupBO>> hmGroups = psSession.getLoginGroups();
        if (CollUtil.isEmpty(hmGroups)){
            throw new GlobalException(CoreError.LOGIN_GROUP_NOT_FOUND);
        }
        Date d = DateUtil.parse(date, TmgUtil.Cs_FORMAT_DATE_TYPE1);
        Map<String, Map<String, String>> hashMapComp = MapUtil.newHashMap(true);
        Map<String, Map<String, String>> hashMapGroup = MapUtil.newHashMap(true);
        hmGroups.forEach((key,value)->
            queryBaseSection("01", key, d,
                    hashMapComp, hashMapGroup,httpSession)
        );
        log.info("【mapComp基点组织:{}】",hashMapComp);
        log.info("【mapGroup基点组织:{}】",hashMapGroup);
        BaseSectionBO bsi = new BaseSectionBO();
        bsi.setHmCompany(hashMapComp);
        bsi.setHmGroup(hashMapGroup);
        return bsi;
    }

    /**
     * 基点組織情報を生成し、hashMapに格納します
     * @param sCustomer 顧客コード
     * @param sSystem システムコード
     * @param date 基準日
     */
    private void queryBaseSection(String sCustomer, String sSystem, Date date,
                                  Map<String, Map<String, String>> hashMapComp,
                                  Map<String, Map<String, String>> hashMapGroup,
                                  HttpSession httpSession) {
        Map<String, List<GroupBaseSectionBO>> lhmComp = MapUtil.newHashMap(true);
        Map<String, List<GroupBaseSectionBO>> lhmGroup = MapUtil.newHashMap(true);
        queryBaseSectionMaster(sCustomer, sSystem, date, lhmComp, lhmGroup,httpSession);
        log.info("hmComp:{}",lhmComp);
        log.info("hmGroup:{}",lhmGroup);
        hashMapComp.put(sSystem, getBaseSectionByCompany(sCustomer, date, lhmComp));
        hashMapGroup.put(sSystem, getBaseSectionByGroup(sCustomer, date, lhmGroup));
    }

    /**
     * 基点組織マスタ取得を取得し、マップに格納します
     * @param sCustomer 顧客区分
     * @param sSystem システムコード
     * @param date 基準日
     */
    private void queryBaseSectionMaster(String sCustomer,
                                        String sSystem,
                                        Date date,
                                        Map<String, List<GroupBaseSectionBO>> lhmComp,
                                        Map<String, List<GroupBaseSectionBO>> lhmGroup,
                                        HttpSession httpSession) {
        PsSession psSession = (PsSession)httpSession.getAttribute(Constant.PS_SESSION);
        List<LoginGroupBO> hmGroups = psSession.getLoginGroups().get(sSystem);
        for (LoginGroupBO hmGroup : hmGroups) {
            String groupCode = hmGroup.getGroupCode();
            List<GroupBaseSectionBO> mastBaseSectionList =
                    iMastGroupbasesectionService.getBaseSectionByGroupCode(sCustomer, sSystem, groupCode, date);
            for (GroupBaseSectionBO groupBaseSectionBO : mastBaseSectionList) {
                String sComp = groupBaseSectionBO.getMgbsCcompanyid();
                if (!lhmComp.containsKey(sComp)) {
                    lhmComp.put(sComp, CollUtil.newArrayList());
                }
                if (!lhmComp.get(sComp).contains(groupBaseSectionBO)) {
                    lhmComp.get(sComp).add(groupBaseSectionBO);
                }
                if (!lhmGroup.containsKey(groupCode)) {
                    lhmGroup.put(groupCode, CollUtil.newArrayList());
                }
                if (!lhmGroup.get(groupCode).contains(groupBaseSectionBO)) {
                    lhmGroup.get(groupCode).add(groupBaseSectionBO);
                }
            }
        }
    }

    /**
     * 法人別基点組織の生成
     * @param sCustomer 顧客コード
     * @param tsDate 基準日
     * @param lhmSectionId 法人別基点組織マスタ <法人コード, List<基点組織マスタ>>
     * @return 法人別基点組織 <法人, 基点組織情報>
     */
    public Map<String, String> getBaseSectionByCompany(String sCustomer,
                                                       Date tsDate,
                                                       Map<String, List<GroupBaseSectionBO>> lhmSectionId) {
        Map<String,String> lhmBase = MapUtil.newHashMap(true);
        for (Map.Entry <String, List<GroupBaseSectionBO>> e : lhmSectionId.entrySet()) {
            String sCompany = e.getKey();	// 法人コード
            lhmBase.put(sCompany, this.createBaseSectionString(sCustomer, sCompany, tsDate, e.getValue()));
        }

        return lhmBase;
    }

    /**
     * 法人別基点組織の文字列を生成
     *  フォーマット: 法人コード#組織コード!組織コード!組織コード
     * @param sCustomer 顧客コード
     * @param sCompany 法人コード
     * @param tsDate 基準日
     * @param lBaseSection 法人別基点組織マスタ <法人コード, List<基点組織マスタ>>
     */
    public String createBaseSectionString(String sCustomer, String sCompany,Date tsDate, List<GroupBaseSectionBO> lBaseSection) {
        // リストの生成
        StringBuilder sbList = new StringBuilder();
        sbList.append(sCompany).append("#");	// 法人コード + "#"
        // 基点組織がない場合は終了
        if (lBaseSection.size() <= 0) {
            return sbList.toString();
        }
        // 基点組織が「のみ」の場合
        if ("1".equals(lBaseSection.get(0).getMgbsCbeloworsingle())) {
            // 組織コードを結合
            for (int i = 0; i < lBaseSection.size(); i++) {
                if (i > 0) { sbList.append("!"); }
                sbList.append(lBaseSection.get(i).getMgbsCsectionid());
            }
            // 基点組織が「以下」の場合
        } else {
            // 基点組織情報の下位組織を取得
            List<BaseSectionOrganisationBO> list = getOrganisationBelow(sCustomer, tsDate, lBaseSection);
            // 組織コードを結合
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) { sbList.append("!"); }
                sbList.append(list.get(i).getMoCsectionidCk());
            }
        }
        return sbList.toString();
    }

    /**
     * グループ別基点組織の生成
     * @param lhmLayeredId グループ別基点組織マスタ <グループコード, List<基点組織マスタ>>
     * @return グループ別基点組織 <グループ, 基点組織情報>
     */
    public Map<String, String> getBaseSectionByGroup(
            String sCustomer,
            Date tsDate,
            Map<String, List<GroupBaseSectionBO>> lhmLayeredId) {
        Map<String, String> lhmGroupBase = MapUtil.newHashMap(true);
        for (Map.Entry<String, List<GroupBaseSectionBO>> e : lhmLayeredId
                .entrySet()) {
            String sGroup = e.getKey();
            lhmGroupBase.put(
                    sGroup,
                    getLayerdBaseSectionString(sCustomer, tsDate,
                            e.getValue()));
        }
        return lhmGroupBase;
    }

    /**
     * グループ別基点組織の文字列を生成
     *  フォーマット: 階層コード!階層コード!階層コード
     * @param sCustomer 顧客コード
     * @param tsDate 基準日
     * @param lBaseSection グループ別基点組織マスタ <グループコード, List<基点組織マスタ>>
     */
    private String getLayerdBaseSectionString(String sCustomer, Date tsDate,
                                         List<GroupBaseSectionBO> lBaseSection) {
        StringBuilder sbList = new StringBuilder();
        if (lBaseSection.size() <= 0) {
            return "";
        }
        if (StrUtil.equals(
                lBaseSection.get(0).getMgbsCbeloworsingle(),
                Constant.NO_SUB_LEVEL)) {
            for (int i = 0; i < lBaseSection.size(); i++) {
                if (i > 0) {
                    sbList.append("!");
                }
                sbList.append(lBaseSection.get(i).getMgbsClayeredsectionid());
            }
        } else {
            List<BaseSectionOrganisationBO> list = getOrganisationBelow(
                    sCustomer, tsDate, lBaseSection);
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) {
                    sbList.append("!");
                }
                sbList.append(list.get(i).getMoClayeredsectionid());
            }
        }
        return sbList.toString();
    }

    /**
     * 基点組織情報の下位組織を取得します
     * @param sCustomer 顧客コード
     * @param tsDate 基準日
     * @param lBaseSection 基点組織マスタ
     * @return 基点組織以下の下位組織
     */
    private List<BaseSectionOrganisationBO> getOrganisationBelow(
            String sCustomer, Date tsDate,
            List<GroupBaseSectionBO> lBaseSection) {
        StringBuilder sbWhere = new StringBuilder(" AND ( ");
        for (int i = 0; i < lBaseSection.size(); i++) {
            if (i > 0) {
                sbWhere.append(" OR ");
            }
            sbWhere.append(" MO_CLAYEREDSECTIONID LIKE '").append((lBaseSection
                    .get(i)).getMgbsClayeredsectionid()).append("%' ");
        }
        sbWhere.append(" ) ");
        return iMastOrganisationService.selectOrganisationByLevel(sCustomer,
                sbWhere.toString(), tsDate);
    }

}
