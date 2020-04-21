package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPaidHolidayAttributeDO;
import jp.smartcompany.job.modules.core.mapper.TmgPaidHolidayAttributeMapper;
import jp.smartcompany.job.modules.core.service.ITmgPaidHolidayAttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * [勤怠]年次休暇付与属性テーブル 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgPaidHolidayAttributeServiceImpl extends ServiceImpl<TmgPaidHolidayAttributeMapper, TmgPaidHolidayAttributeDO> implements ITmgPaidHolidayAttributeService {


        /**
         * 年次休暇付与属性テーブルを検索し、週平均勤務時間を取得
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param employeeId    社員番号
         * @param yyyymmdd      基準日
         * @return int 週平均勤務時間
         */
        @Override
        public int selectAvgWorkTime(String customerId, String companyId, String employeeId
                , Date yyyymmdd) {

                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("companyId", companyId);
                map.put("yyyymmdd", yyyymmdd);
                map.put("employeeId", employeeId);

                // 週平均勤務時間を取得
                Integer workTime = baseMapper.selectAvgWorkTime(map);

                return workTime;
        }

}
