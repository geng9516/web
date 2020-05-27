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
 * [勤怠]日次集計情報
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
@TableName("tmg_daily_totalization")
public class TmgDailyTotalizationDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableId(value="tdt_ccustomerid",type = IdType.INPUT)
        private String tdtCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tdt_ccompanyid")
        private String tdtCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tdt_cemployeeid")
        private String tdtCemployeeid;

        /**
         * データ開始日                        固定：1900/01/01
         */
    @TableField("tdt_dstartdate")
        private Date tdtDstartdate;

        /**
         * データ終了日                        固定：2222/12/31
         */
    @TableField("tdt_denddate")
        private Date tdtDenddate;

        /**
         * 更新者
         */
    @TableField("tdt_cmodifieruserid")
        private String tdtCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tdt_dmodifieddate")
        private Date tdtDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tdt_cmodifierprogramid")
        private String tdtCmodifierprogramid;

        /**
         * 該当年月日                         yyyy/mm/dd
         */
    @TableField("tdt_dyyyymmdd")
        private Date tdtDyyyymmdd;

        /**
         * 集計先の暦日                        yyyy/mm/dd
         */
    @TableField("tdt_dtargetdate")
        private Date tdtDtargetdate;

        /**
         * 集計項目ｺｰﾄﾞ                                                    tmg_mast_totalitem
         */
    @TableField("tdt_ctotalizationid")
        private String tdtCtotalizationid;

        /**
         * 仕訳項目ｺｰﾄﾞ                                                    tmg_mast_journalize
         */
    @TableField("tdt_cjournalizingid")
        private String tdtCjournalizingid;

        /**
         * 値
         */
    @TableField("tdt_nvalue")
        private Long tdtNvalue;


        }