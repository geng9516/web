package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 役職マスタ
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mast_post")
@KeySequence("MAST_POST_SEQ")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MastPostDO implements Serializable {

    private static final long serialVersionUID = 3489556974953895316L;

    /**
     * ｉｄカラム
     */
    @TableId(value = "map_id")
    private Long mapId;

    private String mapCcustomeridCkFk;

    private String mapCcompanyidCkFk;

    private Long mapNweightage;

    private String mapCpostidCk;

    private String mapClanguage;

    private Date mapDstart;

    private Date mapDend;

    private String mapCpostname;

    private String mapCpostnameja;

    private String mapCpostnameen;

    private String mapCpostnamech;

    private String mapCpostname01;

    private String mapCpostname02;

    private String mapCpostnick;

    private String mapCpostnickja;

    private String mapCpostnicken;

    private String mapCpostnickch;

    private String mapCpostnick01;

    private String mapCpostnick02;

    private String mapCmodifieruserid;

    private Date mapDmodifieddate;

    private String mapCsparedesc1;

    private String mapCsparedesc1ja;

    private String mapCsparedesc1en;

    private String mapCsparedesc1ch;

    private String mapCsparedesc101;

    private String mapCsparedesc102;

    private String mapCsparedesc2;

    private String mapCsparedesc2ja;

    private String mapCsparedesc2en;

    private String mapCsparedesc2ch;

    private String mapCsparedesc201;

    private String mapCsparedesc202;

    private String mapCsparedesc3;

    private String mapCsparedesc3ja;

    private String mapCsparedesc3en;

    private String mapCsparedesc3ch;

    private String mapCsparedesc301;

    private String mapCsparedesc302;

    private String mapCsparedesc4;

    private String mapCsparedesc4ja;

    private String mapCsparedesc4en;

    private String mapCsparedesc4ch;

    private String mapCsparedesc401;

    private String mapCsparedesc402;

    private String mapCsparedesc5;

    private String mapCsparedesc5ja;

    private String mapCsparedesc5en;

    private String mapCsparedesc5ch;

    private String mapCsparedesc501;

    private String mapCsparedesc502;

    private String mapCsparechar1;

    private String mapCsparechar2;

    private String mapCsparechar3;

    private String mapCsparechar4;

    private String mapCsparechar5;

    private Integer mapNsparenum1;

    private Integer mapNsparenum2;

    private Integer mapNsparenum3;

    private Integer mapNsparenum4;

    private Integer mapNsparenum5;

    private Date mapDsparedate1;

    private Date mapDsparedate2;

    private Date mapDsparedate3;

    private Date mapDsparedate4;

    private Date mapDsparedate5;

    @Version
    private Long versionno;

}
