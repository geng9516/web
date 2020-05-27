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
 * [勤怠]ノー残業デー設定ワークテーブル
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
@TableName("tmg_hist_no_overtime_days")
public class TmgHistNoOvertimeDaysDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コードﾞ     固定：01
         */
    @TableId(value="thnod_ccustomerid",type = IdType.INPUT)
        private String thnodCcustomerid;

        /**
         * 法人コード
         */
    @TableField("thnod_ccompanyid")
        private String thnodCcompanyid;

        /**
         * 社員番号
         */
    @TableField("thnod_cemployeeid")
        private String thnodCemployeeid;

        /**
         * データ開始日
         */
    @TableField("thnod_dstartdate")
        private Date thnodDstartdate;

        /**
         * データ終了日
         */
    @TableField("thnod_denddate")
        private Date thnodDenddate;

        /**
         * 更新者
         */
    @TableField("thnod_cmodifieruserid")
        private String thnodCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("thnod_dmodifieddate")
        private Date thnodDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("thnod_cmodifierprogramid")
        private String thnodCmodifierprogramid;

        /**
         * 対象日付
         */
    @TableField("thnod_dtargetdate")
        private Date thnodDtargetdate;

        /**
         * 所属コード
         */
    @TableField("thnod_csectionid")
        private String thnodCsectionid;


        }