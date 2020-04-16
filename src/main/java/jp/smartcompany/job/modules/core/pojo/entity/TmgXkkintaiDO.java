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
 * [勤怠]月次集計出力イメージ                xkkintaiに転送するためのテーブル。顧客コード、データ
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
@TableName("tmg_xkkintai")
public class TmgXkkintaiDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("txk_ccustomerid")
        private String txkCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
                @TableId(value = "txk_ccompanyid", type = IdType.AUTO)
                private String txkCcompanyid;

        /**
         * 社員番号
         */
    @TableField("txk_cemployeeid")
        private String txkCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("txk_dstartdate")
        private Date txkDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("txk_denddate")
        private Date txkDenddate;

        /**
         * 更新者
         */
    @TableField("txk_cmodifieruserid")
        private String txkCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("txk_dmodifieddate")
        private Date txkDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("txk_cmodifierprogramid")
        private String txkCmodifierprogramid;

        /**
         * 該当年月
         */
    @TableField("txk_dgaitong")
        private Date txkDgaitong;

        /**
         * 所属区分
         */
    @TableField("txk_cshozokukb")
        private String txkCshozokukb;

        /**
         * 所属名称
         */
    @TableField("txk_cshozokunm")
        private String txkCshozokunm;

        /**
         * 勤怠種別区分
         */
    @TableField("txk_cworktypeid")
        private String txkCworktypeid;

        /**
         * 勤怠種別名称
         */
    @TableField("txk_cworktypename")
        private String txkCworktypename;

        /**
         * 数値01
         */
    @TableField("txk_nnumber01")
        private Long txkNnumber01;

        /**
         * 数値02
         */
    @TableField("txk_nnumber02")
        private Long txkNnumber02;

        /**
         * 数値03
         */
    @TableField("txk_nnumber03")
        private Long txkNnumber03;

        /**
         * 数値04
         */
    @TableField("txk_nnumber04")
        private Long txkNnumber04;

        /**
         * 数値05
         */
    @TableField("txk_nnumber05")
        private Long txkNnumber05;

        /**
         * 数値06
         */
    @TableField("txk_nnumber06")
        private Long txkNnumber06;

        /**
         * 数値07
         */
    @TableField("txk_nnumber07")
        private Long txkNnumber07;

        /**
         * 数値08
         */
    @TableField("txk_nnumber08")
        private Long txkNnumber08;

        /**
         * 数値09
         */
    @TableField("txk_nnumber09")
        private Long txkNnumber09;

        /**
         * 数値10
         */
    @TableField("txk_nnumber10")
        private Long txkNnumber10;

        /**
         * 数値11
         */
    @TableField("txk_nnumber11")
        private Long txkNnumber11;

        /**
         * 数値12
         */
    @TableField("txk_nnumber12")
        private Long txkNnumber12;

        /**
         * 数値13
         */
    @TableField("txk_nnumber13")
        private Long txkNnumber13;

        /**
         * 数値14
         */
    @TableField("txk_nnumber14")
        private Long txkNnumber14;

        /**
         * 数値15
         */
    @TableField("txk_nnumber15")
        private Long txkNnumber15;

        /**
         * 数値16
         */
    @TableField("txk_nnumber16")
        private Long txkNnumber16;

        /**
         * 数値17
         */
    @TableField("txk_nnumber17")
        private Long txkNnumber17;

        /**
         * 数値18
         */
    @TableField("txk_nnumber18")
        private Long txkNnumber18;

        /**
         * 数値19
         */
    @TableField("txk_nnumber19")
        private Long txkNnumber19;

        /**
         * 数値20
         */
    @TableField("txk_nnumber20")
        private Long txkNnumber20;

        /**
         * 数値21
         */
    @TableField("txk_nnumber21")
        private Long txkNnumber21;

        /**
         * 数値22
         */
    @TableField("txk_nnumber22")
        private Long txkNnumber22;

        /**
         * 数値23
         */
    @TableField("txk_nnumber23")
        private Long txkNnumber23;

        /**
         * 数値24
         */
    @TableField("txk_nnumber24")
        private Long txkNnumber24;

        /**
         * 数値25
         */
    @TableField("txk_nnumber25")
        private Long txkNnumber25;

        /**
         * 数値26
         */
    @TableField("txk_nnumber26")
        private Long txkNnumber26;

        /**
         * 数値27
         */
    @TableField("txk_nnumber27")
        private Long txkNnumber27;

        /**
         * 数値28
         */
    @TableField("txk_nnumber28")
        private Long txkNnumber28;

        /**
         * 数値29
         */
    @TableField("txk_nnumber29")
        private Long txkNnumber29;

        /**
         * 数値30
         */
    @TableField("txk_nnumber30")
        private Long txkNnumber30;

        /**
         * 数値31
         */
    @TableField("txk_nnumber31")
        private Long txkNnumber31;

        /**
         * 数値32
         */
    @TableField("txk_nnumber32")
        private Long txkNnumber32;

        /**
         * 数値33
         */
    @TableField("txk_nnumber33")
        private Long txkNnumber33;

        /**
         * 数値34
         */
    @TableField("txk_nnumber34")
        private Long txkNnumber34;

        /**
         * 数値35
         */
    @TableField("txk_nnumber35")
        private Long txkNnumber35;

        /**
         * 数値36
         */
    @TableField("txk_nnumber36")
        private Long txkNnumber36;

        /**
         * 数値37
         */
    @TableField("txk_nnumber37")
        private Long txkNnumber37;

        /**
         * 数値38
         */
    @TableField("txk_nnumber38")
        private Long txkNnumber38;

        /**
         * 数値39
         */
    @TableField("txk_nnumber39")
        private Long txkNnumber39;

        /**
         * 数値40
         */
    @TableField("txk_nnumber40")
        private Long txkNnumber40;

        /**
         * 数値41
         */
    @TableField("txk_nnumber41")
        private Long txkNnumber41;

        /**
         * 数値42
         */
    @TableField("txk_nnumber42")
        private Long txkNnumber42;

        /**
         * 数値43
         */
    @TableField("txk_nnumber43")
        private Long txkNnumber43;

        /**
         * 数値44
         */
    @TableField("txk_nnumber44")
        private Long txkNnumber44;

        /**
         * 数値45
         */
    @TableField("txk_nnumber45")
        private Long txkNnumber45;

        /**
         * 数値46
         */
    @TableField("txk_nnumber46")
        private Long txkNnumber46;

        /**
         * 数値47
         */
    @TableField("txk_nnumber47")
        private Long txkNnumber47;

        /**
         * 数値48
         */
    @TableField("txk_nnumber48")
        private Long txkNnumber48;

        /**
         * 数値49
         */
    @TableField("txk_nnumber49")
        private Long txkNnumber49;

        /**
         * 数値50
         */
    @TableField("txk_nnumber50")
        private Long txkNnumber50;

        /**
         * 数値51
         */
    @TableField("txk_nnumber51")
        private Long txkNnumber51;

        /**
         * 数値52
         */
    @TableField("txk_nnumber52")
        private Long txkNnumber52;

        /**
         * 数値53
         */
    @TableField("txk_nnumber53")
        private Long txkNnumber53;

        /**
         * 数値54
         */
    @TableField("txk_nnumber54")
        private Long txkNnumber54;

        /**
         * 数値55
         */
    @TableField("txk_nnumber55")
        private Long txkNnumber55;

        /**
         * 数値56
         */
    @TableField("txk_nnumber56")
        private Long txkNnumber56;

        /**
         * 数値57
         */
    @TableField("txk_nnumber57")
        private Long txkNnumber57;

        /**
         * 数値58
         */
    @TableField("txk_nnumber58")
        private Long txkNnumber58;

        /**
         * 数値59
         */
    @TableField("txk_nnumber59")
        private Long txkNnumber59;

        /**
         * 数値60
         */
    @TableField("txk_nnumber60")
        private Long txkNnumber60;

        /**
         * 数値61
         */
    @TableField("txk_nnumber61")
        private Long txkNnumber61;

        /**
         * 数値62
         */
    @TableField("txk_nnumber62")
        private Long txkNnumber62;

        /**
         * 数値63
         */
    @TableField("txk_nnumber63")
        private Long txkNnumber63;

        /**
         * 数値64
         */
    @TableField("txk_nnumber64")
        private Long txkNnumber64;

        /**
         * 数値65
         */
    @TableField("txk_nnumber65")
        private Long txkNnumber65;

        /**
         * 数値66
         */
    @TableField("txk_nnumber66")
        private Long txkNnumber66;

        /**
         * 数値67
         */
    @TableField("txk_nnumber67")
        private Long txkNnumber67;

        /**
         * 数値68
         */
    @TableField("txk_nnumber68")
        private Long txkNnumber68;

        /**
         * 数値69
         */
    @TableField("txk_nnumber69")
        private Long txkNnumber69;

        /**
         * 数値70
         */
    @TableField("txk_nnumber70")
        private Long txkNnumber70;

        /**
         * 数値71
         */
    @TableField("txk_nnumber71")
        private Long txkNnumber71;

        /**
         * 数値72
         */
    @TableField("txk_nnumber72")
        private Long txkNnumber72;

        /**
         * 数値73
         */
    @TableField("txk_nnumber73")
        private Long txkNnumber73;

        /**
         * 数値74
         */
    @TableField("txk_nnumber74")
        private Long txkNnumber74;

        /**
         * 数値75
         */
    @TableField("txk_nnumber75")
        private Long txkNnumber75;

        /**
         * 数値76
         */
    @TableField("txk_nnumber76")
        private Long txkNnumber76;

        /**
         * 数値77
         */
    @TableField("txk_nnumber77")
        private Long txkNnumber77;

        /**
         * 数値78
         */
    @TableField("txk_nnumber78")
        private Long txkNnumber78;

        /**
         * 数値79
         */
    @TableField("txk_nnumber79")
        private Long txkNnumber79;

        /**
         * 数値80
         */
    @TableField("txk_nnumber80")
        private Long txkNnumber80;

        /**
         * 数値81
         */
    @TableField("txk_nnumber81")
        private Long txkNnumber81;

        /**
         * 数値82
         */
    @TableField("txk_nnumber82")
        private Long txkNnumber82;

        /**
         * 数値83
         */
    @TableField("txk_nnumber83")
        private Long txkNnumber83;

        /**
         * 数値84
         */
    @TableField("txk_nnumber84")
        private Long txkNnumber84;

        /**
         * 数値85
         */
    @TableField("txk_nnumber85")
        private Long txkNnumber85;

        /**
         * 数値86
         */
    @TableField("txk_nnumber86")
        private Long txkNnumber86;

        /**
         * 数値87
         */
    @TableField("txk_nnumber87")
        private Long txkNnumber87;

        /**
         * 数値88
         */
    @TableField("txk_nnumber88")
        private Long txkNnumber88;

        /**
         * 数値89
         */
    @TableField("txk_nnumber89")
        private Long txkNnumber89;

        /**
         * 数値90
         */
    @TableField("txk_nnumber90")
        private Long txkNnumber90;

        /**
         * 数値91
         */
    @TableField("txk_nnumber91")
        private Long txkNnumber91;

        /**
         * 数値92
         */
    @TableField("txk_nnumber92")
        private Long txkNnumber92;

        /**
         * 数値93
         */
    @TableField("txk_nnumber93")
        private Long txkNnumber93;

        /**
         * 数値94
         */
    @TableField("txk_nnumber94")
        private Long txkNnumber94;

        /**
         * 数値95
         */
    @TableField("txk_nnumber95")
        private Long txkNnumber95;

        /**
         * 数値96
         */
    @TableField("txk_nnumber96")
        private Long txkNnumber96;

        /**
         * 数値97
         */
    @TableField("txk_nnumber97")
        private Long txkNnumber97;

        /**
         * 数値98
         */
    @TableField("txk_nnumber98")
        private Long txkNnumber98;

        /**
         * 数値99
         */
    @TableField("txk_nnumber99")
        private Long txkNnumber99;

        /**
         * 数値100
         */
    @TableField("txk_nnumber100")
        private Long txkNnumber100;

        /**
         * 予備文字列１
         */
    @TableField("txk_csparechar1")
        private String txkCsparechar1;

        /**
         * 予備文字列２
         */
    @TableField("txk_csparechar2")
        private String txkCsparechar2;

        /**
         * 予備文字列３
         */
    @TableField("txk_csparechar3")
        private String txkCsparechar3;

        /**
         * 予備文字列４
         */
    @TableField("txk_csparechar4")
        private String txkCsparechar4;

        /**
         * 予備文字列５
         */
    @TableField("txk_csparechar5")
        private String txkCsparechar5;

        /**
         * 予備数値１
         */
    @TableField("txk_nsparenum1")
        private Long txkNsparenum1;

        /**
         * 予備数値２
         */
    @TableField("txk_nsparenum2")
        private Long txkNsparenum2;

        /**
         * 予備数値３
         */
    @TableField("txk_nsparenum3")
        private Long txkNsparenum3;

        /**
         * 予備数値４
         */
    @TableField("txk_nsparenum4")
        private Long txkNsparenum4;

        /**
         * 予備数値５
         */
    @TableField("txk_nsparenum5")
        private Long txkNsparenum5;

        /**
         * 予備日付１
         */
    @TableField("txk_dsparedate1")
        private Date txkDsparedate1;

        /**
         * 予備日付２
         */
    @TableField("txk_dsparedate2")
        private Date txkDsparedate2;

        /**
         * 予備日付３
         */
    @TableField("txk_dsparedate3")
        private Date txkDsparedate3;

        /**
         * 予備日付４
         */
    @TableField("txk_dsparedate4")
        private Date txkDsparedate4;

        /**
         * 予備日付５
         */
    @TableField("txk_dsparedate5")
        private Date txkDsparedate5;


        }