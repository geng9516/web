package jp.smartcompany.job.modules.personalinformation.conditionsearch.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.ConditionSearch.ConditionSearchMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.*;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingTargetDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSettingDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IConditionSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConditionSearchServiceImpl extends ServiceImpl<ConditionSearchMapper, MastDatadictionaryDO>
implements IConditionSearchService {

    /* ================== 表示项目选择 Start ================ */
    @Override
    public List<TableOptionDTO> selectTable() {
        return baseMapper.selectTable();
    }

    @Override
    public List<ColumnOptionDTO> selectColumns(String tableName) {
        return baseMapper.selectColumnByTable(tableName);
    }
    /* ================== 表示项目选择 End ================ */


    /*
       =================================================
       -------------------  分隔符  --------------------
       =================================================
     */

    /* ================== 条件项目选择 Start ================ */

    /* >>条件を条件式で指定する Start */

    @Override
    public List<TableQueryDefinitionOptionDTO> selectTableForQueryDefinition() {
        return baseMapper.selectTableForQueryDefinition();
    }

    @Override
    public List<ColumnQueryDefinitionOptionDTO> selectColumnForQueryDefinition(String tableName) {
        return baseMapper.selectColumnForQueryDefinition(tableName);
    }

    /* 条件を条件式で指定する End */

    /* >>条件を項目選択で指定する Start */

    @Override
    public List<ConditionWhereDTO> selectWhereConditions(Long settingId) {
        // 表示するWhere句取得(簡易版)
        List<ConditionWhereDTO> defaultWhereConditions = baseMapper.selectWhereConditions(settingId);
        for (ConditionWhereDTO whereItem : defaultWhereConditions) {
            setDefaultWhereValue(whereItem,settingId);
        }
        return defaultWhereConditions;
    }

    private void setDefaultWhereValue(ConditionWhereDTO whereItem,Long settingId) {
       String employFlag = whereItem.getEmployFlag();
       String dialogFlag = whereItem.getDialogFlag();
       String table = whereItem.getTableName();
       String column = whereItem.getColumnName();

       List<ConditionWhereOptionDTO> whereOptionList;
       if (StrUtil.equals(MASTER_EMP, employFlag) && StrUtil.isBlank(dialogFlag)) {
           // 社員マスタの値を設定
           whereItem.setWhereType(EMPLOY);
           if (StrUtil.isBlank(table)) {
               table = "HIST_DESIGNATION";
           }
           if (StrUtil.isBlank(column)){
               column = "HD_CEMPLOYEEID_CK";
           }
           whereOptionList = baseMapper.selectWhereOptionsForType(table,column,settingId);
       } else if (StrUtil.isNotBlank(dialogFlag) && StrUtil.equals(MASTER_QPOST,dialogFlag)) {
           // 役職マスタの値を設定
           whereItem.setWhereType(POST);
           if (StrUtil.isBlank(table)) {
               table = "HIST_DESIGNATION";
           }
           if (StrUtil.isBlank(column)){
               column = "HD_CPOSTID_FK";
           }
           whereOptionList = baseMapper.selectWhereOptionsForType(table,column,settingId);
       } else if (StrUtil.isNotBlank(dialogFlag) && StrUtil.equals(MASTER_QSECTION,dialogFlag)){
           // 所属マスタの値を設定
           whereItem.setWhereType(SECTION);
           if (StrUtil.isBlank(table)) {
               table = "HIST_DESIGNATION";
           }
           if (StrUtil.isBlank(column)){
               column = "HD_CSECTIONID_FK";
           }
           whereOptionList = baseMapper.selectWhereOptionsForType(table,column,settingId);
       } else if ( StrUtil.equals(MASTER_NOEMP,employFlag) && StrUtil.isNotBlank(dialogFlag)) {
           // 名称マスタの値を設定
           whereItem.setWhereType(GENERIC);
           if (StrUtil.isBlank(table)) {
               table = "HIST_DESIGNATION";
           }
           if (StrUtil.isBlank(column)){
               column = "HD_NOFFICIALORNOT";
           }
           whereOptionList = baseMapper.selectWhereOptionsForGeneric(table,column,settingId);
       } else {
           // 手入力の値を設定
           whereItem.setWhereType(INPUT);
           if (StrUtil.isBlank(table)) {
               table = "HIST_DESIGNATION";
           }
           if (StrUtil.isBlank(column)){
               column = "HD_CIFKEYORADDITIONALROLE";
           }
           whereOptionList = baseMapper.selectWhereOptionsForInput(table,column,settingId);
       }
        whereItem.setOptionList(whereOptionList);
    }
    /* 条件を項目選択で指定する End */

    /* ================== 条件项目选择 End ================ */

      /*
       =================================================
       -------------------  分隔符  --------------------
       =================================================
     */

    /* ================== 共有设定 Start ================ */

    /**
     * 获取选择共有范围时可选的group列表
     * @param settingId
     * @return
     */
    @Override
    public Map<String,Object> showAddOrUpdate(Long settingId) {
        Map<String,Object> result = MapUtil.newHashMap();
        HistSearchSettingDO settings;
        long searchSettingId = 1L;
        if (settingId != null) {
            searchSettingId = settingId;
            settings = baseMapper.selectHistSearchSettingBySettingId(settingId);
            result.put("settings",settings);
        }
        List<ConditionSettingTargetDTO> groupOptions = baseMapper.selectConditionSettingTargetList(searchSettingId);
        result.put("groups",groupOptions);
        return result;
    }
    /* ================== 共有设定 End ================ */
}
