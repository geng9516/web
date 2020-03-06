package jp.smartcompany.job.modules.core.manager.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.dao.ErrorAuditDao;
import jp.smartcompany.job.modules.core.manager.ErrorAuditManager;
import jp.smartcompany.job.modules.core.pojo.entity.ErrorAuditDO;
import jp.smartcompany.job.util.SysUtil;
import org.springframework.stereotype.Repository;

/**
 * @author Xiao Wenpeng
 */
@Repository(CoreBean.Manager.ERROR_AUDIT)
public class ErrorAuditManagerImpl extends ServiceImpl<ErrorAuditDao, ErrorAuditDO> implements ErrorAuditManager {

    @Override
    public IPage<ErrorAuditDO> listByPage(String keyword, String startTime, String endTime, IPage<ErrorAuditDO> p) {
        String keywordLike = "%"+keyword+"%";
        QueryWrapper<ErrorAuditDO> queryWrapper = SysUtil.query();
        queryWrapper.apply(StrUtil.isNotBlank(keyword), "lower(username) like {0} or lower(called_method) like {1} or lower(url) like {2} or lower(ip) like {3} or lower(user_agent) like {4} or lower(message) like {5}",keywordLike,keywordLike,keywordLike,keywordLike,keywordLike,keywordLike);
        if (StrUtil.isNotBlank(startTime)&&StrUtil.isNotBlank(endTime)) {
            queryWrapper.or().between("create_time", startTime, endTime);
        }
        return page(
                p,
                queryWrapper
        );
    }

}
