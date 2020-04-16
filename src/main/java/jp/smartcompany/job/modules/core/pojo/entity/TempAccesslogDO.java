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
 * アクセスログワーク
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
@TableName("temp_accesslog")
public class TempAccesslogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "tal_id", type = IdType.AUTO)
                private Long talId;

        /**
         * ログファイル取込日時
         */
    @TableField("tal_dtakendate")
        private Date talDtakendate;

        /**
         * ログファイル取得元パス
         */
    @TableField("tal_clogfilepath")
        private String talClogfilepath;

        /**
         * 開始日時
         */
    @TableField("tal_dstrdate")
        private Date talDstrdate;

        /**
         * 終了日時
         */
    @TableField("tal_denddate")
        private Date talDenddate;

        /**
         * 所要時間
         */
    @TableField("tal_ntakentime")
        private Long talNtakentime;

        /**
         * ipアドレス
         */
    @TableField("tal_cipaddress")
        private String talCipaddress;

        /**
         * ログインユーザid
         */
    @TableField("tal_cloginuserid")
        private String talCloginuserid;

        /**
         * 代替元ユーザid
         */
    @TableField("tal_csubstuserid")
        private String talCsubstuserid;

        /**
         * 閲覧対象ユーザid
         */
    @TableField("tal_ctargetuserid")
        private String talCtargetuserid;

        /**
         * ログイン言語区分
         */
    @TableField("tal_clanguage")
        private String talClanguage;

        /**
         * サイトid
         */
    @TableField("tal_csiteid")
        private String talCsiteid;

        /**
         * アプリケーションid
         */
    @TableField("tal_cappid")
        private String talCappid;

        /**
         * サブアプリケーションid
         */
    @TableField("tal_csubappid")
        private String talCsubappid;

        /**
         * 画面id
         */
    @TableField("tal_cscreenid")
        private String talCscreenid;

        /**
         * 実行メソッド
         */
    @TableField("tal_cprogramname")
        private String talCprogramname;

        /**
         * パーマリンク
         */
    @TableField("tal_cpermalink")
        private String talCpermalink;


        }