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
 * [勤怠]アラートメッセージ格納テーブル
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
@TableName("tmg_alertmsg")
public class TmgAlertmsgDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableId(value="tam_ccustomerid",type = IdType.INPUT)
        private String tamCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tam_ccompanyid")
        private String tamCcompanyid;

        /**
         * 職員番号
         */
    @TableField("tam_cemployeeid")
        private String tamCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tam_dstartdate")
        private Date tamDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tam_denddate")
        private Date tamDenddate;

        /**
         * 更新者
         */
    @TableField("tam_cmodifieruserid")
        private String tamCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tam_dmodifieddate")
        private Date tamDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tam_cmodifierprogramid")
        private String tamCmodifierprogramid;

        /**
         * 発生日付
         */
    @TableField("tam_ddate")
        private Date tamDdate;

        /**
         * 処理月
         */
    @TableField("tam_dyyyymm")
        private Date tamDyyyymm;

        /**
         * モジュール名
         */
    @TableField("tam_module")
        private String tamModule;

        /**
         * アラートメッセージ
         */
    @TableField("tam_message")
        private String tamMessage;


        }