package jp.smartcompany.framework.jsf.orgtree.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import jp.smartcompany.framework.jsf.orgtree.dto.OrgTreeDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrgTreeVO {

    private Long moId;
    private String sectionId;
    private String customerId;
    private String companyId;
    private String parentSectionId;
    private String sectionName;
    private String companyName;
    private String layeredSectionId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OrgTreeVO> children;

    public static OrgTreeVO build(OrgTreeDTO orgTreeDTO) {
        OrgTreeVO deptTreeVO = new OrgTreeVO();
        deptTreeVO.setMoId(orgTreeDTO.getMoId());
        deptTreeVO.setCompanyId(orgTreeDTO.getMoCcompanyidCkFk());
        deptTreeVO.setCustomerId(orgTreeDTO.getMoCcustomeridCkFk());
        deptTreeVO.setSectionId(orgTreeDTO.getMoCsectionidCk());
        deptTreeVO.setParentSectionId(orgTreeDTO.getMoCparentid());
        deptTreeVO.setCompanyName(orgTreeDTO.getMacCcompanyname());
        deptTreeVO.setLayeredSectionId(orgTreeDTO.getMoClayeredsectionid());
        deptTreeVO.setSectionName(orgTreeDTO.getMoCsectionname());
        return deptTreeVO;
    }

}
