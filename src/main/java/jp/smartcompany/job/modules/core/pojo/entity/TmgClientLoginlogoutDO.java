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
 * [勤怠]ログインログアウト時刻保持:
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
@TableName("tmg_client_loginlogout")
public class TmgClientLoginlogoutDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableField("tcll_ccustomerid")
        private String tcllCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tcll_ccompanyid")
        private String tcllCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "tcll_cemployeeid", type = IdType.AUTO)
                private String tcllCemployeeid;

        /**
         * データ開始日 固定：1900/01/01
         */
    @TableField("tcll_dstartdate")
        private Date tcllDstartdate;

        /**
         * データ終了日 固定：2222/12/31
         */
    @TableField("tcll_denddate")
        private Date tcllDenddate;

        /**
         * 更新者
         */
    @TableField("tcll_cmodifieruserid")
        private String tcllCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tcll_dmodifieddate")
        private Date tcllDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tcll_cmodifierprogramid")
        private String tcllCmodifierprogramid;

        /**
         * 年月日 yyyy/mm/dd
         */
    @TableField("tcll_dyyyymmdd")
        private Date tcllDyyyymmdd;

        /**
         * ログイン日時
         */
    @TableField("tcll_dlogin")
        private Date tcllDlogin;

        /**
         * ログイン時刻（数値型）例 1440
         */
    @TableField("tcll_nlogin")
        private Long tcllNlogin;

        /**
         * ログアウト日時
         */
    @TableField("tcll_dlogout")
        private Date tcllDlogout;

        /**
         * ログアウト時刻（数値型）例 1440
         */
    @TableField("tcll_nlogout")
        private Long tcllNlogout;


        }