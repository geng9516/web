package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 表示月遷移リスト情報
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class MgdAttributeVO {

    /**
     * ATTRIBUTE_URL
     */
    private String attributeUrl;
    /**
     * ITEM_NAME
     */
    private String itemName;
    /**
     * CATEGORY_CODE
     */
    private String categoryCode;
    /**
     * CATEGORY_NAME
     */
    private String categoryName;
    /**
     * ITEM_CODE
     */
    private String itemCode;
    /**
     * ITEM_TYPE
     */
    private String itemType;
    /**
     * ATTRIBUTE_ADDLIMIT
     */
    private String attributeAddlimit;
    /**
     * 予備文字列
     */
    private String mgdC;
}
