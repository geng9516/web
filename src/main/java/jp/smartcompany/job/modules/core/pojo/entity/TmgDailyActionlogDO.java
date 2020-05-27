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
 * [勤怠]日別情報操作ログ
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
@TableName("tmg_daily_actionlog")
public class TmgDailyActionlogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableId(value="tdal_ccustomerid",type = IdType.INPUT)
        private String tdalCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tdal_ccompanyid")
        private String tdalCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tdal_cemployeeid")
        private String tdalCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tdal_dstartdate")
        private Date tdalDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tdal_denddate")
        private Date tdalDenddate;

        /**
         * 更新者
         */
    @TableField("tdal_cmodifieruserid")
        private String tdalCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tdal_dmodifieddate")
        private Date tdalDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tdal_cmodifierprogramid")
        private String tdalCmodifierprogramid;

        /**
         * 該当年月日                         yyyy/mm/dd
         */
    @TableField("tdal_dyyyymmdd")
        private Date tdalDyyyymmdd;

        /**
         * 更新id
         */
    @TableField("tdal_nid")
        private Long tdalNid;


        }