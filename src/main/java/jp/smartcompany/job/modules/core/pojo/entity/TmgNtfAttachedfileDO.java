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
 * 休暇休業申請添付ファイルテーブル
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
@TableName("tmg_ntf_attachedfile")
public class TmgNtfAttachedfileDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableField("tnaf_ccustomerid")
        private String tnafCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tnaf_ccompanyid")
        private String tnafCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tnaf_cemployeeid")
        private String tnafCemployeeid;

        /**
         * 申請番号
         */
                @TableId(value = "tnaf_cntfno", type = IdType.AUTO)
                private String tnafCntfno;

        /**
         * ファイル名
         */
    @TableField("tnaf_cfilename")
        private String tnafCfilename;

        /**
         * 表示順
         */
    @TableField("tnaf_nseq")
        private Long tnafNseq;

        /**
         * 添付ファイル
         */
    @TableField("tnaf_battach")
        private String tnafBattach;

        /**
         * 更新ユーザーid
         */
    @TableField("tnaf_cmodifieruserid")
        private String tnafCmodifieruserid;

        /**
         * 更新日時
         */
    @TableField("tnaf_dmodifieddate")
        private Date tnafDmodifieddate;


        }