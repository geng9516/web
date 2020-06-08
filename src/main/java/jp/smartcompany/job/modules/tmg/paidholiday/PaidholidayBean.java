package jp.smartcompany.job.modules.tmg.paidholiday;

import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 * 年次休假管理处理bean -> 对应旧就业的ps.c01.tmg.PaidHoliday.PaidHolidayBean
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaidholidayBean {

    private final IMastEmployeesService iMastEmployeesService;

    /**
     * 一覧の検索
     */
    public List<PaidHolidayInitVO> actionInitHandler(String empSql) {
         return iMastEmployeesService.listPaidHolidayInit(empSql);
    }

}
