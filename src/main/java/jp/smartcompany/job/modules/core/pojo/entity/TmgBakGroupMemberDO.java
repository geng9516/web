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
 * [勤怠]入社取消用バックアップ  グループ割付情報
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
@TableName("tmg_bak_group_member")
public class TmgBakGroupMemberDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 作成日
         */
    @TableField("tgrm_dcreate")
        private Date tgrmDcreate;

        /**
         * 顧客コード                        固定：01
         */
    @TableId(value="tgrm_ccustomerid",type = IdType.INPUT)
        private String tgrmCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tgrm_ccompanyid")
        private String tgrmCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tgrm_cemployeeid")
        private String tgrmCemployeeid;

        /**
         * データ開始日
         */
    @TableField("tgrm_dstartdate")
        private Date tgrmDstartdate;

        /**
         * データ終了日
         */
    @TableField("tgrm_denddate")
        private Date tgrmDenddate;

        /**
         * 更新者
         */
    @TableField("tgrm_cmodifieruserid")
        private String tgrmCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tgrm_dmodifieddate")
        private Date tgrmDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tgrm_cmodifierprogramid")
        private String tgrmCmodifierprogramid;

        /**
         * 部署コード  mo:mo_csectionid_ck
         */
    @TableField("tgrm_csectionid")
        private String tgrmCsectionid;

        /**
         * グループコード  グループでなく部署に対する設定の場合、null  tmg_group：tgr_csectionid
         */
    @TableField("tgrm_cgroupid")
        private String tgrmCgroupid;

        /**
         * 部局コード(連携時の初期化用部局コード)
         */
    @TableField("tgrm_cchar01")
        private String tgrmCchar01;

        /**
         * 組織.部署コード  mo:mo_csectionid_ck
         */
    @TableField("tgrm_cbase_sectionid")
        private String tgrmCbaseSectionid;

        /**
         * 組織.グループコード  グループでなく部署に対する設定の場合、null  tmg_group：tgr_csectionid
         */
    @TableField("tgrm_cbase_groupid")
        private String tgrmCbaseGroupid;


        }