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
 * クロス集計検索設定保存データ（集計条件定義）
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
@TableName("hist_crosssearch_select")
public class HistCrosssearchSelectDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hics_id", type = IdType.AUTO)
                private Long hicsId;

        /**
         * 設定id
         */
    @TableField("hics_nsettingid")
        private Long hicsNsettingid;

        /**
         * 縦軸一次元カラムid
         */
    @TableField("hics_cxaxis_column1")
        private String hicsCxaxisColumn1;

        /**
         * 縦軸一次元日付タイプ
         */
    @TableField("hics_cxaxis_datetype1")
        private String hicsCxaxisDatetype1;

        /**
         * 縦軸一次元ピッチ
         */
    @TableField("hics_nxaxis_pitch1")
        private Long hicsNxaxisPitch1;

        /**
         * 縦軸一次元最小値
         */
    @TableField("hics_cxaxis_min1")
        private String hicsCxaxisMin1;

        /**
         * 縦軸一次元最大値
         */
    @TableField("hics_cxaxis_max1")
        private String hicsCxaxisMax1;

        /**
         * 縦軸小計フラグ
         */
    @TableField("hics_cxaxis_sum")
        private String hicsCxaxisSum;

        /**
         * 縦軸二次元カラムid
         */
    @TableField("hics_cxaxis_column2")
        private String hicsCxaxisColumn2;

        /**
         * 縦軸二次元日付タイプ
         */
    @TableField("hics_cxaxis_datetype2")
        private String hicsCxaxisDatetype2;

        /**
         * 縦軸二次元ピッチ
         */
    @TableField("hics_nxaxis_pitch2")
        private Long hicsNxaxisPitch2;

        /**
         * 縦軸二次元最小値
         */
    @TableField("hics_cxaxis_min2")
        private String hicsCxaxisMin2;

        /**
         * 縦軸二次元最大値
         */
    @TableField("hics_cxaxis_max2")
        private String hicsCxaxisMax2;

        /**
         * 横軸一次元カラムid
         */
    @TableField("hics_cyaxis_column1")
        private String hicsCyaxisColumn1;

        /**
         * 横軸一次元日付タイプ
         */
    @TableField("hics_cyaxis_datetype1")
        private String hicsCyaxisDatetype1;

        /**
         * 横軸一次元ピッチ
         */
    @TableField("hics_nyaxis_pitch1")
        private Long hicsNyaxisPitch1;

        /**
         * 横軸一次元最小値
         */
    @TableField("hics_cyaxis_min1")
        private String hicsCyaxisMin1;

        /**
         * 横軸一次元最大値
         */
    @TableField("hics_cyaxis_max1")
        private String hicsCyaxisMax1;

        /**
         * 横軸小計フラグ
         */
    @TableField("hics_cyaxis_sum")
        private String hicsCyaxisSum;

        /**
         * 横軸二次元カラムid
         */
    @TableField("hics_cyaxis_column2")
        private String hicsCyaxisColumn2;

        /**
         * 横軸二次元日付タイプ
         */
    @TableField("hics_cyaxis_datetype2")
        private String hicsCyaxisDatetype2;

        /**
         * 横軸二次元ピッチ
         */
    @TableField("hics_nyaxis_pitch2")
        private Long hicsNyaxisPitch2;

        /**
         * 横軸二次元最小値
         */
    @TableField("hics_cyaxis_min2")
        private String hicsCyaxisMin2;

        /**
         * 横軸二次元最大値
         */
    @TableField("hics_cyaxis_max2")
        private String hicsCyaxisMax2;

        /**
         * 集計項目カラムid
         */
    @TableField("hics_csum_column")
        private String hicsCsumColumn;

        /**
         * 人数集計フラグ
         */
    @TableField("hics_ccount")
        private String hicsCcount;

        /**
         * 平均フラグ
         */
    @TableField("hics_cavg")
        private String hicsCavg;

        /**
         * 最終更新者
         */
    @TableField("hics_cmodifieruserid")
        private String hicsCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hics_dmodifieddate")
        private Date hicsDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }