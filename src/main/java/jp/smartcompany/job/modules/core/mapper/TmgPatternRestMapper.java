package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternRestDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternDetailRow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 勤務パターン休憩情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgPatternRestMapper extends BaseMapper<TmgPatternRestDO> {


        /**
         *  パターン休憩情報
         *
         * @param map 検索条件
         * @return List<TmgPatternRestDO> パターン休憩情報
         */
        List<TmgPatternRestDO> selectPatternRestTime(Map<String, Object> map);
        }
