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
 * データセキュリティマスタ
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
@TableName("mast_datasecurity")
public class MastDatasecurityDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mds_id", type = IdType.AUTO)
                private Long mdsId;

        /**
         * オブジェクトid
         */
    @TableField("mds_cobjectid")
        private String mdsCobjectid;

        /**
         * サイトid
         */
    @TableField("mds_csiteid")
        private String mdsCsiteid;

        /**
         * アプリケーションid
         */
    @TableField("mds_cappid")
        private String mdsCappid;

        /**
         * ビヘイビア適用フラグ
         */
    @TableField("mds_cbehavior_flg")
        private String mdsCbehaviorFlg;

        /**
         * 下位適用フラグ
         */
    @TableField("mds_cunder_flg")
        private String mdsCunderFlg;

        /**
         * 日付種類選択
         */
    @TableField("mds_ckinddateselect")
        private String mdsCkinddateselect;

        /**
         * 年月日モード
         */
    @TableField("mds_cdatemode")
        private String mdsCdatemode;

        /**
         * 相対過去対象
         */
    @TableField("mds_naspect_start")
        private Long mdsNaspectStart;

        /**
         * 相対未来対象
         */
    @TableField("mds_naspect_end")
        private Long mdsNaspectEnd;

        /**
         * 相対締め日
         */
    @TableField("mds_caspect_close")
        private String mdsCaspectClose;

        /**
         * 絶対開始日
         */
    @TableField("mds_dabsolute_start")
        private Date mdsDabsoluteStart;

        /**
         * 絶対終了日
         */
    @TableField("mds_dabsolute_end")
        private Date mdsDabsoluteEnd;

        /**
         * 最終更新者
         */
    @TableField("mds_cmodifieruserid")
        private String mdsCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mds_dmodifieddate")
        private Date mdsDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }