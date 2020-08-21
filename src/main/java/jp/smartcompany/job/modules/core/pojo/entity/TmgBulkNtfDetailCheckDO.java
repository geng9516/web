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
 * [勤怠]休暇休業一括登録・対象組織情報
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
@TableName("tmg_bulk_ntf_detail_check")
public class TmgBulkNtfDetailCheckDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * id    tmg_bulk_notification_check.tbn_ntbn
         */
                @TableId("tbnd_ntbnid_fk")
                private Long tbndNtbnidFk;

        /**
         * 対象組織コード    mast_organisation.mo_csectioni
         */
    @TableField("tbnd_csectionid")
        private String tbndCsectionid;


        }