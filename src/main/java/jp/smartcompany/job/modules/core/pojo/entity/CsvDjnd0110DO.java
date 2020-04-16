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
 * public.csv_djnd0110
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
@TableName("csv_djnd0110")
public class CsvDjnd0110DO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * ccompkb
         */
    @TableField("ccompkb")
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
         * ndm_age
         */
    @TableField("ndm_age")
        private Long ndmAge;

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
         * saiyo_dte
         */
    @TableField("saiyo_dte")
        private String saiyoDte;

        /**
         * dsaiyo_dte
         */
    @TableField("dsaiyo_dte")
        private Date dsaiyoDte;

        /**
         * saiyo_r_dte
         */
    @TableField("saiyo_r_dte")
        private String saiyoRDte;

        /**
         * dsaiyo_r_dte
         */
    @TableField("dsaiyo_r_dte")
        private Date dsaiyoRDte;

        /**
         * nwkkn_htrei_dte
         */
    @TableField("nwkkn_htrei_dte")
        private String nwkknHtreiDte;

        /**
         * dnwkkn_htrei_dte
         */
    @TableField("dnwkkn_htrei_dte")
        private Date dnwkknHtreiDte;

        /**
         * kinmsaki_cde
         */
    @TableField("kinmsaki_cde")
        private String kinmsakiCde;

        /**
         * kinmsaki_nme
         */
    @TableField("kinmsaki_nme")
        private String kinmsakiNme;

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
         * nnmn_cde
         */
    @TableField("nnmn_cde")
        private String nnmnCde;

        /**
         * nnmn_nme
         */
    @TableField("nnmn_nme")
        private String nnmnNme;

        /**
         * tsyk_ido_cde
         */
    @TableField("tsyk_ido_cde")
        private String tsykIdoCde;

        /**
         * tsyk_ido_nme
         */
    @TableField("tsyk_ido_nme")
        private String tsykIdoNme;

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
         * tsykgo_cde
         */
    @TableField("tsykgo_cde")
        private String tsykgoCde;

        /**
         * tsykgo_nme
         */
    @TableField("tsykgo_nme")
        private String tsykgoNme;

        /**
         * gyo2_cde
         */
    @TableField("gyo2_cde")
        private String gyo2Cde;

        /**
         * gyo2_nme
         */
    @TableField("gyo2_nme")
        private String gyo2Nme;

        /**
         * tikbn_cde
         */
    @TableField("tikbn_cde")
        private String tikbnCde;

        /**
         * tikbn_nme
         */
    @TableField("tikbn_nme")
        private String tikbnNme;

        /**
         * kmkbn_cde
         */
    @TableField("kmkbn_cde")
        private String kmkbnCde;

        /**
         * kmkbn_nme
         */
    @TableField("kmkbn_nme")
        private String kmkbnNme;

        /**
         * exp_num
         */
    @TableField("exp_num")
        private Long expNum;

        /**
         * zikn_num
         */
    @TableField("zikn_num")
        private Long ziknNum;

        /**
         * tsyktat_num
         */
    @TableField("tsyktat_num")
        private Long tsyktatNum;

        /**
         * k_skin_cde
         */
    @TableField("k_skin_cde")
        private String kSkinCde;

        /**
         * k_skin_nme
         */
    @TableField("k_skin_nme")
        private String kSkinNme;

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
         * rinj_hiditi_cde
         */
    @TableField("rinj_hiditi_cde")
        private String rinjHiditiCde;

        /**
         * rinj_hiditi_nme
         */
    @TableField("rinj_hiditi_nme")
        private String rinjHiditiNme;

        /**
         * bg_ed_dte
         */
    @TableField("bg_ed_dte")
        private String bgEdDte;

        /**
         * dbg_ed_dte
         */
    @TableField("dbg_ed_dte")
        private Date dbgEdDte;

        /**
         * bg_sk_ritu
         */
    @TableField("bg_sk_ritu")
        private String bgSkRitu;

        /**
         * bg_kkn_cde
         */
    @TableField("bg_kkn_cde")
        private String bgKknCde;

        /**
         * bg_kkn_nme
         */
    @TableField("bg_kkn_nme")
        private String bgKknNme;

        /**
         * bg_hk_cde
         */
    @TableField("bg_hk_cde")
        private String bgHkCde;

        /**
         * bg_hk_nme
         */
    @TableField("bg_hk_nme")
        private String bgHkNme;

        /**
         * gk_knmei_cde
         */
    @TableField("gk_knmei_cde")
        private String gkKnmeiCde;

        /**
         * gk_knmei_nme
         */
    @TableField("gk_knmei_nme")
        private String gkKnmeiNme;

        /**
         * gk_hkyh_cde
         */
    @TableField("gk_hkyh_cde")
        private String gkHkyhCde;

        /**
         * gk_hkyh_nme
         */
    @TableField("gk_hkyh_nme")
        private String gkHkyhNme;

        /**
         * gk_kyu
         */
    @TableField("gk_kyu")
        private Long gkKyu;

        /**
         * gk_szk
         */
    @TableField("gk_szk")
        private String gkSzk;

        /**
         * nnmn_ido_cde
         */
    @TableField("nnmn_ido_cde")
        private String nnmnIdoCde;

        /**
         * nnmn_ido_nme
         */
    @TableField("nnmn_ido_nme")
        private String nnmnIdoNme;

        /**
         * nnmn_htrei_dte
         */
    @TableField("nnmn_htrei_dte")
        private String nnmnHtreiDte;

        /**
         * dnnmn_htrei_dte
         */
    @TableField("dnnmn_htrei_dte")
        private Date dnnmnHtreiDte;

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
         * kkn_ido_cde
         */
    @TableField("kkn_ido_cde")
        private String kknIdoCde;

        /**
         * kkn_ido_nme
         */
    @TableField("kkn_ido_nme")
        private String kknIdoNme;

        /**
         * knmei_cde
         */
    @TableField("knmei_cde")
        private String knmeiCde;

        /**
         * knmei_nme
         */
    @TableField("knmei_nme")
        private String knmeiNme;

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
         * syksy_htrei_dte
         */
    @TableField("syksy_htrei_dte")
        private String syksyHtreiDte;

        /**
         * dsyksy_htrei_dte
         */
    @TableField("dsyksy_htrei_dte")
        private Date dsyksyHtreiDte;

        /**
         * syksy_num
         */
    @TableField("syksy_num")
        private Long syksyNum;

        /**
         * syksy_ido_cde
         */
    @TableField("syksy_ido_cde")
        private String syksyIdoCde;

        /**
         * syksy_ido_nme
         */
    @TableField("syksy_ido_nme")
        private String syksyIdoNme;

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
         * yosan_cde
         */
    @TableField("yosan_cde")
        private String yosanCde;

        /**
         * yosan_nme
         */
    @TableField("yosan_nme")
        private String yosanNme;

        /**
         * yosan_htrei_dte
         */
    @TableField("yosan_htrei_dte")
        private String yosanHtreiDte;

        /**
         * dyosan_htrei_dte
         */
    @TableField("dyosan_htrei_dte")
        private Date dyosanHtreiDte;

        /**
         * yosan_ido_cde
         */
    @TableField("yosan_ido_cde")
        private String yosanIdoCde;

        /**
         * yosan_ido_nme
         */
    @TableField("yosan_ido_nme")
        private String yosanIdoNme;

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
         * szk_htrei_dte
         */
    @TableField("szk_htrei_dte")
        private String szkHtreiDte;

        /**
         * dszk_htrei_dte
         */
    @TableField("dszk_htrei_dte")
        private Date dszkHtreiDte;

        /**
         * szk_num
         */
    @TableField("szk_num")
        private Long szkNum;

        /**
         * szk_ido_cde
         */
    @TableField("szk_ido_cde")
        private String szkIdoCde;

        /**
         * szk_ido_nme
         */
    @TableField("szk_ido_nme")
        private String szkIdoNme;

        /**
         * kei_cde
         */
    @TableField("kei_cde")
        private String keiCde;

        /**
         * kei_nme
         */
    @TableField("kei_nme")
        private String keiNme;

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
         * kei_htrei_dte
         */
    @TableField("kei_htrei_dte")
        private String keiHtreiDte;

        /**
         * dkei_htrei_dte
         */
    @TableField("dkei_htrei_dte")
        private Date dkeiHtreiDte;

        /**
         * kkr_num
         */
    @TableField("kkr_num")
        private Long kkrNum;

        /**
         * kkrkoza_ido_cde
         */
    @TableField("kkrkoza_ido_cde")
        private String kkrkozaIdoCde;

        /**
         * kkrkoza_ido_nme
         */
    @TableField("kkrkoza_ido_nme")
        private String kkrkozaIdoNme;

        /**
         * hkyh_cde
         */
    @TableField("hkyh_cde")
        private String hkyhCde;

        /**
         * hkyh_nme
         */
    @TableField("hkyh_nme")
        private String hkyhNme;

        /**
         * kyu
         */
    @TableField("kyu")
        private Long kyu;

        /**
         * kyu_ido_cde
         */
    @TableField("kyu_ido_cde")
        private String kyuIdoCde;

        /**
         * kyu_ido_nme
         */
    @TableField("kyu_ido_nme")
        private String kyuIdoNme;

        /**
         * kyu_htrei_dte
         */
    @TableField("kyu_htrei_dte")
        private String kyuHtreiDte;

        /**
         * dkyu_htrei_dte
         */
    @TableField("dkyu_htrei_dte")
        private Date dkyuHtreiDte;

        /**
         * zikyu_num
         */
    @TableField("zikyu_num")
        private Long zikyuNum;

        /**
         * goho
         */
    @TableField("goho")
        private Long goho;

        /**
         * goho_ido_cde
         */
    @TableField("goho_ido_cde")
        private String gohoIdoCde;

        /**
         * goho_ido_nme
         */
    @TableField("goho_ido_nme")
        private String gohoIdoNme;

        /**
         * goho_htrei_dte
         */
    @TableField("goho_htrei_dte")
        private String gohoHtreiDte;

        /**
         * dgoho_htrei_dte
         */
    @TableField("dgoho_htrei_dte")
        private Date dgohoHtreiDte;

        /**
         * st_hkyh_cde
         */
    @TableField("st_hkyh_cde")
        private String stHkyhCde;

        /**
         * st_hkyh_nme
         */
    @TableField("st_hkyh_nme")
        private String stHkyhNme;

        /**
         * st_kyu
         */
    @TableField("st_kyu")
        private Long stKyu;

        /**
         * stkyu_htrei_dte
         */
    @TableField("stkyu_htrei_dte")
        private String stkyuHtreiDte;

        /**
         * dstkyu_htrei_dte
         */
    @TableField("dstkyu_htrei_dte")
        private Date dstkyuHtreiDte;

        /**
         * st_goho
         */
    @TableField("st_goho")
        private Long stGoho;

        /**
         * stgoh_htrei_dte
         */
    @TableField("stgoh_htrei_dte")
        private String stgohHtreiDte;

        /**
         * dstgoh_htrei_dte
         */
    @TableField("dstgoh_htrei_dte")
        private Date dstgohHtreiDte;

        /**
         * nxt_syky_dte
         */
    @TableField("nxt_syky_dte")
        private String nxtSykyDte;

        /**
         * kyyo_gk_cde
         */
    @TableField("kyyo_gk_cde")
        private String kyyoGkCde;

        /**
         * kyyo_gk_nme
         */
    @TableField("kyyo_gk_nme")
        private String kyyoGkNme;

        /**
         * get_dte
         */
    @TableField("get_dte")
        private String getDte;

        /**
         * dget_dte
         */
    @TableField("dget_dte")
        private Date dgetDte;

        /**
         * ensn_flg
         */
    @TableField("ensn_flg")
        private String ensnFlg;

        /**
         * last_tksy_dte
         */
    @TableField("last_tksy_dte")
        private String lastTksyDte;

        /**
         * dlast_tksy_dte
         */
    @TableField("dlast_tksy_dte")
        private Date dlastTksyDte;

        /**
         * tksy_enki_num
         */
    @TableField("tksy_enki_num")
        private Long tksyEnkiNum;

        /**
         * hktys_siky_cde
         */
    @TableField("hktys_siky_cde")
        private String hktysSikyCde;

        /**
         * hktys_siky_nme
         */
    @TableField("hktys_siky_nme")
        private String hktysSikyNme;

        /**
         * hktys_siky_ritu
         */
    @TableField("hktys_siky_ritu")
        private String hktysSikyRitu;

        /**
         * hktys_htrei_dte
         */
    @TableField("hktys_htrei_dte")
        private String hktysHtreiDte;

        /**
         * dhktys_htrei_dte
         */
    @TableField("dhktys_htrei_dte")
        private Date dhktysHtreiDte;

        /**
         * hktys_yosan_cde
         */
    @TableField("hktys_yosan_cde")
        private String hktysYosanCde;

        /**
         * hktys_yosan_nme
         */
    @TableField("hktys_yosan_nme")
        private String hktysYosanNme;

        /**
         * hktys_teisi_dte
         */
    @TableField("hktys_teisi_dte")
        private String hktysTeisiDte;

        /**
         * dhktys_teisi_dte
         */
    @TableField("dhktys_teisi_dte")
        private Date dhktysTeisiDte;

        /**
         * tsyk_tat
         */
    @TableField("tsyk_tat")
        private Long tsykTat;

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
         * tysi_num
         */
    @TableField("tysi_num")
        private Long tysiNum;

        /**
         * dgkin_tysisum
         */
    @TableField("dgkin_tysisum")
        private Long dgkinTysisum;

        /**
         * tysisum_num
         */
    @TableField("tysisum_num")
        private Long tysisumNum;

        /**
         * adrss
         */
    @TableField("adrss")
        private String adrss;

        /**
         * pai043
         */
    @TableField("pai043")
        private Long pai043;

        /**
         * pai044
         */
    @TableField("pai044")
        private Long pai044;

        /**
         * pai045
         */
    @TableField("pai045")
        private Long pai045;

        /**
         * kanriksn3_num
         */
    @TableField("kanriksn3_num")
        private Long kanriksn3Num;

        /**
         * kanriksn6_num
         */
    @TableField("kanriksn6_num")
        private Long kanriksn6Num;

        /**
         * kanriksn12_num
         */
    @TableField("kanriksn12_num")
        private Long kanriksn12Num;

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
         * cho_start_dte
         */
    @TableField("cho_start_dte")
        private String choStartDte;

        /**
         * dcho_start_dte
         */
    @TableField("dcho_start_dte")
        private Date dchoStartDte;

        /**
         * cho_end_dte
         */
    @TableField("cho_end_dte")
        private String choEndDte;

        /**
         * dcho_end_dte
         */
    @TableField("dcho_end_dte")
        private Date dchoEndDte;

        /**
         * cho_siky_ritu
         */
    @TableField("cho_siky_ritu")
        private Long choSikyRitu;

        /**
         * cho_zikn_cde
         */
    @TableField("cho_zikn_cde")
        private String choZiknCde;

        /**
         * cho_zikn_nme
         */
    @TableField("cho_zikn_nme")
        private String choZiknNme;

        /**
         * cho_knsyzi_cde
         */
    @TableField("cho_knsyzi_cde")
        private String choKnsyziCde;

        /**
         * cho_knsyzi_nme
         */
    @TableField("cho_knsyzi_nme")
        private String choKnsyziNme;

        /**
         * kyu_prt_flg
         */
    @TableField("kyu_prt_flg")
        private String kyuPrtFlg;

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
         * n01_num
         */
    @TableField("n01_num")
        private Long n01Num;

        /**
         * n02_num
         */
    @TableField("n02_num")
        private Long n02Num;

        /**
         * n03_num
         */
    @TableField("n03_num")
        private Long n03Num;

        /**
         * n04_num
         */
    @TableField("n04_num")
        private Long n04Num;

        /**
         * n05_num
         */
    @TableField("n05_num")
        private Long n05Num;

        /**
         * cyubin
         */
    @TableField("cyubin")
        private String cyubin;

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


        }