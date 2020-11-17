package jp.smartcompany.job.modules.core.mapper.TmgDailyCheck;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyCheckDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.DailyCheckDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [勤怠]エラーチェック用・日別情報             2007/02/23元テーブルのレイアウト変更に伴い修正 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgDailyCheckMapper extends BaseMapper<TmgDailyCheckDO> {

    int buildSQLForInsertDailyCheck(DailyCheckDto dailyCheckDto);
}
