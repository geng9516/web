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
 * [勤怠]日次仕訳情報
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
@TableName("tmg_daily_journalizing")
public class TmgDailyJournalizingDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableField("tdj_ccustomerid")
        private String tdjCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tdj_ccompanyid")
        private String tdjCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "tdj_cemployeeid", type = IdType.AUTO)
                private String tdjCemployeeid;

        /**
         * データ開始日
         */
    @TableField("tdj_dstartdate")
        private Date tdjDstartdate;

        /**
         * データ終了日
         */
    @TableField("tdj_denddate")
        private Date tdjDenddate;

        /**
         * 更新者
         */
    @TableField("tdj_cmodifieruserid")
        private String tdjCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tdj_dmodifieddate")
        private Date tdjDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tdj_cmodifierprogramid")
        private String tdjCmodifierprogramid;

        /**
         * 該当年月
         */
    @TableField("tdj_dyyyymmdd")
        private Date tdjDyyyymmdd;

        /**
         * 仕訳項目ｺｰﾄﾞ                                                    tmg_mast_journalize
         */
    @TableField("tdj_cjournalizingid")
        private String tdjCjournalizingid;

        /**
         * 開始時刻                          分単位
         */
    @TableField("tdj_nstart")
        private Long tdjNstart;

        /**
         * 終了時刻                          分単位
         */
    @TableField("tdj_nend")
        private Long tdjNend;


        }