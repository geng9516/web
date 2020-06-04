package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupAttributeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.HolidayTimeLimitDto;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.OverTimeLimitDto;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.LimitOfBaseDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * グループ属性テーブル Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgGroupAttributeMapper extends BaseMapper<TmgGroupAttributeDO> {

    List<LimitOfBaseDate> selectLimitOfBaseDate(@Param("custId") String custId,
                                                @Param("compId")String compId,
                                                @Param("sectionId")String sectionId,
                                                @Param("baseDate")String baseDate);

    OverTimeLimitDto selectOverTimeLimit(@Param("custId") String custId,
                                               @Param("compId")String compId,
                                               @Param("sectionId")String sectionId,
                                               @Param("groupId")String groupId);

    HolidayTimeLimitDto selectHolidayTimeLimit(@Param("custId") String custId,
                                                     @Param("compId")String compId,
                                                     @Param("sectionId")String sectionId,
                                                     @Param("groupId")String groupId);
}
