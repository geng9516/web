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
 * 個人調整手当情報db
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
@TableName("dt_djnd0220")
public class DtDjnd0220DO implements Serializable {

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
         * 職員番号
         */
    @TableField("cshainno")
        private String cshainno;

        /**
         * （調整手当）開始年月日
         */
    @TableField("start_dte")
        private String startDte;

        /**
         * （調整手当）開始年月日
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
         * 支給率                          2001.06.16
         */
    @TableField("siky_ritu")
        private Long sikyRitu;

        /**
         * 在勤官署市町村コード
         */
    @TableField("zikn_cde")
        private String ziknCde;

        /**
         * 在勤官署市町村
         */
    @TableField("zikn_nme")
        private String ziknNme;

        /**
         * 官署所在地区分コード
         */
    @TableField("knsyzi_cde")
        private String knsyziCde;

        /**
         * 官署所在地区分
         */
    @TableField("knsyzi_nme")
        private String knsyziNme;

        /**
         * 基準年月日
         */
    @TableField("key_dte")
        private String keyDte;

        /**
         * 基準年月日（西暦）
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
         * 削除年月日                         2002.03.15 削除or修正時基準日
         */
    @TableField("del_dte")
        private String delDte;

        /**
         * 削除年月日（西暦）
         */
    @TableField("ddel_dte")
        private Date ddelDte;

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


        }