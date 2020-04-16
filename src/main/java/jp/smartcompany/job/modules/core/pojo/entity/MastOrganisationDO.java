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
 * 組織ツリーマスタ
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
@TableName("mast_organisation")
public class MastOrganisationDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * ｉｄカラム
         */
                @TableId(value = "mo_id", type = IdType.AUTO)
                private Long moId;

        /**
         * 顧客コード
         */
    @TableField("mo_ccustomerid_ck_fk")
        private String moCcustomeridCkFk;

        /**
         * 法人コード
         */
    @TableField("mo_ccompanyid_ck_fk")
        private String moCcompanyidCkFk;

        /**
         * 組織コード
         */
    @TableField("mo_csectionid_ck")
        private String moCsectionidCk;

        /**
         * 組織階層コード
         */
    @TableField("mo_clayeredsectionid")
        private String moClayeredsectionid;

        /**
         * 組織名称
         */
    @TableField("mo_csectionname")
        private String moCsectionname;

        /**
         * 組織名称（日本語）
         */
    @TableField("mo_csectionnameja")
        private String moCsectionnameja;

        /**
         * 組織名称（英語）
         */
    @TableField("mo_csectionnameen")
        private String moCsectionnameen;

        /**
         * 組織名称（中国語）
         */
    @TableField("mo_csectionnamech")
        private String moCsectionnamech;

        /**
         * 組織名称（予備１）
         */
    @TableField("mo_csectionname01")
        private String moCsectionname01;

        /**
         * 組織名称（予備２）
         */
    @TableField("mo_csectionname02")
        private String moCsectionname02;

        /**
         * 組織略称（通称）
         */
    @TableField("mo_csectionnick")
        private String moCsectionnick;

        /**
         * 組織略称（通称_日本語）
         */
    @TableField("mo_csectionnickja")
        private String moCsectionnickja;

        /**
         * 組織略称（通称_英語）
         */
    @TableField("mo_csectionnicken")
        private String moCsectionnicken;

        /**
         * 組織略称（通称_中国語）
         */
    @TableField("mo_csectionnickch")
        private String moCsectionnickch;

        /**
         * 組織略称（通称_予備１）
         */
    @TableField("mo_csectionnick01")
        private String moCsectionnick01;

        /**
         * 組織略称（通称_予備２）
         */
    @TableField("mo_csectionnick02")
        private String moCsectionnick02;

        /**
         * 言語区分
         */
    @TableField("mo_clanguage")
        private String moClanguage;

        /**
         * データ開始日
         */
    @TableField("mo_dstart")
        private Date moDstart;

        /**
         * データ終了日
         */
    @TableField("mo_dend")
        private Date moDend;

        /**
         * 上位組織コード
         */
    @TableField("mo_cparentid")
        private String moCparentid;

        /**
         * 階層レベル
         */
    @TableField("mo_nlevel")
        private Long moNlevel;

        /**
         * 行
         */
    @TableField("mo_nseq")
        private Long moNseq;

        /**
         * 仮想組織フラグ
         */
    @TableField("mo_nhr")
        private Long moNhr;

        /**
         * 最終更新者
         */
    @TableField("mo_cmodifieruserid")
        private String moCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mo_dmodifieddate")
        private Date moDmodifieddate;

        /**
         * 予備内容1（別名用ダミー）
         */
    @TableField("mo_csparedesc1")
        private String moCsparedesc1;

        /**
         * 予備内容1（日本語）
         */
    @TableField("mo_csparedesc1ja")
        private String moCsparedesc1ja;

        /**
         * 予備内容1（英語）
         */
    @TableField("mo_csparedesc1en")
        private String moCsparedesc1en;

        /**
         * 予備内容1（中国語）
         */
    @TableField("mo_csparedesc1ch")
        private String moCsparedesc1ch;

        /**
         * 予備内容1（予備１）
         */
    @TableField("mo_csparedesc101")
        private String moCsparedesc101;

        /**
         * 予備内容1（予備2）
         */
    @TableField("mo_csparedesc102")
        private String moCsparedesc102;

        /**
         * 予備内容2（別名用ダミー）
         */
    @TableField("mo_csparedesc2")
        private String moCsparedesc2;

        /**
         * 予備内容2（日本語）
         */
    @TableField("mo_csparedesc2ja")
        private String moCsparedesc2ja;

        /**
         * 予備内容2（英語）
         */
    @TableField("mo_csparedesc2en")
        private String moCsparedesc2en;

        /**
         * 予備内容2（中国語）
         */
    @TableField("mo_csparedesc2ch")
        private String moCsparedesc2ch;

        /**
         * 予備内容2（予備１）
         */
    @TableField("mo_csparedesc201")
        private String moCsparedesc201;

        /**
         * 予備内容2（予備2）
         */
    @TableField("mo_csparedesc202")
        private String moCsparedesc202;

        /**
         * 予備内容3（別名用ダミー）
         */
    @TableField("mo_csparedesc3")
        private String moCsparedesc3;

        /**
         * 予備内容3（日本語）
         */
    @TableField("mo_csparedesc3ja")
        private String moCsparedesc3ja;

        /**
         * 予備内容3（英語）
         */
    @TableField("mo_csparedesc3en")
        private String moCsparedesc3en;

        /**
         * 予備内容3（中国語）
         */
    @TableField("mo_csparedesc3ch")
        private String moCsparedesc3ch;

        /**
         * 予備内容3（予備１）
         */
    @TableField("mo_csparedesc301")
        private String moCsparedesc301;

        /**
         * 予備内容3（予備2）
         */
    @TableField("mo_csparedesc302")
        private String moCsparedesc302;

        /**
         * 予備内容4（別名用ダミー）
         */
    @TableField("mo_csparedesc4")
        private String moCsparedesc4;

        /**
         * 予備内容4（日本語）
         */
    @TableField("mo_csparedesc4ja")
        private String moCsparedesc4ja;

        /**
         * 予備内容4（英語）
         */
    @TableField("mo_csparedesc4en")
        private String moCsparedesc4en;

        /**
         * 予備内容4（中国語）
         */
    @TableField("mo_csparedesc4ch")
        private String moCsparedesc4ch;

        /**
         * 予備内容4（予備１）
         */
    @TableField("mo_csparedesc401")
        private String moCsparedesc401;

        /**
         * 予備内容4（予備2）
         */
    @TableField("mo_csparedesc402")
        private String moCsparedesc402;

        /**
         * 予備内容5（別名用ダミー）
         */
    @TableField("mo_csparedesc5")
        private String moCsparedesc5;

        /**
         * 予備内容5（日本語）
         */
    @TableField("mo_csparedesc5ja")
        private String moCsparedesc5ja;

        /**
         * 予備内容5（英語）
         */
    @TableField("mo_csparedesc5en")
        private String moCsparedesc5en;

        /**
         * 予備内容5（中国語）
         */
    @TableField("mo_csparedesc5ch")
        private String moCsparedesc5ch;

        /**
         * 予備内容5（予備１）
         */
    @TableField("mo_csparedesc501")
        private String moCsparedesc501;

        /**
         * 予備内容5（予備2）
         */
    @TableField("mo_csparedesc502")
        private String moCsparedesc502;

        /**
         * 予備文字1
         */
    @TableField("mo_csparechar1")
        private String moCsparechar1;

        /**
         * 予備文字2
         */
    @TableField("mo_csparechar2")
        private String moCsparechar2;

        /**
         * 予備文字3
         */
    @TableField("mo_csparechar3")
        private String moCsparechar3;

        /**
         * 予備文字4
         */
    @TableField("mo_csparechar4")
        private String moCsparechar4;

        /**
         * 予備文字5
         */
    @TableField("mo_csparechar5")
        private String moCsparechar5;

        /**
         * 予備数値1
         */
    @TableField("mo_nsparenum1")
        private Long moNsparenum1;

        /**
         * 予備数値2
         */
    @TableField("mo_nsparenum2")
        private Long moNsparenum2;

        /**
         * 予備数値3
         */
    @TableField("mo_nsparenum3")
        private Long moNsparenum3;

        /**
         * 予備数値4
         */
    @TableField("mo_nsparenum4")
        private Long moNsparenum4;

        /**
         * 予備数値5
         */
    @TableField("mo_nsparenum5")
        private Long moNsparenum5;

        /**
         * 予備日付1
         */
    @TableField("mo_dsparedate1")
        private Date moDsparedate1;

        /**
         * 予備日付2
         */
    @TableField("mo_dsparedate2")
        private Date moDsparedate2;

        /**
         * 予備日付3
         */
    @TableField("mo_dsparedate3")
        private Date moDsparedate3;

        /**
         * 予備日付4
         */
    @TableField("mo_dsparedate4")
        private Date moDsparedate4;

        /**
         * 予備日付5
         */
    @TableField("mo_dsparedate5")
        private Date moDsparedate5;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }