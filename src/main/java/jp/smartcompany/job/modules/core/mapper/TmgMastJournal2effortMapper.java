package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastJournal2effortDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * エフォート項目中間コードマッピングマスタ          主契約・従契約の区分と、仕訳ｺｰﾄﾞの組み合わせ→ｴﾌｫｰﾄ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMastJournal2effortMapper extends BaseMapper<TmgMastJournal2effortDO> {

        }
