package jp.smartcompany.framework.sysboot;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
@Slf4j
public class PsPermissionStringCache {

    private final Map<String, String> gPermissionStringMap = MapUtil.newConcurrentHashMap();

    public String getValue(String sKey) {
        if (CollUtil.isEmpty(gPermissionStringMap)) {
            return "";
        }
        return gPermissionStringMap.get(sKey);
    }

    public void loadPermissionString() {
        LRUCache<Object,Object> lruCache = SpringUtil.getBean("scCache");
        IMastGenericDetailService iMastGenericDetailService = SpringUtil.getBean("mastGenericDetailServiceImpl");
        List<MastGenericDetailDO> permStrList = iMastGenericDetailService.selectPermissionString();
        for (MastGenericDetailDO mastGenericDetailDO : permStrList) {
            String keyJa = mastGenericDetailDO.getMgdCcustomerid() + "_"
                    + mastGenericDetailDO.getMgdCcompanyidCkFk() + "_ja";

            gPermissionStringMap.put(keyJa,
                    mastGenericDetailDO.getMgdCgenericdetaildescja());

            String keyEn = mastGenericDetailDO.getMgdCcustomerid() + "_"
                    + mastGenericDetailDO.getMgdCcompanyidCkFk() + "_en";

            gPermissionStringMap.put(keyEn,
                    mastGenericDetailDO.getMgdCgenericdetaildescen());

            String keyCh = mastGenericDetailDO.getMgdCcustomerid() + "_"
                    + mastGenericDetailDO.getMgdCcompanyidCkFk() + "_ch";

            gPermissionStringMap.put(keyCh,
                    mastGenericDetailDO.getMgdCgenericdetaildescch());

            String key01 = mastGenericDetailDO.getMgdCcustomerid() + "_"
                    + mastGenericDetailDO.getMgdCcompanyidCkFk() + "_01";

            gPermissionStringMap.put(key01,
                    mastGenericDetailDO.getMgdCgenericdetaildesc01());

            String key02 = mastGenericDetailDO.getMgdCcustomerid() + "_"
                    + mastGenericDetailDO.getMgdCcompanyidCkFk() + "_02";

            gPermissionStringMap.put(key02,
                    mastGenericDetailDO.getMgdCgenericdetaildesc02());
        }
        lruCache.put(ScCacheUtil.PERMISSION_STRING,gPermissionStringMap);
    }

}
