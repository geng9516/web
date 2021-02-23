package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.csv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ConditionSearchResultDTO {

    /** タイトル列 */
    private List<String> title;

    /** 検索結果列 */
    private List<List<Object>> result;

}
