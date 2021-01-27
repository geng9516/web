package jp.smartcompany.job.asynctask;


import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDailyDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationPeriodDO;
import jp.smartcompany.job.modules.core.service.IMastMailInfoService;
import jp.smartcompany.job.modules.core.service.ITmgliquidationPeriodService;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.TmgMoYearListVo;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.service.IEmployMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LiquidationTask {

    private final ITmgliquidationPeriodService iTmgliquidationPeriodService;
    private final IEmployMailService iEmployMailService;
    private final IMastMailInfoService iMastMailInfoService;

    private final SendMailTask sendMailTask;

    private String lqdStutas = null;

    @Async
    public void liquidationPolling(String custId,String compId,String empId,String startDate,String endDate,String userId) throws InterruptedException {

        String userMail = iEmployMailService.getEmpMailInfo(userId).get().getTmaEmail();
        while (true){
            synchronized (this){
                if (lqdStutas.equals("TMG_LPSTATUS|4")){
                    getliquidation( custId, compId, empId, startDate,endDate);
                }else if(lqdStutas.equals("TMG_LPSTATUS|9")){
                    //todo
                    sendMailTask.sendTestMail(userMail,
                            "精算登録処理失败しました",
                            "精算登録処理失败しました、システム管理者へ連絡してください",
                            "");
                } else if(lqdStutas.equals("TMG_LPSTATUS|5")){
                    //todo
                    sendMailTask.sendTestMail(userMail,
                            "精算登録処理終了しました",
                            "精算登録終了しました、ご確認お願いします ",
                            "");
                }
            }
            Thread.sleep(1000);
        }



    }

    private synchronized void getliquidation(String custId,String compId,String empId,String startDate ,String endDate){
        lqdStutas = iTmgliquidationPeriodService.getBaseMapper().selectOne(SysUtil.<TmgLiquidationPeriodDO>query()
                    .eq("TLP_CCUSTOMERID", custId)
                    .eq("TLP_CCOMPANYID", compId)
                    .eq("TLP_DSTARTDATE", startDate)
                    .eq("TLP_DENDDATE", endDate)
                    .eq("TLP_CEMPLOYEEID", empId)).getTlpCsparechar1();
    }

}
