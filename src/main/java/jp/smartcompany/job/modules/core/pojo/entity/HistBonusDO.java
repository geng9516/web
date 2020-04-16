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
 * 賞与情報
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
@TableName("hist_bonus")
public class HistBonusDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hbn_id", type = IdType.AUTO)
                private Long hbnId;

        /**
         * 顧客コード
         */
    @TableField("hbn_ccustomerid")
        private String hbnCcustomerid;

        /**
         * 法人コード
         */
    @TableField("hbn_ccompanyid")
        private String hbnCcompanyid;

        /**
         * 社員番号
         */
    @TableField("hbn_cemployeeid")
        private String hbnCemployeeid;

        /**
         * ユーザid
         */
    @TableField("hbn_cuserid")
        private String hbnCuserid;

        /**
         * データ開始日
         */
    @TableField("hbn_dstartdate")
        private Date hbnDstartdate;

        /**
         * データ終了日
         */
    @TableField("hbn_denddate")
        private Date hbnDenddate;

        /**
         * 最終更新者
         */
    @TableField("hbn_cmodifieruserid")
        private String hbnCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hbn_dmodifieddate")
        private Date hbnDmodifieddate;

        /**
         * 支給年月
         */
    @TableField("hbn_ddateofpayment")
        private Date hbnDdateofpayment;

        /**
         * 金額1
         */
    @TableField("hbn_npayment01")
        private Long hbnNpayment01;

        /**
         * 金額2
         */
    @TableField("hbn_npayment02")
        private Long hbnNpayment02;

        /**
         * 金額3
         */
    @TableField("hbn_npayment03")
        private Long hbnNpayment03;

        /**
         * 金額4
         */
    @TableField("hbn_npayment04")
        private Long hbnNpayment04;

        /**
         * 金額5
         */
    @TableField("hbn_npayment05")
        private Long hbnNpayment05;

        /**
         * 金額6
         */
    @TableField("hbn_npayment06")
        private Long hbnNpayment06;

        /**
         * 金額7
         */
    @TableField("hbn_npayment07")
        private Long hbnNpayment07;

        /**
         * 金額8
         */
    @TableField("hbn_npayment08")
        private Long hbnNpayment08;

        /**
         * 金額9
         */
    @TableField("hbn_npayment09")
        private Long hbnNpayment09;

        /**
         * 金額10
         */
    @TableField("hbn_npayment10")
        private Long hbnNpayment10;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }