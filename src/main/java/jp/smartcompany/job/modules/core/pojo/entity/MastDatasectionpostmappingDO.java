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
 * 検索対象範囲条件定義マスタ(組織、役職)
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
@TableName("mast_datasectionpostmapping")
public class MastDatasectionpostmappingDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mdsp_id", type = IdType.AUTO)
                private Long mdspId;

        /**
         * 定義id
         */
    @TableField("mdsp_cpermissionid")
        private String mdspCpermissionid;

        /**
         * 定義区分
         */
    @TableField("mdsp_ctypeid")
        private String mdspCtypeid;

        /**
         * 法人コード
         */
    @TableField("mdsp_ccompanyid")
        private String mdspCcompanyid;

        /**
         * 組織コード
         */
    @TableField("mdsp_csectionid")
        private String mdspCsectionid;

        /**
         * 役職コード
         */
    @TableField("mdsp_cpostid")
        private String mdspCpostid;

        /**
         * 社員番号
         */
    @TableField("mdsp_cemployeeid")
        private String mdspCemployeeid;

        /**
         * 最終更新者
         */
    @TableField("mdsp_cmodifieruserid")
        private String mdspCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mdsp_dmodifieddate")
        private Date mdspDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }