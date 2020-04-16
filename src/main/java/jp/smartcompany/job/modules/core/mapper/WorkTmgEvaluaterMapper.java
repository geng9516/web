package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.WorkTmgEvaluaterDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [勤怠]権限設定  データ開始日、終了日は親となる部署のデータ開始日、終了日とする Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface WorkTmgEvaluaterMapper extends BaseMapper<WorkTmgEvaluaterDO> {

        }
