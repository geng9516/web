package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * public.tmg_mast_csv2workskintai_tgt2
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
@TableName("tmg_mast_csv2workskintai_tgt2")
public class TmgMastCsv2workskintaiTgt2DO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * tcwt_cemployeeid
         */
    @TableId(value="tcwt_cemployeeid",type = IdType.INPUT)
        private String tcwtCemployeeid;

        /**
         * tcwt_kanjiname
         */
    @TableField("tcwt_kanjiname")
        private String tcwtKanjiname;

        /**
         * tcwt_csectionid
         */
    @TableField("tcwt_csectionid")
        private String tcwtCsectionid;

        /**
         * tcwt_cpostid
         */
    @TableField("tcwt_cpostid")
        private Long tcwtCpostid;


        }