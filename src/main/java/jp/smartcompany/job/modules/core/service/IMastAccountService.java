package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * アカウントマスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastAccountService extends IService<MastAccountDO> {

        /**
         * 根据用户名获取账户信息
         * @param username
         * @return
         */
        MastAccountDO getByUsername(String username);

}
