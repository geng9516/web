package jp.smartcompany.framework.relation;


import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.job.modules.core.business.BaseSectionBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.BaseSectionBO;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import javax.servlet.http.HttpSession;

/**
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PsEmpRelation {

    public static final String RELATION_CACHE_SIZE = "RelationCacheSize";
    private final BaseSectionBusiness baseSectionBusiness;
    private final ScCacheUtil scCacheUtil;
    private final IMastEmployeesService iMastEmployeesService;

    public int getRelationId(String sCust, String sLoginUser,
                             String sTargetUser, String sSystem, String sDate) {
        HttpSession session = ContextUtil.getSession();
        PsSession pssess = (PsSession) session.getAttribute(Constant.PS_SESSION);
        int nRelId;
        if (sLoginUser.equals(sTargetUser)) {
            return 0;
        }
        PsEmpRelationCache mapCache = pssess.getLoginEmpRelationCache();
        String sKey = sCust + "_" + sTargetUser + "_" + sSystem + "_" + sDate;
        if ((mapCache != null) && (mapCache.containsKey(sKey))) {
            nRelId = mapCache.get(sKey);
            return nRelId;
        }
        List<Designation> lDesig = pssess.getLoginDesignation(sDate);
        StringBuilder sbDesig = new StringBuilder();
        for (int i = 0; i < lDesig.size(); i++) {
            if (i != 0) {
                sbDesig.append("!");
            }
            sbDesig.append(lDesig.get(i).toString());
        }
        String sDesig = sbDesig.toString();

        BaseSectionBO bsi = baseSectionBusiness.getBaseSection(sDate,ContextUtil.getHttpRequest().getSession());
        Map<String, Map<String, String>> mBaseAll = bsi.getHmGroup();
        Map<String, String> mBase = mBaseAll.get(sSystem);
        Set<String> oCompSet = mBase.keySet();
        StringBuilder sbBase = new StringBuilder();
        String sBaseKey;
        for (String s : oCompSet) {
            sBaseKey = s;
            String sBaseSection = mBase.get(sBaseKey);
            if (StrUtil.isNotBlank(sBaseSection)) {
                if (sbBase.length() > 0) {
                    sbBase.append("!");
                }
                sbBase.append(sBaseSection);
            }
        }
        String sBase = sbBase.toString();
        if ("true".equalsIgnoreCase(scCacheUtil.getSystemProperty("BaseSectionExpandParameter"))) {
            String[] aBase = splitBaseSection(sBase);
            nRelId = iMastEmployeesService.selectRelationEx(
                    sCust, sLoginUser, sTargetUser, sSystem, sDesig, sDate,
                    aBase[0], aBase[1], aBase[2], aBase[3], aBase[4], aBase[5],
                    aBase[6], aBase[7]);
        } else {
            nRelId = iMastEmployeesService.selectRelation(
                    sCust, sLoginUser, sTargetUser, sSystem, sDesig + "\\"
                            + sBase, sDate);
        }
        if (mapCache == null) {
            int nMax;
            try {
                nMax = Integer.parseInt(scCacheUtil.getSystemProperty(RELATION_CACHE_SIZE));
            } catch (NumberFormatException e) {
                nMax = 50;
            }
            mapCache = new PsEmpRelationCache(nMax);
        }
        mapCache.put(sKey, nRelId);
        pssess.setLoginEmpRelationCache(mapCache);
        return nRelId;
    }

    String[] splitBaseSection(String psBase) {
        int sPLITLENGTH = 4000;
        String[] aBase = { null, null, null, null, null, null, null, null };
        for (int i = 0; i < 8; i++) {
            if (psBase.length() < i * sPLITLENGTH) {
                break;
            }
            int nEnd = (i + 1) * sPLITLENGTH;
            if (psBase.length() < nEnd) {
                nEnd = psBase.length();
            }
            aBase[i] = psBase.substring(i * sPLITLENGTH, nEnd);
        }
        return aBase;
    }

}
