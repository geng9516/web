package jp.smartcompany.admin.groupmanager.logic;

import jp.smartcompany.admin.component.dto.SectionPostListDTO;
import jp.smartcompany.admin.component.dto.SectionPostRowDTO;
import jp.smartcompany.admin.component.dto.SectionPostRowListDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupsectionpostmappingDO;

import java.util.Date;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
public interface SectionPostLogic {

    /**
     * DB新規登録(更新)処理用のEntity作成処理(グループ条件定義マスタ(組織・役職))<br>
     * 定義情報をDBへ反映させるためのEntityを作成する。(組織登録専用)
     *
     * @param poList        更新対象リスト
     * @param psCustomerId  顧客コード
     * @param psSystemId    システムID
     * @param psGroupId     グループID
     * @param ptStartDate   開始日
     * @param ptEndDate     終了日
     * @param pnCnt         シーケンス番号
     * @return MastGroupSectionPostMappingEntity  更新用Entity
     *
     */
    MastGroupsectionpostmappingDO insertSection(List<SectionPostRowListDTO> poList,
                                                String psCustomerId, String psSystemId, String psGroupId,
                                                Date ptStartDate, Date ptEndDate, int pnCnt);

    /**
     * DB新規登録(更新)処理用のEntity作成処理(グループ条件定義マスタ(組織・役職))<br>
     * 定義情報をDBへ反映させるためのEntityを作成する。
     *
     * @param poList        更新対象リスト
     * @param psTypeId      定義ID
     * @param psCustomerId  顧客コード
     * @param psSystemId    システムID
     * @param psGroupId     グループID
     * @param ptStartDate   開始日
     * @param ptEndDate     終了日
     * @param pnCnt         シーケンス番号
     * @return MastGroupSectionPostMappingEntity  更新用Entity
     */
    MastGroupsectionpostmappingDO insertSectionPost(
            List<SectionPostRowDTO> poList, String psTypeId,
            String psCustomerId, String psSystemId, String psGroupId,
            Date ptStartDate, Date ptEndDate, int pnCnt);


    /**
     * クエリ作成処理(組織・役職結合式)<br>
     * 組織・役職定義での設定内容を元に、検索クエリを作成する。
     *
     * @param psCustomerId  顧客コード
     * @param psSystemId    システムID
     * @param psGroupId     グループID
     * @param ptStartDate   開始日
     * @param ptEndDate     終了日
     * @return  String グループ判定クエリ
     */
    String createQuery(String psCustomerId, String psSystemId,
                       String psGroupId, Date ptStartDate, Date ptEndDate, List<SectionPostListDTO> sectionPostCompanyList, String companyId);

}
