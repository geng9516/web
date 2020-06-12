package jp.smartcompany.framework.compatible.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class V3CompatiblePostEntity {

    /** 異動歴.役職コード .*/
    private String hdCpostidFk;     //HD_CPOSTID_FK

    /** 基本情報.社員番号 .*/
    private String meCemployeeidCk;    //ME_CEMPLOYEEID_CK

    /** 役所マスタ.ウェイト .*/
    private Double mapNweightage; //MAP_NWEIGHTAGE

    /** 役所マスタ.役所名 .*/
    private String mapCpostname;     //MAP_CPOSTNAME

}
