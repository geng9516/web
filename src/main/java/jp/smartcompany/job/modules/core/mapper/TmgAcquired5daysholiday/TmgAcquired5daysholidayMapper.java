package jp.smartcompany.job.modules.core.mapper.TmgAcquired5daysholiday;

import jp.smartcompany.job.modules.core.pojo.entity.TmgAcquired5daysholidayDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.Acquired5DaysListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 年5日時季指定取得情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgAcquired5daysholidayMapper extends BaseMapper<TmgAcquired5daysholidayDO> {

    /**
     * 一覧/編集画面検索用
     */
    List<Acquired5DaysListVO> buildSQLforList(Map<String, Object> map);

}
