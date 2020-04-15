package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.mapper.OperationAuditMapper;
import jp.smartcompany.job.modules.core.pojo.entity.OperationAuditDO;
import jp.smartcompany.job.modules.core.service.OperationAuditService;
import jp.smartcompany.job.util.SysUtil;
import org.springframework.stereotype.Repository;

/**
 * @author Xiao Wenpeng
 */
@Repository(CoreBean.Service.OPERATION_AUDIT)
public class OperationAuditServiceImpl extends ServiceImpl<OperationAuditMapper, OperationAuditDO> implements OperationAuditService {

    @Override
    public IPage<OperationAuditDO> listByPage(String keyword, String startTime, String endTime, IPage<OperationAuditDO> p) {
        String keywordLike = "%"+keyword+"%";
        QueryWrapper<OperationAuditDO> queryWrapper = SysUtil.query();
        queryWrapper.apply(StrUtil.isNotBlank(keyword), "lower(username) like {0} or lower(operation) like {1} or lower(url) like {2} or lower(ip) like {3} or lower(method) like {4} or lower(params) like {5}",keywordLike,keywordLike,keywordLike,keywordLike,keywordLike,keywordLike);
        if (StrUtil.isNotBlank(startTime)&&StrUtil.isNotBlank(endTime)) {
            queryWrapper.or().between("create_time", startTime, endTime);
        }
        return page(
                p,
                queryWrapper
        );
    }

}
