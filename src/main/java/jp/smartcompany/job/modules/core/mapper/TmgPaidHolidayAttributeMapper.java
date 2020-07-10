package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPaidHolidayAttributeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.empattrsetting.dto.EmpAttrSettingDto;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.AvgWorkTimeHistoryVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.AvgWorkTimeVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.TmgEmpVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]年次休暇付与属性テーブル Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgPaidHolidayAttributeMapper extends BaseMapper<TmgPaidHolidayAttributeDO> {


        /**
         * 年次休暇付与属性テーブルを検索し、週平均勤務時間を取得
         *
         * @param map 検索条件
         * @return int 週平均勤務時間
         */
        int selectAvgWorkTime(Map<String, Object> map);


        List<TmgEmpVo> selectTmgEmp(@Param("param") EmpAttrSettingDto param,@Param("numStrat")int numStrat,@Param("numEnd")int numEnd);

        int selectTmgEmpCount(@Param("param")EmpAttrSettingDto param);

        AvgWorkTimeVo selectAvgWorkTime60(@Param("customerId")String customerId,
                                          @Param("companyId")String companyId,
                                          @Param("employeeId")String employeeId,
                                          @Param("yyyymmdd")String yyyymmdd);

        AvgWorkTimeVo selectDefaultAvgWorkTime(@Param("customerId")String customerId,
                                               @Param("companyId")String companyId,
                                               @Param("employeeId")String employeeId,
                                               @Param("yyyymmdd")String yyyymmdd);

        List<AvgWorkTimeHistoryVo> selectAvgWorkTimeHistory(@Param("customerId")String customerId,
                                                      @Param("companyId")String companyId,
                                                      @Param("employeeId")String employeeId);
}
