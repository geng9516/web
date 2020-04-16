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
 * [勤怠]月次集計データ作成・対象者一覧ワークテーブル
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
@TableName("tmg_work_monthlyoutputlist")
public class TmgWorkMonthlyoutputlistDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード                    固定：01
         */
    @TableField("twmo_ccustomerid")
        private String twmoCcustomerid;

        /**
         * 法人コード
         */
    @TableField("twmo_ccompanyid")
        private String twmoCcompanyid;

        /**
         * 職員番号
         */
                @TableId(value = "twmo_cemployeeid", type = IdType.AUTO)
                private String twmoCemployeeid;

        /**
         * 該当年月
         */
    @TableField("twmo_dyyyymm")
        private Date twmoDyyyymm;

        /**
         * 異動歴のデータ開始日
         */
    @TableField("twmo_dstartdate")
        private Date twmoDstartdate;

        /**
         * 更新者
         */
    @TableField("twmo_cmodifieruserid")
        private String twmoCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("twmo_dmodifieddate")
        private Date twmoDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("twmo_cmodifierprogramid")
        private String twmoCmodifierprogramid;

        /**
         * 所属コード
         */
    @TableField("twmo_csectionid")
        private String twmoCsectionid;

        /**
         * 役職コード
         */
    @TableField("twmo_cpostid")
        private String twmoCpostid;

        /**
         * 部局コード
         */
    @TableField("twmo_cbasesection")
        private String twmoCbasesection;

        /**
         * 給与除外対象フラグ
         */
    @TableField("twmo_nsalaryexcept")
        private Long twmoNsalaryexcept;


        }