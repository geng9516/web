package jp.smartcompany.job.modules.personalinformation.conditionsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.*;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingTargetDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSettingDO;

import java.util.List;
import java.util.Map;

public interface IConditionSearchService extends IService<MastDatadictionaryDO> {

    /** QSECTION */
    String MASTER_QSECTION = "QSECTION";

    /** QPOST */
    String MASTER_QPOST = "QPOST";

    /** 社員情報 */
    String MASTER_EMP = "1";

    /** 社員情報以外 */
    String MASTER_NOEMP = "0";

    /**
     * 社員情報
     */
    int EMPLOY = 1;
    /**
     * 所属情報
     */
    int SECTION =2;
    /**
     * 役職情報
     */
    int POST = 3;
    /**
     * マスタ情報
     */
    int GENERIC = 4;
    /**
     * 手入力情報
     */
    int INPUT = 5;

    List<TableOptionDTO> selectTable();

    List<ColumnOptionDTO> selectColumns(String tableName);

    List<ConditionWhereDTO> selectWhereConditions(Long settingId);

    List<TableQueryDefinitionOptionDTO> selectTableForQueryDefinition();

    List<ColumnQueryDefinitionOptionDTO> selectColumnForQueryDefinition(String tableName);

    HistSearchSettingDO selectHistSearchSettingBySettingId(Long settingId);

    List<ConditionSettingTargetDTO> selectConditionSettingTargetList(Long settingId);
    
    
    String selectSettingOwner(Long settingId);

    void deleteSetting(Long settingId);

    void deleteSelectSetting(Long settingId);

    void deleteWhereSetting(Long settingId);

    void deleteDefinitionSetting(Long settingId);

    void deleteOrderSetting(Long settingId);

    void deleteTargetSetting(Long settingId);

    ColumnOptionDTO  selectColumnByTableAndColumn(String sTableId, String hssCcolumn);
}
