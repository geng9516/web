package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.WorkTmgMonthlyOutputLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [勤怠]月次集計処理ログ(ユーザー自動作成スクリプト用、一時退避テーブル) Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface WorkTmgMonthlyOutputLogMapper extends BaseMapper<WorkTmgMonthlyOutputLogDO> {

        }
