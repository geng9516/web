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
 * アカウントマスタ
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
@TableName("mast_account")
public class MastAccountDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "ma_id", type = IdType.AUTO)
                private Long maId;

        /**
         * 顧客コード
         */
    @TableField("ma_ccustomerid")
        private String maCcustomerid;

        /**
         * ユーザid
         */
    @TableField("ma_cuserid")
        private String maCuserid;

        /**
         * アカウント
         */
    @TableField("ma_caccount")
        private String maCaccount;

        /**
         * 有効期間開始日
         */
    @TableField("ma_dstart")
        private Date maDstart;

        /**
         * 有効期間終了日
         */
    @TableField("ma_dend")
        private Date maDend;

        /**
         * パスワード間違い回数
         */
    @TableField("ma_nretrycounter")
        private Long maNretrycounter;

        /**
         * ロックアウトフラグ
         */
    @TableField("ma_npasswordlock")
        private Long maNpasswordlock;

        /**
         * 管理ツールユーザフラグ
         */
    @TableField("ma_cadminuser")
        private String maCadminuser;

        /**
         * アカウント作成日
         */
    @TableField("ma_dcreate")
        private Date maDcreate;

        /**
         * 最終更新者
         */
    @TableField("ma_cmodifieruserid")
        private String maCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("ma_dmodifieddate")
        private Date maDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }