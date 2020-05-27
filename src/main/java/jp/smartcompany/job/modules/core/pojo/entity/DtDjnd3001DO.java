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
 * 非常勤職員基本情報db                   20030711 idx_2作成
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
@TableName("dt_djnd3001")
public class DtDjnd3001DO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 会社区分
         */
        @TableId(value="ccompkb",type = IdType.INPUT)
        private String ccompkb;

        /**
         * 開始日
         */
    @TableField("dstart")
        private Date dstart;

        /**
         * 開始日（和暦）
         */
    @TableField("sstart")
        private String sstart;

        /**
         * 終了日
         */
    @TableField("dend")
        private Date dend;

        /**
         * 終了日（和暦）
         */
    @TableField("send")
        private String send;

        /**
         * 職員番号
         */
                private String cshainno;

        /**
         * 基準年月日
         */
    @TableField("key_dte")
        private String keyDte;

        /**
         * 基準年月日（西暦）
         */
    @TableField("dkey_dte")
        private Date dkeyDte;

        /**
         * カナ氏名
         */
    @TableField("cnamekna")
        private String cnamekna;

        /**
         * 漢字氏名
         */
    @TableField("cnameknj")
        private String cnameknj;

        /**
         * 通知書用漢字氏名
         */
    @TableField("tsuxhi_kj_nme")
        private String tsuxhiKjNme;

        /**
         * 院生区分                          京都追加
         */
    @TableField("insei_cde")
        private String inseiCde;

        /**
         * 院生区分名称                        京都追加
         */
    @TableField("insei_nme")
        private String inseiNme;

        /**
         * 性別区分
         */
    @TableField("seibetu_kbn")
        private String seibetuKbn;

        /**
         * 性別
         */
    @TableField("seibetu_nme")
        private String seibetuNme;

        /**
         * 生年月日
         */
    @TableField("birth_dte")
        private String birthDte;

        /**
         * 生年月日（西暦）
         */
    @TableField("dbirth_dte")
        private Date dbirthDte;

        /**
         * 年齢
         */
    @TableField("age")
        private Long age;

        /**
         * 旧姓
         */
    @TableField("kyusei_nme")
        private String kyuseiNme;

        /**
         * 改姓年月日
         */
    @TableField("kaisei_dte")
        private String kaiseiDte;

        /**
         * 改姓年月日（西暦）
         */
    @TableField("dkaisei_dte")
        private Date dkaiseiDte;

        /**
         * 本籍コード
         */
    @TableField("honseki_cde")
        private String honsekiCde;

        /**
         * 本籍
         */
    @TableField("honseki_nme")
        private String honsekiNme;

        /**
         * 国籍区分コード
         */
    @TableField("kokuseki_cde")
        private String kokusekiCde;

        /**
         * 国籍区分
         */
    @TableField("kokuseki_nme")
        private String kokusekiNme;

        /**
         * 非常勤職員任免コード
         */
    @TableField("hjksyk_nnmn_cde")
        private String hjksykNnmnCde;

        /**
         * 非常勤職員任免
         */
    @TableField("hjksyk_nnmn_nme")
        private String hjksykNnmnNme;

        /**
         * 任用年月日
         */
    @TableField("ninyo_dte")
        private String ninyoDte;

        /**
         * 任用年月日（西暦）
         */
    @TableField("dninyo_dte")
        private Date dninyoDte;

        /**
         * 任期満了年月日
         */
    @TableField("nnki_mr_dte")
        private String nnkiMrDte;

        /**
         * 任期満了年月日（西暦）
         */
    @TableField("dnnki_mr_dte")
        private Date dnnkiMrDte;

        /**
         * 退職等年月日
         */
    @TableField("tsyk_dte")
        private String tsykDte;

        /**
         * 退職等年月日（西暦）
         */
    @TableField("dtsyk_dte")
        private Date dtsykDte;

        /**
         * 非常勤職員職員コード
         */
    @TableField("hjksyk_skin_cde")
        private String hjksykSkinCde;

        /**
         * 非常勤職員職員
         */
    @TableField("hjksyk_skin_nme")
        private String hjksykSkinNme;

        /**
         * 発令年月日（職員区分）
         */
    @TableField("htrei_sykin_dte")
        private String htreiSykinDte;

        /**
         * 発令年月日（職員区分）
         */
    @TableField("dhtrei_sykin_dte")
        private Date dhtreiSykinDte;

        /**
         * 非常勤職員名称コード
         */
    @TableField("hjksyk_misy_cde")
        private String hjksykMisyCde;

        /**
         * 非常勤職員名称
         */
    @TableField("hjksyk_misy_nme")
        private String hjksykMisyNme;

        /**
         * 職種コード
         */
    @TableField("syksy_cde")
        private String syksyCde;

        /**
         * 職種
         */
    @TableField("syksy_nme")
        private String syksyNme;

        /**
         * 発令年月日（職種）
         */
    @TableField("htrei_syksy_dte")
        private String htreiSyksyDte;

        /**
         * 発令年月日（職種） 西暦
         */
    @TableField("dhtrei_syksy_dte")
        private Date dhtreiSyksyDte;

        /**
         * 定数上の職名コード                     2001.03.30(i)
         */
    @TableField("tsj_skmei_cde")
        private String tsjSkmeiCde;

        /**
         * 定数上の職名                        2001.03.30(i)
         */
    @TableField("tsj_skmei_nme")
        private String tsjSkmeiNme;

        /**
         * 機関コード
         */
    @TableField("kkn_cde")
        private String kknCde;

        /**
         * 機関
         */
    @TableField("kkn_nme")
        private String kknNme;

        /**
         * 発令年月日（機関）
         */
    @TableField("htrei_kkn_dte")
        private String htreiKknDte;

        /**
         * 発令年月日（機関） 西暦
         */
    @TableField("dhtrei_kkn_dte")
        private Date dhtreiKknDte;

        /**
         * 所属コード
         */
    @TableField("szk_cde")
        private String szkCde;

        /**
         * 所属
         */
    @TableField("szk_nme")
        private String szkNme;

        /**
         * 部局コード
         */
    @TableField("bkyk_cde")
        private String bkykCde;

        /**
         * 部局
         */
    @TableField("bkyk_nme")
        private String bkykNme;

        /**
         * ソート順
         */
    @TableField("sort_jyn")
        private Long sortJyn;

        /**
         * 発令年月日（所属）
         */
    @TableField("htrei_syzk_dte")
        private String htreiSyzkDte;

        /**
         * 発令年月日（所属） 西暦
         */
    @TableField("dhtrei_syzk_dte")
        private Date dhtreiSyzkDte;

        /**
         * 係・講座コード
         */
    @TableField("kkrkoza_cde")
        private String kkrkozaCde;

        /**
         * 係・講座
         */
    @TableField("kkrkoza_nme")
        private String kkrkozaNme;

        /**
         * 発令年月日（係・講座）
         */
    @TableField("htrei_kk_dte")
        private String htreiKkDte;

        /**
         * 発令年月日（係・講座）
         */
    @TableField("dhtrei_kk_dte")
        private Date dhtreiKkDte;

        /**
         * 備考欄用所属
         */
    @TableField("bku_syzk")
        private String bkuSyzk;

        /**
         * 在職年数
         */
    @TableField("zisyk_nen")
        private Long zisykNen;

        /**
         * 非常勤職員経費コード
         */
    @TableField("hjksyk_kih_cde")
        private String hjksykKihCde;

        /**
         * 非常勤職員経費
         */
    @TableField("hjksyk_kih_nme")
        private String hjksykKihNme;

        /**
         * 期末勤勉区分コード
         */
    @TableField("hjksyk_kmtkb_cde")
        private String hjksykKmtkbCde;

        /**
         * 期末勤勉区分
         */
    @TableField("hjksyk_kmtkb_nme")
        private String hjksykKmtkbNme;

        /**
         * 俸給表コード
         */
    @TableField("hkhy_cde")
        private String hkhyCde;

        /**
         * 俸給表
         */
    @TableField("hkhy_nme")
        private String hkhyNme;

        /**
         * 基準相当級
         */
    @TableField("kjynst_kyu")
        private Long kjynstKyu;

        /**
         * 基準相当号俸
         */
    @TableField("kjynst_goho")
        private Long kjynstGoho;

        /**
         * 基準相当単価(日給･時給)
         */
    @TableField("kjynst_tanka")
        private Long kjynstTanka;

        /**
         * 金額打切区分
         */
    @TableField("kngkutkr_kbn")
        private String kngkutkrKbn;

        /**
         * 調整数合計
         */
    @TableField("tysi_sum")
        private Long tysiSum;

        /**
         * 調整手当支給率
         */
    @TableField("tsta_skr")
        private Long tstaSkr;

        /**
         * 支給相当級
         */
    @TableField("skyust_kyu")
        private Long skyustKyu;

        /**
         * 支給相当号俸
         */
    @TableField("skyust_goho")
        private Long skyustGoho;

        /**
         * 支給相当単価(日給･時給)
         */
    @TableField("skyust_tanka")
        private Long skyustTanka;

        /**
         * 発令年月日
         */
    @TableField("htreingb_dte")
        private String htreingbDte;

        /**
         * 発令年月日（西暦）
         */
    @TableField("dhtreingb_dte")
        private Date dhtreingbDte;

        /**
         * 学歴区分ｺｰﾄﾞ（給与決定）
         */
    @TableField("gkrk_kyuy_cde")
        private String gkrkKyuyCde;

        /**
         * 学歴区分（給与決定）
         */
    @TableField("gkrk_kyuy_nme")
        private String gkrkKyuyNme;

        /**
         * 取得年月日（給与決定）
         */
    @TableField("get_kyuy_dte")
        private String getKyuyDte;

        /**
         * 取得年月日（給与決定）
         */
    @TableField("dget_kyuy_dte")
        private Date dgetKyuyDte;

        /**
         * 現（元）官職欄(1)
         */
    @TableField("gnknsyk_1")
        private String gnknsyk1;

        /**
         * 現（元）官職欄(2)
         */
    @TableField("gnknsyk_2")
        private String gnknsyk2;

        /**
         * 現（元）官職欄(3)
         */
    @TableField("gnknsyk_3")
        private String gnknsyk3;

        /**
         * 現（元）官職欄(4)
         */
    @TableField("gnknsyk_4")
        private String gnknsyk4;

        /**
         * 退職等後の異動先区分                    京都追加
         */
    @TableField("idosk_cde")
        private String idoskCde;

        /**
         * 退職等後の異動先名称                    京都追加
         */
    @TableField("idosk_nme")
        private String idoskNme;

        /**
         * 基準学歴区分コード
         */
    @TableField("kjyngkrk_cde")
        private String kjyngkrkCde;

        /**
         * 基準学歴区分
         */
    @TableField("kjyngkrk_nme")
        private String kjyngkrkNme;

        /**
         * 任用時経験年数
         */
    @TableField("nnyujkkn_nen")
        private Long nnyujkknNen;

        /**
         * 調整経験年数
         */
    @TableField("tyusikkn_nen")
        private Long tyusikknNen;

        /**
         * (12条)俸給表コード
         */
    @TableField("synky_hokyu_cde")
        private String synkyHokyuCde;

        /**
         * (12条)俸給表
         */
    @TableField("synky_hokyu")
        private String synkyHokyu;

        /**
         * (12条)級
         */
    @TableField("synky_kyu")
        private Long synkyKyu;

        /**
         * (12条)号俸
         */
    @TableField("synky_goho")
        private Long synkyGoho;

        /**
         * 退職手当額
         */
    @TableField("tisyktat_gaku")
        private Long tisyktatGaku;

        /**
         * 俸給月額
         */
    @TableField("hky_ggk")
        private Long hkyGgk;

        /**
         * 俸給の調整額
         */
    @TableField("hky_tysi_gk")
        private Long hkyTysiGk;

        /**
         * 調整手当官署所在地区分
         */
    @TableField("knsyzi_cde")
        private String knsyziCde;

        /**
         * 調整手当官署所在地区分
         */
    @TableField("knsyzi_nme")
        private String knsyziNme;

        /**
         * 通勤手当支給額
         */
    @TableField("tukntat_gaku")
        private Long tukntatGaku;

        /**
         * 住居手当支給額
         */
    @TableField("jukytat_gaku")
        private Long jukytatGaku;

        /**
         * 更新者
         */
    @TableField("kosin_use")
        private String kosinUse;

        /**
         * 更新pgm
         */
    @TableField("last_pgm")
        private String lastPgm;

        /**
         * 更新日時
         */
    @TableField("last_dte")
        private Date lastDte;

        /**
         * 更新区分
         */
    @TableField("kosin_kbn")
        private String kosinKbn;

        /**
         * 称号区分                          2001.06.11追加
         */
    @TableField("syogo_cde")
        private String syogoCde;

        /**
         * 称号                            2001.06.11追加
         */
    @TableField("syogo_nme")
        private String syogoNme;

        /**
         * 学籍番号                          2001.06.11追加
         */
    @TableField("gakuseki_no")
        private String gakusekiNo;

        /**
         * 本務区分コード
         */
    @TableField("honmu_cde")
        private String honmuCde;

        /**
         * 本務区分
         */
    @TableField("honmu_nme")
        private String honmuNme;

        /**
         * 本務先
         */
    @TableField("honmusaki_nme")
        private String honmusakiNme;

        /**
         * 勤務形態区分コード
         */
    @TableField("kinmu_cde")
        private String kinmuCde;

        /**
         * 勤務形態区分
         */
    @TableField("kinmu_nme")
        private String kinmuNme;

        /**
         * 旧姓使用カナ氏名                      2001.10.22 様式変更
         */
    @TableField("kyu_kn_nme")
        private String kyuKnNme;

        /**
         * 旧姓使用漢字氏名                      2001.10.22 様式変更
         */
    @TableField("kyu_kj_nme")
        private String kyuKjNme;

        /**
         * 漢字氏名印字無フラグ                    2001.10.22 様式変更 (1:印字無)
         */
    @TableField("nme_prt_flg")
        private String nmePrtFlg;

        /**
         * 旧姓印字無フラグ                      2001.11.16 追加
         */
    @TableField("kyu_prt_flg")
        private String kyuPrtFlg;

        /**
         * 給与個人番号                        2002.03.11 追加
         */
    @TableField("kyuyo_kojin_cde")
        private String kyuyoKojinCde;

        /**
         * 予算項コード２
         */
    @TableField("yosan_cde_2")
        private String yosanCde2;

        /**
         * 予算項２
         */
    @TableField("yosan_nme_2")
        private String yosanNme2;

        /**
         * 予算項コード３
         */
    @TableField("yosan_cde_3")
        private String yosanCde3;

        /**
         * 予算項３
         */
    @TableField("yosan_nme_3")
        private String yosanNme3;

        /**
         * ﾕｰｻﾞｰ
         */
    @TableField("cmnuser")
        private String cmnuser;

        /**
         * 更新日
         */
    @TableField("dmndate")
        private Date dmndate;

        /**
         * mae_nnyo_ksnen
         */
    @TableField("mae_nnyo_ksnen")
        private Long maeNnyoKsnen;

        /**
         * 更新の有無コード
         */
    @TableField("c01_cde")
        private String c01Cde;

        /**
         * 更新の有無
         */
    @TableField("c01_nme")
        private String c01Nme;

        /**
         * 就業の場所コード
         */
    @TableField("c02_cde")
        private String c02Cde;

        /**
         * 就業の場所
         */
    @TableField("c02_nme")
        private String c02Nme;

        /**
         * 通勤手当相当給与該当コード
         */
    @TableField("c03_cde")
        private String c03Cde;

        /**
         * 通勤手当相当給与該当
         */
    @TableField("c03_nme")
        private String c03Nme;

        /**
         * 規定改正による単価改訂コード
         */
    @TableField("c04_cde")
        private String c04Cde;

        /**
         * 規定改正による単価改訂
         */
    @TableField("c04_nme")
        private String c04Nme;

        /**
         * 任期途中見直し該当コード
         */
    @TableField("c05_cde")
        private String c05Cde;

        /**
         * 任期途中見直し該当
         */
    @TableField("c05_nme")
        private String c05Nme;

        /**
         * c06_cde
         */
    @TableField("c06_cde")
        private String c06Cde;

        /**
         * c06_nme
         */
    @TableField("c06_nme")
        private String c06Nme;

        /**
         * c07_cde
         */
    @TableField("c07_cde")
        private String c07Cde;

        /**
         * c07_nme
         */
    @TableField("c07_nme")
        private String c07Nme;

        /**
         * c08_cde
         */
    @TableField("c08_cde")
        private String c08Cde;

        /**
         * c08_nme
         */
    @TableField("c08_nme")
        private String c08Nme;

        /**
         * c09_cde
         */
    @TableField("c09_cde")
        private String c09Cde;

        /**
         * c09_nme
         */
    @TableField("c09_nme")
        private String c09Nme;

        /**
         * c10_cde
         */
    @TableField("c10_cde")
        private String c10Cde;

        /**
         * c10_nme
         */
    @TableField("c10_nme")
        private String c10Nme;

        /**
         * 単価改訂日
         */
    @TableField("c01_dte")
        private String c01Dte;

        /**
         * d01_dte
         */
    @TableField("d01_dte")
        private Date d01Dte;

        /**
         * 当初雇用年月日
         */
    @TableField("c02_dte")
        private String c02Dte;

        /**
         * d02_dte
         */
    @TableField("d02_dte")
        private Date d02Dte;

        /**
         * c03_dte
         */
    @TableField("c03_dte")
        private String c03Dte;

        /**
         * d03_dte
         */
    @TableField("d03_dte")
        private Date d03Dte;

        /**
         * c04_dte
         */
    @TableField("c04_dte")
        private String c04Dte;

        /**
         * d04_dte
         */
    @TableField("d04_dte")
        private Date d04Dte;

        /**
         * c05_dte
         */
    @TableField("c05_dte")
        private String c05Dte;

        /**
         * d05_dte
         */
    @TableField("d05_dte")
        private Date d05Dte;

        /**
         * 住所
         */
    @TableField("adrss")
        private String adrss;

        /**
         * 郵便番号
         */
    @TableField("cyubin")
        private String cyubin;

        /**
         * 在留資格コード
         */
    @TableField("zairyu_cde")
        private String zairyuCde;

        /**
         * 在留資格
         */
    @TableField("zairyu_nme")
        private String zairyuNme;

        /**
         * 在留期限
         */
    @TableField("zairyu_kgn_dte")
        private String zairyuKgnDte;

        /**
         * 在留期限（西暦）
         */
    @TableField("dzairyu_kgn_dte")
        private Date dzairyuKgnDte;

        /**
         * 資格外活動許可の期限日
         */
    @TableField("sikakugai_dte")
        private String sikakugaiDte;

        /**
         * 資格外活動許可の期限日（西暦）
         */
    @TableField("dsikakugai_dte")
        private Date dsikakugaiDte;


        }