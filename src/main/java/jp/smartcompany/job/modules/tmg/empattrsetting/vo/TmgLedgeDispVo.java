package jp.smartcompany.job.modules.tmg.empattrsetting.vo;

import jp.smartcompany.job.modules.tmg.tmgledger.vo.LedgerSheetVo;
import jp.smartcompany.job.modules.tmg.tmgledger.vo.ListBoxVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TmgLedgeDispVo {
    /**
     * 帳票種別リスト
     */
    private List<LedgerSheetVo> ledgerSheetVo;

    /**
     * 対象年リスト
     */
    private List<ListBoxVo> listBoxYearVo;

    /**
     * 対象年月リスト
     */
    private List<ListBoxVo> ListBoxMonthVo;
}
