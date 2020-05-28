package jp.smartcompany.job.modules.core.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Designation {

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

    /** 社員番号 */
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
