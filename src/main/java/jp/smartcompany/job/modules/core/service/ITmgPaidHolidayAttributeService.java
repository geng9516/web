package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPaidHolidayAttributeDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 * [勤怠]年次休暇付与属性テーブル 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgPaidHolidayAttributeService extends IService<TmgPaidHolidayAttributeDO> {

        /**
         * 年次休暇付与属性テーブルを検索し、週平均勤務時間を取得
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param employeeId    社員番号
         * @param yyyymmdd      基準日
         * @return int 週平均勤務時間
         */
        int selectAvgWorkTime(String customerId, String companyId, String employeeId
                , Date yyyymmdd);

        }
