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
 * メール定義マスタ
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
@TableName("mast_mailinfo")
public class MastMailinfoDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mm_id", type = IdType.AUTO)
                private Long mmId;

        /**
         * メール定義id
         */
    @TableField("mm_cid")
        private String mmCid;

        /**
         * メール定義名称
         */
    @TableField("mm_cmailname")
        private String mmCmailname;

        /**
         * 送信者アドレス
         */
    @TableField("mm_caddress")
        private String mmCaddress;

        /**
         * 送信者氏名
         */
    @TableField("mm_cname")
        private String mmCname;

        /**
         * 送信メール件名
         */
    @TableField("mm_ctitle")
        private String mmCtitle;

        /**
         * 送信メールメッセージ
         */
    @TableField("mm_ccontent")
        private String mmCcontent;

        /**
         * 言語区分
         */
    @TableField("mm_clanguage")
        private String mmClanguage;

        /**
         * 説明
         */
    @TableField("mm_cdesc")
        private String mmCdesc;

        /**
         * 最終更新者
         */
    @TableField("mm_cmodifieruserid")
        private String mmCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mm_dmodifieddate")
        private Date mmDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;

        /**
         * モジュール区分
         */
    @TableField("mm_cmoduletype")
        private String mmCmoduletype;


        }