package jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.TableOptionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ConditionSearchMapper extends BaseMapper<MastDatadictionaryDO> {

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

}
