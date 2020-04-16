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
 * 言語区分マスタ
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
@TableName("mast_language")
public class MastLanguageDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "ml_id", type = IdType.AUTO)
                private Long mlId;

        /**
         * 言語区分
         */
    @TableField("ml_clanguagecode")
        private String mlClanguagecode;

        /**
         * 言語区分（db検索用）
         */
    @TableField("ml_cdblanguagecode")
        private String mlCdblanguagecode;

        /**
         * 言語名称
         */
    @TableField("ml_clanguagename")
        private String mlClanguagename;

        /**
         * 日付フォーマット（表示用）
         */
    @TableField("ml_cdspdateformat")
        private String mlCdspdateformat;

        /**
         * 日付フォーマット（入力用）
         */
    @TableField("ml_cinpdateformat")
        private String mlCinpdateformat;

        /**
         * 時刻フォーマット（表示用）
         */
    @TableField("ml_cdsptimeformat")
        private String mlCdsptimeformat;

        /**
         * 時刻フォーマット（入力用）
         */
    @TableField("ml_cinptimeformat")
        private String mlCinptimeformat;

        /**
         * 使用可否フラグ
         */
    @TableField("ml_cuseflg")
        private String mlCuseflg;

        /**
         * 最終更新者
         */
    @TableField("ml_cmodifieruserid")
        private String mlCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("ml_dmodifieddate")
        private Date mlDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private String versionno;


        }