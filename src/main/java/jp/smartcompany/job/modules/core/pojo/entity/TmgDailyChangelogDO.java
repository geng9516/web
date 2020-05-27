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
 * [勤怠]日別情報変更ログ
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
@TableName("tmg_daily_changelog")
public class TmgDailyChangelogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 更新id
         */
    @TableId(value="tdcl_nid",type = IdType.INPUT)
        private Long tdclNid;

        /**
         * 前後区分( 0：前 1：後)
         */
    @TableField("tdcl_nifbeforeorafter")
        private Long tdclNifbeforeorafter;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tdcl_ccustomerid")
        private String tdclCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tdcl_ccompanyid")
        private String tdclCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tdcl_cemployeeid")
        private String tdclCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tdcl_dstartdate")
        private Date tdclDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tdcl_denddate")
        private Date tdclDenddate;

        /**
         * 更新者
         */
    @TableField("tdcl_cmodifieruserid")
        private String tdclCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tdcl_dmodifieddate")
        private Date tdclDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tdcl_cmodifierprogramid")
        private String tdclCmodifierprogramid;

        /**
         * 勤務年月日                         yyyy/mm/dd
         */
    @TableField("tdcl_dyyyymmdd")
        private Date tdclDyyyymmdd;

        /**
         * ステータスフラグ                      mgd:tmg_datastatus
         */
    @TableField("tdcl_cstatusflg")
        private String tdclCstatusflg;

        /**
         * [打刻]始業時刻                      300～1740(朝5:00～翌朝5:00)
         */
    @TableField("tdcl_nopen_tp")
        private Long tdclNopenTp;

        /**
         * [打刻]終業時刻
         */
    @TableField("tdcl_nclose_tp")
        private Long tdclNcloseTp;

        /**
         * [予定]就業区分                      mgd:tmg_work
         */
    @TableField("tdcl_cworkingid_p")
        private String tdclCworkingidP;

        /**
         * [予定]始業時刻
         */
    @TableField("tdcl_nopen_p")
        private Long tdclNopenP;

        /**
         * [予定]終業時刻
         */
    @TableField("tdcl_nclose_p")
        private Long tdclNcloseP;

        /**
         * [申請反映]始業時刻
         */
    @TableField("tdcl_nopen_n")
        private Long tdclNopenN;

        /**
         * [申請反映]終業時刻
         */
    @TableField("tdcl_nclose_n")
        private Long tdclNcloseN;

        /**
         * [実績]就業区分                      mgd:tmg_work
         */
    @TableField("tdcl_cworkingid_r")
        private String tdclCworkingidR;

        /**
         * [実績]始業時刻
         */
    @TableField("tdcl_nopen_r")
        private Long tdclNopenR;

        /**
         * [実績]終業時刻
         */
    @TableField("tdcl_nclose_r")
        private Long tdclNcloseR;

        /**
         * [実績]本人コメント
         */
    @TableField("tdcl_cowncomment_r")
        private String tdclCowncommentR;

        /**
         * [実績]承認者コメント
         */
    @TableField("tdcl_cbosscomment_r")
        private String tdclCbosscommentR;

        /**
         * [予定]出張区分
         */
    @TableField("tdcl_cbusinesstripid_p")
        private String tdclCbusinesstripidP;

        /**
         * [実績]出張区分
         */
    @TableField("tdcl_cbusinesstripid_r")
        private String tdclCbusinesstripidR;

        /**
         * [予定]コメント
         */
    @TableField("tdcl_ccomment_p")
        private String tdclCcommentP;

        /**
         * 勤務パターンid
         */
    @TableField("tdcl_cpatternid")
        private String tdclCpatternid;


        }