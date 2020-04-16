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
 * public.csv_umstoin
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
@TableName("csv_umstoin")
public class CsvUmstoinDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * ccompkb
         */
    @TableField("ccompkb")
        private String ccompkb;

        /**
         * cqtaikeikb
         */
    @TableField("cqtaikeikb")
        private String cqtaikeikb;

        /**
         * dstart
         */
    @TableField("dstart")
        private Date dstart;

        /**
         * dend
         */
    @TableField("dend")
        private Date dend;

        /**
         * cmnclient
         */
    @TableField("cmnclient")
        private String cmnclient;

        /**
         * cmncomp
         */
    @TableField("cmncomp")
        private String cmncomp;

        /**
         * cmnuser
         */
    @TableField("cmnuser")
        private String cmnuser;

        /**
         * dmndate
         */
    @TableField("dmndate")
        private Date dmndate;

        /**
         * code
         */
    @TableField("code")
        private String code;

        /**
         * name
         */
    @TableField("name")
        private String name;

        /**
         * bkyk_cde
         */
    @TableField("bkyk_cde")
        private String bkykCde;


        }