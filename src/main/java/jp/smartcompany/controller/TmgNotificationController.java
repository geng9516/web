package jp.smartcompany.controller;


import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceDateInfoDTO;
import jp.smartcompany.job.modules.tmg.tmgnotification.TmgNotificationBean;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.paramNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.*;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private HttpServletRequest request;

    /**
         * 申請ステータス
     *
     * @return {"stutasName":"TMG_NTFSTATUS|0","stutasId":取下}
     */
    @GetMapping("StatusFlg")
   public List<StutasFlgVo> getStutas(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        return tmgNotificationBean.getStutas(psDBBean);
    }

    /**
     * 履歴データ一覧
     *
     * @return {"stutasName":"TMG_NTFSTATUS|0","stutasId":取下}
     */
    @GetMapping("NotificationList")
    public NotificationDispVo getNotificationList(@RequestParam("statusFlg") String statusFlg,
                                                        @RequestParam("ntfTypeId") String ntfTypeId,
                                                        @RequestParam("year") String year,
                                                        @RequestParam("page") int page,
                                                        @RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgNotificationBean.getNotificationList(statusFlg,ntfTypeId,year,page,psDBBean);
    }

    /**
     * 申請区分マスタ 新規用
     *
     * @return {"stutasName":"TMG_NTFSTATUS|0","stutasId":取下}
     */
    @GetMapping("NtfTypeList")
    public List<TypeGroupVo> getMgdNtfTypeAppList(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgNotificationBean.getMgdNtfTypeList(psDBBean);
    }


    /**
     * 申請区分マスタ 一覧用
     *
     * @return {"stutasName":"TMG_NTFSTATUS|0","stutasId":取下}
     */
    @GetMapping("NtfTypeDispList")
    public List<TypeGroupVo> getMgdNtfTypeDispAppList(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgNotificationBean.getMgdNtfTypeDispAppList(psDBBean);
    }

    /**
     * 获得历史申请数据
     *
     * @return {"stutasName":"TMG_NTFSTATUS|0","stutasId":取下}
     */
    @GetMapping("RestYearList")
    public List<restYearVo> getRestYear(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgNotificationBean.getRestYear(psDBBean);
    }


    /**
     * 新規申請
     *
     * @return　エラー null 为正常申请
     */
    @PostMapping("MakeNewApply")
    public String makeNewApply(
            @RequestParam("Param") paramNotificationListDto param,
            @RequestParam("uploadFiles")MultipartFile[] uploadFiles,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        //todo
        TmgReferList referList = null;
        if(psDBBean.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)){
            referList=null;
        }else{
            referList = new TmgReferList(psDBBean, "TmgNotification", param.getToday(), TmgReferList.TREEVIEW_TYPE_LIST, true,
                    false, false, false, false);
        }
        String filePath = request.getSession().getServletContext().getRealPath("uploadFile/notificationUploadFiles/");
        return tmgNotificationBean.actionMakeApply(psDBBean,param,referList,uploadFiles,filePath);
    }

    /**
     * 入力site 取下处理
     *需要siteid
     * @return　エラー(-1　失敗　１成功)
     */
    @PostMapping("EditWithdrop")
    public int editWithdrop(
            @RequestParam("NtfNo") String ntfNo,
            @RequestParam("Action") String action,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return tmgNotificationBean.actionEditWithdrop(action,ntfNo,psDBBean);
    }

    /**
     * 組織図
     *
     * @return　エラー
     */
    @GetMapping("ReferList")
    public int getReferliSt() throws Exception {
        return TmgReferList.TREEVIEW_TYPE_LIST;
    }


}
