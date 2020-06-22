package jp.smartcompany.job.modules.core.pojo.bo;

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
@Accessors(chain = true)
@EqualsAndHashCode(of="perms")
public class MenuBO implements Serializable {


        private String url;


        private String type;

        /**
         * 菜单显示顺序
         */

        private String orderNum;


        private String icon;


        private String perms;

        /**
         * 名称(日语)
         */
        private String jaName;

        /**
         * 名称(英语)
         */
        private String enName;

        /**
         * 名称(中文)
         */
        private String zhName;

        /**
         * 客户编号
         */
        private String customerId;

        /**
         * 公司编号
         */
        private String companyId;

        /**
         * 备注
         */
        private String remark;


        private String pageId;

        private Long menuId;


}