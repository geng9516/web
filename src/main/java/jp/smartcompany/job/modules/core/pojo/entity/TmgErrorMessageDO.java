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
 * [勤怠]エラーメッセージマスタ
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
@TableName("tmg_error_message")
public class TmgErrorMessageDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * エラーメッセージコード
         */
                @TableId(value = "tems_cemessagecode", type = IdType.AUTO)
                private String temsCemessagecode;

        /**
         * エラーメッセージ内容
         */
    @TableField("tems_cemessagedetail")
        private String temsCemessagedetail;


        }