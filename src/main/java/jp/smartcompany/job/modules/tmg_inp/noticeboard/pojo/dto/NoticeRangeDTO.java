package jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeRangeDTO {

    private String typeId;
    private String typeName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer peopleCount;

}
