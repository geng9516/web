package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * [勤怠]打刻データ（未反映）
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
@TableName("tmg_timepunch")
@KeySequence("TMG_TIMEPUNCH_SEQ")
public class TmgTimepunchDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * id                                                          tmg_timepunch_seq
         */
                @TableId(value = "ttp_nid")
                private Long ttpNid;

        /**
         * 顧客コード
         */
    @TableField("ttp_ccustomerid")
        private String ttpCcustomerid;

        /**
         * 法人コード
         */
    @TableField("ttp_ccompanyid")
        private String ttpCcompanyid;

        /**
         * 職員番号
         */
    @TableField("ttp_cemployeeid")
        private String ttpCemployeeid;

        /**
         * データ開始日
         */
    @TableField("ttp_dstartdate")
        private Date ttpDstartdate;

        /**
         * データ終了日
         */
    @TableField("ttp_denddate")
        private Date ttpDenddate;

        /**
         * 更新ユーザーid
         */
    @TableField("ttp_cmodifieruserid")
        private String ttpCmodifieruserid;

        /**
         * 更新日時
         */
    @TableField("ttp_dmodifieddate")
        private Date ttpDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("ttp_cmodifierprogramid")
        private String ttpCmodifierprogramid;

        /**
         * 打刻年月日                         時刻丸め
         */
    @TableField("ttp_dtpdate")
        private Date ttpDtpdate;

        /**
         * 打刻時刻
         */
    @TableField("ttp_dtptime")
        private Date ttpDtptime;

        /**
         * 打刻区分                                                        mgd：tmg_tptype
         */
    @TableField("ttp_ctptypeid")
        private String ttpCtptypeid;


        }