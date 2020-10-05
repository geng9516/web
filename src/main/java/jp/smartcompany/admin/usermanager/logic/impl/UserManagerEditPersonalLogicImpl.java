package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditCommonLogic;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditPersonalLogic;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("userManagerEditPersonalLogic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerEditPersonalLogicImpl implements UserManagerEditPersonalLogic {

    private final IMastAccountService accountService;
    private final IMastEmployeesService employeesService;
    private final ScCacheUtil cacheUtil;
    private String passwordMinLen;
    private String passwordMaxLen;
    private String changeLimitCount;


    @Override
    public Map<String,Object> display(String userId) {
         MastAccountDO account = accountService.getByUsername(userId);
         // 表示用：MAXDATEを空にする
         if (account.getMaDend() != null
                && DateUtil.isSameDay(SysUtil.getMaxDateObject(),account.getMaDend())) {
            account.setMaDend(null);
         }
         Date baseDate = DateUtil.date();
         passwordMinLen = cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_PW_MIN_LEN);
         passwordMaxLen = cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_PW_MAX_LEN);
         changeLimitCount = cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_CHANGE_PW_LIMITATION_COUNT);
         int count= employeesService.count(new QueryWrapper<MastEmployeesDO>()
                 .eq("ME_CCUSTOMERID_CK","01")
                 .eq("ME_CUSERID",userId)
                 .le("ME_DSTARTDATE", baseDate)
                 .ge("ME_DSTARTDATE",baseDate));
         boolean isValid = count > 0;
         //画面表示情報取得
         return MapUtil.<String,Object>builder().put("userInfo",account)
                 .put("baseDate",SysUtil.transDateToString(DateUtil.date()))
                 .put("passwordMaxLen",Integer.parseInt(passwordMaxLen))
                 .put("passwordMinLen",Integer.parseInt(passwordMinLen))
                 .put("changeLimitCount",Integer.parseInt(changeLimitCount))
                 .put("isValid",isValid)
                 .build();
    }


}
