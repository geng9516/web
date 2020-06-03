package jp.smartcompany.job.modules.core.util.searchrange;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.dto.TableCombinationTypeDTO;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.List;

/**
 * テーブル結合条件情報常駐変数キャッシュクラス
 * @author Xiao Wenpeng
 */
@Component
public class TableCombinationTypeCache {

    /** テーブル結合条件情報MAP */
    private Map<String, TableCombinationType> tableCombinationTypeMap;
    @Resource
    private  IMastSystemService iMastSystemService;

    /**
     * テーブル結合条件情報取得.
     * @param psTableID テーブルID
     * @return TableCombinationType テーブル結合条件情報
     */
    public TableCombinationType getTableCombinationType(String psTableID) {
        return tableCombinationTypeMap.get(psTableID);
    }

    /**
     * テーブル結合条件読み込み.
     */
    public void loadTableCombinationType() {
        tableCombinationTypeMap = MapUtil.newHashMap();
        List<TableCombinationTypeDTO> tableCombinationTypeDtoList = iMastSystemService.getTableInfo();
        for (TableCombinationTypeDTO tableCombinationTypeDto : tableCombinationTypeDtoList) {
            String sTableName = tableCombinationTypeDto.getTableName();
            String sColumnName = tableCombinationTypeDto.getColumnName();
            if (sColumnName == null) {
                sColumnName = "";
            }
            if (!tableCombinationTypeMap.containsKey(sTableName)) {
                TableCombinationType tableCombinationType = new TableCombinationType();
                tableCombinationType.setTableName(sTableName);
                tableCombinationTypeMap.put(sTableName, tableCombinationType);
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
    }

}
