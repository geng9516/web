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
 * public.csv_djnd0260
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
@TableName("csv_djnd0260")
public class CsvDjnd0260DO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * ccompkb
         */
        @TableId(value="ccompkb",type = IdType.INPUT)
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
         * cshainno
         */
    @TableField("cshainno")
        private String cshainno;

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
         * siky_cde
         */
    @TableField("siky_cde")
        private String sikyCde;

        /**
         * siky_nme
         */
    @TableField("siky_nme")
        private String sikyNme;

        /**
         * siky_ritu
         */
    @TableField("siky_ritu")
        private Long sikyRitu;

        /**
         * key_dte
         */
    @TableField("key_dte")
        private String keyDte;

        /**
         * dkey_dte
         */
    @TableField("dkey_dte")
        private Date dkeyDte;

        /**
         * kosin_use
         */
    @TableField("kosin_use")
        private String kosinUse;

        /**
         * last_pgm
         */
    @TableField("last_pgm")
        private String lastPgm;

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