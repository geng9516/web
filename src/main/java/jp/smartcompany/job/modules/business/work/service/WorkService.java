package jp.smartcompany.job.modules.business.work.service;

import jp.smartcompany.job.modules.base.pojo.bo.PluggableBO;
import jp.smartcompany.job.modules.business.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkService {

    private final VacationService vacationService;

    public String execute(PluggableBO pluggableBO) {
        vacationService.method1();
        return "work execute";
    }

}
