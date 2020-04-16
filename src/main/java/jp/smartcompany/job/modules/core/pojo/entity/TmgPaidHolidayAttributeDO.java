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
 * [勤怠]年次休暇付与属性テーブル
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
@TableName("tmg_paid_holiday_attribute")
public class TmgPaidHolidayAttributeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tpha_ccustomerid")
        private String tphaCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tpha_ccompanyid")
        private String tphaCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tpha_cemployeeid")
        private String tphaCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tpha_dstartdate")
        private Date tphaDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tpha_denddate")
        private Date tphaDenddate;

        /**
         * 更新者
         */
    @TableField("tpha_cmodifieruserid")
        private String tphaCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tpha_dmodifieddate")
        private Date tphaDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tpha_cmodifierprogramid")
        private String tphaCmodifierprogramid;

        /**
         * 週平均勤務時間数
         */
    @TableField("tpha_navgworktime")
        private Long tphaNavgworktime;

        /**
         * 週の所定労働日数
         */
    @TableField("tpha_nworkingdays_week")
        private Long tphaNworkingdaysWeek;

        /**
         * 予定勤務パターン
         */
    @TableField("tpha_cworkingdays_week")
        private String tphaCworkingdaysWeek;


        }