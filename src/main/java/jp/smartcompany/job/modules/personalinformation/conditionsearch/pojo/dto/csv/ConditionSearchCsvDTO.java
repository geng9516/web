package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.csv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConditionSearchCsvDTO {

    private int fromNum;
    private int toNum;
    private String fileName;
    private String filePath;

}
