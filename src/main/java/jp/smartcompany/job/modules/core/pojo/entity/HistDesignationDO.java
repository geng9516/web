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
 * 異動歴
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
@TableName("hist_designation")
public class HistDesignationDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hd_id", type = IdType.AUTO)
                private Long hdId;

        /**
         * 顧客コード
         */
    @TableField("hd_ccustomerid_ck")
        private String hdCcustomeridCk;

        /**
         * 法人コード
         */
    @TableField("hd_ccompanyid_ck")
        private String hdCcompanyidCk;

        /**
         * 社員番号
         */
    @TableField("hd_cemployeeid_ck")
        private String hdCemployeeidCk;

        /**
         * ユーザid
         */
    @TableField("hd_cuserid")
        private String hdCuserid;

        /**
         * データ開始日
         */
    @TableField("hd_dstartdate_ck")
        private Date hdDstartdateCk;

        /**
         * データ終了日
         */
    @TableField("hd_denddate")
        private Date hdDenddate;

        /**
         * 最終更新者
         */
    @TableField("hd_cmodifieruserid")
        private String hdCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hd_dmodifieddate")
        private Date hdDmodifieddate;

        /**
         * 所属発令日
         */
    @TableField("hd_ddateofannounce_section")
        private Date hdDdateofannounceSection;

        /**
         * 所属終了日
         */
    @TableField("hd_ddateofrelieval_section")
        private Date hdDdateofrelievalSection;

        /**
         * 本務兼務区分
         */
    @TableField("hd_cifkeyoradditionalrole")
        private String hdCifkeyoradditionalrole;

        /**
         * 所属コード
         */
    @TableField("hd_csectionid_fk")
        private String hdCsectionidFk;

        /**
         * 所属名称
         */
    @TableField("hd_csectionname")
        private String hdCsectionname;

        /**
         * 所属滞留
         */
    @TableField("hd_csectionperiod")
        private Long hdCsectionperiod;

        /**
         * 役職発令日
         */
    @TableField("hd_ddateofannounce_post")
        private Date hdDdateofannouncePost;

        /**
         * 役職終了日
         */
    @TableField("hd_ddateofrelieval_post")
        private Date hdDdateofrelievalPost;

        /**
         * 役職コード
         */
    @TableField("hd_cpostid_fk")
        private String hdCpostidFk;

        /**
         * 役職名称
         */
    @TableField("hd_cpostname")
        private String hdCpostname;

        /**
         * 役職順位
         */
    @TableField("hd_npostweightage")
        private Long hdNpostweightage;

        /**
         * 発令区分コード
         */
    @TableField("hd_cstatus")
        private String hdCstatus;

        /**
         * 役職滞留
         */
    @TableField("hd_cpostperiod")
        private Long hdCpostperiod;

        /**
         * 職責ランクコード
         */
    @TableField("hd_cjobresponsibilityid")
        private String hdCjobresponsibilityid;

        /**
         * 職責ランク
         */
    @TableField("hd_cjobresponsibilitynm")
        private String hdCjobresponsibilitynm;

        /**
         * 正規区分
         */
    @TableField("hd_noffcialornot")
        private Long hdNoffcialornot;

        /**
         * 正規区分
         */
    @TableField("hd_nofficialornot")
        private Long hdNofficialornot;

        /**
         * 所属長フラグ
         */
    @TableField("hd_cbossornot")
        private String hdCbossornot;

        /**
         * 予備内容1（別名用ダミー）
         */
    @TableField("hd_csparedesc1")
        private String hdCsparedesc1;

        /**
         * 予備内容1（日本語）
         */
    @TableField("hd_csparedesc1ja")
        private String hdCsparedesc1ja;

        /**
         * 予備内容1（英語）
         */
    @TableField("hd_csparedesc1en")
        private String hdCsparedesc1en;

        /**
         * 予備内容1（中国語）
         */
    @TableField("hd_csparedesc1ch")
        private String hdCsparedesc1ch;

        /**
         * 予備内容1（予備１）
         */
    @TableField("hd_csparedesc101")
        private String hdCsparedesc101;

        /**
         * 予備内容1（予備2）
         */
    @TableField("hd_csparedesc102")
        private String hdCsparedesc102;

        /**
         * 予備内容2（別名用ダミー）
         */
    @TableField("hd_csparedesc2")
        private String hdCsparedesc2;

        /**
         * 予備内容2（日本語）
         */
    @TableField("hd_csparedesc2ja")
        private String hdCsparedesc2ja;

        /**
         * 予備内容2（英語）
         */
    @TableField("hd_csparedesc2en")
        private String hdCsparedesc2en;

        /**
         * 予備内容2（中国語）
         */
    @TableField("hd_csparedesc2ch")
        private String hdCsparedesc2ch;

        /**
         * 予備内容2（予備１）
         */
    @TableField("hd_csparedesc201")
        private String hdCsparedesc201;

        /**
         * 予備内容2（予備2）
         */
    @TableField("hd_csparedesc202")
        private String hdCsparedesc202;

        /**
         * 予備内容3（別名用ダミー）
         */
    @TableField("hd_csparedesc3")
        private String hdCsparedesc3;

        /**
         * 予備内容3（日本語）
         */
    @TableField("hd_csparedesc3ja")
        private String hdCsparedesc3ja;

        /**
         * 予備内容3（英語）
         */
    @TableField("hd_csparedesc3en")
        private String hdCsparedesc3en;

        /**
         * 予備内容3（中国語）
         */
    @TableField("hd_csparedesc3ch")
        private String hdCsparedesc3ch;

        /**
         * 予備内容3（予備１）
         */
    @TableField("hd_csparedesc301")
        private String hdCsparedesc301;

        /**
         * 予備内容3（予備2）
         */
    @TableField("hd_csparedesc302")
        private String hdCsparedesc302;

        /**
         * 予備数値1
         */
    @TableField("hd_nnumber01")
        private Long hdNnumber01;

        /**
         * 予備数値2
         */
    @TableField("hd_nnumber02")
        private Long hdNnumber02;

        /**
         * 予備数値3
         */
    @TableField("hd_nnumber03")
        private Long hdNnumber03;

        /**
         * 予備文字列1
         */
    @TableField("hd_cchar01")
        private String hdCchar01;

        /**
         * 予備文字列2
         */
    @TableField("hd_cchar02")
        private String hdCchar02;

        /**
         * 予備文字列3
         */
    @TableField("hd_cchar03")
        private String hdCchar03;

        /**
         * 予備日付1
         */
    @TableField("hd_ddate01")
        private Date hdDdate01;

        /**
         * 予備日付2
         */
    @TableField("hd_ddate02")
        private Date hdDdate02;

        /**
         * 予備日付3
         */
    @TableField("hd_ddate03")
        private Date hdDdate03;

        /**
         * 予備コード1
         */
    @TableField("hd_ccode01")
        private String hdCcode01;

        /**
         * 予備コード1コード
         */
    @TableField("hd_ccodenm01")
        private String hdCcodenm01;

        /**
         * 予備コード2
         */
    @TableField("hd_ccode02")
        private String hdCcode02;

        /**
         * 予備コード2コード
         */
    @TableField("hd_ccodenm02")
        private String hdCcodenm02;

        /**
         * 予備コード3
         */
    @TableField("hd_ccode03")
        private String hdCcode03;

        /**
         * 予備コード3コード
         */
    @TableField("hd_ccodenm03")
        private String hdCcodenm03;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }