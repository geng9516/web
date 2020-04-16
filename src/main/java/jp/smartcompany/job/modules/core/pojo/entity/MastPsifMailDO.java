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
 * 汎用インターフェース　結果通知対象者テーブル
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
@TableName("mast_psif_mail")
public class MastPsifMailDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mpim_id", type = IdType.AUTO)
                private Long mpimId;

        /**
         * 顧客区分
         */
    @TableField("mpim_ccustomerid")
        private String mpimCcustomerid;

        /**
         * 法人コード
         */
    @TableField("mpim_ccompanyid")
        private String mpimCcompanyid;

        /**
         * 社員番号
         */
    @TableField("mpim_cemployeeid")
        private String mpimCemployeeid;

        /**
         * ユーザid
         */
    @TableField("mpim_cuserid")
        private String mpimCuserid;

        /**
         * 最終更新者
         */
    @TableField("mpim_cmodifieruserid")
        private String mpimCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mpim_dmodifieddate")
        private Date mpimDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }