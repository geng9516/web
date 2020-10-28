package jp.smartcompany.job.modules.core.mapper.TmgWorkMonthlyoutputlist;

import jp.smartcompany.job.modules.core.pojo.entity.TmgWorkMonthlyoutputlistDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [勤怠]月次集計データ作成・対象者一覧ワークテーブル Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgWorkMonthlyoutputlistMapper extends BaseMapper<TmgWorkMonthlyoutputlistDO> {


        int selectWorkMonthlyOutputList(@Param("custId")String custId,
                                        @Param("compId")String compId,
                                        @Param("targetYear")String targetYear,
                                        @Param("introductionData")String introductionData);
        }
