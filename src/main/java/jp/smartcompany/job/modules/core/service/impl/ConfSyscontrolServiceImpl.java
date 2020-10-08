package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;
import jp.smartcompany.job.modules.core.mapper.ConfSyscontrol.ConfSyscontrolMapper;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
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

        /**
         * 读取SysControl全部的值
         * @return
         */
        @Override
        public List<ConfSyscontrolDO> getProperties() {
             return list(SysUtil.<ConfSyscontrolDO>query()
                     .select("CS_CCUSTOMERID","CS_CPROPERTYNAME","CS_CPROPERTYVALUE ")
                     .orderByAsc("CS_CPROPERTYNAME","CS_CCUSTOMERID"));
        }
}
