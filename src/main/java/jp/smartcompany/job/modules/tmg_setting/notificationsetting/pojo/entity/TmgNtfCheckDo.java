package jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("TMG_NTF_CHECK")
public class TmgNtfCheckDo implements Serializable {

        private static final long serialVersionUID=1L;

//        Long serialVersionUID = 1L;

        @TableId(value = "tnc_cfuncname")
        private String tncCfuncname;


        @TableField("tnc_cfuncid")
        private String tncCfuncid;

        @TableField("tnc_dstartdate")
        private Date tncDstartdate;

        @TableField("tnc_denddate")
        private Date tncDenddate;

        @TableField("tnc_ccomment")
        private String tncCcomment;

        @TableField("tnc_cgroup")
        private String tncCgroup;

        @TableField("tnc_ctypegroup")
        private String tncCtypegroup;

        @TableField("tnc_ctimetype")
        private String tncCtimetype;

        @TableField("tnc_cgroupingcode")
        private String tncCgroupingcode;

        @TableField("tnc_climit")
        private String tncClimit;

        @TableField("tnc_climitunit")
        private String tncClimitunit;

}