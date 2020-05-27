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
 * [発令] 在籍出向・海外勤務歴
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
@TableName("hist_zaisekisyukou")
public class HistZaisekisyukouDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableId(value="hzs_ccustomerid_ck",type = IdType.INPUT)
        private String hzsCcustomeridCk;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("hzs_ccompanyid_ck")
        private String hzsCcompanyidCk;

        /**
         * 職員番号
         */
                @TableField(value = "hzs_cemployeeid_ck")
                private String hzsCemployeeidCk;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("hzs_dstartdate_ck")
        private Date hzsDstartdateCk;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("hzs_denddate")
        private Date hzsDenddate;

        /**
         * 更新者
         */
    @TableField("hzs_cmodifieruserid")
        private String hzsCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("hzs_dmodifieddate")
        private Date hzsDmodifieddate;

        /**
         * 在籍出向・海外勤務ｺｰﾄﾞ
         */
    @TableField("hzs_czaisekisyukoukb")
        private String hzsCzaisekisyukoukb;

        /**
         * 在籍出向・海外勤務名称
         */
    @TableField("hzs_czaisekisyukounm")
        private String hzsCzaisekisyukounm;


        }