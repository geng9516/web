package jp.smartcompany.boot.event;

import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.tmg.timepunch.TmgTimePunchBean;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StampEventListener implements ApplicationListener<StampEvent> {

    private final TmgTimePunchBean tmgTimePunchBean;

    @Override
    @Async
    public void onApplicationEvent(StampEvent stampEvent) {
       LoginAccountBO accountBO = (LoginAccountBO)stampEvent.getSource();
       tmgTimePunchBean.execTimePunch(accountBO.getHdCemployeeidCk(), accountBO.getHdCcustomeridCk(), accountBO.getHdCcompanyidCk(), stampEvent.getAction());
    }

}
