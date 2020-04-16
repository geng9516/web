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
 * [勤怠]エラーチェック用グループ割付情報          データ開始日、終了日は親となる異動歴のデータ開始日、終了日と
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
@TableName("tmg_group_member_check")
public class TmgGroupMemberCheckDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tgrm_ccustomerid")
        private String tgrmCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tgrm_ccompanyid")
        private String tgrmCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "tgrm_cemployeeid", type = IdType.AUTO)
                private String tgrmCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tgrm_dstartdate")
        private Date tgrmDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
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
         * 部署コード                                                       mo:mo_csectionid_ck
         */
    @TableField("tgrm_csectionid")
        private String tgrmCsectionid;

        /**
         * グループコード                       グループでなく部署に対する設定の場合、null       tmg_group：tgr_csectionid
         */
    @TableField("tgrm_cgroupid")
        private String tgrmCgroupid;

        /**
         * tgrm_cchar01
         */
    @TableField("tgrm_cchar01")
        private String tgrmCchar01;

        /**
         * 組織.部署コード                                                       mo:mo_csectionid_ck
         */
    @TableField("tgrm_cbase_sectionid")
        private String tgrmCbaseSectionid;

        /**
         * 組織.グループコード                       グループでなく部署に対する設定の場合、null       tmg_group：tgr_csectionid
         */
    @TableField("tgrm_cbase_groupid")
        private String tgrmCbaseGroupid;


        }