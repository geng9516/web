package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPaidHolidayDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgPaidHolidayDto;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayDispVO;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ParamNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.RestYearPaidHolidayVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]年次休暇情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgPaidHolidayMapper extends BaseMapper<TmgPaidHolidayDO> {


        List<Date> selectNykLoseDate(Map<String, Object> map);


        List<RestYearPaidHolidayVo> selectNenjikyukazannissu(@Param("params") ParamNotificationListDto params, @Param("detailFlg")int detailFlg);

    /**
     * [勤怠]年次休暇情報より、年次休暇付与状況一覧を取得する
     */
    List<PaidHolidayDispVO> buildSQLForSelectPaidHoliday(@Param("custID") String custID,
                                                         @Param("compCode") String compCode,
                                                         @Param("userCode") String userCode,
                                                         @Param("baseDate") String baseDate,
                                                         @Param("targetDate") String targetDate);

    /**
     *
     * [勤怠]年次休暇情報を更新する
     */
    int buildSQLForUpdatePaidHolyday(TmgPaidHolidayDto tmgPaidHolidayDto);
}
