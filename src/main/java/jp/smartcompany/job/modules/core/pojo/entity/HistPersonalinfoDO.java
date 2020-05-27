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
 * public.hist_personalinfo
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
@TableName("hist_personalinfo")
public class HistPersonalinfoDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableId(value="hpi_ccustomerid",type = IdType.INPUT)
        private String hpiCcustomerid;

        /**
         * 会社区分
         */
    @TableField("hpi_ccompanyid")
        private String hpiCcompanyid;

        /**
         * 職員番号
         */
    @TableField("hpi_cemployeeid")
        private String hpiCemployeeid;

        /**
         * 開始日
         */
    @TableField("hpi_dstart")
        private Date hpiDstart;

        /**
         * 終了日
         */
    @TableField("hpi_dend")
        private Date hpiDend;

        /**
         * 個人番号
         */
    @TableField("hpi_ckojinno")
        private String hpiCkojinno;

        /**
         * 氏名（旧姓使用等対応）
         */
    @TableField("hpi_ckanjinm")
        private String hpiCkanjinm;

        /**
         * カナ氏名（旧姓使用等対応）
         */
    @TableField("hpi_ckananm")
        private String hpiCkananm;

        /**
         * 性別［＊］
         */
    @TableField("hpi_cseibetukb")
        private String hpiCseibetukb;

        /**
         * 性別
         */
    @TableField("hpi_cseibetunm")
        private String hpiCseibetunm;

        /**
         * 生年月日
         */
    @TableField("hpi_dtanjo")
        private Date hpiDtanjo;

        /**
         * 国籍［＊］
         */
    @TableField("hpi_ckokusekikb")
        private String hpiCkokusekikb;

        /**
         * 国籍
         */
    @TableField("hpi_ckokusekinm")
        private String hpiCkokusekinm;

        /**
         * 発令年月日
         */
    @TableField("hpi_dhaturei")
        private Date hpiDhaturei;

        /**
         * 発令区分［＊］
         */
    @TableField("hpi_chatureikb")
        private String hpiChatureikb;

        /**
         * 発令区分名称
         */
    @TableField("hpi_chatureinm")
        private String hpiChatureinm;

        /**
         * 発令内容区分［＊］
         */
    @TableField("hpi_chatureishosaikb")
        private String hpiChatureishosaikb;

        /**
         * 発令内容名称
         */
    @TableField("hpi_chatureishosainm")
        private String hpiChatureishosainm;

        /**
         * 転出・転入区分［＊］
         */
    @TableField("hpi_ctenkyokb")
        private String hpiCtenkyokb;

        /**
         * 転出・転入区分
         */
    @TableField("hpi_ctenkyonm")
        private String hpiCtenkyonm;

        /**
         * 教職員の名称［＊］
         */
    @TableField("hpi_ckyoinkb")
        private String hpiCkyoinkb;

        /**
         * 教職員の名称
         */
    @TableField("hpi_ckyoinnm")
        private String hpiCkyoinnm;

        /**
         * 採用年月日
         */
    @TableField("hpi_dsaiyo")
        private Date hpiDsaiyo;

        /**
         * 期間満了予定日
         */
    @TableField("hpi_dmanryo")
        private Date hpiDmanryo;

        /**
         * 勤続期間基準日
         */
    @TableField("hpi_ckinzoku")
        private String hpiCkinzoku;

        /**
         * 予算項コード［＊］
         */
    @TableField("hpi_cyosankb")
        private String hpiCyosankb;

        /**
         * 予算項
         */
    @TableField("hpi_cyosannm")
        private String hpiCyosannm;

        /**
         * 基本給表［＊］
         */
    @TableField("hpi_ckihonkb")
        private String hpiCkihonkb;

        /**
         * 基本給表名称
         */
    @TableField("hpi_ckihonnm")
        private String hpiCkihonnm;

        /**
         * 所属コード［＊］
         */
    @TableField("hpi_cshozokukb")
        private String hpiCshozokukb;

        /**
         * 所属
         */
    @TableField("hpi_cshozokunm")
        private String hpiCshozokunm;

        /**
         * レベル０２所属名称(正式)
         */
    @TableField("hpi_cshozoku2")
        private String hpiCshozoku2;

        /**
         * レベル０３所属名称(正式)
         */
    @TableField("hpi_cshozoku3")
        private String hpiCshozoku3;

        /**
         * 所属発令年月日
         */
    @TableField("hpi_dshozoku")
        private Date hpiDshozoku;

        /**
         * 出納員コード［＊］
         */
    @TableField("hpi_csuitokb")
        private String hpiCsuitokb;

        /**
         * 出納員名称
         */
    @TableField("hpi_csuitonm")
        private String hpiCsuitonm;

        /**
         * 係・講座［＊］
         */
    @TableField("hpi_ckakarikb")
        private String hpiCkakarikb;

        /**
         * 係講座（正式）
         */
    @TableField("hpi_ckakarinm")
        private String hpiCkakarinm;

        /**
         * 係・講座発令年月日
         */
    @TableField("hpi_dkakari")
        private Date hpiDkakari;

        /**
         * 職名コード［＊］
         */
    @TableField("hpi_cshokumeikb")
        private String hpiCshokumeikb;

        /**
         * 職名
         */
    @TableField("hpi_cshokumeinm")
        private String hpiCshokumeinm;

        /**
         * 職名発令年月日
         */
    @TableField("hpi_dshokumei")
        private Date hpiDshokumei;

        /**
         * 採用前勤務先名称等［＊］
         */
    @TableField("hpi_csaiyomaekb")
        private String hpiCsaiyomaekb;

        /**
         * 採用前勤務先名称等
         */
    @TableField("hpi_csaiyomaenm")
        private String hpiCsaiyomaenm;

        /**
         * 級コード［＊］
         */
    @TableField("hpi_ckyukb")
        private String hpiCkyukb;

        /**
         * 級
         */
    @TableField("hpi_ckyunm")
        private String hpiCkyunm;

        /**
         * 級発令年月日
         */
    @TableField("hpi_dkyu")
        private Date hpiDkyu;

        /**
         * 単価区分［＊］
         */
    @TableField("hpi_ctankakb")
        private String hpiCtankakb;

        /**
         * 単価区分
         */
    @TableField("hpi_ctankanm")
        private String hpiCtankanm;

        /**
         * 任免区分コード［＊］
         */
    @TableField("hpi_cninmenkb")
        private String hpiCninmenkb;

        /**
         * 任免区分
         */
    @TableField("hpi_cninmennm")
        private String hpiCninmennm;

        /**
         * 任免区分変更日
         */
    @TableField("hpi_dninmen")
        private Date hpiDninmen;

        /**
         * 出向フラグ［＊］
         */
    @TableField("hpi_cshukokb")
        private String hpiCshukokb;

        /**
         * 出向フラグ
         */
    @TableField("hpi_cshukonm")
        private String hpiCshukonm;

        /**
         * 出向先機関コード［＊］
         */
    @TableField("hpi_cshukokikankb")
        private String hpiCshukokikankb;

        /**
         * 出向先機関名
         */
    @TableField("hpi_cshukokikannm")
        private String hpiCshukokikannm;

        /**
         * 出向発令日
         */
    @TableField("hpi_dshuko")
        private Date hpiDshuko;

        /**
         * 勤務区分［＊］
         */
    @TableField("hpi_ckinmukb")
        private String hpiCkinmukb;

        /**
         * 勤務区分
         */
    @TableField("hpi_ckinmunm")
        private String hpiCkinmunm;

        /**
         * 時間外労働［＊］
         */
    @TableField("hpi_cjikangaikb")
        private String hpiCjikangaikb;

        /**
         * 時間外労働
         */
    @TableField("hpi_cjikangainm")
        private String hpiCjikangainm;

        /**
         * 時間外労働変更日
         */
    @TableField("hpi_djikangai")
        private Date hpiDjikangai;

        /**
         * 事業所［＊］
         */
    @TableField("hpi_cjigyoshokb")
        private String hpiCjigyoshokb;

        /**
         * 事業所
         */
    @TableField("hpi_cjigyoshonm")
        private String hpiCjigyoshonm;

        /**
         * 事業所変更日
         */
    @TableField("hpi_djigyosho")
        private Date hpiDjigyosho;

        /**
         * 社会保険等［＊］
         */
    @TableField("hpi_cshakaihokenkb")
        private String hpiCshakaihokenkb;

        /**
         * 社会保険等
         */
    @TableField("hpi_cshakaihokennm")
        private String hpiCshakaihokennm;

        /**
         * 社会保険等変更日
         */
    @TableField("hpi_dshakaihoken")
        private Date hpiDshakaihoken;

        /**
         * 退職区分［＊］
         */
    @TableField("hpi_ctaishokukb")
        private String hpiCtaishokukb;

        /**
         * 退職区分
         */
    @TableField("hpi_ctaishokunm")
        private String hpiCtaishokunm;

        /**
         * 退職年月日
         */
    @TableField("hpi_dtaishoku")
        private Date hpiDtaishoku;

        /**
         * 休職区分［＊］
         */
    @TableField("hpi_ckyushokukb")
        private String hpiCkyushokukb;

        /**
         * 休職区分
         */
    @TableField("hpi_ckyushokunm")
        private String hpiCkyushokunm;

        /**
         * 休職発令日
         */
    @TableField("hpi_dkyushoku")
        private Date hpiDkyushoku;

        /**
         * 休職事由コード［＊］
         */
    @TableField("hpi_ckyushokujiyukb")
        private String hpiCkyushokujiyukb;

        /**
         * 休職事由名称
         */
    @TableField("hpi_ckyushokujiyunm")
        private String hpiCkyushokujiyunm;

        /**
         * 休職等給与支給率［＊］
         */
    @TableField("hpi_ckyushokusikyukb")
        private String hpiCkyushokusikyukb;

        /**
         * 休職等給与支給率
         */
    @TableField("hpi_ckyushokusikyunm")
        private String hpiCkyushokusikyunm;

        /**
         * 休職等終了予定日
         */
    @TableField("hpi_dkyushokuyotei")
        private Date hpiDkyushokuyotei;

        /**
         * 雇用保険加入の有無［＊］
         */
    @TableField("hpi_ckoyohokenkb")
        private String hpiCkoyohokenkb;

        /**
         * 雇用保険加入の有無
         */
    @TableField("hpi_ckoyohokennm")
        private String hpiCkoyohokennm;

        /**
         * 再雇用年月日
         */
    @TableField("hpi_dsaikoyo")
        private Date hpiDsaikoyo;

        /**
         * 期間内従事時間数
         */
    @TableField("hpi_njujijikan")
        private Long hpiNjujijikan;

        /**
         * 高齢者雇用該当等［＊］
         */
    @TableField("hpi_ckoreikb")
        private String hpiCkoreikb;

        /**
         * 高齢者雇用該当等
         */
    @TableField("hpi_ckoreinm")
        private String hpiCkoreinm;

        /**
         * 在籍出向・海外勤務［＊］
         */
    @TableField("hpi_czaisekishukokb")
        private String hpiCzaisekishukokb;

        /**
         * 在籍出向・海外勤務
         */
    @TableField("hpi_czaisekishukonm")
        private String hpiCzaisekishukonm;

        /**
         * 最長雇用期限起算日
         */
    @TableField("hpi_dkisanbi")
        private Date hpiDkisanbi;

        /**
         * 号俸コード［＊］
         */
    @TableField("hpi_cgohokb")
        private String hpiCgohokb;

        /**
         * 号俸
         */
    @TableField("hpi_cgohonm")
        private String hpiCgohonm;

        /**
         * 号俸発令年月日
         */
    @TableField("hpi_dgoho")
        private Date hpiDgoho;

        /**
         * 経費名称［＊］
         */
    @TableField("hpi_ckeihikb")
        private String hpiCkeihikb;

        /**
         * 経費名称
         */
    @TableField("hpi_ckeihinm")
        private String hpiCkeihinm;

        /**
         * 経費名称変更日
         */
    @TableField("hpi_dkeihi")
        private Date hpiDkeihi;

        /**
         * 就業(業務従事)場所［＊］
         */
    @TableField("hpi_cshugyobashokb")
        private String hpiCshugyobashokb;

        /**
         * 就業(業務従事)場所
         */
    @TableField("hpi_cshugyobashonm")
        private String hpiCshugyobashonm;

        /**
         * 就業場所発令日
         */
    @TableField("hpi_dshugyobasho")
        private Date hpiDshugyobasho;

        /**
         * 前機関名［＊］
         */
    @TableField("hpi_czenkikankb")
        private String hpiCzenkikankb;

        /**
         * 前機関名
         */
    @TableField("hpi_czenkikannm")
        private String hpiCzenkikannm;

        /**
         * 嘱託職員等勤務時間数［＊］
         */
    @TableField("hpi_cshokutakujikankb")
        private String hpiCshokutakujikankb;

        /**
         * 嘱託職員等勤務時間数
         */
    @TableField("hpi_cshokutakujikannm")
        private String hpiCshokutakujikannm;

        /**
         * 嘱託等時間数変更日
         */
    @TableField("hpi_dshokutakujikan")
        private Date hpiDshokutakujikan;

        /**
         * 管理職手当支給区分［＊］
         */
    @TableField("hpi_ckanrishokukb")
        private String hpiCkanrishokukb;

        /**
         * 管理職手当支給区分
         */
    @TableField("hpi_ckanrishokunm")
        private String hpiCkanrishokunm;

        /**
         * 管理職手当発令年月日
         */
    @TableField("hpi_dkanrishoku")
        private Date hpiDkanrishoku;

        /**
         * 支給開始日(医師手当)
         */
    @TableField("hpi_dsikyuishi")
        private Date hpiDsikyuishi;

        /**
         * 兼務等開始年月日１
         */
    @TableField("hpi_dkenmu1start")
        private Date hpiDkenmu1start;

        /**
         * 兼務等　職名１名称［＊］
         */
    @TableField("hpi_ckenmu1kb")
        private String hpiCkenmu1kb;

        /**
         * 兼務等　職名１名称
         */
    @TableField("hpi_ckenmu1nm")
        private String hpiCkenmu1nm;

        /**
         * 兼務等終了年月日１
         */
    @TableField("hpi_ckemu1end")
        private String hpiCkemu1end;

        /**
         * 学内派遣始期
         */
    @TableField("hpi_dgakunaistart")
        private Date hpiDgakunaistart;

        /**
         * 学内派遣先名称［＊］
         */
    @TableField("hpi_cgakunaikb")
        private String hpiCgakunaikb;

        /**
         * 学内派遣先名称
         */
    @TableField("hpi_cgakunainm")
        private String hpiCgakunainm;

        /**
         * 学内派遣終期
         */
    @TableField("hpi_dgakunaiend")
        private Date hpiDgakunaiend;

        /**
         * 開始日
         */
    @TableField("hpi_dkinmustart")
        private Date hpiDkinmustart;

        /**
         * 終了日
         */
    @TableField("hpi_dkinmuend1")
        private Date hpiDkinmuend1;

        /**
         * 終了日２
         */
    @TableField("hpi_dkinmuend2")
        private Date hpiDkinmuend2;

        /**
         * 終了日３
         */
    @TableField("hpi_dkinmuend3")
        private Date hpiDkinmuend3;

        /**
         * 勤務形態コード［＊］
         */
    @TableField("hpi_ckinmukeitaikb")
        private String hpiCkinmukeitaikb;

        /**
         * 勤務形態
         */
    @TableField("hpi_ckinmukeitainm")
        private String hpiCkinmukeitainm;

        /**
         * 始業時刻1［＊］
         */
    @TableField("hpi_cshigyojikan1kb")
        private String hpiCshigyojikan1kb;

        /**
         * 始業時刻1名称
         */
    @TableField("hpi_cshigyojikan1nm")
        private String hpiCshigyojikan1nm;

        /**
         * 終業時刻1［＊］
         */
    @TableField("hpi_cshugyojikan1kb")
        private String hpiCshugyojikan1kb;

        /**
         * 終業時刻1名称
         */
    @TableField("hpi_cshugyojikan1nm")
        private String hpiCshugyojikan1nm;

        /**
         * 始業時刻2［＊］
         */
    @TableField("hpi_cshigyojikan2kb")
        private String hpiCshigyojikan2kb;

        /**
         * 始業時刻2名称
         */
    @TableField("hpi_cshigyojikan2nm")
        private String hpiCshigyojikan2nm;

        /**
         * 終業時刻2［＊］
         */
    @TableField("hpi_cshugyojikan2kb")
        private String hpiCshugyojikan2kb;

        /**
         * 終業時刻2名称
         */
    @TableField("hpi_cshugyojikan2nm")
        private String hpiCshugyojikan2nm;

        /**
         * 始業時刻3［＊］
         */
    @TableField("hpi_cshigyojikan3kb")
        private String hpiCshigyojikan3kb;

        /**
         * 始業時刻3名称
         */
    @TableField("hpi_cshigyojikan3nm")
        private String hpiCshigyojikan3nm;

        /**
         * 終業時刻3［＊］
         */
    @TableField("hpi_cshugyojikan3kb")
        private String hpiCshugyojikan3kb;

        /**
         * 終業時刻3名称
         */
    @TableField("hpi_cshugyojikan3nm")
        private String hpiCshugyojikan3nm;

        /**
         * 始業時刻4［＊］
         */
    @TableField("hpi_cshigyojikan4kb")
        private String hpiCshigyojikan4kb;

        /**
         * 始業時刻4名称
         */
    @TableField("hpi_cshigyojikan4nm")
        private String hpiCshigyojikan4nm;

        /**
         * 終業時刻4［＊］
         */
    @TableField("hpi_cshugyojikan4kb")
        private String hpiCshugyojikan4kb;

        /**
         * 終業時刻4名称
         */
    @TableField("hpi_cshugyojikan4nm")
        private String hpiCshugyojikan4nm;

        /**
         * 始業時刻5［＊］
         */
    @TableField("hpi_cshigyojikan5kb")
        private String hpiCshigyojikan5kb;

        /**
         * 始業時刻5名称
         */
    @TableField("hpi_cshigyojikan5nm")
        private String hpiCshigyojikan5nm;

        /**
         * 終業時刻5［＊］
         */
    @TableField("hpi_cshugyojikan5kb")
        private String hpiCshugyojikan5kb;

        /**
         * 終業時刻5名称
         */
    @TableField("hpi_cshugyojikan5nm")
        private String hpiCshugyojikan5nm;

        /**
         * 曜日1［＊］
         */
    @TableField("hpi_cyobi1kb")
        private String hpiCyobi1kb;

        /**
         * 曜日1
         */
    @TableField("hpi_cyobi1nm")
        private String hpiCyobi1nm;

        /**
         * 曜日2［＊］
         */
    @TableField("hpi_cyobi2kb")
        private String hpiCyobi2kb;

        /**
         * 曜日2
         */
    @TableField("hpi_cyobi2nm")
        private String hpiCyobi2nm;

        /**
         * 曜日3［＊］
         */
    @TableField("hpi_cyobi3kb")
        private String hpiCyobi3kb;

        /**
         * 曜日3
         */
    @TableField("hpi_cyobi3nm")
        private String hpiCyobi3nm;

        /**
         * 曜日4［＊］
         */
    @TableField("hpi_cyobi4kb")
        private String hpiCyobi4kb;

        /**
         * 曜日4
         */
    @TableField("hpi_cyobi4nm")
        private String hpiCyobi4nm;

        /**
         * 曜日5［＊］
         */
    @TableField("hpi_cyobi5kb")
        private String hpiCyobi5kb;

        /**
         * 曜日5
         */
    @TableField("hpi_cyobi5nm")
        private String hpiCyobi5nm;

        /**
         * 勤務時間数1
         */
    @TableField("hpi_ckinmujikan1")
        private String hpiCkinmujikan1;

        /**
         * 勤務時間数2
         */
    @TableField("hpi_ckinmujikan2")
        private String hpiCkinmujikan2;

        /**
         * 勤務時間数3
         */
    @TableField("hpi_ckinmujikan3")
        private String hpiCkinmujikan3;

        /**
         * 勤務時間数4
         */
    @TableField("hpi_ckinmujikan4")
        private String hpiCkinmujikan4;

        /**
         * 勤務時間数5
         */
    @TableField("hpi_ckinmujikan5")
        private String hpiCkinmujikan5;

        /**
         * 勤務時間数(合計)
         */
    @TableField("hpi_ckinmujikangokei")
        private String hpiCkinmujikangokei;

        /**
         * 休憩開始終了時刻1
         */
    @TableField("hpi_ckyukeijikan1")
        private String hpiCkyukeijikan1;

        /**
         * 休憩開始終了時刻2
         */
    @TableField("hpi_ckyukeijikan2")
        private String hpiCkyukeijikan2;

        /**
         * 休憩開始終了時刻3
         */
    @TableField("hpi_ckyukeijikan3")
        private String hpiCkyukeijikan3;

        /**
         * 休憩開始終了時刻4
         */
    @TableField("hpi_ckyukeijikan4")
        private String hpiCkyukeijikan4;

        /**
         * 休憩開始終了時刻5
         */
    @TableField("hpi_ckyukeijikan5")
        private String hpiCkyukeijikan5;

        /**
         * 業務歴パターンid［＊］
         */
    @TableField("hpi_cgyomupatternkb")
        private String hpiCgyomupatternkb;

        /**
         * 業務歴パターン名称
         */
    @TableField("hpi_cgyomupatternnm")
        private String hpiCgyomupatternnm;

        /**
         * 非常勤講師必須ｺｰﾄﾞ［＊］
         */
    @TableField("hpi_chijokinkb")
        private String hpiChijokinkb;

        /**
         * 非常勤講師必須ｺｰﾄﾞ名称
         */
    @TableField("hpi_chijokinnm")
        private String hpiChijokinnm;

        /**
         * 開始日付
         */
    @TableField("hpi_dkinmukaisi")
        private Date hpiDkinmukaisi;

        /**
         * 終(修)了年月日
         */
    @TableField("hpi_dkinmushuryo")
        private Date hpiDkinmushuryo;

        /**
         * 勤務区分［＊］
         */
    @TableField("hpi_ckinmukubunkb")
        private String hpiCkinmukubunkb;

        /**
         * 勤務区分
         */
    @TableField("hpi_ckinmukubunnm")
        private String hpiCkinmukubunnm;

        /**
         * 勤務回数
         */
    @TableField("hpi_nkinmukaisu")
        private Long hpiNkinmukaisu;

        /**
         * 日／週
         */
    @TableField("hpi_cnitishu")
        private String hpiCnitishu;

        /**
         * 時間／日
         */
    @TableField("hpi_cjikanbi")
        private String hpiCjikanbi;

        /**
         * 時間数
         */
    @TableField("hpi_cjikansu")
        private String hpiCjikansu;

        /**
         * 担当科目曜日・時限
         */
    @TableField("hpi_ctantokamoku")
        private String hpiCtantokamoku;


        }