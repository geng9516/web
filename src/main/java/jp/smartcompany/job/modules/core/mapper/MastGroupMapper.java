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

        /**
         * 現在、有効なグループについての情報を取得
         *
         * @author  isolyamada
         * @param   customerCode  顧客コード
         * @param   systemId      システムID
         * @param   language      言語区分
         * @param   searchDate    今回改定日
         * @param   companyList  参照可能な法人一覧
         * @return  List < GroupManagerGroupListDto >  グループリスト(有効)
         */
        List<GroupManagerGroupListDTO> selectValidGroup(
                @Param("customerCode") String customerCode,
                @Param("systemId") String systemId,
                @Param("language") String language,
                @Param("searchDate") String searchDate,
                @Param("companyList") List<String> companyList
        );

        /**
         * 現在、無効なグループについての情報を取得
         *
         * @author  isolyamada
         * @param   customerCode  顧客コード
         * @param   systemId      システムID
         * @param   language      言語区分
         * @param   validGroupList    有効なグループ一覧
         * @param   companyList  参照可能な法人一覧
         * @return  List<GroupManagerGroupListDto>  グループリスト(無効)
         */
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

        /**
         * 今回改定日が未入力(NULL)のときは、指定されたグループの歴情報を取得
         * 今回改定日が入力済のときは、今回改定日時点の指定されたグループ情報を取得
         *
         * @param   customerCode  顧客コード
         * @param   systemId      システムID
         * @param   language      言語区分
         * @param   groupId       グループID
         * @param   searchDate    今回改定日
         * @param   companyList  参照可能な法人一覧
         * @return  List<GroupManagerGroupListDTO>  グループ履歴リスト
         */
        List<GroupManagerGroupListDTO> selectGroupHistoryList(
                @Param("customerCode") String customerCode,
                @Param("systemId") String systemId,
                @Param("language") String language,
                @Param("groupId") String groupId,
                @Param("searchDate") String searchDate,
                @Param("companyList") List<String> companyList
        );
}
