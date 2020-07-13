package jp.smartcompany.admin.groupmanager.logic.impl;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.admin.component.dto.BaseSectionRowDTO;
import jp.smartcompany.admin.component.dto.BaseSectionRowListDTO;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerGroupEditLogic;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.service.IMastGroupbasesectionService;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupManagerGroupEditLogicImpl implements GroupManagerGroupEditLogic {

    private final PsSearchCompanyUtil psSearchCompanyUtil;
    private final IMastGroupbasesectionService iMastGroupbasesectionService;

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public void detail(Date searchDate,String systemId,String groupId) throws ParseException {
       PsSession psSession = (PsSession) ContextUtil.getHttpRequest().getSession().getAttribute(Constant.PS_SESSION);
       Date maxDate = SysUtil.transStringToDate(PsConst.MAXDATE);
        // 法人検索対象範囲情報取得(参照可能な法人のリストを取得)
        List<String> oCompanyValidList = psSearchCompanyUtil.getCompList(searchDate);
        /**
         * 該当条件(基点組織選択)情報を取得
         */
        // 基点組織編集 - 定義情報取得(法人リスト)
        List<BaseSectionRowDTO> oBaseSectionRowList = iMastGroupbasesectionService.
                selectGroupBaseSectionCompanyList(psSession.getLoginCustomer(),systemId,
                        groupId, psSession.getLanguage(), searchDate, oCompanyValidList);
        dispBaseSectionInfo(psSession.getLoginCustomer(), searchDate, oBaseSectionRowList);
    }

    /**
     * 初期処理<br>
     * 基点組織選択画面にて表示する情報を取得します。
     *
     * @param psCustomerId          顧客コード
     * @param dSearchDate           今回改定日
     * @param poDtoList             基点組織編集 - 定義情報取得(法人リスト)
     */
    private void dispBaseSectionInfo(String psCustomerId, Date dSearchDate,
                                     List<BaseSectionRowDTO> poDtoList) {
        // 基点組織以下・のみフラグ
        String sBelowSingle = null;

        // 法人情報配下の情報を取得する
//        for (int i = 0; i < poDtoList.size(); i++) {
//            BaseSectionRowDTO rowDTO = poDtoList.get(i);
//            String sCompanyId = rowDTO.getMgbsCcompanyid();
//            // 組織ごとの定義情報取得(法人＆組織＆役職リスト)
//            List<BaseSectionRowListDTO> sectionList = iMastGroupbasesectionService.selectGroupBaseSectionList(
//                    psCustomerId, sCompanyId, this.psSystemId, this.psGroupId,
//                    super.getLanguage(), dSearchDate);
//            rowDTO.setGlSectionList(sectionList);
//            // 現在の保持している組織リストの件数を保持する
//            rowDTO.setGnSelectedSectionCnt(sectionList.size());
//            // 1件目の基点組織以下・のみフラグをセット(全件同じフラグなので)
//            if (CollUtil.isNotEmpty(sectionList)) {
//                sBelowSingle = sectionList.get(0).getMgbsCbeloworsingle();
//            }
//
//        }
//
//        // 基点組織情報をJSONにセット
//        String sJSONDataBS = this.psJSONUtilLogic.encodeJSON(this.baseSectionDto.getRowList());
//        this.baseSectionDto.setJSONData(sJSONDataBS);
//
//        // 基点組織以下・のみフラグ nullなら以下とする
//        if (sBelowSingle == null) { sBelowSingle = "0"; }
//        this.gsBaseSectionBelowSingle = sBelowSingle;

    }

}
