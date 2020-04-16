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
 * [勤怠]勤務開始日編集ログ
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
@TableName("tmg_dateofemp_log")
public class TmgDateofempLogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tdlg_ccustomerid")
        private String tdlgCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tdlg_ccompanyid")
        private String tdlgCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tdlg_cemployeeid")
        private String tdlgCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tdlg_dstartdate")
        private Date tdlgDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tdlg_denddate")
        private Date tdlgDenddate;

        /**
         * 更新者
         */
    @TableField("tdlg_cmodifieruserid")
        private String tdlgCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tdlg_dmodifieddate")
        private Date tdlgDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tdlg_cmodifierprogramid")
        private String tdlgCmodifierprogramid;

        /**
         * 更新内容
         */
    @TableField("tdlg_cmodifieddetail")
        private String tdlgCmodifieddetail;


        }