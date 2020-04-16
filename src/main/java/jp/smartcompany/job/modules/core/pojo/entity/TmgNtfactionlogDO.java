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
 * [勤怠]申請ログ情報
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
@TableName("tmg_ntfactionlog")
public class TmgNtfactionlogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tnal_ccustomerid")
        private String tnalCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tnal_ccompanyid")
        private String tnalCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tnal_cemployeeid")
        private String tnalCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tnal_dstartdate")
        private Date tnalDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tnal_denddate")
        private Date tnalDenddate;

        /**
         * 更新者
         */
    @TableField("tnal_cmodifieruserid")
        private String tnalCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tnal_dmodifieddate")
        private Date tnalDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tnal_cmodifierprogramid")
        private String tnalCmodifierprogramid;

        /**
         * 更新サイト
         */
    @TableField("tnal_csiteid")
        private String tnalCsiteid;

        /**
         * 申請番号                          申請者社員番号|連番
         */
    @TableField("tnal_cntfno")
        private String tnalCntfno;

        /**
         * 操作種別
         */
    @TableField("tnal_cntfaction")
        private String tnalCntfaction;

        /**
         * 更新前ステータスフラグ mgd:tmg_ntfstatus
         */
    @TableField("tnal_cpre_statusflg")
        private String tnalCpreStatusflg;

        /**
         * 更新前決裁レベル mgd:tmg_approval_level
         */
    @TableField("tnal_cpre_approval_level")
        private String tnalCpreApprovalLevel;

        /**
         * 更新後ステータスフラグ mgd:tmg_ntfstatus
         */
    @TableField("tnal_caft_statusflg")
        private String tnalCaftStatusflg;

        /**
         * 更新後決裁レベル mgd:tmg_approval_level
         */
    @TableField("tnal_caft_approval_level")
        private String tnalCaftApprovalLevel;

        /**
         * 更新時コメント
         */
    @TableField("tnal_cupdateccomment")
        private String tnalCupdateccomment;


        }