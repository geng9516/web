package jp.smartcompany.job.asynctask;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.service.ITmgWorkMoYearlistService;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.TmgMoYearListVo;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.entity.EmployMailDO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.service.IEmployMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class MonthOutPutTask {

    private final ITmgWorkMoYearlistService iTmgWorkMoYearlistService;
    private final IEmployMailService iEmployMailService;
    private final SendMailTask sendMailTask;

    private String endTime = null;

    @Async
    public void MonthOutPutPolling(String custId,String compId,String secId,String lanage,String baseDate,String userId) throws InterruptedException {

        String userMail = iEmployMailService.getEmpMailInfo(userId).get().getTmaEmail();
        while (true){
            synchronized (this){
                if (StrUtil.hasBlank(endTime)){
                    getMoInfo( custId, compId, secId, lanage, baseDate);
                }else{
                    //todo
                    sendMailTask.sendTestMail(userMail,
                            "集計処理終了しました",
                            "集計処理終了しました、終了時間は" + endTime + "。ご確認お願いします ",
                            "");
                }
            }
            Thread.sleep(2000);
        }



    }

    private synchronized void getMoInfo(String custId,String compId,String secId,String lanage,String baseDate){
        List<TmgMoYearListVo> tmgMoYearListVoList  =iTmgWorkMoYearlistService.selectMoYearList(custId, compId,
                secId, baseDate.substring(0,4), lanage, baseDate);

        endTime = tmgMoYearListVoList.stream().filter(i -> !StrUtil.hasBlank(i.getTotalTime())).collect(Collectors.toList()).get(0).getTotalTime();
    }


}
