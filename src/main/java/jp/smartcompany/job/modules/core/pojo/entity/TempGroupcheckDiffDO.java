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
 * グループ判定結果差分データ
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
@TableName("temp_groupcheck_diff")
public class TempGroupcheckDiffDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "tgcd_id", type = IdType.AUTO)
                private Long tgcdId;

        /**
         * システムid
         */
    @TableField("tgcd_csystemid")
        private String tgcdCsystemid;

        /**
         * 顧客コード
         */
    @TableField("tgcd_ccustomerid")
        private String tgcdCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tgcd_ccompanyid")
        private String tgcdCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tgcd_cemployeeid")
        private String tgcdCemployeeid;

        /**
         * 変更前日付
         */
    @TableField("tgcd_dbeforedate")
        private Date tgcdDbeforedate;

        /**
         * 変更後日付
         */
    @TableField("tgcd_dafterdate")
        private Date tgcdDafterdate;

        /**
         * 変更前グループid
         */
    @TableField("tgcd_cbeforegroupid")
        private String tgcdCbeforegroupid;

        /**
         * 変更後グループid
         */
    @TableField("tgcd_caftergroupid")
        private String tgcdCaftergroupid;

        /**
         * 最終更新者
         */
    @TableField("tgcd_cmodifieruserid")
        private String tgcdCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("tgcd_dmodifieddate")
        private Date tgcdDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }