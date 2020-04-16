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
 * 労働時間マスタ
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
@TableName("tmg_mast_workinghourstype")
public class TmgMastWorkinghourstypeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmwt_ccustomerid")
        private String tmwtCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmwt_ccompanyid")
        private String tmwtCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmwt_dstartdate")
        private Date tmwtDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmwt_denddate")
        private Date tmwtDenddate;

        /**
         * 更新者
         */
    @TableField("tmwt_cmodifieruserid")
        private String tmwtCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmwt_dmodifieddate")
        private Date tmwtDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmwt_cmodifierprogramid")
        private String tmwtCmodifierprogramid;

        /**
         * 勤怠種別
         */
                @TableId(value = "tmwt_cworktypeid", type = IdType.AUTO)
                private String tmwtCworktypeid;

        /**
         * 労働時間ｺｰﾄﾞ
         */
    @TableField("tmwt_cworkinghourstypeid")
        private String tmwtCworkinghourstypeid;

        /**
         * 労働時間名称
         */
    @TableField("tmwt_cworkinghourstypenm")
        private String tmwtCworkinghourstypenm;

        /**
         * 法定内外時間判定ﾌﾗｸﾞ
         */
    @TableField("tmwt_nleagalinoutflg")
        private Long tmwtNleagalinoutflg;


        }