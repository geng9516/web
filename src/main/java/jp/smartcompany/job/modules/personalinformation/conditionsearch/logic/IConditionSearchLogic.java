package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic;

import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.ColumnOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.TableOptionDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IConditionSearchLogic {

    List<TableOptionDTO> getTableOptions();

    List<ColumnOptionDTO> getColumnOptions(String table);

    Map<String,Object> getQueryConditions(Long settingId, Date sDate);

}
