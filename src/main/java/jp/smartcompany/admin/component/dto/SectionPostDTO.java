package jp.smartcompany.admin.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 該当条件編集画面(組織・役職定義)用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class SectionPostDTO {


    /** 各定義ごと情報一覧 */
    private List<SectionPostSelectDTO> glMenuList;

    /** 組織・役職条件選択情報 格納リスト 法人一覧 */
    private List <SectionPostListDTO> glCompanyList;

    /** 選択された定義ID */
    private String gsPermissionId;

    /** 選択中の親情報 組織コード */
    /** (検索対象範囲設定の場合は法人コード) */
    private String gsParentId;

    /** 選択中の子情報 組織コード(検索対象範囲設定のみ) */
    private String gsChildId;

    /** 対象のレコード番号 */
    private String gsSelectedSeq;

    /** 組織・役職画面 表示基準日 */
    private String gsApplyDate;

    /** 選択されたコード */
    private String gsSelectedId;

    /** 選択された名称 */
    private String gsSelectedName;

    /** 選択された法人コード */
    private String gsSelectedCompanyId;

    /** 選択された法人名称 */
    private String gsSelectedCompanyName;


    /** 表示中のアプリケーションID */
    private String gsDispAppId;

    /** 表示フラグ (0:単一法人 1:複数法人) */
    private int gnDispFlg;

}
