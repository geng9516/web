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
 * 法定内残業計算対象身分マスタ
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
@TableName("tmg_mast_worker4overinner")
public class TmgMastWorker4overinnerDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmwo_ccustomerid")
        private String tmwoCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmwo_ccompanyid")
        private String tmwoCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmwo_dstartdate")
        private Date tmwoDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmwo_denddate")
        private Date tmwoDenddate;

        /**
         * 更新者
         */
    @TableField("tmwo_cmodifieruserid")
        private String tmwoCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmwo_dmodifieddate")
        private Date tmwoDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmwo_cmodifierprogramid")
        private String tmwoCmodifierprogramid;

        /**
         * 勤怠種別
         */
                @TableId(value = "tmwo_cworktypeid", type = IdType.AUTO)
                private String tmwoCworktypeid;


        }