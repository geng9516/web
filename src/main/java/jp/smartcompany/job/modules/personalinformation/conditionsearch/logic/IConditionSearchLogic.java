package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic;

import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.ColumnOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.ColumnQueryDefinitionOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.TableOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.TableQueryDefinitionOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingDTO;

import java.util.List;
import java.util.Map;

public interface IConditionSearchLogic {

    /** 出力モード :画面*/
    String MODE_SCREEN = "SCREEN";
    /** 出力モード :CSV出力*/
    String MODE_CSV = "CSV";
    /** 出力モード :テーブル*/
    String MODE_TABLE = "TABLE";

    String UNDER_SCORE = "_";
    /** 別名記号 **/
    String ALIAS_MARK = "_F";

    /**
     * 分页相关
     */
    String CHANGE_PAGE_CONDITION = "pageCondition";
    String CHANGE_CURRENT_PAGE = "currentPage";
    /**定数：リンクボタンページ送り数*/
    // 使用されていない
    static final int MOVE_PAGE_COUNT = 10;

    List<TableOptionDTO> getTableOptions();

    List<ColumnOptionDTO> getColumnOptions(String table);

    List<TableQueryDefinitionOptionDTO> getTableForQueryDefinition();

    List<ColumnQueryDefinitionOptionDTO> getColumnForQueryDefinition(String tableName);

    Map<String,Object> search(ConditionSettingDTO settingDTO);

}
