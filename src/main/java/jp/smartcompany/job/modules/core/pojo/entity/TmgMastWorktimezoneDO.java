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
@TableName("tmg_mast_worktimezone")
public class TmgMastWorktimezoneDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableField("tmwz_ccustomerid")
        private String tmwzCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmwz_ccompanyid")
        private String tmwzCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmwz_dstartdate")
        private Date tmwzDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmwz_denddate")
        private Date tmwzDenddate;

        /**
         * 更新者
         */
    @TableField("tmwz_cmodifieruserid")
        private String tmwzCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmwz_dmodifieddate")
        private Date tmwzDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmwz_cmodifierprogramid")
        private String tmwzCmodifierprogramid;

        /**
         * 開始時刻
         */
                @TableId(value = "tmwz_ntimezonefrom", type = IdType.AUTO)
                private Long tmwzNtimezonefrom;

        /**
         * 終了時刻
         */
    @TableField("tmwz_ntimezoneto")
        private Long tmwzNtimezoneto;

        /**
         * 時間帯区分
         */
    @TableField("tmwz_ctimezone")
        private String tmwzCtimezone;

        /**
         * 時間帯名称
         */
    @TableField("tmwz_ctimezonenm")
        private String tmwzCtimezonenm;


        }