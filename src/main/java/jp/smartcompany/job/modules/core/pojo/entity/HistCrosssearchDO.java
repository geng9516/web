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
 * クロス集計検索設定保存データ
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
@TableName("hist_crosssearch")
public class HistCrosssearchDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hcs_id", type = IdType.AUTO)
                private Long hcsId;

        /**
         * 設定id
         */
    @TableField("hcs_nsettingid")
        private Long hcsNsettingid;

        /**
         * 顧客コード
         */
    @TableField("hcs_ccustomerid_ck")
        private String hcsCcustomeridCk;

        /**
         * 法人コード
         */
    @TableField("hcs_ccompanyid_ck")
        private String hcsCcompanyidCk;

        /**
         * ユーザid
         */
    @TableField("hcs_cuserid")
        private String hcsCuserid;

        /**
         * 設定名
         */
    @TableField("hcs_cfilename")
        private String hcsCfilename;

        /**
         * 備考
         */
    @TableField("hcs_ccomment")
        private String hcsCcomment;

        /**
         * 共有フラグ
         */
    @TableField("hcs_cifpublic")
        private String hcsCifpublic;

        /**
         * 大文字小文字判定有無
         */
    @TableField("hcs_cignorecase")
        private String hcsCignorecase;

        /**
         * データのある行のみ表示
         */
    @TableField("hcs_cnodatanooutput")
        private String hcsCnodatanooutput;

        /**
         * 最終更新者
         */
    @TableField("hcs_cmodifieruserid")
        private String hcsCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hcs_dmodifieddate")
        private Date hcsDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }