/*==============================================================================
 *	system      Progress@Site
 *	version     Ver4.0
 *  since       JDK1.5
 *	id          QueryConditionDto
 *  title       条件式定義コンポーネント用Dtoクラス
 *              条件式定義用の情報を格納する
 *
 *	author      isolyamada
 *	create      2007/10/23
 *				更新日		更新者			更新内容
 *	update      
 *	remark      
 =============================================================================*/

package jp.smartcompany.framework.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 
 * 条件式定義コンポーネント用Dtoクラス
 * @author isolyamada
 */
@Getter
@Setter
@ToString
public class QueryConditionDTO implements java.io.Serializable {

    private static final long serialVersionUID = 7390315249955421799L;
    /** 条件式設定情報一覧 */
    private List <QueryConditionRowDTO> rowList;
    /** 対象のレコード番号 */
    private String gsSelectedSeq;
    /** 選択された条件項目 */
    private String gsPeramId;
    /** 選択されたコード */
    private String gsSelectedId;
    /** 選択された名称 */
    private String gsSelectedName;
    /** 選択済のマスタ区分 */
    private String gsSelectedMaster = "";
    /** 選択済のデータ型 */
    private String gsSelectedType;
    /** 条件式選択画面(比較演算子) 表示フラグ(1:組織・役職 0:それ以外) */
    private int gnOperatorDisp;
    /** 条件式選択画面(指定値) 表示フラグ(0:単一法人 1:複数法人) */
    private int gnValueDisp;
    /** 条件式画面 表示基準日 */
    private String gsApplyDate;
    /** 選択されたテーブルID */
    private String gsSelectedTableId;
    /** 選択された法人コード */
    private String gsSelectedCompanyId;
    /** 選択された自分のフラグ */
    private String gsSelectedMyFlg;
    /** 全社区分 */
    private String gsAllCompanies;
    /** 表示中のアプリケーションID */
    private String gsDispAppId;
    
//    /** 一覧選択 - データディクショナリ(テーブル情報) */
//    private List <QueryConditionSelectDto> glMdTableList;
//    /** 一覧選択 - データディクショナリ(カラム情報) */
//    private List <QueryConditionSelectDto> glMdColumList;
//    /** 一覧選択 - 汎用詳細マスタ(コード・名称) */
//    private List <QueryConditionSelectDto> glMgMasterList;
//
//    /** 一覧選択 - 法人一覧 */
//    private List <QueryConditionSelectDto> glCompanyList;
//    /** 一覧選択 - 組織一覧(指定法人) */
//    private List <QueryConditionSelectDto> glSectionList;
//    /** 一覧選択 - 役職一覧(指定法人) */
//    private List <QueryConditionSelectDto> glPostCompList;
//
//    /** 条件式設定情報(単一法人用) 削除用一覧 */
//    private List <HistGroupDefinitionsEntity> glDeleteDefinitions;
//
//    /** 条件式設定情報(複数法人用) 削除用一覧 */
//    private List <MastDataPermissionDefsEntity> glDeleteDataPermissionDefs;
//
//    /** 条件式設定情報(自由条件検索用) 削除用一覧 */
//    private List <HistSearchDefinitionsEntity> glDeleteDataSearchDefs;
    /** Validator */
//    private Validator validator;

    /** 条件式設定情報一覧 (JSON形式) */
//    private String gsJSONData;
    
}
