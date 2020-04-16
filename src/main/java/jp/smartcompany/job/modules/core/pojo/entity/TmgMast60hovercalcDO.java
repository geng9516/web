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
 * 60hオーバー算定マスタ
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
@TableName("tmg_mast_60hovercalc")
public class TmgMast60hovercalcDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableField("tm60_ccustomerid")
        private String tm60Ccustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tm60_ccompanyid")
        private String tm60Ccompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tm60_dstartdate")
        private Date tm60Dstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tm60_denddate")
        private Date tm60Denddate;

        /**
         * 更新者
         */
    @TableField("tm60_cmodifieruserid")
        private String tm60Cmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tm60_dmodifieddate")
        private Date tm60Dmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tm60_cmodifierprogramid")
        private String tm60Cmodifierprogramid;

        /**
         * 勤怠種別ｺｰﾄﾞ
         */
                @TableId(value = "tm60_cworktypeid", type = IdType.AUTO)
                private String tm60Cworktypeid;

        /**
         * 勤務時間帯区分
         */
    @TableField("tm60_cworktimezone")
        private String tm60Cworktimezone;

        /**
         * 労働時間ｺｰﾄﾞ
         */
    @TableField("tm60_cworkinghourstypeid")
        private String tm60Cworkinghourstypeid;

        /**
         * 予定内勤務ﾌﾗｸﾞ       予定内：1　予定外：0
         */
    @TableField("tm60_nplanwork_inner")
        private Long tm60NplanworkInner;

        /**
         * 法定内勤務ﾌﾗｸﾞ     法定内：1　法定外：0
         */
    @TableField("tm60_nleagalwork_inner")
        private Long tm60NleagalworkInner;

        /**
         * 閾値合計対象ﾌﾗｸﾞ
         */
    @TableField("tm60_ninclude60hoverflg")
        private Long tm60Ninclude60hoverflg;


        }