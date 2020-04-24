package jp.smartcompany.job.modules.tmg.patternsetting;


import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternRestDO;
import jp.smartcompany.job.modules.core.service.ITmgPatternRestService;
import jp.smartcompany.job.modules.core.service.ITmgPatternService;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTimeRange;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternDetailRow;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternRow;
import jp.smartcompany.job.modules.tmg.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgGetTmgPattern extends BaseExecute{

    private final Util utilbean;
    private final ITmgPatternRestService iTmgPatternRestService;
    private final ITmgPatternService iTmgPatternService;
    /**
     *プラガブル用メイン処理
     * @param pluggableDTO PluggableDTO
     * @return Object
     */
    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return null;
    }


    /**
     * デフォルト勤務パターン取得（標準）
     *
     * @param customerId 顧客コード
     * @param companyId 法人コード
     * @param sectionId 部局コード
     * @param groupId グループコード
     * @param patternId 勤務パターンID
     * @param yyyymmdd 基準日
     * @return List<TmgPatternRow></> デフォルト勤務パターン
     */
    protected TmgPatternRow init(String customerId, String companyId, String sectionId,String groupId,String patternId, Date yyyymmdd) {

        List<String> upperSectionList=utilbean.tmgGetUpperSection(customerId,companyId,null,yyyymmdd);

        List<TmgTimeRange> ttRList=new ArrayList<TmgTimeRange>();

        List<TmgPatternRestDO> tprDoList=iTmgPatternRestService.selectPatternRestTime(customerId,companyId,sectionId,
                groupId,patternId,yyyymmdd,upperSectionList.get(0));
        //休憩開始終了時刻の取得と設定
        for(TmgPatternRestDO tprDo:tprDoList){
            TmgTimeRange ttr=new  TmgTimeRange();
            ttr.setCId("TMG_ITEMS|PlanRest");
            ttr.setCType("REJECT");
            ttr.setNOpen(tprDo.getTprNrestopen());
            ttr.setNClose(tprDo.getTprNrestclose());
            ttRList.add(ttr);
        }

        //始業終業時刻と休憩開始終了時刻をTableObjectタイプへ変更
        List<TmgPatternDetailRow> tpDoList=iTmgPatternService.selectPatternDetail(customerId,companyId,sectionId,
                groupId,patternId,yyyymmdd,upperSectionList.get(0));

        for(TmgPatternDetailRow tpdr:tpDoList){
            TmgTimeRange ttr=new  TmgTimeRange();
            ttr.setCId("TMG_ITEMS|PlanDuty");
            ttr.setCType("PLAN");
            ttr.setNOpen(tpdr.getTpaNOpen());
            ttr.setNClose(tpdr.getTpaNClose());
            ttRList.add(ttr);

            TmgPatternRow tpr=new  TmgPatternRow();
            tpr.setCCustomerId(tpdr.getTpaCCustomerId());
            tpr.setCCompanyId(tpdr.getTpaCCompanyId());
            tpr.setCSectionId(tpdr.getTpaCSectionId());
            tpr.setCSectionName(tpdr.getTpaCSectionName());
            tpr.setCSectionNick(tpdr.getTpaCSectionNick());
            tpr.setCGroupId(tpdr.getTpaCGroupId());
            tpr.setCGroupName(tpdr.getTpaCGroupName());
            tpr.setDStartDate(tpdr.getTpaDStartDate());
            tpr.setDEndDate(tpdr.getTpaDEndDate());
            tpr.setCModifierUserId(tpdr.getTpaCModifierUserId());
            tpr.setDModifiedDate(tpdr.getTpaDModifiedDate());
            tpr.setCModifierProgramId(tpdr.getTpaCModifierProgramId());
            tpr.setCPatternId(tpdr.getTpaCPatternId());
            tpr.setCPatternName(tpdr.getTpa_CPatterName());
            tpr.setCDefaultFlg(tpdr.getTpaCDefaultFlg());
            tpr.setTimeRange(ttRList);
            tpr.setCType("SECTION");
            tpr.setNSeq(0);
            tpr.setC2CalDays(tpdr.getTpa_C2CalDays());
            tpr.setCNextPtn(tpdr.getTpaCNextPtn());
            tpr.setNDateChangeTime(tpdr.getTpaNDateChangeTime());
            // 休日フラグ
            tpr.setCHolFlg(null);

            return tpr;
        }

        return null;
    }
}
