package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.admin.component.dto.SectionPostRowDTO;
import jp.smartcompany.admin.component.dto.SectionPostRowListDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupsectionpostmappingDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * グループ定義条件マスタ（組織、役職） Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastGroupsectionpostmappingMapper extends BaseMapper<MastGroupsectionpostmappingDO> {

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
               @Param("customerId") String customerId,
               @Param("companyId") String companyId,
               @Param("systemId") String systemId,
               @Param("groupId") String groupId,
               @Param("searchDate") String searchDate,
               @Param("permissionId") String permissionId,
               @Param("language") String language);

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
               @Param("customerId") String customerId,
               @Param("companyId") String companyId,
               @Param("systemId") String systemId,
               @Param("groupId") String groupId,
               @Param("searchDate") String searchDate,
               @Param("permissionId") String permissionId,
               @Param("sectionId") String sectionId,
               @Param("language") String language);

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
         * @return  List<SectionPostRowListDto>  組織情報リスト
         */
       List<SectionPostRowListDTO> selectGroupSection(
               @Param("customerId") String customerId,
               @Param("companyId") String companyId,
               @Param("systemId") String systemId,
               @Param("groupId") String groupId,
               @Param("searchDate") String searchDate,
               @Param("permissionId") String permissionId,
               @Param("language") String language);

}
