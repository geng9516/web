package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDateofempLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.TmgDateOfEmpLogVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [勤怠]勤務開始日編集ログ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgDateofempLogMapper extends BaseMapper<TmgDateofempLogDO> {

    TmgDateOfEmpLogVo selectTmgDateofempLog(@Param("custId") String custId,
                                            @Param("compId")String compId,
                                            @Param("empId")String empId,
                                            @Param("baseDate")String baseDate);
}
