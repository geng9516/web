package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgWorksKintaiArchiveDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [勤怠]月次集計出力イメージ(works)  過去データ退避 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgWorksKintaiArchiveMapper extends BaseMapper<TmgWorksKintaiArchiveDO> {

        }