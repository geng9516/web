package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic;

import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.ColumnOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.ColumnQueryDefinitionOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.TableOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.TableQueryDefinitionOptionDTO;

import java.util.List;

public interface IConditionSearchLogic {

    List<TableOptionDTO> getTableOptions();

    List<ColumnOptionDTO> getColumnOptions(String table);

    List<TableQueryDefinitionOptionDTO> getTableForQueryDefinition();

    List<ColumnQueryDefinitionOptionDTO> getColumnForQueryDefinition(String tableName);

}
