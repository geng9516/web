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
 * グループ判定チェック結果メール送信先データ
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
@TableName("mast_groupcheckmail")
public class MastGroupcheckmailDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mgcm_id", type = IdType.AUTO)
                private Long mgcmId;

        /**
         * 顧客コード
         */
    @TableField("mgcm_ccustomerid")
        private String mgcmCcustomerid;

        /**
         * 法人コード
         */
    @TableField("mgcm_ccompanyid")
        private String mgcmCcompanyid;

        /**
         * 社員番号
         */
    @TableField("mgcm_cemployeeid")
        private String mgcmCemployeeid;

        /**
         * ユーザid
         */
    @TableField("mgcm_cuserid")
        private String mgcmCuserid;

        /**
         * 最終更新者
         */
    @TableField("mgcm_cmodifieruserid")
        private String mgcmCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mgcm_dmodifieddate")
        private Date mgcmDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }