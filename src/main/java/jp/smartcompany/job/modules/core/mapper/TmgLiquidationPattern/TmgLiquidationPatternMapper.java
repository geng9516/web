package jp.smartcompany.job.modules.core.mapper.TmgLiquidationPattern;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationPatternDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.SelectPatternDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * [勤怠]勤務パターン                    制約：月中に歴が切れないこと、デフォルトフラグがonの行は同 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgLiquidationPatternMapper extends BaseMapper<TmgLiquidationPatternDO> {

    List<SelectPatternDto> selectLiquidationPatternInfo(@Param("empId") String empId,
                                                        @Param("yyyymm")String yyyymm,
                                                        @Param("custID")String custID,
                                                        @Param("compCode")String compCode);

    @Select("select TMG_LIQUIDATION_PATTERN_SEQ.nextval from dual ")
    String selectSeq();
}
