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
 * システムマスタ
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
@TableName("mast_system")
public class MastSystemDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "ms_id", type = IdType.AUTO)
                private Long msId;

        /**
         * システムコード
         */
    @TableField("ms_csystemid_pk")
        private String msCsystemidPk;

        /**
         * システム名称
         */
    @TableField("ms_csystemname")
        private String msCsystemname;

        /**
         * システム名称（日本語）
         */
    @TableField("ms_csystemnameja")
        private String msCsystemnameja;

        /**
         * システム名称（英語）
         */
    @TableField("ms_csystemnameen")
        private String msCsystemnameen;

        /**
         * システム名称（中国語）
         */
    @TableField("ms_csystemnamech")
        private String msCsystemnamech;

        /**
         * システム名称（予備１）
         */
    @TableField("ms_csystemname01")
        private String msCsystemname01;

        /**
         * システム名称（予備２）
         */
    @TableField("ms_csystemname02")
        private String msCsystemname02;

        /**
         * 言語区分
         */
    @TableField("ms_clanguage")
        private String msClanguage;

        /**
         * 作成日付
         */
    @TableField("ms_dcreateddate")
        private Date msDcreateddate;

        /**
         * 削除フラグ
         */
    @TableField("ms_cdelflag")
        private String msCdelflag;

        /**
         * 削除日付
         */
    @TableField("ms_ddeldate")
        private Date msDdeldate;

        /**
         * システム種別
         */
    @TableField("ms_ntype")
        private Long msNtype;

        /**
         * 最終更新者
         */
    @TableField("ms_cmodifieruserid")
        private String msCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("ms_dmodifieddate")
        private Date msDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }