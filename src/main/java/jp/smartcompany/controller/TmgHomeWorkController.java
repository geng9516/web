package jp.smartcompany.controller;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmghomework.TmgHomeWorkBean;
import jp.smartcompany.job.modules.tmg.tmghomework.dto.UpdateDto;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public List<HomeWorkVO>  selectHomeWorkInfo(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                                @RequestParam("baseDate") String baseDate ) {
        return tmgHomeWorkBean.selectHomeWorkData(psDBBean, baseDate);
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

}