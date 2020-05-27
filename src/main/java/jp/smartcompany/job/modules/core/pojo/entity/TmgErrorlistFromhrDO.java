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
 * ｆｒｏｍ＿ｈｒエラーリストテーブル
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
@TableName("tmg_errorlist_fromhr")
public class TmgErrorlistFromhrDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableId(value="tefh_ccustomerid",type = IdType.INPUT)
        private String tefhCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tefh_ccompanyid")
        private String tefhCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tefh_cemployeeid")
        private String tefhCemployeeid;

        /**
         * 最終更新者
         */
    @TableField("tefh_cmodifieruserid")
        private String tefhCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("tefh_dmodifieddate")
        private Date tefhDmodifieddate;

        /**
         * 最終更新プログラムid
         */
    @TableField("tefh_cmodifierprogramid")
        private String tefhCmodifierprogramid;

        /**
         * エラーメッセージ
         */
    @TableField("tefh_cerr_msg")
        private String tefhCerrMsg;

        /**
         * 再実行フラグ ０：未実施 １：実施済
         */
    @TableField("tefh_nretry_flg")
        private Long tefhNretryFlg;


        }