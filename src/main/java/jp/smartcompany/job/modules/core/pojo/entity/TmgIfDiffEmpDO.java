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
 * [勤怠]インターフェース  入退職職員差分データ
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
@TableName("tmg_if_diff_emp")
public class TmgIfDiffEmpDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableId(value="tide_ccustomerid",type = IdType.INPUT)
        private String tideCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tide_ccompanyid")
        private String tideCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tide_cemployeeid")
        private String tideCemployeeid;

        /**
         * 更新者
         */
    @TableField("tide_dmodifieruserid")
        private String tideDmodifieruserid;

        /**
         * 更新日時
         */
    @TableField("tide_dmodifieddate")
        private Date tideDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tide_cmodifierprogramid")
        private String tideCmodifierprogramid;

        /**
         * テーブル名
         */
    @TableField("tide_cdifftableid")
        private String tideCdifftableid;

        /**
         * 変更の種類(1：追加、2：削除)
         */
    @TableField("tide_ndifftype")
        private Long tideNdifftype;


        }