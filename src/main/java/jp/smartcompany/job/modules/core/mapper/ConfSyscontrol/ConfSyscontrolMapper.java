package jp.smartcompany.job.modules.core.mapper.ConfSyscontrol;

import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * システムプロパティマスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface ConfSyscontrolMapper extends BaseMapper<ConfSyscontrolDO> {


        /**
         * システムプロパティを取得する
         *
         * @param map 検索条件
         * @return String システムプロパティ値
         */
        String selectPropertyValue(Map<String, Object> map);

        /**
         * 引数の条件でデータ取得できない場合は、顧客コード「00」でデータ取得
         *
         * @param map 検索条件
         * @return String システムプロパティ値
         */
        String  selectPropertyValueNotFound(Map<String, Object> map);

}
