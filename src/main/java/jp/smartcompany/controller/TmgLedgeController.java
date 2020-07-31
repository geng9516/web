package jp.smartcompany.controller;


import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.TmgLedgeDispVo;
import jp.smartcompany.job.modules.tmg.tmgledger.TmgLedgerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("sys/tmgLedger")
public class TmgLedgeController {

    @Autowired
    private TmgLedgerBean tmgLedgerBean;

    /**
     * 帳票出力画面　ini画面用データ取得
     *
     * @param psDBBean
     *
     * @return tmgLedgeDispVo
     */
    @GetMapping("iniDisp")
    public TmgLedgeDispVo iniDisp(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        TmgLedgeDispVo tmgLedgeDispVo = new TmgLedgeDispVo();

        tmgLedgeDispVo.setLedgerSheetVo(tmgLedgerBean.getLedgerSheetList(psDBBean));
        tmgLedgeDispVo.setListBoxYearVo(tmgLedgerBean.getYearlist(psDBBean));
        tmgLedgeDispVo.setListBoxMonthVo(tmgLedgerBean.getMonthlist(psDBBean));
        return tmgLedgeDispVo;
    }
}
