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
 * 初期値設定マスタ
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
@TableName("mast_default_values")
public class MastDefaultValuesDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mdv_id", type = IdType.AUTO)
                private Long mdvId;

        /**
         * データ種別コード
         */
    @TableField("mdv_ccode")
        private String mdvCcode;

        /**
         * 初期値データ
         */
    @TableField("mdv_cvalue")
        private String mdvCvalue;

        /**
         * データ種別名
         */
    @TableField("mdv_ccodedesc")
        private String mdvCcodedesc;

        /**
         * 最終更新者
         */
    @TableField("mdv_cmodifieruserid")
        private String mdvCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mdv_dmodifieddate")
        private Date mdvDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }