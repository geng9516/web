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
 * [勤怠]月次集計データ差異チェックテーブル 月次集計実行より後に勤務実績が修正されている職員・年月を登録
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
@TableName("tmg_mo_change_check")
public class TmgMoChangeCheckDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード     固定：01
         */
                @TableId(value = "tmcc_ccustomerid", type = IdType.AUTO)
                private String tmccCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tmcc_ccompanyid")
        private String tmccCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tmcc_cemployeeid")
        private String tmccCemployeeid;

        /**
         * データ開始日
         */
    @TableField("tmcc_dstartdate")
        private Date tmccDstartdate;

        /**
         * データ終了日
         */
    @TableField("tmcc_denddate")
        private Date tmccDenddate;

        /**
         * 更新者
         */
    @TableField("tmcc_cmodifieruserid")
        private String tmccCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmcc_dmodifieddate")
        private Date tmccDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmcc_cmodifierprogramid")
        private String tmccCmodifierprogramid;


        }