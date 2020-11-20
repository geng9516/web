package jp.smartcompany.job.modules.core.mapper.MastCompany;

import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 法人ツリーマスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastCompanyMapper extends BaseMapper<MastCompanyDO> {

       List<MastCompanyDO> selectCompanyInfo(@Param("custId") String sCustid,@Param("language") String sLanguage,@Param("date") String sDate);

       List<HistDesignationDO> selectTargetCompany(@Param("existsSql") String existsSql,
                                                   @Param("searchDate") String searchDate);

       List<HistDesignationDO> selectAllCompany(@Param("custId") String custId,
                                                   @Param("searchDate") String searchDate);

       List<MastCompanyDO> selectCompanyList(@Param("custId") String customerId,
                                             @Param("language") String language,
                                             @Param("date") String date,
                                             @Param("companies") List<String> companyList);

       @Select("SELECT PSMASTER.FUNC_GET_COMP_NAME('01',psCompanyId'01','${searchDate}', 'ja') AS MAC_CCOMPANYNAME FROM DUAL")
       String getCompanyName(String searchDate);
}
