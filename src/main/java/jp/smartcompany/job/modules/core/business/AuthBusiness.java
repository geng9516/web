package jp.smartcompany.job.modules.core.business;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.service.IMastPasswordService;
import jp.smartcompany.job.util.SysDateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Xiao Wenpeng
 */
@Service(CoreBean.Business.AUTH_BUSINESS)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthBusiness {

    private final IMastPasswordService iMastPasswordService;

    public boolean checkPassword(String userId,String password) {
        Date passwordSetDate = iMastPasswordService.getUpdateDateByUsernamePassword(userId,password);
        if (passwordSetDate == null) {

        // 判断密码是否已经过了使用期限
        } else {
            if (SysDateUtil.isLess(passwordSetDate, DateUtil.date())) {
               // 抛出密码过期异常
            }
        }
        return true;
    }

}
