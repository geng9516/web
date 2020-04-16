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
 * [勤怠]打刻処理job分割用テーブル　　　
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
@TableName("tmg_auto_set_tp_list")
public class TmgAutoSetTpListDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 通し番号                        　　　　
         */
                @TableId(value = "tal_njobno", type = IdType.AUTO)
                private Long talNjobno;

        /**
         * 職員番号                        　　　　
         */
    @TableField("tal_cemployeeid")
        private String talCemployeeid;


        }