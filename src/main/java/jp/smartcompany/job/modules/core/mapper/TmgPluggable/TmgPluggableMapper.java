package jp.smartcompany.job.modules.core.mapper.TmgPluggable;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPluggableDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * プラガブル情報マスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgPluggableMapper extends BaseMapper<TmgPluggableDO> {

    /**
     * 勤怠種別実行処理取得
     *
     * @param map 検索条件
     * @return List<TmgPluggableDO> プラガブル情報
     */
    List<TmgPluggableDO> listTmgPluggableDO(Map<String, Object> map);


}
