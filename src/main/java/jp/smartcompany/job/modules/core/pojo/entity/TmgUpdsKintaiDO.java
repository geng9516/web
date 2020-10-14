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
 * [勤怠]月次集計出力イメージ(upds連携用、過去データ退避
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
@TableName("tmg_upds_kintai")
public class TmgUpdsKintaiDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableId(value="tuk_ccustomerid",type = IdType.INPUT)
        private String tukCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tuk_ccompanyid")
        private String tukCcompanyid;

        /**
         * 職員番号
         */
    @TableField("tuk_cemployeeid")
        private String tukCemployeeid;

        /**
         * 開始日
         */
    @TableField("tuk_dstartdate")
        private Date tukDstartdate;

        /**
         * 終了日
         */
    @TableField("tuk_denddate")
        private Date tukDenddate;

        /**
         * 更新者
         */
    @TableField("tuk_cmodifieruserid")
        private String tukCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tuk_dmodifieddate")
        private Date tukDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tuk_cmodifierprogramid")
        private String tukCmodifierprogramid;

        /**
         * 履歴ｼｰｹﾝｽ                       遡及計算用、csv出力時の連番とは異なるので注意
         */
    @TableField("tuk_nseq")
        private Long tukNseq;

        /**
         * 連番                            csv出力時に計算、実データとしては登録しない
         */
    @TableField("tuk_cdata1")
        private String tukCdata1;

        /**
         * 年月                            yyyymm形式
         */
    @TableField("tuk_cdata2")
        private String tukCdata2;

        /**
         * （将来拡張用）
         */
    @TableField("tuk_cdata3")
        private String tukCdata3;

        /**
         * 個人番号
         */
    @TableField("tuk_cdata4")
        private String tukCdata4;

        /**
         * 超勤１ 予算項
         */
    @TableField("tuk_cdata5")
        private String tukCdata5;

        /**
         * 超125
         */
    @TableField("tuk_cdata6")
        private String tukCdata6;

        /**
         * 超135
         */
    @TableField("tuk_cdata7")
        private String tukCdata7;

        /**
         * 超150
         */
    @TableField("tuk_cdata8")
        private String tukCdata8;

        /**
         * 超160
         */
    @TableField("tuk_cdata9")
        private String tukCdata9;

        /**
         * 超夜
         */
    @TableField("tuk_cdata10")
        private String tukCdata10;

        /**
         * 超休
         */
    @TableField("tuk_cdata11")
        private String tukCdata11;

        /**
         * 超勤２ 予算項
         */
    @TableField("tuk_cdata12")
        private String tukCdata12;

        /**
         * 超125
         */
    @TableField("tuk_cdata13")
        private String tukCdata13;

        /**
         * 超135
         */
    @TableField("tuk_cdata14")
        private String tukCdata14;

        /**
         * 超150
         */
    @TableField("tuk_cdata15")
        private String tukCdata15;

        /**
         * 超160
         */
    @TableField("tuk_cdata16")
        private String tukCdata16;

        /**
         * 超夜
         */
    @TableField("tuk_cdata17")
        private String tukCdata17;

        /**
         * 超休
         */
    @TableField("tuk_cdata18")
        private String tukCdata18;

        /**
         * 超勤３ 予算項
         */
    @TableField("tuk_cdata19")
        private String tukCdata19;

        /**
         * 超125
         */
    @TableField("tuk_cdata20")
        private String tukCdata20;

        /**
         * 超135
         */
    @TableField("tuk_cdata21")
        private String tukCdata21;

        /**
         * 超150
         */
    @TableField("tuk_cdata22")
        private String tukCdata22;

        /**
         * 超160
         */
    @TableField("tuk_cdata23")
        private String tukCdata23;

        /**
         * 超夜
         */
    @TableField("tuk_cdata24")
        private String tukCdata24;

        /**
         * 超休
         */
    @TableField("tuk_cdata25")
        private String tukCdata25;

        /**
         * 宿直１ 予算項
         */
    @TableField("tuk_cdata26")
        private String tukCdata26;

        /**
         * 区分
         */
    @TableField("tuk_cdata27")
        private String tukCdata27;

        /**
         * 回数
         */
    @TableField("tuk_cdata28")
        private String tukCdata28;

        /**
         * 宿直２ 予算項
         */
    @TableField("tuk_cdata29")
        private String tukCdata29;

        /**
         * 区分
         */
    @TableField("tuk_cdata30")
        private String tukCdata30;

        /**
         * 回数
         */
    @TableField("tuk_cdata31")
        private String tukCdata31;

        /**
         * 宿直３ 予算項
         */
    @TableField("tuk_cdata32")
        private String tukCdata32;

        /**
         * 区分
         */
    @TableField("tuk_cdata33")
        private String tukCdata33;

        /**
         * 回数
         */
    @TableField("tuk_cdata34")
        private String tukCdata34;

        /**
         * 宿直４ 予算項
         */
    @TableField("tuk_cdata35")
        private String tukCdata35;

        /**
         * 区分
         */
    @TableField("tuk_cdata36")
        private String tukCdata36;

        /**
         * 回数
         */
    @TableField("tuk_cdata37")
        private String tukCdata37;

        /**
         * 特勤１ 予算項
         */
    @TableField("tuk_cdata38")
        private String tukCdata38;

        /**
         * 区分
         */
    @TableField("tuk_cdata39")
        private String tukCdata39;

        /**
         * 回数
         */
    @TableField("tuk_cdata40")
        private String tukCdata40;

        /**
         * 特勤２ 予算項
         */
    @TableField("tuk_cdata41")
        private String tukCdata41;

        /**
         * 区分
         */
    @TableField("tuk_cdata42")
        private String tukCdata42;

        /**
         * 回数
         */
    @TableField("tuk_cdata43")
        private String tukCdata43;

        /**
         * 特勤３ 予算項
         */
    @TableField("tuk_cdata44")
        private String tukCdata44;

        /**
         * 区分
         */
    @TableField("tuk_cdata45")
        private String tukCdata45;

        /**
         * 回数
         */
    @TableField("tuk_cdata46")
        private String tukCdata46;

        /**
         * 特勤４ 予算項
         */
    @TableField("tuk_cdata47")
        private String tukCdata47;

        /**
         * 区分
         */
    @TableField("tuk_cdata48")
        private String tukCdata48;

        /**
         * 回数
         */
    @TableField("tuk_cdata49")
        private String tukCdata49;

        /**
         * 特勤５ 予算項
         */
    @TableField("tuk_cdata50")
        private String tukCdata50;

        /**
         * 区分
         */
    @TableField("tuk_cdata51")
        private String tukCdata51;

        /**
         * 回数
         */
    @TableField("tuk_cdata52")
        private String tukCdata52;

        /**
         * 特勤６ 予算項
         */
    @TableField("tuk_cdata53")
        private String tukCdata53;

        /**
         * 区分
         */
    @TableField("tuk_cdata54")
        private String tukCdata54;

        /**
         * 回数
         */
    @TableField("tuk_cdata55")
        private String tukCdata55;

        /**
         * 特勤７ 予算項
         */
    @TableField("tuk_cdata56")
        private String tukCdata56;

        /**
         * 区分
         */
    @TableField("tuk_cdata57")
        private String tukCdata57;

        /**
         * 回数
         */
    @TableField("tuk_cdata58")
        private String tukCdata58;

        /**
         * 特勤８ 予算項
         */
    @TableField("tuk_cdata59")
        private String tukCdata59;

        /**
         * 区分
         */
    @TableField("tuk_cdata60")
        private String tukCdata60;

        /**
         * 回数
         */
    @TableField("tuk_cdata61")
        private String tukCdata61;

        /**
         * 勤務 日数
         */
    @TableField("tuk_cdata62")
        private String tukCdata62;

        /**
         * 勤務 時数
         */
    @TableField("tuk_cdata63")
        private String tukCdata63;

        /**
         * 超100 時間
         */
    @TableField("tuk_cdata64")
        private String tukCdata64;

        /**
         * 時給者 勤務日数
         */
    @TableField("tuk_cdata65")
        private String tukCdata65;

        /**
         * 欠勤日数
         */
    @TableField("tuk_cdata66")
        private String tukCdata66;

        /**
         * 減額時間
         */
    @TableField("tuk_cdata67")
        private String tukCdata67;

        /**
         * 超勤１ 予算項
         */
    @TableField("tuk_cdata68")
        private String tukCdata68;

        /**
         * 超125
         */
    @TableField("tuk_cdata69")
        private String tukCdata69;

        /**
         * 超135
         */
    @TableField("tuk_cdata70")
        private String tukCdata70;

        /**
         * 超150
         */
    @TableField("tuk_cdata71")
        private String tukCdata71;

        /**
         * 超160
         */
    @TableField("tuk_cdata72")
        private String tukCdata72;

        /**
         * 超夜
         */
    @TableField("tuk_cdata73")
        private String tukCdata73;

        /**
         * 超休
         */
    @TableField("tuk_cdata74")
        private String tukCdata74;

        /**
         * 超勤２ 予算項
         */
    @TableField("tuk_cdata75")
        private String tukCdata75;

        /**
         * 超125
         */
    @TableField("tuk_cdata76")
        private String tukCdata76;

        /**
         * 超135
         */
    @TableField("tuk_cdata77")
        private String tukCdata77;

        /**
         * 超150
         */
    @TableField("tuk_cdata78")
        private String tukCdata78;

        /**
         * 超160
         */
    @TableField("tuk_cdata79")
        private String tukCdata79;

        /**
         * 超夜
         */
    @TableField("tuk_cdata80")
        private String tukCdata80;

        /**
         * 超休
         */
    @TableField("tuk_cdata81")
        private String tukCdata81;

        /**
         * 超勤３ 予算項
         */
    @TableField("tuk_cdata82")
        private String tukCdata82;

        /**
         * 超125
         */
    @TableField("tuk_cdata83")
        private String tukCdata83;

        /**
         * 超135
         */
    @TableField("tuk_cdata84")
        private String tukCdata84;

        /**
         * 超150
         */
    @TableField("tuk_cdata85")
        private String tukCdata85;

        /**
         * 超160
         */
    @TableField("tuk_cdata86")
        private String tukCdata86;

        /**
         * 超夜
         */
    @TableField("tuk_cdata87")
        private String tukCdata87;

        /**
         * 超休
         */
    @TableField("tuk_cdata88")
        private String tukCdata88;

        /**
         * 勤務 日数
         */
    @TableField("tuk_cdata89")
        private String tukCdata89;

        /**
         * 勤務 時数
         */
    @TableField("tuk_cdata90")
        private String tukCdata90;

        /**
         * 超100 時間
         */
    @TableField("tuk_cdata91")
        private String tukCdata91;

        /**
         * 欠勤日数
         */
    @TableField("tuk_cdata92")
        private String tukCdata92;

        /**
         * 減額時間
         */
    @TableField("tuk_cdata93")
        private String tukCdata93;

        /**
         * 拡張 超勤2 100
         */
    @TableField("tuk_cdata94")
        private String tukCdata94;

        /**
         * 超勤3 100
         */
    @TableField("tuk_cdata95")
        private String tukCdata95;

        /**
         * 日割前超勤1 予算項
         */
    @TableField("tuk_cdata96")
        private String tukCdata96;

        /**
         * 超125
         */
    @TableField("tuk_cdata97")
        private String tukCdata97;

        /**
         * 超135
         */
    @TableField("tuk_cdata98")
        private String tukCdata98;

        /**
         * 超150
         */
    @TableField("tuk_cdata99")
        private String tukCdata99;

        /**
         * 超160
         */
    @TableField("tuk_cdata100")
        private String tukCdata100;

        /**
         * 超夜
         */
    @TableField("tuk_cdata101")
        private String tukCdata101;

        /**
         * 超休
         */
    @TableField("tuk_cdata102")
        private String tukCdata102;

        /**
         * 日割前超勤2 予算項
         */
    @TableField("tuk_cdata103")
        private String tukCdata103;

        /**
         * 超125
         */
    @TableField("tuk_cdata104")
        private String tukCdata104;

        /**
         * 超135
         */
    @TableField("tuk_cdata105")
        private String tukCdata105;

        /**
         * 超150
         */
    @TableField("tuk_cdata106")
        private String tukCdata106;

        /**
         * 超160
         */
    @TableField("tuk_cdata107")
        private String tukCdata107;

        /**
         * 超夜
         */
    @TableField("tuk_cdata108")
        private String tukCdata108;

        /**
         * 超休
         */
    @TableField("tuk_cdata109")
        private String tukCdata109;

        /**
         * 日割前超勤3 予算項
         */
    @TableField("tuk_cdata110")
        private String tukCdata110;

        /**
         * 超125
         */
    @TableField("tuk_cdata111")
        private String tukCdata111;

        /**
         * 超135
         */
    @TableField("tuk_cdata112")
        private String tukCdata112;

        /**
         * 超150
         */
    @TableField("tuk_cdata113")
        private String tukCdata113;

        /**
         * 超160
         */
    @TableField("tuk_cdata114")
        private String tukCdata114;

        /**
         * 超夜
         */
    @TableField("tuk_cdata115")
        private String tukCdata115;

        /**
         * 超休
         */
    @TableField("tuk_cdata116")
        private String tukCdata116;

        /**
         * その他 勤務日数
         */
    @TableField("tuk_cdata117")
        private String tukCdata117;

        /**
         * 勤務 時数
         */
    @TableField("tuk_cdata118")
        private String tukCdata118;

        /**
         * 超100 時間
         */
    @TableField("tuk_cdata119")
        private String tukCdata119;

        /**
         * 欠勤日数
         */
    @TableField("tuk_cdata120")
        private String tukCdata120;

        /**
         * 減額時間
         */
    @TableField("tuk_cdata121")
        private String tukCdata121;

        /**
         * 日割前（単2）超勤1 予算項
         */
    @TableField("tuk_cdata122")
        private String tukCdata122;

        /**
         * 超125
         */
    @TableField("tuk_cdata123")
        private String tukCdata123;

        /**
         * 超135
         */
    @TableField("tuk_cdata124")
        private String tukCdata124;

        /**
         * 超150
         */
    @TableField("tuk_cdata125")
        private String tukCdata125;

        /**
         * 超160
         */
    @TableField("tuk_cdata126")
        private String tukCdata126;

        /**
         * 超夜
         */
    @TableField("tuk_cdata127")
        private String tukCdata127;

        /**
         * 超休
         */
    @TableField("tuk_cdata128")
        private String tukCdata128;

        /**
         * 日割前（単2）超勤2 予算項
         */
    @TableField("tuk_cdata129")
        private String tukCdata129;

        /**
         * 超125
         */
    @TableField("tuk_cdata130")
        private String tukCdata130;

        /**
         * 超135
         */
    @TableField("tuk_cdata131")
        private String tukCdata131;

        /**
         * 超150
         */
    @TableField("tuk_cdata132")
        private String tukCdata132;

        /**
         * 超160
         */
    @TableField("tuk_cdata133")
        private String tukCdata133;

        /**
         * 超夜
         */
    @TableField("tuk_cdata134")
        private String tukCdata134;

        /**
         * 超休
         */
    @TableField("tuk_cdata135")
        private String tukCdata135;

        /**
         * 日割前（単2）超勤3 予算項
         */
    @TableField("tuk_cdata136")
        private String tukCdata136;

        /**
         * 超125
         */
    @TableField("tuk_cdata137")
        private String tukCdata137;

        /**
         * 超135
         */
    @TableField("tuk_cdata138")
        private String tukCdata138;

        /**
         * 超150
         */
    @TableField("tuk_cdata139")
        private String tukCdata139;

        /**
         * 超160
         */
    @TableField("tuk_cdata140")
        private String tukCdata140;

        /**
         * 超夜
         */
    @TableField("tuk_cdata141")
        private String tukCdata141;

        /**
         * 超休
         */
    @TableField("tuk_cdata142")
        private String tukCdata142;

        /**
         * その他 勤務 日数
         */
    @TableField("tuk_cdata143")
        private String tukCdata143;

        /**
         * 勤務 時数
         */
    @TableField("tuk_cdata144")
        private String tukCdata144;

        /**
         * 超100 時間
         */
    @TableField("tuk_cdata145")
        private String tukCdata145;

        /**
         * 欠勤日数
         */
    @TableField("tuk_cdata146")
        private String tukCdata146;

        /**
         * 減額時間
         */
    @TableField("tuk_cdata147")
        private String tukCdata147;

        /**
         * 日割前拡張 超勤2 100
         */
    @TableField("tuk_cdata148")
        private String tukCdata148;

        /**
         * 超勤3 100
         */
    @TableField("tuk_cdata149")
        private String tukCdata149;

        /**
         * 超勤175(単価1/予算項1)
         */
    @TableField("tuk_cdata150")
        private String tukCdata150;

        /**
         * 超勤175(単価1/予算項2)
         */
    @TableField("tuk_cdata151")
        private String tukCdata151;

        /**
         * 超勤175(単価1/予算項3)
         */
    @TableField("tuk_cdata152")
        private String tukCdata152;

        /**
         * 超勤175(単価2/予算項1)
         */
    @TableField("tuk_cdata153")
        private String tukCdata153;

        /**
         * 超勤175(単価2/予算項2)
         */
    @TableField("tuk_cdata154")
        private String tukCdata154;

        /**
         * 超勤175(単価2/予算項3)
         */
    @TableField("tuk_cdata155")
        private String tukCdata155;

        /**
         * 日割前・超勤175(単価1/予算項1)
         */
    @TableField("tuk_cdata156")
        private String tukCdata156;

        /**
         * 日割前・超勤175(単価1/予算項2)
         */
    @TableField("tuk_cdata157")
        private String tukCdata157;

        /**
         * 日割前・超勤175(単価1/予算項3)
         */
    @TableField("tuk_cdata158")
        private String tukCdata158;

        /**
         * 日割前・超勤175(単価2/予算項1)
         */
    @TableField("tuk_cdata159")
        private String tukCdata159;

        /**
         * 日割前・超勤175(単価2/予算項2)
         */
    @TableField("tuk_cdata160")
        private String tukCdata160;

        /**
         * 日割前・超勤175(単価2/予算項3)
         */
    @TableField("tuk_cdata161")
        private String tukCdata161;

        /**
         * tuk_cdata162
         */
    @TableField("tuk_cdata162")
        private String tukCdata162;

        /**
         * tuk_cdata163
         */
    @TableField("tuk_cdata163")
        private String tukCdata163;

        /**
         * tuk_cdata164
         */
    @TableField("tuk_cdata164")
        private String tukCdata164;

        /**
         * tuk_cdata165
         */
    @TableField("tuk_cdata165")
        private String tukCdata165;

        /**
         * tuk_cdata166
         */
    @TableField("tuk_cdata166")
        private String tukCdata166;

        /**
         * tuk_cdata167
         */
    @TableField("tuk_cdata167")
        private String tukCdata167;

        /**
         * tuk_cdata168
         */
    @TableField("tuk_cdata168")
        private String tukCdata168;

        /**
         * tuk_cdata169
         */
    @TableField("tuk_cdata169")
        private String tukCdata169;

        /**
         * tuk_cdata170
         */
    @TableField("tuk_cdata170")
        private String tukCdata170;

        /**
         * tuk_cdata171
         */
    @TableField("tuk_cdata171")
        private String tukCdata171;

        /**
         * tuk_cdata172
         */
    @TableField("tuk_cdata172")
        private String tukCdata172;

        /**
         * tuk_cdata173
         */
    @TableField("tuk_cdata173")
        private String tukCdata173;

        /**
         * tuk_cdata174
         */
    @TableField("tuk_cdata174")
        private String tukCdata174;

        /**
         * tuk_cdata175
         */
    @TableField("tuk_cdata175")
        private String tukCdata175;

        /**
         * tuk_cdata176
         */
    @TableField("tuk_cdata176")
        private String tukCdata176;

        /**
         * tuk_cdata177
         */
    @TableField("tuk_cdata177")
        private String tukCdata177;

        /**
         * tuk_cdata178
         */
    @TableField("tuk_cdata178")
        private String tukCdata178;

        /**
         * tuk_cdata179
         */
    @TableField("tuk_cdata179")
        private String tukCdata179;

        /**
         * tuk_cdata180
         */
    @TableField("tuk_cdata180")
        private String tukCdata180;

        /**
         * tuk_cdata181
         */
    @TableField("tuk_cdata181")
        private String tukCdata181;

        /**
         * tuk_cdata182
         */
    @TableField("tuk_cdata182")
        private String tukCdata182;

        /**
         * tuk_cdata183
         */
    @TableField("tuk_cdata183")
        private String tukCdata183;

        /**
         * tuk_cdata184
         */
    @TableField("tuk_cdata184")
        private String tukCdata184;

        /**
         * tuk_cdata185
         */
    @TableField("tuk_cdata185")
        private String tukCdata185;

        /**
         * tuk_cdata186
         */
    @TableField("tuk_cdata186")
        private String tukCdata186;

        /**
         * tuk_cdata187
         */
    @TableField("tuk_cdata187")
        private String tukCdata187;

        /**
         * tuk_cdata188
         */
    @TableField("tuk_cdata188")
        private String tukCdata188;

        /**
         * tuk_cdata189
         */
    @TableField("tuk_cdata189")
        private String tukCdata189;

        /**
         * tuk_cdata190
         */
    @TableField("tuk_cdata190")
        private String tukCdata190;

        /**
         * tuk_cdata191
         */
    @TableField("tuk_cdata191")
        private String tukCdata191;

        /**
         * tuk_cdata192
         */
    @TableField("tuk_cdata192")
        private String tukCdata192;

        /**
         * tuk_cdata193
         */
    @TableField("tuk_cdata193")
        private String tukCdata193;

        /**
         * tuk_cdata194
         */
    @TableField("tuk_cdata194")
        private String tukCdata194;

        /**
         * tuk_cdata195
         */
    @TableField("tuk_cdata195")
        private String tukCdata195;

        /**
         * tuk_cdata196
         */
    @TableField("tuk_cdata196")
        private String tukCdata196;

        /**
         * tuk_cdata197
         */
    @TableField("tuk_cdata197")
        private String tukCdata197;

        /**
         * tuk_cdata198
         */
    @TableField("tuk_cdata198")
        private String tukCdata198;

        /**
         * tuk_cdata199
         */
    @TableField("tuk_cdata199")
        private String tukCdata199;

        /**
         * tuk_cdata200
         */
    @TableField("tuk_cdata200")
        private String tukCdata200;


        }