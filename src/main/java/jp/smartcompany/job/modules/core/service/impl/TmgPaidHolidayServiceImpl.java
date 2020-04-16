package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPaidHolidayDO;
import jp.smartcompany.job.modules.core.mapper.TmgPaidHolidayMapper;
import jp.smartcompany.job.modules.core.service.ITmgPaidHolidayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]年次休暇情報 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgPaidHolidayServiceImpl extends ServiceImpl<TmgPaidHolidayMapper, TmgPaidHolidayDO> implements ITmgPaidHolidayService {
        @Override
        public List<Date> selectNykLoseDate(String customerId, String companyId, Date startDate, Date endDate, String employeeId){
                List<Date> dateList;
                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("companyId", companyId);
                map.put("startDate", startDate);
                map.put("endDate", endDate);
                map.put("employeeId", employeeId);

                // 年休ルールを取得
                dateList =baseMapper.selectNykLoseDate(map);

                return dateList;
        }
        }
