package jp.smartcompany.admin.groupmanager.logic.impl;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerGroupListDTO;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerGroupEditLogic;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.service.IMastGroupService;
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupManagerGroupEditLogicImpl implements GroupManagerGroupEditLogic {

    private final ScCacheUtil scCacheUtil;
    private final PsSearchCompanyUtil searchCompanyUtil;
    private final IMastGroupService iMastGroupService;

    /** 法人選択フラグ(全社区分) */
    private static final String COMPANY_FLG_ALL = "all";
    /** 法人選択フラグ(個別法人) */
    private static final String COMPANY_FLG_ONE = "one";

    public Map<String, Object> getGroupEditList(Date searchDate,String systemId,String groupId) throws ParseException {
        // 法人検索対象範囲情報取得(参照可能な法人のリストを取得)
        List<String> oCompanyValidList = searchCompanyUtil.getCompList(searchDate);
        // 今回改定日とシステム最大日付を文字列からTimpstamp型に変換
        Date tSearchDate = searchDate;
        Date dSearchDate = searchDate;
        Date tMaxDate = SysUtil.transStringToDate(PsConst.MAXDATE);
        PsSession psSession = (PsSession) ContextUtil.getHttpRequest().getSession().getAttribute(Constant.PS_SESSION);
        GroupManagerGroupListDTO groupManagerGroupListDTO = getGroupInfoDispInfo(psSession.getLoginCustomer(),
                systemId, groupId, psSession.getLoginDesignation().get(0),
                tSearchDate, tMaxDate,psSession.getLanguage(),oCompanyValidList);

        return null;
    }

    /**
     * 初期処理(新規作成用)<br>
     * 該当条件設定画面にて表示する情報を取得します。
     *
     * @param psCustomerId  顧客コード
     * @param psSystemId    システムコード
     * @param psGroupId     グループコード
     * @param pdDesignation 異動歴情報
     * @param ptStartDate   開始日(今回改定日)
     * @param ptEndDate     終了日
     * @param psLanguage    言語区分
     */
    public GroupManagerGroupListDTO getGroupInfoDispInfo(String psCustomerId, String psSystemId,
                                     String psGroupId, Designation pdDesignation, Date ptStartDate,
                                     Date ptEndDate,  String psLanguage,List<String> companyList) {
        GroupManagerGroupListDTO groupListDTO = new GroupManagerGroupListDTO();
        groupListDTO.setMgCcustomerid(psCustomerId);
        groupListDTO.setMgCsystemidCkFk(psSystemId);
        // グループIDが渡されてこない場合は、新規登録用の処理を行う
        if (psGroupId == null) {
            // 優先順位(初期値は固定で"999")
//            BigDecimal bWeightage = BigDecimal.valueOf(999);

            // 優先順位(現在の最大優先順位)が指定されていた場合
//            if (psWeightage != null) {
//                bWeightage = BigDecimal.valueOf(Integer.valueOf(psWeightage).intValue());
//            }
            // 異動歴より、法人情報を取得する
            String sCompanyId   = pdDesignation.getCompanyCode();
            String sCompanyName = pdDesignation.getCompanyName();
            // 表示用Dtoに初期表示値を格納
            groupListDTO.setMgId(null);
            groupListDTO.setMgDstartdate(ptStartDate);
            groupListDTO.setMgDenddate(ptEndDate);
            groupListDTO.setMgCgroupidPk("");
            groupListDTO.setMgCgroupdescription("");
            groupListDTO.setMgCgroupdescriptionja("");
            groupListDTO.setMgCgroupdescriptionen("");
            groupListDTO.setMgCgroupdescriptionch("");
            groupListDTO.setMgCgroupdescription01("");
            groupListDTO.setMgCgroupdescription02("");
            groupListDTO.setMgClanguage(psLanguage);
            groupListDTO.setMgNpartinentnumber(null);
//            groupListDTO.setMgNweightage(bWeightage);
            groupListDTO.setMgCtext("");
            groupListDTO.setMgCcompanyid(sCompanyId);
            groupListDTO.setGsCompanyName(sCompanyName);
            groupListDTO.setGbDisabled(false);
            // 法人選択フラグを格納(個別法人選択)
            groupListDTO.setCompanySelectedFlg(COMPANY_FLG_ONE);
        }else {
            // 指定グループ情報取得
//            List<GroupManagerGroupListDTO> oGroupInfo =
//                    iMastGroupService.selectGroupHistoryList(
//                            psCustomerId, psSystemId, psLanguage,
//                            psGroupId, ptStartDate,companyList);
//            if (CollUtil.isNotEmpty(oGroupInfo)) {
//                // 表示用Dtoに取得データを格納
//                groupListDTO.setMgId(oGroupInfo.get(0).getMgId());
//                groupListDTO.setMgCgroupidPk(oGroupInfo.get(0).getMgCgroupidPk());
//                groupListDTO.setMgClanguage(oGroupInfo.get(0).getMgClanguage());
//                groupListDTO.setMgDstartdate(oGroupInfo.get(0).getMgDstartdate());
//                groupListDTO.setMgDenddate(oGroupInfo.get(0).getMgDenddate());
//                groupListDTO.setMgCgroupdescription(oGroupInfo.get(0).getMgCgroupdescription());
//                groupListDTO.setMgCgroupdescriptionja(oGroupInfo.get(0).getMgCgroupdescriptionja());
//                groupListDTO.setMgCgroupdescriptionen(oGroupInfo.get(0).getMgCgroupdescriptionen());
//                groupListDTO.setMgCgroupdescriptionch(oGroupInfo.get(0).getMgCgroupdescriptionch());
//                groupListDTO.setMgCgroupdescription01(oGroupInfo.get(0).getMgCgroupdescription01());
//                groupListDTO.setMgCgroupdescription02(oGroupInfo.get(0).getMgCgroupdescription02());
//                groupListDTO.setMgCcompanyid(oGroupInfo.get(0).getMgCcompanyid());
//                groupListDTO.setMgNpartinentnumber(oGroupInfo.get(0).getMgNpartinentnumber());
//                groupListDTO.setMgNweightage(oGroupInfo.get(0).getMgNweightage());
//                groupListDTO.setMgCtext(oGroupInfo.get(0).getMgCtext());
//                groupListDTO.setGsCompanyName(oGroupInfo.get(0).getGsCompanyName());
//                groupListDTO.setGbDisabled(true);
//                // 法人選択情報を格納
//                if (this.getCompanyId().equals(PsConst.CODE_ALL_COMPANIES)) {
//                    // 全社区分選択
//                    groupListDTO.setCompanySelectedFlg(COMPANY_FLG_ONE);
//                } else {
//                    // 個別法人選択
//                    groupListDTO.setCompanySelectedFlg(COMPANY_FLG_ALL);
//                }
//            }
        }

        return null;
    }

}
