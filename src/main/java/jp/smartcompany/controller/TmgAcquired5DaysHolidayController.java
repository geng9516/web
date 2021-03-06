package jp.smartcompany.controller;

import jp.smartcompany.job.modules.core.util.PsDBBean;

import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.TmgAcquired5DaysHolidayBean;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.Acquired5DaysListVO;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.PaidHolidayVO;

import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.UpdateAcquired5DaysVO;
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
    public List<Acquired5DaysListVO> list(@RequestAttribute("BeanName") PsDBBean psDBBean, String recordDate, String year,String txtUserCode) throws Exception {

        tmgAcquired5DaysHolidayBean.execute(psDBBean, recordDate, year);

        return tmgAcquired5DaysHolidayBean.selectList(txtUserCode, psDBBean, recordDate);
    }

    /**
     * 年5休暇管理詳細
     */
    @GetMapping("showDisp")
    @ResponseBody
    public List<PaidHolidayVO> showDisp(@RequestAttribute("BeanName") PsDBBean psDBBean, String recordDate, String year, String txtUserCode, String kijunbi, String pdSearchStart, String pdSearchEnd) throws Exception {

        tmgAcquired5DaysHolidayBean.execute(psDBBean, recordDate, year);

        return tmgAcquired5DaysHolidayBean.showDisp(txtUserCode, kijunbi, pdSearchStart, pdSearchEnd, psDBBean);
    }

    /**
     * 年5日有給指定
     */
    @PostMapping(value = "update", produces = "application/json;charset=UTF-8")
    public void update(@RequestAttribute("BeanName") PsDBBean psDBBean,String year, @RequestBody UpdateAcquired5DaysVO updateAcquired5DaysVO) throws Exception {

        tmgAcquired5DaysHolidayBean.execute(psDBBean, updateAcquired5DaysVO.getRecordDate(), year);

        tmgAcquired5DaysHolidayBean.update(psDBBean,updateAcquired5DaysVO);
    }
}
