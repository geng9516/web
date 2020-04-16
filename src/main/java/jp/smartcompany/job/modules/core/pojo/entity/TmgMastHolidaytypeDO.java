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
 * 休日区分マスタ
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
@TableName("tmg_mast_holidaytype")
public class TmgMastHolidaytypeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmht_ccustomerid")
        private String tmhtCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmht_ccompanyid")
        private String tmhtCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmht_dstartdate")
        private Date tmhtDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmht_denddate")
        private Date tmhtDenddate;

        /**
         * 更新者
         */
    @TableField("tmht_cmodifieruserid")
        private String tmhtCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmht_dmodifieddate")
        private Date tmhtDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmht_cmodifierprogramid")
        private String tmhtCmodifierprogramid;

        /**
         * 就業区分ｺｰﾄﾞ
         */
                @TableId(value = "tmht_cworkingid", type = IdType.AUTO)
                private String tmhtCworkingid;

        /**
         * 労働時間ｺｰﾄﾞ
         */
    @TableField("tmht_cworkinghoursid")
        private String tmhtCworkinghoursid;


        }