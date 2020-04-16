package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMoChangeCheckDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [勤怠]月次集計データ差異チェックテーブル 月次集計実行より後に勤務実績が修正されている職員・年月を登録 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMoChangeCheckMapper extends BaseMapper<TmgMoChangeCheckDO> {

        }
