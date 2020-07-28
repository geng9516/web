package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.admin.component.dto.SectionPostRowDTO;
import jp.smartcompany.admin.component.dto.SectionPostRowListDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupsectionpostmappingDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ定義条件マスタ（組織、役職） 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastGroupsectionpostmappingService extends IService<MastGroupsectionpostmappingDO> {

        /**
         * グループ条件定義マスタ(組織、役職)の各定義情報を取得
         *
         * @author  Xiao Wenpeng
         * @param   customerId    顧客コード
         * @param   companyId     法人コード
         * @param   systemId      システムID
         * @param   groupId       グループID
         * @param   searchDate    今回改定日
         * @param   permissionId  定義ID
         * @param   language      言語区分
         * @return  List < SectionPostRowDto >  定義情報リスト
         */
        List<SectionPostRowDTO> selectGroupSectionPost(
                String customerId,
                String companyId,
                String systemId,
                String groupId,
                Date searchDate,
                String permissionId,
                String language);

        /**
         * 組織情報ごとのグループ条件定義マスタ(組織、役職)の各定義情報を取得
         *
         * @author  Xiao Wenpeng
         * @param   customerId    顧客コード
         * @param   companyId     法人コード
         * @param   systemId      システムID
         * @param   groupId       グループID
         * @param   searchDate    今回改定日
         * @param   permissionId  定義ID
         * @param   sectionId     組織ID
         * @param   language      言語区分
         * @return  List < SectionPostRowDto >  定義情報リスト(組織情報ごと)
         */
        List<SectionPostRowDTO> selectWholeSectionInfo(
                String customerId,
                String companyId,
                String systemId,
                String groupId,
                Date searchDate,
                String permissionId,
                String sectionId,
                String language);

        /**
         * グループ条件定義マスタ(組織、役職)の組織情報を取得
         *
         * @author  Xiao Wenpeng
         * @param   customerId    顧客コード
         * @param   companyId     法人コード
         * @param   systemId      システムID
         * @param   groupId       グループID
         * @param   searchDate    今回改定日
         * @param   permissionId  定義ID
         * @param   language      言語区分
         * @return  List<SectionPostRowListDTO> 組織情報リスト
         */
        List<SectionPostRowListDTO> selectGroupSection(
                String customerId,
                String companyId,
                String systemId,
                String groupId,
                Date searchDate,
                String permissionId,
                String language);

        /**
         * グループ条件定義マスタ(組織、役職)の指定された情報をすべて削除します
         *
         * @author  isolyamada
         * @param   psCustomerId    顧客コード
         * @param   psCompanyId     法人コード
         * @param   psSystemId      システムID
         * @param   psGroupId       グループID
         * @param   pdSearchDate    今回改定日
         * @return  int             更新件数
         * @exception
         */
        int deleteGroupSectionPostAll(String psCustomerId, String psCompanyId,
                                  String psSystemId, String psGroupId, Date pdSearchDate);

        /**
         * グループ条件定義マスタ(組織、役職)の定義区分(複数指定）ごとの削除処理
         *
         * @param   psCustomerId 顧客コード
         * @param   psSystemId   システムコード
         * @param   psGroupId    グループID
         * @param   psTypeIdList     定義区分(複数指定)
         * @param   pdSearchDate    今回改定日
         * @return  グループ条件定義マスタ取得(組織、役職)
         * @exception
         */
        int deleteSectionPostTypeList(String psCustomerId, String psSystemId,
                                      String psGroupId, List<String> psTypeIdList, Date pdSearchDate);

        /**
         * グループ条件定義マスタ(組織、役職)の定義区分(複数指定）ごとの削除処理
         *
         * @param   customerId 顧客コード
         * @param   systemId   システムコード
         * @param   groupId    グループID
         * @param   typeId     定義区分(複数指定)
         * @param   startDate    今回改定日
         */
        void deleteSectionPostType(
                String customerId,String systemId,String groupId, String typeId, Date startDate);

}
