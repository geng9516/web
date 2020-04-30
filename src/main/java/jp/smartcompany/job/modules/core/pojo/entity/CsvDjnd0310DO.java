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
 * public.csv_djnd0310
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
@TableName("csv_djnd0310")
public class CsvDjnd0310DO implements Serializable {

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
         * symk_kbn
         */
    @TableField("symk_kbn")
        private String symkKbn;

        /**
         * seq_num
         */
    @TableField("seq_num")
        private Long seqNum;

        /**
         * htreingb_dte
         */
    @TableField("htreingb_dte")
        private String htreingbDte;

        /**
         * dhtreingb_dte
         */
    @TableField("dhtreingb_dte")
        private Date dhtreingbDte;

        /**
         * ido_cde
         */
    @TableField("ido_cde")
        private String idoCde;

        /**
         * ido_nme
         */
    @TableField("ido_nme")
        private String idoNme;

        /**
         * reki1_cde
         */
    @TableField("reki1_cde")
        private String reki1Cde;

        /**
         * reki1_nme
         */
    @TableField("reki1_nme")
        private String reki1Nme;

        /**
         * reki2_cde
         */
    @TableField("reki2_cde")
        private String reki2Cde;

        /**
         * reki2_nme
         */
    @TableField("reki2_nme")
        private String reki2Nme;

        /**
         * reki3_cde
         */
    @TableField("reki3_cde")
        private String reki3Cde;

        /**
         * reki3_nme
         */
    @TableField("reki3_nme")
        private String reki3Nme;

        /**
         * reki4_cde
         */
    @TableField("reki4_cde")
        private String reki4Cde;

        /**
         * reki4_nme
         */
    @TableField("reki4_nme")
        private String reki4Nme;

        /**
         * reki5_cde
         */
    @TableField("reki5_cde")
        private String reki5Cde;

        /**
         * reki5_nme
         */
    @TableField("reki5_nme")
        private String reki5Nme;

        /**
         * reki1_num
         */
    @TableField("reki1_num")
        private Long reki1Num;

        /**
         * reki2_num
         */
    @TableField("reki2_num")
        private Long reki2Num;

        /**
         * reki3_num
         */
    @TableField("reki3_num")
        private Long reki3Num;

        /**
         * reki1_dte
         */
    @TableField("reki1_dte")
        private String reki1Dte;

        /**
         * dreki1_dte
         */
    @TableField("dreki1_dte")
        private Date dreki1Dte;

        /**
         * reki2_dte
         */
    @TableField("reki2_dte")
        private String reki2Dte;

        /**
         * dreki2_dte
         */
    @TableField("dreki2_dte")
        private Date dreki2Dte;

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
         * reki1_flg
         */
    @TableField("reki1_flg")
        private String reki1Flg;

        /**
         * reki2_flg
         */
    @TableField("reki2_flg")
        private String reki2Flg;

        /**
         * reki6_cde
         */
    @TableField("reki6_cde")
        private String reki6Cde;

        /**
         * reki6_nme
         */
    @TableField("reki6_nme")
        private String reki6Nme;

        /**
         * reki7_cde
         */
    @TableField("reki7_cde")
        private String reki7Cde;

        /**
         * reki7_nme
         */
    @TableField("reki7_nme")
        private String reki7Nme;

        /**
         * reki8_cde
         */
    @TableField("reki8_cde")
        private String reki8Cde;

        /**
         * reki8_nme
         */
    @TableField("reki8_nme")
        private String reki8Nme;

        /**
         * reki9_cde
         */
    @TableField("reki9_cde")
        private String reki9Cde;

        /**
         * reki9_nme
         */
    @TableField("reki9_nme")
        private String reki9Nme;

        /**
         * reki4_num
         */
    @TableField("reki4_num")
        private Long reki4Num;

        /**
         * reki5_num
         */
    @TableField("reki5_num")
        private Long reki5Num;

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