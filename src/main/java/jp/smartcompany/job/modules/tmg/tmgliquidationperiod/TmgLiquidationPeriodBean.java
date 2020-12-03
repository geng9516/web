package jp.smartcompany.job.modules.tmg.tmgliquidationperiod;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.*;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.*;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.EditDispVo;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.LiquidationDailyInfoVo;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.LiquidationDispVo;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public LiquidationDispVo getLiquidationDisp(String type, String searchText, PsDBBean psDBBean) {
        LiquidationDispVo vo = new LiquidationDispVo();
        List<LiquidationPeriodListDto> liquidationPeriodListDtos = iTmgliquidationPeriodService.getLiquidationDispFromType(psDBBean.getCustID(), psDBBean.getCompCode(), type, searchText);
        vo.setLiquidationPeriodList(liquidationPeriodListDtos);
        return vo;
    }

    // 数据获取 职种
    public LiquidationDispVo getLiquidationDisp(String workType, PsDBBean psDBBean) {
        LiquidationDispVo vo = new LiquidationDispVo();
        List<LiquidationPeriodListDto> liquidationPeriodListDtos = iTmgliquidationPeriodService.getLiquidationDispFromWorkType(psDBBean.getCustID(), psDBBean.getCompCode(), workType);
        vo.setLiquidationPeriodList(liquidationPeriodListDtos);
//        vo.setPageSize(50);
//        vo.setTotalCount(iTmgNotificationService.selectNotificationListCount(param));
//        vo.setTotalPage(notificationDispVo.getTotalCount()/50+1);
//        vo.setCurrPage(page);
        return vo;
    }


    //详细页面api
    public EditDispVo getEditDisop(String empId, String startDate, String endDate, PsDBBean psDBBean) throws Exception {
        EditDispVo editVo = new EditDispVo();
        //清算期间月列表获取
        List<String> monthlist = iTmgliquidationDailyService.getMonthList(empId, startDate, endDate);
        for (String yyyymm : monthlist) {
            //清算月数据获取
            List<LiquidationDailyDto> monthDtos = iTmgliquidationDailyService.getMonthInfo(empId, yyyymm);
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
        tlpDo.setTlpCavgworktime(!StrUtil.hasEmpty(updateDto.getAvgWorkTime())?Long.parseLong(updateDto.getAvgWorkTime()):(long)40);
        //最長働く一日労働時間上限
        tlpDo.setTlpCmaxdayhours(!StrUtil.hasEmpty(updateDto.getDailyMaxWorkTime())?Long.parseLong(updateDto.getDailyMaxWorkTime()):(long)600);
        //最長働く週間労働時間上限
        tlpDo.setTlpCmaxweekhours(!StrUtil.hasEmpty(updateDto.getWeeklyMaxWorkTime())?Long.parseLong(updateDto.getWeeklyMaxWorkTime()):(long)52);
        //一年間総労働日数
        tlpDo.setTlpCtotalworkdays(!StrUtil.hasEmpty(updateDto.getTotalWorkDays())?Long.parseLong(updateDto.getTotalWorkDays()):(long)280);
        //最長連続働く日数上限
        tlpDo.setTlpCmaxcontiday(!StrUtil.hasEmpty(updateDto.getMaxContiDays())?Long.parseLong(updateDto.getMaxContiDays()):(long)6);
        //最長連続働く週数上限
        tlpDo.setTlpCmaxcontiweek(!StrUtil.hasEmpty(updateDto.getMaxContiWeeks())?Long.parseLong(updateDto.getMaxContiWeeks()):(long)3);
        //週間労働時間上限超過可能回数
        tlpDo.setTlpCoverweekcount(!StrUtil.hasEmpty(updateDto.getOverContiWeeks())?Long.parseLong(updateDto.getOverContiWeeks()):(long)3);

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
                    if(tlpInfo.getTlpCmaxdayhours() != 0){
                        errMg=errMg.replace("@MAXDAYHOURS",minToHours("600"));
                    }else{
                        errMg=errMg.replace("@MAXDAYHOURS",minToHours(String.valueOf(tlpInfo.getTlpCmaxdayhours())));
                    }
                    break;
                //2	周check。每周最大工作小时52小时
                case "TMG_ERRCODE|LIQUIDATION_002" :
                    if(tlpInfo.getTlpCmaxweekhours() != 0 ){
                        errMg=errMg.replace("@MAXWEEKHOURS",minToHours("3120"));
                    }else{
                        errMg=errMg.replace("@MAXWEEKHOURS",minToHours(String.valueOf(tlpInfo.getTlpCmaxweekhours())));
                    }
                    break;
                //3	连续工作日数 不能超过6天
                case "TMG_ERRCODE|LIQUIDATION_003" :
                    if(tlpInfo.getTlpCmaxcontiday() != 0){
                        errMg=errMg.replace("@MAXCONTIDAY","6");
                    }else{
                        errMg=errMg.replace("@MAXCONTIDAY",String.valueOf(tlpInfo.getTlpCmaxcontiday()));
                    }
                    break;
                //4	不能连续3周超过48小时
                case "TMG_ERRCODE|LIQUIDATION_004" :
                    if(tlpInfo.getTlpCmaxcontiweek() != 0){
                        errMg=errMg.replace("@MAXCONTIWEEK","3");
                    }else{
                        errMg=errMg.replace("@MAXCONTIWEEK",String.valueOf(tlpInfo.getTlpCmaxcontiweek()));
                    }
                    break;
                //5	每3个月 ，不能存在 3周以上 最大工作时间超过48小时
                case "TMG_ERRCODE|LIQUIDATION_005" :
                    errMg=errMg.replace("@MONTH",DateUtil.format(errCheckDo.getTldcDyyyymm(),"yyyy年MM月"));
                    if(tlpInfo.getTlpCoverweekcount() != 0){
                        errMg=errMg.replace("@OVERWEEKCOUNT","3");
                    }else{
                        errMg=errMg.replace("@OVERWEEKCOUNT",String.valueOf(tlpInfo.getTlpCoverweekcount()));
                    }
                    break;
                //6	期间内最大工作日数		280* （日数/365）
                case "TMG_ERRCODE|LIQUIDATION_006" :
                    String maxDays;
                    if(tlpInfo.getTlpCtotalworkdays() != 0){
                        maxDays="280";
                    }else{
                        maxDays=String.valueOf(tlpInfo.getTlpCtotalworkdays());
                    }
                    String days=String.valueOf(DateUtil.betweenDay(tlpInfo.getTlpDstartdate(),tlpInfo.getTlpDenddate(),true));
                    days= String.valueOf(NumberUtil.round(NumberUtil.mul( NumberUtil.div(Double.valueOf(days),Double.valueOf("365"),4),Double.parseDouble(maxDays)),0));
                    errMg=errMg.replace("@TOTALWORKDAYS",days);
                    break;
                //7	期间内平均每周不能超过 周法定
                case "TMG_ERRCODE|LIQUIDATION_007" :
                    if(StrUtil.hasEmpty(String.valueOf(tlpInfo.getTlpCavgworktime()))){
                        errMg=errMg.replace("@AVGWORKTIME",minToHours("3"));
                    }else{
                        errMg=errMg.replace("@AVGWORKTIME",minToHours(String.valueOf(tlpInfo.getTlpCavgworktime())));
                    }
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
        List<SelectPatternDto> patternInfoVos=iTmgLiquidationPatternService.selectLiquidationPatternInfo( empId, yyyymm,psDBBean.getCustID(),psDBBean.getCompCode());
        List<PatternInfoDto> patternInfoDtoList=new ArrayList<PatternInfoDto>();

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
                patternInfoDto.setStartTime(selectPatternDto.getOpentime());
                patternInfoDto.setEndTime(selectPatternDto.getClosetime());
                PattternRestTime restTime=new PattternRestTime();
                restTime.setRestClose(selectPatternDto.getRestclose());
                restTime.setRestOpen(selectPatternDto.getRestopen());
                patternInfoDto.getRestTime().add(restTime);
                patternInfoDtoList.add(patternInfoDto);
            }
        }
        monthlyMap.put("workPatternList", patternInfoDtoList);
        return monthlyMap;
    }

    //月数据更新
    public int UpdateLiquidationDaily(MonthDto monthDto,String empid,String startDate,String endDate,PsDBBean psDBBean) {
        List<LiquidationDailyDto> monthList=monthDto.getMonthList();
        int updateNum=0;
        int errNum=0;
        for (LiquidationDailyDto dailyDto: monthList) {
            TmgLiquidationDailyDO tlddDo= new TmgLiquidationDailyDO();
            //定休日调整
            if(dailyDto.getKubunid().indexOf("TMG_HOLFLG")> -1){
                tlddDo.setTlddCsparechar3(dailyDto.getKubunid());
                tlddDo.setTlddCkubun(dailyDto.getKubunid());
                tlddDo.setTlddCsparechar1("TMG_DATASTATUS|3");
                tlddDo.setTlddCstarttime(null);
                tlddDo.setTlddCendtime(null);
                tlddDo.setTlddCreststarttime1(null);
                tlddDo.setTlddCrestendtime1(null);
                tlddDo.setTlddCreststarttime2(null);
                tlddDo.setTlddCrestendtime2(null);
            }
            //出勤或者休出 选用pattern
            if(dailyDto.getKubunid().indexOf("TMG_LIQUIDATION_PATTERN")> -1){
                tlddDo.setTlddCpattern(dailyDto.getKubunid());
                tlddDo.setTlddCsparechar1("TMG_DATASTATUS|3");
                tlddDo.setTlddCstarttime(dailyDto.getStarttime());
                tlddDo.setTlddCendtime(dailyDto.getEndtime());
                tlddDo.setTlddCreststarttime1(dailyDto.getReststarttime1());
                tlddDo.setTlddCrestendtime1(dailyDto.getRestendtime1());
                tlddDo.setTlddCreststarttime2(dailyDto.getReststarttime2());
                tlddDo.setTlddCrestendtime2(dailyDto.getRestendtime2());
            }
            //原值
//            if(dailyDto.getKubunid().indexOf("TMG_WORK")> -1){
//                break;
//            }
            tlddDo.setTlddCmodifieruserid(psDBBean.getUserCode());
            tlddDo.setTlddDmodifieddate(DateTime.now());
            //数据插入
            updateNum = updateNum+iTmgliquidationDailyService.getBaseMapper().update(tlddDo,
                    SysUtil.<TmgLiquidationDailyDO>query()
                            .eq("TLDD_CCUSTOMERID", psDBBean.getCustID())
                            .eq("TLDD_CCOMPANYID", psDBBean.getCompCode())
                            .eq("TLDD_DSTARTDATE", startDate)
                            .eq("TLDD_DENDDATE", endDate)
                            .eq("TLDD_CEMPLOYEEID", empid)
                            .eq("TLDD_DYYYYMMDD", dailyDto.getYyyymmdd()) );
        }
        if (updateNum>0){
            //check LiquidationDaily 执行
            errNum=checkLiquidationDaily(empid,monthList.get(0).getYyyymmdd(),psDBBean);
        }
        return  errNum;
    }

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
        tlpDo.setTpaNopen(timeToMin(patternInfoDto.getStartTime()));
        tlpDo.setTpaNclose(timeToMin(patternInfoDto.getEndTime()));
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
            tlprDo.setTprNrestopen(timeToMin(patternInfoDto.getRestTime().get(i).getRestOpen()));
            tlprDo.setTprNrestclose(timeToMin(patternInfoDto.getRestTime().get(i).getRestClose()));
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
    private long timeToMin(String time){
        if(StrUtil.hasEmpty(time) || time.indexOf(":") < 0){ return 0;}
        int hour =Integer.valueOf(time.split(":")[0]);
        int min =Integer.valueOf(time.split(":")[1]);
        if(hour == 0 && min == 0){
            return 0;
        }
        long mins= hour* 60 + min;
        return mins;
    }
}
