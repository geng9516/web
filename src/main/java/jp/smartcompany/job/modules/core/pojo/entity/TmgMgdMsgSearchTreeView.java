package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@TableName("TMG_V_MGD_SEARCH_TREE")
public class TmgMgdMsgSearchTreeView {

    /**
     * 顧客コード
     */
    @TableId(value="MGD_CCUSTOMERID")
    private String customerId;
    /**
     * 法人コード
     */
    @TableField(value="MGD_CCOMPANYID_CK_FK")
    private String companyId;
    /**
     * 終了日
     */
    @TableField(value = "MGD_START_CK")
    private Date startDate;
    /**
     * 終了日
     */
    @TableField(value="MGD_DEND")
    private Date endDate;
    /**
     * 言語区分
     */
    @TableField(value="MGD_CLANGUAGE_CK")
    private String language;
    /**
     * マスタコード(連番)
     */
    @TableField(value = "MGD_CMASTERCODE")
    private String masterCode;
    /**
     * 項目名
     */
    @TableField(value="MGD_CITEMNAME")
    private String name;
    /**
     * 表示メッセージ
     */
    @TableField(value="MGD_CMSG")
    private String msg;

}
