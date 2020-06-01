package jp.smartcompany.job.modules.tmg.tmgresults.dto;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Wang Ziyue
 * TMG_DISPMONTHLYITEMS
 */
@Getter
@Setter
@ToString
public class TmgDispItemsDto {
    /**ヘッダ名称*/
    @TableField("MGD_CHEADER")
    private String mgdCHeader;
    /**Select句*/
    @TableField("MGD_CSQL")
    private String mgdCSql;
    /**カラム名*/
    @TableField("MGD_CCOLUMNID")
    private String mgdCColumnId;
    /**カラムKEY*/
    @TableField("MGD_CCOLUMNKEY")
    private String mgdCColumnKey;
    /**表示順*/
    @TableField("MGD_NSEQ")
    private int mgdNSeq;

}
