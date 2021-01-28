package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.paidholiday.PaidholidayBean;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayDispVO;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayUpdateVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nie Wanqun
 * @description controller
 * @objectSource
 * @date 2020/05/18
 **/
@RestController
@RequestMapping("sys/paidholiday")
public class PaidholidayController {

    @Autowired
    private  PaidholidayBean paidHolidayBean;

    /**
     * 年次休暇管理一览界面数据获取
     */
    @GetMapping("list")
    @ResponseBody
    public List<PaidHolidayInitVO> list(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        return paidHolidayBean.actionInitHandler(referList.buildSQLForSelectEmployees());
    }

    /**
     * 年次休暇管理詳細
     */
    @GetMapping("detail")
    @ResponseBody
    public PaidHolidayDispVO detail(@RequestAttribute("BeanName") PsDBBean psDBBean, String txtUserCode, String txtDate) throws Exception {

        return paidHolidayBean.showDispDetail(psDBBean, txtUserCode, txtDate);
    }

    /**
     * 年次休暇管理詳細
     */
    @GetMapping("detailList")
    @ResponseBody
    public List<PaidHolidayDispVO> detailList(@RequestAttribute("BeanName") PsDBBean psDBBean, String txtUserCode, String txtDate) throws Exception {

        return paidHolidayBean.showDispList(psDBBean, txtUserCode, txtDate);
    }

    /**
     * 年次休暇管理詳細
     */
    @GetMapping("execUpdate")
    @ResponseBody
    public void execUpdate(@RequestAttribute("BeanName") PsDBBean psDBBean, PaidHolidayUpdateVO paidHolidayUpdateVO, String txtDate) throws Exception {
         paidHolidayBean.execUpdate(psDBBean, paidHolidayUpdateVO, txtDate);
    }


}
