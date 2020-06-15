package jp.smartcompany.framework.auth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class LoginControlEntity {
    private String hdCcustomeridCk;
    private String hdCcompanyidCk;
    private String macClayeredcompanyid;
    private Integer macNseq;
    private String macCcompanyname;
    private String hdCemployeeidCk;
    private String hdCuserid;
    private String meCemployeename;
    private String meCkananame;
    private String hdCsectionidFk;
    private String moClayeredsectionid;
    private Integer moNseq;
    private String moCsectionname;
    private String hdCpostidFk;
    private Integer mapNweightage;
    private String mapCpostname;
    private String hdCifkeyoradditionalrole;
    private Date hdDstartdateCk;
    private String hdCbossornot;
    private String maCuserid;

}
