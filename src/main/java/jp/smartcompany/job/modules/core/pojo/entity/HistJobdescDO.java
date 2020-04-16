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
 * 職種歴
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
@TableName("hist_jobdesc")
public class HistJobdescDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hjd_id", type = IdType.AUTO)
                private Long hjdId;

        /**
         * 顧客コード
         */
    @TableField("hjd_ccustomerid")
        private String hjdCcustomerid;

        /**
         * 法人コード
         */
    @TableField("hjd_ccompanyid")
        private String hjdCcompanyid;

        /**
         * 社員番号
         */
    @TableField("hjd_cemployeeid")
        private String hjdCemployeeid;

        /**
         * ユーザid
         */
    @TableField("hjd_cuserid")
        private String hjdCuserid;

        /**
         * データ開始日
         */
    @TableField("hjd_dstartdate")
        private Date hjdDstartdate;

        /**
         * データ終了日
         */
    @TableField("hjd_denddate")
        private Date hjdDenddate;

        /**
         * 最終更新者
         */
    @TableField("hjd_cmodifieruserid")
        private String hjdCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hjd_dmodifieddate")
        private Date hjdDmodifieddate;

        /**
         * 発令日
         */
    @TableField("hjd_ddateofannouncement")
        private Date hjdDdateofannouncement;

        /**
         * 職種
         */
    @TableField("hjd_cjobdesc")
        private String hjdCjobdesc;

        /**
         * 職種コード
         */
    @TableField("hjd_cjobdesccd")
        private String hjdCjobdesccd;

        /**
         * 職種滞留
         */
    @TableField("hjd_njobperiod")
        private Long hjdNjobperiod;

        /**
         * 予備内容1（別名用ダミー）
         */
    @TableField("hjd_csparedesc1")
        private String hjdCsparedesc1;

        /**
         * 予備内容1（日本語）
         */
    @TableField("hjd_csparedesc1ja")
        private String hjdCsparedesc1ja;

        /**
         * 予備内容1（英語）
         */
    @TableField("hjd_csparedesc1en")
        private String hjdCsparedesc1en;

        /**
         * 予備内容1（中国語）
         */
    @TableField("hjd_csparedesc1ch")
        private String hjdCsparedesc1ch;

        /**
         * 予備内容1（予備１）
         */
    @TableField("hjd_csparedesc101")
        private String hjdCsparedesc101;

        /**
         * 予備内容1（予備2）
         */
    @TableField("hjd_csparedesc102")
        private String hjdCsparedesc102;

        /**
         * 予備内容2（別名用ダミー）
         */
    @TableField("hjd_csparedesc2")
        private String hjdCsparedesc2;

        /**
         * 予備内容2（日本語）
         */
    @TableField("hjd_csparedesc2ja")
        private String hjdCsparedesc2ja;

        /**
         * 予備内容2（英語）
         */
    @TableField("hjd_csparedesc2en")
        private String hjdCsparedesc2en;

        /**
         * 予備内容2（中国語）
         */
    @TableField("hjd_csparedesc2ch")
        private String hjdCsparedesc2ch;

        /**
         * 予備内容2（予備１）
         */
    @TableField("hjd_csparedesc201")
        private String hjdCsparedesc201;

        /**
         * 予備内容2（予備2）
         */
    @TableField("hjd_csparedesc202")
        private String hjdCsparedesc202;

        /**
         * 予備内容3（別名用ダミー）
         */
    @TableField("hjd_csparedesc3")
        private String hjdCsparedesc3;

        /**
         * 予備内容3（日本語）
         */
    @TableField("hjd_csparedesc3ja")
        private String hjdCsparedesc3ja;

        /**
         * 予備内容3（英語）
         */
    @TableField("hjd_csparedesc3en")
        private String hjdCsparedesc3en;

        /**
         * 予備内容3（中国語）
         */
    @TableField("hjd_csparedesc3ch")
        private String hjdCsparedesc3ch;

        /**
         * 予備内容3（予備１）
         */
    @TableField("hjd_csparedesc301")
        private String hjdCsparedesc301;

        /**
         * 予備内容3（予備2）
         */
    @TableField("hjd_csparedesc302")
        private String hjdCsparedesc302;

        /**
         * 予備数値1
         */
    @TableField("hjd_nnumber01")
        private Long hjdNnumber01;

        /**
         * 予備数値2
         */
    @TableField("hjd_nnumber02")
        private Long hjdNnumber02;

        /**
         * 予備数値3
         */
    @TableField("hjd_nnumber03")
        private Long hjdNnumber03;

        /**
         * 予備文字列1
         */
    @TableField("hjd_cchar01")
        private String hjdCchar01;

        /**
         * 予備文字列2
         */
    @TableField("hjd_cchar02")
        private String hjdCchar02;

        /**
         * 予備文字列3
         */
    @TableField("hjd_cchar03")
        private String hjdCchar03;

        /**
         * 予備日付1
         */
    @TableField("hjd_ddate01")
        private Date hjdDdate01;

        /**
         * 予備日付2
         */
    @TableField("hjd_ddate02")
        private Date hjdDdate02;

        /**
         * 予備日付3
         */
    @TableField("hjd_ddate03")
        private Date hjdDdate03;

        /**
         * 予備コード1
         */
    @TableField("hjd_ccode01")
        private String hjdCcode01;

        /**
         * 予備コード1コード
         */
    @TableField("hjd_ccode01cd")
        private String hjdCcode01cd;

        /**
         * 予備コード2
         */
    @TableField("hjd_ccode02")
        private String hjdCcode02;

        /**
         * 予備コード2コード
         */
    @TableField("hjd_ccode02cd")
        private String hjdCcode02cd;

        /**
         * 予備コード3
         */
    @TableField("hjd_ccode03")
        private String hjdCcode03;

        /**
         * 予備コード3コード
         */
    @TableField("hjd_ccode03cd")
        private String hjdCcode03cd;

        /**
         * hjdprimary key_ccode03cd
         */
    @TableField("hjdprimary_key_ccode03cd")
        private String hjdprimaryKeyCcode03cd;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }