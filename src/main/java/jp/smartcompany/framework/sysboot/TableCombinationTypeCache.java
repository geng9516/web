package jp.smartcompany.framework.sysboot;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import jp.smartcompany.framework.sysboot.dto.TableCombinationTypeDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.List;

/**
 * テーブル結合条件情報常駐変数キャッシュクラス
 * @author Xiao Wenpeng
 */
@Slf4j
public class TableCombinationTypeCache {

    /** テーブル結合条件情報MAP */
    private final Map<String, TableCombinationTypeDTO> tableCombinationTypeMap = MapUtil.newHashMap();

    /**
     * テーブル結合条件情報取得.
     * @param psTableID テーブルID
     * @return TableCombinationType テーブル結合条件情報
     */
    public TableCombinationTypeDTO getTableCombinationType(String psTableID) {
        return tableCombinationTypeMap.get(psTableID);
    }

    /**
     * テーブル結合条件読み込み.
     */
    public void loadTableCombinationType() {
        LRUCache<Object,Object> lruCache = SpringUtil.getBean("scCache");
        IMastSystemService iMastSystemService = SpringUtil.getBean("mastSystemServiceImpl");
        List<jp.smartcompany.job.modules.core.pojo.dto.TableCombinationTypeDTO> tableCombinationTypeDTODtoList = iMastSystemService.getTableInfo();
        for (jp.smartcompany.job.modules.core.pojo.dto.TableCombinationTypeDTO tableCombinationTypeDto : tableCombinationTypeDTODtoList) {
            String sTableName = tableCombinationTypeDto.getTableName();
            String sColumnName = tableCombinationTypeDto.getColumnName();
            if (sColumnName == null) {
                sColumnName = "";
            }
            if (!tableCombinationTypeMap.containsKey(sTableName)) {
                TableCombinationTypeDTO tableCombinationTypeDTO = new TableCombinationTypeDTO();
                tableCombinationTypeDTO.setTableName(sTableName);
                tableCombinationTypeMap.put(sTableName, tableCombinationTypeDTO);
            }
            if (sColumnName.contains("_DENDOFTRIALEMPLOYMENT")) {
                /* カラムのネーミングルールに反するので何もしない */
            } else if (sColumnName.contains("_ID")) {
                tableCombinationTypeMap.get(sTableName).setIdColumnName(sColumnName);
            } else if (sColumnName.contains("_CCUSTOMERID")) {
                tableCombinationTypeMap.get(sTableName).setCustomerIdColumnName(sColumnName);
            } else if (sColumnName.contains("_CCOMPANYID")) {
                tableCombinationTypeMap.get(sTableName).setCompanyIdColumnName(sColumnName);
            } else if (sColumnName.contains("_CUSERID")) {
                tableCombinationTypeMap.get(sTableName).setUserIdColumnName(sColumnName);
            } else if (sColumnName.contains("_CEMPLOYEEID")) {
                tableCombinationTypeMap.get(sTableName).setEmployeeIdColumnName(sColumnName);
            } else if (sColumnName.contains("_CSECTIONID")) {
                tableCombinationTypeMap.get(sTableName).setSectionIdColumnName(sColumnName);
            } else if (sColumnName.contains("_CPOSTID")) {
                tableCombinationTypeMap.get(sTableName).setPostIdColumnName(sColumnName);
            } else if (sColumnName.contains("_DSTART")) {
                tableCombinationTypeMap.get(sTableName).setStartDateColumnName(sColumnName);
            } else if (sColumnName.contains("_DEND")) {
                tableCombinationTypeMap.get(sTableName).setEndDateColumnName(sColumnName);
            } else if (sColumnName.contains("_CLANGUAGE")) {
                tableCombinationTypeMap.get(sTableName).setLanguageColumnName(sColumnName);
            } else if (sColumnName.contains("_CLAYEREDSECTIONID")) {
                tableCombinationTypeMap.get(sTableName).setLayeredSectionIdColumnName(sColumnName);
            } else if (sColumnName.contains("_CIFKEYORADDITIONALROLE")) {
                tableCombinationTypeMap.get(sTableName).setIfKeyOrAdditionalRoleColumnName(sColumnName);
            }
        }
        lruCache.put(ScCacheUtil.TABLE_COMBINATION_TYPE_MAP,tableCombinationTypeMap);
        log.info("【テーブル結合条件情報取得処理】：{}",tableCombinationTypeMap);
    }

}
