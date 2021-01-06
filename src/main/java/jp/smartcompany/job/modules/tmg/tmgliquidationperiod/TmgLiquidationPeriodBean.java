package jp.smartcompany.job.modules.tmg.tmgliquidationperiod;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.*;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.*;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.EditDispVo;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.LiquidationDailyInfoVo;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.LiquidationDispVo;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgSearchRangeUtil;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  清算期間設定bean
 *
 * @author Wang Ziyue
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgLiquidationPeriodBean {

    private final ITmgLiquidationPatternService iTmgLiquidationPatternService;
    private final ITmgLiquidationPatternRestService iTmgLiquidationPatternRestService;
    private final ITmgliquidationPeriodService iTmgliquidationPeriodService;
    private final ITmgliquidationDailyCheckService iTmgliquidationDailyCheckService;
    private final ITmgliquidationDailyService iTmgliquidationDailyService;
    private final IMastGenericDetailService iMastGenericDetailService;
    private final IMastOrganisationService iMastOrganisationService;
    private final TmgSearchRangeUtil tmgSearchRangeUtil;
    //private final TmgSearchRangeUtil tmgSearchRangeUtil = SpringUtil.getBean(TmgSearchRangeUtil.class);

    private final  ITmgNotificationService iTmgNotificationService;
    // 职种获取
    public List<WorkTypeGroupDto> getWorkTypeList(PsDBBean psDBBean) throws Exception {

        //变形
        List<WorkTypeDto> variational = iMastGenericDetailService.selectWorkerType4Variational(psDBBean.getCustID(),
                psDBBean.getCompCode(), TmgUtil.getSysdate());
        //flex
        List<WorkTypeDto> flex = iMastGenericDetailService.selectWorkerType4Flex(psDBBean.getCustID(),
                psDBBean.getCompCode(), TmgUtil.getSysdate());
        WorkTypeGroupDto variationalGroup = new WorkTypeGroupDto();
        WorkTypeGroupDto flexGroup = new WorkTypeGroupDto();
        variationalGroup.setChild(variational);
        variationalGroup.setWorktypeid("variational");
        variationalGroup.setWorktypename("変形");
        flexGroup.setChild(flex);
        flexGroup.setWorktypeid("flex");
        flexGroup.setWorktypename("フレックス");

        List<WorkTypeGroupDto> vos = new ArrayList<>();
        vos.add(variationalGroup);
        vos.add(flexGroup);
        return vos;
    }

    // 数据获取 搜索
    public LiquidationDispVo getLiquidationDisp(String type, String searchText, PsDBBean psDBBean,TmgReferList referList) {


        LiquidationDispVo vo = new LiquidationDispVo();
        List<LiquidationPeriodListDto> liquidationPeriodListDtos = iTmgliquidationPeriodService.getLiquidationDispFromType(psDBBean.getCustID(), psDBBean.getCompCode(), type, searchText);

        //承认site 特别处理
        if(psDBBean.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            List<JsonDto> emplist = JSONUtil.toList(referList.getJSONArrayForMemberList(),JsonDto.class);
            liquidationPeriodListDtos = liquidationPeriodListDtos.stream().filter(e -> {
                for (JsonDto jsonDto : emplist) {
                    if (jsonDto.getData().getEmpid().equals(e.getEmployeeid()) ) {
                        return true;
                    }
                }
                return true;
            }).collect(Collectors.toList());
        }

        vo.setLiquidationPeriodList(liquidationPeriodListDtos);
        return vo;
    }

    // 数据获取 职种
    public LiquidationDispVo getLiquidationDisp(String workType, PsDBBean psDBBean,TmgReferList referList) throws Exception {

        LiquidationDispVo vo = new LiquidationDispVo();
        List<LiquidationPeriodListDto> liquidationPeriodListDtos = iTmgliquidationPeriodService.getLiquidationDispFromWorkType(psDBBean.getCustID(), psDBBean.getCompCode(), workType);

        //承认site 特别处理
        if(psDBBean.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            List<JsonDto> emplist = JSONUtil.toList(referList.getJSONArrayForMemberList(),JsonDto.class);
            liquidationPeriodListDtos = liquidationPeriodListDtos.stream().filter(e -> {
                for (JsonDto jsonDto : emplist) {
                    if (jsonDto.getData().getEmpid().equals(e.getEmployeeid()) ) {
                        return true;
                    }
                }
                return true;
            }).collect(Collectors.toList());
        }
        vo.setLiquidationPeriodList(liquidationPeriodListDtos);
//        vo.setPageSize(50);
//        vo.setTotalCount(iTmgNotificationService.selectNotificationListCount(param));
//        vo.setTotalPage(notificationDispVo.getTotalCount()/50+1);
//        vo.setCurrPage(page);
        return vo;
    }


    //详细页面api
    public EditDispVo getEditDisp(String empId, String startDate, String endDate, PsDBBean psDBBean) throws Exception {
        EditDispVo editVo = new EditDispVo();
        //清算期间月列表获取
        List<String> monthlist = iTmgliquidationDailyService.getMonthList(empId, startDate, endDate);
        for (String yyyymm : monthlist) {
            //清算月数据获取
            List<LiquidationDailyDto> monthDtos = iTmgliquidationDailyService.getMonthInfo(empId, yyyymm,startDate,endDate);
            //清算日休憩数据获取
            for (int i=0 ;i<monthDtos.size();i++){
                if(!StrUtil.hasEmpty(monthDtos.get(i).getNtfstatus()) && monthDtos.get(i).getNtfstatus().equals("TMG_NTFSTATUS|5")){
                    List<String> ntfInfo= iTmgNotificationService.getSelectNtfInfo(monthDtos.get(i).getYyyymmdd(),empId,psDBBean.getCustID(),psDBBean.getCompCode());
                    monthDtos.get(i).setNtftype(ntfInfo);
                }
            }
            editVo.getMonthDtoList().add(monthDtos);
        }
        //月工作时间合计
        List<MonthSumTimeDto> monthSumTimeDtos= iTmgliquidationDailyService.getMonthSumTime(empId, startDate, endDate,psDBBean.getCustID(),psDBBean.getCompCode());
        editVo.setMonthTimeList(monthSumTimeDtos);
        //当前数据存在的errmsg获取
        List<TmgLiquidationDailyCheckDO> errList = iTmgliquidationDailyCheckService.getBaseMapper().selectList(
                SysUtil.<TmgLiquidationDailyCheckDO>query().eq("TLDC_CEMPLOYEEID", empId)
                        .eq("TLDC_DSTARTDATE", startDate)
                        .eq("TLDC_DENDDATE", endDate));
        TmgLiquidationPeriodDO tlpInfo = iTmgliquidationPeriodService.getBaseMapper().selectOne(
                SysUtil.<TmgLiquidationPeriodDO>query().eq("TLP_CEMPLOYEEID", empId)
                        .eq("TLP_DSTARTDATE", startDate)
                        .eq("TLP_DENDDATE", endDate));
        editVo.setErrList(LiquidationCheck(errList,tlpInfo));
        return editVo;
    }


    //新規精算期間
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse insertLiquidation(LiquidationUpdateListDto updateDto, PsDBBean psDBBean) {
        //sequence 获取
        String seq = iTmgliquidationPeriodService.getSeq();
        //一览数据新规
        insertTlp(seq, updateDto, psDBBean);
        //详细数据新规
        insertTlDD(updateDto.getEmpId(), updateDto.getCurDateFrom(), updateDto.getCurDateTo(), psDBBean);

        checkLiquidationDaily(updateDto.getEmpId(),updateDto.getCurDateFrom(),psDBBean);

        return GlobalResponse.ok();
    }

    @Transactional(rollbackFor = GlobalException.class)
    //清算期间删除
    public GlobalResponse deleteLiquidation(String empId, String startDate, String endDate) {
        iTmgliquidationDailyService.getBaseMapper().delete(SysUtil.<TmgLiquidationDailyDO>query().eq("TLDD_CEMPLOYEEID", empId)
                .eq("TLDD_DSTARTDATE", startDate)
                .eq("TLDD_DENDDATE", endDate));
        iTmgliquidationPeriodService.getBaseMapper().delete(SysUtil.<TmgLiquidationPeriodDO>query().eq("TLP_CEMPLOYEEID", empId)
                .eq("TLP_DSTARTDATE", startDate)
                .eq("TLP_DENDDATE", endDate));
        iTmgliquidationDailyCheckService.getBaseMapper().delete(SysUtil.<TmgLiquidationDailyCheckDO>query().eq("TLDC_CEMPLOYEEID", empId)
                .eq("TLDC_DSTARTDATE", startDate)
                .eq("TLDC_DENDDATE", endDate));
        return GlobalResponse.ok();
    }

    //一览数据新规
    private int insertTlp(String seq,LiquidationUpdateListDto updateDto, PsDBBean psDBBean) {
        TmgLiquidationPeriodDO tlpDo = new TmgLiquidationPeriodDO();
        tlpDo.setTlpCcompanyid(psDBBean.getCompCode());
        tlpDo.setTlpCcustomerid(psDBBean.getCustID());
        tlpDo.setTlpCemployeeid(updateDto.getEmpId());
        tlpDo.setTlpCmodifieruserid(psDBBean.getUserCode());
        tlpDo.setTlpDmodifieddate(DateTime.now());
        tlpDo.setTlpDstartdate(DateUtil.parseDate(updateDto.getCurDateFrom()));
        tlpDo.setTlpDenddate(DateUtil.parseDate(updateDto.getCurDateTo()));
        tlpDo.setTlpCworktypeid(updateDto.getWorkTypeId());

        //週平均勤務時間数
        tlpDo.setTlpCavgworktime(!StrUtil.hasEmpty(updateDto.getAvgWorkTime())?Long.parseLong(updateDto.getAvgWorkTime()):(long)2400);
        //最長働く一日労働時間上限
        tlpDo.setTlpCmaxdayhours(!StrUtil.hasEmpty(updateDto.getDailyMaxWorkTime())?Long.parseLong(updateDto.getDailyMaxWorkTime()):(long)600);
        //最長働く週間労働時間上限
        tlpDo.setTlpCmaxweekhours(!StrUtil.hasEmpty(updateDto.getWeeklyMaxWorkTime())?Long.parseLong(updateDto.getWeeklyMaxWorkTime()):(long)3120);
        //一年間総労働日数
        tlpDo.setTlpCtotalworkdays(!StrUtil.hasEmpty(updateDto.getTotalWorkDays())?Long.parseLong(updateDto.getTotalWorkDays()):(long)280);
        //最長連続働く日数上限
        tlpDo.setTlpCmaxcontiday(!StrUtil.hasEmpty(updateDto.getMaxContiDays())?Long.parseLong(updateDto.getMaxContiDays()):(long)6);
        //最長連続働く週数上限
        tlpDo.setTlpCmaxcontiweek(!StrUtil.hasEmpty(updateDto.getMaxContiWeeks())?Long.parseLong(updateDto.getMaxContiWeeks()):(long)3);
        //週間労働時間上限超過可能回数
        tlpDo.setTlpCoverweekcount(!StrUtil.hasEmpty(updateDto.getOverContiWeeks())?Long.parseLong(updateDto.getOverContiWeeks()):(long)3);
        //週間労働時間上限超過可能回数
        tlpDo.setTlpNsparenum1(!StrUtil.hasEmpty(updateDto.getBeginOfWeek()) ?Long.parseLong(updateDto.getBeginOfWeek()):null);

        tlpDo.setTlpCtlpid(seq);
        return iTmgliquidationPeriodService.getBaseMapper().insert(tlpDo);
    }

    //详细数据新规
    private void insertTlDD(String empId, String startDate, String endDate, PsDBBean psDBBean) {
        iTmgliquidationDailyService.execTLDDInsert(empId, startDate, endDate, psDBBean.getUserCode(), psDBBean.getCustID(), psDBBean.getCompCode());
    }

    //errmsg 生成
    private List<String> LiquidationCheck(List<TmgLiquidationDailyCheckDO> errDo,TmgLiquidationPeriodDO tlpInfo) {
        List<String> errList = new ArrayList<>();
        for (TmgLiquidationDailyCheckDO errCheckDo : errDo) {
            String errMg = null;
            errMg=errCheckDo.getTldcCerrmsg().replace("@DAY",DateUtil.format(errCheckDo.getTldcDyyyymmdd(),"yyyy/MM/dd"));
            switch(errCheckDo.getTldcCerrcode()){
                //1 日check。每天最大工作时间10小时
                case "TMG_ERRCODE|LIQUIDATION_001" :
                    errMg=errMg.replace("@MAXDAYHOURS",minToHours(String.valueOf(tlpInfo.getTlpCmaxdayhours())));
                    break;
                //2	周check。每周最大工作小时52小时
                case "TMG_ERRCODE|LIQUIDATION_002" :

                    errMg=errMg.replace("@MAXWEEKHOURS",minToHours(String.valueOf(tlpInfo.getTlpCmaxweekhours())));
                    break;
                //3	连续工作日数 不能超过6天
                case "TMG_ERRCODE|LIQUIDATION_003" :

                    errMg=errMg.replace("@MAXCONTIDAY",String.valueOf(tlpInfo.getTlpCmaxcontiday()));

                    break;
                //4	不能连续3周超过48小时
                case "TMG_ERRCODE|LIQUIDATION_004" :

                    errMg=errMg.replace("@MAXCONTIWEEK",String.valueOf(tlpInfo.getTlpCmaxcontiweek()));

                    break;
                //5	每3个月 ，不能存在 3周以上 最大工作时间超过48小时
                case "TMG_ERRCODE|LIQUIDATION_005" :
                    errMg=errMg.replace("@MONTH",DateUtil.format(errCheckDo.getTldcDyyyymm(),"yyyy年MM月"));
                    errMg=errMg.replace("@OVERWEEKCOUNT",String.valueOf(tlpInfo.getTlpCoverweekcount()));

                    break;
                //6	期间内最大工作日数		280* （日数/365）
                case "TMG_ERRCODE|LIQUIDATION_006" :
                    String maxDays=String.valueOf(tlpInfo.getTlpCtotalworkdays());
                    String days=String.valueOf(DateUtil.betweenDay(tlpInfo.getTlpDstartdate(),tlpInfo.getTlpDenddate(),true));
                    days= String.valueOf(NumberUtil.round(NumberUtil.mul( NumberUtil.div(Double.valueOf(days),Double.valueOf("365"),4),Double.parseDouble(maxDays)),0));
                    errMg=errMg.replace("@TOTALWORKDAYS",days);
                    break;
                //7	期间内平均每周不能超过 周法定
                case "TMG_ERRCODE|LIQUIDATION_007" :

                    errMg=errMg.replace("@AVGWORKTIME",minToHours(String.valueOf(tlpInfo.getTlpCavgworktime())));

                    break;
            }
            if(!StrUtil.hasEmpty(errMg)){
                errList.add(errMg);
            }
        }
        return errList;
    }

    //分钟转小时
    private String minToHours(String min){
        String hours=(Integer.valueOf(min)/60)+"."+(Integer.valueOf(min)%60);
        return hours;
    }

    //月編集画面表示
    public Map<String, Object> EditMonthDisp(String empId, String yyyymm, PsDBBean psDBBean) {
        Map<String, Object> monthlyMap = MapUtil.newHashMap();
        //月data
        List<LiquidationDailyInfoVo> liquidationDailyInfoVoList = iTmgliquidationDailyService.selectDailyInfo(psDBBean.getCustID(),psDBBean.getCompCode(),empId,yyyymm);
        for (int i=0;i<liquidationDailyInfoVoList.size();i++) {

            if(!StrUtil.hasEmpty(liquidationDailyInfoVoList.get(i).getNtfStatus()) && liquidationDailyInfoVoList.get(i).getNtfStatus().equals("TMG_NTFSTATUS|5")){
                List<String> ntfInfo= iTmgNotificationService.getSelectNtfInfo(liquidationDailyInfoVoList.get(i).getDays(),empId,psDBBean.getCustID(),psDBBean.getCompCode());
                liquidationDailyInfoVoList.get(i).setComment(ntfInfo.stream().filter(ntf-> ntf!=null).collect(Collectors.joining("\\n")));
            }

            if(!StrUtil.hasEmpty(liquidationDailyInfoVoList.get(i).getNtfStatus()) && liquidationDailyInfoVoList.get(i).getNtfStatus().equals("TMG_NTFSTAUTS|5")){
                List<String> ntfList=iTmgNotificationService.getSelectNtfInfo(liquidationDailyInfoVoList.get(i).getDays(),empId,psDBBean.getCustID(),psDBBean.getCompCode());
                liquidationDailyInfoVoList.get(i).setComment(ntfList.stream().filter(string ->!ntfList.isEmpty()).collect(Collectors.joining("\\")));
            }
            if(liquidationDailyInfoVoList.get(i).getStatus().equals("TMG_DATASTATUS|9")){
                //確定状態变灰
                liquidationDailyInfoVoList.get(i).setDisabled(true);
                continue;
            }else if(!StrUtil.hasEmpty(liquidationDailyInfoVoList.get(i).getNtfStatus()) && liquidationDailyInfoVoList.get(i).getNtfStatus().equals("TMG_NTFSTATUS|5") &&
                    !StrUtil.hasEmpty(liquidationDailyInfoVoList.get(i).getNtftype())&&liquidationDailyInfoVoList.get(i).getNtftype().indexOf("TMG_WORK")>-1){
                //全休の時に、編集できません（時間休暇の時に、編集できます）
                liquidationDailyInfoVoList.get(i).setDisabled(true);
                continue;
            }else if(!StrUtil.hasEmpty(liquidationDailyInfoVoList.get(i).getKubunid())&& (liquidationDailyInfoVoList.get(i).getKubunid().equals("TMG_WORK|451") ||
                    liquidationDailyInfoVoList.get(i).getKubunid().equals("TMG_WORK|452") )){
                //振替休日（法定）及び　振替休日（法定外）の場合、編集できません
                liquidationDailyInfoVoList.get(i).setDisabled(true);
                continue;
            }else{
                liquidationDailyInfoVoList.get(i).setDisabled(false);
            }
        }

        monthlyMap.put("liquidationDailyInfoVoList", liquidationDailyInfoVoList);
        //pattern list（选取用）

        monthlyMap.put("workPatternList", getPatternList(empId, yyyymm,psDBBean.getCustID(),psDBBean.getCompCode()));
        return monthlyMap;
    }



    //pattern 处理
    public List<PatternInfoDto> getPatternList(String empId ,String yyyymm,String custId,String compCode){
        List<SelectPatternDto> patternInfoVos=iTmgLiquidationPatternService.selectLiquidationPatternInfo( empId , yyyymm, custId, compCode);
        List<PatternInfoDto> patternInfoDtoList=new ArrayList<>();
        for (SelectPatternDto selectPatternDto:patternInfoVos){
            boolean exist=false;
            for(int i=0 ;i<patternInfoDtoList.size();i++){
                if(patternInfoDtoList.get(i).getPatternId().equals(selectPatternDto.getPatternid())){
                    if(selectPatternDto.getSeq()>1){
                        exist=true;
                        PattternRestTime restTime=new PattternRestTime();
                        restTime.setRestClose(selectPatternDto.getRestclose());
                        restTime.setRestOpen(selectPatternDto.getRestopen());
                        patternInfoDtoList.get(i).getRestTime().add(restTime);
                    }
                }
            }
            if(!exist){
                PatternInfoDto patternInfoDto=new PatternInfoDto();
                patternInfoDto.setPatternId(selectPatternDto.getPatternid());
                patternInfoDto.setPatternName(selectPatternDto.getPatternname());
                patternInfoDto.setTarget(selectPatternDto.getTarget());
                patternInfoDto.setStartTime(selectPatternDto.getOpentime());
                patternInfoDto.setEndTime(selectPatternDto.getClosetime());
                PattternRestTime restTime=new PattternRestTime();
                restTime.setRestClose(selectPatternDto.getRestclose());
                restTime.setRestOpen(selectPatternDto.getRestopen());
                patternInfoDto.getRestTime().add(restTime);
                patternInfoDto.setModifierdDate(selectPatternDto.getModifierddate());
                patternInfoDto.setModifierUser(selectPatternDto.getModifieruser());
                patternInfoDtoList.add(patternInfoDto);
            }
        }
        return patternInfoDtoList;
    }

    //删除pattern
    public GlobalResponse deletePattern(String patternId,PsDBBean psDBBean){
        iTmgLiquidationPatternService.getBaseMapper().delete(
                SysUtil.<TmgLiquidationPatternDO>query()
                        .eq("TPA_CCUSTOMERID", psDBBean.getCustID())
                        .eq("TPA_CCOMPANYID", psDBBean.getCompCode())
                        .eq("TPA_CPATTERNID", patternId));

        iTmgLiquidationPatternRestService.getBaseMapper().delete(
                SysUtil.<TmgLiquidationPatternRestDO>query()
                        .eq("TPR_CCUSTOMERID", psDBBean.getCustID())
                        .eq("TPR_CCOMPANYID", psDBBean.getCompCode())
                        .eq("TPR_CPATTERNID", patternId));
        return GlobalResponse.ok();
    }



    //月数据更新
    public int UpdateLiquidationDaily(MonthDto monthDto,String empid,String startDate,String endDate,PsDBBean psDBBean) throws ParseException {
        List<LqdDto> monthList=monthDto.getMonthList();
        int updateNum=0;
        int errNum=0;
        for (LqdDto dailyDto: monthList) {
            TmgLiquidationDailyDO tlddDo= new TmgLiquidationDailyDO();
            //定休日调整
            if(dailyDto.getKubunid().indexOf("TMG_HOLFLG")> -1){
                //holflg column
                tlddDo.setTlddCsparechar3(dailyDto.getKubunid());
                tlddDo.setTlddCkubun(iMastGenericDetailService.getHolFlg(dailyDto.getKubunid()));
                //日承認状態
                tlddDo.setTlddCsparechar1("TMG_DATASTATUS|3");

                tlddDo.setTlddCpattern("");
                tlddDo.setTlddCstarttime("");
                tlddDo.setTlddCendtime("");
                tlddDo.setTlddCworkhours("");
                tlddDo.setTlddCreststarttime1("");
                tlddDo.setTlddCrestendtime1("");
                tlddDo.setTlddCreststarttime2("");
                tlddDo.setTlddCrestendtime2("");
                tlddDo.setTlddCreststarttime3("");
                tlddDo.setTlddCrestendtime3("");
                tlddDo.setTlddCreststarttime4("");
                tlddDo.setTlddCrestendtime4("");
            }
            //出勤或者休出 选用pattern
            if(dailyDto.getKubunid().indexOf("TMG_LIQUIDATION_PATTERN")> -1){
                tlddDo.setTlddCpattern(dailyDto.getKubunid());
                tlddDo.setTlddCkubun("TMG_WORK|000");
                tlddDo.setTlddCsparechar1("TMG_DATASTATUS|3");
                tlddDo.setTlddCstarttime(timeToMin(dailyDto.getStarttime()));
                tlddDo.setTlddCendtime(timeToMin(dailyDto.getEndtime()));
                tlddDo.setTlddCworkhours(dailyDto.getWorkhours());
                tlddDo.setTlddCreststarttime1(timeToMin(dailyDto.getReststarttime1()));
                tlddDo.setTlddCrestendtime1(timeToMin(dailyDto.getRestendtime1()));
                tlddDo.setTlddCreststarttime2(timeToMin(dailyDto.getReststarttime2()));
                tlddDo.setTlddCrestendtime2(timeToMin(dailyDto.getRestendtime2()));
                tlddDo.setTlddCreststarttime3(timeToMin(dailyDto.getReststarttime3()));
                tlddDo.setTlddCrestendtime3(timeToMin(dailyDto.getRestendtime3()));
                tlddDo.setTlddCreststarttime4(timeToMin(dailyDto.getReststarttime4()));
                tlddDo.setTlddCrestendtime4(timeToMin(dailyDto.getRestendtime4()));
            }
            //原值
//            if(dailyDto.getKubunid().indexOf("TMG_WORK")> -1){
//                break;
//            }
            tlddDo.setTlddCmodifieruserid(psDBBean.getUserCode());
            tlddDo.setTlddDmodifieddate(DateTime.now());
            //数据插入
            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");//注意月份是MM
            //Date date = simpleDateFormat.parse(dailyDto.getDays());
            updateNum = updateNum+iTmgliquidationDailyService.getBaseMapper().update(tlddDo,
                    SysUtil.<TmgLiquidationDailyDO>query()
                            .eq("TLDD_CCUSTOMERID", psDBBean.getCustID())
                            .eq("TLDD_CCOMPANYID", psDBBean.getCompCode())
                            .eq("TLDD_DSTARTDATE", startDate)
                            .eq("TLDD_DENDDATE", endDate)
                            .eq("TLDD_CEMPLOYEEID", empid)
                            .eq("TLDD_DYYYYMMDD", dailyDto.getDays()) );
        }
        if (updateNum>0){
            //check LiquidationDaily 执行
            errNum=checkLiquidationDaily(empid,monthList.get(0).getDays(),psDBBean);
        }
        return  errNum;
    }


    //check所有数据
    public int checkLiquidationDaily(String empId, String yyyymm, PsDBBean psDBBean){
        return iTmgliquidationDailyService.checkLiquidationDaily(psDBBean.getCustID(),psDBBean.getCompCode(),empId,yyyymm);
    }

    //新规pattern
    public GlobalResponse insertPattern(PatternInfoDto patternInfoDto, PsDBBean psDBBean){
        //新规TmgLiquidationPattern
        String seq = iTmgLiquidationPatternService.selectSeq();

        TmgLiquidationPatternDO tlpDo=new TmgLiquidationPatternDO();
        tlpDo.setTpaCcompanyid(psDBBean.getCompCode());
        tlpDo.setTpaCcustomerid(psDBBean.getCustID());
        tlpDo.setTpaDstartdate(TmgUtil.minDate);
        tlpDo.setTpaDenddate(TmgUtil.maxDate);
        tlpDo.setTpaCmodifieruserid(psDBBean.getUserCode());
        tlpDo.setTpaDmodifieddate(DateTime.now());

        tlpDo.setTpaCemployeeid(patternInfoDto.getEmpId());
        tlpDo.setTpaCsectionid(patternInfoDto.getSectionId());
        tlpDo.setTpaCworktypeid(patternInfoDto.getWorktypeId());

        tlpDo.setTpaCpatternid("TMG_LIQUIDATION_PATTERN|"+seq);
        tlpDo.setTpaCpatternname(patternInfoDto.getPatternName());
        tlpDo.setTpaNopen(Long.parseLong(timeToMin(patternInfoDto.getStartTime())));
        tlpDo.setTpaNclose(Long.parseLong(timeToMin(patternInfoDto.getEndTime())));
        iTmgLiquidationPatternService.getBaseMapper().insert(tlpDo);

        //新规TmgLiquidationPatternRest
        for (int i=0;i<patternInfoDto.getRestTime().size();i++) {
            if (StrUtil.hasEmpty(patternInfoDto.getRestTime().get(i).getRestOpen())){break;}
            TmgLiquidationPatternRestDO tlprDo=new TmgLiquidationPatternRestDO();
            tlprDo.setTprCcompanyid(psDBBean.getCompCode());
            tlprDo.setTprCcustomerid(psDBBean.getCustID());
            tlprDo.setTprCmodifieruserid(psDBBean.getUserCode());
            tlprDo.setTprDmodifieddate(DateTime.now());
            tlprDo.setTprDstartdate(TmgUtil.minDate);
            tlprDo.setTprDenddate(TmgUtil.maxDate);

            tlprDo.setTprCpatternid("TMG_LIQUIDATION_PATTERN|"+seq);
            tlprDo.setTprNrestopen(Long.parseLong(timeToMin(patternInfoDto.getRestTime().get(i).getRestOpen())));
            tlprDo.setTprNrestclose(Long.parseLong(timeToMin(patternInfoDto.getRestTime().get(i).getRestClose())));
            tlprDo.setTprSeq((long)i+1);

            iTmgLiquidationPatternRestService.getBaseMapper().insert(tlprDo);
        }
        return GlobalResponse.ok();
    }


    //最後登録
    public GlobalResponse upload(String empId,String startDate, String endDate,PsDBBean psDBBean){
        //procedure 执行
        iTmgliquidationDailyService.execTDAInsert(empId, startDate, endDate, psDBBean.getUserCode(), psDBBean.getCustID(), psDBBean.getCompCode());
        // procedure 执行时 err发生有无
        TmgLiquidationPeriodDO tlpDo= iTmgliquidationPeriodService.getBaseMapper().selectOne(SysUtil.<TmgLiquidationPeriodDO>query()
                .eq("TLP_CCUSTOMERID", psDBBean.getCustID())
                .eq("TLP_CCOMPANYID", psDBBean.getCompCode())
                .eq("TLP_DSTARTDATE", startDate)
                .eq("TLP_DENDDATE", endDate)
                .eq("TLP_CEMPLOYEEID", empId));
        String stutas=tlpDo.getTlpCsparechar1();
        if (!StrUtil.hasEmpty(stutas) && stutas.equals("TLD_STATUS|3")){
            return GlobalResponse.ok("error");
        }else{
            return GlobalResponse.ok();
        }
    }

    //时间转分钟
    private String  timeToMin(String time){
        if(StrUtil.hasEmpty(time) || time.indexOf(":") < 0){ return "";}
        int hour =Integer.valueOf(time.split(":")[0]);
        int min =Integer.valueOf(time.split(":")[1]);
        if(hour == 0 && min == 0){
            return "";
        }
        String mins= String.valueOf(hour* 60 + min);
        return mins;
    }



    public List<Map<String,String>> getSectionList(PsDBBean psDBBean){
        List<Map<String,String>> sectionList=new ArrayList<>();
        String sql = getDivTreeSearchRange(psDBBean, ContextUtil.getSession());
        if(!StrUtil.isBlank(sql)){
            sectionList =iMastOrganisationService.getSearchRangeSection(psDBBean.getCustID(),psDBBean.getCompCode(),sql);
        }
        return sectionList;

    }
    /**
     * 検索対象範囲条件の取得(職員に対する検索対象範囲とは別に分ける。Treeでは上位所属を利用するが社員リストでは出てはいけないため)
     * @param psDBBean
     * @param session
     * @return
     */
    public String getDivTreeSearchRange(PsDBBean psDBBean, HttpSession session) {
        try {
            return  tmgSearchRangeUtil.getExistsQueryBaseSection(psDBBean,session,"o.MO_CSECTIONID_CK");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
