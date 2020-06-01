package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPaidHolidayDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.paramNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.notificationListVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.restYearPaidHolidayVo;

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



        /**
         * 年次休暇残日数及び時間
         *
         * @param params 　params
         * @return String パターン
         */
        List<restYearPaidHolidayVo> selectNenjikyukazannissu(paramNotificationListDto params,int detailFlg);
        }
