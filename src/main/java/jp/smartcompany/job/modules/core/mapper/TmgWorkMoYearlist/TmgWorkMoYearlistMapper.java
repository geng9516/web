package jp.smartcompany.job.modules.core.mapper.TmgWorkMoYearlist;

import jp.smartcompany.job.modules.core.pojo.entity.TmgWorkMoYearlistDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.TmgMoYearListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [勤怠]月次集計データ作成・年度状況一覧ワークテーブル Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgWorkMoYearlistMapper extends BaseMapper<TmgWorkMoYearlistDO> {

    List<TmgMoYearListVo> selectMoYearList(@Param("cust") String cust,
                                           @Param("comp")String comp,
                                           @Param("secid")String secid,
                                           @Param("dyyyymm")String dyyyymm,
                                           @Param("lang")String lang,
                                           @Param("psBaseDate")String psBaseDate);

    int selectMoYearListColumn(@Param("cust")String cust,
                                  @Param("comp")String comp,
                                  @Param("secid")String secid,
                                  @Param("targetDate")String targetDate,
                                  @Param("introDate")String introDate,
                                  @Param("lang")String lang,
                                  @Param("psBaseDate")String psBaseDate);
}
