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
 * 取込出張データ
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
@TableName("tmg_hist_shucho")
public class TmgHistShuchoDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム シーケンスtmg_hist_shucho_seqを作る必要があります。
         */
                @TableId(value = "ths_id", type = IdType.AUTO)
                private Long thsId;

        /**
         * 顧客コード
         */
    @TableField("ths_ccustomerid")
        private String thsCcustomerid;

        /**
         * 法人コード
         */
    @TableField("ths_ccompanyid")
        private String thsCcompanyid;

        /**
         * データ開始日
         */
    @TableField("ths_dstart")
        private Date thsDstart;

        /**
         * データ終了日
         */
    @TableField("ths_dend")
        private Date thsDend;

        /**
         * 個人番号
         */
    @TableField("ths_cemployeeid")
        private String thsCemployeeid;

        /**
         * 氏名
         */
    @TableField("ths_ckanjiname")
        private String thsCkanjiname;

        /**
         * 所属
         */
    @TableField("ths_csection_name")
        private String thsCsectionName;

        /**
         * 処理番号
         */
    @TableField("ths_cshucho_id")
        private String thsCshuchoId;

        /**
         * 旅行開始日
         */
    @TableField("ths_dtripstart")
        private Date thsDtripstart;

        /**
         * 旅行終了日
         */
    @TableField("ths_dtripend")
        private Date thsDtripend;

        /**
         * 用務先１
         */
    @TableField("ths_cyoumu1")
        private String thsCyoumu1;

        /**
         * 用務先２
         */
    @TableField("ths_cyoumu2")
        private String thsCyoumu2;

        /**
         * 用務先３
         */
    @TableField("ths_cyoumu3")
        private String thsCyoumu3;

        /**
         * 海外出張 kaigai|0→国内出張、kaigai|1→海外出張
         */
    @TableField("ths_ckaigai_flag")
        private String thsCkaigaiFlag;

        /**
         * 取込日時
         */
    @TableField("ths_dtorikomi")
        private Date thsDtorikomi;

        /**
         * 処理日時
         */
    @TableField("ths_dshori")
        private Date thsDshori;

        /**
         * 処理フラグ shuchoflag|0→未、shuchoflag|1→済、shuchoflag|9→無効
         */
    @TableField("ths_cshori_flag")
        private String thsCshoriFlag;

        /**
         * 取消日時
         */
    @TableField("ths_dcancel")
        private Date thsDcancel;

        /**
         * 取消フラグ shuchoflag|0→未、shuchoflag|1→済
         */
    @TableField("ths_ccancel_flag")
        private String thsCcancelFlag;

        /**
         * メッセージ
         */
    @TableField("ths_cmessage")
        private String thsCmessage;

        /**
         * バージョンno v4互換用
         */
    @TableField("versionno")
        private Long versionno;


        }