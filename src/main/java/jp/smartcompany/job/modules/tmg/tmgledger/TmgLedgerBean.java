package jp.smartcompany.job.modules.tmg.tmgledger;

import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import jp.smartcompany.job.modules.core.service.ITmgMonthlyService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgledger.vo.LedgerSheetVo;
import jp.smartcompany.job.modules.tmg.tmgledger.vo.ListBoxVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgLedgerBean {

    private final IMastGenericDetailService iMastGenericDetailService;
    private final ITmgMonthlyService iTmgMonthlyService;


    /**
     * 帳票種別リストボックスのデータを取得するクエリ文を生成します。
     */
    public List<LedgerSheetVo> getLedgerSheetList(PsDBBean psDBBean){
        List<LedgerSheetVo> ledgerSheetVoList = iMastGenericDetailService.selectLedgerSheetList(psDBBean.getCustID(),psDBBean.getCompCode(),psDBBean.getLanguage());
        return ledgerSheetVoList;
    }

    /**
     * 対象年リストボックスのデータを取得するクエリ文を生成します。
     */
    public List<ListBoxVo> getYearlist(PsDBBean psDBBean){
        List<ListBoxVo> yearList = iTmgMonthlyService.selectYearDate(psDBBean.getCustID(),psDBBean.getCompCode());
        return yearList;
    }
    /**
     * 対象年月リストボックスのデータを取得するクエリ文を生成します。
     */
    public List<ListBoxVo> getMonthlist(PsDBBean psDBBean){
        List<ListBoxVo> monthList = iTmgMonthlyService.selectMonthDate(psDBBean.getCustID(),psDBBean.getCompCode());
        return monthList;
    }
}
