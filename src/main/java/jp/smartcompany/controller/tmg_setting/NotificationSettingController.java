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

    @GetMapping("groupManager")
    public List<GroupVo> getNTFGroup(@RequestParam(value="sysdate",required = false) String sysdate) {
        return notificationSettingBean.getNTFGroup(sysdate);
    }


    @GetMapping("typeGroupManager")
    public List<TypeGroupVo> getNTFTypeGroup(@RequestParam(value="sysdate",required = false) String sysdate) {
        return notificationSettingBean.getNTFTypeGroup(sysdate);
    }
}
