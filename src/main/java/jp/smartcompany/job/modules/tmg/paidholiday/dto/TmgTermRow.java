package jp.smartcompany.job.modules.tmg.paidholiday.dto;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Wang Ziyue
 */
@Getter
@Setter
@ToString
public class TmgTermRow {

    @TableField("CTYPE")
    private String cType;

    @TableField("DOPEN")
    private Date dOpen;

    @TableField("DCLOSE")
    private Date dClose;

    @TableField("CVALUE_A")
    private String CValueA;

    @TableField("CVALUE_B")
    private String CValueB;

}
