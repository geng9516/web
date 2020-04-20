package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.mapper.MastAccountMapper;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.util.SysUtil;
import org.springframework.stereotype.Repository;

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
                return getOne(qw);
        }

}
