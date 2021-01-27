package jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ConditionSearchMapper extends BaseMapper<MastDatadictionaryDO> {

    /**
     * 查询可用于选择的数据表
     * @return 可用于许纳泽的数据表
     */
    List<TableOptionDTO> selectTable();

    /**
     * 根据表名查询列名
     * @param tableName 表名
     * @return 表中的列
     */
    List<ColumnOptionDTO> selectColumnByTable(String tableName);

    /**
     * 查询条件式选择时可用的表
     * @return 条件式选择时可用的表
     */
    List<TableQueryDefinitionOptionDTO> selectTableForQueryDefinition();

    /**
     * 查询条件式选择时可用的列
     * @param tableName 表名
     * @return 条件式选择时可用的列
     */
    List<ColumnQueryDefinitionOptionDTO> selectColumnForQueryDefinition(String tableName);


    @Select("SELECT DISTINCT" +
            " MSW_CTABLEID as tableName," +
            " MSW_CCOLUMNID as columnName," +
            " PSMASTER.FUNC_GET_COLUMN_DESC(MSW_CCUSTOMERID,MSW_CTABLEID,MSW_CCOLUMNID,'ja') as columnDescription," +
            " MSW_CEMPLOYEE as employFlag," +
            " MSW_NSEQ as sort," +
            " MD_CTYPEOFCOLUMN as columnType," +
            " MD_CMASTERTBLNAME as dialogFlag," +
            " (" +
            "   SELECT DISTINCT HSW_CUSE FROM HIST_SEARCH_WHERE WHERE HSW_CCOLUMN = MSW_CCOLUMNID AND HSW_NSETTINGID = #{value}" +
            " ) as useFlag," +
            " DECODE(MG_CIFCOMPANYTYPE,'1','*','') as companyId" +
            " FROM" +
            " MAST_SEARCH_WHERE," +
            " MAST_DATADICTIONARY," +
            " MAST_GENERIC" +
            " WHERE" +
            " MSW_CCUSTOMERID = '01'" +
            " AND MSW_CTABLEID = MD_CTABLENAME" +
            " AND MSW_CCOLUMNID = MD_CCOLUMNNAME" +
            " AND MD_CMASTERTBLNAME = MG_CGENERICGROUPID_CK(+)" +
            " ORDER BY MSW_NSEQ")
    List<ConditionWhereDTO> selectWhereConditions(Long settingId);

    @Select("SELECT" +
            " HSW_CVALUE as value," +
            " HSW_CUSE as useFlag," +
            " PSMASTER.FUNC_GET_EMP_NAME('01','01',HSW_CVALUE,TRUNC(SYSDATE),'ja') as name" +
            " FROM" +
            " HIST_SEARCH_WHERE" +
            " WHERE HSW_CTABLE = #{table}" +
            "AND HSW_CCOLUMN = #{column}" +
            "AND HSW_NSETTINGID = #{settingId}")
    List<ConditionWhereOptionDTO> selectWhereOptionsForType(@Param("table") String tableName,@Param("column") String columnName,@Param("settingId") Long settingId);

    @Select("SELECT" +
            " HSW_CVALUE as value," +
            "HSW_CUSE as useFlag," +
            "PSMASTER.FUNC_GET_GENERICDETAIL_DESC_EX ('01','01',MD_CMASTERTBLNAME,SUBSTR(HSW_CVALUE, INSTR(HSW_CVALUE,'|') + 1),TRUNC(SYSDATE),'ja') as name" +
            " FROM"+
            " HIST_SEARCH_WHERE,MAST_DATADICTIONARY" +
            " WHERE" +
            " HSW_CTABLE = #{table}" +
            " AND HSW_CCOLUMN = #{column}" +
            " AND HSW_NSETTINGID = #{settingId}" +
            " AND HSW_CTABLE = MD_CTABLENAME" +
            " AND HSW_CCOLUMN = MD_CCOLUMNNAME")
    List<ConditionWhereOptionDTO> selectWhereOptionsForGeneric(@Param("table") String tableName,@Param("column") String columnName,@Param("settingId") Long settingId);

    @Select("SELECT" +
            " HSW_CVALUE as value," +
            " HSW_CUSE as useFlag" +
            " FROM" +
            " HIST_SEARCH_WHERE" +
            " WHERE" +
            " HSW_CTABLE = #{table}" +
            " AND HSW_CCOLUMN = #{column}" +
            " AND HSW_NSETTINGID = #{settingId}")
    List<ConditionWhereOptionDTO> selectWhereOptionsForInput(@Param("table") String tableName,@Param("column") String columnName,@Param("settingId") Long settingId);

}
