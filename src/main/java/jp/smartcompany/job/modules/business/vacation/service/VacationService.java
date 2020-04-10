package jp.smartcompany.job.modules.business.vacation.service;

import jp.smartcompany.job.modules.base.pojo.bo.PluggableBO;
import org.springframework.stereotype.Service;

@Service
public class VacationService {

    public void method1() {
        System.out.println("vacation method1");
    }

    public void method2() {
        System.out.println("vacation method2");
    }

    public String execute(PluggableBO pluggableBO) {
        return "vacation execute";
    }
}
