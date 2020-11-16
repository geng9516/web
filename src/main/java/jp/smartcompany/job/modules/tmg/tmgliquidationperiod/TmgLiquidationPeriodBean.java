package jp.smartcompany.job.modules.tmg.tmgliquidationperiod;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
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

    private final ITmgEmployeesService iTmgEmployeesService;
    private final ITmgliquidationPeriodService iTmgliquidationPeriodService;
    private final ITmgliquidationDetailService iTmgliquidationDetailService;
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
            LiquidationMonthDto dto = new LiquidationMonthDto();
            //清算月数据获取
            List<LiquidationDailyDto> monthDtos = iTmgliquidationDailyService.getMonthInfo(empId, yyyymm);
            dto.setMonthInfo(monthDtos);
            editVo.getMonthDtoList().add(dto);
        }
        //月工作时间合计
        List<MonthSumTimeDto> monthSumTimeDtos= iTmgliquidationDailyService.getMonthSumTime(empId, startDate, endDate,psDBBean.getCustID(),psDBBean.getCompCode());
        editVo.setMonthTimeList(monthSumTimeDtos);
        //当前数据存在的errmsg获取
        List<TmgLiquidationDailyCheckDO> errList = iTmgliquidationDailyCheckService.getBaseMapper().selectList(
                SysUtil.<TmgLiquidationDailyCheckDO>query().eq("TLDC_CEMPLOYEEID", empId)
                        .eq("TLDC_DSTARTDATE", startDate)
                        .eq("TLDC_DENDDATE", endDate));
        editVo.setErrList(LiquidationCheck(errList));
        return editVo;
    }


    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse insertLiquidation(LiquidationUpdateListDto updateDto, PsDBBean psDBBean) {
        //sequence 获取
        String seq = iTmgliquidationPeriodService.getSeq();
        //一览数据新规
        insertTlp(seq, updateDto.getEmpId(), updateDto.getWorktypeId(), updateDto.getStartDate(), updateDto.getEndDate(), psDBBean);
        //详细数据新规
        insertTlDD(updateDto.getEmpId(), updateDto.getStartDate(), updateDto.getEndDate(), psDBBean);
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
    private int insertTlp(String seq, String empId, String workTypeId, String startDate, String endDate, PsDBBean psDBBean) {
        TmgLiquidationPeriodDO tlpDo = new TmgLiquidationPeriodDO();
        tlpDo.setTlpCcompanyid(psDBBean.getCompCode());
        tlpDo.setTlpCcustomerid(psDBBean.getCustID());
        tlpDo.setTlpCemployeeid(empId);
        tlpDo.setTlpCmodifieruserid(psDBBean.getUserCode());
        tlpDo.setTlpDmodifieddate(DateTime.now());
        tlpDo.setTlpDstartdate(DateUtil.parseDate(startDate));
        tlpDo.setTlpDenddate(DateUtil.parseDate(endDate));
        tlpDo.setTlpCworktypeid(workTypeId);
        tlpDo.setTlpCtlpid(seq);
        return iTmgliquidationPeriodService.getBaseMapper().insert(tlpDo);
    }

    //详细数据新规
    private void insertTlDD(String empId, String startDate, String endDate, PsDBBean psDBBean) {
        iTmgliquidationDailyService.execTLDDInsert(empId, startDate, endDate, psDBBean.getUserCode(), psDBBean.getCustID(), psDBBean.getCompCode());
    }

    //errmsg 生成
    private List<String> LiquidationCheck(List<TmgLiquidationDailyCheckDO> errDo) {
        List<String> errList = new ArrayList<>();

        for (TmgLiquidationDailyCheckDO errCheckDo : errDo) {
            String errMsg;
            errList.add(errCheckDo.getTldcCerrmsg());
            //1 日check。每天最大工作时间10小时

            //2	周check。每周最大工作小时52小时

            //3	连续工作日数 不能超过6天

            //4	不能连续3周超过48小时

            //5	每3个月 ，不能存在 3周以上 最大工作时间超过48小时

            //6	期间内最大工作日数		280* （日数/365）

            //7	期间内平均每周不能超过 周法定
        }


        return errList;
    }

    //月数据更新
    public int UpdateLiquidationDaily(List<LiquidationDailyDto> monthList,String empid,String startDate,String endDate,PsDBBean psDBBean) {
        int updateNum=0;
        int errNum=0;
        for (LiquidationDailyDto dailyDto: monthList) {
            TmgLiquidationDailyDO tlddDo= new TmgLiquidationDailyDO();
            //定休日调整
            if(dailyDto.getKubun().indexOf("TMG_HOLFLG")> -1){
                tlddDo.setTlddCsparechar3(dailyDto.getKubun());
                tlddDo.setTlddCkuben(dailyDto.getKubun());
                tlddDo.setTlddCsparechar1("TMG_DATASTATUS|3");
            }
            //出勤或者休出 选用pattern
            if(dailyDto.getKubun().indexOf("TMG_PATTERN")> -1){
                tlddDo.setTlddCpattern(dailyDto.getKubun());
                tlddDo.setTlddCsparechar1("TMG_DATASTATUS|3");
                tlddDo.setTlddCstarttime(dailyDto.getStarttime());
                tlddDo.setTlddCendtime(dailyDto.getEndtime());
                tlddDo.setTlddCreststarttime(dailyDto.getResttimestart());
                tlddDo.setTlddCrestendtime(dailyDto.getResttimeend());

            }
            //原值
            if(dailyDto.getKubun().indexOf("TMG_WORK")> -1){
                break;
            }
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
            errNum=iTmgliquidationDailyService.checkLiquidationDaily(psDBBean.getCustID(),psDBBean.getCompCode(),empid,monthList.get(0).getYyyymmdd());
        }
        return  errNum;
    }

    //月編集画面表示
    public Map<String, Object> EditMonthDisp(String empId, String yyyymm, PsDBBean psDBBean) {
        Map<String, Object> monthlyMap = MapUtil.newHashMap();
        //月data
        List<LiquidationDailyInfoVo> liquidationDailyInfoVoList = iTmgliquidationDailyService.selectDailyInfo(psDBBean.getCustID(),psDBBean.getCompCode(),empId,yyyymm);


        monthlyMap.put("liquidationDailyInfoVoList", liquidationDailyInfoVoList);
        //todo:pattern list（选取用）





        return monthlyMap;
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
}
