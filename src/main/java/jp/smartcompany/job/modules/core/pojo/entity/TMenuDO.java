package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-24
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id",callSuper = false)
@Accessors(chain = true)
@TableName("t_menu")
@KeySequence("T_MENU_ID_SEQ")
public class TMenuDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 物理主键
         */
                @TableId(value = "id", type = IdType.INPUT)
                private Long id;

        /**
         * 逻辑主键（一切查询和关联以此主键为准）
         */
    @TableField("menu_id")
        private Long menuId;

        /**
         * 对应菜单url
         */
    @TableField("url")
        private String url;

        /**
         * 父级菜单id
         */
    @TableField("parent_menu_id")
        private Long parentMenuId;

        /**
         * 菜单类型： 0 目录 1 菜单 2 按钮
         */
    @TableField("menu_type")
        private String type;

        /**
         * 菜单显示顺序
         */
    @TableField("order_num")
        private String orderNum;

        /**
         * 菜单层级，包含了菜单的层级信息,如：1.2.5
         */
    @TableField("menu_level")
        private String level;

        /**
         * 图标
         */
    @TableField("icon")
        private String icon;

        /**
         * shiro判断是否为唯一键的标识
         */
    @TableField("perms")
        private String perms;

        /**
         * 名称(日语)
         */
    @TableField("ja_name")
        private String jaName;

        /**
         * 名称(英语)
         */
    @TableField("en_name")
        private String enName;

        /**
         * 名称(中文)
         */
    @TableField("zh_name")
        private String zhName;

        /**
         * 数据有效开始时间
         */
    @TableField("start_date")
        private Date startDate;

        /**
         * 数据有效结束时间
         */
    @TableField("end_date")
        private Date endDate;

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
        private Date updateTime;

        /**
         * 修改时间
         */
    @TableField("create_time")
        private Date createTime;

        /**
         * 乐观锁版本号
         */
    @Version
    @TableField("version")
        private Long version;

        /**
         * 是否是跳转页面按钮
         */
    @TableField("dispatcher_button")
        private Boolean dispatcherButton;

        /**
         * 页面id，作为url参数传递给后端调用相应处理逻辑的标识
         */
    @TableField("page_id")
        private String pageId;


        }