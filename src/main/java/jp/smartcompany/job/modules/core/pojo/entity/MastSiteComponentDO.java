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
 * サイトコンポーネントマスタ
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
@TableName("mast_site_component")
public class MastSiteComponentDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mscm_id", type = IdType.AUTO)
                private Long mscmId;

        /**
         * コンポーネントid
         */
    @TableField("mscm_ccomponentid")
        private String mscmCcomponentid;

        /**
         * コンポーネント名
         */
    @TableField("mscm_cname")
        private String mscmCname;

        /**
         * コンポーネントurl
         */
    @TableField("mscm_curl")
        private String mscmCurl;

        /**
         * 最終更新者
         */
    @TableField("mscm_cmodifieruserid")
        private String mscmCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mscm_dmodifieddate")
        private Date mscmDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }