package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerGroupDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerGroupListDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerModifiedDateDTO;
import jp.smartcompany.job.modules.core.pojo.bo.DBMastGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * グループ定義マスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastGroupMapper extends BaseMapper<MastGroupDO> {

        /**
         *根据语言标识获取所有角色组
         * @param language
         * @return
         */
        List<DBMastGroupBO> getUserGroupByLanguage(@Param("language") String language, @Param("systemCode") String systemCode);

        /**
         *根据语言标识和用户id获取角色组
         * @param language
         * @param userId
         * @return List<LoginUserGroupBO>
         */
        List<DBMastGroupBO> getPretreatGroupByLanguageUserId(@Param("language") String language, @Param("userId") String userId);

        List<GroupAppManagerGroupDTO> selectAppManagerGroup(@Param("customerId") String customerId,@Param("systemId") String systemId,
                                                            @Param("language") String language,
                                                            @Param("searchDate") String searchDate,
                                                            @Param("companyId") String companyId,
                                                            @Param("searchCompanyList") List<String> companyIds);

        List<GroupAppManagerGroupDTO> selectAppManagerDate(@Param("customerId") String customerId,
                                                           @Param("systemId") String systemId,
                                                           @Param("companyId") String companyId,
                                                           @Param("searchCompanyList") List<String> searchCompanyList);

        List<GroupManagerGroupListDTO> selectValidGroup(
                @Param("customerCode") String customerCode,
                @Param("systemId") String systemId,
                @Param("language") String language,
                @Param("searchDate") String searchDate,
                @Param("companyList") List<String> companyList
        );

        List<GroupManagerGroupListDTO> selectInvalidGroup(
                @Param("customerCode") String customerCode,
                @Param("systemId") String systemId,
                @Param("language") String language,
                @Param("companyList") List<String> companyList,
                @Param("validGroupList") List<String> validGroupList
        );

        List<GroupManagerModifiedDateDTO> selectHistoryDate(
                @Param("customerCode") String customerCode,
                @Param("systemId") String systemId,
                @Param("companyList") List<String> companyList,
                @Param("searchDate") String searchDate
        );
}
