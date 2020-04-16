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
 * アプリケーション設定マスタ
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
@TableName("mast_apptree")
public class MastApptreeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mtr_id", type = IdType.AUTO)
                private Long mtrId;

        /**
         * オブジェクトid
         */
    @TableField("mtr_cobjectid")
        private String mtrCobjectid;

        /**
         * サイトid
         */
    @TableField("mtr_csiteid")
        private String mtrCsiteid;

        /**
         * アプリケーションid
         */
    @TableField("mtr_cappid")
        private String mtrCappid;

        /**
         * サブアプリケーションid
         */
    @TableField("mtr_csubappid")
        private String mtrCsubappid;

        /**
         * 画面id
         */
    @TableField("mtr_cscreenid")
        private String mtrCscreenid;

        /**
         * ボタンid
         */
    @TableField("mtr_cbuttonid")
        private String mtrCbuttonid;

        /**
         * 項目名
         */
    @TableField("mtr_cobjname")
        private String mtrCobjname;

        /**
         * 項目名（日本語）
         */
    @TableField("mtr_cobjnameja")
        private String mtrCobjnameja;

        /**
         * 項目名（英語）
         */
    @TableField("mtr_cobjnameen")
        private String mtrCobjnameen;

        /**
         * 項目名（中国語）
         */
    @TableField("mtr_cobjnamech")
        private String mtrCobjnamech;

        /**
         * 項目名（予備１）
         */
    @TableField("mtr_cobjname01")
        private String mtrCobjname01;

        /**
         * 項目名（予備２）
         */
    @TableField("mtr_cobjname02")
        private String mtrCobjname02;

        /**
         * 種別
         */
    @TableField("mtr_ctype")
        private String mtrCtype;

        /**
         * 使用レイアウトid
         */
    @TableField("mtr_ctemplateid")
        private String mtrCtemplateid;

        /**
         * url
         */
    @TableField("mtr_curl")
        private String mtrCurl;

        /**
         * 画像url
         */
    @TableField("mtr_cimageurl")
        private String mtrCimageurl;

        /**
         * サイト説明文
         */
    @TableField("mtr_csitecaption")
        private String mtrCsitecaption;

        /**
         * サイト説明文（日本語）
         */
    @TableField("mtr_csitecaptionja")
        private String mtrCsitecaptionja;

        /**
         * サイト説明文（英語）
         */
    @TableField("mtr_csitecaptionen")
        private String mtrCsitecaptionen;

        /**
         * サイト説明文（中国語）
         */
    @TableField("mtr_csitecaptionch")
        private String mtrCsitecaptionch;

        /**
         * サイト説明文（予備１）
         */
    @TableField("mtr_csitecaption01")
        private String mtrCsitecaption01;

        /**
         * サイト説明文（予備２）
         */
    @TableField("mtr_csitecaption02")
        private String mtrCsitecaption02;

        /**
         * 並び順
         */
    @TableField("mtr_nseq")
        private Long mtrNseq;

        /**
         * 対応バージョン
         */
    @TableField("mtr_cversion")
        private String mtrCversion;

        /**
         * 対応システム
         */
    @TableField("mtr_csystemid")
        private String mtrCsystemid;

        /**
         * デフォルト検索対象者
         */
    @TableField("mtr_cdefaulttargetuser")
        private String mtrCdefaulttargetuser;

        /**
         * 適用基準日区分
         */
    @TableField("mtr_ccriterialdatetype")
        private String mtrCcriterialdatetype;

        /**
         * 検索対象範囲適用種別
         */
    @TableField("mtr_cdatapermissiontype")
        private String mtrCdatapermissiontype;

        /**
         * アプリケーション自動起動
         */
    @TableField("mtr_cappautoload")
        private String mtrCappautoload;

        /**
         * オンラインヘルプurl
         */
    @TableField("mtr_conlinehelpurl")
        private String mtrConlinehelpurl;

        /**
         * オンラインヘルプwindow属性
         */
    @TableField("mtr_conlinehelpattr")
        private String mtrConlinehelpattr;

        /**
         * ドメインコード
         */
    @TableField("mtr_cdomainid")
        private String mtrCdomainid;

        /**
         * インラインフレーム使用フラグ
         */
    @TableField("mtr_ciframeflag")
        private String mtrCiframeflag;

        /**
         * 最終更新者
         */
    @TableField("mtr_cmodifieruserid")
        private String mtrCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mtr_dmodifieddate")
        private Date mtrDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }