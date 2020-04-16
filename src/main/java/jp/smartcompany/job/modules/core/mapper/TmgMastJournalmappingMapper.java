package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastJournalmappingDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 仕訳項目マッピングマスタ                  日次仕訳処理において、各種仕訳処理の結果を仕訳項目にマッピン Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMastJournalmappingMapper extends BaseMapper<TmgMastJournalmappingDO> {

        }
