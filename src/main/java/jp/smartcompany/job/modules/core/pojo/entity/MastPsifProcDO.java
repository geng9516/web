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
 * 汎用インターフェース　ユーザ個別アプリケーションテーブル
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
@TableName("mast_psif_proc")
public class MastPsifProcDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mpip_id", type = IdType.AUTO)
                private Long mpipId;

        /**
         * 処理区分
         */
    @TableField("mpip_cprocid")
        private String mpipCprocid;

        /**
         * 処理テーブル
         */
    @TableField("mpip_ctable")
        private String mpipCtable;

        /**
         * 開始日付
         */
    @TableField("mpip_dstart")
        private Date mpipDstart;

        /**
         * 終了日付
         */
    @TableField("mpip_dend")
        private Date mpipDend;

        /**
         * 処理順序
         */
    @TableField("mpip_nseq")
        private Long mpipNseq;

        /**
         * procedure名
         */
    @TableField("mpip_ccommand")
        private String mpipCcommand;

        /**
         * 備考
         */
    @TableField("mpip_ccomments")
        private String mpipCcomments;

        /**
         * 最終更新者
         */
    @TableField("mpip_cmodifieruserid")
        private String mpipCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mpip_dmodifieddate")
        private Date mpipDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }