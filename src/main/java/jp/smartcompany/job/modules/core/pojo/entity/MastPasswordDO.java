package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.KeySequence;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * パスワードマスタ
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
@TableName("mast_password")
@KeySequence("MAST_PASSWORD_SEQ")
public class MastPasswordDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId("map_id")
                private Long mapId;

        /**
         * ユーザid
         */
    @TableField("map_cuserid")
        private String mapCuserid;

        /**
         * 履歴no
         */
    @TableField("map_nhistory")
        private Long mapNhistory;

        /**
         * パスワード
         */
    @TableField("map_cpassword")
        private String mapCpassword;

        /**
         * パスワード設定日
         */
    @TableField("map_dpwddate")
        private Date mapDpwddate;

        /**
         * 最終更新者
         */
    @TableField("map_cmodifieruserid")
        private String mapCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("map_dmodifieddate")
        private Date mapDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }