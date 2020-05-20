package jp.smartcompany.job.modules.core.business;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.job.common.Constant;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.bo.EvaluatorBO;
import jp.smartcompany.job.modules.core.service.IHistDesignationService;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
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
@Service(CoreBean.Business.SECTION_CHIEF)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Deprecated
public class SectionChiefBusiness {

    private final HttpSession httpSession;
    private final IHistDesignationService iHistDesignationService;
    private final IMastOrganisationService iMastOrganisationService;

    /** 顧客コード */
    private static final String CUSTOMER_ID = "01";

    /**
     * <p>
     * システム情報（所属長情報）を取得する。<br>
     * </p>
     * <div> 基準日時点の所属長情報を取得する。 </div>
     *
     * @author t-abe
     * @param psUserId ユーザID
     * @param pdSearchDate 検索基準日
     * @return 異動歴情報格納クラス。<br>
     *         検索結果が0件の場合、NULLを返却する。
     * @exception
     */
    public List<EvaluatorBO> getSectionChiefWithSection(String psUserId, String psSectionId, Date pdSearchDate) {
        PsSession psSession = (PsSession)httpSession.getAttribute(Constant.PS_SESSION);
        // 言語区分を取得
        String sLanguage = psSession.getLanguage();
        // 言語区分を取得
        // 対象者のユーザ情報(異動暦情報)を取得
        List<EvaluatorBO> evaluatorList
                = iHistDesignationService.selectWithSection(
                CUSTOMER_ID, psUserId, psSectionId, pdSearchDate, sLanguage);

        // ユーザ情報があるか？
        if (evaluatorList == null || evaluatorList.isEmpty()) {
            return null;
        }

        // 返却用
        List<EvaluatorBO> sectionChiefDesignationList = CollUtil.newLinkedList();

        Iterator<EvaluatorBO> evaluatorIte = evaluatorList.iterator();
        while (evaluatorIte.hasNext()) {

            // 所属長情報を取得する
            EvaluatorBO evaluator = evaluatorIte.next();

            List<EvaluatorBO> evaluatorForMyChiefList =
                    iHistDesignationService.selectSectionChief(
                            evaluator.getCustomerCode(),
                            evaluator.getCompanyCode(),
                            evaluator.getSection(),
                            pdSearchDate,
                            sLanguage);

            if (evaluatorForMyChiefList != null && !evaluatorForMyChiefList.isEmpty()) {

                // 複数件の所属長情報が返却された場合は1件目をその組織の所属長とする
                // ・並び順はその異動暦の開始日付および社員番号の昇順
                // ・複数の異動暦がある場合にはその異動暦毎の所属長を返却
                EvaluatorBO evaluatorForMyChief = evaluatorForMyChiefList.get(0);

                // この所属の所属長が自分の場合は？
                // 一つ上位の所属に対する所属長を取得し返却する
                if (evaluatorForMyChief.getUserid().equals(psUserId) && evaluatorForMyChief.getSection().equals(evaluator.getSection())) {

                    // 対象者と所属長が同一なのでこの時点の結果を破棄する
                    evaluatorForMyChief = null;

                    // 上位・下位組織情報取得クラスより1つ上位組織の組織コードを取得
                    List<String> parentSectionCodeList
                            = iMastOrganisationService.selectHighSection(
                            evaluator.getCustomerCode(),
                            evaluator.getCompanyCode(),
                            evaluator.getSection(),
                            pdSearchDate);

                    // 上位組織情報を取得出来た場合
                    if (parentSectionCodeList != null && !parentSectionCodeList.isEmpty()) {

                        // 直上の組織コードを取得
                        int nParentSectionCodeLastIndex = parentSectionCodeList.size() - 2;
                        if (0 <= nParentSectionCodeLastIndex) {

                            String sParentSectionCode
                                    = parentSectionCodeList.get(nParentSectionCodeLastIndex);

                            // 直上の組織コードに対する所属長情報を取得
                            evaluatorForMyChiefList =
                                    iHistDesignationService.selectSectionChief(
                                            evaluator.getCustomerCode(),
                                            evaluator.getCompanyCode(),
                                            sParentSectionCode,
                                            pdSearchDate,
                                            sLanguage);

                            if (evaluatorForMyChiefList != null
                                    && !evaluatorForMyChiefList.isEmpty()) {
                                evaluatorForMyChief = evaluatorForMyChiefList.get(0);
                            }
                        }
                    }
                }

                if (evaluatorForMyChief != null) {
                    sectionChiefDesignationList.add(evaluatorForMyChief);
                }
            }
        }

        return sectionChiefDesignationList;
    }

}
