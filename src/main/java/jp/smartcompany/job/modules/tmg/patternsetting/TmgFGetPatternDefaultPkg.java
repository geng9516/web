package jp.smartcompany.job.modules.tmg.patternsetting;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.core.service.IHistDesignationService;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import jp.smartcompany.job.modules.core.service.ITmgPatternAppliesService;
import jp.smartcompany.job.modules.core.service.ITmgPatternService;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTimeRange;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.SectionGroupId;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternRow;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPersonalPatternRow;
import jp.smartcompany.job.modules.tmg.util.SqlUtil;
import jp.smartcompany.job.modules.tmg.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgFGetPatternDefaultPkg extends BaseExecute {


private  final ITmgPatternAppliesService iTmgPatternAppliesService;
private  final TmgGetTmgPattern tmgGetTmgPattern;
private  final TmgGetPersonalPattern tmgGetPersonalPattern;
private  final ITmgPatternService iTmgPatternService;
private  final TmgPatternTimeRangeCalc tmgPatternTimeRangeCalc;
private  final IMastGenericDetailService iMastGenericDetailService;
private  final Util util;
private  final IHistDesignationService iHistDesignationService;
    /**
     *プラガブル用メイン処理
     * @param pluggableDTO PluggableDTO
     * @return Object
     */
    @Override
    public Object execute(PluggableDTO pluggableDTO) {

        // init()を呼び出す
        return this.init(pluggableDTO.getCustomerId(),pluggableDTO.getCompanyId(),pluggableDTO.getEmployeeId(),pluggableDTO.getYyyymmdd());
    }

    /**
     * デフォルト勤務パターン取得（標準）
     *
     * @param customerId 顧客コード
     * @param companyId 法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd 基準日
     * @return List<TmgPatternRow></> デフォルト勤務パターン
     */
    protected TmgPatternRow init(String customerId, String companyId, String employeeId, Date yyyymmdd) {

        String patternId;
        customerId= (String) SqlUtil.nvlSql(customerId,"01");
        companyId= (String) SqlUtil.nvlSql(companyId,"01");
        employeeId= (String) SqlUtil.nvlSql(employeeId,"01");
        yyyymmdd= (Date) SqlUtil.nvlSql(yyyymmdd,DateUtil.date());

        List<TmgTimeRange> ttrList=new ArrayList<TmgTimeRange>();

        TmgPatternRow tpr=new  TmgPatternRow();
        //パターン割付情報を検索
        patternId=iTmgPatternAppliesService.selectPatternId(customerId,companyId,employeeId,yyyymmdd);
        //該当者に適用されている勤務パターンを取得
        if(!StrUtil.hasEmpty(patternId)){
            tpr= tmgGetTmgPattern.init(customerId,companyId,null,null,patternId,yyyymmdd);
            if(tpr!=null){
                return tpr;
            }
        }

        //個人契約情報の勤務パターンを取得
        TmgPersonalPatternRow tppr=tmgGetPersonalPattern.init(customerId,companyId,employeeId,yyyymmdd);
        if(tppr!=null){
            //始業終業時刻を設定
            TmgTimeRange ttr=new TmgTimeRange();
            ttr.setCId("TMG_ITEMS|PlanDuty");
            ttr.setCType("PLAN");
            ttr.setNOpen(tppr.getNOpen());
            ttr.setNClose(tppr.getNClose());
            ttrList.add(ttr);
            //人勤務パターンをもとにする場合、休憩開始終了がNULLの休憩レコードは作成しない
            if(tppr.getNRestOpen() != 0 &&tppr.getNRestClose()!=0){
                //休憩開始終了時刻を設定
                ttr=null;
                ttr.setCId("TMG_ITEMS|PlanRest");
                ttr.setCType("REJECT");
                ttr.setNOpen(tppr.getNRestOpen());
                ttr.setNClose(tppr.getNRestClose());
                ttrList.add(ttr);
            }
            //日付変更時刻はルート組織から取得
            int changeTme = iTmgPatternService.selectChangeTime(customerId,companyId,yyyymmdd);
            // ルート組織　日付変更時刻が未	設定の場合、300を格納
            changeTme=(int)SqlUtil.nvlSql(changeTme,300);
            tpr.setCCustomerId(tppr.getCCustomerId());
            tpr.setCCompanyId(tppr.getCcCmpanyId());
            tpr.setCPatternName("契約情報");
            tpr.setTimeRange(ttrList);
            tpr.setNTime(tmgPatternTimeRangeCalc.init(ttrList));
            tpr.setCType("PERSONAL");
            tpr.setNSeq(0);
            tpr.setNDateChangeTime(changeTme);
            tpr.setCHolFlg(tppr.getCHolFlg());
            return tpr;

        }

        //ワークタイプのデフォルトパターンを検索
        String workPattern =iMastGenericDetailService.selectWorkPattern(customerId,companyId,yyyymmdd,
                util.tmgGetWorkerType(customerId,companyId,employeeId,yyyymmdd));
        //名称マスタ上にワークタイプ毎のデフォルトが指定されている場合はこれを適用
        if(!StrUtil.hasEmpty(workPattern)){
            tpr=tmgGetTmgPattern.init(customerId,companyId,null,null,workPattern,yyyymmdd);
            if(tpr!=null){
                return tpr;
            }
        }
        //在職部署・グループのデフォルトを取得
        SectionGroupId sgId=iHistDesignationService.selectSecGroupId(customerId,companyId,employeeId,yyyymmdd);
        if(!StrUtil.hasEmpty(sgId.getSectionId())){
            tpr=tmgGetTmgPattern.init(customerId,companyId,null,null,sgId.getGroupId(),yyyymmdd);
            if(tpr!=null){
                return tpr;
            }
            //上位組織を順に検索(自組織のデフォルトグループも含む)
            List<String> upperSectionList=util.tmgGetUpperSection(customerId,companyId,sgId.getSectionId(),yyyymmdd);
            //上位組織のデフォルトを取得
            tpr=tmgGetTmgPattern.init(customerId,companyId,null,null,upperSectionList.get(0),yyyymmdd);
            if(tpr!=null&&tpr.getCDefaultFlg().equals("TMG_ONOFF|1")){
                return tpr;
            }
        }
        return null;
    }
}
