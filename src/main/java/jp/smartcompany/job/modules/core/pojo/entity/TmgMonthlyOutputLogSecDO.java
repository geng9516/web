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
 * [勤怠]月次集計処理ログ(部局別)
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
@TableName("tmg_monthly_output_log_sec")
public class TmgMonthlyOutputLogSecDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableId(value="tmols_ccustomerid",type=IdType.INPUT)
        private String tmolsCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmols_ccompanyid")
        private String tmolsCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tmols_dstartdate")
        private Date tmolsDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tmols_denddate")
        private Date tmolsDenddate;

        /**
         * 更新者
         */
    @TableField("tmols_cmodifieruserid")
        private String tmolsCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmols_dmodifieddate")
        private Date tmolsDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmols_cmodifierprogramid")
        private String tmolsCmodifierprogramid;

        /**
         * 該当年月
         */
    @TableField("tmols_dyyyymm")
        private Date tmolsDyyyymm;

        /**
         * 所属コード
         */
    @TableField("tmols_csectionid")
        private String tmolsCsectionid;

        /**
         * ジョブno
         */
    @TableField("tmols_njobno")
        private Long tmolsNjobno;

        /**
         * 処理ステータス                                                     mgd:tmg_calcstatus
         */
    @TableField("tmols_cstatusflg")
        private String tmolsCstatusflg;

        /**
         * 処理件数
         */
    @TableField("tmols_ncount")
        private Long tmolsNcount;

        /**
         * エラーコード                        oracleのエラーコードを格納
         */
    @TableField("tmols_cerrcode")
        private String tmolsCerrcode;

        /**
         * エラーメッセージ                      oracleのエラーメッセージを格納
         */
    @TableField("tmols_cerrmsg")
        private String tmolsCerrmsg;


        }