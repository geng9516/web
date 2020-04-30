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
 * public.err_djnd3001
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
@TableName("err_djnd3001")
public class ErrDjnd3001DO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * ccompkb
         */
        @TableId(value = "ccompkb", type = IdType.INPUT)
        private String ccompkb;

        /**
         * dstart
         */
    @TableField("dstart")
        private Date dstart;

        /**
         * sstart
         */
    @TableField("sstart")
        private String sstart;

        /**
         * dend
         */
    @TableField("dend")
        private Date dend;

        /**
         * send
         */
    @TableField("send")
        private String send;

        /**
         * cshainno
         */
    @TableField("cshainno")
        private String cshainno;

        /**
         * key_dte
         */
    @TableField("key_dte")
        private String keyDte;

        /**
         * dkey_dte
         */
    @TableField("dkey_dte")
        private Date dkeyDte;

        /**
         * cnamekna
         */
    @TableField("cnamekna")
        private String cnamekna;

        /**
         * cnameknj
         */
    @TableField("cnameknj")
        private String cnameknj;

        /**
         * tsuxhi_kj_nme
         */
    @TableField("tsuxhi_kj_nme")
        private String tsuxhiKjNme;

        /**
         * insei_cde
         */
    @TableField("insei_cde")
        private String inseiCde;

        /**
         * insei_nme
         */
    @TableField("insei_nme")
        private String inseiNme;

        /**
         * seibetu_kbn
         */
    @TableField("seibetu_kbn")
        private String seibetuKbn;

        /**
         * seibetu_nme
         */
    @TableField("seibetu_nme")
        private String seibetuNme;

        /**
         * birth_dte
         */
    @TableField("birth_dte")
        private String birthDte;

        /**
         * dbirth_dte
         */
    @TableField("dbirth_dte")
        private Date dbirthDte;

        /**
         * age
         */
    @TableField("age")
        private Long age;

        /**
         * kyusei_nme
         */
    @TableField("kyusei_nme")
        private String kyuseiNme;

        /**
         * kaisei_dte
         */
    @TableField("kaisei_dte")
        private String kaiseiDte;

        /**
         * dkaisei_dte
         */
    @TableField("dkaisei_dte")
        private Date dkaiseiDte;

        /**
         * honseki_cde
         */
    @TableField("honseki_cde")
        private String honsekiCde;

        /**
         * honseki_nme
         */
    @TableField("honseki_nme")
        private String honsekiNme;

        /**
         * kokuseki_cde
         */
    @TableField("kokuseki_cde")
        private String kokusekiCde;

        /**
         * kokuseki_nme
         */
    @TableField("kokuseki_nme")
        private String kokusekiNme;

        /**
         * hjksyk_nnmn_cde
         */
    @TableField("hjksyk_nnmn_cde")
        private String hjksykNnmnCde;

        /**
         * hjksyk_nnmn_nme
         */
    @TableField("hjksyk_nnmn_nme")
        private String hjksykNnmnNme;

        /**
         * ninyo_dte
         */
    @TableField("ninyo_dte")
        private String ninyoDte;

        /**
         * dninyo_dte
         */
    @TableField("dninyo_dte")
        private Date dninyoDte;

        /**
         * nnki_mr_dte
         */
    @TableField("nnki_mr_dte")
        private String nnkiMrDte;

        /**
         * dnnki_mr_dte
         */
    @TableField("dnnki_mr_dte")
        private Date dnnkiMrDte;

        /**
         * tsyk_dte
         */
    @TableField("tsyk_dte")
        private String tsykDte;

        /**
         * dtsyk_dte
         */
    @TableField("dtsyk_dte")
        private Date dtsykDte;

        /**
         * hjksyk_skin_cde
         */
    @TableField("hjksyk_skin_cde")
        private String hjksykSkinCde;

        /**
         * hjksyk_skin_nme
         */
    @TableField("hjksyk_skin_nme")
        private String hjksykSkinNme;

        /**
         * htrei_sykin_dte
         */
    @TableField("htrei_sykin_dte")
        private String htreiSykinDte;

        /**
         * dhtrei_sykin_dte
         */
    @TableField("dhtrei_sykin_dte")
        private Date dhtreiSykinDte;

        /**
         * hjksyk_misy_cde
         */
    @TableField("hjksyk_misy_cde")
        private String hjksykMisyCde;

        /**
         * hjksyk_misy_nme
         */
    @TableField("hjksyk_misy_nme")
        private String hjksykMisyNme;

        /**
         * syksy_cde
         */
    @TableField("syksy_cde")
        private String syksyCde;

        /**
         * syksy_nme
         */
    @TableField("syksy_nme")
        private String syksyNme;

        /**
         * htrei_syksy_dte
         */
    @TableField("htrei_syksy_dte")
        private String htreiSyksyDte;

        /**
         * dhtrei_syksy_dte
         */
    @TableField("dhtrei_syksy_dte")
        private Date dhtreiSyksyDte;

        /**
         * tsj_skmei_cde
         */
    @TableField("tsj_skmei_cde")
        private String tsjSkmeiCde;

        /**
         * tsj_skmei_nme
         */
    @TableField("tsj_skmei_nme")
        private String tsjSkmeiNme;

        /**
         * kkn_cde
         */
    @TableField("kkn_cde")
        private String kknCde;

        /**
         * kkn_nme
         */
    @TableField("kkn_nme")
        private String kknNme;

        /**
         * htrei_kkn_dte
         */
    @TableField("htrei_kkn_dte")
        private String htreiKknDte;

        /**
         * dhtrei_kkn_dte
         */
    @TableField("dhtrei_kkn_dte")
        private Date dhtreiKknDte;

        /**
         * szk_cde
         */
    @TableField("szk_cde")
        private String szkCde;

        /**
         * szk_nme
         */
    @TableField("szk_nme")
        private String szkNme;

        /**
         * bkyk_cde
         */
    @TableField("bkyk_cde")
        private String bkykCde;

        /**
         * bkyk_nme
         */
    @TableField("bkyk_nme")
        private String bkykNme;

        /**
         * sort_jyn
         */
    @TableField("sort_jyn")
        private Long sortJyn;

        /**
         * htrei_syzk_dte
         */
    @TableField("htrei_syzk_dte")
        private String htreiSyzkDte;

        /**
         * dhtrei_syzk_dte
         */
    @TableField("dhtrei_syzk_dte")
        private Date dhtreiSyzkDte;

        /**
         * kkrkoza_cde
         */
    @TableField("kkrkoza_cde")
        private String kkrkozaCde;

        /**
         * kkrkoza_nme
         */
    @TableField("kkrkoza_nme")
        private String kkrkozaNme;

        /**
         * htrei_kk_dte
         */
    @TableField("htrei_kk_dte")
        private String htreiKkDte;

        /**
         * dhtrei_kk_dte
         */
    @TableField("dhtrei_kk_dte")
        private Date dhtreiKkDte;

        /**
         * bku_syzk
         */
    @TableField("bku_syzk")
        private String bkuSyzk;

        /**
         * zisyk_nen
         */
    @TableField("zisyk_nen")
        private Long zisykNen;

        /**
         * hjksyk_kih_cde
         */
    @TableField("hjksyk_kih_cde")
        private String hjksykKihCde;

        /**
         * hjksyk_kih_nme
         */
    @TableField("hjksyk_kih_nme")
        private String hjksykKihNme;

        /**
         * hjksyk_kmtkb_cde
         */
    @TableField("hjksyk_kmtkb_cde")
        private String hjksykKmtkbCde;

        /**
         * hjksyk_kmtkb_nme
         */
    @TableField("hjksyk_kmtkb_nme")
        private String hjksykKmtkbNme;

        /**
         * hkhy_cde
         */
    @TableField("hkhy_cde")
        private String hkhyCde;

        /**
         * hkhy_nme
         */
    @TableField("hkhy_nme")
        private String hkhyNme;

        /**
         * kjynst_kyu
         */
    @TableField("kjynst_kyu")
        private Long kjynstKyu;

        /**
         * kjynst_goho
         */
    @TableField("kjynst_goho")
        private Long kjynstGoho;

        /**
         * kjynst_tanka
         */
    @TableField("kjynst_tanka")
        private Long kjynstTanka;

        /**
         * kngkutkr_kbn
         */
    @TableField("kngkutkr_kbn")
        private String kngkutkrKbn;

        /**
         * tysi_sum
         */
    @TableField("tysi_sum")
        private Long tysiSum;

        /**
         * tsta_skr
         */
    @TableField("tsta_skr")
        private Long tstaSkr;

        /**
         * skyust_kyu
         */
    @TableField("skyust_kyu")
        private Long skyustKyu;

        /**
         * skyust_goho
         */
    @TableField("skyust_goho")
        private Long skyustGoho;

        /**
         * skyust_tanka
         */
    @TableField("skyust_tanka")
        private Long skyustTanka;

        /**
         * htreingb_dte
         */
    @TableField("htreingb_dte")
        private String htreingbDte;

        /**
         * dhtreingb_dte
         */
    @TableField("dhtreingb_dte")
        private Date dhtreingbDte;

        /**
         * gkrk_kyuy_cde
         */
    @TableField("gkrk_kyuy_cde")
        private String gkrkKyuyCde;

        /**
         * gkrk_kyuy_nme
         */
    @TableField("gkrk_kyuy_nme")
        private String gkrkKyuyNme;

        /**
         * get_kyuy_dte
         */
    @TableField("get_kyuy_dte")
        private String getKyuyDte;

        /**
         * dget_kyuy_dte
         */
    @TableField("dget_kyuy_dte")
        private Date dgetKyuyDte;

        /**
         * gnknsyk_1
         */
    @TableField("gnknsyk_1")
        private String gnknsyk1;

        /**
         * gnknsyk_2
         */
    @TableField("gnknsyk_2")
        private String gnknsyk2;

        /**
         * gnknsyk_3
         */
    @TableField("gnknsyk_3")
        private String gnknsyk3;

        /**
         * gnknsyk_4
         */
    @TableField("gnknsyk_4")
        private String gnknsyk4;

        /**
         * idosk_cde
         */
    @TableField("idosk_cde")
        private String idoskCde;

        /**
         * idosk_nme
         */
    @TableField("idosk_nme")
        private String idoskNme;

        /**
         * kjyngkrk_cde
         */
    @TableField("kjyngkrk_cde")
        private String kjyngkrkCde;

        /**
         * kjyngkrk_nme
         */
    @TableField("kjyngkrk_nme")
        private String kjyngkrkNme;

        /**
         * nnyujkkn_nen
         */
    @TableField("nnyujkkn_nen")
        private Long nnyujkknNen;

        /**
         * tyusikkn_nen
         */
    @TableField("tyusikkn_nen")
        private Long tyusikknNen;

        /**
         * synky_hokyu_cde
         */
    @TableField("synky_hokyu_cde")
        private String synkyHokyuCde;

        /**
         * synky_hokyu
         */
    @TableField("synky_hokyu")
        private String synkyHokyu;

        /**
         * synky_kyu
         */
    @TableField("synky_kyu")
        private Long synkyKyu;

        /**
         * synky_goho
         */
    @TableField("synky_goho")
        private Long synkyGoho;

        /**
         * tisyktat_gaku
         */
    @TableField("tisyktat_gaku")
        private Long tisyktatGaku;

        /**
         * hky_ggk
         */
    @TableField("hky_ggk")
        private Long hkyGgk;

        /**
         * hky_tysi_gk
         */
    @TableField("hky_tysi_gk")
        private Long hkyTysiGk;

        /**
         * knsyzi_cde
         */
    @TableField("knsyzi_cde")
        private String knsyziCde;

        /**
         * knsyzi_nme
         */
    @TableField("knsyzi_nme")
        private String knsyziNme;

        /**
         * tukntat_gaku
         */
    @TableField("tukntat_gaku")
        private Long tukntatGaku;

        /**
         * jukytat_gaku
         */
    @TableField("jukytat_gaku")
        private Long jukytatGaku;

        /**
         * kosin_use
         */
    @TableField("kosin_use")
        private String kosinUse;

        /**
         * last_pgm
         */
    @TableField("last_pgm")
        private String lastPgm;

        /**
         * last_dte
         */
    @TableField("last_dte")
        private Date lastDte;

        /**
         * kosin_kbn
         */
    @TableField("kosin_kbn")
        private String kosinKbn;

        /**
         * syogo_cde
         */
    @TableField("syogo_cde")
        private String syogoCde;

        /**
         * syogo_nme
         */
    @TableField("syogo_nme")
        private String syogoNme;

        /**
         * gakuseki_no
         */
    @TableField("gakuseki_no")
        private String gakusekiNo;

        /**
         * honmu_cde
         */
    @TableField("honmu_cde")
        private String honmuCde;

        /**
         * honmu_nme
         */
    @TableField("honmu_nme")
        private String honmuNme;

        /**
         * honmusaki_nme
         */
    @TableField("honmusaki_nme")
        private String honmusakiNme;

        /**
         * kinmu_cde
         */
    @TableField("kinmu_cde")
        private String kinmuCde;

        /**
         * kinmu_nme
         */
    @TableField("kinmu_nme")
        private String kinmuNme;

        /**
         * kyu_kn_nme
         */
    @TableField("kyu_kn_nme")
        private String kyuKnNme;

        /**
         * kyu_kj_nme
         */
    @TableField("kyu_kj_nme")
        private String kyuKjNme;

        /**
         * nme_prt_flg
         */
    @TableField("nme_prt_flg")
        private String nmePrtFlg;

        /**
         * kyu_prt_flg
         */
    @TableField("kyu_prt_flg")
        private String kyuPrtFlg;

        /**
         * kyuyo_kojin_cde
         */
    @TableField("kyuyo_kojin_cde")
        private String kyuyoKojinCde;

        /**
         * yosan_cde_2
         */
    @TableField("yosan_cde_2")
        private String yosanCde2;

        /**
         * yosan_nme_2
         */
    @TableField("yosan_nme_2")
        private String yosanNme2;

        /**
         * yosan_cde_3
         */
    @TableField("yosan_cde_3")
        private String yosanCde3;

        /**
         * yosan_nme_3
         */
    @TableField("yosan_nme_3")
        private String yosanNme3;

        /**
         * cmnuser
         */
    @TableField("cmnuser")
        private String cmnuser;

        /**
         * dmndate
         */
    @TableField("dmndate")
        private Date dmndate;

        /**
         * mae_nnyo_ksnen
         */
    @TableField("mae_nnyo_ksnen")
        private Long maeNnyoKsnen;

        /**
         * c01_cde
         */
    @TableField("c01_cde")
        private String c01Cde;

        /**
         * c01_nme
         */
    @TableField("c01_nme")
        private String c01Nme;

        /**
         * c02_cde
         */
    @TableField("c02_cde")
        private String c02Cde;

        /**
         * c02_nme
         */
    @TableField("c02_nme")
        private String c02Nme;

        /**
         * c03_cde
         */
    @TableField("c03_cde")
        private String c03Cde;

        /**
         * c03_nme
         */
    @TableField("c03_nme")
        private String c03Nme;

        /**
         * c04_cde
         */
    @TableField("c04_cde")
        private String c04Cde;

        /**
         * c04_nme
         */
    @TableField("c04_nme")
        private String c04Nme;

        /**
         * c05_cde
         */
    @TableField("c05_cde")
        private String c05Cde;

        /**
         * c05_nme
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
         * c01_dte
         */
    @TableField("c01_dte")
        private String c01Dte;

        /**
         * d01_dte
         */
    @TableField("d01_dte")
        private Date d01Dte;

        /**
         * c02_dte
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
         * adrss
         */
    @TableField("adrss")
        private String adrss;

        /**
         * cyubin
         */
    @TableField("cyubin")
        private String cyubin;

        /**
         * zairyu_cde
         */
    @TableField("zairyu_cde")
        private String zairyuCde;

        /**
         * zairyu_nme
         */
    @TableField("zairyu_nme")
        private String zairyuNme;

        /**
         * zairyu_kgn_dte
         */
    @TableField("zairyu_kgn_dte")
        private String zairyuKgnDte;

        /**
         * dzairyu_kgn_dte
         */
    @TableField("dzairyu_kgn_dte")
        private Date dzairyuKgnDte;

        /**
         * sikakugai_dte
         */
    @TableField("sikakugai_dte")
        private String sikakugaiDte;

        /**
         * dsikakugai_dte
         */
    @TableField("dsikakugai_dte")
        private Date dsikakugaiDte;

        /**
         * cerrmsg
         */
    @TableField("cerrmsg")
        private String cerrmsg;


        }