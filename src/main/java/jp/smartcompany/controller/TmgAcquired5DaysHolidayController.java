package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;

import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayDispVO;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayUpdateVO;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.TmgAcquired5DaysHolidayBean;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.Acquired5DaysListVO;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.PaidHolidayVO;
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
@RequestMapping("sys/tmgacquired5daysholiday")
public class TmgAcquired5DaysHolidayController {

    @Autowired
    private TmgAcquired5DaysHolidayBean tmgAcquired5DaysHolidayBean;

    /**
     * 年5休暇管理一览界面数据获取
     */
    @GetMapping("list")
    @ResponseBody
    public List<Acquired5DaysListVO> list(@RequestAttribute("BeanName") PsDBBean psDBBean,String year) throws Exception {

        tmgAcquired5DaysHolidayBean.execute(psDBBean);

        return tmgAcquired5DaysHolidayBean.selectList(null,psDBBean);
    }

    /**
     * 年5休暇管理一览界面数据获取
     */
    @GetMapping("edit")
    @ResponseBody
    public List<Acquired5DaysListVO> edit(@RequestAttribute("BeanName") PsDBBean psDBBean,String year, String userCode) throws Exception {

        tmgAcquired5DaysHolidayBean.execute(psDBBean);

        return tmgAcquired5DaysHolidayBean.selectList(userCode,psDBBean);
    }

    /**
     * 年5休暇管理詳細
     */
    @GetMapping("showDisp")
    @ResponseBody
    public List<PaidHolidayVO> showDisp(@RequestAttribute("BeanName") PsDBBean psDBBean, String txtUserCode, String kijunbi, String pdSearchStart, String pdSearchEnd) throws Exception {
        tmgAcquired5DaysHolidayBean.execute(psDBBean);
        return tmgAcquired5DaysHolidayBean.showDisp(txtUserCode,kijunbi,pdSearchStart,pdSearchEnd,psDBBean);
    }

    /**
     * 年次休暇管理詳細
     */
    @GetMapping("update")
    @ResponseBody
    public void update(@RequestAttribute("BeanName") PsDBBean psDBBean, String txtUserCode, String txtDate) throws Exception {
        tmgAcquired5DaysHolidayBean.execute(psDBBean);
         tmgAcquired5DaysHolidayBean.update(psDBBean);
    }


}
