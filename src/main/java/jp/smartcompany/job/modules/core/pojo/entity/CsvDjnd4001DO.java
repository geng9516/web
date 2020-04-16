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
 * public.csv_djnd4001
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
@TableName("csv_djnd4001")
public class CsvDjnd4001DO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * ccompkb
         */
    @TableField("ccompkb")
        private String ccompkb;

        /**
         * dstart
         */
    @TableField("dstart")
        private Date dstart;

        /**
         * sstart
         */
    @TableField("sstart")
        private String sstart;

        /**
         * dend
         */
    @TableField("dend")
        private Date dend;

        /**
         * send
         */
    @TableField("send")
        private String send;

        /**
         * cde_sybt
         */
    @TableField("cde_sybt")
        private String cdeSybt;

        /**
         * cde_val
         */
    @TableField("cde_val")
        private String cdeVal;

        /**
         * dta1
         */
    @TableField("dta1")
        private String dta1;

        /**
         * dta2
         */
    @TableField("dta2")
        private String dta2;

        /**
         * dta3
         */
    @TableField("dta3")
        private String dta3;

        /**
         * dta4
         */
    @TableField("dta4")
        private String dta4;

        /**
         * dta5
         */
    @TableField("dta5")
        private String dta5;

        /**
         * dta6
         */
    @TableField("dta6")
        private String dta6;

        /**
         * dta7
         */
    @TableField("dta7")
        private String dta7;

        /**
         * dta8
         */
    @TableField("dta8")
        private String dta8;

        /**
         * dta9
         */
    @TableField("dta9")
        private String dta9;

        /**
         * dta10
         */
    @TableField("dta10")
        private String dta10;

        /**
         * dta11
         */
    @TableField("dta11")
        private String dta11;

        /**
         * dta12
         */
    @TableField("dta12")
        private String dta12;

        /**
         * ctrl_cde1
         */
    @TableField("ctrl_cde1")
        private String ctrlCde1;

        /**
         * ctrl_cde2
         */
    @TableField("ctrl_cde2")
        private String ctrlCde2;

        /**
         * ctrl_cde3
         */
    @TableField("ctrl_cde3")
        private String ctrlCde3;

        /**
         * ctrl_cde4
         */
    @TableField("ctrl_cde4")
        private String ctrlCde4;

        /**
         * ctrl_cde5
         */
    @TableField("ctrl_cde5")
        private String ctrlCde5;

        /**
         * name
         */
    @TableField("name")
        private String name;

        /**
         * dname
         */
    @TableField("dname")
        private String dname;

        /**
         * sname
         */
    @TableField("sname")
        private String sname;

        /**
         * rname
         */
    @TableField("rname")
        private String rname;

        /**
         * kname01
         */
    @TableField("kname01")
        private String kname01;

        /**
         * kname02
         */
    @TableField("kname02")
        private String kname02;

        /**
         * kname03
         */
    @TableField("kname03")
        private String kname03;

        /**
         * dname_comment
         */
    @TableField("dname_comment")
        private String dnameComment;

        /**
         * start_dte
         */
    @TableField("start_dte")
        private String startDte;

        /**
         * dstart_dte
         */
    @TableField("dstart_dte")
        private Date dstartDte;

        /**
         * end_dte
         */
    @TableField("end_dte")
        private String endDte;

        /**
         * dend_dte
         */
    @TableField("dend_dte")
        private Date dendDte;

        /**
         * kosin_usr
         */
    @TableField("kosin_usr")
        private String kosinUsr;

        /**
         * kosin_pgm
         */
    @TableField("kosin_pgm")
        private String kosinPgm;

        /**
         * last_dte
         */
    @TableField("last_dte")
        private Date lastDte;

        /**
         * kosin_kbn
         */
    @TableField("kosin_kbn")
        private String kosinKbn;

        /**
         * cmnuser
         */
    @TableField("cmnuser")
        private String cmnuser;

        /**
         * dmndate
         */
    @TableField("dmndate")
        private Date dmndate;


        }