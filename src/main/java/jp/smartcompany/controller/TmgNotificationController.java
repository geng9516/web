package jp.smartcompany.controller;


import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgnotification.TmgNotificationBean;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ParamNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.*;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    @Autowired
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
       // String filepath=request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + "/uploadFile";
        return tmgNotificationBean.getNotificationList(statusFlg,ntfTypeId,year,page,psDBBean,"");
    }

    /**
     * 再申請履歴データ
     *
     */
    @GetMapping("NotificationDetail")
    public NotificationDetailVo getNotificationDetail(@RequestParam("ntfNo") String ntfNo,
                                                  @RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgNotificationBean.getNotificationDetail(ntfNo,psDBBean);
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
    public List<RestYearVo> getRestYear(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgNotificationBean.getRestYear(psDBBean);
    }


    /**
     * 新規申請/再申請/代理申請
     *
     * @return　エラー null 为正常申请
     */
    @PostMapping("MakeApply")
    @ResponseBody
    public GlobalResponse makeApply(
            @RequestParam("params") String params,
            @RequestParam(value = "uploadFiles" ,required=false) MultipartFile[] uploadFiles,
            @RequestParam(value ="deleteFiles",required=false) String[] deleteFiles,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        ParamNotificationListDto paramNotificationListDto =JSONUtil.parse(params).toBean(ParamNotificationListDto.class);
        //String filePath = request.getSession().getServletContext().getRealPath("uploadFile/notificationUploadFiles/");
        return tmgNotificationBean.actionMakeApply(psDBBean,paramNotificationListDto,uploadFiles,deleteFiles);
    }



    /**
     * 入力site 取下处理
     *需要siteid
     * @return　エラー
     */
    @PostMapping("EditWithdrop")
    public GlobalResponse editWithdrop(
            @RequestBody Map map,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return tmgNotificationBean.actionEditWithdrop(map.get("action").toString(),map.get("ntfNo").toString(),psDBBean);
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
