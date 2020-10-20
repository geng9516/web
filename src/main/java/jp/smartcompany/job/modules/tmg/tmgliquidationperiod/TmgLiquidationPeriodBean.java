package jp.smartcompany.job.modules.tmg.tmgliquidationperiod;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import jp.smartcompany.job.modules.core.service.ITmgEmployeesService;
import jp.smartcompany.job.modules.core.service.ITmgliquidationDetailService;
import jp.smartcompany.job.modules.core.service.ITmgliquidationPeriodService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationPeriodListDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.WorkTypeDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.WorkTypeGroupDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.EditDispVo;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.LiquidationDispVo;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private final IMastGenericDetailService iMastGenericDetailService;

    // 职种
    public List<WorkTypeGroupDto> getWorkTypeList(PsDBBean psDBBean) throws Exception {

        //变形
        List<WorkTypeDto> variational=iMastGenericDetailService.selectWorkerType4Variational(psDBBean.getCustID(),
                psDBBean.getCompCode(), TmgUtil.getSysdate());
        //flex
        List<WorkTypeDto> flex=iMastGenericDetailService.selectWorkerType4Flex(psDBBean.getCustID(),
                psDBBean.getCompCode(),TmgUtil.getSysdate());
        WorkTypeGroupDto variationalGroup=new WorkTypeGroupDto();
        WorkTypeGroupDto flexGroup=new WorkTypeGroupDto();
        variationalGroup.setChild(variational);
        variationalGroup.setWorktypeid("variational");
        variationalGroup.setWorktypename("変形");
        flexGroup.setChild(flex);
        flexGroup.setWorktypeid("flex");
        flexGroup.setWorktypename("フレックス");

        List<WorkTypeGroupDto> vos =new ArrayList<>();
        vos.add(variationalGroup);
        vos.add(flexGroup);
        return vos;
    }

    // 数据获取 搜索
    public LiquidationDispVo getLiquidationDisp(String type, String searchText, PsDBBean psDBBean) {
        LiquidationDispVo vo =new LiquidationDispVo();
        List<LiquidationPeriodListDto> liquidationPeriodListDtos=iTmgliquidationPeriodService.getLiquidationDispFromType(psDBBean.getCustID(),psDBBean.getCompCode(),type,searchText);
        vo.setLiquidationPeriodList(liquidationPeriodListDtos);
        return vo;
    }
    // 数据获取 职种
    public LiquidationDispVo getLiquidationDisp(String workType, PsDBBean psDBBean) {
        LiquidationDispVo vo =new LiquidationDispVo();
        List<LiquidationPeriodListDto> liquidationPeriodListDtos=iTmgliquidationPeriodService.getLiquidationDispFromWorkType(psDBBean.getCustID(),psDBBean.getCompCode(),workType);
        vo.setLiquidationPeriodList(liquidationPeriodListDtos);
//        vo.setPageSize(50);
//        vo.setTotalCount(iTmgNotificationService.selectNotificationListCount(param));
//        vo.setTotalPage(notificationDispVo.getTotalCount()/50+1);
//        vo.setCurrPage(page);
        return vo;
    }


    //详细页面api
    public List<EditDispVo> getEditDisop(String tlpId , String startDate , String endDate , PsDBBean psDBBean) throws Exception {
        List<EditDispVo> vos=new ArrayList<>();
        if(StrUtil.hasEmpty(tlpId)){
            vos= iTmgliquidationDetailService.getLiquidationDetailNew(startDate,String.valueOf(DateUtil.betweenMonth(DateUtil.parse(startDate),DateUtil.parse(endDate),true)+1));
        }else{
            vos = iTmgliquidationDetailService.getLiquidationDetail(psDBBean.getCustID(),psDBBean.getCompCode(),tlpId,startDate,endDate);
        }
        return vos;
    }



    public GlobalResponse updateEdit() throws Exception {
        //todo
        return GlobalResponse.ok();
    }

}
