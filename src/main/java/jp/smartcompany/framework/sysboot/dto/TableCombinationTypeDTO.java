package jp.smartcompany.framework.sysboot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class TableCombinationTypeDTO {

    /** テーブルID */
    private String tableName;
    private String columnName;
    /** IDカラム */
    private String idColumnName;
    /** 顧客コードカラム */
    private String customerIdColumnName;
    /** 法人コードカラム */
    private String companyIdColumnName;
    /** ユーザIDカラム */
    private String userIdColumnName;
    /** 職員番号カラム */
    private String employeeIdColumnName;
    /** 所属コードカラム */
    private String sectionIdColumnName;
    /** 役職コードカラム */
    private String postIdColumnName;
    /** 開始日カラム */
    private String startDateColumnName;
    /** 終了日カラム */
    private String endDateColumnName;
    /** 言語区分カラム */
    private String languageColumnName;
    /** 組織内部階層コードカラム */
    private String layeredSectionIdColumnName;
    /** 本務兼務区分カラム */
    private String ifKeyOrAdditionalRoleColumnName;

}
