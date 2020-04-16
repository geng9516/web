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
 * 掲示板記事参照グループ
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
@TableName("hist_bulletinboard_gr")
public class HistBulletinboardGrDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * id
         */
                @TableId(value = "hbg_id", type = IdType.AUTO)
                private Long hbgId;

        /**
         * 記事id
         */
    @TableField("hbg_carticleid")
        private Long hbgCarticleid;

        /**
         * グループid
         */
    @TableField("hbg_cgroupid")
        private String hbgCgroupid;

        /**
         * 最終更新者
         */
    @TableField("hbg_cmodifieruserid")
        private String hbgCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hbg_dmodifieddate")
        private Date hbgDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }