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

    @Select("<script>" +
                "SELECT " +
                "MAC_CCOMPANYID_CK as id," +
                "PSMASTER.FUNC_GET_COMP_NAME('01',MAC_CCOMPANYID_CK,MAC_DSTART,'ja') as name " +
                "FROM " +
                "MAST_COMPANY " +
                "WHERE " +
                "MAC_CCUSTOMERID_CK_FK = '01'" +
                "AND MAC_CCOMPANYID_CK IN <foreach item='item' index='index' collection='companyIds' open='(' separator=',' close=')'>#{item}</foreach> " +
                "AND MAC_DEND = TO_DATE('2222/12/31')" +
                "AND MAC_CLANGUAGE = 'ja'" +
                "ORDER BY MAC_CCOMPANYID_CK" +
            "</script>")
    List<CompanyDTO> selectCompanyList(@Param("companyIds") List<String> companyIds);

    @Select("SELECT PSMASTER.FUNC_GET_TABLE_DESC('01',a.MD_CTABLENAME,'ja') as description," +
            "a.MD_CTABLENAME as name " +
            "FROM(" +
            "SELECT DISTINCT(MD_CTABLENAME),MD_CCUSTOMERID FROM MAST_DATADICTIONARY" +
            " WHERE MD_CCUSTOMERID = '01' AND MD_CAVLFORCKSTART = '1') a,MAST_DATADICTBL b" +
            " WHERE" +
            " a.MD_CCUSTOMERID = b.MDT_CCUSTOMERID AND" +
            " a.MD_CTABLENAME = b.MDT_CTABLENAME" +
            " ORDER BY " +
            " b.MDT_NTABLESEQ,a.MD_CTABLENAME")
    List<TableOptionDTO> selectTable();

    @Select("SELECT MD_CTABLENAME as tableName,MD_CCOLUMNNAME as columnName,PSMASTER.FUNC_GET_COLUMN_DESC('01',MD_CTABLENAME," +
            "MD_CCOLUMNNAME,'ja') as columnFieldName" +
            " FROM MAST_DATADICTIONARY" +
            " WHERE" +
            " MD_CCUSTOMERID= '01'" +
            " AND MD_CAVLFORCKSTART = '1'" +
            " AND MD_CTABLENAME = #{value}" +
            " ORDER BY" +
            " MD_NTABLESEQ,MD_CTABLENAME,MD_NSEQ")
    List<ColumnOptionDTO> selectColumns(String tableName);

    @Select("SELECT " +
            "HSS_CCOLUMN as columnName, " +
            "PSMASTER.FUNC_GET_COLUMN_DESC('01',MD_CTABLENAME,HSS_CCOLUMN,'ja') as columnDescription "+
            "FROM " +
            "HIST_SEARCH_SELECT," +
            "MAST_DATADICTIONARY " +
            "WHERE " +
            "HSS_CCOLUMN = MD_CCOLUMNNAME " +
            "AND HSS_NSETTINGID = #{value} " +
            "ORDER BY SS_NSEQ")
    List<ConditionSelectDTO> selectDefaultSelectConditions(Long settingId);

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
    List<ConditionWhereDTO> selectDefaultWhereConditions(Long settingId);

    @Select("SELECT " +
            "HSD_ID as id," +
            "HSD_NSETTINGID AS settingId," +
            "HSD_CANDOR AS andOr," +
            "HSD_COPENEDPARENTHSIS AS leftParentheses," +
            "HSD_CTABLEID AS tableId," +
            "HSD_CCOLUMNID AS columnId," +
            "HSD_CCOLUMNNAME AS columnName," +
            "HSD_CTYPEOFCOLUMN AS columnType," +
            "HSD_COPERATOR AS operator," +
            "HSD_CVALUE AS value," +
            "HSD_CDISPLAYVALUE AS displayValue," +
            "HSD_CCLOSEDPARENTHSIS AS rightParentheses," +
            "HSD_NSEQ AS SEQ " +
            "FROM HIST_SEARCH_DEFINITIONS " +
            "WHERE " +
            "HSD_NSETTINGID = #{value}" +
            "ORDER BY HSD_NSEQ"
    )
    List<ConditionQueryOptionDTO> selectQueryWhereConditions(Long settingId);

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

    @Select("SELECT " +
            "HSO_CCOLUMN columnName," +
            "PSMASTER.FUNC_GET_COLUMN_DESC('01',MD_CTABLENAME,HSO_CCOLUMN,'ja') as columnDescription," +
            "HSO_CORDER as orderBy " +
            "FROM " +
            "HIST_SEARCH_ORDER,MAST_DATADICTIONARY " +
            "WHERE HSO_CCOLUMN = MD_CCOLUMNNAME AND HSO_NSETTINGID = #{settingId}" +
            "ORDER BY HSO_NSEQ")
    List<OrderConditionDTO> selectOrderConditions(Long settingId);

}
