package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.admin.component.dto.BaseSectionRowDTO;
import jp.smartcompany.job.modules.core.pojo.bo.GroupBaseSectionBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupbasesectionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * グループ別基点組織マスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Mapper
public interface MastGroupbasesectionMapper extends BaseMapper<MastGroupbasesectionDO> {

   List<GroupBaseSectionBO> getBaseSectionByGroupCode(@Param("customerId") String customerId,
                                                      @Param("systemCode") String systemCode,
                                                      @Param("groupCode") String groupCode,
                                                      @Param("date") String date);

   List<BaseSectionRowDTO> selectGroupBaseSectionCompanyList(
           @Param("custId") String psCustomerId,
           @Param("systemId") String psSystemId,
           @Param("groupId") String psGroupId,
           @Param("language") String psLanguage,
           @Param("searchDate") String searchDate,
           @Param("companyList") List<String> plValidCompany
   );

}
