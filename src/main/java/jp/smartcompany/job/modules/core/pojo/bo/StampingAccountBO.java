package jp.smartcompany.job.modules.core.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class StampingAccountBO implements Serializable {

    private String hdCcustomeridCk; //HD_CCUSTOMERID_CK
    private String hdCcompanyidCk; //HD_CCOMPANYID_CK
    private String macClayeredcompanyid; //MAC_CLAYEREDCOMPANYID
    private Integer macNseq; //MAC_NSEQ
    private String macCcompanyname; //MAC_CCOMPANYNAME
    private String hdCemployeeidCk; //HD_CEMPLOYEEID_CK
    private String hdCuserid; //HD_CUSERID
    private String meCemployeename; //ME_CEMPLOYEENAME
    private String meCkananame; //ME_CKANANAME
    private String hdCsectionidFk; //HD_CSECTIONID_FK
    private String moClayeredsectionid; //MO_CLAYEREDSECTIONID
    private Integer moNseq; //MO_NSEQ
    private String moCsectionname; //MO_CSECTIONNAME
    private String hdCpostidFk; //HD_CPOSTID_FK
    private Integer mapNweightage; //MAP_NWEIGHTAGE
    private String mapCpostname; //MAP_CPOSTNAME
    private String hdCifkeyoradditionalrole; //HD_CIFKEYORADDITIONALROLE
    private Date hdDstartdateCk; //HD_DSTARTDATE_CK
    private String hdCbossornot; //HD_CBOSSORNOT
    private String maCuserid; //MA_CUSERID
    private String workTypeName;

    private String encodePassword;

}
