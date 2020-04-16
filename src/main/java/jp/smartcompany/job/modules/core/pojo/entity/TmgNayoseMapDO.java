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
 * [就業]名寄せ情報
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
@TableName("tmg_nayose_map")
public class TmgNayoseMapDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableField("tnm_ccustomerid")
        private String tnmCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tnm_ccompanyid")
        private String tnmCcompanyid;

        /**
         * ユーザid(＝職員番号)
         */
    @TableField("tnm_cuserid")
        private String tnmCuserid;

        /**
         * アカウント(＝共通id)
         */
    @TableField("tnm_caccount")
        private String tnmCaccount;

        /**
         * 開始日
         */
    @TableField("tnm_dstart")
        private Date tnmDstart;

        /**
         * 終了日
         */
    @TableField("tnm_dend")
        private Date tnmDend;

        /**
         * デフォルト有効フラグ(初回ログイン時に自動選択されるユーザidかどうか)
         */
    @TableField("tnm_nisdefault")
        private Long tnmNisdefault;


        }