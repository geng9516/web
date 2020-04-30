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
 * コードdb
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
@TableName("dt_djnd4001")
public class DtDjnd4001DO implements Serializable {

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
         * コード種別                         コードを識別する種別
         */
                @TableId(value = "cde_sybt", type = IdType.AUTO)
                private String cdeSybt;

        /**
         * コード番号                         コード
         */
    @TableField("cde_val")
        private String cdeVal;

        /**
         * データ1
         */
    @TableField("dta1")
        private String dta1;

        /**
         * データ2
         */
    @TableField("dta2")
        private String dta2;

        /**
         * データ3
         */
    @TableField("dta3")
        private String dta3;

        /**
         * データ4
         */
    @TableField("dta4")
        private String dta4;

        /**
         * データ5
         */
    @TableField("dta5")
        private String dta5;

        /**
         * データ6
         */
    @TableField("dta6")
        private String dta6;

        /**
         * データ7
         */
    @TableField("dta7")
        private String dta7;

        /**
         * データ8
         */
    @TableField("dta8")
        private String dta8;

        /**
         * データ9
         */
    @TableField("dta9")
        private String dta9;

        /**
         * データ10
         */
    @TableField("dta10")
        private String dta10;

        /**
         * データ11
         */
    @TableField("dta11")
        private String dta11;

        /**
         * データ12
         */
    @TableField("dta12")
        private String dta12;

        /**
         * 制御コード1
         */
    @TableField("ctrl_cde1")
        private String ctrlCde1;

        /**
         * 制御コード2
         */
    @TableField("ctrl_cde2")
        private String ctrlCde2;

        /**
         * 制御コード3
         */
    @TableField("ctrl_cde3")
        private String ctrlCde3;

        /**
         * 制御コード4
         */
    @TableField("ctrl_cde4")
        private String ctrlCde4;

        /**
         * 制御コード5
         */
    @TableField("ctrl_cde5")
        private String ctrlCde5;

        /**
         * 画面用名称                         ★2000.12.12追加
         */
    @TableField("name")
        private String name;

        /**
         * ポップアップ用名称                     ★2000.12.12追加
         */
    @TableField("dname")
        private String dname;

        /**
         * 正式名称                          ★2000.12.12追加
         */
    @TableField("sname")
        private String sname;

        /**
         * 略称                            ★2000.12.12追加
         */
    @TableField("rname")
        private String rname;

        /**
         * 記録名称1                         ★2000.12.12追加
         */
    @TableField("kname01")
        private String kname01;

        /**
         * 記録名称2                         ★2000.12.12追加
         */
    @TableField("kname02")
        private String kname02;

        /**
         * 記録名称3                         ★2000.12.12追加
         */
    @TableField("kname03")
        private String kname03;

        /**
         * ポップアップ用名称ｺﾒﾝﾄ                 2001.03.30(i) 20ﾊﾞｲﾄまで利用
         */
    @TableField("dname_comment")
        private String dnameComment;

        /**
         * 基準年月日                         ★2000.11.07(i)
         */
    @TableField("start_dte")
        private String startDte;

        /**
         * 基準年月日（西暦）
         */
    @TableField("dstart_dte")
        private Date dstartDte;

        /**
         * 終了年月日                         ★2000.11.07(i)
         */
    @TableField("end_dte")
        private String endDte;

        /**
         * 終了年月日（西暦）
         */
    @TableField("dend_dte")
        private Date dendDte;

        /**
         * 更新者
         */
    @TableField("kosin_usr")
        private String kosinUsr;

        /**
         * 更新プログラム
         */
    @TableField("kosin_pgm")
        private String kosinPgm;

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
         * ﾕｰｻﾞｰ
         */
    @TableField("cmnuser")
        private String cmnuser;

        /**
         * 更新日
         */
    @TableField("dmndate")
        private Date dmndate;


        }