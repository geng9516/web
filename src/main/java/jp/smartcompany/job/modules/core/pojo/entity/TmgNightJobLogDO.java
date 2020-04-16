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
 * [勤怠]夜間更新処理ログ
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
@TableName("tmg_night_job_log")
public class TmgNightJobLogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tnjl_ccustomerid")
        private String tnjlCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tnjl_ccompanyid")
        private String tnjlCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tnjl_dstartdate")
        private Date tnjlDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tnjl_denddate")
        private Date tnjlDenddate;

        /**
         * 更新者
         */
    @TableField("tnjl_cmodifieruserid")
        private String tnjlCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tnjl_dmodifieddate")
        private Date tnjlDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tnjl_cmodifierprogramid")
        private String tnjlCmodifierprogramid;

        /**
         * 対象日
         */
    @TableField("tnjl_dyyyymm")
        private Date tnjlDyyyymm;

        /**
         * 処理開始時刻
         */
    @TableField("tnjl_dopen")
        private Date tnjlDopen;

        /**
         * 処理終了時刻
         */
    @TableField("tnjl_dclose")
        private Date tnjlDclose;

        /**
         * 処理ステータス                                                     mgd:tmg_calcstatus
         */
    @TableField("tnjl_cstatusflg")
        private String tnjlCstatusflg;

        /**
         * 処理件数
         */
    @TableField("tnjl_ncount")
        private Long tnjlNcount;

        /**
         * エラーコード                        oracleのエラーコードを格納
         */
    @TableField("tnjl_cerrcode")
        private String tnjlCerrcode;

        /**
         * エラーメッセージ                      oracleのエラーメッセージを格納
         */
    @TableField("tnjl_cerrmsg")
        private String tnjlCerrmsg;


        }