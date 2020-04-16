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
 * 長期休暇・出勤停止者データ
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
@TableName("tmg_long_absence")
public class TmgLongAbsenceDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "tla_id", type = IdType.AUTO)
                private Long tlaId;

        /**
         * 顧客コード
         */
    @TableField("tla_ccustomerid")
        private String tlaCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tla_ccompanyid")
        private String tlaCcompanyid;

        /**
         * 個人コード
         */
    @TableField("tla_cemployeeid")
        private String tlaCemployeeid;

        /**
         * ユーザid
         */
    @TableField("tla_cuserid")
        private String tlaCuserid;

        /**
         * データ開始日
         */
    @TableField("tla_dstartdate")
        private Date tlaDstartdate;

        /**
         * データ終了日
         */
    @TableField("tla_denddate")
        private Date tlaDenddate;

        /**
         * 20日を超える日(休日除く)
         */
    @TableField("tla_d20days")
        private Date tlaD20days;

        /**
         * 90日経過後(休日含む)の最初の労働日
         */
    @TableField("tla_d90days")
        private Date tlaD90days;

        /**
         * バージョンno v4互換用
         */
    @TableField("versionno")
        private Long versionno;


        }