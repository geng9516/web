package jp.smartcompany.job.modules.personalinformation.conditionsearch.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.ConditionSearchMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.*;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IConditionSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionSearchServiceImpl extends ServiceImpl<ConditionSearchMapper, MastDatadictionaryDO>
implements IConditionSearchService {

    private final ScCacheUtil cacheUtil;

    @Override
    public List<CompanyDTO> selectCompanyList(List<String> companyIds) {
        return baseMapper.selectCompanyList(companyIds);
    }

    @Override
    public List<TableOptionDTO> selectTable() {
        return baseMapper.selectTable();
    }

    @Override
    public List<ColumnOptionDTO> selectColumns(String tableName) {
        return baseMapper.selectColumns(tableName);
    }

    /**
     * 就业暂时未用到
     */
    @Override
    public List<ConditionSelectDTO> selectDefaultSelectConditions(Long settingId) {
        return baseMapper.selectDefaultSelectConditions(settingId);
    }

    @Override
    public List<ConditionWhereDTO> selectDefaultWhereConditions(Long settingId) {
        // 表示するWhere句取得(簡易版)
        List<ConditionWhereDTO> defaultWhereConditions = baseMapper.selectDefaultWhereConditions(settingId);
        for (ConditionWhereDTO whereItem : defaultWhereConditions) {
            setDefaultWhereValue(whereItem,settingId);
        }
        return defaultWhereConditions;
    }

    @Override
    public ConditionQueryDTO selectQueryWhereConditions(Long settingId, Date searchDate) {
        List<ConditionQueryOptionDTO> queryWhereConditions = baseMapper.selectQueryWhereConditions(settingId);
        return setQueryWhereConditions(queryWhereConditions,searchDate);
    }

    @Override
    public List<OrderConditionDTO> selectOrderConditions(Long settingId) {
        List<OrderConditionDTO> orderConditionList = baseMapper.selectOrderConditions(settingId);
        for (OrderConditionDTO orderItem : orderConditionList) {
            // 並び順判定
            if(StrUtil.equals(orderItem.getOrderBy(),"0")){
                orderItem.setColumnDescription(orderItem.getColumnDescription() + "(昇)");
                orderItem.setColumnName(orderItem.getColumnName() + " ASC");
            }else{
                orderItem.setColumnDescription(orderItem.getColumnDescription() + "(降)");
                orderItem.setColumnName(orderItem.getColumnName() + " DESC");
            }
        }
        return orderConditionList;
    }






    private ConditionQueryDTO setQueryWhereConditions(List<ConditionQueryOptionDTO> queryWhereConditions, Date searchDate) {
        ConditionQueryDTO conditionQueryDTO = new ConditionQueryDTO();
        conditionQueryDTO.setApplyDate(SysUtil.transDateToString(searchDate));
        conditionQueryDTO.setCompanyId("01");
        for (ConditionQueryOptionDTO whereItem : queryWhereConditions) {
            String key = "01_"+ whereItem.getColumnId();
            // データディクショナリより、マスターテーブル区分を取得
            // キー："顧客コード" + _ + "カラム名"
            String tableName = cacheUtil.getDataDictionary(key).getMdCmastertblname();
            whereItem.setTableName(tableName);
        }
        conditionQueryDTO.setOptionList(queryWhereConditions);
        return conditionQueryDTO;
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

}
