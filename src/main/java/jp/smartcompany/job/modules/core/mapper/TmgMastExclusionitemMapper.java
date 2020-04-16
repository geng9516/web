package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastExclusionitemDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 除外項目マスタ                       集計処理カテゴリごとに、重複している場合に重複部分を除外する Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMastExclusionitemMapper extends BaseMapper<TmgMastExclusionitemDO> {

        }
