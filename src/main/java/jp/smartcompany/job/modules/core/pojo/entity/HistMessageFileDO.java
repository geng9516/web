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
 * メッセージ通知 メッセージファイル情報テーブル
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
@TableName("hist_message_file")
public class HistMessageFileDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hmf_id", type = IdType.AUTO)
                private Long hmfId;

        /**
         * メッセージ送信者情報idカラム
         */
    @TableField("hmf_nsender_id")
        private Long hmfNsenderId;

        /**
         * 名称
         */
    @TableField("hmf_cname")
        private String hmfCname;

        /**
         * 内容
         */
    @TableField("hmf_ccontent")
        private String hmfCcontent;

        /**
         * mimeタイプ
         */
    @TableField("hmf_cmimetype")
        private String hmfCmimetype;

        /**
         * 最終更新者
         */
    @TableField("hmf_cmodifieruserid")
        private String hmfCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hmf_dmodifieddate")
        private Date hmfDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }