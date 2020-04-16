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
 * グループ判定処理制御データ
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
@TableName("temp_groupcheckflags")
public class TempGroupcheckflagsDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "tgcf_id", type = IdType.AUTO)
                private Long tgcfId;

        /**
         * 基準日(変更元)
         */
    @TableField("tgcf_dbeforedate")
        private Date tgcfDbeforedate;

        /**
         * 基準日(変更先)
         */
    @TableField("tgcf_dafterdate")
        private Date tgcfDafterdate;

        /**
         * エラーメッセージ
         */
    @TableField("tgcf_cerrormessage")
        private String tgcfCerrormessage;

        /**
         * ステータス
         */
    @TableField("tgcf_cstatus")
        private String tgcfCstatus;

        /**
         * 最終更新者
         */
    @TableField("tgcf_cmodifieruserid")
        private String tgcfCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("tgcf_dmodifieddate")
        private Date tgcfDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }