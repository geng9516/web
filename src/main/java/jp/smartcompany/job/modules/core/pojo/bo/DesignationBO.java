package jp.smartcompany.job.modules.core.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 *  id          Designation
 *  title       異動歴情報格納クラス。
 *              異動歴情報機能は、ログインユーザおよび評価者、評価対象者の異動歴情報を保持し、
 *              外部からの要求によって保持する情報を受け渡すことを目的とする。
 *  @author      Xiao Wenpeng
 *  #2213 評価者情報格納クラス作成による対応
 *  判定区分」「評価レベル」を評価者情報格納クラス
 *  (Evaluator.java)へ移動
 *
 */
@ToString
@Getter
@Setter
public class DesignationBO {

    /** 顧客コード */
    private String customerCode;

    /** 法人コード */
    private String companyCode;

    /** 法人内部階層コード */
    private String companyHierarchy;

    /** 法人並び順 */
    private String companyOrder;

    /** 法人名称 */
    private String companyName;

    /** 職員番号 */
    private String employee;

    /** ユーザID */
    private String userid;

    /** 氏名 */
    private String name;

    /** 氏名カナ */
    private String nameKana;

    /** 組織コード */
    private String section;

    /** 組織内部階層コード */
    private String sectionHierarchy;

    /** 組織並び順 */
    private String sectionOrder;

    /** 組織名称 */
    private String sectionName;

    /** 役職コード */
    private String postCode;

    /** 役職順位 */
    private Integer postRank;

    /** 役職名称 */
    private String postName;

    /** 本務兼務区分コード */
    private String attachRole;

    /** 異動歴開始日取得 */
    private Date personnelChangesBigin;

    /** 異動歴終了日取得 */
    private Date endDate;

    /** 所属長フラグ */
    private String bossOrNot;

}
