package jp.smartcompany.job.modules.core.mapper.MastAccount;

import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerUpdateParamDTO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * アカウントマスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastAccountMapper extends BaseMapper<MastAccountDO> {

        List<LoginAccountBO> selectAccountInfo(String username);

        List<UserManagerDTO> selectStartCheckAccount(
                @Param("customerId") String customerId,
                @Param("userId") String userId,
                @Param("account") String account);

        List<UserManagerUpdateParamDTO> selectPasswordForUpdateInfo(
                @Param("pwdDate") String pwdDate,
                @Param("password") String sCryptPassword,
                @Param("loginUserId") String userId,
                @Param("originalPassword") String sUpdatePassword,
                @Param("custId") String customerId,
                @Param("userList") List<String> userIds,
                @Param("companyList") List<String> companyList);

        List<UserManagerDTO> selectPasswordList(
                @Param("custId") String custId,
                @Param("language") String language,
                @Param("userList") List<String> userIds,
                @Param("searchType") Integer searchType,
                @Param("companyList") List<String> companyList
        );
}
