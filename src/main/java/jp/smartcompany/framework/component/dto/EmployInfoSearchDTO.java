package jp.smartcompany.framework.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class EmployInfoSearchDTO implements Serializable {

    private String propValue;
    private String searchWord;
    private String searchWordConve;
    private String searchWordEnglish;
    private String targetComp;
    private String targetDept;
    private String searchDate;
    private String language;
    private String exists;
    private String designation;
    private List<String> validCompanies;
    private String searchFlg;
    private String loginUser;
    private String systemId;
    private String orderBy;
    private String companyCode;
    private String ifKeyorAdditionalRole;
    private String page;
    private String limit;
    private String order;
    private String sidx;

}
