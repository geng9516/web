package jp.smartcompany.job.modules.core.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class PsDBBean {

    protected String compCode = null;
    protected String employeeCode = null;
    protected String strUserID = null;
    protected String custID = null;
    protected String SystemCode = null;
    private String language;

}
