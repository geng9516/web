package jp.smartcompany.job.modules.core.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class TreeSearchConditionVO {

    private String searchBtnText;
    private Map<String,String> searchTypes;
    private Map<String,String> searchConditions;

}
