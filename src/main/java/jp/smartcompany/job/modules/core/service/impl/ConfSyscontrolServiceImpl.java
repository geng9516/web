package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;
import jp.smartcompany.job.modules.core.mapper.ConfSyscontrol.ConfSyscontrolMapper;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto.MailConfigDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
@RequiredArgsConstructor
public class ConfSyscontrolServiceImpl extends ServiceImpl<ConfSyscontrolMapper, ConfSyscontrolDO> implements IConfSyscontrolService {

        private static final String MAIL_USERNAME = "MAIL_USERNAME";
        private static final String MAIL_PASSWORD = "MAIL_PASSWORD";
        private static final String MAIL_PORT = "MAIL_PORT";
        private static final String MAIL_HOST = "MAIL_HOST";

        private final LRUCache<Object,Object> lruCache;

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

        @Override
        @Transactional(rollbackFor = GlobalException.class)
        public void updateMailConfig(MailConfigDTO dto) {
           QueryWrapper<ConfSyscontrolDO> usernameQw = SysUtil.query();
           usernameQw.eq("CS_CPROPERTYNAME",MAIL_USERNAME);
           ConfSyscontrolDO mailUsername = getOne(usernameQw);

           QueryWrapper<ConfSyscontrolDO> passwordQw = SysUtil.query();
           passwordQw.eq("CS_CPROPERTYNAME",MAIL_PASSWORD);
           ConfSyscontrolDO mailPassword = getOne(passwordQw);

           QueryWrapper<ConfSyscontrolDO> portQw = SysUtil.query();
           portQw.eq("CS_CPROPERTYNAME",MAIL_PORT);
           ConfSyscontrolDO mailPort = getOne(portQw);

           QueryWrapper<ConfSyscontrolDO> hostQw = SysUtil.query();
           hostQw.eq("CS_CPROPERTYNAME",MAIL_HOST);
           ConfSyscontrolDO mailHost = getOne(hostQw);

           mailUsername.setCsCpropertyvalue(dto.getUsername());
           mailPassword.setCsCpropertyvalue(dto.getPassword());
           mailPort.setCsCpropertyvalue(dto.getPort());
           mailHost.setCsCpropertyvalue(dto.getHost());

           updateBatchById(CollUtil.newArrayList(mailUsername,mailPassword,mailPort,mailHost));

           lruCache.remove(ScCacheUtil.SYSTEM_PROPERTY_MAP);
        }


        

}
