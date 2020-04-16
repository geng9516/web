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
 * メッセージ通知 メッセージ送信情報テーブル
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
@TableName("hist_message")
public class HistMessageDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hme_id", type = IdType.AUTO)
                private Long hmeId;

        /**
         * 顧客コード
         */
    @TableField("hme_ccustomerid")
        private String hmeCcustomerid;

        /**
         * 送信者ユーザid
         */
    @TableField("hme_cuserid")
        private String hmeCuserid;

        /**
         * サイトid
         */
    @TableField("hme_csiteid")
        private String hmeCsiteid;

        /**
         * コンテンツid
         */
    @TableField("hme_cappid")
        private String hmeCappid;

        /**
         * 公開日
         */
    @TableField("hme_dreleasedate")
        private Date hmeDreleasedate;

        /**
         * 送信件名（日本語）
         */
    @TableField("hme_ctitleja")
        private String hmeCtitleja;

        /**
         * 送信内容（日本語）
         */
    @TableField("hme_ccontentja")
        private String hmeCcontentja;

        /**
         * 表示url
         */
    @TableField("hme_curl")
        private String hmeCurl;

        /**
         * 通知種別
         */
    @TableField("hme_cnotification_type")
        private String hmeCnotificationType;

        /**
         * 送信内容まとめフラグ
         */
    @TableField("hme_nsummailflag")
        private Long hmeNsummailflag;

        /**
         * 送信元メールアドレス（日本語）
         */
    @TableField("hme_cfromaddressja")
        private String hmeCfromaddressja;

        /**
         * 最終更新者
         */
    @TableField("hme_cmodifieruserid")
        private String hmeCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hme_dmodifieddate")
        private Date hmeDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;

        /**
         * 送信件名（英語）
         */
    @TableField("hme_ctitleen")
        private String hmeCtitleen;

        /**
         * 送信件名（中国語）
         */
    @TableField("hme_ctitlech")
        private String hmeCtitlech;

        /**
         * 送信件名（予備１）
         */
    @TableField("hme_ctitle01")
        private String hmeCtitle01;

        /**
         * 送信件名（予備２）
         */
    @TableField("hme_ctitle02")
        private String hmeCtitle02;

        /**
         * 送信内容（英語）
         */
    @TableField("hme_ccontenten")
        private String hmeCcontenten;

        /**
         * 送信内容（中国語）
         */
    @TableField("hme_ccontentch")
        private String hmeCcontentch;

        /**
         * 送信内容（予備１）
         */
    @TableField("hme_ccontent01")
        private String hmeCcontent01;

        /**
         * 送信内容（予備２）
         */
    @TableField("hme_ccontent02")
        private String hmeCcontent02;

        /**
         * 送信元メールアドレス（英語）
         */
    @TableField("hme_cfromaddressen")
        private String hmeCfromaddressen;

        /**
         * 送信元メールアドレス（中国語）
         */
    @TableField("hme_cfromaddressch")
        private String hmeCfromaddressch;

        /**
         * 送信元メールアドレス（予備１）
         */
    @TableField("hme_cfromaddress01")
        private String hmeCfromaddress01;

        /**
         * 送信元メールアドレス（予備２）
         */
    @TableField("hme_cfromaddress02")
        private String hmeCfromaddress02;


        }