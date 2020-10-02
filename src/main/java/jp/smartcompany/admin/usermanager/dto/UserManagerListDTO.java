package jp.smartcompany.admin.usermanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserManagerListDTO {

    //プロパティ変数
    /** カラム：ME_CCUSTOMERID_CK*/
    private String meCcustomeridCk; //ME_CCUSTOMERID_CK
    /** カラム：ME_CCOMPANYID*/
    private String meCcompanyid; //ME_CCOMPANYID
    /** カラム：ME_CEMPLOYEEID_CK*/
    private String meCemployeeidCk; //ME_CEMPLOYEEID_CK
    /** カラム：ME_CUSERID*/
    private String meCuserid; //ME_CUSERID
    /** カラム：ME_CKANANAME*/
    private String meCkananame; //ME_CKANANAME
    /** カラム：ME_CKANJINAME*/
    private String meCname; //ME_CNAME
    /** カラム：ME_DDATEOFRETIREMENT*/
    private Date meDdateofretirement; //ME_DDATEOFRETIREMENT
    /** カラム：ME_CMAIL*/
    private String meCmail; //ME_CMAIL
    /** カラム：ME_ID*/
    private Long meId; //ME_ID
    /** カラム：MA_ID*/
    private Long maId; //MA_ID
    /** カラム：MA_CUSERID*/
    private String maCuserid; //MA_CUSERID
    /** カラム：MA_CACCOUNT*/
    private String maCaccount; //MA_CACCOUNT
    /** カラム：MA_DSTART*/
    private Date maDstart; //MA_DSTART
    /** カラム：MA_DEND*/
    private Date maDend; //MA_DEND
    /** カラム：MA_NRETRYCOUNTER*/
    private Integer maNretrycounter; //MA_NRETRYCOUNTER
    /** カラム：MA_NPASSWORDLOCK*/
    private Integer maNpasswordlock; //MA_NPASSWORDLOCK
    /** カラム：MA_CADMINUSER*/
    private String maCadminuser; //MA_CADMINUSER
    /** カラム：ME_DDATEOFEMPLOYEMENT*/
    private Date meDdateofemployement; //ME_DDATEOFEMPLOYEMENT
    /** カラム：ME_DSTARTDATE*/
    private Date meDstartdate; //ME_DSTARTDATE

}
