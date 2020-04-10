package jp.smartcompany.job.modules.business.work.service;

import jp.smartcompany.job.modules.business.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkService {

    private final VacationService vacationService;

    public void method1(String param1,String param2,Integer param3) {
        System.out.println(param1);
        System.out.println(param2);
        System.out.println(param3);
        // 执行逻辑的时候还需要和休假模块连携时，调用休假模块的method1方法
        vacationService.method1();
        System.out.println("work method1");
    }

    public void method2() {
        System.out.println("work method2");
    }

}
