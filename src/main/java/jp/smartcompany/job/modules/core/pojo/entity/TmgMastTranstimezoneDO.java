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
 * 勤務時間帯区分マスタ
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
@TableName("tmg_mast_transtimezone")
public class TmgMastTranstimezoneDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableField("tmtz_ccustomerid")
        private String tmtzCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmtz_ccompanyid")
        private String tmtzCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmtz_dstartdate")
        private Date tmtzDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmtz_denddate")
        private Date tmtzDenddate;

        /**
         * 更新者
         */
    @TableField("tmtz_cmodifieruserid")
        private String tmtzCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmtz_dmodifieddate")
        private Date tmtzDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmtz_cmodifierprogramid")
        private String tmtzCmodifierprogramid;

        /**
         * 処理フェーズ（変換functionのオブジェクト名）
         */
    @TableField("tmtz_cphase")
        private String tmtzCphase;

        /**
         * 変換前マスタコード
         */
                @TableId(value = "tmtz_cmaster4source", type = IdType.AUTO)
                private String tmtzCmaster4source;

        /**
         * 変換後マスタコード
         */
    @TableField("tmtz_cmaster4dest")
        private String tmtzCmaster4dest;


        }