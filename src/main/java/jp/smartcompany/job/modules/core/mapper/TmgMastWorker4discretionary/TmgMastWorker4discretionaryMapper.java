package jp.smartcompany.job.modules.core.mapper.TmgMastWorker4discretionary;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastWorker4discretionaryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 裁量労働身分マスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMastWorker4discretionaryMapper extends BaseMapper<TmgMastWorker4discretionaryDO> {

    /**
     * 裁量労働対象者か判断する
     */
    String buildSQLForSelectDiscretion(@Param("custId") String custId,
                                       @Param("compCode") String compCode,
                                       @Param("employeeCode") String employeeCode,
                                       @Param("baseDate") String baseDate);
}
