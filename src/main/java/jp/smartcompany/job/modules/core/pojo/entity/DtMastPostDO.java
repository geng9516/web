package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 役職マスタ
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dt_mast_post")
public class DtMastPostDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
        @TableId(value="map_id",type = IdType.INPUT)
        private Long mapId;

        /**
         * 顧客コード
         */
    @TableField("map_ccustomerid_ck_fk")
        private String mapCcustomeridCkFk;

        /**
         * 法人コード
         */
    @TableField("map_ccompanyid_ck_fk")
        private String mapCcompanyidCkFk;

        /**
         * 役職順位
         */
    @TableField("map_nweightage")
        private Long mapNweightage;

        /**
         * 役職コード
         */
    @TableField("map_cpostid_ck")
        private String mapCpostidCk;

        /**
         * 言語区分
         */
    @TableField("map_clanguage")
        private String mapClanguage;

        /**
         * 開始日
         */
    @TableField("map_dstart")
        private Date mapDstart;

        /**
         * 終了日
         */
    @TableField("map_dend")
        private Date mapDend;

        /**
         * 役職名称
         */
    @TableField("map_cpostname")
        private String mapCpostname;

        /**
         * 役職名称（日本語）
         */
    @TableField("map_cpostnameja")
        private String mapCpostnameja;

        /**
         * 役職名称（英語）
         */
    @TableField("map_cpostnameen")
        private String mapCpostnameen;

        /**
         * 役職名称（中国語）
         */
    @TableField("map_cpostnamech")
        private String mapCpostnamech;

        /**
         * 役職名称（予備１）
         */
    @TableField("map_cpostname01")
        private String mapCpostname01;

        /**
         * 役職名称（予備２）
         */
    @TableField("map_cpostname02")
        private String mapCpostname02;

        /**
         * 役職略称
         */
    @TableField("map_cpostnick")
        private String mapCpostnick;

        /**
         * 役職略称（日本語）
         */
    @TableField("map_cpostnickja")
        private String mapCpostnickja;

        /**
         * 役職略称（英語）
         */
    @TableField("map_cpostnicken")
        private String mapCpostnicken;

        /**
         * 役職略称（中国語）
         */
    @TableField("map_cpostnickch")
        private String mapCpostnickch;

        /**
         * 役職略称（予備１）
         */
    @TableField("map_cpostnick01")
        private String mapCpostnick01;

        /**
         * 役職略称（予備２）
         */
    @TableField("map_cpostnick02")
        private String mapCpostnick02;

        /**
         * 最終更新者
         */
    @TableField("map_cmodifieruserid")
        private String mapCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("map_dmodifieddate")
        private Date mapDmodifieddate;

        /**
         * 予備内容1（別名用ダミー）
         */
    @TableField("map_csparedesc1")
        private String mapCsparedesc1;

        /**
         * 予備内容1（日本語）
         */
    @TableField("map_csparedesc1ja")
        private String mapCsparedesc1ja;

        /**
         * 予備内容1（英語）
         */
    @TableField("map_csparedesc1en")
        private String mapCsparedesc1en;

        /**
         * 予備内容1（中国語）
         */
    @TableField("map_csparedesc1ch")
        private String mapCsparedesc1ch;

        /**
         * 予備内容1（予備１）
         */
    @TableField("map_csparedesc101")
        private String mapCsparedesc101;

        /**
         * 予備内容1（予備2）
         */
    @TableField("map_csparedesc102")
        private String mapCsparedesc102;

        /**
         * 予備内容2（別名用ダミー）
         */
    @TableField("map_csparedesc2")
        private String mapCsparedesc2;

        /**
         * 予備内容2（日本語）
         */
    @TableField("map_csparedesc2ja")
        private String mapCsparedesc2ja;

        /**
         * 予備内容2（英語）
         */
    @TableField("map_csparedesc2en")
        private String mapCsparedesc2en;

        /**
         * 予備内容2（中国語）
         */
    @TableField("map_csparedesc2ch")
        private String mapCsparedesc2ch;

        /**
         * 予備内容2（予備１）
         */
    @TableField("map_csparedesc201")
        private String mapCsparedesc201;

        /**
         * 予備内容2（予備2）
         */
    @TableField("map_csparedesc202")
        private String mapCsparedesc202;

        /**
         * 予備内容3（別名用ダミー）
         */
    @TableField("map_csparedesc3")
        private String mapCsparedesc3;

        /**
         * 予備内容3（日本語）
         */
    @TableField("map_csparedesc3ja")
        private String mapCsparedesc3ja;

        /**
         * 予備内容3（英語）
         */
    @TableField("map_csparedesc3en")
        private String mapCsparedesc3en;

        /**
         * 予備内容3（中国語）
         */
    @TableField("map_csparedesc3ch")
        private String mapCsparedesc3ch;

        /**
         * 予備内容3（予備１）
         */
    @TableField("map_csparedesc301")
        private String mapCsparedesc301;

        /**
         * 予備内容3（予備2）
         */
    @TableField("map_csparedesc302")
        private String mapCsparedesc302;

        /**
         * 予備内容4（別名用ダミー）
         */
    @TableField("map_csparedesc4")
        private String mapCsparedesc4;

        /**
         * 予備内容4（日本語）
         */
    @TableField("map_csparedesc4ja")
        private String mapCsparedesc4ja;

        /**
         * 予備内容4（英語）
         */
    @TableField("map_csparedesc4en")
        private String mapCsparedesc4en;

        /**
         * 予備内容4（中国語）
         */
    @TableField("map_csparedesc4ch")
        private String mapCsparedesc4ch;

        /**
         * 予備内容4（予備１）
         */
    @TableField("map_csparedesc401")
        private String mapCsparedesc401;

        /**
         * 予備内容4（予備2）
         */
    @TableField("map_csparedesc402")
        private String mapCsparedesc402;

        /**
         * 予備内容5（別名用ダミー）
         */
    @TableField("map_csparedesc5")
        private String mapCsparedesc5;

        /**
         * 予備内容5（日本語）
         */
    @TableField("map_csparedesc5ja")
        private String mapCsparedesc5ja;

        /**
         * 予備内容5（英語）
         */
    @TableField("map_csparedesc5en")
        private String mapCsparedesc5en;

        /**
         * 予備内容5（中国語）
         */
    @TableField("map_csparedesc5ch")
        private String mapCsparedesc5ch;

        /**
         * 予備内容5（予備１）
         */
    @TableField("map_csparedesc501")
        private String mapCsparedesc501;

        /**
         * 予備内容5（予備2）
         */
    @TableField("map_csparedesc502")
        private String mapCsparedesc502;

        /**
         * 予備文字1
         */
    @TableField("map_csparechar1")
        private String mapCsparechar1;

        /**
         * 予備文字2
         */
    @TableField("map_csparechar2")
        private String mapCsparechar2;

        /**
         * 予備文字3
         */
    @TableField("map_csparechar3")
        private String mapCsparechar3;

        /**
         * 予備文字4
         */
    @TableField("map_csparechar4")
        private String mapCsparechar4;

        /**
         * 予備文字5
         */
    @TableField("map_csparechar5")
        private String mapCsparechar5;

        /**
         * 予備数値1
         */
    @TableField("map_nsparenum1")
        private Long mapNsparenum1;

        /**
         * 予備数値2
         */
    @TableField("map_nsparenum2")
        private Long mapNsparenum2;

        /**
         * 予備数値3
         */
    @TableField("map_nsparenum3")
        private Long mapNsparenum3;

        /**
         * 予備数値4
         */
    @TableField("map_nsparenum4")
        private Long mapNsparenum4;

        /**
         * 予備数値5
         */
    @TableField("map_nsparenum5")
        private Long mapNsparenum5;

        /**
         * 予備日付1
         */
    @TableField("map_dsparedate1")
        private Date mapDsparedate1;

        /**
         * 予備日付2
         */
    @TableField("map_dsparedate2")
        private Date mapDsparedate2;

        /**
         * 予備日付3
         */
    @TableField("map_dsparedate3")
        private Date mapDsparedate3;

        /**
         * 予備日付4
         */
    @TableField("map_dsparedate4")
        private Date mapDsparedate4;

        /**
         * 予備日付5
         */
    @TableField("map_dsparedate5")
        private Date mapDsparedate5;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }