package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgNtfAttachedfileDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 休暇休業申請添付ファイルテーブル Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgNtfAttachedfileMapper extends BaseMapper<TmgNtfAttachedfileDO> {

        /**添付ファイル一覧*/
        List<TmgNtfAttachedfileDO>  selectFileDisp(@Param("custId")String custId,
                                                   @Param("compId")String compId,
                                                   @Param("ntfNo")String ntfNo);

    String selectSeq(@Param("custId")String custId,
                   @Param("compId")String compId,
                   @Param("ntfNo")String ntfNo);
}
