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
 * public.temp_tmg_personal_pattern
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
@TableName("temp_tmg_personal_pattern")
public class TempTmgPersonalPatternDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * tppa_ccustomerid
         */
    @TableId(value="tppa_ccustomerid",type=IdType.INPUT)
        private String tppaCcustomerid;

        /**
         * tppa_ccompanyid
         */
    @TableField("tppa_ccompanyid")
        private String tppaCcompanyid;

        /**
         * tppa_cemployeeid
         */
                @TableField(value = "tppa_cemployeeid")
                private String tppaCemployeeid;

        /**
         * tppa_dstartdate
         */
    @TableField("tppa_dstartdate")
        private Date tppaDstartdate;

        /**
         * tppa_denddate
         */
    @TableField("tppa_denddate")
        private Date tppaDenddate;

        /**
         * tppa_cmodifieruserid
         */
    @TableField("tppa_cmodifieruserid")
        private String tppaCmodifieruserid;

        /**
         * tppa_dmodifieddate
         */
    @TableField("tppa_dmodifieddate")
        private Date tppaDmodifieddate;

        /**
         * tppa_cmodifierprogramid
         */
    @TableField("tppa_cmodifierprogramid")
        private String tppaCmodifierprogramid;

        /**
         * tppa_navgworktime
         */
    @TableField("tppa_navgworktime")
        private Long tppaNavgworktime;

        /**
         * tppa_cworkingdays_week
         */
    @TableField("tppa_cworkingdays_week")
        private String tppaCworkingdaysWeek;

        /**
         * tppa_nkinmu_num1
         */
    @TableField("tppa_nkinmu_num1")
        private Long tppaNkinmuNum1;

        /**
         * tppa_nopen1
         */
    @TableField("tppa_nopen1")
        private Long tppaNopen1;

        /**
         * tppa_nclose1
         */
    @TableField("tppa_nclose1")
        private Long tppaNclose1;

        /**
         * tppa_nrestopen1
         */
    @TableField("tppa_nrestopen1")
        private Long tppaNrestopen1;

        /**
         * tppa_nrestclose1
         */
    @TableField("tppa_nrestclose1")
        private Long tppaNrestclose1;

        /**
         * tppa_cyobi1
         */
    @TableField("tppa_cyobi1")
        private String tppaCyobi1;

        /**
         * tppa_cholflg1
         */
    @TableField("tppa_cholflg1")
        private String tppaCholflg1;

        /**
         * tppa_nkinmu_num2
         */
    @TableField("tppa_nkinmu_num2")
        private Long tppaNkinmuNum2;

        /**
         * tppa_nopen2
         */
    @TableField("tppa_nopen2")
        private Long tppaNopen2;

        /**
         * tppa_nclose2
         */
    @TableField("tppa_nclose2")
        private Long tppaNclose2;

        /**
         * tppa_nrestopen2
         */
    @TableField("tppa_nrestopen2")
        private Long tppaNrestopen2;

        /**
         * tppa_nrestclose2
         */
    @TableField("tppa_nrestclose2")
        private Long tppaNrestclose2;

        /**
         * tppa_cyobi2
         */
    @TableField("tppa_cyobi2")
        private String tppaCyobi2;

        /**
         * tppa_cholflg2
         */
    @TableField("tppa_cholflg2")
        private String tppaCholflg2;

        /**
         * tppa_nkinmu_num3
         */
    @TableField("tppa_nkinmu_num3")
        private Long tppaNkinmuNum3;

        /**
         * tppa_nopen3
         */
    @TableField("tppa_nopen3")
        private Long tppaNopen3;

        /**
         * tppa_nclose3
         */
    @TableField("tppa_nclose3")
        private Long tppaNclose3;

        /**
         * tppa_nrestopen3
         */
    @TableField("tppa_nrestopen3")
        private Long tppaNrestopen3;

        /**
         * tppa_nrestclose3
         */
    @TableField("tppa_nrestclose3")
        private Long tppaNrestclose3;

        /**
         * tppa_cyobi3
         */
    @TableField("tppa_cyobi3")
        private String tppaCyobi3;

        /**
         * tppa_cholflg3
         */
    @TableField("tppa_cholflg3")
        private String tppaCholflg3;

        /**
         * tppa_nkinmu_num4
         */
    @TableField("tppa_nkinmu_num4")
        private Long tppaNkinmuNum4;

        /**
         * tppa_nopen4
         */
    @TableField("tppa_nopen4")
        private Long tppaNopen4;

        /**
         * tppa_nclose4
         */
    @TableField("tppa_nclose4")
        private Long tppaNclose4;

        /**
         * tppa_nrestopen4
         */
    @TableField("tppa_nrestopen4")
        private Long tppaNrestopen4;

        /**
         * tppa_nrestclose4
         */
    @TableField("tppa_nrestclose4")
        private Long tppaNrestclose4;

        /**
         * tppa_cyobi4
         */
    @TableField("tppa_cyobi4")
        private String tppaCyobi4;

        /**
         * tppa_cholflg4
         */
    @TableField("tppa_cholflg4")
        private String tppaCholflg4;

        /**
         * tppa_nkinmu_num5
         */
    @TableField("tppa_nkinmu_num5")
        private Long tppaNkinmuNum5;

        /**
         * tppa_nopen5
         */
    @TableField("tppa_nopen5")
        private Long tppaNopen5;

        /**
         * tppa_nclose5
         */
    @TableField("tppa_nclose5")
        private Long tppaNclose5;

        /**
         * tppa_nrestopen5
         */
    @TableField("tppa_nrestopen5")
        private Long tppaNrestopen5;

        /**
         * tppa_nrestclose5
         */
    @TableField("tppa_nrestclose5")
        private Long tppaNrestclose5;

        /**
         * tppa_cyobi5
         */
    @TableField("tppa_cyobi5")
        private String tppaCyobi5;

        /**
         * tppa_cholflg5
         */
    @TableField("tppa_cholflg5")
        private String tppaCholflg5;

        /**
         * tppa_nkinmu_num6
         */
    @TableField("tppa_nkinmu_num6")
        private Long tppaNkinmuNum6;

        /**
         * tppa_nopen6
         */
    @TableField("tppa_nopen6")
        private Long tppaNopen6;

        /**
         * tppa_nclose6
         */
    @TableField("tppa_nclose6")
        private Long tppaNclose6;

        /**
         * tppa_nrestopen6
         */
    @TableField("tppa_nrestopen6")
        private Long tppaNrestopen6;

        /**
         * tppa_nrestclose6
         */
    @TableField("tppa_nrestclose6")
        private Long tppaNrestclose6;

        /**
         * tppa_cyobi6
         */
    @TableField("tppa_cyobi6")
        private String tppaCyobi6;

        /**
         * tppa_cholflg6
         */
    @TableField("tppa_cholflg6")
        private String tppaCholflg6;

        /**
         * tppa_nkinmu_num7
         */
    @TableField("tppa_nkinmu_num7")
        private Long tppaNkinmuNum7;

        /**
         * tppa_nopen7
         */
    @TableField("tppa_nopen7")
        private Long tppaNopen7;

        /**
         * tppa_nclose7
         */
    @TableField("tppa_nclose7")
        private Long tppaNclose7;

        /**
         * tppa_nrestopen7
         */
    @TableField("tppa_nrestopen7")
        private Long tppaNrestopen7;

        /**
         * tppa_nrestclose7
         */
    @TableField("tppa_nrestclose7")
        private Long tppaNrestclose7;

        /**
         * tppa_cyobi7
         */
    @TableField("tppa_cyobi7")
        private String tppaCyobi7;

        /**
         * tppa_cholflg7
         */
    @TableField("tppa_cholflg7")
        private String tppaCholflg7;

        /**
         * tppa_nworkingdays_week
         */
    @TableField("tppa_nworkingdays_week")
        private Long tppaNworkingdaysWeek;


        }