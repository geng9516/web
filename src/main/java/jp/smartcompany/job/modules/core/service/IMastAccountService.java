package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerUpdateParamDTO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

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

        LoginAccountBO getAccountInfo(String username);

        List<UserManagerDTO> selectStartCheckAccount(
                String customerId,
                String userid,
                String account);

        List<UserManagerDTO> selectPersonalCheckAccountOld(String customerId, String account);

        UserManagerDTO selectPersonalCheckUserid(String userId);

        List<UserManagerUpdateParamDTO> selectPasswordForUpdateInfo(
                String pwdDate,
                String sCryptPassword,
                String userId,
                String sUpdatePassword,
                String customerId,
                List<String> userIds,
                List<String> companyList);

        List<UserManagerDTO> selectPasswordList(
                String custId,
                String language,
                List<String> userIds,
                Integer searchType,
                List<String> companyList
        );
}
