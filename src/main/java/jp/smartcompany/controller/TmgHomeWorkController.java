package jp.smartcompany.controller;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmghomework.TmgHomeWorkBean;
import jp.smartcompany.job.modules.tmg.tmghomework.dto.UpdateDto;
import jp.smartcompany.job.modules.tmg.tmghomework.dto.UpdateListDto;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkAdminListVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkAdminVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkMonthVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author 顧成斌
 * @description 在宅勤務登録
 * @objectSource null
 * @date 2020/11/20
 **/
@RestController
@RequestMapping("sys/homeWork")
public class TmgHomeWorkController {

    @Autowired
    private TmgHomeWorkBean tmgHomeWorkBean;

    /**
     * 表示処理
     * http://localhost:6879/sys/homeWork/selectHomeWorkInfo
     *
     * @param psDBBean
     * @param baseDate
     * @return
     */
    @GetMapping("selectHomeWorkInfo")
    @ResponseBody
    public List<HomeWorkVO> selectHomeWorkInfo(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                                @RequestParam("baseDate") String baseDate ) {
        return tmgHomeWorkBean.selectHomeWorkData(psDBBean, baseDate);
    }

    /**
     * ADMIN表示処理
     * http://localhost:6879/sys/homeWork/selectAdminHomeWorkInfo
     *
     * @param psDBBean
     * @param baseDate
     * @return
     */
    @GetMapping("selectAdminHomeWorkInfo")
    @ResponseBody
    public List<HomeWorkAdminVO> selectAdminHomeWorkInfo(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                                          @RequestParam("baseDate") String baseDate ) throws Exception {
        return tmgHomeWorkBean.selectAdminHomeWorkInfo(psDBBean, baseDate);
    }

    /**
     * ADMIN表示処理
     * http://localhost:6879/sys/homeWork/selectAdminHomeWorkList
     *
     * @param psDBBean
     * @param baseDate
     * @return
     */
    @GetMapping("selectAdminHomeWorkList")
    @ResponseBody
    public List<HomeWorkAdminListVO> selectAdminHomeWorkList(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                             @RequestParam("baseDate") String baseDate ) throws Exception {

        return tmgHomeWorkBean.selectAdminHomeWorkList(psDBBean, baseDate);
    }

    /**
     * ADMIN表示処理
     * http://localhost:6879/sys/homeWork/selectAdminHomeWorkUpdateList
     *
     * @param psDBBean
     * @param baseDate
     * @return
     */
    @GetMapping("selectAdminHomeWorkUpdateList")
    @ResponseBody
    public List<HomeWorkAdminVO> selectAdminHomeWorkUpdateList(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                                             @RequestParam("baseDate") String baseDate ) throws Exception {

        return tmgHomeWorkBean.selectAdminHomeWorkUpdateList(psDBBean, baseDate);
    }

    /**
     * Month表示処理
     * http://localhost:6879/sys/homeWork/selectAdminHomeWorkMonthList
     *
     * @param psDBBean
     * @param baseDate
     * @return
     */
    @GetMapping("selectAdminHomeWorkMonthList")
    @ResponseBody
    public List<HomeWorkMonthVO> selectAdminHomeWorkMonthList(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                                              @RequestParam("baseDate") String baseDate ) throws Exception {

        return tmgHomeWorkBean.selectAdminHomeWorkMonthList(psDBBean, baseDate);
    }

    /**
     * 登録処理
     * http://localhost:6879/sys/homeWork/updateHmoeWork
     *
     * @param psDBBean
     * @param homeWorkVO
     */
    @PostMapping("updateHmoeWork")
    @ResponseBody
    public void updateHmoeWork(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                         @RequestBody UpdateDto homeWorkVO ){
         tmgHomeWorkBean.updateHmoeWorkData( psDBBean, homeWorkVO.getHomeWorkVO());
    }
    /**
     * 登録処理
     * http://localhost:6879/sys/homeWork/updateHmoeWorkAdminData
     *
     * @param psDBBean
     * @param homeWorkVO
     */
    @PostMapping("updateHmoeWorkAdminData")
    @ResponseBody
    public void updateHmoeWorkAdminData(@RequestAttribute("BeanName") PsDBBean psDBBean,
                               @RequestBody UpdateDto homeWorkVO ){
        tmgHomeWorkBean.updateHmoeWorkAdminData( psDBBean, homeWorkVO.getHomeWorkVO());
    }

    /**
     * 登録処理
     * http://localhost:6879/sys/homeWork/updateHmoeWorkAdminDataList
     *
     * @param psDBBean
     * @param homeWorkAdminVO
     */
    @PostMapping("updateHmoeWorkAdminDataList")
    @ResponseBody
    public void updateHmoeWorkAdminDataList(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                        @RequestBody UpdateListDto homeWorkAdminVO ){
        tmgHomeWorkBean.updateHmoeWorkAdminDataList( psDBBean, homeWorkAdminVO.getHomeWorkAdminVO());
    }

}