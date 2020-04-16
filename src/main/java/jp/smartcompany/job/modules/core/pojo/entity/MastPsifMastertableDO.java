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
 * インターフェース制御テーブル
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
@TableName("mast_psif_mastertable")
public class MastPsifMastertableDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
                @TableId(value = "mp_ccustomerid", type = IdType.AUTO)
                private String mpCcustomerid;

        /**
         * 法人コード
         */
    @TableField("mp_ccompanyid")
        private String mpCcompanyid;

        /**
         * グループid
         */
    @TableField("mp_cgroupid")
        private String mpCgroupid;

        /**
         * グループ名
         */
    @TableField("mp_cgroupnm")
        private String mpCgroupnm;

        /**
         * コード
         */
    @TableField("mp_ccode")
        private String mpCcode;

        /**
         * マスタコード
         */
    @TableField("mp_cmasterkb")
        private String mpCmasterkb;

        /**
         * 開始日
         */
    @TableField("mp_dstart")
        private Date mpDstart;

        /**
         * 終了日
         */
    @TableField("mp_dend")
        private Date mpDend;

        /**
         * マスタ名称
         */
    @TableField("mp_cdetail")
        private String mpCdetail;

        /**
         * 名称１
         */
    @TableField("mp_cyobi1")
        private String mpCyobi1;

        /**
         * 名称２
         */
    @TableField("mp_cyobi2")
        private String mpCyobi2;

        /**
         * 名称３
         */
    @TableField("mp_cyobi3")
        private String mpCyobi3;

        /**
         * 名称４
         */
    @TableField("mp_cyobi4")
        private String mpCyobi4;

        /**
         * 名称５
         */
    @TableField("mp_cyobi5")
        private String mpCyobi5;

        /**
         * 数値１
         */
    @TableField("mp_nyobi1")
        private Long mpNyobi1;

        /**
         * 数値２
         */
    @TableField("mp_nyobi2")
        private Long mpNyobi2;

        /**
         * 数値３
         */
    @TableField("mp_nyobi3")
        private Long mpNyobi3;

        /**
         * 数値４
         */
    @TableField("mp_nyobi4")
        private Long mpNyobi4;

        /**
         * 数値５
         */
    @TableField("mp_nyobi5")
        private Long mpNyobi5;


        }