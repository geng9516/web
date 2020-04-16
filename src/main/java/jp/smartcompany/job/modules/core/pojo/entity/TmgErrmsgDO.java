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
 * [勤怠]エラーメッセージ格納テーブル
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
@TableName("tmg_errmsg")
public class TmgErrmsgDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("ter_ccustomerid")
        private String terCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("ter_ccompanyid")
        private String terCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("ter_dstartdate")
        private Date terDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("ter_denddate")
        private Date terDenddate;

        /**
         * 更新者
         */
    @TableField("ter_cmodifieruserid")
        private String terCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("ter_dmodifieddate")
        private Date terDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("ter_cmodifierprogramid")
        private String terCmodifierprogramid;

        /**
         * エラーコード
         */
    @TableField("ter_cerrcode")
        private String terCerrcode;

        /**
         * 言語
         */
    @TableField("ter_clanguage")
        private String terClanguage;


        }