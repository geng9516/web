package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupMemberCheckDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [勤怠]エラーチェック用グループ割付情報          データ開始日、終了日は親となる異動歴のデータ開始日、終了日と Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgGroupMemberCheckMapper extends BaseMapper<TmgGroupMemberCheckDO> {

        }
