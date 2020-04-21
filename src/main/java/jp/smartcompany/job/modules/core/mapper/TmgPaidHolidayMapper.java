package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPaidHolidayDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]年次休暇情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgPaidHolidayMapper extends BaseMapper<TmgPaidHolidayDO> {


        List<Date> selectNykLoseDate(Map<String, Object> map);
        }