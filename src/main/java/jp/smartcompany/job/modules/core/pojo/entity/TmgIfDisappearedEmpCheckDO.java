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
 * [勤怠]エラーチェック用インターフェース  連携元消失データ
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
@TableName("tmg_if_disappeared_emp_check")
public class TmgIfDisappearedEmpCheckDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "tidae_id", type = IdType.AUTO)
                private Long tidaeId;

        /**
         * 顧客コード
         */
    @TableField("tidae_ccustomerid")
        private String tidaeCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tidae_ccompanyid")
        private String tidaeCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tidae_cemployeeid")
        private String tidaeCemployeeid;

        /**
         * データ開始日
         */
    @TableField("tidae_dstartdate")
        private Date tidaeDstartdate;

        /**
         * データ終了日
         */
    @TableField("tidae_denddate")
        private Date tidaeDenddate;

        /**
         * 更新者
         */
    @TableField("tidae_cmodifieruserid")
        private String tidaeCmodifieruserid;

        /**
         * 更新日時
         */
    @TableField("tidae_dmodifieddate")
        private Date tidaeDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tidae_cmodifierprogramid")
        private String tidaeCmodifierprogramid;

        /**
         * 漢字指名
         */
    @TableField("tidae_ckanjiname")
        private String tidaeCkanjiname;

        /**
         * 所属コード
         */
    @TableField("tidae_csectionid")
        private String tidaeCsectionid;

        /**
         * 所属名称
         */
    @TableField("tidae_csectionname")
        private String tidaeCsectionname;

        /**
         * 処理区分 tmg_disappearedstatusflg（0:未処理、1:削除済み、9:無視）
         */
    @TableField("tidae_cdisappearedstatusflg")
        private String tidaeCdisappearedstatusflg;

        /**
         * 消失日
         */
    @TableField("tidae_ddisappeared")
        private Date tidaeDdisappeared;


        }