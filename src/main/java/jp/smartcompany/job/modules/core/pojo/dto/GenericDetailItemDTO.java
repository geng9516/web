package jp.smartcompany.job.modules.core.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericDetailItemDTO {

    private String mgdCmastercode;
    private String mgdCgenericdetaildesc;
    private String mgdCsparechar1;
    private String mgdCsparechar2;
    private String mgdCsparechar3;
    private String mgdCsoarechar4;
    private String mgdCsoarechar5;
    private Double mgdNsparenum1;
    private Double mgdNsparenum2;
    private Double mgdNsparenum3;
    private Double mgdNsparenum4;
    private Double mgdNsparenum5;
    private Date mgdDsoaredate1;
    private Date mgdDsoaredate2;
    private Date mgdDsoaredate3;
    private Date mgdDsoaredate4;
    private Date mgdDsoaredate5;

}
