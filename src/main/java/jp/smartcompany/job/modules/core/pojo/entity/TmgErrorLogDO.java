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
 * [勤怠]エラーログ
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
@TableName("tmg_error_log")
public class TmgErrorLogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "tel_nid", type = IdType.AUTO)
                private Long telNid;

        /**
         * 発生日時
         */
    @TableField("tel_derrdate")
        private Date telDerrdate;

        /**
         * チェック種別
         */
    @TableField("tel_cchksybt")
        private String telCchksybt;

        /**
         * 発生モジュール
         */
    @TableField("tel_cerrmodule")
        private String telCerrmodule;

        /**
         * エラーテーブル名
         */
    @TableField("tel_cerrtableid")
        private String telCerrtableid;

        /**
         * エラーメッセージ
         */
    @TableField("tel_cerrmessage")
        private String telCerrmessage;

        /**
         * データ判別用_顧客コード
         */
    @TableField("tel_cerrdt_ccustomerid_ck")
        private String telCerrdtCcustomeridCk;

        /**
         * データ判別用_法人コード
         */
    @TableField("tel_cerrdt_ccompanyid")
        private String telCerrdtCcompanyid;

        /**
         * データ判別用_データ開始日
         */
    @TableField("tel_derrdt_dstartdate")
        private Date telDerrdtDstartdate;

        /**
         * データ判別用_データ終了日
         */
    @TableField("tel_derrdt_denddate")
        private Date telDerrdtDenddate;

        /**
         * データ判別用_社員番号
         */
    @TableField("tel_cerrdt_cemployeeid_ck")
        private String telCerrdtCemployeeidCk;

        /**
         * データ判別用_ユーザid
         */
    @TableField("tel_cerrdt_cuserid")
        private String telCerrdtCuserid;

        /**
         * データ判別用_組織コード
         */
    @TableField("tel_cerrdt_csectionid_ck")
        private String telCerrdtCsectionidCk;

        /**
         * データ判別用_役職コード
         */
    @TableField("tel_cerrdt_cpostid_ck")
        private String telCerrdtCpostidCk;

        /**
         * データ判別用_名称マスタコード
         */
    @TableField("tel_cerrdt_cgenericgroupid")
        private String telCerrdtCgenericgroupid;

        /**
         * データ判別用_明細データコード
         */
    @TableField("tel_cerrdt_cgenericdetailid_ck")
        private String telCerrdtCgenericdetailidCk;

        /**
         * データ判別用_マスタコード
         */
    @TableField("tel_cerrdt_cmastercode")
        private String telCerrdtCmastercode;

        /**
         * データ判別用_該当年月
         */
    @TableField("tel_derrdt_dyyyymm")
        private Date telDerrdtDyyyymm;


        }