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
 * 汎用メール送信テーブル
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
@TableName("mast_sendmail")
public class MastSendmailDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mas_id", type = IdType.AUTO)
                private Long masId;

        /**
         * メール定義id
         */
    @TableField("mas_cid")
        private String masCid;

        /**
         * 最終更新者
         */
    @TableField("mas_cmodifieruserid")
        private String masCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mas_dmodifieddate")
        private Date masDmodifieddate;

        /**
         * 送信者アドレス
         */
    @TableField("mas_cfromaddress")
        private String masCfromaddress;

        /**
         * 送信日時
         */
    @TableField("mas_dsenddate")
        private Date masDsenddate;

        /**
         * 送信先アドレス(to)
         */
    @TableField("mas_ctoaddress")
        private String masCtoaddress;

        /**
         * 送信先アドレス(cc)
         */
    @TableField("mas_cccaddress")
        private String masCccaddress;

        /**
         * 送信先アドレス(bcc)
         */
    @TableField("mas_cbccaddress")
        private String masCbccaddress;

        /**
         * 送信メール件名
         */
    @TableField("mas_ctitle")
        private String masCtitle;

        /**
         * 送信メール本文
         */
    @TableField("mas_ccontent")
        private String masCcontent;

        /**
         * 添付ファイル名
         */
    @TableField("mas_cattachname")
        private String masCattachname;

        /**
         * 添付ファイル
         */
    @TableField("mas_battach")
        private String masBattach;

        /**
         * 送信フラグ
         */
    @TableField("mas_nsend")
        private Long masNsend;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }