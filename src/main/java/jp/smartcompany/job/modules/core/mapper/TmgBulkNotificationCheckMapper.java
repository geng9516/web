package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNotificationCheckDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 休暇休業一括登録 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgBulkNotificationCheckMapper extends BaseMapper<TmgBulkNotificationCheckDO> {

    String checkBulkNtf(@Param("seq") String seq,
                        @Param("custID")String custID,
                        @Param("compCode")String compCode);
}
