package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * [勤怠]greennuts用：打刻データログ
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
@TableName("tmg_greennuts_tplog")
@KeySequence("tmg_greennuts_tplog_seq")
public class TmgGreennutsTplogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * id
         */
                @TableId("tgtl_nid")
                private Long tgtlNid;

        /**
         * 打刻データ
         */
    @TableField("tgtl_ctimepunchdata")
        private String tgtlCtimepunchdata;

        /**
         * icカードid                       打刻データ：1～16バイト
         */
    @TableField("tgtl_ciccardid")
        private String tgtlCiccardid;

        /**
         * 社員コード                         打刻データ：17～26バイト
         */
    @TableField("tgtl_cemployeeid")
        private String tgtlCemployeeid;

        /**
         * 出退勤区分                         打刻データ：27～28バイト
         */
    @TableField("tgtl_ctptypeid")
        private String tgtlCtptypeid;

        /**
         * スキャン日時                        打刻データ：29～42バイト
         */
    @TableField("tgtl_ctptime")
        private String tgtlCtptime;

        /**
         * 最終更新者
         */
    @TableField("tgtl_cmodifieruserid")
        private String tgtlCmodifieruserid;

        /**
         * 最終更新日時
         */
    @TableField("tgtl_dmodifieddate")
        private Date tgtlDmodifieddate;

        /**
         * 備考                            反映失敗時のエラーコード等
         */
    @TableField("tgtl_cmemo")
        private String tgtlCmemo;


        }