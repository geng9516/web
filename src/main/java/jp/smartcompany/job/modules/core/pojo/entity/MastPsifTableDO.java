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
 * 汎用インターフェース　処理対象テーブル
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
@TableName("mast_psif_table")
public class MastPsifTableDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mpit_id", type = IdType.AUTO)
                private Long mpitId;

        /**
         * テーブルid
         */
    @TableField("mpit_ctableid")
        private String mpitCtableid;

        /**
         * 使用フラグ
         */
    @TableField("mpit_cuseflg")
        private String mpitCuseflg;

        /**
         * シーケンスid
         */
    @TableField("mpit_csequence")
        private String mpitCsequence;

        /**
         * シーケンス対応カラム
         */
    @TableField("mpit_csequencecolumn")
        private String mpitCsequencecolumn;

        /**
         * シーケンスモード
         */
    @TableField("mpit_csequencemode")
        private String mpitCsequencemode;

        /**
         * 最終更新者
         */
    @TableField("mpit_cmodifieruserid")
        private String mpitCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mpit_dmodifieddate")
        private Date mpitDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }