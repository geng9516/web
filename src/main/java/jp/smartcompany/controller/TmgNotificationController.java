package jp.smartcompany.controller;


import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceDateInfoDTO;
import jp.smartcompany.job.modules.tmg.tmgnotification.TmgNotificationBean;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.*;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Wang Ziyue
 * @description test controller
 * @objectSource
 * @date 2020/06/11
 **/
@RestController
@RequestMapping("sys/vapply")
public class TmgNotificationController {

    @Autowired
    private TmgNotificationBean tmgNotificationBean;


    /**
         * 申請ステータス
     *
     * @return {"stutasName":"TMG_NTFSTATUS|0","stutasId":取下}
     */
    @GetMapping("StatusFlg")
   public List<StutasFlgVo> getStutas() {
        //初期化対象
        return tmgNotificationBean.getStutas();
    }

    /**
     * 一覧
     *
     * @return {"stutasName":"TMG_NTFSTATUS|0","stutasId":取下}
     */
    @GetMapping("NotificationList")
    public List<notificationListVo> getNotificationList(@RequestParam("statusFlg") String statusFlg,
                                                        @RequestParam("ntfTypeId") String ntfTypeId,
                                                        @RequestParam("year") String year,
                                                        @RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgNotificationBean.getNotificationList(statusFlg,ntfTypeId,year,psDBBean);
    }

    /**
     * 申請区分マスタ
     *
     * @return {"stutasName":"TMG_NTFSTATUS|0","stutasId":取下}
     */
    @GetMapping("NtfTypeList")
    public List<TypeGroupVo> getMgdNtfTypeDispAppList(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgNotificationBean.getMgdNtfTypeDispAppList(psDBBean);
    }


    /**
     * 获得历史申请数据
     *
     * @return {"stutasName":"TMG_NTFSTATUS|0","stutasId":取下}
     */
    @GetMapping("RestYearList")
    public List<restYearPaidHolidayVo> getRestYear(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgNotificationBean.getRestYear(psDBBean);
    }

}