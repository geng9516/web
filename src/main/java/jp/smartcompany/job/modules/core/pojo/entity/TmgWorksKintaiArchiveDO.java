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
 * [勤怠]月次集計出力イメージ(works)  過去データ退避
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
@TableName("tmg_works_kintai_archive")
public class TmgWorksKintaiArchiveDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("txkw_ccustomerid")
        private String txkwCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("txkw_ccompanyid")
        private String txkwCcompanyid;

        /**
         * 社員番号
         */
    @TableField("txkw_cemployeeid")
        private String txkwCemployeeid;

        /**
         * 開始日
         */
    @TableField("txkw_dstartdate")
        private Date txkwDstartdate;

        /**
         * 終了日
         */
    @TableField("txkw_denddate")
        private Date txkwDenddate;

        /**
         * 更新者
         */
    @TableField("txkw_cmodifieruserid")
        private String txkwCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("txkw_dmodifieddate")
        private Date txkwDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("txkw_cmodifierprogramid")
        private String txkwCmodifierprogramid;

        /**
         * 履歴ｼｰｹﾝｽ
         */
    @TableField("txkw_nseq")
        private Long txkwNseq;

        /**
         * シフトコード
         */
    @TableField("txkw_cshiftcode")
        private String txkwCshiftcode;

        /**
         * シフト区分
         */
    @TableField("txkw_cshiftid")
        private String txkwCshiftid;

        /**
         * 月平均所定労働時間
         */
    @TableField("txkw_nnumber01")
        private Long txkwNnumber01;

        /**
         * 勤務時間数・非
         */
    @TableField("txkw_nnumber02")
        private Long txkwNnumber02;

        /**
         * 減額
         */
    @TableField("txkw_nnumber03")
        private Long txkwNnumber03;

        /**
         * 超勤(100/100)
         */
    @TableField("txkw_nnumber04")
        private Long txkwNnumber04;

        /**
         * 超勤(125/100)
         */
    @TableField("txkw_nnumber05")
        private Long txkwNnumber05;

        /**
         * 超勤(150/100)
         */
    @TableField("txkw_nnumber06")
        private Long txkwNnumber06;

        /**
         * 休日(135/100)
         */
    @TableField("txkw_nnumber07")
        private Long txkwNnumber07;

        /**
         * 休日(160/100)
         */
    @TableField("txkw_nnumber08")
        private Long txkwNnumber08;

        /**
         * 夜勤(25/100)
         */
    @TableField("txkw_nnumber09")
        private Long txkwNnumber09;

        /**
         * 超勤(175/100)
         */
    @TableField("txkw_nnumber10")
        private Long txkwNnumber10;

        /**
         * 超勤2(100/100)
         */
    @TableField("txkw_nnumber11")
        private Long txkwNnumber11;

        /**
         * 超勤2(125/100)
         */
    @TableField("txkw_nnumber12")
        private Long txkwNnumber12;

        /**
         * 超勤2(150/100)
         */
    @TableField("txkw_nnumber13")
        private Long txkwNnumber13;

        /**
         * 休日2(135/100)
         */
    @TableField("txkw_nnumber14")
        private Long txkwNnumber14;

        /**
         * 休日2(160/100)
         */
    @TableField("txkw_nnumber15")
        private Long txkwNnumber15;

        /**
         * 夜勤2(25/100)
         */
    @TableField("txkw_nnumber16")
        private Long txkwNnumber16;

        /**
         * 超勤2(175/100)
         */
    @TableField("txkw_nnumber17")
        private Long txkwNnumber17;

        /**
         * 宿日直(医師当直)
         */
    @TableField("txkw_nnumber18")
        private Long txkwNnumber18;

        /**
         * 宿日直(核物教員)
         */
    @TableField("txkw_nnumber19")
        private Long txkwNnumber19;

        /**
         * 宿日直(医病看護師長)
         */
    @TableField("txkw_nnumber20")
        private Long txkwNnumber20;

        /**
         * 宿日直(医病薬剤師)
         */
    @TableField("txkw_nnumber21")
        private Long txkwNnumber21;

        /**
         * 宿日直(その他)
         */
    @TableField("txkw_nnumber22")
        private Long txkwNnumber22;

        /**
         * 高所15(4h以上)
         */
    @TableField("txkw_nnumber23")
        private Long txkwNnumber23;

        /**
         * 高所15(4h未満)
         */
    @TableField("txkw_nnumber24")
        private Long txkwNnumber24;

        /**
         * 高所30(4h以上)
         */
    @TableField("txkw_nnumber25")
        private Long txkwNnumber25;

        /**
         * 高所30(4h未満)
         */
    @TableField("txkw_nnumber26")
        private Long txkwNnumber26;

        /**
         * 爆発物(4h以上)
         */
    @TableField("txkw_nnumber27")
        private Long txkwNnumber27;

        /**
         * 爆発物(4h未満)
         */
    @TableField("txkw_nnumber28")
        private Long txkwNnumber28;

        /**
         * 死体処理
         */
    @TableField("txkw_nnumber29")
        private Long txkwNnumber29;

        /**
         * 死体処理(運搬)
         */
    @TableField("txkw_nnumber30")
        private Long txkwNnumber30;

        /**
         * 放射線
         */
    @TableField("txkw_nnumber31")
        private Long txkwNnumber31;

        /**
         * 異常圧力(0.2mpaまで)
         */
    @TableField("txkw_nnumber32")
        private Long txkwNnumber32;

        /**
         * 異常圧力(0.3mpaまで)
         */
    @TableField("txkw_nnumber33")
        private Long txkwNnumber33;

        /**
         * 異常圧力(0.3mpa超)
         */
    @TableField("txkw_nnumber34")
        private Long txkwNnumber34;

        /**
         * 夜間看護(深夜全部)
         */
    @TableField("txkw_nnumber35")
        private Long txkwNnumber35;

        /**
         * 夜間看護(深夜4h以上)
         */
    @TableField("txkw_nnumber36")
        private Long txkwNnumber36;

        /**
         * 夜間看護(深夜2h～4h)
         */
    @TableField("txkw_nnumber37")
        private Long txkwNnumber37;

        /**
         * 夜間看護(深夜2h未満)
         */
    @TableField("txkw_nnumber38")
        private Long txkwNnumber38;

        /**
         * 夜間看護(5km未満)
         */
    @TableField("txkw_nnumber39")
        private Long txkwNnumber39;

        /**
         * 夜間看護(5～10km)
         */
    @TableField("txkw_nnumber40")
        private Long txkwNnumber40;

        /**
         * 夜間看護(10km以上)
         */
    @TableField("txkw_nnumber41")
        private Long txkwNnumber41;

        /**
         * ドクターヘリ
         */
    @TableField("txkw_nnumber42")
        private Long txkwNnumber42;


        }