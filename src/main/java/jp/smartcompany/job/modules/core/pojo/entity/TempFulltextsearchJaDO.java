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
 * 全文検索対象データ
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
@TableName("temp_fulltextsearch_ja")
public class TempFulltextsearchJaDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "tftsja_id", type = IdType.AUTO)
                private Long tftsjaId;

        /**
         * ドメインid
         */
    @TableField("tftsja_cdomainid")
        private String tftsjaCdomainid;

        /**
         * 顧客コード
         */
    @TableField("tftsja_ccustomerid")
        private String tftsjaCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tftsja_ccompanyid")
        private String tftsjaCcompanyid;

        /**
         * 検索対象物
         */
    @TableField("tftsja_ctarget")
        private String tftsjaCtarget;

        /**
         * アプリケーションid
         */
    @TableField("tftsja_capplicationid")
        private String tftsjaCapplicationid;

        /**
         * サブアプリケーションid
         */
    @TableField("tftsja_csubapplicationid")
        private String tftsjaCsubapplicationid;

        /**
         * 画面id
         */
    @TableField("tftsja_cscreenid")
        private String tftsjaCscreenid;

        /**
         * permalink
         */
    @TableField("tftsja_cpermalink")
        private String tftsjaCpermalink;

        /**
         * 検索対象データ
         */
    @TableField("tftsja_cdata")
        private String tftsjaCdata;

        /**
         * 取得元テーブル
         */
    @TableField("tftsja_csrctable")
        private String tftsjaCsrctable;

        /**
         * 取得元idカラム
         */
    @TableField("tftsja_csrcid")
        private Long tftsjaCsrcid;

        /**
         * 取得元カラム
         */
    @TableField("tftsja_csrccolumn")
        private String tftsjaCsrccolumn;

        /**
         * データ更新日
         */
    @TableField("tftsja_dmodifieddate")
        private Date tftsjaDmodifieddate;


        }