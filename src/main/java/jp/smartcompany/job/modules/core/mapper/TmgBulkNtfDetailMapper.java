package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNtfDetailDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.SectionDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [勤怠]休暇休業一括登録・対象組織情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgBulkNtfDetailMapper extends BaseMapper<TmgBulkNtfDetailDO> {

    List<SectionDetailVo> selectSectionList(@Param("seq") String seq,
                                            @Param("custID") String custID,
                                            @Param("compID") String compID,
                                            @Param("lang") String lang);
}
