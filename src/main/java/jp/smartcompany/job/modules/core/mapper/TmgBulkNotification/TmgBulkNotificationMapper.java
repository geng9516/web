package jp.smartcompany.job.modules.core.mapper.TmgBulkNotification;

import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNotificationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.dto.HistoryDto;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.DetailDataVo;
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
public interface TmgBulkNotificationMapper extends BaseMapper<TmgBulkNotificationDO> {

    List<HistoryDto> selectHistoryList(@Param("custID") String custID,
                                       @Param("compCode")String compCode,
                                       @Param("language")String language);

    String selectNextSeq();

    DetailDataVo selectDetail(@Param("seq") String seq,@Param("language") String language);
}
