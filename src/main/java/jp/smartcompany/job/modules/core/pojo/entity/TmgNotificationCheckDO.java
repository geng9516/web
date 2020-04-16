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
 * [勤怠]エラーチェック用・申請情報
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
@TableName("tmg_notification_check")
public class TmgNotificationCheckDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tntf_ccustomerid")
        private String tntfCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tntf_ccompanyid")
        private String tntfCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tntf_cemployeeid")
        private String tntfCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tntf_dstartdate")
        private Date tntfDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tntf_denddate")
        private Date tntfDenddate;

        /**
         * 更新者
         */
    @TableField("tntf_cmodifieruserid")
        private String tntfCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tntf_dmodifieddate")
        private Date tntfDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tntf_cmodifierprogramid")
        private String tntfCmodifierprogramid;

        /**
         * 連番
         */
    @TableField("tntf_nseq")
        private Long tntfNseq;

        /**
         * 申請番号                          申請者社員番号|連番
         */
    @TableField("tntf_cntfno")
        private String tntfCntfno;

        /**
         * ステータスフラグ               mgd:tmg_ntfstatus
         */
    @TableField("tntf_cstatusflg")
        private String tntfCstatusflg;

        /**
         * 申請者社員番号
         */
    @TableField("tntf_calteremployeeid")
        private String tntfCalteremployeeid;

        /**
         * 申請日
         */
    @TableField("tntf_dnotification")
        private Date tntfDnotification;

        /**
         * 対象期間：開始日
         */
    @TableField("tntf_dbegin")
        private Date tntfDbegin;

        /**
         * 対象期間：終了日
         */
    @TableField("tntf_dend")
        private Date tntfDend;

        /**
         * 対象期間：解除日
         */
    @TableField("tntf_dcancel")
        private Date tntfDcancel;

        /**
         * 対象時間：始業時の非勤務時間                時間の直接指定の場合
         */
    @TableField("tntf_ntime_open")
        private Long tntfNtimeOpen;

        /**
         * 対象時間：終業時の非勤務時間                時間の直接指定の場合
         */
    @TableField("tntf_ntime_close")
        private Long tntfNtimeClose;

        /**
         * 対象時間：開始時刻                     時間帯指定の場合
         */
    @TableField("tntf_ntimezone_open")
        private Long tntfNtimezoneOpen;

        /**
         * 対象時間：終了時刻                     時間帯指定の場合
         */
    @TableField("tntf_ntimezone_close")
        private Long tntfNtimezoneClose;

        /**
         * 対象曜日：指定なし
         */
    @TableField("tntf_nnoreserved")
        private Long tntfNnoreserved;

        /**
         * 対象曜日：月曜
         */
    @TableField("tntf_nmon")
        private Long tntfNmon;

        /**
         * 対象曜日：火曜
         */
    @TableField("tntf_ntue")
        private Long tntfNtue;

        /**
         * 対象曜日：水曜
         */
    @TableField("tntf_nwed")
        private Long tntfNwed;

        /**
         * 対象曜日：木曜
         */
    @TableField("tntf_nthu")
        private Long tntfNthu;

        /**
         * 対象曜日：金曜
         */
    @TableField("tntf_nfri")
        private Long tntfNfri;

        /**
         * 対象曜日：土曜
         */
    @TableField("tntf_nsat")
        private Long tntfNsat;

        /**
         * 対象曜日：日曜
         */
    @TableField("tntf_nsun")
        private Long tntfNsun;

        /**
         * 対象曜日指定                        ビットフラグ
         */
    @TableField("tntf_ndayofweek")
        private Long tntfNdayofweek;

        /**
         * 申請種類                                      mgd:tmg_ntftype
         */
    @TableField("tntf_ctype")
        private String tntfCtype;

        /**
         * 申請事由
         */
    @TableField("tntf_cowncomment")
        private String tntfCowncomment;

        /**
         * 承認者社員番号
         */
    @TableField("tntf_cboss")
        private String tntfCboss;

        /**
         * 承認者コメント
         */
    @TableField("tntf_cbosscomment")
        private String tntfCbosscomment;

        /**
         * 承認日
         */
    @TableField("tntf_dboss")
        private Date tntfDboss;

        /**
         * 申請解除社員番号
         */
    @TableField("tntf_ccancel")
        private String tntfCcancel;

        /**
         * 申請解除者のコメント
         */
    @TableField("tntf_ccancelcomment")
        private String tntfCcancelcomment;

        /**
         * 代休日
         */
    @TableField("tntf_ddaikyu")
        private Date tntfDdaikyu;

        /**
         * 特別休暇：傷病種類
         */
    @TableField("tntf_csick_type")
        private String tntfCsickType;

        /**
         * 特別休暇：傷病名
         */
    @TableField("tntf_csick_name")
        private String tntfCsickName;

        /**
         * 特別休暇：同一傷病区分
         */
    @TableField("tntf_csame_sick_type")
        private String tntfCsameSickType;

        /**
         * 特別休暇：災害申請区分
         */
    @TableField("tntf_cdisaster")
        private String tntfCdisaster;

        /**
         * 特別休暇：起算日
         */
    @TableField("tntf_dperiod_date")
        private Date tntfDperiodDate;

        /**
         * 特別休暇：上限加算
         */
    @TableField("tntf_nuapper_addition")
        private Long tntfNuapperAddition;

        /**
         * imワークフロー用申請番号
         */
    @TableField("tntf_cntfno_im")
        private String tntfCntfnoIm;

        /**
         * 休憩開始時刻
         */
    @TableField("tntf_nrestopen")
        private Long tntfNrestopen;

        /**
         * 休憩終了時刻
         */
    @TableField("tntf_nrestclose")
        private Long tntfNrestclose;

        /**
         * 氏名(家族、子等の)
         */
    @TableField("tntf_ckanjiname")
        private String tntfCkanjiname;

        /**
         * 続柄
         */
    @TableField("tntf_crelation")
        private String tntfCrelation;

        /**
         * 生年月日
         */
    @TableField("tntf_ddateofbirth")
        private Date tntfDdateofbirth;

        /**
         * 対象の人数
         */
    @TableField("tntf_nnumber_of_target")
        private Long tntfNnumberOfTarget;

        /**
         * 分割前申請番号
         */
    @TableField("tntf_cntfno_moto")
        private String tntfCntfnoMoto;

        /**
         * 解除：終了日
         */
    @TableField("tntf_dcancelend")
        private Date tntfDcancelend;

        /**
         * tntf_capproval_level
         */
    @TableField("tntf_capproval_level")
        private String tntfCapprovalLevel;

        /**
         * tntf_csiteid
         */
    @TableField("tntf_csiteid")
        private String tntfCsiteid;

        /**
         * tntf_cntfaction
         */
    @TableField("tntf_cntfaction")
        private String tntfCntfaction;


        }