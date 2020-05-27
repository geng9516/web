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
 * [勤怠]月次集計処理ログ
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
@TableName("tmg_monthly_output_log")
public class TmgMonthlyOutputLogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableId(value="tmol_ccustomerid",type = IdType.INPUT)
        private String tmolCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmol_ccompanyid")
        private String tmolCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tmol_dstartdate")
        private Date tmolDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tmol_denddate")
        private Date tmolDenddate;

        /**
         * 更新者
         */
    @TableField("tmol_cmodifieruserid")
        private String tmolCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmol_dmodifieddate")
        private Date tmolDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmol_cmodifierprogramid")
        private String tmolCmodifierprogramid;

        /**
         * 該当年月
         */
    @TableField("tmol_dyyyymm")
        private Date tmolDyyyymm;

        /**
         * 処理ステータス                                                     mgd:tmg_calcstatus
         */
    @TableField("tmol_cstatusflg")
        private String tmolCstatusflg;

        /**
         * 処理件数
         */
    @TableField("tmol_ncount")
        private Long tmolNcount;

        /**
         * エラーコード                        oracleのエラーコードを格納
         */
    @TableField("tmol_cerrcode")
        private String tmolCerrcode;

        /**
         * エラーメッセージ                      oracleのエラーメッセージを格納
         */
    @TableField("tmol_cerrmsg")
        private String tmolCerrmsg;


        }