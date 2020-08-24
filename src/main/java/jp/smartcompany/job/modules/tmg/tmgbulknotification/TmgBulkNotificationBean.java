package jp.smartcompany.job.modules.tmg.tmgbulknotification;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNotificationCheckDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNtfDetailCheckDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgErrmsgDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.dto.HistoryDto;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.dto.SectionRankDto;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.dto.UploadDto;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.*;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 休暇・休業一括登録bean
 *
 * @author Wang Ziyue
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgBulkNotificationBean {

    public final ITmgBulkNotificationService iTmgBulkNotificationService;
    public final ITmgBulkNtfDetailService iTmgBulkNtfDetailService;
    public final ITmgBulkNotificationCheckService iTmgBulkNotificationCheckService;
    public final ITmgBulkNtfDetailCheckService iTmgBulkNtfDetailCheckService;
    public final IMastGenericDetailService iMastGenericDetailService;
    public final ITmgTriggerService iTmgTriggerService;
    public final ITmgErrmsgService iTmgErrmsgService;
    public final IMastOrganisationService iMastOrganisationService;
    public final ITmgBulkNotificationLogService iTmgBulkNotificationLogService;
    /** ディスクリプタ */
    public static final String BEAN_DESC              = "TmgBulkNotification";

    /**
     * 一覧画面表示処理を実行します
     * 一覧画面の表示に必要なクエリを実行し
     */
    public BulkDispVo execRList(String baseDate,PsDBBean psDBBean){
        BulkDispVo vo  = new BulkDispVo();
        List<HistoryDto> historyDtos= iTmgBulkNotificationService.selectHistoryList(psDBBean.getCustID(),psDBBean.getCompCode(),psDBBean.getLanguage());
        vo.setTimeRange(iMastGenericDetailService.selectBulkTimeRange(psDBBean.getCustID(),psDBBean.getCompCode(),baseDate));
        vo.setHistoryList(historyDtos);
        return  vo;
    }



    /**
     * 新規登録画面表示処理を実行します(一括登録区分取得)
     */
    public List<NewBulkdropDownVo> execRMakeBulkNtf(String baseDate, PsDBBean psDBBean){
        List<NewBulkdropDownVo> vos  = iMastGenericDetailService.selectBulkdropDown(psDBBean.getCustID(),psDBBean.getCompCode(),baseDate);
        return  vos;
    }

    /**
     * 登録処理を実行します
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse execCMakeBulkNtf(UploadDto uploadDto,PsDBBean psDBBean){
        String action ="ACT_MakeBulkNtf_CMake";
        uploadDto.setSeq(iTmgBulkNotificationService.selectNextSeq());

        try{
            deleteTrigger(action,psDBBean);
            deleteErrMsg(action,psDBBean);
            deleteBulkNtfDetailCheck(uploadDto.getSeq());
            deleteBulkNotificationCheck(action,psDBBean);

            insertBulkNotificationCheck(uploadDto,action,psDBBean);
            for(String secId:uploadDto.getSectionId().split(",")){
                InsertBulkNtfDetailCheckForMulti(uploadDto.getSeq(),secId);
            }
            inserErrMsg(action,uploadDto,psDBBean);
            String errMsg=selectErrMsg(action ,psDBBean);
            if(errMsg.equals("0")){
                insertTrigger(uploadDto.getSeq(),action,psDBBean);
                return GlobalResponse.ok("0");
            }else {
                return GlobalResponse.ok(errMsg);
            }
        }
        catch (GlobalException e){
            return GlobalResponse.error(e.getMessage());
        }finally {
            deleteTrigger(action,psDBBean);
            deleteErrMsg(action,psDBBean);
            deleteBulkNtfDetailCheck(uploadDto.getSeq());
            deleteBulkNotificationCheck(action,psDBBean);
        }
    }




    /**
     * 詳細画面表示処理を実行します
     * 詳細画面の表示に必要なクエリを実行し、詳細画面のJSPページをセットします
     */
    public DetailDataVo execRDetail(String Seq ,PsDBBean psDBBean) {

        DetailDataVo detailDataVo =iTmgBulkNotificationService.selectDetail(Seq,psDBBean.getLanguage());
        return detailDataVo;
    }


    /**
     * 取消処理を実行します
     * 新規登録画面の表示に必要なSQLを実行します
     * 実際の取消処理はDBサーバ内のストアドプロシージャであり、Beanでは起動用テーブルにレコードをInsertするだけです。
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse execUCancel(String Seq ,PsDBBean psDBBean){
        String action ="ACT_DispDetail_UCancel";
        try {
            insertTrigger(Seq,action,psDBBean);
            deleteTrigger(action,psDBBean);
        }catch (GlobalException e){
            return GlobalResponse.error(e.getMessage());
        }
        return  GlobalResponse.ok();
    }






    public List<ErrorDetailVo> execRErrorList(String seq){
        return iTmgBulkNotificationLogService.selectErrorList(seq);
    }

    public List<SectionDetailVo> execRTargetSecList(String seq,PsDBBean psDBBean){
        return iTmgBulkNtfDetailService.selectSectionList(seq,psDBBean.getCustID(),psDBBean.getCompCode(),psDBBean.getLanguage());
    }














    private int deleteTrigger(String action ,PsDBBean psDBBean){
        QueryWrapper<TmgTriggerDO> ttDo = new QueryWrapper<TmgTriggerDO>();
        ttDo.eq("TTR_CMODIFIERPROGRAMID", BEAN_DESC + "_" + action);
        ttDo.eq("TTR_CMODIFIERUSERID", psDBBean.getUserCode());
        ttDo.eq("TTR_CCUSTOMERID", psDBBean.getCustID());
        ttDo.eq("TTR_CCOMPANYID", psDBBean.getCompCode());
        return iTmgTriggerService.getBaseMapper().delete(ttDo);
    }

    /**
     * エラーメッセージを削除するSQLを返す
     *
     * @return String SQL文
     */
    private int deleteErrMsg(String action ,PsDBBean psDBBean) {

        Map errMsg = new HashMap();
        errMsg.put("TER_CCUSTOMERID", psDBBean.getCustID());
        errMsg.put("TER_CCOMPANYID", psDBBean.getCompCode());
        errMsg.put("TER_CMODIFIERUSERID", psDBBean.getUserCode());
        errMsg.put("TER_CMODIFIERPROGRAMID", BEAN_DESC + "_" + action);

        return iTmgErrmsgService.getBaseMapper().deleteByMap(errMsg);
    }

    /**
     * 休暇休業一括登録対象組織データを削除する
     * buildSQLForDeleteBulkNtfDetailCheck
     *
     * @return String SQL文
     */
    private int deleteBulkNtfDetailCheck(String bulkNtfId) {
        Map TBC = new HashMap();
        TBC.put("TBND_NTBNID_FK", bulkNtfId);
        return iTmgBulkNtfDetailCheckService.getBaseMapper().deleteByMap(TBC);
    }

    /**
     * 休暇休業一括申請チェックテーブルデータを削除するクエリ文を生成します。

     */
    private int deleteBulkNotificationCheck(String action ,PsDBBean psDBBean) {

        Map TBN = new HashMap();
        TBN.put("TBN_CCUSTOMERID", psDBBean.getCustID());
        TBN.put("TBN_CCOMPANYID", psDBBean.getCompCode());
        TBN.put("TBN_CMODIFIERUSERID", psDBBean.getUserCode());
        TBN.put("TBN_CMODIFIERPROGRAMID", BEAN_DESC + "_" + action);

        return iTmgBulkNotificationCheckService.getBaseMapper().deleteByMap(TBN);

    }

    private int insertBulkNotificationCheck(UploadDto uploadDto,String action,
                                            PsDBBean psDBBean) {
        String section=null;
        String sectionIds = "'" + uploadDto.getSectionId().replace(",", "','") + "'";
        List<SectionRankDto> sectionList = iMastOrganisationService.selectSectionRankDto(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getLanguage(), sectionIds);
        for (SectionRankDto dto : sectionList) {
            if (dto.getRank().equals("1")) {
                section=dto.getMoCsectionidCk();
                break;
            }
        }
        TmgBulkNotificationCheckDO tbncDo = new TmgBulkNotificationCheckDO();
        tbncDo.setTbnNtbnid(Long.parseLong(uploadDto.getSeq()));
        tbncDo.setTbnCcustomerid(psDBBean.getCustID());
        tbncDo.setTbnCcompanyid(psDBBean.getCompCode());
        tbncDo.setTbnCsectionid(section);
        tbncDo.setTbnDstartdate(TmgUtil.minDate);
        tbncDo.setTbnDenddate(TmgUtil.maxDate);
        tbncDo.setTbnCmodifieruserid(psDBBean.getUserCode());
        tbncDo.setTbnDmodifieddate(DateTime.now());
        tbncDo.setTbnCmodifierprogramid("TmgBulkNotification_" + action);
        tbncDo.setTbnCbulkntftype(uploadDto.getBulkNtfId());
        tbncDo.setTbnDbegin(DateUtil.parse(uploadDto.getBeginDate()));
        tbncDo.setTbnDend(DateUtil.parse(uploadDto.getEndDate()));
        tbncDo.setTbnCstatus("TMG_BULKNTFSTATUS|110");
        tbncDo.setTbnDcreatedate(DateTime.now());
        tbncDo.setTbnCcreateuserid(psDBBean.getUserCode());

        return iTmgBulkNotificationCheckService.getBaseMapper().insert(tbncDo);
    }

    private int InsertBulkNtfDetailCheckForMulti(String seq,String secId){
        TmgBulkNtfDetailCheckDO tbndcDo = new TmgBulkNtfDetailCheckDO();
        tbndcDo.setTbndCsectionid(secId);
        tbndcDo.setTbndNtbnidFk(Long.parseLong(seq));
        return iTmgBulkNtfDetailCheckService.getBaseMapper().insert(tbndcDo);
    }

    private int inserErrMsg(String action,UploadDto uploadDto,PsDBBean psDBBean){

        String sProgramId = BEAN_DESC + "_" + action;

        TmgErrmsgDO tmgErrmsgDO = new TmgErrmsgDO();
        tmgErrmsgDO.setTerCcustomerid(psDBBean.getCustID());
        tmgErrmsgDO.setTerCcompanyid(psDBBean.getCompCode());
        tmgErrmsgDO.setTerDstartdate(TmgUtil.minDate);
        tmgErrmsgDO.setTerDenddate(TmgUtil.maxDate);
        tmgErrmsgDO.setTerCmodifieruserid(psDBBean.getUserCode());
        tmgErrmsgDO.setTerDmodifieddate(DateTime.now());
        tmgErrmsgDO.setTerCmodifierprogramid(sProgramId);
        tmgErrmsgDO.setTerCerrcode(iTmgBulkNotificationCheckService.checkBulkNtf(uploadDto.getSeq(),
                psDBBean.getCustID(),psDBBean.getCompCode()));
        tmgErrmsgDO.setTerClanguage(psDBBean.getLanguage());

        return iTmgErrmsgService.getBaseMapper().insert(tmgErrmsgDO);
    }

    /**
     * エラーメッセージを取得する
     *
     * @return String SQL文
     */
    private String selectErrMsg(String action,PsDBBean psDBBean) {

        QueryWrapper<TmgErrmsgDO> teDo = new QueryWrapper<TmgErrmsgDO>();
        teDo.eq("TER_CMODIFIERPROGRAMID", BEAN_DESC + "_" + action);
        teDo.eq("TER_CMODIFIERUSERID", psDBBean.getUserCode());
        return iTmgErrmsgService.getBaseMapper().selectOne(teDo).getTerCerrcode();
    }



    /**
     * トリガーテーブルへ登録を行うクエリ文を生成します。
     *
     * @return String SQL文
     */
    private int insertTrigger(String seq,String action,PsDBBean psDBBean) {

        TmgTriggerDO tmgTriggerDO = new TmgTriggerDO();
        tmgTriggerDO.setTtrCcustomerid(psDBBean.getCustID());
        tmgTriggerDO.setTtrCcompanyid(psDBBean.getCompCode());
        tmgTriggerDO.setTtrCemployeeid(psDBBean.getUserCode());
        tmgTriggerDO.setTtrDstartdate(TmgUtil.minDate);
        tmgTriggerDO.setTtrDenddate(TmgUtil.maxDate);

        tmgTriggerDO.setTtrCmodifieruserid(psDBBean.getUserCode());
        tmgTriggerDO.setTtrDmodifieddate(DateTime.now());
        tmgTriggerDO.setTtrCmodifierprogramid(BEAN_DESC + "_" + action);
        tmgTriggerDO.setTtrCprogramid(BEAN_DESC + "_" + action);
        tmgTriggerDO.setTtrCparameter1(action);
        tmgTriggerDO.setTtrNparameter1(Double.parseDouble(seq));

        return iTmgTriggerService.getBaseMapper().insert(tmgTriggerDO);

    }
}
