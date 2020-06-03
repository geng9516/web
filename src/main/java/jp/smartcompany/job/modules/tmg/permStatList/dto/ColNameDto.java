package jp.smartcompany.job.modules.tmg.permStatList.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 項目名DTO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ColNameDto {

    private String colName;
    private String disppermStatusName;
    private String disppermStatus;
}
