package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPersonalPatternDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * [勤怠]個人別勤務パターン Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgPersonalPatternMapper extends BaseMapper<TmgPersonalPatternDO> {


        /**
         * 個人別勤務パターンテーブルを検索し、週平均勤務時間を取得
         *
         * @param map 検索条件
         * @return int 週平均勤務時間
         */
        int selectAvgWorkTime(Map<String, Object> map);
        }
