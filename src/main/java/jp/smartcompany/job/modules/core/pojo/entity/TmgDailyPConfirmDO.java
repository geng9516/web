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
 * [勤怠]予定確認比較用情報
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
@TableName("tmg_daily_p_confirm")
public class TmgDailyPConfirmDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tdc_ccustomerid")
        private String tdcCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tdc_ccompanyid")
        private String tdcCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "tdc_cemployeeid", type = IdType.AUTO)
                private String tdcCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tdc_dstartdate")
        private Date tdcDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tdc_denddate")
        private Date tdcDenddate;

        /**
         * 更新者
         */
    @TableField("tdc_cmodifieruserid")
        private String tdcCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tdc_dmodifieddate")
        private Date tdcDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tdc_cmodifierprogramid")
        private String tdcCmodifierprogramid;

        /**
         * 該当年月                          yyyy/mm/01
         */
    @TableField("tdc_dyyyymm")
        private Date tdcDyyyymm;

        /**
         * 勤務年月日                         yyyy/mm/dd
         */
    @TableField("tdc_dyyyymmdd")
        private Date tdcDyyyymmdd;

        /**
         * [予定]就業区分                                                    mgd:tmg_work
         */
    @TableField("tdc_cworkingid_p")
        private String tdcCworkingidP;

        /**
         * [予定]出張区分                                                    mgd:tmg_business_trip
         */
    @TableField("tdc_cbusinesstripid_p")
        private String tdcCbusinesstripidP;

        /**
         * 予定比較用文字列
         */
    @TableField("tdc_cconfirm")
        private String tdcCconfirm;


        }