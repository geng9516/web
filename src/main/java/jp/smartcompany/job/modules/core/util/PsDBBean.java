package jp.smartcompany.job.modules.core.util;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.util.SysUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class PsDBBean {

    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

    protected String compCode = null;
    protected String employeeCode = null;
    protected String strUserID = null;
    protected String custID = null;
    protected String systemCode = null;
    public Map<String, String> requestHash;
    private String language;

    public String getReqParam(String sKey) {
        Object oVal = null;
        oVal = requestHash.get(sKey);
        if (oVal == null) {
            return null;
        }
        return oVal.toString();
    }

    public String getSysDate() {
        return getDataBaseDate();
    }

    private String getDataBaseDate() {
        try {
            return DateUtil.format(DateUtil.date(), DEFAULT_DATE_FORMAT);
        } catch (Exception e) {
        }
        return null;
    }

    public String getSiteId() {
        return this.requestHash.get("SiteId");
    }

    public String getUserCode() {
        return this.strUserID;
    }

    public String escDBString(String sString) {
        return SysUtil.transStringNullToDB(
                SysUtil.escapeQuote(sString));
    }



}
