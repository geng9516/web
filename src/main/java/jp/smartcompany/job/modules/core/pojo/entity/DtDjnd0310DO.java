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
 * 個人履歴情報db
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
@TableName("dt_djnd0310")
public class DtDjnd0310DO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 会社区分
         */
    @TableField("ccompkb")
        private String ccompkb;

        /**
         * 開始日
         */
    @TableField("dstart")
        private Date dstart;

        /**
         * 開始日（和暦）
         */
    @TableField("sstart")
        private String sstart;

        /**
         * 終了日
         */
    @TableField("dend")
        private Date dend;

        /**
         * 終了日（和暦）
         */
    @TableField("send")
        private String send;

        /**
         * 職員番号
         */
    @TableField("cshainno")
        private String cshainno;

        /**
         * 種目区分
         */
    @TableField("symk_kbn")
        private String symkKbn;

        /**
         * 同日seq
         */
    @TableField("seq_num")
        private Long seqNum;

        /**
         * 発令年月日
         */
    @TableField("htreingb_dte")
        private String htreingbDte;

        /**
         * 発令年月日（西暦）
         */
    @TableField("dhtreingb_dte")
        private Date dhtreingbDte;

        /**
         * 異動種目コード
         */
    @TableField("ido_cde")
        private String idoCde;

        /**
         * 異動種目
         */
    @TableField("ido_nme")
        private String idoNme;

        /**
         * コード（１）
         */
    @TableField("reki1_cde")
        private String reki1Cde;

        /**
         * 名称（１）                         2001.06.16
         */
    @TableField("reki1_nme")
        private String reki1Nme;

        /**
         * コード（２）
         */
    @TableField("reki2_cde")
        private String reki2Cde;

        /**
         * 名称（２）                         2001.06.16
         */
    @TableField("reki2_nme")
        private String reki2Nme;

        /**
         * コード（３）
         */
    @TableField("reki3_cde")
        private String reki3Cde;

        /**
         * 名称（３）                         2001.06.16
         */
    @TableField("reki3_nme")
        private String reki3Nme;

        /**
         * コード（４）
         */
    @TableField("reki4_cde")
        private String reki4Cde;

        /**
         * 名称（４）                         2001.06.16
         */
    @TableField("reki4_nme")
        private String reki4Nme;

        /**
         * コード（５）
         */
    @TableField("reki5_cde")
        private String reki5Cde;

        /**
         * 名称（５）                         2001.06.16
         */
    @TableField("reki5_nme")
        private String reki5Nme;

        /**
         * 数字（１）
         */
    @TableField("reki1_num")
        private Long reki1Num;

        /**
         * 数字（２）
         */
    @TableField("reki2_num")
        private Long reki2Num;

        /**
         * 数字（３）
         */
    @TableField("reki3_num")
        private Long reki3Num;

        /**
         * 日付（１）
         */
    @TableField("reki1_dte")
        private String reki1Dte;

        /**
         * 日付（１）（西暦）
         */
    @TableField("dreki1_dte")
        private Date dreki1Dte;

        /**
         * 日付（２）
         */
    @TableField("reki2_dte")
        private String reki2Dte;

        /**
         * 日付（２）（西暦）
         */
    @TableField("dreki2_dte")
        private Date dreki2Dte;

        /**
         * 基準年月日
         */
    @TableField("key_dte")
        private String keyDte;

        /**
         * 基準年月日（西暦）
         */
    @TableField("dkey_dte")
        private Date dkeyDte;

        /**
         * 更新者
         */
    @TableField("kosin_use")
        private String kosinUse;

        /**
         * 更新pgm
         */
    @TableField("last_pgm")
        private String lastPgm;

        /**
         * 更新日時
         */
    @TableField("last_dte")
        private Date lastDte;

        /**
         * 更新区分
         */
    @TableField("kosin_kbn")
        private String kosinKbn;

        /**
         * フラグ（１）
         */
    @TableField("reki1_flg")
        private String reki1Flg;

        /**
         * フラグ（２）
         */
    @TableField("reki2_flg")
        private String reki2Flg;

        /**
         * コード（６）
         */
    @TableField("reki6_cde")
        private String reki6Cde;

        /**
         * 名称（６）                         2001.06.16
         */
    @TableField("reki6_nme")
        private String reki6Nme;

        /**
         * コード（７）                        2001.11.22 add
         */
    @TableField("reki7_cde")
        private String reki7Cde;

        /**
         * 名称（７）                         2001.11.22 add
         */
    @TableField("reki7_nme")
        private String reki7Nme;

        /**
         * コード（８）                        2001.11.22 add
         */
    @TableField("reki8_cde")
        private String reki8Cde;

        /**
         * 名称（８）                         2001.11.22 add
         */
    @TableField("reki8_nme")
        private String reki8Nme;

        /**
         * コード（９）                        2001.11.22 add
         */
    @TableField("reki9_cde")
        private String reki9Cde;

        /**
         * 名称（９）                         2001.11.22 add
         */
    @TableField("reki9_nme")
        private String reki9Nme;

        /**
         * 数字（４）                         2001.11.22 add
         */
    @TableField("reki4_num")
        private Long reki4Num;

        /**
         * 数字（５）                         2001.11.22 add
         */
    @TableField("reki5_num")
        private Long reki5Num;

        /**
         * ﾕｰｻﾞｰ
         */
    @TableField("cmnuser")
        private String cmnuser;

        /**
         * 更新日
         */
    @TableField("dmndate")
        private Date dmndate;


        }