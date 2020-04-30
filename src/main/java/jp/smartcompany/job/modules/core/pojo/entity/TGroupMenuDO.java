package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-24
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_group_menu")
public class TGroupMenuDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * t_menu表中的menu_id
         */
                @TableId(value = "menu_id", type = IdType.INPUT)
                private String menuId;

        /**
         * mast_group表中的mg_cgroupid_pk
         */
    @TableField("group_code")
        private String groupCode;

        /**
         * 客户编号
         */
    @TableField("customer_id")
        private String customerId;

        /**
         * 公司编号
         */
    @TableField("company_id")
        private String companyId;

        /**
         * 备注
         */
    @TableField("remark")
        private String remark;

        /**
         * 更新者id
         */
    @TableField("update_user_id")
        private String updateUserId;

        /**
         * 更新时间
         */
    @TableField("update_time")
        private String updateTime;

        /**
         * 修改时间
         */
    @TableField("create_time")
        private String createTime;

    /**
     * 数据有效开始时间
     */
    private Date startDate;

    /**
     * 数据有效结束时间
     */
    private Date endDate;

        /**
         * 乐观锁版本号
         */
    @Version
    @TableField("version")
        private String version;


        }