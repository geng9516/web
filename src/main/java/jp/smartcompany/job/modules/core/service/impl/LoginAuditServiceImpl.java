package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.mapper.LoginAudit.LoginAuditMapper;
import jp.smartcompany.job.modules.core.pojo.entity.LoginAuditDO;
import jp.smartcompany.job.modules.core.service.LoginAuditService;
import jp.smartcompany.boot.util.SysUtil;
import org.springframework.stereotype.Repository;

/**
 * @author Xiao Wenpeng
 */
@Repository(CoreBean.Service.LOGIN_AUDIT)
public class LoginAuditServiceImpl extends ServiceImpl<LoginAuditMapper, LoginAuditDO> implements LoginAuditService {

    @Override
    public IPage<LoginAuditDO> listByPage(String keyword, String startTime,String endTime,IPage<LoginAuditDO> p) {
        String keywordLike = "%"+keyword+"%";
        QueryWrapper<LoginAuditDO> queryWrapper = SysUtil.query();
        queryWrapper.apply(StrUtil.isNotBlank(keyword), "lower(username) like {0} or lower(operation) like {1} or lower(ip) like {2} or lower(user_agent) like {3}",keywordLike,keywordLike,keywordLike,keywordLike);
        if (StrUtil.isNotBlank(startTime)&&StrUtil.isNotBlank(endTime)) {
            queryWrapper.or().between("create_time", startTime, endTime);
        }
        return page(
                p,
                queryWrapper
        );
    }

}
