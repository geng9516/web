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
 * public.tmg_works_kintai_company
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
@TableName("tmg_works_kintai_company")
public class TmgWorksKintaiCompanyDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * txkw_ccustomerid
         */
    @TableId(value="txkw_ccustomerid",type = IdType.INPUT)
        private String txkwCcustomerid;

        /**
         * txkw_ccompanyid
         */
    @TableField("txkw_ccompanyid")
        private String txkwCcompanyid;

        /**
         * txkw_cemployeeid
         */
    @TableField("txkw_cemployeeid")
        private String txkwCemployeeid;

        /**
         * txkw_dstartdate
         */
    @TableField("txkw_dstartdate")
        private Date txkwDstartdate;

        /**
         * txkw_denddate
         */
    @TableField("txkw_denddate")
        private Date txkwDenddate;

        /**
         * txkw_cmodifieruserid
         */
    @TableField("txkw_cmodifieruserid")
        private String txkwCmodifieruserid;

        /**
         * txkw_dmodifieddate
         */
    @TableField("txkw_dmodifieddate")
        private Date txkwDmodifieddate;

        /**
         * txkw_cmodifierprogramid
         */
    @TableField("txkw_cmodifierprogramid")
        private String txkwCmodifierprogramid;

        /**
         * txkw_nseq
         */
    @TableField("txkw_nseq")
        private Long txkwNseq;

        /**
         * txkw_cshiftcode
         */
    @TableField("txkw_cshiftcode")
        private String txkwCshiftcode;

        /**
         * txkw_cshiftid
         */
    @TableField("txkw_cshiftid")
        private String txkwCshiftid;

        /**
         * txkw_nnumber01
         */
    @TableField("txkw_nnumber01")
        private Long txkwNnumber01;

        /**
         * txkw_nnumber02
         */
    @TableField("txkw_nnumber02")
        private Long txkwNnumber02;

        /**
         * txkw_nnumber03
         */
    @TableField("txkw_nnumber03")
        private Long txkwNnumber03;

        /**
         * txkw_nnumber04
         */
    @TableField("txkw_nnumber04")
        private Long txkwNnumber04;

        /**
         * txkw_nnumber05
         */
    @TableField("txkw_nnumber05")
        private Long txkwNnumber05;

        /**
         * txkw_nnumber06
         */
    @TableField("txkw_nnumber06")
        private Long txkwNnumber06;

        /**
         * txkw_nnumber07
         */
    @TableField("txkw_nnumber07")
        private Long txkwNnumber07;

        /**
         * txkw_nnumber08
         */
    @TableField("txkw_nnumber08")
        private Long txkwNnumber08;

        /**
         * txkw_nnumber09
         */
    @TableField("txkw_nnumber09")
        private Long txkwNnumber09;

        /**
         * txkw_nnumber10
         */
    @TableField("txkw_nnumber10")
        private Long txkwNnumber10;

        /**
         * txkw_nnumber11
         */
    @TableField("txkw_nnumber11")
        private Long txkwNnumber11;

        /**
         * txkw_nnumber12
         */
    @TableField("txkw_nnumber12")
        private Long txkwNnumber12;

        /**
         * txkw_nnumber13
         */
    @TableField("txkw_nnumber13")
        private Long txkwNnumber13;

        /**
         * txkw_nnumber14
         */
    @TableField("txkw_nnumber14")
        private Long txkwNnumber14;

        /**
         * txkw_nnumber15
         */
    @TableField("txkw_nnumber15")
        private Long txkwNnumber15;

        /**
         * txkw_nnumber16
         */
    @TableField("txkw_nnumber16")
        private Long txkwNnumber16;

        /**
         * txkw_nnumber17
         */
    @TableField("txkw_nnumber17")
        private Long txkwNnumber17;

        /**
         * txkw_nnumber18
         */
    @TableField("txkw_nnumber18")
        private Long txkwNnumber18;

        /**
         * txkw_nnumber19
         */
    @TableField("txkw_nnumber19")
        private Long txkwNnumber19;

        /**
         * txkw_nnumber20
         */
    @TableField("txkw_nnumber20")
        private Long txkwNnumber20;

        /**
         * txkw_nnumber21
         */
    @TableField("txkw_nnumber21")
        private Long txkwNnumber21;

        /**
         * txkw_nnumber22
         */
    @TableField("txkw_nnumber22")
        private Long txkwNnumber22;

        /**
         * txkw_nnumber23
         */
    @TableField("txkw_nnumber23")
        private Long txkwNnumber23;

        /**
         * txkw_nnumber24
         */
    @TableField("txkw_nnumber24")
        private Long txkwNnumber24;

        /**
         * txkw_nnumber25
         */
    @TableField("txkw_nnumber25")
        private Long txkwNnumber25;

        /**
         * txkw_nnumber26
         */
    @TableField("txkw_nnumber26")
        private Long txkwNnumber26;

        /**
         * txkw_nnumber27
         */
    @TableField("txkw_nnumber27")
        private Long txkwNnumber27;

        /**
         * txkw_nnumber28
         */
    @TableField("txkw_nnumber28")
        private Long txkwNnumber28;

        /**
         * txkw_nnumber29
         */
    @TableField("txkw_nnumber29")
        private Long txkwNnumber29;

        /**
         * txkw_nnumber30
         */
    @TableField("txkw_nnumber30")
        private Long txkwNnumber30;

        /**
         * txkw_nnumber31
         */
    @TableField("txkw_nnumber31")
        private Long txkwNnumber31;

        /**
         * txkw_nnumber32
         */
    @TableField("txkw_nnumber32")
        private Long txkwNnumber32;

        /**
         * txkw_nnumber33
         */
    @TableField("txkw_nnumber33")
        private Long txkwNnumber33;

        /**
         * txkw_nnumber34
         */
    @TableField("txkw_nnumber34")
        private Long txkwNnumber34;

        /**
         * txkw_nnumber35
         */
    @TableField("txkw_nnumber35")
        private Long txkwNnumber35;

        /**
         * txkw_nnumber36
         */
    @TableField("txkw_nnumber36")
        private Long txkwNnumber36;

        /**
         * txkw_nnumber37
         */
    @TableField("txkw_nnumber37")
        private Long txkwNnumber37;

        /**
         * txkw_nnumber38
         */
    @TableField("txkw_nnumber38")
        private Long txkwNnumber38;

        /**
         * txkw_nnumber39
         */
    @TableField("txkw_nnumber39")
        private Long txkwNnumber39;

        /**
         * txkw_nnumber40
         */
    @TableField("txkw_nnumber40")
        private Long txkwNnumber40;

        /**
         * txkw_nnumber41
         */
    @TableField("txkw_nnumber41")
        private Long txkwNnumber41;

        /**
         * txkw_nnumber42
         */
    @TableField("txkw_nnumber42")
        private Long txkwNnumber42;


        }