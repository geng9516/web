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
 * 法定内残業計算対象非勤務マスタ
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
@TableName("tmg_mast_notwork4overinner")
public class TmgMastNotwork4overinnerDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmno_ccustomerid")
        private String tmnoCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmno_ccompanyid")
        private String tmnoCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmno_dstartdate")
        private Date tmnoDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmno_denddate")
        private Date tmnoDenddate;

        /**
         * 更新者
         */
    @TableField("tmno_cmodifieruserid")
        private String tmnoCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmno_dmodifieddate")
        private Date tmnoDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmno_cmodifierprogramid")
        private String tmnoCmodifierprogramid;

        /**
         * マスターコード
         */
                @TableId(value = "tmno_cmaster", type = IdType.AUTO)
                private String tmnoCmaster;


        }