package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerGroupDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerGroupListDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerModifiedDateDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerSortDTO;
import jp.smartcompany.job.modules.core.pojo.bo.DBMastGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ定義マスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastGroupService extends IService<MastGroupDO> {

        /**
         * 根据语言标识获取所有角色组
         * @param language
         * @return
         */
        List<DBMastGroupBO> getUserGroupByLanguage(String language, String systemCode);

        /**
         * 根据语言标识和用户id获取角色组
         * @param language
         * @param userId
         * @return
         */
        List<DBMastGroupBO> getPretreatGroupByLanguageUserId(String language, String userId);

        /**
         * グループ一覧取得
          */
        List<GroupAppManagerGroupDTO> selectAppManagerGroup(String customerId, String systemId, String language,
        Date searchDate, String companyId,List<String> companyIds);


        /**
         * 現在、有効なグループについての情報を取得
         * @author  isolyamada
         * @param   customerCode  顧客コード
         * @param   systemId      システムID
         * @param   language      言語区分
         * @param   searchDate    今回改定日
         * @param   companyList  参照可能な法人一覧
         * @return  List < GroupManagerGroupListDto >  グループリスト(有効)
         */
        List<GroupManagerGroupListDTO> selectValidGroup(
                String customerCode,
                String systemId,
                String language,
                String searchDate,
                List<String> companyList
        );

        /**
         * 現在、無効なグループについての情報を取得
         *
         * @author  isolyamada
         * @param   customerCode  顧客コード
         * @param   systemId      システムID
         * @param   language      言語区分
         * @param   validGroup    有効なグループ一覧
         * @param   validCompany  参照可能な法人一覧
         * @return  List < GroupManagerGroupListDto >  グループリスト(無効)
         * @exception
         */
        List<GroupManagerGroupListDTO> selectInvalidGroupList(
                String customerCode,
                String systemId,
                String language,
                List<String> validGroup,
                List<String> validCompany
        );


        /**
         * グループの歴日付一覧を取得
         *
         * @author  isolyamada
         * @param   customerCode  顧客コード
         * @param   systemId      システムID
         * @param   searchDate    今回改定日
         * @param   companyList  参照可能な法人一覧
         * @return  List < GroupManagerModifiedDateDto >  歴日付一覧
         */
        List<GroupManagerModifiedDateDTO> selectHistoryDate(
                String customerCode,
                String systemId,
                List<String> companyList,
                String searchDate
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
                String customerCode, String systemId, String language, String groupId, Date searchDate, List<String> companyList
        );

        int selectGroupExists(String customerId,String systemId,String groupId);

        /**
         * グループ全体の優先順位を更新
         * (グループが削除された場合のみ)
         */
        int updateGroupPrionityLevel(String searchDate,
                                      String custId,
                                      String systemId);

        /**
         * 指定されたグループの画面入力情報(順序)を更新
         */
        int updateGroupSort(List<GroupManagerSortDTO> list);
}
