package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.mapper.MastAccountMapper;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * アカウントマスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastAccountServiceImpl extends ServiceImpl<MastAccountMapper, MastAccountDO> implements IMastAccountService {

        @Override
        public MastAccountDO getByUsername(String username) {
                QueryWrapper<MastAccountDO> qw = SysUtil.query();
                qw.eq("ma_caccount", username)
                  .lt("ma_dstart", DateUtil.date())
                  .gt("ma_dend",DateUtil.date());
                List<MastAccountDO> accountList = list(qw);
                if (CollUtil.isEmpty(accountList)){
                   return null;
                }
                return accountList.get(0);
        }

        @Override
        public LoginAccountBO getAccountInfo(String username) {
              List<LoginAccountBO> loginAccountList = baseMapper.selectAccountInfo(username);
              if (CollUtil.isEmpty(loginAccountList)){
                      return null;
              }
              return loginAccountList.get(0);
        }

}
