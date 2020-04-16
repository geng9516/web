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
 * [勤怠]月次集計データ作成・年度状況一覧ワークテーブル
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
@TableName("tmg_work_mo_yearlist")
public class TmgWorkMoYearlistDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード                    固定：01
         */
    @TableField("twmy_ccustomerid")
        private String twmyCcustomerid;

        /**
         * 法人コード
         */
    @TableField("twmy_ccompanyid")
        private String twmyCcompanyid;

        /**
         * 職員番号
         */
                @TableId(value = "twmy_cemployeeid", type = IdType.AUTO)
                private String twmyCemployeeid;

        /**
         * 該当年月
         */
    @TableField("twmy_dyyyymm")
        private Date twmyDyyyymm;

        /**
         * 更新者
         */
    @TableField("twmy_cmodifieruserid")
        private String twmyCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("twmy_dmodifieddate")
        private Date twmyDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("twmy_cmodifierprogramid")
        private String twmyCmodifierprogramid;

        /**
         * 月次締め状態
         */
    @TableField("twmy_nfixedmonthly")
        private Long twmyNfixedmonthly;

        /**
         * 給与確定状態
         */
    @TableField("twmy_nfixedsalary")
        private Long twmyNfixedsalary;

        /**
         * 集計時の問題
         */
    @TableField("twmy_nalertmsgcnt")
        private Long twmyNalertmsgcnt;

        /**
         * 月例給与データの有無
         */
    @TableField("twmy_nsalarydata")
        private Long twmyNsalarydata;

        /**
         * 月例遡及データの有無
         */
    @TableField("twmy_nretroaction")
        private Long twmyNretroaction;


        }