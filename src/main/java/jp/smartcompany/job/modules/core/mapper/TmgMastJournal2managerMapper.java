package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastJournal2managerDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 超勤手当支給対象判定マスタ                 管理職/非管理職ごとに、給与用項目として使用する仕訳ｺｰﾄﾞ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMastJournal2managerMapper extends BaseMapper<TmgMastJournal2managerDO> {

        }
