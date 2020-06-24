package jp.smartcompany.controller;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.OvertimeInstructBean;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.TypeGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Wang Ziyue
 * @description test controller
 * @objectSource
 * @date 2020/06/11
 **/
@RestController
@RequestMapping("sys/overtimeinstruct")
public class OvertimeInstructController {

    @Autowired
    private OvertimeInstructBean overtimeInstructBean;

    /**
     * 申請区分マスタ 一覧用
     *
     * @return {"stutasName":"TMG_NTFSTATUS|0","stutasId":取下}
     */
    @GetMapping("MonthlyResult")
    public void getMgdNtfTypeDispAppList(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        //return overtimeInstructBean.getMgdNtfTypeDispAppList(psDBBean);
    }
}
