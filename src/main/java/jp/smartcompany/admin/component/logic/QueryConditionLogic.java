package jp.smartcompany.admin.component.logic;

import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.job.modules.core.pojo.entity.HistGroupdefinitionsDO;

import java.util.Date;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
public interface QueryConditionLogic {

    /**
     * DB新規登録(更新)処理用のEntity作成処理(グループ条件定義マスタ(条件式))<br>
     * 定義情報をDBへ反映させるためのEntityを作成する。（グループ判定用のクエリも作成）
     *
     * @param psCustomerId  顧客コード
     * @param psSystemId    システムコード
     * @param psGroupId     グループコード
     * @param ptStartDate   開始日
     * @param ptEndDate     終了日
     * @param poDto         条件式用Dto
     * @param pnCnt         シーケンス番号
     * @return HistGroupdefinitionsDO   更新用Entity
     */
    HistGroupdefinitionsDO insertGroupDefinitions(String psCustomerId,
                                                  String psSystemId, String psGroupId, Date ptStartDate,
                                                  Date ptEndDate, QueryConditionRowDTO poDto, int pnCnt, String psCompanyid);

    /**
     * クエリ作成処理(条件式定義)<br>
     * 条件式定義での設定内容を元に、検索クエリを作成する。
     *
     * @param psCustomerId  顧客コード
     * @param psCompanyId   法人コード
     * @param psSystemId    システムコード
     * @param psGroupId     グループコード
     * @param pdSearchDate  今回改定日
     * @return  String      グループ判定用クエリ(条件式定義)
     */
    String createQueryCondition(String psCustomerId, String psCompanyId,
                                String psSystemId, String psGroupId, Date pdSearchDate, List<QueryConditionRowDTO> queryConditionList);
}
