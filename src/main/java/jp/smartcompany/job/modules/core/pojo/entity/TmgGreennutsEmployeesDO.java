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
 * [勤怠]greennuts用：icカード→職員番号マッピング
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
@TableName("tmg_greennuts_employees")
public class TmgGreennutsEmployeesDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableField("tge_ccustomerid")
        private String tgeCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tge_ccompanyid")
        private String tgeCcompanyid;

        /**
         * 職員番号
         */
    @TableField("tge_cemployeeid")
        private String tgeCemployeeid;

        /**
         * icカードid
         */
                @TableId(value = "tge_ciccardid", type = IdType.AUTO)
                private String tgeCiccardid;

        /**
         * データ開始日
         */
    @TableField("tge_dstartdate")
        private Date tgeDstartdate;

        /**
         * データ終了日
         */
    @TableField("tge_denddate")
        private Date tgeDenddate;

        /**
         * 最終更新者
         */
    @TableField("tge_cmodifieruserid")
        private String tgeCmodifieruserid;

        /**
         * 最終更新日時
         */
    @TableField("tge_dmodifieddate")
        private Date tgeDmodifieddate;


        }