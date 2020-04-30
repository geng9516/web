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
 * 広域異動手当db
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
@TableName("dt_djnd0260")
public class DtDjnd0260DO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 会社区分
         */
        @TableId(value="ccompkb",type = IdType.INPUT)
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
         * 職員番号
         */
                @TableId(value = "cshainno", type = IdType.AUTO)
                private String cshainno;

        /**
         * 開始年月日
         */
    @TableField("start_dte")
        private String startDte;

        /**
         * 開始年月日（西暦）
         */
    @TableField("dstart_dte")
        private Date dstartDte;

        /**
         * 終了年月日
         */
    @TableField("end_dte")
        private String endDte;

        /**
         * 終了年月日（西暦）
         */
    @TableField("dend_dte")
        private Date dendDte;

        /**
         * 支給率コード
         */
    @TableField("siky_cde")
        private String sikyCde;

        /**
         * 支給率名称
         */
    @TableField("siky_nme")
        private String sikyNme;

        /**
         * 支給率
         */
    @TableField("siky_ritu")
        private Long sikyRitu;

        /**
         * 基準日
         */
    @TableField("key_dte")
        private String keyDte;

        /**
         * 基準日（西暦）
         */
    @TableField("dkey_dte")
        private Date dkeyDte;

        /**
         * 更新者
         */
    @TableField("kosin_use")
        private String kosinUse;

        /**
         * 更新pgm
         */
    @TableField("last_pgm")
        private String lastPgm;

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
         * ユーザー
         */
    @TableField("cmnuser")
        private String cmnuser;

        /**
         * 更新日
         */
    @TableField("dmndate")
        private Date dmndate;


        }