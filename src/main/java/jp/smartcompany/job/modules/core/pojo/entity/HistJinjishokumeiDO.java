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
 * 人事職名歴                         companyから連携した人事発令データに基く職名歴です
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
@TableName("hist_jinjishokumei")
public class HistJinjishokumeiDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
                @TableId(value = "hjs_ccustomerid_ck", type = IdType.AUTO)
                private String hjsCcustomeridCk;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("hjs_ccompanyid_ck")
        private String hjsCcompanyidCk;

        /**
         * 職員番号
         */
    @TableField("hjs_cemployeeid_ck")
        private String hjsCemployeeidCk;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("hjs_dstartdate_ck")
        private Date hjsDstartdateCk;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("hjs_denddate")
        private Date hjsDenddate;

        /**
         * 更新者
         */
    @TableField("hjs_cmodifieruserid")
        private String hjsCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("hjs_dmodifieddate")
        private Date hjsDmodifieddate;

        /**
         * 職名ｺｰﾄﾞ(人事)
         */
    @TableField("hjs_cshokumeikb")
        private String hjsCshokumeikb;

        /**
         * 職名名称(人事)
         */
    @TableField("hjs_cshokumeinm")
        private String hjsCshokumeinm;


        }