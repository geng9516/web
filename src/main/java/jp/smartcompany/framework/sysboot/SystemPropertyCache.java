package jp.smartcompany.framework.sysboot;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.framework.sysboot.dto.SystemPropertyDTO;
import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * システムプロパティ情報常駐変数キャッシュクラス
 * @author Xiao Wenpeng
 */
@Slf4j
public class SystemPropertyCache {

    /** システムプロパティ情報MAP */
    private final Map<String, SystemPropertyDTO> systemPropertyMap = MapUtil.newHashMap();

    /**
     * システムプロパティ情報取得.
     * @param psKey プロパティ名
     * @return プロパティ値
     */
    public Object getValue(String psKey) {
        Object propValue = null;
        SystemPropertyDTO systemPropertyDTO = systemPropertyMap.get(psKey);
        if (systemPropertyDTO != null) {
            propValue = systemPropertyDTO.getPropValue();
        }
        return propValue;
    }

    /**
     * システムプロパティ情報更新.
     * @param psKey		キー
     * @param psValue	値
     */
    public void setValue(String psKey, String psValue) {
        SystemPropertyDTO systemPropertyDTO = systemPropertyMap.get(psKey);
        if(systemPropertyDTO != null) {
            systemPropertyDTO.setPropValue(psValue);
        } else {
            systemPropertyDTO = new SystemPropertyDTO();
            systemPropertyDTO.setCustomerId("00");
            systemPropertyDTO.setPropName(psKey);
            systemPropertyDTO.setPropValue(psValue);
        }
        systemPropertyMap.put(psKey, systemPropertyDTO);
    }

    /**
     * システムプロパティ読み込み.
     */
    public void loadSystemProperty() {
        IConfSyscontrolService sysControlService = SpringUtil.getBean("confSyscontrolServiceImpl");
        // 必要な項目だけ転送
        List<ConfSyscontrolDO> controlList = sysControlService.getProperties();
        List<SystemPropertyDTO> propList = controlList.stream()
                .map(item -> new SystemPropertyDTO(
                        item.getCsCcustomerid(),
                        item.getCsCpropertyname(),
                        item.getCsCpropertyvalue()
                ))
                .collect(Collectors.toList());
        for (SystemPropertyDTO property : propList) {
            // キーには顧客コードを含めない
            // 制約事項：プロパティ名が重複してはならない
            String key = property.getPropName();
            // 格納
            systemPropertyMap.put(key, property);
        }
        log.info("【システムプロパティ：{}】",systemPropertyMap);
    }

}
