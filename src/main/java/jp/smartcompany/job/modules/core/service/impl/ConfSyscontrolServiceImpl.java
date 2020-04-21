package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;
import jp.smartcompany.job.modules.core.mapper.ConfSyscontrolMapper;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * システムプロパティマスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class ConfSyscontrolServiceImpl extends ServiceImpl<ConfSyscontrolMapper, ConfSyscontrolDO> implements IConfSyscontrolService {

        /**
         * システムプロパティを取得する
         *
         * @param customerId     法人コード
         * @param propertyName    システムプロパティ名
         * @return String システムプロパティ値
         */
        @Override
        public String selectPropertyValue(String customerId, String propertyName) {

                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("propertyName", propertyName);

                // データ取得
                String  propertyValue = baseMapper.selectPropertyValue(map);

                return propertyValue;
        }

        /**
         * 引数の条件でデータ取得できない場合は、顧客コード「00」でデータ取得
         *
         * @param customerId     法人コード
         * @param propertyName    システムプロパティ名
         * @return String システムプロパティ値
         */
        @Override
        public String selectPropertyValueNotFound(String customerId, String propertyName) {

                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("propertyName", propertyName);

                // データ取得
                String  propertyValue = baseMapper.selectPropertyValueNotFound(map);

                return propertyValue;
        }
}
