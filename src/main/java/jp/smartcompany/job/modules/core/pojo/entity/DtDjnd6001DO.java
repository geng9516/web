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
 * 非正規職員基本情報db
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
@TableName("dt_djnd6001")
public class DtDjnd6001DO implements Serializable {

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
         * 社員番号
         */
    @TableField("cshainno")
        private String cshainno;

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
         * アルファベット氏名
         */
    @TableField("cnameeiji")
        private String cnameeiji;

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
         * 任用期間開始日
         */
    @TableField("dninyo_start")
        private Date dninyoStart;

        /**
         * 任用期間開始日（和暦）
         */
    @TableField("sninyo_start")
        private String sninyoStart;

        /**
         * 任用期間終了日
         */
    @TableField("dninyo_end")
        private Date dninyoEnd;

        /**
         * 任用期間終了日（和暦）
         */
    @TableField("sninyo_end")
        private String sninyoEnd;

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
         * 部局名
         */
    @TableField("bkyk_nme")
        private String bkykNme;

        /**
         * ソート順
         */
    @TableField("sort_jyn")
        private Long sortJyn;

        /**
         * 派遣元
         */
    @TableField("hkn_nme")
        private String hknNme;

        /**
         * 予備項目_文字コード1
         */
    @TableField("c01_cde")
        private String c01Cde;

        /**
         * 予備項目_文字名称1
         */
    @TableField("c01_nme")
        private String c01Nme;

        /**
         * 予備項目_文字コード2
         */
    @TableField("c02_cde")
        private String c02Cde;

        /**
         * 予備項目_文字名称2
         */
    @TableField("c02_nme")
        private String c02Nme;

        /**
         * 予備項目_文字コード3
         */
    @TableField("c03_cde")
        private String c03Cde;

        /**
         * 予備項目_文字名称3
         */
    @TableField("c03_nme")
        private String c03Nme;

        /**
         * 予備項目_文字コード4
         */
    @TableField("c04_cde")
        private String c04Cde;

        /**
         * 予備項目_文字名称4
         */
    @TableField("c04_nme")
        private String c04Nme;

        /**
         * 予備項目_文字コード5
         */
    @TableField("c05_cde")
        private String c05Cde;

        /**
         * 予備項目_文字名称5
         */
    @TableField("c05_nme")
        private String c05Nme;

        /**
         * 予備項目_数値1
         */
    @TableField("n01_num")
        private Long n01Num;

        /**
         * 予備項目_数値2
         */
    @TableField("n02_num")
        private Long n02Num;

        /**
         * 予備項目_数値3
         */
    @TableField("n03_num")
        private Long n03Num;

        /**
         * 予備項目_数値4
         */
    @TableField("n04_num")
        private Long n04Num;

        /**
         * 予備項目_数値5
         */
    @TableField("n05_num")
        private Long n05Num;

        /**
         * 予備項目_日付和暦1
         */
    @TableField("c01_dte")
        private String c01Dte;

        /**
         * 予備項目_日付西暦1
         */
    @TableField("d01_dte")
        private Date d01Dte;

        /**
         * 予備項目_日付和暦2
         */
    @TableField("c02_dte")
        private String c02Dte;

        /**
         * 予備項目_日付西暦2
         */
    @TableField("d02_dte")
        private Date d02Dte;

        /**
         * 予備項目_日付和暦3
         */
    @TableField("c03_dte")
        private String c03Dte;

        /**
         * 予備項目_日付西暦3
         */
    @TableField("d03_dte")
        private Date d03Dte;

        /**
         * 予備項目_日付和暦4
         */
    @TableField("c04_dte")
        private String c04Dte;

        /**
         * 予備項目_日付西暦4
         */
    @TableField("d04_dte")
        private Date d04Dte;

        /**
         * 予備項目_日付和暦5
         */
    @TableField("c05_dte")
        private String c05Dte;

        /**
         * 予備項目_日付西暦5
         */
    @TableField("d05_dte")
        private Date d05Dte;

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
         * ﾕｰｻﾞ
         */
    @TableField("cmnuser")
        private String cmnuser;

        /**
         * 更新日
         */
    @TableField("dmndate")
        private Date dmndate;


        }