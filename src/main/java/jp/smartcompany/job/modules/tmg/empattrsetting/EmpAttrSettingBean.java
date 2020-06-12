package jp.smartcompany.job.modules.tmg.empattrsetting;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.*;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.empattrsetting.dto.EmpAttrSettingDto;
import jp.smartcompany.job.modules.tmg.empattrsetting.dto.UpdateCheckInfoDto;
import jp.smartcompany.job.modules.tmg.empattrsetting.dto.UpdateGroupInfoDto;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.*;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.employeeDetailVo;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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



    private TmgReferList referList=null;
    private ITmgPaidHolidayAttributeService iTmgPaidHolidayAttributeService;
    private IMastGenericDetailService iMastGenericDetailService;
    private IHistDesignationService iHistDesignationService;
    private ITmgTriggerService iTmgTriggerService;
    private ITmgEmployeeAttributeService iTmgEmployeeAttributeService;
    private IMastEmployeesService IMastEmployeesService;
    private ITmgDateofempLogService iTmgDateofempLogService;
    private PsDBBean psDBBean;
    EmpAttrSettingDto param = new EmpAttrSettingDto();


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
    private void actionScreenDisp() throws Exception {

        if(StrUtil.hasEmpty(param.getTargetSectionId())){
            // 組織が選択されていない場合、何も検索せずに画面を表示する
            return;
        }

        // 一覧画面：一覧：SELECT
        List<TmgEmpVo> tmgEmpVoList= iTmgPaidHolidayAttributeService.selectTmgEmp(param,1,50);

        // 一覧画面：件数：SELECT
        int tmgEmpListCount=iTmgPaidHolidayAttributeService.selectTmgEmpCount(param);

        // 一覧画面：一括編集用項目の制御情報：SELECT
        List<EmpAttsetDispVo> empAttsetDispVoList=iMastGenericDetailService.selectEmpAttsetDisp(param.getCustId(),param.getCompId(),param.getBaseDate(),param.getLang());

    }
    /**
     * 機能：管理対象者一括編集_表示処理
     *
     * @throws Exception
     * screenDispEditManageFlag
     */
    private void actionScreenDispEditManageFlag() throws Exception {

        // 一覧画面：一覧：SELECT
        List<TmgEmpVo> tmgEmpVoList= iTmgPaidHolidayAttributeService.selectTmgEmp(param,1,50);

        // 一覧画面：件数：SELECT
        int tmgEmpListCount=iTmgPaidHolidayAttributeService.selectTmgEmpCount(param);

        // 一覧画面：一括編集用項目の制御情報：SELECT
        List<EmpAttsetDispVo> empAttsetDispVoList=iMastGenericDetailService.selectEmpAttsetDisp(param.getCustId(),param.getCompId(),param.getBaseDate(),param.getLang());

        List<MastGenericDetailDO> mgdDoList = iMastGenericDetailService.selectWorkPlace(param.getCustId(),param.getCompId(),param.getLang(),
                "TMG_WORKPLACE" ,param.getBaseDate());

    }


    /**
     * 機能：平均勤務時間編集_表示処理
     *
     *screenDispEditAvgWorktime
     */
    private void actionScreenDispEditAvgWorktime() throws Exception {


        // 平均勤務時間編集：社員情報：SELECT
        employeeDetailVo employeeDetailVo = iHistDesignationService.selectemployee(param.getCustId(),param.getCompId(),param.getTargetUser(),
                param.getLang(),param.getTargetSectionId());

        // 平均勤務時間編集：平均勤務時間：SELECT
        AvgWorkTimeVo avgWorkTimeVo=iTmgPaidHolidayAttributeService.selectAvgWorkTime60(param.getCustId(),param.getCompId(),param.getTargetUser(),param.getBaseDate());

        // 平均勤務時間編集：デフォルト平均勤務時間：SELECT
        AvgWorkTimeVo avgWorkTimeVoDefault=iTmgPaidHolidayAttributeService.selectDefaultAvgWorkTime(param.getCustId(),param.getCompId(),param.getTargetUser(),param.getBaseDate());

        // 平均勤務時間編集：平均勤務時間設定状況：SELECT
        AvgWorkTimeHistoryVo avgWorkTimeHistoryVo=iTmgPaidHolidayAttributeService.selectAvgWorkTimeHistory(param.getCustId(),param.getCompId(),param.getTargetUser());

        // 平均勤務時間編集：平均勤務時間上限：SELECT
        MgdTimeLimitVo mgdTimeLimitVo=iMastGenericDetailService.selectMgdTimeLimit();


    }


    /**
     * 機能：平均勤務時間編集_登録処理
     *
     * modifyAvgWorktime
     */
    private void actionModifyAvgWorktime() throws Exception {


        // 開始日が基準日以降のレコード削除(delete)

        // TMG_PAID_HOLIDAY_ATTRIBUTE削除用クエリを返す
        int deleteTmgPaidHolidayAttribute=iTmgPaidHolidayAttributeService.getBaseMapper().delete(SysUtil.<TmgPaidHolidayAttributeDO>query()
                .eq("TPHA_CCUSTOMERID",param.getCustId())
                .eq("TPHA_CCOMPANYID",param.getCompId())
                .eq("TPHA_CEMPLOYEEID",param.getTargetUser())
                .ge("TPHA_DSTARTDATE",param.getBaseDate()));


        // 基準日時点で適用されているデータの終了日を基準日-1で更新(update)
        int updPaidHolidayAttribute=updPaidHolidayAttribute();

        // 画面入力された値を登録(insert)
        int insertPaidHolidayAttribute=insertPaidHolidayAttribute();

        // トリガーテーブルにデータ削除
        int deleteTmgTriggerBef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID",param.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID", "EmpAttrSetting")
                .eq("TTR_CEMPLOYEEID", param.getTargetUser())
                .eq("TTR_CCUSTOMERID", param.getCustId())
                .eq("TTR_CCOMPANYID", param.getCompId()));

        // トリガーテーブルにデータ登録
        int insertTmgTrigger=insertTmgTrigger(param.getBaseDate());
        // トリガーテーブルのデータ削除
        int deleteTmgTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID",param.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID", "EmpAttrSetting")
                .eq("TTR_CEMPLOYEEID", param.getTargetUser())
                .eq("TTR_CCUSTOMERID", param.getCustId())
                .eq("TTR_CCOMPANYID", param.getCompId()));

    }


    /**
     * 機能：平均勤務時間編集_削除処理
     * deleteAvgWorktime
     * @throws Exception
     */
    private void actionDeleteAvgWorktime() throws Exception {

        // 開始日が基準日以降のレコード削除(delete)
        // TMG_PAID_HOLIDAY_ATTRIBUTE削除用クエリを返す
        int deleteTmgPaidHolidayAttribute=iTmgPaidHolidayAttributeService.getBaseMapper().delete(SysUtil.<TmgPaidHolidayAttributeDO>query()
                .eq("TPHA_CCUSTOMERID",param.getCustId())
                .eq("TPHA_CCOMPANYID",param.getCompId())
                .eq("TPHA_CEMPLOYEEID",param.getTargetUser())
                .ge("TPHA_DSTARTDATE",param.getBaseDate()));

        // 基準日時点で適用されているデータの終了日を基準日-1で更新(update)
        int updPaidHolidayAttribute=updPaidHolidayAttribute();

        // トリガーテーブルにデータ登録
        int insertTmgTrigger=insertTmgTrigger(param.getBaseDate());
        // トリガーテーブルのデータ削除
        int deleteTmgTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID",param.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID", "EmpAttrSetting")
                .eq("TTR_CEMPLOYEEID", param.getTargetUser())
                .eq("TTR_CCUSTOMERID", param.getCustId())
                .eq("TTR_CCOMPANYID", param.getCompId()));

    }


    /**
     * 機能：管理対象者一括編集_更新処理
     *modifyManageFlag
     */
    private void actionModifyManageFlag() throws Exception {
        // 管理対象者分の更新用クエリ取得
        List<UpdateCheckInfoDto> updateCheckInfoDtoManageList = new ArrayList<UpdateCheckInfoDto>();
        updateManageFlgEmployee(updateCheckInfoDtoManageList);

        // 超過勤務対象者分の更新用クエリ取得
        List<UpdateCheckInfoDto> updateCheckInfoDtoOverTimesList = new ArrayList<UpdateCheckInfoDto>();
        updateOverTimesEmployee(updateCheckInfoDtoOverTimesList);

        // 勤務先グループの更新用クエリ取得
        List<UpdateGroupInfoDto> updateGroupInfoDtoWorkPlaceList = new ArrayList<UpdateGroupInfoDto>();
        updateWorkPlace(updateGroupInfoDtoWorkPlaceList);
    }

    /**
     * 機能：勤務開始日編集_表示処理
     *
     * screenDispEditBeginDate
     * @throws Exception
     */
    private void actionScreenDispEditBeginDate() throws Exception {


        // 平均勤務時間編集：社員情報：SELECT
        employeeDetailVo employeeDetailVo = iHistDesignationService.selectemployee(param.getCustId(),param.getCompId(),param.getTargetUser(),
                param.getLang(),param.getTargetSectionId());

        // 勤務開始日設定状況：SELECT
        EmploymentWithMgdVo employmentWithMgdVo = iMastGenericDetailService.selectDateOfEmploymentWithMGD(param.getCustId(),param.getCompId()
        ,param.getLang(),param.getTargetUser(),TmgUtil.Cs_MG_TMG_DATEOFEMPLOYMENT);
        // 発令上の勤務開始日取得：SELECT
        EmployMentWithMEVo employMentWithMEVo=IMastEmployeesService.selectDateofemploymentWithME(param.getCustId(),param.getCompId(),param.getTargetUser());
        // 勤務開始日更新履歴取得：SELECT
        TmgDateOfEmpLogVo tmgDateOfEmpLogVo=iTmgDateofempLogService.selectTmgDateofempLog(param.getCustId(),param.getCompId(),param.getTargetUser(),param.getBaseDate());


    }


    /**
     * 機能：勤務開始日設定状況_新規登録処理
     *insertBeginDate
     */
    private void actionInsertBeginDate() throws Exception {

        // 勤務開始日を名称マスタに保存
        int insertMgd=iMastGenericDetailService.insertMgdKinmuStart(param.getCustId(),param.getCompId(),param.getTargetUser()
                                        ,param.getUserCode(),param.getBaseDate(),null,null,null);

        // 登録内容をログテーブルに保存
        int insertTmgDateofempLog =insertTmgDateofempLog(getModifiedMessage());

        // トリガーテーブルのデータ削除
        int deleteTriggerBef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID",param.getCustId())
                .eq("TTR_CCOMPANYID",param.getCompId())
                .eq("TTR_CEMPLOYEEID",param.getTargetUser())
                .eq("TTR_CMODIFIERUSERID",param.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID","EmpAttrSetting"));

        // トリガーテーブルにデータ登録
        int insertTrigger = insertTrigger(param.getPsStartDate());

        // トリガーテーブルのデータ削除
        int deleteTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID",param.getCustId())
                .eq("TTR_CCOMPANYID",param.getCompId())
                .eq("TTR_CEMPLOYEEID",param.getTargetUser())
                .eq("TTR_CMODIFIERUSERID",param.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID","EmpAttrSetting"));

    }


    /**
     * 機能：勤務開始日設定状況_更新処理
     *updateBeginDate
     * @throws Exception
     */
    private void actionUpdateBeginDate() throws Exception{

        // 名称マスタを更新
        int updateMgd = updateMgd();

        // 登録内容をログテーブルに保存
        int insertTmgDateofempLog =insertTmgDateofempLog(getModifiedMessage());

        // トリガーテーブルのデータ削除
        int deleteTriggerBef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID",param.getCustId())
                .eq("TTR_CCOMPANYID",param.getCompId())
                .eq("TTR_CEMPLOYEEID",param.getTargetUser())
                .eq("TTR_CMODIFIERUSERID",param.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID","EmpAttrSetting"));

        // トリガーテーブルにデータ登録
        int insertTrigger = insertTrigger(param.getPsMaxOldStartDate());

        // トリガーテーブルのデータ削除
        int deleteTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID",param.getCustId())
                .eq("TTR_CCOMPANYID",param.getCompId())
                .eq("TTR_CEMPLOYEEID",param.getTargetUser())
                .eq("TTR_CMODIFIERUSERID",param.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID","EmpAttrSetting"));



    }

    /**
     * 機能：勤務開始日設定状況_削除処理
     *deleteBeginDate
     */
    private void actionDeleteBeginDate() throws Exception {

        // 名称マスタから削除
        int deleteMgd=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("MGD_CCUSTOMERID",param.getCustId())
                .eq("MGD_CCOMPANYID_CK_FK",param.getCompId())
                .eq("MGD_CGENERICGROUPID","TMG_DATEOFEMPLOYMENT")
                .eq("MGD_CGENERICDETAILID_CK",param.getTargetUser())
                .eq("MGD_CLANGUAGE_CK",param.getLang())
                .eq("MGD_DSTART_CK",param.getPsOldStartDate()));

        // 登録内容をログテーブルに保存
        int insertTmgDateofempLog =insertTmgDateofempLog(getModifiedMessage());

        // トリガーテーブルのデータ削除
        int deleteTriggerBef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID",param.getCustId())
                .eq("TTR_CCOMPANYID",param.getCompId())
                .eq("TTR_CEMPLOYEEID",param.getTargetUser())
                .eq("TTR_CMODIFIERUSERID",param.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID","EmpAttrSetting"));

        // トリガーテーブルにデータ登録
        int insertTrigger = insertTrigger(param.getPsOldStartDate());

        // トリガーテーブルのデータ削除
        int deleteTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CCUSTOMERID",param.getCustId())
                .eq("TTR_CCOMPANYID",param.getCompId())
                .eq("TTR_CEMPLOYEEID",param.getTargetUser())
                .eq("TTR_CMODIFIERUSERID",param.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID","EmpAttrSetting"));

    }

    /**
     * 名称マスタを更新
     */
    private int updateMgd() {
        QueryWrapper<MastGenericDetailDO> queryWrapper= new QueryWrapper<MastGenericDetailDO>();
        queryWrapper.eq("MGD_CCUSTOMERID",param.getCustId());
        queryWrapper.eq("MGD_CCOMPANYID_CK_FK",param.getCompId());
        queryWrapper.eq("MGD_CGENERICGROUPID",TmgUtil.Cs_MG_TMG_DATEOFEMPLOYMENT);
        queryWrapper.eq("MGD_CGENERICDETAILID_CK",param.getTargetUser());
        queryWrapper.eq("MGD_CLANGUAGE_CK",param.getLang());
        queryWrapper.eq("MGD_DSTART_CK",param.getPsOldStartDate());

        long versionNo=iMastGenericDetailService.getBaseMapper().selectOne(queryWrapper).getVersionno();

        MastGenericDetailDO mgdDo = new MastGenericDetailDO();
        mgdDo.setMgdDstartCk(DateUtil.parse(param.getPsStartDate()));
        mgdDo.setMgdDend(DateUtil.parse(param.getPsEndDate()));
        mgdDo.setMgdCmodifieruserid(param.getUserCode());
        mgdDo.setMgdDmodifieddate(DateTime.now());
        mgdDo.setMgdDsparedate1(DateUtil.parse(param.getPsBeginDate()));
        mgdDo.setVersionno(versionNo+1);

        return iMastGenericDetailService.getBaseMapper().update(mgdDo,queryWrapper);
    }


    private int insertTrigger(String paramDate) {
        TmgTriggerDO ttDo=new TmgTriggerDO();
        ttDo.setTtrCcustomerid(param.getCustId());
        ttDo.setTtrCcompanyid(param.getCompId());
        ttDo.setTtrCemployeeid(param.getTargetUser());
        ttDo.setTtrDstartdate(TmgUtil.minDate);
        ttDo.setTtrDenddate(TmgUtil.maxDate);
        ttDo.setTtrCmodifieruserid(param.getUserCode());
        ttDo.setTtrDmodifieddate(DateTime.now());
        ttDo.setTtrCmodifierprogramid("EmpAttrSetting");
        ttDo.setTtrCprogramid("EmpAttrSetting");
        ttDo.setTtrDparameter1(DateUtil.parse(paramDate));
        return iTmgTriggerService.getBaseMapper().insert(ttDo);
    }

    private int insertTmgDateofempLog(String message) {
        TmgDateofempLogDO tddo = new TmgDateofempLogDO();
        tddo.setTdlgCcustomerid(param.getCustId());
        tddo.setTdlgCcompanyid(param.getCompId());
        tddo.setTdlgCemployeeid(param.getTargetUser());
        tddo.setTdlgDstartdate(TmgUtil.minDate);
        tddo.setTdlgDenddate(TmgUtil.maxDate);
        tddo.setTdlgCmodifieruserid(param.getUserCode());
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
    public String getModifiedMessage(){

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
        if (param.getAction().equalsIgnoreCase(ACTION_EDITBEGINDATE_C)) {
            sMessage =  WRD_BD_ADD + SPACE + WRD_BD_BEFORE_BRACKETS + param.getPsStartDate() + SPACE + WRD_BD_HYPHEN + SPACE +
                    param.getPsEndDate() + SPACE + WRD_BD_COLON + SPACE + param.getPsBeginDate() + WRD_BD_AFTER_BRACKETS;
        }
        // 更新時・・・更新 [(旧)適用開始日 - (旧)適用終了日:(旧)勤務開始日] → [適用開始日 - 適用終了日:勤務開始日]
        else if (param.getAction().equalsIgnoreCase(ACTION_EDITBEGINDATE_U)) {
            sMessage =  WRD_BD_UPDATE + SPACE + WRD_BD_BEFORE_BRACKETS + param.getPsOldStartDate() + SPACE + WRD_BD_HYPHEN + SPACE +
                    param.getPsEndDate() + SPACE + WRD_BD_COLON + SPACE + param.getPsOldBeginDate() + WRD_BD_AFTER_BRACKETS + SPACE +
                    WRD_BD_RIGHT_ARROW + SPACE + WRD_BD_BEFORE_BRACKETS + param.getPsStartDate() + SPACE + WRD_BD_HYPHEN + SPACE +
                    param.getPsEndDate() + SPACE + WRD_BD_COLON + SPACE + param.getPsBeginDate() + WRD_BD_AFTER_BRACKETS;
        }
        // 削除時・・・削除 [適用開始日 - 適用終了日:勤務開始日]
        else if (param.getAction().equalsIgnoreCase(ACTION_EDITBEGINDATE_D)) {
            sMessage =  WRD_BD_DELETE + SPACE + WRD_BD_BEFORE_BRACKETS + param.getPsOldStartDate() + SPACE + WRD_BD_HYPHEN + SPACE +
                    param.getPsEndDate() + SPACE + WRD_BD_COLON + SPACE + param.getPsBeginDate() + WRD_BD_AFTER_BRACKETS;
        }

        return sMessage;
    }


    /**
     * 勤務先グループ更新用のSQLをVectorオブジェクトに追加して返却します。
     * 追加しない場合はそのままVectorオブジェクトを返却します。
     *buildSQLForUpdateWorkPlace
     */
    private void updateWorkPlace(List<UpdateGroupInfoDto> updateGroupInfoDtoWorkPlaceList){
        String attribute=null;

        //管理対象者分のSQL
        for (UpdateGroupInfoDto ugiDto:updateGroupInfoDtoWorkPlaceList){
            if (!ugiDto.getInitGroup().equals(ugiDto.getUpdaeGroup())){
                attribute=getAttribute(!ugiDto.getUpdaeGroup().equals("") && ugiDto.getUpdaeGroup() != null);


                int deleteTmgEmployeeAttribute=iTmgEmployeeAttributeService.getBaseMapper().delete(SysUtil.<TmgEmployeeAttributeDO>query()
                        .eq("TPHA_CCUSTOMERID",param.getCustId())
                        .eq("TPHA_CCOMPANYID",param.getCompId())
                        .eq("TPHA_CEMPLOYEEID",ugiDto.getEmployeeId())
                        .likeRight("TES_CTYPE",TYPE_ITEM_WORKPLACE)
                        .ge("TPHA_DSTARTDATE",param.getBaseDate()));

                int updateTmgEmployeeAttribute=updateTmgEmployeeAttributeGroup(ugiDto.getEmployeeId(),TYPE_ITEM_WORKPLACE);

                // 値が入っている場合のみInsertする
                if (!ugiDto.getUpdaeGroup().equals("") && ugiDto.getUpdaeGroup() != null) {
                    int insertTmgEmployeeAttribute=insertTmgEmployeeAttribute(ugiDto.getEmployeeId(),TYPE_ITEM_WORKPLACE,attribute);
                }
            }
        }

    }

    /**
     * 超過勤務対象者分のSQLをVectorオブジェクトに追加して返却します。
     * 追加しない場合はそのままVectorオブジェクトを返却します。
     *buildSQLForUpdateOverTimesEmployee
     */
    private void updateOverTimesEmployee(List<UpdateCheckInfoDto> updateCheckInfoDtoOverTimesList){
        String attribute=null;

        //管理対象者分のSQL
        for (UpdateCheckInfoDto uciDto:updateCheckInfoDtoOverTimesList){
            // 変更確認
            // 変更されている場合
            if(uciDto.isInitCheckType()!=uciDto.isUpdaeCheckType()){
                attribute=getAttribute(uciDto.isUpdaeCheckType());
                // 管理対象者一括チェックが編集可の場合のみ、TMG_EMPLOYEE_ATTRIBUTEに対する処理を行うようにする。
                if("".equals(getEditTaisho())){
                    int deleteTmgEmployeeAttribute=iTmgEmployeeAttributeService.getBaseMapper().delete(SysUtil.<TmgEmployeeAttributeDO>query()
                            .eq("TPHA_CCUSTOMERID",param.getCustId())
                            .eq("TPHA_CCOMPANYID",param.getCompId())
                            .eq("TPHA_CEMPLOYEEID",uciDto.getEmployeeId())
                            .eq("TES_CTYPE",TYPE_ITEM_EXCLUDE_OVERTIME)
                            .ge("TPHA_DSTARTDATE",param.getBaseDate()));

                    int updateTmgEmployeeAttribute=updateTmgEmployeeAttribute(uciDto.getEmployeeId(),TYPE_ITEM_EXCLUDE_OVERTIME);

                    int insertTmgEmployeeAttribute=insertTmgEmployeeAttribute(uciDto.getEmployeeId(),TYPE_ITEM_EXCLUDE_OVERTIME,attribute);
                }
            }
        }

    }

    /**
     * 管理対象者分のSQL
     * buildSQLForUpdateManageFlgEmployee
     */
    private void updateManageFlgEmployee(List<UpdateCheckInfoDto> updateCheckInfoDtoManageList){
        String attribute=null;

        //管理対象者分のSQL
        for (UpdateCheckInfoDto uciDto:updateCheckInfoDtoManageList){
            // 変更確認
            // 変更されている場合
            if(uciDto.isInitCheckType()!=uciDto.isUpdaeCheckType()){
                attribute=getAttribute(uciDto.isUpdaeCheckType());
                // 管理対象者一括チェックが編集可の場合のみ、TMG_EMPLOYEE_ATTRIBUTEに対する処理を行うようにする。
                if("".equals(getEditTaisho())){
                    int deleteTmgEmployeeAttribute=iTmgEmployeeAttributeService.getBaseMapper().delete(SysUtil.<TmgEmployeeAttributeDO>query()
                            .eq("TPHA_CCUSTOMERID",param.getCustId())
                            .eq("TPHA_CCOMPANYID",param.getCompId())
                            .eq("TPHA_CEMPLOYEEID",uciDto.getEmployeeId())
                            .eq("TES_CTYPE",TYPE_ITEM_MANAGEFLG)
                            .ge("TPHA_DSTARTDATE",param.getBaseDate()));

                    int updateTmgEmployeeAttribute=updateTmgEmployeeAttribute(uciDto.getEmployeeId(),TYPE_ITEM_MANAGEFLG);

                    int insertTmgEmployeeAttribute=insertTmgEmployeeAttribute(uciDto.getEmployeeId(),TYPE_ITEM_MANAGEFLG,attribute);
                }
            }
        }
    }


    public String getAttribute(Boolean pbAttribute) {
        if (pbAttribute.equals(Boolean.TRUE)) {
            return TmgUtil.Cs_MGD_ONOFF_1;
        } else {
            return TmgUtil.Cs_MGD_ONOFF_0;
        }
    }

    /**
     *
     * 個人属性の指定タイプレコードの内、基準日時点で有効なレコードの終了日を基準日の前日で更新
     */
    private int updateTmgEmployeeAttribute(String empId,String psType){
        TmgEmployeeAttributeDO teaDo=new TmgEmployeeAttributeDO();
        teaDo.setTesCmodifieruserid(param.getUserCode());
        teaDo.setTesDmodifieddate(DateTime.now());
        teaDo.setTesCmodifierprogramid("EmpAttrSetting");
        teaDo.setTesDenddate(DateUtil.parse(param.getBaseDate()));

        QueryWrapper<TmgEmployeeAttributeDO> queryWrapper = new QueryWrapper<TmgEmployeeAttributeDO>();
        queryWrapper.eq("TES_CCUSTOMERID", param.getCustId());
        queryWrapper.eq("TES_CCOMPANYID", param.getCompId());
        queryWrapper.eq("TES_CEMPLOYEEID", empId);
        queryWrapper.lt("TES_DSTARTDATE", DateUtil.parse(param.getBaseDate()));
        queryWrapper.ge("TES_DENDDATE", DateUtil.parse(param.getBaseDate()));
        queryWrapper.eq("TES_CTYPE", psType);

        return iTmgEmployeeAttributeService.getBaseMapper().update(teaDo,queryWrapper);
    }

    private int insertTmgEmployeeAttribute(String empId,String psType,String Attribute){
        TmgEmployeeAttributeDO teaDo=new TmgEmployeeAttributeDO();
        teaDo.setTesCcustomerid(param.getCustId());
        teaDo.setTesCcompanyid(param.getCompId());
        teaDo.setTesCemployeeid(empId);
        teaDo.setTesDstartdate(DateUtil.parse(param.getBaseDate()));
        teaDo.setTesDenddate(TmgUtil.maxDate);
        teaDo.setTesCmodifieruserid(param.getUserCode());
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
    private int updateTmgEmployeeAttributeGroup(String empId,String psType){
        TmgEmployeeAttributeDO teaDo=new TmgEmployeeAttributeDO();
        teaDo.setTesCmodifieruserid(param.getUserCode());
        teaDo.setTesDmodifieddate(DateTime.now());
        teaDo.setTesCmodifierprogramid("EmpAttrSetting");
        teaDo.setTesDenddate(DateUtil.parse(param.getBaseDate()));

        QueryWrapper<TmgEmployeeAttributeDO> queryWrapper = new QueryWrapper<TmgEmployeeAttributeDO>();
        queryWrapper.eq("TES_CCUSTOMERID", param.getCustId());
        queryWrapper.eq("TES_CCOMPANYID", param.getCompId());
        queryWrapper.eq("TES_CEMPLOYEEID", empId);
        queryWrapper.lt("TES_DSTARTDATE", DateUtil.parse(param.getBaseDate()));
        queryWrapper.ge("TES_DENDDATE", DateUtil.parse(param.getBaseDate()));
        queryWrapper.likeRight("TES_CTYPE", psType);

        return iTmgEmployeeAttributeService.getBaseMapper().update(teaDo,queryWrapper);
    }

    private int insertTmgEmployeeAttributeGroup(String empId,String psType,String Attribute){
        TmgEmployeeAttributeDO teaDo=new TmgEmployeeAttributeDO();
        teaDo.setTesCcustomerid(param.getCustId());
        teaDo.setTesCcompanyid(param.getCompId());
        teaDo.setTesCemployeeid(empId);
        teaDo.setTesDstartdate(DateUtil.parse(param.getBaseDate()));
        teaDo.setTesDenddate(TmgUtil.maxDate);
        teaDo.setTesCmodifieruserid(param.getUserCode());
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
    public String getEditTaisho() {

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
    private int insertPaidHolidayAttribute() {

        TmgPaidHolidayAttributeDO tmgPaidHolidayAttributeDO = new TmgPaidHolidayAttributeDO();
        tmgPaidHolidayAttributeDO.setTphaCcustomerid(param.getCustId());
        tmgPaidHolidayAttributeDO.setTphaCcompanyid(param.getCompId());
        tmgPaidHolidayAttributeDO.setTphaCemployeeid(param.getTargetUser());

        tmgPaidHolidayAttributeDO.setTphaDstartdate(DateUtil.parse(param.getBaseDate()));
        tmgPaidHolidayAttributeDO.setTphaDenddate(TmgUtil.maxDate);

        tmgPaidHolidayAttributeDO.setTphaCmodifieruserid(param.getUserCode());
        tmgPaidHolidayAttributeDO.setTphaDmodifieddate(DateTime.now());
        tmgPaidHolidayAttributeDO.setTphaCmodifierprogramid("EmpAttrSetting");

        tmgPaidHolidayAttributeDO.setTphaNavgworktime(timeCalculator(param.getSAvgWorkingHours(),param.getSAvgWorkingMinutes()));
        tmgPaidHolidayAttributeDO.setTphaNworkingdaysWeek(Long.valueOf(param.getSWorkingdaysWeek()));

        tmgPaidHolidayAttributeDO.setTphaCworkingdaysWeek(iMastGenericDetailService.selectWeekDaysCom(
                param.getCustId(),param.getCompId(),param.getBaseDate(),Integer.valueOf(param.getSWorkingdaysWeek()),
                timeCalculator(param.getSAvgWorkingHours(),param.getSAvgWorkingMinutes()).intValue()
        ));

        return iTmgPaidHolidayAttributeService.getBaseMapper().insert(tmgPaidHolidayAttributeDO);

    }


    /**
     * トリガーテーブル(TMG_TRIGGER)登録用クエリを返す
     *
     */
    private int insertTmgTrigger(String psParmDate) {

        TmgTriggerDO ttDo = new TmgTriggerDO();

        ttDo.setTtrCcustomerid(param.getCustId());
        ttDo.setTtrCcompanyid(param.getCompId());
        ttDo.setTtrCemployeeid(param.getTargetUser());
        ttDo.setTtrDstartdate(TmgUtil.minDate);
        ttDo.setTtrDenddate(TmgUtil.maxDate);
        ttDo.setTtrCmodifieruserid(param.getUserCode());
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
    private int updPaidHolidayAttribute() {

        TmgPaidHolidayAttributeDO tmgPaidHolidayAttributeDO = new TmgPaidHolidayAttributeDO();
        tmgPaidHolidayAttributeDO.setTphaDenddate(DateUtil.offsetDay(DateUtil.parse(param.getBaseDate()), -1));
        tmgPaidHolidayAttributeDO.setTphaCmodifieruserid(param.getUserCode());
        tmgPaidHolidayAttributeDO.setTphaDmodifieddate(DateTime.now());
        tmgPaidHolidayAttributeDO.setTphaCmodifierprogramid("EmpAttrSetting");

        QueryWrapper<TmgPaidHolidayAttributeDO> queryWrapper = new QueryWrapper<TmgPaidHolidayAttributeDO>();
        queryWrapper.eq("TPHA_CCUSTOMERID", param.getCustId());
        queryWrapper.eq("TPHA_CCOMPANYID", param.getCompId());
        queryWrapper.eq("TPHA_CEMPLOYEEID", param.getTargetUser());
        queryWrapper.lt("TPHA_DSTARTDATE", DateUtil.parse(param.getBaseDate()));
        queryWrapper.ge("TPHA_DENDDATE", DateUtil.parse(param.getBaseDate()));

        return iTmgPaidHolidayAttributeService.getBaseMapper().update(tmgPaidHolidayAttributeDO, queryWrapper);


    }
}
