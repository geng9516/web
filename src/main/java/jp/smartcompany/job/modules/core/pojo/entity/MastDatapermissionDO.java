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
 * 検索対象範囲条件定義マスタ
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
@TableName("mast_datapermission")
public class MastDatapermissionDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mdp_id", type = IdType.AUTO)
                private Long mdpId;

        /**
         * 定義id
         */
    @TableField("mdp_cpermissionid")
        private String mdpCpermissionid;

        /**
         * 定義名
         */
    @TableField("mdp_cpermissionname")
        private String mdpCpermissionname;

        /**
         * 検索対象範囲定義種別
         */
    @TableField("mdp_cbaseflag")
        private String mdpCbaseflag;

        /**
         * 検索対象範囲定義編集種別
         */
    @TableField("mdp_ceditflag")
        private String mdpCeditflag;

        /**
         * 定義権限
         */
    @TableField("mdp_cpermission")
        private String mdpCpermission;

        /**
         * 最終更新者
         */
    @TableField("mdp_cmodifieruserid")
        private String mdpCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mdp_dmodifieddate")
        private Date mdpDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }