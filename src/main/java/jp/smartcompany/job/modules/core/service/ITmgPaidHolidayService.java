package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPaidHolidayDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * [勤怠]年次休暇情報 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgPaidHolidayService extends IService<TmgPaidHolidayDO> {

        List<Date> selectNykLoseDate(String customerId, String companyId, Date startDate, Date endDate, String employeeId);

        }
