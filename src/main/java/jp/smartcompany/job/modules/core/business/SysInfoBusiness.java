package jp.smartcompany.job.modules.core.business;

import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *  PsSysInfoLogicImpl
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysInfoBusiness {

    private final IMastOrganisationService iMastOrganisationService;

    /**
     * <B>【上位組織取得（組織指定）】</B><BR>
     * 自組織の上位組織を<BR>
     * SQL用のカンマ区切りリストで取得する。
     *
     * @param  psCustID         検索対象顧客コード
     * @param  psCompCode       検索対象法人コード
     * @param  psTargetDept     検索対象組織コード
     * @param  pdSearchDate     データ検索基準日
     * @return List < String >     上位組織リスト
     */
    public List< String > getUpperSectionListDeptForSQL(
            String psCustID, String psCompCode, String psTargetDept, Date pdSearchDate) {
        return iMastOrganisationService.selectHighSection(
                psCustID, psCompCode, psTargetDept, pdSearchDate);
    }

    /**
     * <B>【下位組織リスト取得（組織指定）】</B><BR>
     * 自組織の下位組織を<BR>
     * SQL用のカンマ区切りリストで取得する。
     *
     * @param  psCustID         検索対象顧客コード
     * @param  psCompCode       検索対象法人コード
     * @param	psTargetDept     検索対象組織コード
     * @param  pdSearchDate     データ検索基準日
     * @return List < String >  下位組織リスト
     */
    public List < String > getLowerSectionListDeptForSQL(
            String psCustID, String psCompCode, String psTargetDept, Date pdSearchDate) {
        return iMastOrganisationService.getSubSection(
                psCustID, psCompCode, psTargetDept, pdSearchDate);
    }
}
