package jp.smartcompany.framework.sysboot;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.framework.sysboot.bo.SystemProperty;
import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * システムプロパティ情報常駐変数キャッシュクラス
 * @author Xiao Wenpeng
 */
public class SystemPropertyCache {

    /** システムプロパティ情報MAP */
    private final Map<String, SystemProperty> systemPropertyMap = MapUtil.newHashMap();

    /**
     * システムプロパティ情報取得.
     * @param psKey プロパティ名
     * @return プロパティ値
     */
    public Object getValue(String psKey) {
        Object propValue = null;
        SystemProperty systemProperty = systemPropertyMap.get(psKey);
        if (systemProperty != null) {
            propValue = systemProperty.getPropValue();
        }
        return propValue;
    }

    /**
     * システムプロパティ情報更新.
     * @param psKey		キー
     * @param psValue	値
     */
    public void setValue(String psKey, String psValue) {
        SystemProperty systemProperty = systemPropertyMap.get(psKey);
        if(systemProperty != null) {
            systemProperty.setPropValue(psValue);
        } else {
            systemProperty = new SystemProperty();
            systemProperty.setCustomerId("00");
            systemProperty.setPropName(psKey);
            systemProperty.setPropValue(psValue);
        }
        systemPropertyMap.put(psKey, systemProperty);
    }

    /**
     * システムプロパティ読み込み.
     */
    public void loadSystemProperty() {
        IConfSyscontrolService sysControlService = SpringUtil.getBean("confSyscontrolServiceImpl");
        // 必要な項目だけ転送
        List<ConfSyscontrolDO> controlList = sysControlService.getProperties();
        List<SystemProperty> propList = controlList.stream()
                .map(item -> new SystemProperty(
                        item.getCsCcustomerid(),
                        item.getCsCpropertyname(),
                        item.getCsCpropertyvalue()
                ))
                .collect(Collectors.toList());
        for (SystemProperty property : propList) {
            // キーには顧客コードを含めない
            // 制約事項：プロパティ名が重複してはならない
            String key = property.getPropName();
            // 格納
            systemPropertyMap.put(key, property);
        }
    }

}
