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
 * [勤怠]ｇｗデータ連携  休暇等の不在情報（累計）
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
@TableName("tmg_hist_ooto4gw")
public class TmgHistOoto4gwDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * id                                                          tmg_hist_ooto4gw_seq
         */
                @TableId(value = "thow_nid", type = IdType.AUTO)
                private Long thowNid;

        /**
         * 顧客コード
         */
    @TableField("thow_ccustomerid")
        private String thowCcustomerid;

        /**
         * 法人コード
         */
    @TableField("thow_ccompanyid")
        private String thowCcompanyid;

        /**
         * 職員番号
         */
    @TableField("thow_cemployeeid")
        private String thowCemployeeid;

        /**
         * 開始日
         */
    @TableField("thow_dstartdate")
        private Date thowDstartdate;

        /**
         * 終了日
         */
    @TableField("thow_denddate")
        private Date thowDenddate;

        /**
         * 更新ユーザーid
         */
    @TableField("thow_cmodifieruserid")
        private String thowCmodifieruserid;

        /**
         * 更新日時
         */
    @TableField("thow_dmodifieddate")
        private Date thowDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("thow_cmodifierprogramid")
        private String thowCmodifierprogramid;

        /**
         * 開始時刻
         */
    @TableField("thow_nstarttime")
        private Long thowNstarttime;

        /**
         * 終了時刻
         */
    @TableField("thow_nendtime")
        private Long thowNendtime;

        /**
         * 作成日時
         */
    @TableField("thow_dcreatedate")
        private Date thowDcreatedate;

        /**
         * 削除フラグ
         */
    @TableField("thow_cdelflg")
        private Long thowCdelflg;

        /**
         * 削除日時
         */
    @TableField("thow_ddeldate")
        private Date thowDdeldate;

        /**
         * タイトル
         */
    @TableField("thow_ctitle")
        private String thowCtitle;


        }