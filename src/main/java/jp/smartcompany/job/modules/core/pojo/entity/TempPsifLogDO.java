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
 * 汎用インターフェース　処理結果ログテーブル
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
@TableName("temp_psif_log")
public class TempPsifLogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "tpil_id", type = IdType.AUTO)
                private Long tpilId;

        /**
         * 処理日付
         */
    @TableField("tpil_dexec")
        private Date tpilDexec;

        /**
         * 表示順
         */
    @TableField("tpil_nseq")
        private Long tpilNseq;

        /**
         * タイムスタンプ
         */
    @TableField("tpil_dstsmp")
        private Date tpilDstsmp;

        /**
         * プログラムid
         */
    @TableField("tpil_cpgmid")
        private String tpilCpgmid;

        /**
         * ステータス
         */
    @TableField("tpil_cstatus")
        private String tpilCstatus;

        /**
         * コメント
         */
    @TableField("tpil_ccomments")
        private String tpilCcomments;

        /**
         * 最終更新者
         */
    @TableField("tpil_cmodifieruserid")
        private String tpilCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("tpil_dmodifieddate")
        private Date tpilDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }