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
 * ユーザ別言語設定テーブル
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
@TableName("hist_userlanguage")
public class HistUserlanguageDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hul_id", type = IdType.AUTO)
                private Long hulId;

        /**
         * ユーザid
         */
    @TableField("hul_cuserid")
        private String hulCuserid;

        /**
         * 言語区分
         */
    @TableField("hul_clanguage")
        private String hulClanguage;

        /**
         * 最終更新者
         */
    @TableField("hul_cmodifieruserid")
        private String hulCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hul_dmodifieddate")
        private Date hulDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }