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
 * [勤怠]反映済打刻データログ
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
@TableName("tmg_hist_timepunch")
public class TmgHistTimepunchDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * id                            対応seq無し（ttp_nidをセット）
         */
                @TableId(value = "thtp_nid", type = IdType.AUTO)
                private String thtpNid;

        /**
         * 顧客コード
         */
    @TableField("thtp_ccustomerid")
        private String thtpCcustomerid;

        /**
         * 法人コード
         */
    @TableField("thtp_ccompanyid")
        private String thtpCcompanyid;

        /**
         * 職員番号
         */
    @TableField("thtp_cemployeeid")
        private String thtpCemployeeid;

        /**
         * データ開始日
         */
    @TableField("thtp_dstartdate")
        private Date thtpDstartdate;

        /**
         * データ終了日
         */
    @TableField("thtp_denddate")
        private Date thtpDenddate;

        /**
         * 更新ユーザーid
         */
    @TableField("thtp_cmodifieruserid")
        private String thtpCmodifieruserid;

        /**
         * 更新日時
         */
    @TableField("thtp_dmodifieddate")
        private Date thtpDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("thtp_cmodifierprogramid")
        private String thtpCmodifierprogramid;

        /**
         * 打刻年月日                         時刻丸め
         */
    @TableField("thtp_dtpdate")
        private Date thtpDtpdate;

        /**
         * 打刻時刻
         */
    @TableField("thtp_dtptime")
        private Date thtpDtptime;

        /**
         * 打刻区分                                                        mgd：tmg_tptype
         */
    @TableField("thtp_ctptypeid")
        private String thtpCtptypeid;

        /**
         * 反映先勤務日                        反映先のtmg_daily
         */
    @TableField("thtp_dyyyymmdd")
        private Date thtpDyyyymmdd;

        /**
         * 反映ステータス                                                     mgd：tmg_tpstatus
         */
    @TableField("thtp_ctpstatus")
        private String thtpCtpstatus;

        /**
         * 備考                            反映失敗時のエラーコード等
         */
    @TableField("thtp_cmemo")
        private String thtpCmemo;


        }