package jp.smartcompany.job.modules.core.mapper.TmgPatternApplies;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternAppliesDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * [就業]勤務パターン適用情報(部署) Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgPatternAppliesMapper extends BaseMapper<TmgPatternAppliesDO> {


        /**
         *  パターン割付情報を検索
         *
         * @param map 検索条件
         * @return String パターンID
         */
        String selectPatternId(Map<String, Object> map);
        }
