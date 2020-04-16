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
 * public.mast_datadictbl_v126
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
@TableName("mast_datadictbl_v126")
public class MastDatadictblV126DO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * mdt_id
         */
    @TableField("mdt_id")
        private Long mdtId;

        /**
         * mdt_ccustomerid
         */
    @TableField("mdt_ccustomerid")
        private String mdtCcustomerid;

        /**
         * mdt_ctablename
         */
    @TableField("mdt_ctablename")
        private String mdtCtablename;

        /**
         * mdt_ntableseq
         */
    @TableField("mdt_ntableseq")
        private Long mdtNtableseq;

        /**
         * mdt_ctabledesc
         */
    @TableField("mdt_ctabledesc")
        private String mdtCtabledesc;

        /**
         * mdt_ctabledescja
         */
    @TableField("mdt_ctabledescja")
        private String mdtCtabledescja;

        /**
         * mdt_ctabledescen
         */
    @TableField("mdt_ctabledescen")
        private String mdtCtabledescen;

        /**
         * mdt_ctabledescch
         */
    @TableField("mdt_ctabledescch")
        private String mdtCtabledescch;

        /**
         * mdt_ctabledesc01
         */
    @TableField("mdt_ctabledesc01")
        private String mdtCtabledesc01;

        /**
         * mdt_ctabledesc02
         */
    @TableField("mdt_ctabledesc02")
        private String mdtCtabledesc02;

        /**
         * mdt_cmodifieruserid
         */
    @TableField("mdt_cmodifieruserid")
        private String mdtCmodifieruserid;

        /**
         * mdt_dmodifieddate
         */
    @TableField("mdt_dmodifieddate")
        private Date mdtDmodifieddate;

        /**
         * versionno
         */
    @TableField("versionno")
        private Long versionno;


        }