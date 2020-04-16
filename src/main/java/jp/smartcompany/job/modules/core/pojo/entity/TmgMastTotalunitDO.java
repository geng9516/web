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
 * 集計項目カテゴリマスタ
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
@TableName("tmg_mast_totalunit")
public class TmgMastTotalunitDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmtu_ccustomerid")
        private String tmtuCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmtu_ccompanyid")
        private String tmtuCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmtu_dstartdate")
        private Date tmtuDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmtu_denddate")
        private Date tmtuDenddate;

        /**
         * 更新者
         */
    @TableField("tmtu_cmodifieruserid")
        private String tmtuCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmtu_dmodifieddate")
        private Date tmtuDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmtu_cmodifierprogramid")
        private String tmtuCmodifierprogramid;

        /**
         * 集計単位ｺｰﾄﾞ
         */
                @TableId(value = "tmtu_cunitid", type = IdType.AUTO)
                private String tmtuCunitid;

        /**
         * 集計単位名称
         */
    @TableField("tmtu_cunitnm")
        private String tmtuCunitnm;


        }