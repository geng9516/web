package jp.smartcompany.framework.jsf.orgtree.logic;

import jp.smartcompany.framework.jsf.orgtree.dto.OrgTreeDTO;

import java.util.List;

/**
 * 組織ツリーコンポーネントのLogicインターフェース.<br>
 * @author Xiao Wenpeng
 *
 */
public interface OrgTreeListLogic {

    /**
     * 取得結果を返却.
     * @param psCustomerId 顧客コード
     * @param psLanguage 言語区分
     * @param psCompanyId 法人コード
     * @param pdSearchDate 検索基準日
     * @param useSearchRange 検索対象範囲を掛けるかどうか(false:掛けない それ以外掛ける)
     * @param searchRange 検索対象範囲種別(1:法人絞込み検索範囲 それ以外:検索対象範囲)
     * @return List<OrgTreeDTO>
     **/
    List<OrgTreeDTO> getOrgTreeList(
            String psCustomerId,
            String psLanguage,
            String psCompanyId,
            String pdSearchDate,
            String psCompanyCode,
            String psSectionCode,
            Boolean useSearchRange,
            String searchRange
    );

    /**
     * 選択中の法人の取得結果を返却.
     * @param psCustomerId 顧客コード
     * @param psLanguage 言語区分
     * @param psCompanyId 法人コード
     * @param searchDate 検索基準日
     * @param exists 検索条件範囲
     * @return String
     **/
    List <OrgTreeDTO> getSelCompOrgTreeList(
            String psCustomerId,
            String psLanguage,
            String psCompanyId,
            String searchDate,
            String exists
    );

}
