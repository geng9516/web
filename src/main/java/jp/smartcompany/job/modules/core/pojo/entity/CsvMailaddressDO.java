package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * public.csv_mailaddress
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
@TableName("csv_mailaddress")
public class CsvMailaddressDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * cemployeeid
         */
    @TableField("cemployeeid")
        private String cemployeeid;

        /**
         * cmailaddress
         */
    @TableField("cmailaddress")
        private String cmailaddress;


        }