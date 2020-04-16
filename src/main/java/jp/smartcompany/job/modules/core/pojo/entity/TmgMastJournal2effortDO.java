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
 * エフォート項目中間コードマッピングマスタ          主契約・従契約の区分と、仕訳ｺｰﾄﾞの組み合わせ→ｴﾌｫｰﾄ
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
@TableName("tmg_mast_journal2effort")
public class TmgMastJournal2effortDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmje_ccustomerid")
        private String tmjeCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmje_ccompanyid")
        private String tmjeCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tmje_dstartdate")
        private Date tmjeDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tmje_denddate")
        private Date tmjeDenddate;

        /**
         * 更新者
         */
    @TableField("tmje_cmodifieruserid")
        private String tmjeCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmje_dmodifieddate")
        private Date tmjeDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmje_cmodifierprogramid")
        private String tmjeCmodifierprogramid;

        /**
         * 仕訳項目ｺｰﾄﾞ
         */
                @TableId(value = "tmje_cjournalizingid", type = IdType.AUTO)
                private String tmjeCjournalizingid;

        /**
         * ｴﾌｫｰﾄ区分                       ｴﾌｫｰﾄ用変換ｺｰﾄﾞ ﾊﾟｯｹｰｼﾞ定数：1：主契約 2
         */
    @TableField("tmje_ceffortid")
        private String tmjeCeffortid;

        /**
         * 中間ｺｰﾄﾞ
         */
    @TableField("tmje_cmiddleid")
        private String tmjeCmiddleid;


        }