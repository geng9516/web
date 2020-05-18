package jp.smartcompany.job.modules.core.util;

import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PsSession implements Serializable {

    private static final long serialVersionUID = 1L;
    private String loginAccount;
    private String loginCustomer;
    private String loginCompany;
    private String loginUser;
    private String loginEmployee;
    private String language;
    private String loginKanjiName;
    private List<Designation> loginDesignation;
    private String defaultCssCategory;
    private String loginCssCategory;
    private Map<String, List<LoginGroupBO>> loginGroups;

    private Map<String, Map<String, String>> loginBaseSection;
    private Map<String, Map<String, String>> loginGroupBaseSection;

}
