package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastTarget4latecheckDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 遅刻早退判定対象マスタ                   勤怠種別、出張区分から遅刻・早退を判定する対象を設定するマス Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMastTarget4latecheckMapper extends BaseMapper<TmgMastTarget4latecheckDO> {

        }
