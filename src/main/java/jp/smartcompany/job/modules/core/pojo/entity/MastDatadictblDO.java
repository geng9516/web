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
 * テーブル名称マスタ
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
@TableName("mast_datadictbl")
public class MastDatadictblDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mdt_id", type = IdType.AUTO)
                private Long mdtId;

        /**
         * 顧客コード
         */
    @TableField("mdt_ccustomerid")
        private String mdtCcustomerid;

        /**
         * テーブルid
         */
    @TableField("mdt_ctablename")
        private String mdtCtablename;

        /**
         * テーブル並び順
         */
    @TableField("mdt_ntableseq")
        private Long mdtNtableseq;

        /**
         * テーブル名称（別名用ダミー）
         */
    @TableField("mdt_ctabledesc")
        private String mdtCtabledesc;

        /**
         * テーブル名称（日本語）
         */
    @TableField("mdt_ctabledescja")
        private String mdtCtabledescja;

        /**
         * テーブル名称（英語）
         */
    @TableField("mdt_ctabledescen")
        private String mdtCtabledescen;

        /**
         * テーブル名称（中国語）
         */
    @TableField("mdt_ctabledescch")
        private String mdtCtabledescch;

        /**
         * テーブル名称（予備１）
         */
    @TableField("mdt_ctabledesc01")
        private String mdtCtabledesc01;

        /**
         * テーブル名称（予備２）
         */
    @TableField("mdt_ctabledesc02")
        private String mdtCtabledesc02;

        /**
         * 更新者
         */
    @TableField("mdt_cmodifieruserid")
        private String mdtCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("mdt_dmodifieddate")
        private Date mdtDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }