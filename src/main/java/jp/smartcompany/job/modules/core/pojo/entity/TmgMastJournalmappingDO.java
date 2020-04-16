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
 * 仕訳項目マッピングマスタ                  日次仕訳処理において、各種仕訳処理の結果を仕訳項目にマッピン
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
@TableName("tmg_mast_journalmapping")
public class TmgMastJournalmappingDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmjm_ccustomerid")
        private String tmjmCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmjm_ccompanyid")
        private String tmjmCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmjm_dstartdate")
        private Date tmjmDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmjm_denddate")
        private Date tmjmDenddate;

        /**
         * 更新者
         */
    @TableField("tmjm_cmodifieruserid")
        private String tmjmCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmjm_dmodifieddate")
        private Date tmjmDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmjm_cmodifierprogramid")
        private String tmjmCmodifierprogramid;

        /**
         * 労働時間ｺｰﾄﾞ
         */
    @TableField("tmjm_cworkinghourstypeid")
        private String tmjmCworkinghourstypeid;

        /**
         * 超勤閾値ｺｰﾄﾞ
         */
    @TableField("tmjm_covertmjmeborderid")
        private String tmjmCovertmjmeborderid;

        /**
         * 超勤命令ｺｰﾄﾞ
         */
    @TableField("tmjm_cinstructiontypeid")
        private String tmjmCinstructiontypeid;

        /**
         * 深夜重複ﾌﾗｸﾞ
         */
    @TableField("tmjm_cworktimezone")
        private String tmjmCworktimezone;

        /**
         * 仕訳項目ｺｰﾄﾞ
         */
    @TableField("tmjm_cjournalizingid")
        private String tmjmCjournalizingid;

        /**
         * 予定内勤務ﾌﾗｸﾞ
         */
    @TableField("tmjm_cplanwork_inner")
        private Long tmjmCplanworkInner;

        /**
         * 法定内勤務ﾌﾗｸﾞ
         */
    @TableField("tmjm_cleagalwork_inner")
        private Long tmjmCleagalworkInner;


        }