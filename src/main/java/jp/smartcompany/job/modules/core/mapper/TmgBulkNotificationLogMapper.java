package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNotificationLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.ErrorDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 休暇休業一括登録 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgBulkNotificationLogMapper extends BaseMapper<TmgBulkNotificationLogDO> {

    List<ErrorDetailVo> selectErrorList(@Param("seq") String seq);
}
