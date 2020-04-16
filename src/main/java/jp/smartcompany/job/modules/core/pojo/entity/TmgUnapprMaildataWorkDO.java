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
 * メール送信ワークテーブル
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
@TableName("tmg_unappr_maildata_work")
public class TmgUnapprMaildataWorkDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "tumw_id", type = IdType.AUTO)
                private Long tumwId;

        /**
         * 顧客コード
         */
    @TableField("tumw_ccustomerid")
        private String tumwCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tumw_ccompanyid")
        private String tumwCcompanyid;

        /**
         * 最終更新者
         */
    @TableField("tumw_cmodifieruserid")
        private String tumwCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("tumw_dmodifieddate")
        private Date tumwDmodifieddate;

        /**
         * 最終更新プログラムid
         */
    @TableField("tumw_cmodifierprogramid")
        private String tumwCmodifierprogramid;

        /**
         * イベントid
         */
    @TableField("tumw_ceventid")
        private String tumwCeventid;

        /**
         * メール定義id
         */
    @TableField("tumw_cid")
        private String tumwCid;

        /**
         * 送信先の職員番号
         */
    @TableField("tumw_ctoemployeeid")
        private String tumwCtoemployeeid;

        /**
         * 送信先の職員名称
         */
    @TableField("thmw_ctoemployee_name")
        private String thmwCtoemployeeName;

        /**
         * 送信先の職員メールアドレス
         */
    @TableField("thmw_ctoaddress")
        private String thmwCtoaddress;

        /**
         * 未承認の職員番号
         */
    @TableField("tumw_cemployeeid")
        private String tumwCemployeeid;

        /**
         * 未承認の職員名称
         */
    @TableField("tumw_cemployee_name")
        private String tumwCemployeeName;

        /**
         * 部局コード
         */
    @TableField("tumw_csectionid")
        private String tumwCsectionid;

        /**
         * グループコード
         */
    @TableField("tumw_cgroupid")
        private String tumwCgroupid;

        /**
         * グループ名称
         */
    @TableField("tumw_cgroupname")
        private String tumwCgroupname;

        /**
         * 承認部局コード
         */
    @TableField("tumw_ceva_sectionid")
        private String tumwCevaSectionid;

        /**
         * 役職順位
         */
    @TableField("tumw_nmp_weight")
        private Long tumwNmpWeight;

        /**
         * 出力順
         */
    @TableField("tumw_nsort")
        private Long tumwNsort;

        /**
         * 超勤：宛先区分（1:本人 2:承認者）
         */
    @TableField("tumw_naddress_flg")
        private Long tumwNaddressFlg;

        /**
         * 超勤：閾値（時間）
         */
    @TableField("tumw_novertime_max_limit")
        private Long tumwNovertimeMaxLimit;

        /**
         * 超勤した時間（分単位）
         */
    @TableField("tumw_novertime_value")
        private Long tumwNovertimeValue;


        }