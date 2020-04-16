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
 * 昇格歴
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
@TableName("hist_jobgrade")
public class HistJobgradeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hjg_id", type = IdType.AUTO)
                private Long hjgId;

        /**
         * 顧客コード
         */
    @TableField("hjg_ccustomerid")
        private String hjgCcustomerid;

        /**
         * 法人コード
         */
    @TableField("hjg_ccompanyid")
        private String hjgCcompanyid;

        /**
         * 社員番号
         */
    @TableField("hjg_cemployeeid")
        private String hjgCemployeeid;

        /**
         * ユーザid
         */
    @TableField("hjg_cuserid")
        private String hjgCuserid;

        /**
         * データ開始日
         */
    @TableField("hjg_dstartdate")
        private Date hjgDstartdate;

        /**
         * データ終了日
         */
    @TableField("hjg_denddate")
        private Date hjgDenddate;

        /**
         * 最終更新者
         */
    @TableField("hjg_cmodifieruserid")
        private String hjgCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hjg_dmodifieddate")
        private Date hjgDmodifieddate;

        /**
         * 発令日
         */
    @TableField("hjg_ddateofannouncement")
        private Date hjgDdateofannouncement;

        /**
         * 資格等級
         */
    @TableField("hjg_cgrade")
        private String hjgCgrade;

        /**
         * 資格等級コード
         */
    @TableField("hjg_cgradecd")
        private String hjgCgradecd;

        /**
         * 資格等級滞留
         */
    @TableField("hjg_ngradeperiod")
        private Long hjgNgradeperiod;

        /**
         * 級・号俸
         */
    @TableField("hjg_cclass")
        private String hjgCclass;

        /**
         * 級・号俸コード
         */
    @TableField("hjg_cclasscd")
        private String hjgCclasscd;

        /**
         * 級・号俸滞留
         */
    @TableField("hjg_nclassperiod")
        private Long hjgNclassperiod;

        /**
         * 予備内容1（別名用ダミー）
         */
    @TableField("hjg_csparedesc1")
        private String hjgCsparedesc1;

        /**
         * 予備内容1（日本語）
         */
    @TableField("hjg_csparedesc1ja")
        private String hjgCsparedesc1ja;

        /**
         * 予備内容1（英語）
         */
    @TableField("hjg_csparedesc1en")
        private String hjgCsparedesc1en;

        /**
         * 予備内容1（中国語）
         */
    @TableField("hjg_csparedesc1ch")
        private String hjgCsparedesc1ch;

        /**
         * 予備内容1（予備１）
         */
    @TableField("hjg_csparedesc101")
        private String hjgCsparedesc101;

        /**
         * 予備内容1（予備2）
         */
    @TableField("hjg_csparedesc102")
        private String hjgCsparedesc102;

        /**
         * 予備内容2（別名用ダミー）
         */
    @TableField("hjg_csparedesc2")
        private String hjgCsparedesc2;

        /**
         * 予備内容2（日本語）
         */
    @TableField("hjg_csparedesc2ja")
        private String hjgCsparedesc2ja;

        /**
         * 予備内容2（英語）
         */
    @TableField("hjg_csparedesc2en")
        private String hjgCsparedesc2en;

        /**
         * 予備内容2（中国語）
         */
    @TableField("hjg_csparedesc2ch")
        private String hjgCsparedesc2ch;

        /**
         * 予備内容2（予備１）
         */
    @TableField("hjg_csparedesc201")
        private String hjgCsparedesc201;

        /**
         * 予備内容2（予備2）
         */
    @TableField("hjg_csparedesc202")
        private String hjgCsparedesc202;

        /**
         * 予備内容3（別名用ダミー）
         */
    @TableField("hjg_csparedesc3")
        private String hjgCsparedesc3;

        /**
         * 予備内容3（日本語）
         */
    @TableField("hjg_csparedesc3ja")
        private String hjgCsparedesc3ja;

        /**
         * 予備内容3（英語）
         */
    @TableField("hjg_csparedesc3en")
        private String hjgCsparedesc3en;

        /**
         * 予備内容3（中国語）
         */
    @TableField("hjg_csparedesc3ch")
        private String hjgCsparedesc3ch;

        /**
         * 予備内容3（予備１）
         */
    @TableField("hjg_csparedesc301")
        private String hjgCsparedesc301;

        /**
         * 予備内容3（予備2）
         */
    @TableField("hjg_csparedesc302")
        private String hjgCsparedesc302;

        /**
         * 予備数値1
         */
    @TableField("hjg_nnumber01")
        private Long hjgNnumber01;

        /**
         * 予備数値2
         */
    @TableField("hjg_nnumber02")
        private Long hjgNnumber02;

        /**
         * 予備数値3
         */
    @TableField("hjg_nnumber03")
        private Long hjgNnumber03;

        /**
         * 予備文字列1
         */
    @TableField("hjg_cchar01")
        private String hjgCchar01;

        /**
         * 予備文字列2
         */
    @TableField("hjg_cchar02")
        private String hjgCchar02;

        /**
         * 予備文字列3
         */
    @TableField("hjg_cchar03")
        private String hjgCchar03;

        /**
         * 予備日付1
         */
    @TableField("hjg_ddate01")
        private Date hjgDdate01;

        /**
         * 予備日付2
         */
    @TableField("hjg_ddate02")
        private Date hjgDdate02;

        /**
         * 予備日付3
         */
    @TableField("hjg_ddate03")
        private Date hjgDdate03;

        /**
         * 予備コード1
         */
    @TableField("hjg_ccode01")
        private String hjgCcode01;

        /**
         * 予備コード1コード
         */
    @TableField("hjg_ccode01cd")
        private String hjgCcode01cd;

        /**
         * 予備コード2
         */
    @TableField("hjg_ccode02")
        private String hjgCcode02;

        /**
         * 予備コード2コード
         */
    @TableField("hjg_ccode02cd")
        private String hjgCcode02cd;

        /**
         * 予備コード3
         */
    @TableField("hjg_ccode03")
        private String hjgCcode03;

        /**
         * 予備コード3コード
         */
    @TableField("hjg_ccode03cd")
        private String hjgCcode03cd;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }