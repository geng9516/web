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
 * [勤怠]月別情報                      2007/2/23 年休調整日数のカラムを追加。年休(時間)
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
@TableName("tmg_monthly")
public class TmgMonthlyDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmo_ccustomerid")
        private String tmoCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmo_ccompanyid")
        private String tmoCcompanyid;

        /**
         * 職員番号
         */
                @TableId(value = "tmo_cemployeeid", type = IdType.AUTO)
                private String tmoCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tmo_dstartdate")
        private Date tmoDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tmo_denddate")
        private Date tmoDenddate;

        /**
         * 更新者
         */
    @TableField("tmo_cmodifieruserid")
        private String tmoCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmo_dmodifieddate")
        private Date tmoDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmo_cmodifierprogramid")
        private String tmoCmodifierprogramid;

        /**
         * 該当年                           yyyy
         */
    @TableField("tmo_nyyyy")
        private Long tmoNyyyy;

        /**
         * 該当年月                          yyyy/mm/01
         */
    @TableField("tmo_dyyyymm")
        private Date tmoDyyyymm;

        /**
         * ステータスフラグ                                                    mgd:tmg_datastatus
         */
    @TableField("tmo_cstatusflg")
        private String tmoCstatusflg;

        /**
         * 付与前：年休月初残日数
         */
    @TableField("tmo_npaid_bef_begining_days")
        private Long tmoNpaidBefBeginingDays;

        /**
         * 付与前：年休月初残時間数
         */
    @TableField("tmo_npaid_bef_begining_hours")
        private Long tmoNpaidBefBeginingHours;

        /**
         * 付与前：年休(終日)取得回数
         */
    @TableField("tmo_npaid_bef_used_days")
        private Long tmoNpaidBefUsedDays;

        /**
         * 付与前：年休(半休)取得回数
         */
    @TableField("tmo_npaid_bef_used_halfdays")
        private Long tmoNpaidBefUsedHalfdays;

        /**
         * 付与前：年休(時間)取得時間数
         */
    @TableField("tmo_npaid_bef_used_hours")
        private Long tmoNpaidBefUsedHours;

        /**
         * 付与後：年休繰越日数
         */
    @TableField("tmo_npaid_carryforward")
        private Long tmoNpaidCarryforward;

        /**
         * 付与後：年休付与日数
         */
    @TableField("tmo_npaid_add")
        private Long tmoNpaidAdd;

        /**
         * 付与後：年休調整付与日数
         */
    @TableField("tmg_npaid_ajust")
        private Long tmgNpaidAjust;

        /**
         * 付与後：年休調整繰越日数
         */
    @TableField("tmo_npaid_cr_ajust")
        private Long tmoNpaidCrAjust;

        /**
         * 付与後：年休調整付与時間
         */
    @TableField("tmo_npaid_add_ajust_hours")
        private Long tmoNpaidAddAjustHours;

        /**
         * 付与後：年休調整繰越時間
         */
    @TableField("tmo_npaid_cr_ajust_hours")
        private Long tmoNpaidCrAjustHours;

        /**
         * 付与後：年休月初残日数/付与前：年休残日数
         */
    @TableField("tmo_npaid_begining_days")
        private Long tmoNpaidBeginingDays;

        /**
         * 付与後：年休月初残時間数/付与前：年休残時間数
         */
    @TableField("tmo_npaid_begining_hours")
        private Long tmoNpaidBeginingHours;

        /**
         * 付与後：年休(終日)取得回数
         */
    @TableField("tmo_npaid_used_days")
        private Long tmoNpaidUsedDays;

        /**
         * 付与後：年休(半休)取得回数
         */
    @TableField("tmo_npaid_used_halfdays")
        private Long tmoNpaidUsedHalfdays;

        /**
         * 付与後：年休(時間)取得時間数
         */
    @TableField("tmo_npaid_used_hours")
        private Long tmoNpaidUsedHours;

        /**
         * 付与後：年休残日数
         */
    @TableField("tmo_npaid_rest_days")
        private Long tmoNpaidRestDays;

        /**
         * 付与後：年休残時間数
         */
    @TableField("tmo_npaid_rest_hours")
        private Long tmoNpaidRestHours;

        /**
         * 病休取得日数
         */
    @TableField("tmo_nsichness_used_days")
        private Long tmoNsichnessUsedDays;

        /**
         * 病休取得時間数                       分単位で格納
         */
    @TableField("tmo_nsichness_used_hours")
        private Long tmoNsichnessUsedHours;

        /**
         * 特休取得日数
         */
    @TableField("tmo_nspecial_used_days")
        private Long tmoNspecialUsedDays;

        /**
         * 特休取得時間数                       分単位で格納
         */
    @TableField("tmo_nspecial_used_hours")
        private Long tmoNspecialUsedHours;

        /**
         * 勤怠月別項目001
         */
    @TableField("tmo_ncalc001")
        private Long tmoNcalc001;

        /**
         * 勤怠月別項目002
         */
    @TableField("tmo_ncalc002")
        private Long tmoNcalc002;

        /**
         * 勤怠月別項目003
         */
    @TableField("tmo_ncalc003")
        private Long tmoNcalc003;

        /**
         * 勤怠月別項目004
         */
    @TableField("tmo_ncalc004")
        private Long tmoNcalc004;

        /**
         * 勤怠月別項目005
         */
    @TableField("tmo_ncalc005")
        private Long tmoNcalc005;

        /**
         * 勤怠月別項目006
         */
    @TableField("tmo_ncalc006")
        private Long tmoNcalc006;

        /**
         * 勤怠月別項目007
         */
    @TableField("tmo_ncalc007")
        private Long tmoNcalc007;

        /**
         * 勤怠月別項目008
         */
    @TableField("tmo_ncalc008")
        private Long tmoNcalc008;

        /**
         * 勤怠月別項目009
         */
    @TableField("tmo_ncalc009")
        private Long tmoNcalc009;

        /**
         * 勤怠月別項目010
         */
    @TableField("tmo_ncalc010")
        private Long tmoNcalc010;

        /**
         * 勤怠月別項目011
         */
    @TableField("tmo_ncalc011")
        private Long tmoNcalc011;

        /**
         * 勤怠月別項目012
         */
    @TableField("tmo_ncalc012")
        private Long tmoNcalc012;

        /**
         * 勤怠月別項目013
         */
    @TableField("tmo_ncalc013")
        private Long tmoNcalc013;

        /**
         * 勤怠月別項目014
         */
    @TableField("tmo_ncalc014")
        private Long tmoNcalc014;

        /**
         * 勤怠月別項目015
         */
    @TableField("tmo_ncalc015")
        private Long tmoNcalc015;

        /**
         * 勤怠月別項目016
         */
    @TableField("tmo_ncalc016")
        private Long tmoNcalc016;

        /**
         * 勤怠月別項目017
         */
    @TableField("tmo_ncalc017")
        private Long tmoNcalc017;

        /**
         * 勤怠月別項目018
         */
    @TableField("tmo_ncalc018")
        private Long tmoNcalc018;

        /**
         * 勤怠月別項目019
         */
    @TableField("tmo_ncalc019")
        private Long tmoNcalc019;

        /**
         * 勤怠月別項目020
         */
    @TableField("tmo_ncalc020")
        private Long tmoNcalc020;

        /**
         * 勤怠月別項目021
         */
    @TableField("tmo_ncalc021")
        private Long tmoNcalc021;

        /**
         * 勤怠月別項目022
         */
    @TableField("tmo_ncalc022")
        private Long tmoNcalc022;

        /**
         * 勤怠月別項目023
         */
    @TableField("tmo_ncalc023")
        private Long tmoNcalc023;

        /**
         * 勤怠月別項目024
         */
    @TableField("tmo_ncalc024")
        private Long tmoNcalc024;

        /**
         * 勤怠月別項目025
         */
    @TableField("tmo_ncalc025")
        private Long tmoNcalc025;

        /**
         * 勤怠月別項目026
         */
    @TableField("tmo_ncalc026")
        private Long tmoNcalc026;

        /**
         * 勤怠月別項目027
         */
    @TableField("tmo_ncalc027")
        private Long tmoNcalc027;

        /**
         * 勤怠月別項目028
         */
    @TableField("tmo_ncalc028")
        private Long tmoNcalc028;

        /**
         * 勤怠月別項目029
         */
    @TableField("tmo_ncalc029")
        private Long tmoNcalc029;

        /**
         * 勤怠月別項目030
         */
    @TableField("tmo_ncalc030")
        private Long tmoNcalc030;

        /**
         * 勤怠月別項目031
         */
    @TableField("tmo_ncalc031")
        private Long tmoNcalc031;

        /**
         * 勤怠月別項目032
         */
    @TableField("tmo_ncalc032")
        private Long tmoNcalc032;

        /**
         * 勤怠月別項目033
         */
    @TableField("tmo_ncalc033")
        private Long tmoNcalc033;

        /**
         * 勤怠月別項目034
         */
    @TableField("tmo_ncalc034")
        private Long tmoNcalc034;

        /**
         * 勤怠月別項目035
         */
    @TableField("tmo_ncalc035")
        private Long tmoNcalc035;

        /**
         * 勤怠月別項目036
         */
    @TableField("tmo_ncalc036")
        private Long tmoNcalc036;

        /**
         * 勤怠月別項目037
         */
    @TableField("tmo_ncalc037")
        private Long tmoNcalc037;

        /**
         * 勤怠月別項目038
         */
    @TableField("tmo_ncalc038")
        private Long tmoNcalc038;

        /**
         * 勤怠月別項目039
         */
    @TableField("tmo_ncalc039")
        private Long tmoNcalc039;

        /**
         * 勤怠月別項目040
         */
    @TableField("tmo_ncalc040")
        private Long tmoNcalc040;

        /**
         * 勤怠月別項目041
         */
    @TableField("tmo_ncalc041")
        private Long tmoNcalc041;

        /**
         * 勤怠月別項目042
         */
    @TableField("tmo_ncalc042")
        private Long tmoNcalc042;

        /**
         * 勤怠月別項目043
         */
    @TableField("tmo_ncalc043")
        private Long tmoNcalc043;

        /**
         * 勤怠月別項目044
         */
    @TableField("tmo_ncalc044")
        private Long tmoNcalc044;

        /**
         * 勤怠月別項目045
         */
    @TableField("tmo_ncalc045")
        private Long tmoNcalc045;

        /**
         * 勤怠月別項目046
         */
    @TableField("tmo_ncalc046")
        private Long tmoNcalc046;

        /**
         * 勤怠月別項目047
         */
    @TableField("tmo_ncalc047")
        private Long tmoNcalc047;

        /**
         * 勤怠月別項目048
         */
    @TableField("tmo_ncalc048")
        private Long tmoNcalc048;

        /**
         * 勤怠月別項目049
         */
    @TableField("tmo_ncalc049")
        private Long tmoNcalc049;

        /**
         * 勤怠月別項目050
         */
    @TableField("tmo_ncalc050")
        private Long tmoNcalc050;

        /**
         * 勤怠月別項目051
         */
    @TableField("tmo_ncalc051")
        private Long tmoNcalc051;

        /**
         * 勤怠月別項目052
         */
    @TableField("tmo_ncalc052")
        private Long tmoNcalc052;

        /**
         * 勤怠月別項目053
         */
    @TableField("tmo_ncalc053")
        private Long tmoNcalc053;

        /**
         * 勤怠月別項目054
         */
    @TableField("tmo_ncalc054")
        private Long tmoNcalc054;

        /**
         * 勤怠月別項目055
         */
    @TableField("tmo_ncalc055")
        private Long tmoNcalc055;

        /**
         * 勤怠月別項目056
         */
    @TableField("tmo_ncalc056")
        private Long tmoNcalc056;

        /**
         * 勤怠月別項目057
         */
    @TableField("tmo_ncalc057")
        private Long tmoNcalc057;

        /**
         * 勤怠月別項目058
         */
    @TableField("tmo_ncalc058")
        private Long tmoNcalc058;

        /**
         * 勤怠月別項目059
         */
    @TableField("tmo_ncalc059")
        private Long tmoNcalc059;

        /**
         * 勤怠月別項目060
         */
    @TableField("tmo_ncalc060")
        private Long tmoNcalc060;

        /**
         * 勤怠月別項目061
         */
    @TableField("tmo_ncalc061")
        private Long tmoNcalc061;

        /**
         * 勤怠月別項目062
         */
    @TableField("tmo_ncalc062")
        private Long tmoNcalc062;

        /**
         * 勤怠月別項目063
         */
    @TableField("tmo_ncalc063")
        private Long tmoNcalc063;

        /**
         * 勤怠月別項目064
         */
    @TableField("tmo_ncalc064")
        private Long tmoNcalc064;

        /**
         * 勤怠月別項目065
         */
    @TableField("tmo_ncalc065")
        private Long tmoNcalc065;

        /**
         * 勤怠月別項目066
         */
    @TableField("tmo_ncalc066")
        private Long tmoNcalc066;

        /**
         * 勤怠月別項目067
         */
    @TableField("tmo_ncalc067")
        private Long tmoNcalc067;

        /**
         * 勤怠月別項目068
         */
    @TableField("tmo_ncalc068")
        private Long tmoNcalc068;

        /**
         * 勤怠月別項目069
         */
    @TableField("tmo_ncalc069")
        private Long tmoNcalc069;

        /**
         * 勤怠月別項目070
         */
    @TableField("tmo_ncalc070")
        private Long tmoNcalc070;

        /**
         * 勤怠月別項目071
         */
    @TableField("tmo_ncalc071")
        private Long tmoNcalc071;

        /**
         * 勤怠月別項目072
         */
    @TableField("tmo_ncalc072")
        private Long tmoNcalc072;

        /**
         * 勤怠月別項目073
         */
    @TableField("tmo_ncalc073")
        private Long tmoNcalc073;

        /**
         * 勤怠月別項目074
         */
    @TableField("tmo_ncalc074")
        private Long tmoNcalc074;

        /**
         * 勤怠月別項目075
         */
    @TableField("tmo_ncalc075")
        private Long tmoNcalc075;

        /**
         * 勤怠月別項目076
         */
    @TableField("tmo_ncalc076")
        private Long tmoNcalc076;

        /**
         * 勤怠月別項目077
         */
    @TableField("tmo_ncalc077")
        private Long tmoNcalc077;

        /**
         * 勤怠月別項目078
         */
    @TableField("tmo_ncalc078")
        private Long tmoNcalc078;

        /**
         * 勤怠月別項目079
         */
    @TableField("tmo_ncalc079")
        private Long tmoNcalc079;

        /**
         * 勤怠月別項目080
         */
    @TableField("tmo_ncalc080")
        private Long tmoNcalc080;

        /**
         * 勤怠月別項目081
         */
    @TableField("tmo_ncalc081")
        private Long tmoNcalc081;

        /**
         * 勤怠月別項目082
         */
    @TableField("tmo_ncalc082")
        private Long tmoNcalc082;

        /**
         * 勤怠月別項目083
         */
    @TableField("tmo_ncalc083")
        private Long tmoNcalc083;

        /**
         * 勤怠月別項目084
         */
    @TableField("tmo_ncalc084")
        private Long tmoNcalc084;

        /**
         * 勤怠月別項目085
         */
    @TableField("tmo_ncalc085")
        private Long tmoNcalc085;

        /**
         * 勤怠月別項目086
         */
    @TableField("tmo_ncalc086")
        private Long tmoNcalc086;

        /**
         * 勤怠月別項目087
         */
    @TableField("tmo_ncalc087")
        private Long tmoNcalc087;

        /**
         * 勤怠月別項目088
         */
    @TableField("tmo_ncalc088")
        private Long tmoNcalc088;

        /**
         * 勤怠月別項目089
         */
    @TableField("tmo_ncalc089")
        private Long tmoNcalc089;

        /**
         * 勤怠月別項目090
         */
    @TableField("tmo_ncalc090")
        private Long tmoNcalc090;

        /**
         * 勤怠月別項目091
         */
    @TableField("tmo_ncalc091")
        private Long tmoNcalc091;

        /**
         * 勤怠月別項目092
         */
    @TableField("tmo_ncalc092")
        private Long tmoNcalc092;

        /**
         * 勤怠月別項目093
         */
    @TableField("tmo_ncalc093")
        private Long tmoNcalc093;

        /**
         * 勤怠月別項目094
         */
    @TableField("tmo_ncalc094")
        private Long tmoNcalc094;

        /**
         * 勤怠月別項目095
         */
    @TableField("tmo_ncalc095")
        private Long tmoNcalc095;

        /**
         * 勤怠月別項目096
         */
    @TableField("tmo_ncalc096")
        private Long tmoNcalc096;

        /**
         * 勤怠月別項目097
         */
    @TableField("tmo_ncalc097")
        private Long tmoNcalc097;

        /**
         * 勤怠月別項目098
         */
    @TableField("tmo_ncalc098")
        private Long tmoNcalc098;

        /**
         * 勤怠月別項目099
         */
    @TableField("tmo_ncalc099")
        private Long tmoNcalc099;

        /**
         * 勤怠月別項目100
         */
    @TableField("tmo_ncalc100")
        private Long tmoNcalc100;

        /**
         * 同月内振替フラグ  1:同月内で振替  null:同週内で振替
         */
    @TableField("tmo_nmonth_chk_flg")
        private Long tmoNmonthChkFlg;

        /**
         * 勤怠月別項目101
         */
    @TableField("tmo_ncalc101")
        private Long tmoNcalc101;

        /**
         * 勤怠月別項目102
         */
    @TableField("tmo_ncalc102")
        private Long tmoNcalc102;

        /**
         * 勤怠月別項目103
         */
    @TableField("tmo_ncalc103")
        private Long tmoNcalc103;

        /**
         * 勤怠月別項目104
         */
    @TableField("tmo_ncalc104")
        private Long tmoNcalc104;

        /**
         * 勤怠月別項目105
         */
    @TableField("tmo_ncalc105")
        private Long tmoNcalc105;

        /**
         * 勤怠月別項目106
         */
    @TableField("tmo_ncalc106")
        private Long tmoNcalc106;

        /**
         * 勤怠月別項目107
         */
    @TableField("tmo_ncalc107")
        private Long tmoNcalc107;

        /**
         * 勤怠月別項目108
         */
    @TableField("tmo_ncalc108")
        private Long tmoNcalc108;

        /**
         * 勤怠月別項目109
         */
    @TableField("tmo_ncalc109")
        private Long tmoNcalc109;

        /**
         * 勤怠月別項目110
         */
    @TableField("tmo_ncalc110")
        private Long tmoNcalc110;

        /**
         * 勤怠月別項目111
         */
    @TableField("tmo_ncalc111")
        private Long tmoNcalc111;

        /**
         * 勤怠月別項目112
         */
    @TableField("tmo_ncalc112")
        private Long tmoNcalc112;

        /**
         * 勤怠月別項目113
         */
    @TableField("tmo_ncalc113")
        private Long tmoNcalc113;

        /**
         * 勤怠月別項目114
         */
    @TableField("tmo_ncalc114")
        private Long tmoNcalc114;

        /**
         * 勤怠月別項目115
         */
    @TableField("tmo_ncalc115")
        private Long tmoNcalc115;

        /**
         * 勤怠月別項目116
         */
    @TableField("tmo_ncalc116")
        private Long tmoNcalc116;

        /**
         * 勤怠月別項目117
         */
    @TableField("tmo_ncalc117")
        private Long tmoNcalc117;

        /**
         * 勤怠月別項目118
         */
    @TableField("tmo_ncalc118")
        private Long tmoNcalc118;

        /**
         * 勤怠月別項目119
         */
    @TableField("tmo_ncalc119")
        private Long tmoNcalc119;

        /**
         * 勤怠月別項目120
         */
    @TableField("tmo_ncalc120")
        private Long tmoNcalc120;

        /**
         * 勤怠月別項目121
         */
    @TableField("tmo_ncalc121")
        private Long tmoNcalc121;

        /**
         * 勤怠月別項目122
         */
    @TableField("tmo_ncalc122")
        private Long tmoNcalc122;

        /**
         * 勤怠月別項目123
         */
    @TableField("tmo_ncalc123")
        private Long tmoNcalc123;

        /**
         * 勤怠月別項目124
         */
    @TableField("tmo_ncalc124")
        private Long tmoNcalc124;

        /**
         * 勤怠月別項目125
         */
    @TableField("tmo_ncalc125")
        private Long tmoNcalc125;

        /**
         * 勤怠月別項目126
         */
    @TableField("tmo_ncalc126")
        private Long tmoNcalc126;

        /**
         * 勤怠月別項目127
         */
    @TableField("tmo_ncalc127")
        private Long tmoNcalc127;

        /**
         * 勤怠月別項目128
         */
    @TableField("tmo_ncalc128")
        private Long tmoNcalc128;

        /**
         * 勤怠月別項目129
         */
    @TableField("tmo_ncalc129")
        private Long tmoNcalc129;

        /**
         * 勤怠月別項目130
         */
    @TableField("tmo_ncalc130")
        private Long tmoNcalc130;

        /**
         * 勤怠月別項目131
         */
    @TableField("tmo_ncalc131")
        private Long tmoNcalc131;

        /**
         * 勤怠月別項目132
         */
    @TableField("tmo_ncalc132")
        private Long tmoNcalc132;

        /**
         * 勤怠月別項目133
         */
    @TableField("tmo_ncalc133")
        private Long tmoNcalc133;

        /**
         * 勤怠月別項目134
         */
    @TableField("tmo_ncalc134")
        private Long tmoNcalc134;

        /**
         * 勤怠月別項目135
         */
    @TableField("tmo_ncalc135")
        private Long tmoNcalc135;

        /**
         * 勤怠月別項目136
         */
    @TableField("tmo_ncalc136")
        private Long tmoNcalc136;

        /**
         * 勤怠月別項目137
         */
    @TableField("tmo_ncalc137")
        private Long tmoNcalc137;

        /**
         * 勤怠月別項目138
         */
    @TableField("tmo_ncalc138")
        private Long tmoNcalc138;

        /**
         * 勤怠月別項目139
         */
    @TableField("tmo_ncalc139")
        private Long tmoNcalc139;

        /**
         * 勤怠月別項目140
         */
    @TableField("tmo_ncalc140")
        private Long tmoNcalc140;

        /**
         * 勤怠月別項目141
         */
    @TableField("tmo_ncalc141")
        private Long tmoNcalc141;

        /**
         * 勤怠月別項目142
         */
    @TableField("tmo_ncalc142")
        private Long tmoNcalc142;

        /**
         * 勤怠月別項目143
         */
    @TableField("tmo_ncalc143")
        private Long tmoNcalc143;

        /**
         * 勤怠月別項目144
         */
    @TableField("tmo_ncalc144")
        private Long tmoNcalc144;

        /**
         * 勤怠月別項目145
         */
    @TableField("tmo_ncalc145")
        private Long tmoNcalc145;

        /**
         * 勤怠月別項目146
         */
    @TableField("tmo_ncalc146")
        private Long tmoNcalc146;

        /**
         * 勤怠月別項目147
         */
    @TableField("tmo_ncalc147")
        private Long tmoNcalc147;

        /**
         * 勤怠月別項目148
         */
    @TableField("tmo_ncalc148")
        private Long tmoNcalc148;

        /**
         * 勤怠月別項目149
         */
    @TableField("tmo_ncalc149")
        private Long tmoNcalc149;

        /**
         * 勤怠月別項目150
         */
    @TableField("tmo_ncalc150")
        private Long tmoNcalc150;

        /**
         * 勤怠月別項目151
         */
    @TableField("tmo_ncalc151")
        private Long tmoNcalc151;

        /**
         * 勤怠月別項目152
         */
    @TableField("tmo_ncalc152")
        private Long tmoNcalc152;

        /**
         * 勤怠月別項目153
         */
    @TableField("tmo_ncalc153")
        private Long tmoNcalc153;

        /**
         * 勤怠月別項目154
         */
    @TableField("tmo_ncalc154")
        private Long tmoNcalc154;

        /**
         * 勤怠月別項目155
         */
    @TableField("tmo_ncalc155")
        private Long tmoNcalc155;

        /**
         * 勤怠月別項目156
         */
    @TableField("tmo_ncalc156")
        private Long tmoNcalc156;

        /**
         * 勤怠月別項目157
         */
    @TableField("tmo_ncalc157")
        private Long tmoNcalc157;

        /**
         * 勤怠月別項目158
         */
    @TableField("tmo_ncalc158")
        private Long tmoNcalc158;

        /**
         * 勤怠月別項目159
         */
    @TableField("tmo_ncalc159")
        private Long tmoNcalc159;

        /**
         * 勤怠月別項目160
         */
    @TableField("tmo_ncalc160")
        private Long tmoNcalc160;

        /**
         * 勤怠月別項目161
         */
    @TableField("tmo_ncalc161")
        private Long tmoNcalc161;

        /**
         * 勤怠月別項目162
         */
    @TableField("tmo_ncalc162")
        private Long tmoNcalc162;

        /**
         * 勤怠月別項目163
         */
    @TableField("tmo_ncalc163")
        private Long tmoNcalc163;

        /**
         * 勤怠月別項目164
         */
    @TableField("tmo_ncalc164")
        private Long tmoNcalc164;

        /**
         * 勤怠月別項目165
         */
    @TableField("tmo_ncalc165")
        private Long tmoNcalc165;

        /**
         * 勤怠月別項目166
         */
    @TableField("tmo_ncalc166")
        private Long tmoNcalc166;

        /**
         * 勤怠月別項目167
         */
    @TableField("tmo_ncalc167")
        private Long tmoNcalc167;

        /**
         * 勤怠月別項目168
         */
    @TableField("tmo_ncalc168")
        private Long tmoNcalc168;

        /**
         * 勤怠月別項目169
         */
    @TableField("tmo_ncalc169")
        private Long tmoNcalc169;

        /**
         * 勤怠月別項目170
         */
    @TableField("tmo_ncalc170")
        private Long tmoNcalc170;

        /**
         * 勤怠月別項目171
         */
    @TableField("tmo_ncalc171")
        private Long tmoNcalc171;

        /**
         * 勤怠月別項目172
         */
    @TableField("tmo_ncalc172")
        private Long tmoNcalc172;

        /**
         * 勤怠月別項目173
         */
    @TableField("tmo_ncalc173")
        private Long tmoNcalc173;

        /**
         * 勤怠月別項目174
         */
    @TableField("tmo_ncalc174")
        private Long tmoNcalc174;

        /**
         * 勤怠月別項目175
         */
    @TableField("tmo_ncalc175")
        private Long tmoNcalc175;

        /**
         * 勤怠月別項目176
         */
    @TableField("tmo_ncalc176")
        private Long tmoNcalc176;

        /**
         * 勤怠月別項目177
         */
    @TableField("tmo_ncalc177")
        private Long tmoNcalc177;

        /**
         * 勤怠月別項目178
         */
    @TableField("tmo_ncalc178")
        private Long tmoNcalc178;

        /**
         * 勤怠月別項目179
         */
    @TableField("tmo_ncalc179")
        private Long tmoNcalc179;

        /**
         * 勤怠月別項目180
         */
    @TableField("tmo_ncalc180")
        private Long tmoNcalc180;

        /**
         * 勤怠月別項目181
         */
    @TableField("tmo_ncalc181")
        private Long tmoNcalc181;

        /**
         * 勤怠月別項目182
         */
    @TableField("tmo_ncalc182")
        private Long tmoNcalc182;

        /**
         * 勤怠月別項目183
         */
    @TableField("tmo_ncalc183")
        private Long tmoNcalc183;

        /**
         * 勤怠月別項目184
         */
    @TableField("tmo_ncalc184")
        private Long tmoNcalc184;

        /**
         * 勤怠月別項目185
         */
    @TableField("tmo_ncalc185")
        private Long tmoNcalc185;

        /**
         * 勤怠月別項目186
         */
    @TableField("tmo_ncalc186")
        private Long tmoNcalc186;

        /**
         * 勤怠月別項目187
         */
    @TableField("tmo_ncalc187")
        private Long tmoNcalc187;

        /**
         * 勤怠月別項目188
         */
    @TableField("tmo_ncalc188")
        private Long tmoNcalc188;

        /**
         * 勤怠月別項目189
         */
    @TableField("tmo_ncalc189")
        private Long tmoNcalc189;

        /**
         * 勤怠月別項目190
         */
    @TableField("tmo_ncalc190")
        private Long tmoNcalc190;

        /**
         * 勤怠月別項目191
         */
    @TableField("tmo_ncalc191")
        private Long tmoNcalc191;

        /**
         * 勤怠月別項目192
         */
    @TableField("tmo_ncalc192")
        private Long tmoNcalc192;

        /**
         * 勤怠月別項目193
         */
    @TableField("tmo_ncalc193")
        private Long tmoNcalc193;

        /**
         * 勤怠月別項目194
         */
    @TableField("tmo_ncalc194")
        private Long tmoNcalc194;

        /**
         * 勤怠月別項目195
         */
    @TableField("tmo_ncalc195")
        private Long tmoNcalc195;

        /**
         * 勤怠月別項目196
         */
    @TableField("tmo_ncalc196")
        private Long tmoNcalc196;

        /**
         * 勤怠月別項目197
         */
    @TableField("tmo_ncalc197")
        private Long tmoNcalc197;

        /**
         * 勤怠月別項目198
         */
    @TableField("tmo_ncalc198")
        private Long tmoNcalc198;

        /**
         * 勤怠月別項目199
         */
    @TableField("tmo_ncalc199")
        private Long tmoNcalc199;

        /**
         * 勤怠月別項目200
         */
    @TableField("tmo_ncalc200")
        private Long tmoNcalc200;

        /**
         * 裁量労働：勤務状態
         */
    @TableField("tmo_csts_work")
        private String tmoCstsWork;

        /**
         * 裁量労働：健康状態
         */
    @TableField("tmo_csts_health")
        private String tmoCstsHealth;

        /**
         * 予定確定
         */
    @TableField("tmo_csts_plan")
        private String tmoCstsPlan;

        /**
         * 付与後：年休繰越時間数
         */
    @TableField("tmo_npaid_cr_hours")
        private Long tmoNpaidCrHours;

        /**
         * 付与前：付与前日残日数
         */
    @TableField("tmo_npaid_bef_rest_days")
        private Long tmoNpaidBefRestDays;

        /**
         * 付与前：付与前日残時間
         */
    @TableField("tmo_npaid_bef_rest_hours")
        private Long tmoNpaidBefRestHours;

        /**
         * 喪失日数
         */
    @TableField("tmo_nlose_days")
        private Long tmoNloseDays;

        /**
         * 喪失時間数
         */
    @TableField("tmo_nlose_hours")
        private Long tmoNloseHours;


        }