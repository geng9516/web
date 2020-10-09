package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import jp.smartcompany.admin.usermanager.dto.ChangePasswordDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.form.UserManagerEditPasswordForm;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditCommonLogic;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditPasswordLogic;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.List;

@Service("userManagerEditPasswordLogic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerEditPasswordLogicImpl implements UserManagerEditPasswordLogic {

    private final ScCacheUtil cacheUtil;
    private final UserManagerEditCommonLogic userManagerEditCommonLogic;
    private final PsSearchCompanyUtil searchCompanyUtil;
    private final IMastAccountService accountService;

    private int passwordMinLen;
    private int passwordMaxLen;
    private int changeLimitCount;

    @Override
    public Map<String,Object> showChangePassword(ChangePasswordDTO changePasswordDTO) {
        //システムプロパティ設定
        passwordMinLen = Integer.parseInt(cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_PW_MAX_LEN));
        passwordMaxLen = Integer.parseInt(cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_PW_MAX_LEN));
        changeLimitCount = Integer.parseInt(cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_CHANGE_PW_LIMITATION_COUNT));
        String custId = "01";
        String language = "ja";
        Date now = DateUtil.date();
        List<String> companyList = searchCompanyUtil.getCompList(now);
        List<UserManagerDTO>  userList = accountService.selectPasswordList(custId,language,changePasswordDTO.getUserIds(),changePasswordDTO.getSearchType(),companyList);
        for (UserManagerDTO userManagerDTO : userList) {
            if (DateUtil.isSameDay(userManagerDTO.getMaDend(), SysUtil.getMaxDateObject())) {
                userManagerDTO.setMaDend(null);
            }
            String status = userManagerEditCommonLogic.getStatus(userManagerDTO);
            userManagerDTO.setStatus(status);
        }
        return MapUtil.<String,Object>builder()
                .put("passwordMaxLen",passwordMaxLen)
                .put("passwordMinLen",passwordMinLen)
                .put("changeLimitCount",changeLimitCount)
                .put("userList",userList)
        .build();
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public Map<String,Object> changePassword(UserManagerEditPasswordForm form) {
        String customerId = "01";
        passwordMaxLen = Integer.parseInt(cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_PW_MAX_LEN));
        String userId = SecurityUtil.getUserId();
        String language = "ja";
        return userManagerEditCommonLogic.updatePassword(
          customerId,
          userId,
          language,
          form.getUserIds(),
          form.getPasswordType(),
          form.getPassword(),
          form.getForceChangePassword()
        );
    }

}
