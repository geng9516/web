package jp.smartcompany.job.modules.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.appcontrol.TopPageInfo;
import jp.smartcompany.framework.auth.entity.LoginControlEntity;
import jp.smartcompany.framework.relation.PsDesignationCache;
import jp.smartcompany.framework.relation.PsEmpRelationCache;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
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

    private static final long serialVersionUID = 2722637536345008026L;
    // 当前登录用户账号
    private String loginAccount;
    // 法人code
    private String loginCustomer;
    // 公司code
    private String loginCompany;
    // 当前登录用户名
    private String loginUser;
    // 账号对应的员工表里的员工id
    private String loginEmployee;
    // 语言
    private String language;
    // 汉字名称
    private String loginKanjiName;
    // 当前用户所属部门
    private List<Designation> loginDesignation;
    // 当前用户所属用户组
    private Map<String, List<LoginGroupBO>> loginGroups;
    //  ログインユーザの基点組織情報
    private Map<String, Map<String, String>> loginBaseSection;
    // 基点組織情報キャッシュ(基準日毎)
    private Map<String, Map<String, String>> loginGroupBaseSection;
    /** ログインユーザのビヘイビア判定結果 */
    private Map< String, String > loginBehaviors;
    // ログインユーザのアプリケーション起動権限判定結果 ※本番
    private TopPageInfo loginAppPermission;
    /** ビヘイビアを適用する(V3互換用) */
    private boolean useBehaviorForV3Compatible;

    private PsDesignationCache designationCache;
    private PsEmpRelationCache loginEmpRelationCache;

    public List<Designation> getLoginDesignation(String psDate) {
        try {
            Date tdate = SysUtil.transStringToDate(psDate);
            Date sysDate = SysUtil.transStringToDate(SysUtil.getSysdate("yyyy/MM/dd"));
            if (tdate.equals(sysDate)) {
                return getLoginDesignation();
            }
            if ((this.designationCache != null) && (this.designationCache.containsKey(psDate))) {
                return (List<Designation>) this.designationCache.get(psDate);
            }
            if (this.designationCache == null) {
                this.designationCache = new PsDesignationCache(16);
            }
            List<Designation> designationList = getLoginDesignationSessionInfo(psDate);
            this.designationCache.put(psDate, designationList);
            return designationList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Designation> getLoginDesignationSessionInfo(String psDate) {
        IMastEmployeesService iMastEmployeesService = SpringUtil.getBean(IMastEmployeesService.class);
        List<LoginControlEntity> lAccountInfoList = iMastEmployeesService.selectUserInfoByDate(loginUser,language,psDate);
        List<Designation> lDesignationList = CollUtil.newArrayList();
        for (LoginControlEntity accountInfo : lAccountInfoList) {
            Designation designation = new Designation();
            designation.setCustomerCode(accountInfo.getHdCcustomeridCk());
            designation.setCompanyCode(accountInfo.getHdCcompanyidCk());
            designation.setCompanyHierarchy(accountInfo.getMacClayeredcompanyid());
            designation.setCompanyOrder(accountInfo.getMacNseq().toString());

            designation.setCompanyName(accountInfo.getMacCcompanyname());

            designation.setEmployee(accountInfo.getHdCemployeeidCk());

            designation.setUserid(accountInfo.getHdCuserid());

            designation.setName(accountInfo.getMeCemployeename());

            designation.setNameKana(accountInfo.getMeCkananame());

            designation.setSection(accountInfo.getHdCsectionidFk());

            designation.setSectionHierarchy(accountInfo
                    .getMoClayeredsectionid());

            designation.setSectionOrder(accountInfo.getMoNseq().toString());

            designation.setSectionName(accountInfo.getMoCsectionname());

            designation.setPostCode(accountInfo.getHdCpostidFk());

            designation.setPostRank(accountInfo.getMapNweightage());

            designation.setPostName(accountInfo.getMapCpostname());

            designation.setAttachRole(accountInfo.getHdCifkeyoradditionalrole());

            designation.setPersonnelChangesBigin(accountInfo.getHdDstartdateCk());
            designation.setBossOrNot(accountInfo.getHdCbossornot());

            lDesignationList.add(designation);
        }
        return lDesignationList;
    }

}
