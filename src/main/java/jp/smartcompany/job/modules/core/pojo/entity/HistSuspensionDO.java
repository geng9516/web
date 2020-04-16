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
 * 休職歴
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
@TableName("hist_suspension")
public class HistSuspensionDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hs_id", type = IdType.AUTO)
                private Long hsId;

        /**
         * 顧客コード
         */
    @TableField("hs_ccustomerid")
        private String hsCcustomerid;

        /**
         * 法人コード
         */
    @TableField("hs_ccompanyid")
        private String hsCcompanyid;

        /**
         * 社員番号
         */
    @TableField("hs_cemployeeid")
        private String hsCemployeeid;

        /**
         * ユーザid
         */
    @TableField("hs_cuserid")
        private String hsCuserid;

        /**
         * データ開始日
         */
    @TableField("hs_dstartdate")
        private Date hsDstartdate;

        /**
         * データ終了日
         */
    @TableField("hs_denddate")
        private Date hsDenddate;

        /**
         * 最終更新者
         */
    @TableField("hs_cmodifieruserid")
        private String hsCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hs_dmodifieddate")
        private Date hsDmodifieddate;

        /**
         * 休職理由
         */
    @TableField("hs_csuspensiontype")
        private String hsCsuspensiontype;

        /**
         * 休職理由コード
         */
    @TableField("hs_csuspensiontypecd")
        private String hsCsuspensiontypecd;

        /**
         * 休職理由備考
         */
    @TableField("hs_creason")
        private String hsCreason;

        /**
         * 予備内容1（別名用ダミー）
         */
    @TableField("hs_csparedesc1")
        private String hsCsparedesc1;

        /**
         * 予備内容1（日本語）
         */
    @TableField("hs_csparedesc1ja")
        private String hsCsparedesc1ja;

        /**
         * 予備内容1（英語）
         */
    @TableField("hs_csparedesc1en")
        private String hsCsparedesc1en;

        /**
         * 予備内容1（中国語）
         */
    @TableField("hs_csparedesc1ch")
        private String hsCsparedesc1ch;

        /**
         * 予備内容1（予備１）
         */
    @TableField("hs_csparedesc101")
        private String hsCsparedesc101;

        /**
         * 予備内容1（予備2）
         */
    @TableField("hs_csparedesc102")
        private String hsCsparedesc102;

        /**
         * 予備内容2（別名用ダミー）
         */
    @TableField("hs_csparedesc2")
        private String hsCsparedesc2;

        /**
         * 予備内容2（日本語）
         */
    @TableField("hs_csparedesc2ja")
        private String hsCsparedesc2ja;

        /**
         * 予備内容2（英語）
         */
    @TableField("hs_csparedesc2en")
        private String hsCsparedesc2en;

        /**
         * 予備内容2（中国語）
         */
    @TableField("hs_csparedesc2ch")
        private String hsCsparedesc2ch;

        /**
         * 予備内容2（予備１）
         */
    @TableField("hs_csparedesc201")
        private String hsCsparedesc201;

        /**
         * 予備内容2（予備2）
         */
    @TableField("hs_csparedesc202")
        private String hsCsparedesc202;

        /**
         * 予備内容3（別名用ダミー）
         */
    @TableField("hs_csparedesc3")
        private String hsCsparedesc3;

        /**
         * 予備内容3（日本語）
         */
    @TableField("hs_csparedesc3ja")
        private String hsCsparedesc3ja;

        /**
         * 予備内容3（英語）
         */
    @TableField("hs_csparedesc3en")
        private String hsCsparedesc3en;

        /**
         * 予備内容3（中国語）
         */
    @TableField("hs_csparedesc3ch")
        private String hsCsparedesc3ch;

        /**
         * 予備内容3（予備１）
         */
    @TableField("hs_csparedesc301")
        private String hsCsparedesc301;

        /**
         * 予備内容3（予備2）
         */
    @TableField("hs_csparedesc302")
        private String hsCsparedesc302;

        /**
         * 予備数値1
         */
    @TableField("hs_nnumber01")
        private Long hsNnumber01;

        /**
         * 予備数値2
         */
    @TableField("hs_nnumber02")
        private Long hsNnumber02;

        /**
         * 予備数値3
         */
    @TableField("hs_nnumber03")
        private Long hsNnumber03;

        /**
         * 予備文字列1
         */
    @TableField("hs_cchar01")
        private String hsCchar01;

        /**
         * 予備文字列2
         */
    @TableField("hs_cchar02")
        private String hsCchar02;

        /**
         * 予備文字列3
         */
    @TableField("hs_cchar03")
        private String hsCchar03;

        /**
         * 予備日付1
         */
    @TableField("hs_ddate01")
        private Date hsDdate01;

        /**
         * 予備日付2
         */
    @TableField("hs_ddate02")
        private Date hsDdate02;

        /**
         * 予備日付3
         */
    @TableField("hs_ddate03")
        private Date hsDdate03;

        /**
         * 予備コード1
         */
    @TableField("hs_ccode01")
        private String hsCcode01;

        /**
         * 予備コード1コード
         */
    @TableField("hs_ccode01cd")
        private String hsCcode01cd;

        /**
         * 予備コード2
         */
    @TableField("hs_ccode02")
        private String hsCcode02;

        /**
         * 予備コード2コード
         */
    @TableField("hs_ccode02cd")
        private String hsCcode02cd;

        /**
         * 予備コード3
         */
    @TableField("hs_ccode03")
        private String hsCcode03;

        /**
         * 予備コード3コード
         */
    @TableField("hs_ccode03cd")
        private String hsCcode03cd;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }