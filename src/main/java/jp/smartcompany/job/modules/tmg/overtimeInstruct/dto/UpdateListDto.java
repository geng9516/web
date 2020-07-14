package jp.smartcompany.job.modules.tmg.overtimeInstruct.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UpdateListDto {
    private List<UpdateDto> updateDtoList;
}
