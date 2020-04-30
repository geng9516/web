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
 * public.mast_account_aft
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
@TableName("mast_account_aft")
public class MastAccountAftDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * ma_id
         */
    @TableId(value="ma_id",type=IdType.INPUT)
        private Long maId;

        /**
         * ma_ccustomerid
         */
    @TableField("ma_ccustomerid")
        private String maCcustomerid;

        /**
         * ma_cuserid
         */
    @TableField("ma_cuserid")
        private String maCuserid;

        /**
         * ma_caccount
         */
    @TableField("ma_caccount")
        private String maCaccount;

        /**
         * ma_dstart
         */
    @TableField("ma_dstart")
        private Date maDstart;

        /**
         * ma_dend
         */
    @TableField("ma_dend")
        private Date maDend;

        /**
         * ma_nretrycounter
         */
    @TableField("ma_nretrycounter")
        private Long maNretrycounter;

        /**
         * ma_npasswordlock
         */
    @TableField("ma_npasswordlock")
        private Long maNpasswordlock;

        /**
         * ma_cadminuser
         */
    @TableField("ma_cadminuser")
        private String maCadminuser;

        /**
         * ma_dcreate
         */
    @TableField("ma_dcreate")
        private Date maDcreate;

        /**
         * ma_cmodifieruserid
         */
    @TableField("ma_cmodifieruserid")
        private String maCmodifieruserid;

        /**
         * ma_dmodifieddate
         */
    @TableField("ma_dmodifieddate")
        private Date maDmodifieddate;

        /**
         * versionno
         */
    @TableField("versionno")
        private Long versionno;


        }