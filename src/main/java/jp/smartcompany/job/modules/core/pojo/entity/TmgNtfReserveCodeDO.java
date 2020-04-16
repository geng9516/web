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
 * public.tmg_ntf_reserve_code
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
@TableName("tmg_ntf_reserve_code")
public class TmgNtfReserveCodeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * tnrc_ntfno
         */
                @TableId(value = "tnrc_ntfno", type = IdType.AUTO)
                private String tnrcNtfno;

        /**
         * tnrc_reserve_date
         */
    @TableField("tnrc_reserve_date")
        private Date tnrcReserveDate;

        /**
         * tnrc_reserve_cd
         */
    @TableField("tnrc_reserve_cd")
        private String tnrcReserveCd;


        }