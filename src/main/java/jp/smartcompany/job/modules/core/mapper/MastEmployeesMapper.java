package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基本情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastEmployeesMapper extends BaseMapper<MastEmployeesDO> {

    /**
     * 勤務日を取得
     *
     * @param map 検索条件
     * @return Date
     */
    Date selectBegindateWork(Map<String, Object> map);

    List<PaidHolidayInitVO> selectPaidHolidayInit(String empSql);

}
