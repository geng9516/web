package jp.smartcompany.job.modules.tmg.paidholiday;

import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 * 年次休假管理处理bean -> 对应旧就业的ps.c01.tmg.PaidHoliday.PaidHolidayBean
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaidHolidayBean {

    private final IMastEmployeesService iMastEmployeesService;

    /**
     * 一覧の検索
     * @param modelMap
     */
    public void actionInitHandler(ModelMap modelMap) {
         modelMap.addAttribute("vacationDaysList",iMastEmployeesService.listPaidHolidayInit());
    }

}
