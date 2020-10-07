package jp.smartcompany.controller.admin;

import jp.smartcompany.admin.usermanager.dto.ChangePasswordDTO;
import jp.smartcompany.admin.usermanager.form.UserManagerEditEndForm;
import jp.smartcompany.admin.usermanager.form.ShowLimitDateForm;
import jp.smartcompany.admin.usermanager.form.UserManagerEditPersonalForm;
import jp.smartcompany.admin.usermanager.form.UserManagerEditStartForm;
import jp.smartcompany.admin.usermanager.logic.*;
import jp.smartcompany.boot.util.PageUtil;
import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.List;

/**
 * ユーザ管理
 * @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/usermanager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerController {

    private final UserManagerMainLogic userManagerMainLogic;
    private final UserManagerEditPersonalLogic userManagerEditPersonalLogic;
    private final UserManagerEditStartLogic userManagerEditStartLogic;
    private final UserManagerEditEndLogic userManagerEditEndLogic;
    private final UserManagerEditPasswordLogic userManagerEditPasswordLogic;


    /* ============ 旧代码： UserManagerMainAction 开始 ===========*/
    @GetMapping("search")
    public PageUtil search(@RequestParam Map<String,Object> params) {
        return userManagerMainLogic.search(params);
    }

    // ロックアウト解除
    @PostMapping("unlock")
    public String unLock(@RequestBody @Valid @NotEmpty List<String> userIds) {
        userManagerMainLogic.unLock(userIds);
        return "パスワード変更成功";
    }
    /* ============ 旧代码： UserManagerMainAction 结束 ===========*/

    /* ============ 旧代码： UserManagerEditPolicyAction 开始 ===========*/
    @GetMapping("policy")
    public List<ConfSyscontrolDO> passwordPolicy() {
        return userManagerMainLogic.passwordPolicy();
    }
    @PostMapping("policy")
    public String updatePolicy(@RequestBody @Valid @NotEmpty List<ConfSyscontrolDO> controlList) {
        userManagerMainLogic.updatePolicy(controlList);
        return "パスワード変更成功";
    }
    /* ============ 旧代码： UserManagerEditPolicyAction 结束 ===========*/


    /* ============ 旧代码： UserManagerEditEndAction 开始 ===========*/
    @PostMapping("show/endDate")
    public Map<String,Object> showChangeEndDate(@Valid @RequestBody ShowLimitDateForm form) {
        return userManagerEditEndLogic.showChangeEndDate(form);
    }

    @PostMapping("change/endDate")
    public String changeEndDate(@RequestBody @Valid UserManagerEditEndForm form) {
       userManagerEditEndLogic.changeEndDate(form);
       return "利用終了日変更成功";
    }
    /* ============ 旧代码： UserManagerEditEndAction 结束 ===========*/


    /* ============ 旧代码： UserManagerEditStartAction 开始 ===========*/
    @PostMapping("show/startDate")
    public Map<String,Object> showChangeStartDate(@Valid @RequestBody ShowLimitDateForm form) {
        return userManagerEditStartLogic.showChangeStartDate(form);
    }

    @PostMapping("change/startDate")
    public Map<String,String> changeStartDate(@Valid @RequestBody UserManagerEditStartForm form) {
        return userManagerEditStartLogic.changeStartDate(form);
    }
    /* ============ 旧代码： UserManagerEditStartAction 结束 ===========*/

    /* ============ UserManagerEditPersonalAction 开始 ============= */
    @GetMapping("personal/{id}")
    public Map<String,Object> showPersonal(@PathVariable("id") String userId) {
        return userManagerEditPersonalLogic.display(userId);
    }

    @PostMapping("personal")
    public Map<String,String> updatePersonal(@RequestBody UserManagerEditPersonalForm form) {
        return userManagerEditPersonalLogic.updatePersonal(form);
    }
    /* ============ UserManagerEditPersonalAction 结束 ============= */

    /* ============ UserManagerEditPasswordAction 开始 ============= */
    @PostMapping("show/password")
    public Map<String,Object> showChangePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return userManagerEditPasswordLogic.showChangePassword(changePasswordDTO);
    }

    /* ============ UserManagerEditPasswordAction 结束 ============= */
}
