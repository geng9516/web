package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * トランザクション制御テーブル
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
@TableName("tmg_transaction_control")
public class TmgTransactionControlDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 社員番号
         */
                @TableId(value = "ttc_cemployeeid", type = IdType.AUTO)
                private String ttcCemployeeid;


        }