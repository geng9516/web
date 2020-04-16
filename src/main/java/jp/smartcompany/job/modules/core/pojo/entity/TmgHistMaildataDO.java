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
 * メール送信テーブル
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
@TableName("tmg_hist_maildata")
public class TmgHistMaildataDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "thmd_id", type = IdType.AUTO)
                private Long thmdId;

        /**
         * 顧客コード
         */
    @TableField("thmd_ccustomerid")
        private String thmdCcustomerid;

        /**
         * 法人コード
         */
    @TableField("thmd_ccompanyid")
        private String thmdCcompanyid;

        /**
         * 最終更新者
         */
    @TableField("thmd_cmodifieruserid")
        private String thmdCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("thmd_dmodifieddate")
        private Date thmdDmodifieddate;

        /**
         * 最終更新プログラムid
         */
    @TableField("thmd_cmodifierprogramid")
        private String thmdCmodifierprogramid;

        /**
         * イベントid
         */
    @TableField("thmd_ceventid")
        private String thmdCeventid;

        /**
         * メール定義id
         */
    @TableField("thmd_cid")
        private String thmdCid;

        /**
         * メールを受け取る職員番号
         */
    @TableField("thmd_cemployeeid_receive")
        private String thmdCemployeeidReceive;

        /**
         * 対象の職員番号。超勤の職員。日次・月次未承認はnull、申請は申請者
         */
    @TableField("thmd_cemployeeid_target")
        private String thmdCemployeeidTarget;

        /**
         * 基準日
         */
    @TableField("thmd_dyyyymmdd")
        private Date thmdDyyyymmdd;

        /**
         * 作成日時
         */
    @TableField("thmd_dcreate")
        private Date thmdDcreate;

        /**
         * 差出人アドレス（ｆｒｏｍ）
         */
    @TableField("thmd_cfromaddress")
        private String thmdCfromaddress;

        /**
         * 送信先アドレス（ｔｏ）
         */
    @TableField("thmd_ctoaddress")
        private String thmdCtoaddress;

        /**
         * 送信メール件名
         */
    @TableField("thmd_ctitle")
        private String thmdCtitle;

        /**
         * 送信メール本文
         */
    @TableField("thmd_ccontent")
        private String thmdCcontent;

        /**
         * 送信日時
         */
    @TableField("thmd_dsend")
        private Date thmdDsend;

        /**
         * 送信ステータス ０：未送信　１：送信済　２：送信エラー
         */
    @TableField("thmd_nsend_status")
        private Long thmdNsendStatus;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }