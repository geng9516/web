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
    public void getLedgerSheetList(PsDBBean psDBBean){
        List<LedgerSheetVo> ledgerSheetVoList = iMastGenericDetailService.selectLedgerSheetList(psDBBean.getCustID(),psDBBean.getCompCode(),psDBBean.getLanguage());
    }

    /**
     * 対象年リストボックスのデータを取得するクエリ文を生成します。
     */
    public void getYearlist(PsDBBean psDBBean){
        List<ListBoxVo> list = iTmgMonthlyService.selectYearDate(psDBBean.getCustID(),psDBBean.getCompCode());
    }
    /**
     * 対象年月リストボックスのデータを取得するクエリ文を生成します。
     */
    public void getMonthlist(PsDBBean psDBBean){
        List<ListBoxVo> list = iTmgMonthlyService.selectMonthDate(psDBBean.getCustID(),psDBBean.getCompCode());
    }
}
