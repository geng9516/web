package jp.smartcompany.job.modules.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.ErrMsgDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Mapper
public interface TmgTriggerMapper extends BaseMapper<TmgTriggerDO> {

    /**
     * 月次一覧、また日次登録（承認）画面表示時打刻反映処理　トリガーに追加する
     */
    int buildSQLForInsertTriggerByTimePunch(Map<String, Object> map);

    /**
     * トリガーに追加する
     */
    int buildSQLForInsertTrigger(Map<String, Object> map);

}
