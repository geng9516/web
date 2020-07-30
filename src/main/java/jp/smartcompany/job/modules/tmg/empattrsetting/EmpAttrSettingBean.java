package jp.smartcompany.job.modules.tmg.empattrsetting;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.*;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.empattrsetting.dto.*;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.*;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 個人属性設定
 * ps.c01.tmg.EmpAttrSettingBean
 *
 * @author Wang Ziyue
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmpAttrSettingBean {



    private final ITmgPaidHolidayAttributeService iTmgPaidHolidayAttributeService;
    private final IMastGenericDetailService iMastGenericDetailService;
    private final IHistDesignationService iHistDesignationService;
    private final ITmgTriggerService iTmgTriggerService;
    private final ITmgEmployeeAttributeService iTmgEmployeeAttributeService;
    private final IMastEmployeesService IMastEmployeesService;
    private final ITmgDateofempLogService iTmgDateofempLogService;


    /** 項目区分：管理対象者[TMG_ITEMS|MANAGEFLG] */
    public static final String TYPE_ITEM_MANAGEFLG = "TMG_ITEMS|MANAGEFLG";

    /** 項目区分：超過勤務対象者[TMG_ITEMS|EXCLUDE_OVERTIME] */
    public static final String TYPE_ITEM_EXCLUDE_OVERTIME = "TMG_ITEMS|EXCLUDE_OVERTIME";
    // 勤務先グループ
    public static final String TYPE_ITEM_WORKPLACE  = "TMG_WORKPLACE";


    // 勤務開始日設定状況画面-新規登録
    public static final String ACTION_EDITBEGINDATE_C = "ACTION_EDITBEGINDATE_C";
    // 勤務開始日設定状況画面-更新
    public static final String ACTION_EDITBEGINDATE_U = "ACTION_EDITBEGINDATE_U";
    // 勤務開始日設定状況画面-削除
    public static final String ACTION_EDITBEGINDATE_D = "ACTION_EDITBEGINDATE_D";



    /**
     * 機能：個人属性一覧_表示処理
     *
     * @throws Exception
     * screenDisp
     */
    public EmpDispVo actionScreenDisp(String empId,String baseDate,String sPage,PsDBBean psDBBean,TmgReferList referList) throws Exception {

        int pageInfo[] = getPageOfSearchNumber(sPage);
        EmpAttrSettingDto param = new EmpAttrSettingDto();
        param.setUserCode(psDBBean.getUserCode());
        param.setCompId(psDBBean.getCompCode());
        param.setCustId(psDBBean.getCustID());
        param.setTargetSectionId(referList.getTargetSec());
        param.setBaseDate(baseDate);
        param.setLang(psDBBean.getLanguage());
        param.setEmpListsql(referList.buildSQLForSelectEmployees());

        if(StrUtil.hasEmpty(referList.getTargetSec())){
            // 組織が選択されていない場合、何も検索せずに画面を表示する
            return null;
        }

        // 一覧画面：一覧：SELECT
        List<TmgEmpVo> tmgEmpVoList= iTmgPaidHolidayAttributeService.selectTmgEmp(param,pageInfo[0],pageInfo[1]);

        for(TmgEmpVo vo:tmgEmpVoList){
            if(vo.getTmgItemsExcludeOvertime().equals("0")){
                vo.setExcludeOvertime(true);
            }else{
                vo.setExcludeOvertime(false);
            }
            if(vo.getTmgItemsManageflg().equals("TMG_MANAGEFLG|1")){
                vo.setManageFlg(true);
            }else{
                vo.setManageFlg(false);
            }
        }

        // 一覧画面：件数：SELECT
        int tmgEmpListCount=iTmgPaidHolidayAttributeService.selectTmgEmpCount(param);

        // 一覧画面：一括編集用項目の制御情報：SELECT
        List<EmpAttsetDispVo> empAttsetDispVoList=iMastGenericDetailService.selectEmpAttsetDisp(psDBBean.getCustID(),psDBBean.getCompCode(),param.getBaseDate(),psDBBean.getLanguage());

        EmpDispVo empDispVo= new EmpDispVo();
        empDispVo.setTotalCount(tmgEmpListCount);
        empDispVo.setList(tmgEmpVoList);
        empDispVo.setCurrPage(Integer.valueOf(sPage));
        empDispVo.setPageSize(50);
        empDispVo.setTotalPage(tmgEmpListCount/50+1);
        return empDispVo;
    }


    /**
     * 表头获取
     * @param baseDate
     * @param psDBBean
     * @return
     */
    public List<EmpAttsetDispVo> getTableHeader(String baseDate,PsDBBean psDBBean){
        // 一覧画面：一括編集用項目の制御情報：SELECT
        List<EmpAttsetDispVo> empAttsetDispVoList=iMastGenericDetailService.selectEmpAttsetDisp(psDBBean.getCustID(),psDBBean.getCompCode(),baseDate,psDBBean.getLanguage());
        return empAttsetDispVoList;
    }

//    /**
//     * 機能：管理対象者一括編集_表示処理
//     *
//     * @throws Exception
//     * screenDispEditManageFlag
//     */
//    private void actionScreenDispEditManageFlag(String sPage,PsDBBean psDBBean,TmgReferList referList) throws Exception {
//
//        int pageInfo[] = getPageOfSearchNumber(sPage);
//        // 一覧画面：一覧：SELECT
//        List<TmgEmpVo> tmgEmpVoList= iTmgPaidHolidayAttributeService.selectTmgEmp(param,pageInfo[0],pageInfo[1]);
//
//        // 一覧画面：件数：SELECT
//        int tmgEmpListCount=iTmgPaidHolidayAttributeService.selectTmgEmpCount(param);
//
//        // 一覧画面：一括編集用項目の制御情報：SELECT
//        List<EmpAttsetDispVo> empAttsetDispVoList=iMastGenericDetailService.selectEmpAttsetDisp(psDBBean.getCustID(),psDBBean.getCompCode(),param.getBaseDate(),psDBBean.getLanguage());
//
//        List<MastGenericDetailDO> mgdDoList = iMastGenericDetailService.selectWorkPlace(psDBBean.getCustID(),psDBBean.getCompCode(),psDBBean.getLanguage(),
//                "TMG_WORKPLACE" ,param.getBaseDate());
//
//    }


    /**
     * 機能：平均勤務時間編集_表示処理
     *
     *screenDispEditAvgWorktime
     */
    public EditDispVo actionScreenDispEditAvgWorktime(String empId,String baseDate,PsDBBean psDBBean) throws Exception {

        // 平均勤務時間編集：社員情報：SELECT
//        EmployeeDetailVo employeeDetailVo = iHistDesignationService.selectemployee(psDBBean.getCustID(),psDBBean.getCompCode(),psDBBean.getTargetUser(),
//                psDBBean.getLanguage(),param.getTargetSectionId());

        // 平均勤務時間編集：平均勤務時間：SELECT
        AvgWorkTimeVo avgWorkTimeVo=iTmgPaidHolidayAttributeService.selectAvgWorkTime60(psDBBean.getCustID(),psDBBean.getCompCode(),empId,baseDate);

        // 平均勤務時間編集：デフォルト平均勤務時間：SELECT
        AvgWorkTimeVo avgWorkTimeVoDefault=iTmgPaidHolidayAttributeService.selectDefaultAvgWorkTime(psDBBean.getCustID(),psDBBean.getCompCode(),empId,baseDate);

        // 平均勤務時間編集：平均勤務時間設定状況：SELECT
        List<AvgWorkTimeHistoryVo> avgWorkTimeHistoryVos=iTmgPaidHolidayAttributeService.selectAvgWorkTimeHistory(psDBBean.getCustID(),psDBBean.getCompCode(),empId);

        for(AvgWorkTimeHistoryVo vo:avgWorkTimeHistoryVos){
            vo.setContent(Integer.valueOf(vo.getTphaNavgworktime())/60+"時 "+Integer.valueOf(vo.getTphaNavgworktime())%60+"分"+"(週"+vo.getTphaNworkingdaysWeek()+"日)");
        }
        // 平均勤務時間編集：平均勤務時間上限：SELECT
        MgdTimeLimitVo mgdTimeLimitVo=iMastGenericDetailService.selectMgdTimeLimit();


        EditDispVo editDispVo = new EditDispVo();
        editDispVo.setLimitTime(String.valueOf(mgdTimeLimitVo.getAllminutes()));
        editDispVo.setHistoryList(avgWorkTimeHistoryVos);

        if(avgWorkTimeVo != null){
            editDispVo.setNowAvgWorkTime(avgWorkTimeVo);
        }else{
            editDispVo.setNowAvgWorkTime(avgWorkTimeVoDefault);
        }

        editDispVo.getNowAvgWorkTime().setAvgWorkTime(String.format("%02d", Integer.valueOf(editDispVo.getNowAvgWorkTime().getAvgWorkTime1()))+":"+String.format("%02d", Integer.valueOf(editDispVo.getNowAvgWorkTime().getAvgWorkTime2())));
        return editDispVo;
    }


    /**
     * 機能：平均勤務時間編集_登録処理
     *
     * modifyAvgWorktime
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse actionModifyAvgWorktime(String empId,String baseDate, AvgTimeForUpdateDto params, PsDBBean psDBBean) throws Exception {

        // 開始日が基準日以降のレコード削除(delete)
        // TMG_PAID_HOLIDAY_ATTRIBUTE削除用クエリを返す
        int deleteTmgPaidHolidayAttribute=iTmgPaidHolidayAttributeService.getBaseMapper().delete(SysUtil.<TmgPaidHolidayAttributeDO>query()
                .eq("TPHA_CCUSTOMERID",psDBBean.getCustID())
                .eq("TPHA_CCOMPANYID",psDBBean.getCompCode())
                .eq("TPHA_CEMPLOYEEID",empId)
                .ge("TPHA_DSTARTDATE",baseDate));

        // 基準日時点で適用されているデータの終了日を基準日-1で更新(update)
        int updPaidHolidayAttribute=updPaidHolidayAttribute(empId,baseDate,psDBBean);

        // 画面入力された値を登録(insert)
        int insertPaidHolidayAttribute=insertPaidHolidayAttribute(empId,baseDate,params,psDBBean);

        // トリガーテーブルにデータ削除
        int deleteTmgTriggerBef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID", "EmpAttrSetting")
                .eq("TTR_CEMPLOYEEID", empId)
                .eq("TTR_CCUSTOMERID", psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode()));

        // トリガーテーブルにデータ登録
        int insertTmgTrigger=insertTmgTrigger(empId,baseDate,psDBBean);
        // トリガーテーブルのデータ削除
        int deleteTmgTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID", "EmpAttrSetting")
                .eq("TTR_CEMPLOYEEID", empId)
                .eq("TTR_CCUSTOMERID", psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode()));

        if(updPaidHolidayAttribute==1&&insertPaidHolidayAttribute==1&&insertTmgTrigger==1){
            return  GlobalResponse.ok();
        }else{
            return  GlobalResponse.error();
        }
    }


    /**
     * 機能：平均勤務時間編集_削除処理
     * deleteAvgWorktime
     * @throws Exception
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse actionDeleteAvgWorktime(String empId,String baseDate,PsDBBean psDBBean) throws Exception {

        // 開始日が基準日以降のレコード削除(delete)
        // TMG_PAID_HOLIDAY_ATTRIBUTE削除用クエリを返す
        int deleteTmgPaidHolidayAttribute=iTmgPaidHolidayAttributeService.getBaseMapper().delete(SysUtil.<TmgPaidHolidayAttributeDO>query()
                .eq("TPHA_CCUSTOMERID",psDBBean.getCustID())
                .eq("TPHA_CCOMPANYID",psDBBean.getCompCode())
                .eq("TPHA_CEMPLOYEEID",empId)
                .ge("TPHA_DSTARTDATE",baseDate));

        // 基準日時点で適用されているデータの終了日を基準日-1で更新(update)
        int updPaidHolidayAttribute=updPaidHolidayAttribute(empId,baseDate,psDBBean);

        // トリガーテーブルにデータ登録
        int insertTmgTrigger=insertTmgTrigger(empId,baseDate,psDBBean);
        // トリガーテーブルのデータ削除
        int deleteTmgTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID", "EmpAttrSetting")
                .eq("TTR_CEMPLOYEEID", empId)
                .eq("TTR_CCUSTOMERID", psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode()));

        if(updPaidHolidayAttribute==1&&insertTmgTrigger==1){
            return  GlobalResponse.ok("平均勤務時間設定が削除されました。");
        } else if (updPaidHolidayAttribute==0&&insertTmgTrigger==1) {
            return  GlobalResponse.ok("削除対象の平均勤務時間設定が存在していません");
        } else {
            return  GlobalResponse.error();
        }
    }


    /**
     * 機能：管理対象者一括編集_更新処理
     *modifyManageFlag
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse actionModifyManageFlag(List<UpdateCheckInfoDto> updateCheckInfoDtoManageList ,
                                                 List<UpdateCheckInfoDto> updateCheckInfoDtoOverTimesList ,
                                                 List<UpdateGroupInfoDto> updateGroupInfoDtoWorkPlaceList ,
                                                 String baseDate,PsDBBean psDBBean) throws Exception {
        try {
            if (updateCheckInfoDtoManageList.size() > 0) {
            // 管理対象者分の更新用クエリ取得
            updateManageFlgEmployee(baseDate, updateCheckInfoDtoManageList, psDBBean);
            }
            if(updateCheckInfoDtoOverTimesList.size()>0){
            // 超過勤務対象者分の更新用クエリ取得
            updateOverTimesEmployee(baseDate, updateCheckInfoDtoOverTimesList, psDBBean);
            }
            // 勤務先グループの更新用クエリ取得
            if(updateGroupInfoDtoWorkPlaceList.size()>0){
                updateWorkPlace(baseDate, updateGroupInfoDtoWorkPlaceList, psDBBean);
            }
        }catch (GlobalException e){
            return GlobalResponse.error(e.getMessage());
        }
        return  GlobalResponse.ok();
    }

    /**
     * 機能：勤務開始日編集_表示処理
     *
     * screenDispEditBeginDate
     * @throws Exception
     */
    public BeginDateEditDispVo actionScreenDispEditBeginDate(String empId,String baseDate,PsDBBean psDBBean) throws Exception {


        // 平均勤務時間編集：社員情報：SELECT
//        EmployeeDetailVo employeeDetailVo = iHistDesignationService.selectemployee(psDBBean.getCustID(),psDBBean.getCompCode(),psDBBean.getTargetUser(),
//                psDBBean.getLanguage(),param.getTargetSectionId());

        // 勤務開始日設定状況：SELECT
        List<EmploymentWithMgdVo> employmentWithMgdVo = iMastGenericDetailService.selectDateOfEmploymentWithMGD(psDBBean.getCustID(),psDBBean.getCompCode()
        ,psDBBean.getLanguage(),empId,TmgUtil.Cs_MG_TMG_DATEOFEMPLOYMENT);
        // 発令上の勤務開始日取得：SELECT
        EmployMentWithMEVo employMentWithMEVo=IMastEmployeesService.selectDateofemploymentWithME(psDBBean.getCustID(),psDBBean.getCompCode(),empId);
        // 勤務開始日更新履歴取得：SELECT
        List<TmgDateOfEmpLogVo> tmgDateOfEmpLogVo=iTmgDateofempLogService.selectTmgDateofempLog(psDBBean.getCustID(),psDBBean.getCompCode(),empId,baseDate);

        BeginDateEditDispVo beginDate = new BeginDateEditDispVo();
        beginDate.setList(employmentWithMgdVo);
        beginDate.setBeginDate(employMentWithMEVo);
        beginDate.setHistoryList(tmgDateOfEmpLogVo);
        return beginDate;

    }


    /**
     * 機能：勤務開始日設定状況_新規登録処理
     *insertBeginDate
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse actionInsertBeginDate(String empId,String baseDate, PsDBBean psDBBean, BeginDateForUpdateDto params) throws Exception {

        // 勤務開始日を名称マスタに保存
        int insertMgd=iMastGenericDetailService.insertMgdKinmuStart(psDBBean.getCustID(),psDBBean.getCompCode(),empId
                                        ,psDBBean.getUserCode(),baseDate,params.getPsStartDate(),params.getPsEndDate(),params.getPsBeginDate());

        // 登録内容をログテーブルに保存
        int insertTmgDateofempLog =insertTmgDateofempLog(empId,getModifiedMessage(params),psDBBean);

        // トリガーテーブルのデータ削除
        int deleteTriggerBef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                .eq("TTR_CCOMPANYID",psDBBean.getCompCode())
                .eq("TTR_CEMPLOYEEID",empId)
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID","EmpAttrSetting"));

        // トリガーテーブルにデータ登録
        int insertTrigger = insertTrigger(empId,params.getPsStartDate(),psDBBean);

        // トリガーテーブルのデータ削除
        int deleteTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                .eq("TTR_CCOMPANYID",psDBBean.getCompCode())
                .eq("TTR_CEMPLOYEEID",empId)
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID","EmpAttrSetting"));

        if(insertMgd==1&&insertTmgDateofempLog==1&&insertTrigger==1){
            return  GlobalResponse.ok();
        }else{
            return  GlobalResponse.error();
        }
    }


    /**
     * 機能：勤務開始日設定状況_更新処理
     *updateBeginDate
     * @throws Exception
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse actionUpdateBeginDate(String empId,PsDBBean psDBBean,BeginDateForUpdateDto params) throws Exception{

        // 名称マスタを更新
        int updateMgd = updateMgd(empId,psDBBean,params);

        // 登録内容をログテーブルに保存
        int insertTmgDateofempLog =insertTmgDateofempLog(empId,getModifiedMessage(params),psDBBean);

        // トリガーテーブルのデータ削除
        int deleteTriggerBef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                .eq("TTR_CCOMPANYID",psDBBean.getCompCode())
                .eq("TTR_CEMPLOYEEID",empId)
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID","EmpAttrSetting"));

        // トリガーテーブルにデータ登録
        int insertTrigger = insertTrigger(empId,params.getPsMaxOldStartDate(),psDBBean);

        // トリガーテーブルのデータ削除
        int deleteTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                .eq("TTR_CCOMPANYID",psDBBean.getCompCode())
                .eq("TTR_CEMPLOYEEID",empId)
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID","EmpAttrSetting"));

        if(updateMgd==1&&insertTmgDateofempLog==1&&insertTrigger==1){
            return  GlobalResponse.ok();
        }else{
            return  GlobalResponse.error();
        }

    }

    /**
     * 機能：勤務開始日設定状況_削除処理
     *deleteBeginDate
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse actionDeleteBeginDate(String empId,PsDBBean psDBBean,BeginDateForUpdateDto params) throws Exception {

        // 名称マスタから削除
        int deleteMgd=iMastGenericDetailService.getBaseMapper().delete(SysUtil.<MastGenericDetailDO>query()
                .eq("MGD_CCUSTOMERID",psDBBean.getCustID())
                .eq("MGD_CCOMPANYID_CK_FK",psDBBean.getCompCode())
                .eq("MGD_CGENERICGROUPID","TMG_DATEOFEMPLOYMENT")
                .eq("MGD_CGENERICDETAILID_CK",empId)
                .eq("MGD_CLANGUAGE_CK",psDBBean.getLanguage())
                .eq("MGD_DSTART_CK",params.getPsOldStartDate()));

        // 登録内容をログテーブルに保存
        int insertTmgDateofempLog =insertTmgDateofempLog(empId,getModifiedMessage(params),psDBBean);

        // トリガーテーブルのデータ削除
        int deleteTriggerBef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                .eq("TTR_CCOMPANYID",psDBBean.getCompCode())
                .eq("TTR_CEMPLOYEEID",empId)
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID","EmpAttrSetting"));

        // トリガーテーブルにデータ登録
        int insertTrigger = insertTrigger(empId,params.getPsOldStartDate(),psDBBean);

        // トリガーテーブルのデータ削除
        int deleteTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                .eq("TTR_CCOMPANYID",psDBBean.getCompCode())
                .eq("TTR_CEMPLOYEEID",empId)
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID","EmpAttrSetting"));

        if(deleteMgd==1&&insertTmgDateofempLog==1&&insertTrigger==1){
            return  GlobalResponse.ok();
        }else{
            return  GlobalResponse.error();
        }
    }

    /**
     * 名称マスタを更新
     */
    private int updateMgd(String empId,PsDBBean psDBBean,BeginDateForUpdateDto params) {
        QueryWrapper<MastGenericDetailDO> queryWrapper= new QueryWrapper<MastGenericDetailDO>();
        queryWrapper.eq("MGD_CCUSTOMERID",psDBBean.getCustID());
        queryWrapper.eq("MGD_CCOMPANYID_CK_FK",psDBBean.getCompCode());
        queryWrapper.eq("MGD_CGENERICGROUPID",TmgUtil.Cs_MG_TMG_DATEOFEMPLOYMENT);
        queryWrapper.eq("MGD_CGENERICDETAILID_CK",empId);
        queryWrapper.eq("MGD_CLANGUAGE_CK",psDBBean.getLanguage());
        queryWrapper.eq("MGD_DSTART_CK",params.getPsOldStartDate());

        long versionNo=iMastGenericDetailService.getBaseMapper().selectOne(queryWrapper).getVersionno();

        MastGenericDetailDO mgdDo = new MastGenericDetailDO();
        mgdDo.setMgdDstartCk(DateUtil.parse(params.getPsStartDate()));
        mgdDo.setMgdDend(DateUtil.parse(params.getPsEndDate()));
        mgdDo.setMgdCmodifieruserid(psDBBean.getUserCode());
        mgdDo.setMgdDmodifieddate(DateTime.now());
        mgdDo.setMgdDsparedate1(DateUtil.parse(params.getPsBeginDate()));
        mgdDo.setVersionno(versionNo+1);

        return iMastGenericDetailService.getBaseMapper().update(mgdDo,queryWrapper);
    }


    private int insertTrigger(String empId,String paramDate,PsDBBean psDBBean) {
        TmgTriggerDO ttDo=new TmgTriggerDO();
        ttDo.setTtrCcustomerid(psDBBean.getCustID());
        ttDo.setTtrCcompanyid(psDBBean.getCompCode());
        ttDo.setTtrCemployeeid(empId);
        ttDo.setTtrDstartdate(TmgUtil.minDate);
        ttDo.setTtrDenddate(TmgUtil.maxDate);
        ttDo.setTtrCmodifieruserid(psDBBean.getUserCode());
        ttDo.setTtrDmodifieddate(DateTime.now());
        ttDo.setTtrCmodifierprogramid("EmpAttrSetting");
        ttDo.setTtrCprogramid("EmpAttrSetting");
        ttDo.setTtrDparameter1(DateUtil.parse(paramDate));
        return iTmgTriggerService.getBaseMapper().insert(ttDo);
    }

    private int insertTmgDateofempLog(String empId,String message, PsDBBean psDBBean) {
        TmgDateofempLogDO tddo = new TmgDateofempLogDO();
        tddo.setTdlgCcustomerid(psDBBean.getCustID());
        tddo.setTdlgCcompanyid(psDBBean.getCompCode());
        tddo.setTdlgCemployeeid(empId);
        tddo.setTdlgDstartdate(TmgUtil.minDate);
        tddo.setTdlgDenddate(TmgUtil.maxDate);
        tddo.setTdlgCmodifieruserid(psDBBean.getUserCode());
        tddo.setTdlgDmodifieddate(DateTime.now());
        tddo.setTdlgCmodifierprogramid("EmpAttrSetting");
        tddo.setTdlgCmodifieddetail(message);
        return iTmgDateofempLogService.getBaseMapper().insert(tddo);
    }



    /**
     *
     * 勤務開始日編集ログに保持する更新内容を返却する
     *
     * @return
     */
    public String getModifiedMessage(BeginDateForUpdateDto params){

        String sMessage = new String();
        String WRD_BD_ADD             = "追加";
        String WRD_BD_UPDATE          = "更新";
        String WRD_BD_DELETE          = "削除";
        String WRD_BD_BEFORE_BRACKETS = "[";
        String WRD_BD_HYPHEN          = "-";
        String WRD_BD_COLON           = ":";
        String WRD_BD_AFTER_BRACKETS  = "]";
        String WRD_BD_RIGHT_ARROW     = "→";
        String SPACE                  = " ";

        // 新規登録時・・・新規 [適用開始日 - 適用終了日:勤務開始日]
        if (params.getAction().equalsIgnoreCase(ACTION_EDITBEGINDATE_C)) {
            sMessage =  WRD_BD_ADD + SPACE + WRD_BD_BEFORE_BRACKETS + params.getPsStartDate() + SPACE + WRD_BD_HYPHEN + SPACE +
                    params.getPsEndDate() + SPACE + WRD_BD_COLON + SPACE + params.getPsBeginDate() + WRD_BD_AFTER_BRACKETS;
        }
        // 更新時・・・更新 [(旧)適用開始日 - (旧)適用終了日:(旧)勤務開始日] → [適用開始日 - 適用終了日:勤務開始日]
        else if (params.getAction().equalsIgnoreCase(ACTION_EDITBEGINDATE_U)) {
            sMessage =  WRD_BD_UPDATE + SPACE + WRD_BD_BEFORE_BRACKETS + params.getPsOldStartDate() + SPACE + WRD_BD_HYPHEN + SPACE +
                    params.getPsEndDate() + SPACE + WRD_BD_COLON + SPACE + params.getPsOldBeginDate() + WRD_BD_AFTER_BRACKETS + SPACE +
                    WRD_BD_RIGHT_ARROW + SPACE + WRD_BD_BEFORE_BRACKETS + params.getPsStartDate() + SPACE + WRD_BD_HYPHEN + SPACE +
                    params.getPsEndDate() + SPACE + WRD_BD_COLON + SPACE + params.getPsBeginDate() + WRD_BD_AFTER_BRACKETS;
        }
        // 削除時・・・削除 [適用開始日 - 適用終了日:勤務開始日]
        else if (params.getAction().equalsIgnoreCase(ACTION_EDITBEGINDATE_D)) {
            sMessage =  WRD_BD_DELETE + SPACE + WRD_BD_BEFORE_BRACKETS + params.getPsOldStartDate() + SPACE + WRD_BD_HYPHEN + SPACE +
                    params.getPsEndDate() + SPACE + WRD_BD_COLON + SPACE + params.getPsBeginDate() + WRD_BD_AFTER_BRACKETS;
        }

        return sMessage;
    }


    /**
     * 勤務先グループ更新用のSQLをVectorオブジェクトに追加して返却します。
     * 追加しない場合はそのままVectorオブジェクトを返却します。
     *buildSQLForUpdateWorkPlace
     */
    private void updateWorkPlace(String baseDate,List<UpdateGroupInfoDto> updateGroupInfoDtoWorkPlaceList,PsDBBean psDBBean){
        String attribute=null;

        //管理対象者分のSQL
        for (UpdateGroupInfoDto ugiDto:updateGroupInfoDtoWorkPlaceList){
            if (!ugiDto.getInitGroup().equals(ugiDto.getUpdaeGroup())){
                attribute=getAttributeManageFlg(!ugiDto.getUpdaeGroup().equals("") && ugiDto.getUpdaeGroup() != null);


                int deleteTmgEmployeeAttribute=iTmgEmployeeAttributeService.getBaseMapper().delete(SysUtil.<TmgEmployeeAttributeDO>query()
                        .eq("TES_CCUSTOMERID",psDBBean.getCustID())
                        .eq("TES_CCOMPANYID",psDBBean.getCompCode())
                        .eq("TES_CEMPLOYEEID",ugiDto.getEmployeeId())
                        .likeRight("TES_CTYPE",TYPE_ITEM_WORKPLACE)
                        .ge("TES_DSTARTDATE",DateUtil.parse(baseDate)));

                int updateTmgEmployeeAttribute=updateTmgEmployeeAttributeGroup(ugiDto.getEmployeeId(),baseDate,TYPE_ITEM_WORKPLACE,psDBBean);

                // 値が入っている場合のみInsertする
                if (!ugiDto.getUpdaeGroup().equals("") && ugiDto.getUpdaeGroup() != null) {
                    int insertTmgEmployeeAttribute=insertTmgEmployeeAttribute(ugiDto.getEmployeeId(),baseDate,TYPE_ITEM_WORKPLACE,attribute,psDBBean);
                }
            }
        }

    }

    /**
     * 超過勤務対象者分のSQLをVectorオブジェクトに追加して返却します。
     * 追加しない場合はそのままVectorオブジェクトを返却します。
     *buildSQLForUpdateOverTimesEmployee
     */
    private void updateOverTimesEmployee(String baseDate,List<UpdateCheckInfoDto> updateCheckInfoDtoOverTimesList,PsDBBean psDBBean){
        String attribute=null;

        //管理対象者分のSQL
        for (UpdateCheckInfoDto uciDto:updateCheckInfoDtoOverTimesList){
            // 変更確認
            // 変更されている場合
            if(uciDto.isInitCheckType()!=uciDto.isUpdaeCheckType()){
                attribute=getAttributeExcludeOvertime(uciDto.isUpdaeCheckType());
                // 管理対象者一括チェックが編集可の場合のみ、TMG_EMPLOYEE_ATTRIBUTEに対する処理を行うようにする。
                if("".equals(getEditTaisho(psDBBean))){
                    int deleteTmgEmployeeAttribute=iTmgEmployeeAttributeService.getBaseMapper().delete(SysUtil.<TmgEmployeeAttributeDO>query()
                            .eq("TES_CCUSTOMERID",psDBBean.getCustID())
                            .eq("TES_CCOMPANYID",psDBBean.getCompCode())
                            .eq("TES_CEMPLOYEEID",uciDto.getEmployeeId())
                            .eq("TES_CTYPE",TYPE_ITEM_EXCLUDE_OVERTIME)
                            .ge("TES_DSTARTDATE",DateUtil.parse(getFirstDayOfMonth(baseDate))));

                    int updateTmgEmployeeAttribute=updateTmgEmployeeAttribute(uciDto.getEmployeeId(),baseDate,TYPE_ITEM_EXCLUDE_OVERTIME,psDBBean);

                    int insertTmgEmployeeAttribute=insertTmgEmployeeAttribute(uciDto.getEmployeeId(),baseDate,TYPE_ITEM_EXCLUDE_OVERTIME,attribute,psDBBean);
                }
            }
        }

    }

    /**
     * 管理対象者分のSQL
     * buildSQLForUpdateManageFlgEmployee
     */
    private void updateManageFlgEmployee(String baseDate,List<UpdateCheckInfoDto> updateCheckInfoDtoManageList,PsDBBean psDBBean){
        String attribute=null;

        //管理対象者分のSQL
        for (UpdateCheckInfoDto uciDto:updateCheckInfoDtoManageList){
            // 変更確認
            // 変更されている場合
            if(uciDto.isInitCheckType()!=uciDto.isUpdaeCheckType()){
                attribute=getAttributeManageFlg(uciDto.isUpdaeCheckType());
                // 管理対象者一括チェックが編集可の場合のみ、TMG_EMPLOYEE_ATTRIBUTEに対する処理を行うようにする。
                if("".equals(getEditTaisho(psDBBean))){
                    int deleteTmgEmployeeAttribute=iTmgEmployeeAttributeService.getBaseMapper().delete(SysUtil.<TmgEmployeeAttributeDO>query()
                            .eq("TES_CCUSTOMERID",psDBBean.getCustID())
                            .eq("TES_CCOMPANYID",psDBBean.getCompCode())
                            .eq("TES_CEMPLOYEEID",uciDto.getEmployeeId())
                            .eq("TES_CTYPE",TYPE_ITEM_MANAGEFLG)
                            .ge("TES_DSTARTDATE",DateUtil.parse(getFirstDayOfMonth(baseDate))));

                    int updateTmgEmployeeAttribute=updateTmgEmployeeAttribute(uciDto.getEmployeeId(),baseDate,TYPE_ITEM_MANAGEFLG,psDBBean);

                    int insertTmgEmployeeAttribute=insertTmgEmployeeAttribute(uciDto.getEmployeeId(),baseDate,TYPE_ITEM_MANAGEFLG,attribute,psDBBean);
                }
            }
        }
    }


    public String getAttributeManageFlg(Boolean pbAttribute) {
        if (pbAttribute.equals(Boolean.TRUE)) {
            return TmgUtil.Cs_MGD_ONOFF_1;
        } else {
            return TmgUtil.Cs_MGD_ONOFF_0;
        }
    }

    public String getAttributeExcludeOvertime(Boolean pbAttribute) {
        if (pbAttribute.equals(Boolean.TRUE)) {
            return TmgUtil.Cs_MGD_ONOFF_0;
        } else {
            return TmgUtil.Cs_MGD_ONOFF_1;
        }
    }

    /**
     *
     * 個人属性の指定タイプレコードの内、基準日時点で有効なレコードの終了日を基準日の前日で更新
     */
    private int updateTmgEmployeeAttribute(String empId,String baseDate,String psType,PsDBBean psDBBean){
        TmgEmployeeAttributeDO teaDo=new TmgEmployeeAttributeDO();
        teaDo.setTesCmodifieruserid(psDBBean.getUserCode());
        teaDo.setTesDmodifieddate(DateTime.now());
        teaDo.setTesCmodifierprogramid("EmpAttrSetting");
        teaDo.setTesDenddate(DateUtil.offset(DateUtil.parse(baseDate), DateField.DAY_OF_MONTH, -1));

        QueryWrapper<TmgEmployeeAttributeDO> queryWrapper = new QueryWrapper<TmgEmployeeAttributeDO>();
        queryWrapper.eq("TES_CCUSTOMERID", psDBBean.getCustID());
        queryWrapper.eq("TES_CCOMPANYID", psDBBean.getCompCode());
        queryWrapper.eq("TES_CEMPLOYEEID", empId);
        queryWrapper.lt("TES_DSTARTDATE", DateUtil.parse(getFirstDayOfMonth(baseDate)));
        queryWrapper.ge("TES_DENDDATE", DateUtil.parse(getFirstDayOfMonth(baseDate)));
        queryWrapper.eq("TES_CTYPE", psType);

        return iTmgEmployeeAttributeService.getBaseMapper().update(teaDo,queryWrapper);
    }

    private int insertTmgEmployeeAttribute(String empId,String baseDate,String psType,String Attribute,PsDBBean psDBBean){

        TmgEmployeeAttributeDO teaDo=new TmgEmployeeAttributeDO();
        teaDo.setTesCcustomerid(psDBBean.getCustID());
        teaDo.setTesCcompanyid(psDBBean.getCompCode());
        teaDo.setTesCemployeeid(empId);
        teaDo.setTesDstartdate(DateUtil.parse(getFirstDayOfMonth(baseDate)));
        teaDo.setTesDenddate(TmgUtil.maxDate);
        teaDo.setTesCmodifieruserid(psDBBean.getUserCode());
        teaDo.setTesDmodifieddate(DateTime.now());
        teaDo.setTesCmodifierprogramid("EmpAttrSetting");
        teaDo.setTesCtype(psType);
        teaDo.setTesCattribute(Attribute);
        return iTmgEmployeeAttributeService.getBaseMapper().insert(teaDo);
    }

    /**
     *
     * 個人属性の指定タイプレコードの内、基準日時点で有効なレコードの終了日を基準日の前日で更新
     */
    private int updateTmgEmployeeAttributeGroup(String empId,String baseDate,String psType,PsDBBean psDBBean){
        TmgEmployeeAttributeDO teaDo=new TmgEmployeeAttributeDO();
        teaDo.setTesCmodifieruserid(psDBBean.getUserCode());
        teaDo.setTesDmodifieddate(DateTime.now());
        teaDo.setTesCmodifierprogramid("EmpAttrSetting");
        teaDo.setTesDenddate(DateUtil.parse(baseDate));

        QueryWrapper<TmgEmployeeAttributeDO> queryWrapper = new QueryWrapper<TmgEmployeeAttributeDO>();
        queryWrapper.eq("TES_CCUSTOMERID", psDBBean.getCustID());
        queryWrapper.eq("TES_CCOMPANYID", psDBBean.getCompCode());
        queryWrapper.eq("TES_CEMPLOYEEID", empId);
        queryWrapper.lt("TES_DSTARTDATE", DateUtil.parse(baseDate));
        queryWrapper.ge("TES_DENDDATE", DateUtil.parse(baseDate));
        queryWrapper.likeRight("TES_CTYPE", psType);

        return iTmgEmployeeAttributeService.getBaseMapper().update(teaDo,queryWrapper);
    }

    private int insertTmgEmployeeAttributeGroup(String empId,String baseDate,String psType,String Attribute,PsDBBean psDBBean){
        TmgEmployeeAttributeDO teaDo=new TmgEmployeeAttributeDO();
        teaDo.setTesCcustomerid(psDBBean.getCustID());
        teaDo.setTesCcompanyid(psDBBean.getCompCode());
        teaDo.setTesCemployeeid(empId);
        teaDo.setTesDstartdate(DateUtil.parse(baseDate));
        teaDo.setTesDenddate(TmgUtil.maxDate);
        teaDo.setTesCmodifieruserid(psDBBean.getUserCode());
        teaDo.setTesDmodifieddate(DateTime.now());
        teaDo.setTesCmodifierprogramid("EmpAttrSetting");
        teaDo.setTesCtype(psType);
        teaDo.setTesCattribute(Attribute);
        return iTmgEmployeeAttributeService.getBaseMapper().insert(teaDo);
    }
    /**
     *  管理対象者一括チェックの編集可否を取得します
     * @return String	 管理対象者一括チェックの編集可否
     */
    public String getEditTaisho(PsDBBean psDBBean) {

        String sEditTaisho = "disabled";
        String sPropnameTaisho = psDBBean.getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_EDITMANAGEFLG);

        if(sPropnameTaisho != null && !sPropnameTaisho.equals("") && sPropnameTaisho.equals("yes")){
            sEditTaisho = "";
        }

        return sEditTaisho;
    }

    /**
     *TMG_PAID_HOLIDAY_ATTRIBUTE登録用クエリを返す
     * @return int
     */
    private int insertPaidHolidayAttribute(String empId,String baseDate,AvgTimeForUpdateDto params,PsDBBean psDBBean) {

        TmgPaidHolidayAttributeDO tmgPaidHolidayAttributeDO = new TmgPaidHolidayAttributeDO();
        tmgPaidHolidayAttributeDO.setTphaCcustomerid(psDBBean.getCustID());
        tmgPaidHolidayAttributeDO.setTphaCcompanyid(psDBBean.getCompCode());
        tmgPaidHolidayAttributeDO.setTphaCemployeeid(empId);

        tmgPaidHolidayAttributeDO.setTphaDstartdate(DateUtil.parse(baseDate));
        tmgPaidHolidayAttributeDO.setTphaDenddate(TmgUtil.maxDate);

        tmgPaidHolidayAttributeDO.setTphaCmodifieruserid(psDBBean.getUserCode());
        tmgPaidHolidayAttributeDO.setTphaDmodifieddate(DateTime.now());
        tmgPaidHolidayAttributeDO.setTphaCmodifierprogramid("EmpAttrSetting");

        tmgPaidHolidayAttributeDO.setTphaNavgworktime(timeCalculator(params.getSAvgWorkingHours(),params.getSAvgWorkingMinutes()));
        tmgPaidHolidayAttributeDO.setTphaNworkingdaysWeek(Long.valueOf(params.getSWorkingdaysWeek()));

        tmgPaidHolidayAttributeDO.setTphaCworkingdaysWeek(iMastGenericDetailService.selectWeekDaysCom(
                psDBBean.getCustID(),psDBBean.getCompCode(),baseDate,Integer.valueOf(params.getSWorkingdaysWeek()),
                timeCalculator(params.getSAvgWorkingHours(),params.getSAvgWorkingMinutes()).intValue()
        ));

        return iTmgPaidHolidayAttributeService.getBaseMapper().insert(tmgPaidHolidayAttributeDO);

    }


    /**
     * トリガーテーブル(TMG_TRIGGER)登録用クエリを返す
     *
     */
    private int insertTmgTrigger(String empId,String psParmDate,PsDBBean psDBBean) {

        TmgTriggerDO ttDo = new TmgTriggerDO();

        ttDo.setTtrCcustomerid(psDBBean.getCustID());
        ttDo.setTtrCcompanyid(psDBBean.getCompCode());
        ttDo.setTtrCemployeeid(empId);
        ttDo.setTtrDstartdate(TmgUtil.minDate);
        ttDo.setTtrDenddate(TmgUtil.maxDate);
        ttDo.setTtrCmodifieruserid(psDBBean.getUserCode());
        ttDo.setTtrDmodifieddate(DateTime.now());
        ttDo.setTtrCmodifierprogramid("EmpAttrSetting");
        ttDo.setTtrCprogramid("EmpAttrSetting");
        // リクエスト 接頭辞：開始日(一番古い)
        ttDo.setTtrDparameter1(DateUtil.parse(psParmDate));

        return iTmgTriggerService.getBaseMapper().insert(ttDo);
    }


    private Long timeCalculator(String hour, String min) {
        long ihour=Integer.parseInt(hour);
        long imin=Integer.parseInt(min);

        return ihour*60+imin;
    }


    /**
     * 基準日時点で適用されているデータの終了日を基準日-1で更新(update)
     * @return int
     */
    private int updPaidHolidayAttribute(String empId,String baseDate,PsDBBean psDBBean) {

        TmgPaidHolidayAttributeDO tmgPaidHolidayAttributeDO = new TmgPaidHolidayAttributeDO();
        tmgPaidHolidayAttributeDO.setTphaDenddate(DateUtil.offsetDay(DateUtil.parse(baseDate), -1));
        tmgPaidHolidayAttributeDO.setTphaCmodifieruserid(psDBBean.getUserCode());
        tmgPaidHolidayAttributeDO.setTphaDmodifieddate(DateTime.now());
        tmgPaidHolidayAttributeDO.setTphaCmodifierprogramid("EmpAttrSetting");

        QueryWrapper<TmgPaidHolidayAttributeDO> queryWrapper = new QueryWrapper<TmgPaidHolidayAttributeDO>();
        queryWrapper.eq("TPHA_CCUSTOMERID", psDBBean.getCustID());
        queryWrapper.eq("TPHA_CCOMPANYID", psDBBean.getCompCode());
        queryWrapper.eq("TPHA_CEMPLOYEEID", empId);
        queryWrapper.lt("TPHA_DSTARTDATE", DateUtil.parse(baseDate));
        queryWrapper.ge("TPHA_DENDDATE", DateUtil.parse(baseDate));

        return iTmgPaidHolidayAttributeService.getBaseMapper().update(tmgPaidHolidayAttributeDO, queryWrapper);


    }


    /**
     * ページ情報からページング処理を実行し開始件～終了件を int型配列[0=開始件, 1=終了件]に格納し返します。
     *
     * @param sPage ページ情報
     */
    public int[] getPageOfSearchNumber(String sPage) {
        int iPage = 1;
        if (sPage != null) {
            iPage = Integer.parseInt(sPage);
        }

        int[] pageInfo = new int[2];

        if (iPage > 1) {
            pageInfo[0] = (iPage - 1) * 50 + 1;
            pageInfo[1] = iPage * 50;
        } else {
            pageInfo[0] = 1;
            pageInfo[1] = 50;
        }

        return pageInfo;
    }

    /**
     * 基準日の月初日を取得する。
     *
     * @param baseDate 基準日
     * @return firstDayOfMonth　月初日
     */
    private String getFirstDayOfMonth(String baseDate) {

        //获得baseDate 年的部分
        String year =  String.valueOf(DateUtil.year(DateUtil.parse(baseDate)));
        //获得baseDate 月份，从0开始计数
        int mm = DateUtil.month(DateUtil.parse(baseDate))+1;
        String month = mm >= 10 ? String.valueOf(mm) : ("0" + mm);

        String firstDayOfMonth = year + month + "01";

        return firstDayOfMonth;
    }
}
