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
 * [勤怠]初期移行用連携データ格納テーブル
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
@TableName("tmg_if_initialize")
public class TmgIfInitializeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tii_ccustomerid")
        private String tiiCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tii_ccompanyid")
        private String tiiCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "tii_cemployeeid", type = IdType.AUTO)
                private String tiiCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tii_dstartdate")
        private Date tiiDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tii_denddate")
        private Date tiiDenddate;

        /**
         * 更新者
         */
    @TableField("tii_cmodifieruserid")
        private String tiiCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tii_dmodifieddate")
        private Date tiiDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tii_cmodifierprogramid")
        private String tiiCmodifierprogramid;

        /**
         * 利用開始年月                        yyyymm
         */
    @TableField("tii_dbegindate")
        private Date tiiDbegindate;

        /**
         * 年休残日数
         */
    @TableField("tii_npaid_rest_days")
        private Long tiiNpaidRestDays;

        /**
         * 年休残時間数                        分単位で格納
         */
    @TableField("tii_npaid_rest_hours")
        private Long tiiNpaidRestHours;


        }