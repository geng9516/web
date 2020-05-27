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
 * [勤怠]ｇｗデータ連携  休暇等の不在情報（最新）
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
@TableName("tmg_ooto4gw")
public class TmgOoto4gwDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * id                                                          tmg_ooto4gw_seq
         */
    @TableId(value="tow_nid",type = IdType.INPUT)
        private Long towNid;

        /**
         * 顧客コード
         */
    @TableField("tow_ccustomerid")
        private String towCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tow_ccompanyid")
        private String towCcompanyid;

        /**
         * 職員番号
         */
    @TableField("tow_cemployeeid")
        private String towCemployeeid;

        /**
         * 開始日
         */
    @TableField("tow_dstartdate")
        private Date towDstartdate;

        /**
         * 終了日
         */
    @TableField("tow_denddate")
        private Date towDenddate;

        /**
         * 更新ユーザーid
         */
    @TableField("tow_cmodifieruserid")
        private String towCmodifieruserid;

        /**
         * 更新日時
         */
    @TableField("tow_dmodifieddate")
        private Date towDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tow_cmodifierprogramid")
        private String towCmodifierprogramid;

        /**
         * 開始時刻
         */
    @TableField("tow_nstarttime")
        private Long towNstarttime;

        /**
         * 終了時刻
         */
    @TableField("tow_nendtime")
        private Long towNendtime;

        /**
         * 作成日時
         */
    @TableField("tow_dcreatedate")
        private Date towDcreatedate;

        /**
         * 削除フラグ
         */
    @TableField("tow_cdelflg")
        private Long towCdelflg;

        /**
         * 削除日時
         */
    @TableField("tow_ddeldate")
        private Date towDdeldate;

        /**
         * タイトル
         */
    @TableField("tow_ctitle")
        private String towCtitle;


        }