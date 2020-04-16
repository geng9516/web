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
 * 時間外労働「無」歴
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
@TableName("hist_jikangai")
public class HistJikangaiDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ
         */
                @TableId(value = "hjkg_ccustomerid_ck", type = IdType.AUTO)
                private String hjkgCcustomeridCk;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("hjkg_ccompanyid_ck")
        private String hjkgCcompanyidCk;

        /**
         * 職員番号
         */
    @TableField("hjkg_cemployeeid_ck")
        private String hjkgCemployeeidCk;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("hjkg_dstartdate_ck")
        private Date hjkgDstartdateCk;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("hjkg_denddate")
        private Date hjkgDenddate;

        /**
         * 更新者
         */
    @TableField("hjkg_cmodifieruserid")
        private String hjkgCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("hjkg_dmodifieddate")
        private Date hjkgDmodifieddate;

        /**
         * 時間外労働「無」ｺｰﾄﾞ
         */
    @TableField("hjkg_cjikangaikb")
        private String hjkgCjikangaikb;

        /**
         * 時間外労働「無」名称
         */
    @TableField("hjkg_cjikangainm")
        private String hjkgCjikangainm;


        }