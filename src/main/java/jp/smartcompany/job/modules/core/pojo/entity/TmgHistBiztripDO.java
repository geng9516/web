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
 * 出張テーブル
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
@TableName("tmg_hist_biztrip")
public class TmgHistBiztripDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableField("thb_ccustomerid")
        private String thbCcustomerid;

        /**
         * 法人コード
         */
    @TableField("thb_ccompanyid")
        private String thbCcompanyid;

        /**
         * データ開始日
         */
    @TableField("thb_dstart")
        private Date thbDstart;

        /**
         * データ終了日
         */
    @TableField("thb_dend")
        private Date thbDend;

        /**
         * 職員番号
         */
    @TableField("thb_cemployeeid")
        private String thbCemployeeid;

        /**
         * 氏名
         */
    @TableField("thb_ckanjiname")
        private String thbCkanjiname;

        /**
         * 所属
         */
    @TableField("thb_csectionname")
        private String thbCsectionname;

        /**
         * 出張番号
         */
    @TableField("thb_cbiztripid")
        private String thbCbiztripid;

        /**
         * 出張開始日(チェック用)
         */
    @TableField("thb_cbiztripstart_check")
        private String thbCbiztripstartCheck;

        /**
         * 出張終了日(チェック用)
         */
    @TableField("thb_cbiztripend_check")
        private String thbCbiztripendCheck;

        /**
         * 削除フラグ(チェック用)
         */
    @TableField("thb_cdelete_flg_check")
        private String thbCdeleteFlgCheck;

        /**
         * 出張開始日
         */
    @TableField("thb_dbiztripstart")
        private Date thbDbiztripstart;

        /**
         * 出張終了日
         */
    @TableField("thb_dbiztripend")
        private Date thbDbiztripend;

        /**
         * 取込番号
         */
                @TableId(value = "thb_cimportid", type = IdType.AUTO)
                private String thbCimportid;

        /**
         * 取込行番号
         */
    @TableField("thb_nrowno")
        private Long thbNrowno;

        /**
         * 取込日
         */
    @TableField("thb_dimport")
        private Date thbDimport;

        /**
         * 取消日
         */
    @TableField("thb_dcancel")
        private Date thbDcancel;

        /**
         * 出張ステータス
         */
    @TableField("thb_cbiztripstatus")
        private String thbCbiztripstatus;

        /**
         * メッセージ
         */
    @TableField("thb_cmessage")
        private String thbCmessage;


        }