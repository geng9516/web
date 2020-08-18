package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgAcquired5daysholidayDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.Acquired5DaysListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 年5日時季指定取得情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgAcquired5daysholidayMapper extends BaseMapper<TmgAcquired5daysholidayDO> {

    /**
     * 一覧/編集画面検索用
     */
    List<Acquired5DaysListVO> buildSQLforList(Map<String, Object> map);
    /**
     * 修正基準日チェック期間再計算
     */
    void buildSQLTmgAcquired5daykikanbi(@Param("txtUserCode") String txtUserCode,
                                        @Param("kijunbi")String kijunbi,
                                        @Param("txtYear")String txtYear,
                                        @Param("kijunbiEdit")String kijunbiEdit,
                                        @Param("userCode")String userCode,
                                        @Param("programId")String programId,
                                        @Param("custID")String custID,
                                        @Param("compCode")String compCode,
                                        @Param("txtDpaidholidayEnd")String txtDpaidholidayEnd,
                                        @Param("txtNusedaysDays")String txtNusedaysDays,
                                        @Param("txtFuyobi")String txtFuyobi);
}
