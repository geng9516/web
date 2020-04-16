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
 * (普通昇給)カレンダーマスタ
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
@TableName("dt_dmcal")
public class DtDmcalDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 会社区分
         */
    @TableField("ccompkb")
        private String ccompkb;

        /**
         * 開始日
         */
    @TableField("dstart")
        private Date dstart;

        /**
         * 開始日（和暦）
         */
    @TableField("sstart")
        private String sstart;

        /**
         * 終了日
         */
    @TableField("dend")
        private Date dend;

        /**
         * 終了日（和暦）
         */
    @TableField("send")
        private String send;

        /**
         * 年                             gyy
         */
                @TableId(value = "year", type = IdType.AUTO)
                private String year;

        /**
         * 月
         */
    @TableField("month")
        private Long month;

        /**
         * 日付属性                          各桁が日に該当／属性区分0～3
         */
    @TableField("zokusei")
        private String zokusei;

        /**
         * 更新者
         */
    @TableField("kosin_usr")
        private String kosinUsr;

        /**
         * 更新プログラム
         */
    @TableField("kosin_pgm")
        private String kosinPgm;

        /**
         * 更新日時
         */
    @TableField("last_dte")
        private Date lastDte;

        /**
         * 更新区分
         */
    @TableField("kosin_kbn")
        private String kosinKbn;

        /**
         * ﾕｰｻﾞｰ
         */
    @TableField("cmnuser")
        private String cmnuser;

        /**
         * 更新日
         */
    @TableField("dmndate")
        private Date dmndate;

        /**
         * dgaitong
         */
    @TableField("dgaitong")
        private Date dgaitong;


        }