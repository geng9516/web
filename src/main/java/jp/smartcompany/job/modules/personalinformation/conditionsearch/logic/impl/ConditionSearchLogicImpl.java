package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.impl;

import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.ColumnOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.ColumnQueryDefinitionOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.TableOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.TableQueryDefinitionOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IConditionSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionSearchLogicImpl implements IConditionSearchLogic {

    private final IConditionSearchService conditionSearchService;

    // 获取数据库表选择列表
    @Override
    public List<TableOptionDTO> getTableOptions() {
        return conditionSearchService.selectTable();
    }

    // 根据数据库表名获取数据库表里的所有列名
    @Override
    public List<ColumnOptionDTO> getColumnOptions(String table) {
        return conditionSearchService.selectColumns(table);
    }

    // 获取条件式定义里可选择的数据库表
    @Override
    public List<TableQueryDefinitionOptionDTO> getTableForQueryDefinition() {
        return conditionSearchService.selectTableForQueryDefinition();
    }

    // 获取条件定义里可选择的表的列名
    @Override
    public List<ColumnQueryDefinitionOptionDTO> getColumnForQueryDefinition(String tableName) {
        return conditionSearchService.selectColumnForQueryDefinition(tableName);
    }

    /** CSV最大行 */
//    private static final String PROP_JK_MAX_COLS = "JkMaxCols";
//    private final ScCacheUtil cacheUtil;

    //        String sJkMaxCols = cacheUtil.getSystemProperty(PROP_JK_MAX_COLS);
//        if (StrUtil.isBlank(sJkMaxCols)) {
//            throw new GlobalException("MAX行は設定していません");
//        }
//        int maxCols = 0;
//        try {
//            maxCols = Integer.parseInt(sJkMaxCols);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            throw new GlobalException(e.getMessage());
//        }

}
