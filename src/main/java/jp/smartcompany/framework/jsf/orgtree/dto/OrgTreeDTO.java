package jp.smartcompany.framework.jsf.orgtree.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * サイトコンポーネントの組織ツリーーDtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class OrgTreeDTO implements Serializable {

    private static final long serialVersionUID = -2430790659992130022L;
    //プロパティ変数
    private BigDecimal moId; //MO_ID
    private String moCcustomeridCkFk; //MO_CCUSTOMERID_CK_FK
    private String moCcompanyidCkFk; //MO_CCOMPANYID_CK_FK
    private String moCsectionidCk; //MO_CSECTIONID_CK
    private String moClayeredsectionid; //MO_CLAYEREDSECTIONID
    private String moCsectionname; //MO_CSECTIONNAME
    private String moCsectionnameja; //MO_CSECTIONNAMEJA
    private String moCsectionnameen; //MO_CSECTIONNAMEEN
    private String moCsectionnamech; //MO_CSECTIONNAMECH
    private String moCsectionname01; //MO_CSECTIONNAME01
    private String moCsectionname02; //MO_CSECTIONNAME02
    private String moCsectionnick; //MO_CSECTIONNICK
    private String moCsectionnickja; //MO_CSECTIONNICKJA
    private String moCsectionnicken; //MO_CSECTIONNICKEN
    private String moCsectionnickch; //MO_CSECTIONNICKCH
    private String moCsectionnick01; //MO_CSECTIONNICK01
    private String moCsectionnick02; //MO_CSECTIONNICK02
    private String moClanguage; //MO_CLANGUAGE
    private Timestamp moDstart; //MO_DSTART
    private Timestamp moDend; //MO_DEND
    private String moCparentid; //MO_CPARENTID
    private BigDecimal moNlevel; //MO_NLEVEL
    private BigDecimal moNseq; //MO_NSEQ
    private BigDecimal moNhr; //MO_NHR
    private String moCmodifieruserid; //MO_CMODIFIERUSERID
    private Timestamp moDmodifieddate; //MO_DMODIFIEDDATE
    private String moCsparechar1; //MO_CSPARECHAR1
    private String moCsparechar2; //MO_CSPARECHAR2
    private String moCsparechar3; //MO_CSPARECHAR3
    private String moCsparechar4; //MO_CSPARECHAR4
    private String moCsparechar5; //MO_CSPARECHAR5
    private BigDecimal moNsparenum1; //MO_NSPARENUM1
    private BigDecimal moNsparenum2; //MO_NSPARENUM2
    private BigDecimal moNsparenum3; //MO_NSPARENUM3
    private BigDecimal moNsparenum4; //MO_NSPARENUM4
    private BigDecimal moNsparenum5; //MO_NSPARENUM5
    private Timestamp moDsparedate1; //MO_DSPAREDATE1
    private Timestamp moDsparedate2; //MO_DSPAREDATE2
    private Timestamp moDsparedate3; //MO_DSPAREDATE3
    private Timestamp moDsparedate4; //MO_DSPAREDATE4
    private Timestamp moDsparedate5; //MO_DSPAREDATE5
    private Integer versionNo; //VERSIONNO
    /** 法人名称 */
    private String macCcompanyname;

}
