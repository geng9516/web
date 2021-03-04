package jp.smartcompany.controller.tmg_setting;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.PageUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.vo.CategoryGenericDetailVO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.logic.MailManagerLogic;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto.MailConfigDTO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto.TestSendMail;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto.UpdateMailDTO;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.NotificationSettingBean;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo.GroupVo;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo.NtfDispVo;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo.TypeGroupVo;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("sys/notificationSetting")
public class NotificationSettingController {

    private final NotificationSettingBean notificationSettingBean;

    //Group管理画面data获取
    @GetMapping("groupManager")
    public List<GroupVo> getNTFGroup(@RequestParam(value="sysdate",required = false) String sysdate) {
        return notificationSettingBean.getNTFGroup(sysdate);
    }

    //typeGroup管理画面data获取
    @GetMapping("typeGroupManager")
    public List<TypeGroupVo> getNTFTypeGroup(@RequestParam(value="sysdate",required = false) String sysdate) {
        return notificationSettingBean.getNTFTypeGroup(sysdate);
    }


    //主页面data获取
    @GetMapping("ntfDisp")
    public List<NtfDispVo> getNtfDisp(@RequestParam("ntfGroup")String ntfGroup,@RequestParam(value="sysdate",required = false) String sysdate) {
        return notificationSettingBean.getNtfDisp(ntfGroup,sysdate);
    }

    //新规 编辑画面 data获取
    @GetMapping("editDisp")
    public Map<String,Object> getEditDisp(@RequestParam(value="sysdate",required = false) String sysdate) {
        return notificationSettingBean.getEditDisp(sysdate);
    }

    //新规 修改申请名 checkNtfName
    @GetMapping("checkNtfName")
    public int checkNtfName(@RequestParam("ntfName") String ntfName,
                            @RequestParam(value="sysdate",required = false) String sysdate) {
        return notificationSettingBean.checkName(ntfName,sysdate,"TMG_NTFTYPE");
    }

    //新规 修改申请名 checkGroupName
    @GetMapping("checkGroupName")
    public int checkGroupName(@RequestParam("ntfName") String groupName,
                            @RequestParam(value="sysdate",required = false) String sysdate) {
        return notificationSettingBean.checkName(groupName,sysdate,"TMG_NTFGROUP");
    }

    //新规 修改申请名 checkTypeGroupName
    @GetMapping("checkTypeGroupName")
    public int checkTypeGroupName(@RequestParam("ntfName") String typeGroupName,
                            @RequestParam(value="sysdate",required = false) String sysdate) {
        return notificationSettingBean.checkName(typeGroupName,sysdate,"TMG_NTFTYPEGROUP");
    }



}
