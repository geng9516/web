package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.MastPasswordDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 * パスワードマスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastPasswordService extends IService<MastPasswordDO> {

        /**
         * 根据用户id和明码获取密码设定日期
         * @param userId
         * @param password
         * @return
         */
        Date getUpdateDateByUsernamePassword(String userId,String password);

}
