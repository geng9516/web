package jp.smartcompany.job.modules.core.business;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.bo.DesignationBO;
import jp.smartcompany.job.modules.core.pojo.bo.EvaluatorBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastRelationshipDO;
import jp.smartcompany.job.modules.core.service.IHistDesignationService;
import jp.smartcompany.job.modules.core.service.IMastRelationshipService;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
@Deprecated
@Service(CoreBean.Business.EVALUATOR)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluatorSysInfoBusiness {

    private final IHistDesignationService iHistDesignationService;
    private final IMastRelationshipService iMastRelationshipService;
    private final SectionChiefBusiness sectionChiefBusiness;
    private final HttpSession httpSession;

    public List<EvaluatorBO> getEvaluator(String customerId, String systemId,
                                          String userId, Date pdSearchDate, int evaluation,
                                          String reportType, boolean judgFlg) {
        PsSession psSession = (PsSession)httpSession.getAttribute(Constant.PS_SESSION);
        String sLanguage = psSession.getLanguage();
        if (!judgFlg) {
            List<EvaluatorBO> manualEvaluatorList = iHistDesignationService
                    .selectEvaluator(customerId, systemId, userId,
                            pdSearchDate, evaluation,
                            reportType, sLanguage);
            if (CollUtil.isNotEmpty(manualEvaluatorList)) {
                return manualEvaluatorList;
            }
        }
        return autoEvaluator(customerId, systemId,
                userId, pdSearchDate, evaluation, sLanguage);
    }

    protected List<EvaluatorBO> autoEvaluator(String psCustomerId,
                                            String psSystemId, String psUserId, Date pdSearchDate,
                                            int pnEvaluation, String psLanguage) {
        List<EvaluatorBO> designationTergetUserList = iHistDesignationService.selectAllEvaluator(
                psCustomerId, psUserId, pdSearchDate, psLanguage);
        if (CollUtil.isEmpty(designationTergetUserList)) {
            return CollUtil.newLinkedList();
        }
        List<String> initSections = CollUtil.newLinkedList();
        for (EvaluatorBO evaluatorBO : designationTergetUserList) {
            initSections.add(evaluatorBO.getSectionHierarchy());
        }
        List<MastRelationshipDO> evaluationLevelList = iMastRelationshipService.selectEvaluationLevel(psSystemId, pnEvaluation, pdSearchDate);
        Iterator<MastRelationshipDO> evaluationLevelIte = evaluationLevelList.iterator();
        while (evaluationLevelIte.hasNext()) {
            MastRelationshipDO mastRelationshipEntity = evaluationLevelIte
                    .next();
            Long nEvaluationLevel = mastRelationshipEntity
                    .getMrNevaluation();

            List<EvaluatorBO> designationListForSectionChief = CollUtil.newLinkedList();
            Iterator<EvaluatorBO> designationTergetUserIte = designationTergetUserList.iterator();
            while (designationTergetUserIte.hasNext()) {
                DesignationBO designationTergetUser = designationTergetUserIte.next();
                String sDesignationTergetUserid = designationTergetUser.getUserid();
                String sSectionId = designationTergetUser.getSection();
//                if ((SystemPropertyCache.getValue("SkipSameBoss") != null)
//                        && (SystemPropertyCache.getValue("SkipSameBoss")
//                        .equalsIgnoreCase("yes"))) {
//                    for (;;) {
//                        List<Evaluator> designationListForSectionChiefTemp = this.psSectionChiefLogic
//                                .getSectionChiefWithSection(
//                                        sDesignationTergetUserid, sSectionId,
//                                        pdSearchDate);
//                        if ((designationListForSectionChiefTemp == null)
//                                || (designationListForSectionChiefTemp.size() == 0)) {
//                            break;
//                        }
//                        if (!((Evaluator) designationListForSectionChiefTemp
//                                .get(0)).getUserid().equals(
//                                sDesignationTergetUserid)) {
//                            break;
//                        }
//                        sSectionId = ((Evaluator) designationListForSectionChiefTemp
//                                .get(0)).getSection();
//                    }
//                }
                List<EvaluatorBO> designationListForSectionChiefTemp = sectionChiefBusiness.getSectionChiefWithSection(sDesignationTergetUserid,
                                sSectionId, pdSearchDate);

                if ((designationListForSectionChiefTemp != null)
                        && (!designationListForSectionChiefTemp.isEmpty())) {
                    Iterator<String> initSectionIte = initSections.iterator();
                    for (int i = 0; i < designationListForSectionChiefTemp
                            .size(); i++) {
                        boolean bDuplicate = true;
                        while (initSectionIte.hasNext()) {
                            String sSectionHierarchy = initSectionIte
                                    .next();
                            if (sSectionHierarchy.contains(designationListForSectionChiefTemp
                                    .get(i).getSectionHierarchy())) {
                                bDuplicate = false;
                                break;
                            }
                        }
                        if (bDuplicate) {
                            designationListForSectionChiefTemp.remove(i);
                        }
                    }
                    Iterator<EvaluatorBO> designationIteForSectionChiefTemp = designationListForSectionChiefTemp
                            .iterator();
                    while (designationIteForSectionChiefTemp.hasNext()) {
                        EvaluatorBO designationForSectionChiefTemp = designationIteForSectionChiefTemp.next();
//                        if (SystemPropertyCache.getValue("SkipSameBoss").equalsIgnoreCase("yes")) {
//                            if (!designationForSectionChiefTemp.getUserid()
//                                    .equals(sDesignationTergetUserid)) {
//                                designationForSectionChiefTemp
//                                        .setEvalLevel(nEvaluationLevel
//                                                .toString());
//                                designationForSectionChiefTemp.setJudgDiv("0");
//
//                                designationListForSectionChief
//                                        .add(designationForSectionChiefTemp);
//                            }
//                        }
//                        else if ((!designationForSectionChiefTemp.getUserid()
//                                .equals(sDesignationTergetUserid))
//                                || (!designationForSectionChiefTemp
//                                .getSection().equals(sSectionId))) {
                            designationForSectionChiefTemp
                                    .setEvalLevel(nEvaluationLevel.toString());
                            designationForSectionChiefTemp.setJudgDiv("0");

                            designationListForSectionChief
                                    .add(designationForSectionChiefTemp);
//                        }
                    }
                }
            }
            designationTergetUserList = designationListForSectionChief;
        }
        return designationTergetUserList;
    }

}
