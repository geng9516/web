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
 * 静的組織ツリー生成状態データ
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
@TableName("cont_treegeneration")
public class ContTreegenerationDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "ct_id", type = IdType.AUTO)
                private Long ctId;

        /**
         * ツリー生成状態
         */
    @TableField("ct_cstatus")
        private String ctCstatus;

        /**
         * ツリー作成日時
         */
    @TableField("ct_ddate")
        private Date ctDdate;

        /**
         * 最終更新者
         */
    @TableField("ct_cmodifieruserid")
        private String ctCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("ct_dmodifieddate")
        private Date ctDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }