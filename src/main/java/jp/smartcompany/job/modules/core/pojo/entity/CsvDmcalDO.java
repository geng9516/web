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
 * public.csv_dmcal
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
@TableName("csv_dmcal")
public class CsvDmcalDO implements Serializable {

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
         * year
         */
    @TableField("year")
        private String year;

        /**
         * month
         */
    @TableField("month")
        private Long month;

        /**
         * zokusei
         */
    @TableField("zokusei")
        private String zokusei;

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

        /**
         * dgaitong
         */
    @TableField("dgaitong")
        private Date dgaitong;


        }