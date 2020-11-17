package jp.smartcompany.job.modules.core.mapper.HistSuspension;

import jp.smartcompany.job.modules.core.pojo.entity.HistSuspensionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.HatuReiDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 休職歴 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface HistSuspensionMapper extends BaseMapper<HistSuspensionDO> {

    HatuReiDto getHatuRei(@Param("custID") String custID,
                          @Param("compCode") String compCode,
                          @Param("targetUser") String targetUser,
                          @Param("baseDate") String baseDate);
}
