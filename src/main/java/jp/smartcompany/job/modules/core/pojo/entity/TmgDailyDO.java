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
 * [勤怠]日別情報
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
@TableName("tmg_daily")
public class TmgDailyDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ固定：01
         */
    @TableField("tda_ccustomerid")
        private String tdaCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tda_ccompanyid")
        private String tdaCcompanyid;

        /**
         * 職員番号
         */
                @TableId(value = "tda_cemployeeid", type = IdType.AUTO)
                private String tdaCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日 固定：1900/01/01
         */
    @TableField("tda_dstartdate")
        private Date tdaDstartdate;

        /**
         * ﾃﾞｰﾀ終了日 固定：2222/12/31
         */
    @TableField("tda_denddate")
        private Date tdaDenddate;

        /**
         * 更新者
         */
    @TableField("tda_cmodifieruserid")
        private String tdaCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tda_dmodifieddate")
        private Date tdaDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tda_cmodifierprogramid")
        private String tdaCmodifierprogramid;

        /**
         * 該当年 yyyy
         */
    @TableField("tda_nyyyy")
        private Long tdaNyyyy;

        /**
         * 該当年月yyyy/mm/01
         */
    @TableField("tda_dyyyymm")
        private Date tdaDyyyymm;

        /**
         * 勤務年月日 yyyy/mm/dd
         */
    @TableField("tda_dyyyymmdd")
        private Date tdaDyyyymmdd;

        /**
         * ステータスフラグmgd:tmg_datastatus
         */
    @TableField("tda_cstatusflg")
        private String tdaCstatusflg;

        /**
         * 申請ステータスフラグmgd:tmg_ntfstatus
         */
    @TableField("tda_cntfstatusflg")
        private String tdaCntfstatusflg;

        /**
         * エラーコードmgd:tmg_errcode
         */
    @TableField("tda_cerrcode")
        private String tdaCerrcode;

        /**
         * エラーメッセージ
         */
    @TableField("tda_cerrmsg")
        private String tdaCerrmsg;

        /**
         * [打刻]始業時刻300～1740(朝5:00～翌朝5:00)
         */
    @TableField("tda_nopen_tp")
        private Long tdaNopenTp;

        /**
         * [打刻]終業時刻
         */
    @TableField("tda_nclose_tp")
        private Long tdaNcloseTp;

        /**
         * 休日フラグ mgd:tmg_holflg
         */
    @TableField("tda_cholflg")
        private String tdaCholflg;

        /**
         * [予定]就業区分mgd:tmg_work
         */
    @TableField("tda_cworkingid_p")
        private String tdaCworkingidP;

        /**
         * [予定]就業名称
         */
    @TableField("tda_cworkingname_p")
        private String tdaCworkingnameP;

        /**
         * [予定]始業時刻
         */
    @TableField("tda_nopen_p")
        private Long tdaNopenP;

        /**
         * [予定]終業時刻
         */
    @TableField("tda_nclose_p")
        private Long tdaNcloseP;

        /**
         * [予定]休憩開始時刻
         */
    @TableField("tda_nrestopen_p")
        private Long tdaNrestopenP;

        /**
         * [予定]休憩終了時刻
         */
    @TableField("tda_nrestclose_p")
        private Long tdaNrestcloseP;

        /**
         * [予定]更新者
         */
    @TableField("tda_cmodifieruserid_p")
        private String tdaCmodifieruseridP;

        /**
         * [予定]更新日時
         */
    @TableField("tda_dmodifieddate_p")
        private Date tdaDmodifieddateP;

        /**
         * [予定]予定作成ロック 連続した終日休暇の間の休日は予定作成で編集不可にする
         */
    @TableField("tda_nlock_p")
        private Long tdaNlockP;

        /**
         * [予定]休憩45分選択 休憩45分選択を反映するかどうかの判定に使用。
         */
    @TableField("tda_nrest45_p")
        private Long tdaNrest45P;

        /**
         * [申請反映]始業時刻
         */
    @TableField("tda_nopen_n")
        private Long tdaNopenN;

        /**
         * [申請反映]終業時刻
         */
    @TableField("tda_nclose_n")
        private Long tdaNcloseN;

        /**
         * [申請反映]休憩開始時刻
         */
    @TableField("tda_nrestopen_n")
        private Long tdaNrestopenN;

        /**
         * [申請反映]休憩終了時刻
         */
    @TableField("tda_nrestclose_n")
        private Long tdaNrestcloseN;

        /**
         * [申請反映]更新者
         */
    @TableField("tda_cmodifieruserid_n")
        private String tdaCmodifieruseridN;

        /**
         * [申請反映]更新日時
         */
    @TableField("tda_dmodifieddate_n")
        private Date tdaDmodifieddateN;

        /**
         * [超過勤務]開始時刻
         */
    @TableField("tda_nopen_o")
        private Long tdaNopenO;

        /**
         * [超過勤務]終了時刻
         */
    @TableField("tda_nclose_o")
        private Long tdaNcloseO;

        /**
         * [超過勤務]指示コメント
         */
    @TableField("tda_ccomment_o")
        private String tdaCcommentO;

        /**
         * [超過勤務]更新者
         */
    @TableField("tda_cmodifieruserid_o")
        private String tdaCmodifieruseridO;

        /**
         * [超過勤務]更新日時
         */
    @TableField("tda_dmodifieddate_o")
        private Date tdaDmodifieddateO;

        /**
         * [実績]就業区分mgd:tmg_work
         */
    @TableField("tda_cworkingid_r")
        private String tdaCworkingidR;

        /**
         * [実績]就業名称
         */
    @TableField("tda_cworkingname_r")
        private String tdaCworkingnameR;

        /**
         * [実績]始業時刻
         */
    @TableField("tda_nopen_r")
        private Long tdaNopenR;

        /**
         * [実績]終業時刻
         */
    @TableField("tda_nclose_r")
        private Long tdaNcloseR;

        /**
         * [実績]休憩開始時刻
         */
    @TableField("tda_nrestopen_r")
        private Long tdaNrestopenR;

        /**
         * [実績]休憩終了時刻
         */
    @TableField("tda_nrestclose_r")
        private Long tdaNrestcloseR;

        /**
         * [実績]本人コメント
         */
    @TableField("tda_cowncomment_r")
        private String tdaCowncommentR;

        /**
         * [実績]承認者コメント
         */
    @TableField("tda_cbosscomment_r")
        private String tdaCbosscommentR;

        /**
         * [実績]更新者
         */
    @TableField("tda_cmodifieruserid_r")
        private String tdaCmodifieruseridR;

        /**
         * [実績]更新日時
         */
    @TableField("tda_dmodifieddate_r")
        private Date tdaDmodifieddateR;

        /**
         * [実績]部分休業メッセージ 勤怠登録画面の｢備考｣表示用。tmg_daily_detai
         */
    @TableField("tda_cmessage")
        private String tdaCmessage;

        /**
         * [計算]項目１超勤(125/100)
         */
    @TableField("tda_ncalc01")
        private Long tdaNcalc01;

        /**
         * [計算]項目２休日(135/100)
         */
    @TableField("tda_ncalc02")
        private Long tdaNcalc02;

        /**
         * [計算]項目３超勤(150/100)
         */
    @TableField("tda_ncalc03")
        private Long tdaNcalc03;

        /**
         * [計算]項目４休日(160/100)
         */
    @TableField("tda_ncalc04")
        private Long tdaNcalc04;

        /**
         * [計算]項目５夜勤(25/100)
         */
    @TableField("tda_ncalc05")
        private Long tdaNcalc05;

        /**
         * [計算]項目６休日(135/100)
         */
    @TableField("tda_ncalc06")
        private Long tdaNcalc06;

        /**
         * [計算]項目７勤務日数
         */
    @TableField("tda_ncalc07")
        private Long tdaNcalc07;

        /**
         * [計算]項目８勤務時間数
         */
    @TableField("tda_ncalc08")
        private Long tdaNcalc08;

        /**
         * [計算]項目９翌日：勤務時間数
         */
    @TableField("tda_ncalc09")
        private Long tdaNcalc09;

        /**
         * [計算]項目１０翌日：欠勤：時間
         */
    @TableField("tda_ncalc10")
        private Long tdaNcalc10;

        /**
         * [計算]項目１１欠勤：日
         */
    @TableField("tda_ncalc11")
        private Long tdaNcalc11;

        /**
         * [計算]項目１２欠勤：時間
         */
    @TableField("tda_ncalc12")
        private Long tdaNcalc12;

        /**
         * [計算]項目１３給与用：勤務日数
         */
    @TableField("tda_ncalc13")
        private Long tdaNcalc13;

        /**
         * [計算]項目１４給与用：勤務時間数
         */
    @TableField("tda_ncalc14")
        private Long tdaNcalc14;

        /**
         * [計算]項目１５遅刻回数
         */
    @TableField("tda_ncalc15")
        private Long tdaNcalc15;

        /**
         * [計算]項目１６早退回数
         */
    @TableField("tda_ncalc16")
        private Long tdaNcalc16;

        /**
         * [計算]項目１７休出日数
         */
    @TableField("tda_ncalc17")
        private Long tdaNcalc17;

        /**
         * [計算]項目１８翌日：超勤(150/100)
         */
    @TableField("tda_ncalc18")
        private Long tdaNcalc18;

        /**
         * [計算]項目１９翌日：休日(160/100)
         */
    @TableField("tda_ncalc19")
        private Long tdaNcalc19;

        /**
         * [計算]項目２０翌日：夜勤(25/100)
         */
    @TableField("tda_ncalc20")
        private Long tdaNcalc20;

        /**
         * [計算]項目２１翌日：給与用：勤務時間数
         */
    @TableField("tda_ncalc21")
        private Long tdaNcalc21;

        /**
         * [計算]項目２２超勤(100/100)
         */
    @TableField("tda_ncalc22")
        private Long tdaNcalc22;

        /**
         * [計算]項目２３翌日：超勤(100/100)
         */
    @TableField("tda_ncalc23")
        private Long tdaNcalc23;

        /**
         * [計算]項目２４翌日：超勤(125/100)
         */
    @TableField("tda_ncalc24")
        private Long tdaNcalc24;

        /**
         * [計算]項目２５翌日：休日(135/100)
         */
    @TableField("tda_ncalc25")
        private Long tdaNcalc25;

        /**
         * [計算]項目２６給与用：減額対象の時間数
         */
    @TableField("tda_ncalc26")
        private Long tdaNcalc26;

        /**
         * [計算]項目２７翌日：給与用：減額対象の時間数
         */
    @TableField("tda_ncalc27")
        private Long tdaNcalc27;

        /**
         * [計算]項目２８所定労働時間数
         */
    @TableField("tda_ncalc28")
        private Long tdaNcalc28;

        /**
         * [計算]項目２９超勤(175/100)
         */
    @TableField("tda_ncalc29")
        private Long tdaNcalc29;

        /**
         * [計算]項目３０翌日：超勤(175/100)
         */
    @TableField("tda_ncalc30")
        private Long tdaNcalc30;

        /**
         * [予定]出張区分
         */
    @TableField("tda_cbusinesstripid_p")
        private String tdaCbusinesstripidP;

        /**
         * [実績]出張区分
         */
    @TableField("tda_cbusinesstripid_r")
        private String tdaCbusinesstripidR;

        /**
         * [予定]コメント
         */
    @TableField("tda_ccomment_p")
        private String tdaCcommentP;

        /**
         * 勤務パターンid
         */
    @TableField("tda_cpatternid")
        private String tdaCpatternid;

        /**
         * [計算]項目３１休日(185/100)
         */
    @TableField("tda_ncalc31")
        private Long tdaNcalc31;

        /**
         * [計算]項目３２翌日：休日(185/100)
         */
    @TableField("tda_ncalc32")
        private Long tdaNcalc32;

        /**
         * [計算]項目３３超勤2(100/100)
         */
    @TableField("tda_ncalc33")
        private Long tdaNcalc33;

        /**
         * [計算]項目３４翌日：超勤2(100/100)
         */
    @TableField("tda_ncalc34")
        private Long tdaNcalc34;

        /**
         * [計算]項目３５超勤2(125/100)
         */
    @TableField("tda_ncalc35")
        private Long tdaNcalc35;

        /**
         * [計算]項目３６翌日：超勤2(125/100)
         */
    @TableField("tda_ncalc36")
        private Long tdaNcalc36;

        /**
         * [計算]項目３７超勤2(150/100)
         */
    @TableField("tda_ncalc37")
        private Long tdaNcalc37;

        /**
         * [計算]項目３８翌日：超勤2(150/100)
         */
    @TableField("tda_ncalc38")
        private Long tdaNcalc38;

        /**
         * [計算]項目３９超勤2(175/100)
         */
    @TableField("tda_ncalc39")
        private Long tdaNcalc39;

        /**
         * [計算]項目４０翌日：超勤2(175/100)
         */
    @TableField("tda_ncalc40")
        private Long tdaNcalc40;

        /**
         * [計算]項目４１休日2(135/100)
         */
    @TableField("tda_ncalc41")
        private Long tdaNcalc41;

        /**
         * [計算]項目４２翌日：休日2(135/100)
         */
    @TableField("tda_ncalc42")
        private Long tdaNcalc42;

        /**
         * [計算]項目４３休日2(160/100)
         */
    @TableField("tda_ncalc43")
        private Long tdaNcalc43;

        /**
         * [計算]項目４４翌日：休日2(160/100)
         */
    @TableField("tda_ncalc44")
        private Long tdaNcalc44;

        /**
         * [計算]項目４５休日2(185/100)
         */
    @TableField("tda_ncalc45")
        private Long tdaNcalc45;

        /**
         * [計算]項目４６翌日：休日2(185/100)
         */
    @TableField("tda_ncalc46")
        private Long tdaNcalc46;

        /**
         * [計算]項目４７超勤(125/100)：60hover
         */
    @TableField("tda_ncalc47")
        private Long tdaNcalc47;

        /**
         * [計算]項目４８翌日：超勤(125/100)：60hover
         */
    @TableField("tda_ncalc48")
        private Long tdaNcalc48;

        /**
         * [計算]項目４９超勤(150/100)：60hover
         */
    @TableField("tda_ncalc49")
        private Long tdaNcalc49;

        /**
         * [計算]項目５０翌日：超勤(150/100)：60hover
         */
    @TableField("tda_ncalc50")
        private Long tdaNcalc50;

        /**
         * [計算]項目５１休日(135/100)：60hover
         */
    @TableField("tda_ncalc51")
        private Long tdaNcalc51;

        /**
         * [計算]項目５２翌日：休日(135/100)：60hover
         */
    @TableField("tda_ncalc52")
        private Long tdaNcalc52;

        /**
         * [計算]項目５３休日(160/100)：60hover
         */
    @TableField("tda_ncalc53")
        private Long tdaNcalc53;

        /**
         * [計算]項目５４翌日：休日(160/100)：60hover
         */
    @TableField("tda_ncalc54")
        private Long tdaNcalc54;

        /**
         * [計算]項目５５超勤2(125/100)：60hover
         */
    @TableField("tda_ncalc55")
        private Long tdaNcalc55;

        /**
         * [計算]項目５６翌日：超勤2(125/100)：60hover
         */
    @TableField("tda_ncalc56")
        private Long tdaNcalc56;

        /**
         * [計算]項目５７超勤2(150/100)：60hover
         */
    @TableField("tda_ncalc57")
        private Long tdaNcalc57;

        /**
         * [計算]項目５８翌日：超勤2(150/100)：60hover
         */
    @TableField("tda_ncalc58")
        private Long tdaNcalc58;

        /**
         * [計算]項目５９休日2(135/100)：60hover
         */
    @TableField("tda_ncalc59")
        private Long tdaNcalc59;

        /**
         * [計算]項目６０翌日：休日2(135/100)：60hover
         */
    @TableField("tda_ncalc60")
        private Long tdaNcalc60;

        /**
         * [計算]項目６１休日2(160/100)：60hover
         */
    @TableField("tda_ncalc61")
        private Long tdaNcalc61;

        /**
         * [計算]項目６２翌日：休日2(160/100)：60hover
         */
    @TableField("tda_ncalc62")
        private Long tdaNcalc62;

        /**
         * [計算]項目６３給与用：超勤(100/100)
         */
    @TableField("tda_ncalc63")
        private Long tdaNcalc63;

        /**
         * [計算]項目６４翌日：給与用：超勤(100/100)
         */
    @TableField("tda_ncalc64")
        private Long tdaNcalc64;

        /**
         * [計算]項目６５給与用：超勤(125/100)
         */
    @TableField("tda_ncalc65")
        private Long tdaNcalc65;

        /**
         * [計算]項目６６翌日：給与用：超勤(125/100)
         */
    @TableField("tda_ncalc66")
        private Long tdaNcalc66;

        /**
         * [計算]項目６７給与用：超勤(150/100)
         */
    @TableField("tda_ncalc67")
        private Long tdaNcalc67;

        /**
         * [計算]項目６８翌日：給与用：超勤(150/100)
         */
    @TableField("tda_ncalc68")
        private Long tdaNcalc68;

        /**
         * [計算]項目６９給与用：超勤(175/100)
         */
    @TableField("tda_ncalc69")
        private Long tdaNcalc69;

        /**
         * [計算]項目７０翌日：給与用：超勤(175/100)
         */
    @TableField("tda_ncalc70")
        private Long tdaNcalc70;

        /**
         * [計算]項目７１給与用：休日(135/100)
         */
    @TableField("tda_ncalc71")
        private Long tdaNcalc71;

        /**
         * [計算]項目７２翌日：給与用：休日(135/100)
         */
    @TableField("tda_ncalc72")
        private Long tdaNcalc72;

        /**
         * [計算]項目７３給与用：休日(160/100)
         */
    @TableField("tda_ncalc73")
        private Long tdaNcalc73;

        /**
         * [計算]項目７４翌日：給与用：休日(160/100)
         */
    @TableField("tda_ncalc74")
        private Long tdaNcalc74;

        /**
         * [計算]項目７５給与用：休日(185/100)
         */
    @TableField("tda_ncalc75")
        private Long tdaNcalc75;

        /**
         * [計算]項目７６翌日：給与用：休日(185/100)
         */
    @TableField("tda_ncalc76")
        private Long tdaNcalc76;

        /**
         * [計算]項目７７給与用：超勤2(100/100)
         */
    @TableField("tda_ncalc77")
        private Long tdaNcalc77;

        /**
         * [計算]項目７８翌日：給与用：超勤2(100/100)
         */
    @TableField("tda_ncalc78")
        private Long tdaNcalc78;

        /**
         * [計算]項目７９給与用：超勤2(125/100)
         */
    @TableField("tda_ncalc79")
        private Long tdaNcalc79;

        /**
         * [計算]項目８０翌日：給与用：超勤2(125/100)
         */
    @TableField("tda_ncalc80")
        private Long tdaNcalc80;

        /**
         * [計算]項目８１給与用：超勤2(150/100)
         */
    @TableField("tda_ncalc81")
        private Long tdaNcalc81;

        /**
         * [計算]項目８２翌日：給与用：超勤2(150/100)
         */
    @TableField("tda_ncalc82")
        private Long tdaNcalc82;

        /**
         * [計算]項目８３給与用：超勤2(175/100)
         */
    @TableField("tda_ncalc83")
        private Long tdaNcalc83;

        /**
         * [計算]項目８４翌日：給与用：超勤2(175/100)
         */
    @TableField("tda_ncalc84")
        private Long tdaNcalc84;

        /**
         * [計算]項目８５給与用：休日2(135/100)
         */
    @TableField("tda_ncalc85")
        private Long tdaNcalc85;

        /**
         * [計算]項目８６翌日：給与用：休日2(135/100)
         */
    @TableField("tda_ncalc86")
        private Long tdaNcalc86;

        /**
         * [計算]項目８７給与用：休日2(160/100)
         */
    @TableField("tda_ncalc87")
        private Long tdaNcalc87;

        /**
         * [計算]項目８８翌日：給与用：休日2(160/100)
         */
    @TableField("tda_ncalc88")
        private Long tdaNcalc88;

        /**
         * [計算]項目８９給与用：休日2(185/100)
         */
    @TableField("tda_ncalc89")
        private Long tdaNcalc89;

        /**
         * [計算]項目９０翌日：給与用：休日2(185/100)
         */
    @TableField("tda_ncalc90")
        private Long tdaNcalc90;

        /**
         * [計算]項目９１年次有給休暇(半休)：回数
         */
    @TableField("tda_ncalc91")
        private Long tdaNcalc91;

        /**
         * [計算]項目９２年次有給休暇：時間
         */
    @TableField("tda_ncalc92")
        private Long tdaNcalc92;

        /**
         * [計算]項目９３特別休暇（有給）：時間
         */
    @TableField("tda_ncalc93")
        private Long tdaNcalc93;

        /**
         * [計算]項目９４翌日：特別休暇（有給）：時間
         */
    @TableField("tda_ncalc94")
        private Long tdaNcalc94;

        /**
         * [計算]項目９５特別休暇（無給）：時間
         */
    @TableField("tda_ncalc95")
        private Long tdaNcalc95;

        /**
         * [計算]項目９６翌日：特別休暇（無給）：時間
         */
    @TableField("tda_ncalc96")
        private Long tdaNcalc96;

        /**
         * [計算]項目９７就業禁止（有給）：時間
         */
    @TableField("tda_ncalc97")
        private Long tdaNcalc97;

        /**
         * [計算]項目９８翌日：就業禁止（有給）：時間
         */
    @TableField("tda_ncalc98")
        private Long tdaNcalc98;

        /**
         * [計算]項目９９就業禁止（無給）：時間
         */
    @TableField("tda_ncalc99")
        private Long tdaNcalc99;

        /**
         * [計算]項目１００翌日：就業禁止（無給）：時間
         */
    @TableField("tda_ncalc100")
        private Long tdaNcalc100;

        /**
         * [計算]項目１０１勤務しないことの承認：時間
         */
    @TableField("tda_ncalc101")
        private Long tdaNcalc101;

        /**
         * [計算]項目１０２翌日：勤務しないことの承認：時間
         */
    @TableField("tda_ncalc102")
        private Long tdaNcalc102;

        /**
         * [計算]項目１０３短時間勤務減額時間数（子の養育）：時間
         */
    @TableField("tda_ncalc103")
        private Long tdaNcalc103;

        /**
         * [計算]項目１０４翌日：短時間勤務減額時間数（子の養育）：時間
         */
    @TableField("tda_ncalc104")
        private Long tdaNcalc104;

        /**
         * [計算]項目１０５短時間勤務減額時間数（家族の介護）：時間
         */
    @TableField("tda_ncalc105")
        private Long tdaNcalc105;

        /**
         * [計算]項目１０６翌日：短時間勤務減額時間数（家族の介護）：時間
         */
    @TableField("tda_ncalc106")
        private Long tdaNcalc106;

        /**
         * [計算]項目１０７出張：時間
         */
    @TableField("tda_ncalc107")
        private Long tdaNcalc107;

        /**
         * [計算]項目１０８翌日：出張：時間
         */
    @TableField("tda_ncalc108")
        private Long tdaNcalc108;

        /**
         * [計算]項目１０９出張(移動日)：時間
         */
    @TableField("tda_ncalc109")
        private Long tdaNcalc109;

        /**
         * [計算]項目１１０翌日：出張(移動日)：時間
         */
    @TableField("tda_ncalc110")
        private Long tdaNcalc110;

        /**
         * [計算]項目１１１外出：時間
         */
    @TableField("tda_ncalc111")
        private Long tdaNcalc111;

        /**
         * [計算]項目１１２翌日：外出：時間
         */
    @TableField("tda_ncalc112")
        private Long tdaNcalc112;

        /**
         * [計算]項目１１３研修：時間
         */
    @TableField("tda_ncalc113")
        private Long tdaNcalc113;

        /**
         * [計算]項目１１４翌日：研修：時間
         */
    @TableField("tda_ncalc114")
        private Long tdaNcalc114;

        /**
         * [計算]項目１１５集計予備
         */
    @TableField("tda_ncalc115")
        private Long tdaNcalc115;

        /**
         * [計算]項目１１６集計予備
         */
    @TableField("tda_ncalc116")
        private Long tdaNcalc116;

        /**
         * [計算]項目１１７集計予備
         */
    @TableField("tda_ncalc117")
        private Long tdaNcalc117;

        /**
         * [計算]項目１１８集計予備
         */
    @TableField("tda_ncalc118")
        private Long tdaNcalc118;

        /**
         * [計算]項目１１９集計予備
         */
    @TableField("tda_ncalc119")
        private Long tdaNcalc119;

        /**
         * [計算]項目１２０集計予備
         */
    @TableField("tda_ncalc120")
        private Long tdaNcalc120;

        /**
         * [計算]項目１２１集計予備
         */
    @TableField("tda_ncalc121")
        private Long tdaNcalc121;

        /**
         * [計算]項目１２２集計予備
         */
    @TableField("tda_ncalc122")
        private Long tdaNcalc122;

        /**
         * [計算]項目１２３集計予備
         */
    @TableField("tda_ncalc123")
        private Long tdaNcalc123;

        /**
         * [計算]項目１２４集計予備
         */
    @TableField("tda_ncalc124")
        private Long tdaNcalc124;

        /**
         * [計算]項目１２５集計予備
         */
    @TableField("tda_ncalc125")
        private Long tdaNcalc125;

        /**
         * [計算]項目１２６集計予備
         */
    @TableField("tda_ncalc126")
        private Long tdaNcalc126;

        /**
         * [計算]項目１２７集計予備
         */
    @TableField("tda_ncalc127")
        private Long tdaNcalc127;

        /**
         * [計算]項目１２８集計予備
         */
    @TableField("tda_ncalc128")
        private Long tdaNcalc128;

        /**
         * [計算]項目１２９集計予備
         */
    @TableField("tda_ncalc129")
        private Long tdaNcalc129;

        /**
         * [計算]項目１３０集計予備
         */
    @TableField("tda_ncalc130")
        private Long tdaNcalc130;

        /**
         * [計算]項目１３１集計予備
         */
    @TableField("tda_ncalc131")
        private Long tdaNcalc131;

        /**
         * [計算]項目１３２集計予備
         */
    @TableField("tda_ncalc132")
        private Long tdaNcalc132;

        /**
         * [計算]項目１３３集計予備
         */
    @TableField("tda_ncalc133")
        private Long tdaNcalc133;

        /**
         * [計算]項目１３４集計予備
         */
    @TableField("tda_ncalc134")
        private Long tdaNcalc134;

        /**
         * [計算]項目１３５集計予備
         */
    @TableField("tda_ncalc135")
        private Long tdaNcalc135;

        /**
         * [計算]項目１３６集計予備
         */
    @TableField("tda_ncalc136")
        private Long tdaNcalc136;

        /**
         * [計算]項目１３７集計予備
         */
    @TableField("tda_ncalc137")
        private Long tdaNcalc137;

        /**
         * [計算]項目１３８集計予備
         */
    @TableField("tda_ncalc138")
        private Long tdaNcalc138;

        /**
         * [計算]項目１３９集計予備
         */
    @TableField("tda_ncalc139")
        private Long tdaNcalc139;

        /**
         * [計算]項目１４０集計予備
         */
    @TableField("tda_ncalc140")
        private Long tdaNcalc140;

        /**
         * [計算]項目１４１集計予備
         */
    @TableField("tda_ncalc141")
        private Long tdaNcalc141;

        /**
         * [計算]項目１４２集計予備
         */
    @TableField("tda_ncalc142")
        private Long tdaNcalc142;

        /**
         * [計算]項目１４３集計予備
         */
    @TableField("tda_ncalc143")
        private Long tdaNcalc143;

        /**
         * [計算]項目１４４集計予備
         */
    @TableField("tda_ncalc144")
        private Long tdaNcalc144;

        /**
         * [計算]項目１４５集計予備
         */
    @TableField("tda_ncalc145")
        private Long tdaNcalc145;

        /**
         * [計算]項目１４６集計予備
         */
    @TableField("tda_ncalc146")
        private Long tdaNcalc146;

        /**
         * [計算]項目１４７集計予備
         */
    @TableField("tda_ncalc147")
        private Long tdaNcalc147;

        /**
         * [計算]項目１４８集計予備
         */
    @TableField("tda_ncalc148")
        private Long tdaNcalc148;

        /**
         * [計算]項目１４９集計予備
         */
    @TableField("tda_ncalc149")
        private Long tdaNcalc149;

        /**
         * [計算]項目１５０集計予備
         */
    @TableField("tda_ncalc150")
        private Long tdaNcalc150;

        /**
         * 申請反映元申請番号
         */
    @TableField("tda_crefntfno")
        private String tdaCrefntfno;

        /**
         * 申請反映前の予定就業区分
         */
    @TableField("tda_cworkingid_p_pre")
        private String tdaCworkingidPPre;

        /**
         * 振替先（元）の日付
         */
    @TableField("tda_dsubstituted")
        private Date tdaDsubstituted;

        /**
         * [修正実績]始業時刻
         */
    @TableField("tda_nopen_f")
        private Long tdaNopenF;

        /**
         * [修正実績]終業時刻
         */
    @TableField("tda_nclose_f")
        private Long tdaNcloseF;


        }