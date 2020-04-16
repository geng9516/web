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
 * 個人基本情報db                      2000.12.08　以下のdbを統合
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
@TableName("dt_djnd0110")
public class DtDjnd0110DO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 会社区分
         */
    @TableField("ccompkb")
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
                @TableId(value = "cshainno", type = IdType.AUTO)
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
         * カナ氏名                          ↓個人基本情報db(hjhs0110)
         */
    @TableField("cnamekna")
        private String cnamekna;

        /**
         * 漢字氏名
         */
    @TableField("cnameknj")
        private String cnameknj;

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
         * 年度末年齢
         */
    @TableField("ndm_age")
        private Long ndmAge;

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
         * 国家公務員採用年月日
         */
    @TableField("saiyo_dte")
        private String saiyoDte;

        /**
         * 国家公務員採用年月日西暦
         */
    @TableField("dsaiyo_dte")
        private Date dsaiyoDte;

        /**
         * 国家公務員再採用年月日
         */
    @TableField("saiyo_r_dte")
        private String saiyoRDte;

        /**
         * 国家公務員再採用年月日
         */
    @TableField("dsaiyo_r_dte")
        private Date dsaiyoRDte;

        /**
         * 現機関発令年月日
         */
    @TableField("nwkkn_htrei_dte")
        private String nwkknHtreiDte;

        /**
         * 現機関発令年月日（西暦）
         */
    @TableField("dnwkkn_htrei_dte")
        private Date dnwkknHtreiDte;

        /**
         * 採用直前の勤務先コード
         */
    @TableField("kinmsaki_cde")
        private String kinmsakiCde;

        /**
         * 採用直前の勤務先等
         */
    @TableField("kinmsaki_nme")
        private String kinmsakiNme;

        /**
         * 本籍（国籍）コード
         */
    @TableField("honseki_cde")
        private String honsekiCde;

        /**
         * 本籍（国籍）
         */
    @TableField("honseki_nme")
        private String honsekiNme;

        /**
         * 国籍区分コード
         */
    @TableField("kokuseki_cde")
        private String kokusekiCde;

        /**
         * 国籍
         */
    @TableField("kokuseki_nme")
        private String kokusekiNme;

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
         * 任免区分コード
         */
    @TableField("nnmn_cde")
        private String nnmnCde;

        /**
         * 任免区分
         */
    @TableField("nnmn_nme")
        private String nnmnNme;

        /**
         * 退職等異動種目コード
         */
    @TableField("tsyk_ido_cde")
        private String tsykIdoCde;

        /**
         * 退職等異動種目
         */
    @TableField("tsyk_ido_nme")
        private String tsykIdoNme;

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
         * 退職等後の就職先等コード
         */
    @TableField("tsykgo_cde")
        private String tsykgoCde;

        /**
         * 退職等後の就職先等
         */
    @TableField("tsykgo_nme")
        private String tsykgoNme;

        /**
         * 行（二）区分コード
         */
    @TableField("gyo2_cde")
        private String gyo2Cde;

        /**
         * 行（二）区分
         */
    @TableField("gyo2_nme")
        private String gyo2Nme;

        /**
         * 定員区分コード
         */
    @TableField("tikbn_cde")
        private String tikbnCde;

        /**
         * 定員区分
         */
    @TableField("tikbn_nme")
        private String tikbnNme;

        /**
         * 勤務区分コード
         */
    @TableField("kmkbn_cde")
        private String kmkbnCde;

        /**
         * 勤務区分
         */
    @TableField("kmkbn_nme")
        private String kmkbnNme;

        /**
         * 経験年数
         */
    @TableField("exp_num")
        private Long expNum;

        /**
         * 在官年数
         */
    @TableField("zikn_num")
        private Long ziknNum;

        /**
         * 退職手当法上の勤続年数
         */
    @TableField("tsyktat_num")
        private Long tsyktatNum;

        /**
         * 教育職員コード
         */
    @TableField("k_skin_cde")
        private String kSkinCde;

        /**
         * 教育職員
         */
    @TableField("k_skin_nme")
        private String kSkinNme;

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
         * 臨時的任用被代替者個人番                  ★2001.2.14(i)
         */
    @TableField("rinj_hiditi_cde")
        private String rinjHiditiCde;

        /**
         * 臨時的任用被代替者
         */
    @TableField("rinj_hiditi_nme")
        private String rinjHiditiNme;

        /**
         * 分限関係 終了予定年月日
         */
    @TableField("bg_ed_dte")
        private String bgEdDte;

        /**
         * 分限関係 終了予定年月日
         */
    @TableField("dbg_ed_dte")
        private Date dbgEdDte;

        /**
         * 分限関係 支給率
         */
    @TableField("bg_sk_ritu")
        private String bgSkRitu;

        /**
         * 分限関係 国際機関コード
         */
    @TableField("bg_kkn_cde")
        private String bgKknCde;

        /**
         * 分限関係 国際機関名
         */
    @TableField("bg_kkn_nme")
        private String bgKknNme;

        /**
         * 分限関係 派遣先コード
         */
    @TableField("bg_hk_cde")
        private String bgHkCde;

        /**
         * 分限関係 派遣先
         */
    @TableField("bg_hk_nme")
        private String bgHkNme;

        /**
         * 現官職 官名コード                     ★2000.12.11(u) 1→2ﾊﾞｲﾄ
         */
    @TableField("gk_knmei_cde")
        private String gkKnmeiCde;

        /**
         * 現官職 官名
         */
    @TableField("gk_knmei_nme")
        private String gkKnmeiNme;

        /**
         * 現官職 俸給表コード
         */
    @TableField("gk_hkyh_cde")
        private String gkHkyhCde;

        /**
         * 現官職 俸給表
         */
    @TableField("gk_hkyh_nme")
        private String gkHkyhNme;

        /**
         * 現官職 級
         */
    @TableField("gk_kyu")
        private Long gkKyu;

        /**
         * 現官職 所属等
         */
    @TableField("gk_szk")
        private String gkSzk;

        /**
         * 任免異動種目コード
         */
    @TableField("nnmn_ido_cde")
        private String nnmnIdoCde;

        /**
         * 任免異動種目
         */
    @TableField("nnmn_ido_nme")
        private String nnmnIdoNme;

        /**
         * 発令等年月日
         */
    @TableField("nnmn_htrei_dte")
        private String nnmnHtreiDte;

        /**
         * 発令等年月日（西暦）
         */
    @TableField("dnnmn_htrei_dte")
        private Date dnnmnHtreiDte;

        /**
         * 現機関コード
         */
    @TableField("kkn_cde")
        private String kknCde;

        /**
         * 現機関
         */
    @TableField("kkn_nme")
        private String kknNme;

        /**
         * 機関任免異動種目コード                   ★2001.2.12(i)
         */
    @TableField("kkn_ido_cde")
        private String kknIdoCde;

        /**
         * 機関任免異動種目                      ★2001.2.12(i)
         */
    @TableField("kkn_ido_nme")
        private String kknIdoNme;

        /**
         * 官名コード
         */
    @TableField("knmei_cde")
        private String knmeiCde;

        /**
         * 官名
         */
    @TableField("knmei_nme")
        private String knmeiNme;

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
         * (職種)発令年月日
         */
    @TableField("syksy_htrei_dte")
        private String syksyHtreiDte;

        /**
         * (職種)発令年月日（西暦）
         */
    @TableField("dsyksy_htrei_dte")
        private Date dsyksyHtreiDte;

        /**
         * 職種年数
         */
    @TableField("syksy_num")
        private Long syksyNum;

        /**
         * 職種任免異動種目コード                   ★2001.2.12(i)
         */
    @TableField("syksy_ido_cde")
        private String syksyIdoCde;

        /**
         * 職種任免異動種目                      ★2001.2.12(i)
         */
    @TableField("syksy_ido_nme")
        private String syksyIdoNme;

        /**
         * 定数上の職名コード                     ☆2001.01.17add
         */
    @TableField("tsj_skmei_cde")
        private String tsjSkmeiCde;

        /**
         * 定数上の職名                        ☆2001.01.17add
         */
    @TableField("tsj_skmei_nme")
        private String tsjSkmeiNme;

        /**
         * 予算項コード
         */
    @TableField("yosan_cde")
        private String yosanCde;

        /**
         * 予算項
         */
    @TableField("yosan_nme")
        private String yosanNme;

        /**
         * 予算項発令年月日                      ★2001.2.12(i)
         */
    @TableField("yosan_htrei_dte")
        private String yosanHtreiDte;

        /**
         * 予算項発令年月日（西暦）
         */
    @TableField("dyosan_htrei_dte")
        private Date dyosanHtreiDte;

        /**
         * 予算項任免異動種目コード                  ★2001.2.12(i)
         */
    @TableField("yosan_ido_cde")
        private String yosanIdoCde;

        /**
         * 予算項任免異動種目                     ★2001.2.12(i)
         */
    @TableField("yosan_ido_nme")
        private String yosanIdoNme;

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
         * (所属)発令年月日
         */
    @TableField("szk_htrei_dte")
        private String szkHtreiDte;

        /**
         * (所属)発令年月日（西暦）
         */
    @TableField("dszk_htrei_dte")
        private Date dszkHtreiDte;

        /**
         * 所属年数
         */
    @TableField("szk_num")
        private Long szkNum;

        /**
         * 所属任免異動種目コード                   ★2001.2.12(i)
         */
    @TableField("szk_ido_cde")
        private String szkIdoCde;

        /**
         * 所属任免異動種目                      ★2001.2.12(i)
         */
    @TableField("szk_ido_nme")
        private String szkIdoNme;

        /**
         * 系コード
         */
    @TableField("kei_cde")
        private String keiCde;

        /**
         * 系
         */
    @TableField("kei_nme")
        private String keiNme;

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
         * (係)発令年月日
         */
    @TableField("kei_htrei_dte")
        private String keiHtreiDte;

        /**
         * (係)発令年月日（西暦）
         */
    @TableField("dkei_htrei_dte")
        private Date dkeiHtreiDte;

        /**
         * 係年数
         */
    @TableField("kkr_num")
        private Long kkrNum;

        /**
         * 係講座任免異動種目コード                  ★2001.2.12(i)
         */
    @TableField("kkrkoza_ido_cde")
        private String kkrkozaIdoCde;

        /**
         * 係講座任免異動種目                     ★2001.2.12(i)
         */
    @TableField("kkrkoza_ido_nme")
        private String kkrkozaIdoNme;

        /**
         * 俸給表コード
         */
    @TableField("hkyh_cde")
        private String hkyhCde;

        /**
         * 俸給表
         */
    @TableField("hkyh_nme")
        private String hkyhNme;

        /**
         * 級
         */
    @TableField("kyu")
        private Long kyu;

        /**
         * 級異動種目コード
         */
    @TableField("kyu_ido_cde")
        private String kyuIdoCde;

        /**
         * 級異動種目
         */
    @TableField("kyu_ido_nme")
        private String kyuIdoNme;

        /**
         * (級)発令年月日
         */
    @TableField("kyu_htrei_dte")
        private String kyuHtreiDte;

        /**
         * (級)発令年月日（西暦）
         */
    @TableField("dkyu_htrei_dte")
        private Date dkyuHtreiDte;

        /**
         * 在級年数
         */
    @TableField("zikyu_num")
        private Long zikyuNum;

        /**
         * 号俸
         */
    @TableField("goho")
        private Long goho;

        /**
         * 号俸異動種目コード
         */
    @TableField("goho_ido_cde")
        private String gohoIdoCde;

        /**
         * 号俸異動種目
         */
    @TableField("goho_ido_nme")
        private String gohoIdoNme;

        /**
         * (号俸)発令年月日
         */
    @TableField("goho_htrei_dte")
        private String gohoHtreiDte;

        /**
         * (号俸)発令年月日（西暦）
         */
    @TableField("dgoho_htrei_dte")
        private Date dgohoHtreiDte;

        /**
         * 昇格直前の俸給表コード
         */
    @TableField("st_hkyh_cde")
        private String stHkyhCde;

        /**
         * 昇格直前の俸給表
         */
    @TableField("st_hkyh_nme")
        private String stHkyhNme;

        /**
         * 昇格直前の級
         */
    @TableField("st_kyu")
        private Long stKyu;

        /**
         * 昇格直前の級発令年月日
         */
    @TableField("stkyu_htrei_dte")
        private String stkyuHtreiDte;

        /**
         * 昇格直前の級発令年月日
         */
    @TableField("dstkyu_htrei_dte")
        private Date dstkyuHtreiDte;

        /**
         * 昇格直前の号俸
         */
    @TableField("st_goho")
        private Long stGoho;

        /**
         * 昇格直前の号俸発令年月日
         */
    @TableField("stgoh_htrei_dte")
        private String stgohHtreiDte;

        /**
         * 昇格直前の号俸発令年月日
         */
    @TableField("dstgoh_htrei_dte")
        private Date dstgohHtreiDte;

        /**
         * 次期昇給期
         */
    @TableField("nxt_syky_dte")
        private String nxtSykyDte;

        /**
         * 給与決定学歴区分コード
         */
    @TableField("kyyo_gk_cde")
        private String kyyoGkCde;

        /**
         * 給与決定学歴区分
         */
    @TableField("kyyo_gk_nme")
        private String kyyoGkNme;

        /**
         * 取得年月日                         ↑個人基本情報db(hjhs0110)
         */
    @TableField("get_dte")
        private String getDte;

        /**
         * 取得年月日（西暦）
         */
    @TableField("dget_dte")
        private Date dgetDte;

        /**
         * 延伸中フラグ                        1:延伸中(★2000.12.11追加)
         */
    @TableField("ensn_flg")
        private String ensnFlg;

        /**
         * 前回特昇年月日                       ↓個人給与情報db(hjhs0210)
         */
    @TableField("last_tksy_dte")
        private String lastTksyDte;

        /**
         * 前回特昇年月日（西暦）
         */
    @TableField("dlast_tksy_dte")
        private Date dlastTksyDte;

        /**
         * 特昇延回数
         */
    @TableField("tksy_enki_num")
        private Long tksyEnkiNum;

        /**
         * (俸給の特別調整額)　区分
         */
    @TableField("hktys_siky_cde")
        private String hktysSikyCde;

        /**
         * 　支給区分
         */
    @TableField("hktys_siky_nme")
        private String hktysSikyNme;

        /**
         * 　支給率
         */
    @TableField("hktys_siky_ritu")
        private String hktysSikyRitu;

        /**
         * 　発令年月日
         */
    @TableField("hktys_htrei_dte")
        private String hktysHtreiDte;

        /**
         * 　発令年月日（西暦）
         */
    @TableField("dhktys_htrei_dte")
        private Date dhktysHtreiDte;

        /**
         * 　予算項コード
         */
    @TableField("hktys_yosan_cde")
        private String hktysYosanCde;

        /**
         * 　予算項
         */
    @TableField("hktys_yosan_nme")
        private String hktysYosanNme;

        /**
         * 　支給停止・再支給年月日
         */
    @TableField("hktys_teisi_dte")
        private String hktysTeisiDte;

        /**
         * 　支給停止・再支給年月日
         */
    @TableField("dhktys_teisi_dte")
        private Date dhktysTeisiDte;

        /**
         * (支給額)退職手当                     2001.01.18
         */
    @TableField("tsyk_tat")
        private Long tsykTat;

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
         * 俸給の調整額調整数                     ↑個人給与情報db(hjhs0210)
         */
    @TableField("tysi_num")
        private Long tysiNum;

        /**
         * 大学院担当 調整数                     ★2001.2.6 調整数合計要素
         */
    @TableField("dgkin_tysisum")
        private Long dgkinTysisum;

        /**
         * 調整数合計
         */
    @TableField("tysisum_num")
        private Long tysisumNum;

        /**
         * 現住所                           ↓給与システム情報db(akpay)
         */
    @TableField("adrss")
        private String adrss;

        /**
         * 6月期役職段階別加算割合
         */
    @TableField("pai043")
        private Long pai043;

        /**
         * 12月期役職段階別加算割合
         */
    @TableField("pai044")
        private Long pai044;

        /**
         * 3月期役職段階別加算割合                  ↑給与システム情報db(akpay)
         */
    @TableField("pai045")
        private Long pai045;

        /**
         * 3月期管理加算割合
         */
    @TableField("kanriksn3_num")
        private Long kanriksn3Num;

        /**
         * 6月期管理加算割合
         */
    @TableField("kanriksn6_num")
        private Long kanriksn6Num;

        /**
         * 12月期管理加算割合
         */
    @TableField("kanriksn12_num")
        private Long kanriksn12Num;

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
         * 調整手当開始年月日                     2001.11.14 追加
         */
    @TableField("cho_start_dte")
        private String choStartDte;

        /**
         * 調整手当開始年月日 西暦
         */
    @TableField("dcho_start_dte")
        private Date dchoStartDte;

        /**
         * 調整手当終了年月日                     2001.11.14 追加
         */
    @TableField("cho_end_dte")
        private String choEndDte;

        /**
         * 調整手当終了年月日 西暦
         */
    @TableField("dcho_end_dte")
        private Date dchoEndDte;

        /**
         * 調整手当支給率                       2001.11.14 追加
         */
    @TableField("cho_siky_ritu")
        private Long choSikyRitu;

        /**
         * 調整手当在勤官署コード                   2001.11.14 追加
         */
    @TableField("cho_zikn_cde")
        private String choZiknCde;

        /**
         * 調整手当在勤官署                      2001.11.14 追加
         */
    @TableField("cho_zikn_nme")
        private String choZiknNme;

        /**
         * 調整手当官署所在地コード                  2001.11.14 追加
         */
    @TableField("cho_knsyzi_cde")
        private String choKnsyziCde;

        /**
         * 調整手当官署所在地                     2001.11.14 追加
         */
    @TableField("cho_knsyzi_nme")
        private String choKnsyziNme;

        /**
         * 旧姓印字無フラグ                      2001.11.15 追加
         */
    @TableField("kyu_prt_flg")
        private String kyuPrtFlg;

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
         * c01_cde
         */
    @TableField("c01_cde")
        private String c01Cde;

        /**
         * 長期組合員番号
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
         * 昇給調整の有無コード
         */
    @TableField("c03_cde")
        private String c03Cde;

        /**
         * 昇給調整の有無
         */
    @TableField("c03_nme")
        private String c03Nme;

        /**
         * 加算号俸区分
         */
    @TableField("c04_cde")
        private String c04Cde;

        /**
         * 加算号俸名称
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
         * 昇給起算日(和暦)
         */
    @TableField("c01_dte")
        private String c01Dte;

        /**
         * 昇給起算日(西暦)
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
         * 予備数値1   20070820 分限関係 支給額
         */
    @TableField("n01_num")
        private Long n01Num;

        /**
         * 予備数値2
         */
    @TableField("n02_num")
        private Long n02Num;

        /**
         * 予備数値3
         */
    @TableField("n03_num")
        private Long n03Num;

        /**
         * 予備数値4
         */
    @TableField("n04_num")
        private Long n04Num;

        /**
         * 予備数値5
         */
    @TableField("n05_num")
        private Long n05Num;

        /**
         * 郵便番号
         */
    @TableField("cyubin")
        private String cyubin;

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