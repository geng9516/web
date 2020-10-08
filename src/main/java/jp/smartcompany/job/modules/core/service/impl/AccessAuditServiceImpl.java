package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.mapper.AccessAudit.AccessAuditMapper;
import jp.smartcompany.job.modules.core.pojo.entity.AccessAuditDO;
import jp.smartcompany.job.modules.core.service.AccessAuditService;
import jp.smartcompany.boot.util.SysUtil;
import org.springframework.stereotype.Repository;

/**
 * @author Xiao Wenpeng
 */
@Repository(CoreBean.Service.ACCESS_AUDIT)
public class AccessAuditServiceImpl extends ServiceImpl<AccessAuditMapper, AccessAuditDO> implements AccessAuditService {

    @Override
    public IPage<AccessAuditDO> listByPage(String keyword, String startTime, String endTime, IPage<AccessAuditDO> p) {
        String keywordLike = "%"+keyword+"%";
        QueryWrapper<AccessAuditDO> queryWrapper = SysUtil.query();
        queryWrapper.apply(StrUtil.isNotBlank(keyword), "lower(ip) like {0} or lower(url) like {1} or lower(method) like {2}",keywordLike,keywordLike,keywordLike);
        if (StrUtil.isNotBlank(startTime)&&StrUtil.isNotBlank(endTime)) {
            queryWrapper.or().between("create_time", startTime, endTime);
        }
        return page(
                p,
                queryWrapper
        );
    }

}
