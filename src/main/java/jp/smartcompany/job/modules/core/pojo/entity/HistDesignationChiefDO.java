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
 * public.hist_designation_chief
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
@TableName("hist_designation_chief")
public class HistDesignationChiefDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * hdc_ccustomerid
         */
    @TableId(value="hdc_ccustomerid",type=IdType.INPUT)
        private String hdcCcustomerid;

        /**
         * hdc_ccompanyid
         */
    @TableField("hdc_ccompanyid")
        private String hdcCcompanyid;

        /**
         * hdc_csectionid
         */
    @TableField("hdc_csectionid")
        private String hdcCsectionid;

        /**
         * hdc_cemployeeid
         */
    @TableField("hdc_cemployeeid")
        private String hdcCemployeeid;

        /**
         * hdc_dstartdate
         */
    @TableField("hdc_dstartdate")
        private Date hdcDstartdate;

        /**
         * hdc_denddate
         */
    @TableField("hdc_denddate")
        private Date hdcDenddate;


        }