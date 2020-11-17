package jp.smartcompany.job.modules.core.mapper.TmgGroup;

import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [勤怠]グループ                      データ開始日、終了日は親となる部署のデータ開始日、終了日とす Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgGroupMapper extends BaseMapper<TmgGroupDO> {

        }
