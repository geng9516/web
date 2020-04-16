package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * イントラマート_ユーザアプリケーション状態
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
@TableName("tmg_im_user_app_status")
public class TmgImUserAppStatusDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * プロセス定義cd
         */
                @TableId(value = "tiuas_process_def_cd", type = IdType.AUTO)
                private String tiuasProcessDefCd;

        /**
         * バージョンcd
         */
    @TableField("tiuas_version_cd")
        private String tiuasVersionCd;

        /**
         * プロセスcd
         */
    @TableField("tiuas_process_cd")
        private String tiuasProcessCd;

        /**
         * ユーザアプリケーションキー
         */
    @TableField("tiuas_parameter_cd")
        private String tiuasParameterCd;


        }