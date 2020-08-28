package jp.smartcompany.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgnotification.TmgNotificationBean;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ParamNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.*;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
    public NotificationDispVo getNotificationList(@RequestParam(value = "statusFlg",required=false) String statusFlg,
                                                        @RequestParam(value ="ntfTypeId",required=false) String ntfTypeId,
                                                        @RequestParam(value ="serchEmpId",required=false) String serchEmpId,
                                                        @RequestParam("year") String year,
                                                        @RequestParam("page") int page,
                                                        @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
       // String filepath=request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + "/uploadFile";
        return tmgNotificationBean.getNotificationList(statusFlg,ntfTypeId,serchEmpId,year,page,psDBBean,"");
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
     * @return
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
     * 新規申請/再申請/代理申請/取消/承认
     *
     * @return　エラー null 为正常申请
     */
    @PostMapping("MakeApply")
    @ResponseBody
    public GlobalResponse makeApply(
            @RequestParam("params") String params,
            @RequestParam(value = "uploadFiles" ,required=false) MultipartFile[] uploadFiles,
            @RequestParam(value ="deleteFiles",required=false) String deleteFiles,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        ParamNotificationListDto paramNotificationListDto =JSONUtil.parse(params).toBean(ParamNotificationListDto.class);
        String[] deleteFileslist= null;
        if(!StrUtil.hasEmpty(deleteFiles)){
            deleteFileslist =JSONUtil.parse(deleteFiles).toBean(String[].class);
        }
        if (uploadFiles.length<1){
            uploadFiles=null;
        }
        return tmgNotificationBean.actionMakeApply(psDBBean,paramNotificationListDto,uploadFiles,deleteFileslist);
    }


    /**
     * 一括承認
     *
     * @return　エラー null 为正常申请
     */
    @PostMapping("BulkPermit")
    @ResponseBody
    public GlobalResponse BulkPermit(
            @RequestParam("ntfNoList") String ntfNoList,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        List<String> list =JSONUtil.parse(ntfNoList).toBean(ArrayList.class);
        return tmgNotificationBean.bulkPermit(list,psDBBean);
    }

    /**
     * 差戻し
     *
     * @return　エラー null 为正常申请
     */
    @PostMapping("ApplyReject")
    @ResponseBody
    public GlobalResponse actionUpdateReject(
            @RequestParam("params") String params,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        ParamNotificationListDto paramNotificationListDto =JSONUtil.parse(params).toBean(ParamNotificationListDto.class);

        return tmgNotificationBean.actionUpdateReject(psDBBean,paramNotificationListDto);
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
    @GetMapping("EmployeeList")
    public List<EmployeeListVo> getEmployeeList(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        return tmgNotificationBean.getEmployeeList(psDBBean);
    }

    /**
     * 社員情報
     *
     * @return　エラー
     */
    @GetMapping("EmployInfo")
    public EmployeeDetailVo getEmpInfo(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        return tmgNotificationBean.getEmpInfo(psDBBean);
    }


    /**
     * 休暇休業承認の権限判定で代理申請の使用可否を決める
     *
     * @return　エラー
     */
    @GetMapping("hasAuthority")
    public boolean hasAuthority(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        return tmgNotificationBean.hasAuthority(psDBBean);
    }


    @GetMapping("download")
    public void downloadFileAction(String url,HttpServletResponse response) {

        HttpServletRequest request=ContextUtil.getHttpRequest();
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            File file = new File(url);
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
            IOUtils.copy(fis,response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
