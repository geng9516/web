package jp.smartcompany.job.modules.tmg.evaluatersetting;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.ToString;

/**
 * @author Xiao Wenpeng
 */
@ToString
public class EvaluatorSettingParam {

    private String site;
    private String language;
    private String action;
    private String YYYYMMDD;
    private String companyId;
    private String customerId;
    private String section;
    private String sectionName;
    private String rootGroup;
    private String group;
    private String groupName;
    private String employee;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    // 当前为承认site
    public boolean isSiteTp() {
        return TmgUtil.Cs_SITE_ID_TMG_PERM.equals(site);
    }

    // 当前为管理site
    public boolean isSiteTa() {
        return TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(site);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAction() {
        // 初回起動時：権限設定状況表示
        if(StrUtil.isBlank(action)){
            return EvaluatorSettingConst.ACT_DISP_REVALLIST;
        } else {
            return action;
        }
    }
    public void setAction(String action) {
        this.action = action;
    }

    public String getYYYYMMDD() {
        return YYYYMMDD;
    }

    public void setYYYYMMDD(String YYYYMMDD) {
        this.YYYYMMDD = YYYYMMDD;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerID(String sCustomerID) {
        this.customerId = sCustomerID;
    }

    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    /**
     * 組織が選択されているかどうか
     * @return  boolean	組織が指定されている場合true
     */
    public boolean isSection() {
        return StrUtil.isNotBlank(section);
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getRootGroup() {
        return this.rootGroup;
    }

    public void setRootGroup(String rootGroup) {
        this.rootGroup = rootGroup;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

}
