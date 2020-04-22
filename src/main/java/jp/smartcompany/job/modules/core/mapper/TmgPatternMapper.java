package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternDetailRow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]勤務パターン                    制約：月中に歴が切れないこと、デフォルトフラグがonの行は同 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgPatternMapper extends BaseMapper<TmgPatternDO> {

        /**
         *  パターン情報
         *
         * @param map 検索条件
         * @return List<TmgPatternDetailRow> パターン情報
         */
        List<TmgPatternDetailRow> selectPatternDetail(Map<String, Object> map);


        /**
         *  日付変更時刻はルート組織から取得
         *
         * @param map 検索条件
         * @return List<TmgPatternDetailRow> パターン情報
         */
        int selectChangeTime(Map<String, Object> map);
        }
