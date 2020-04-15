package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.AccessAuditDO;

/**
 * @author Xiao Wenpeng
 */
public interface AccessAuditService extends IService<AccessAuditDO> {
    /**
     * 根据参数查询访问日志
     * @param keyword 查询关键字
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param p 分页参数
     * @return IPage<AccessAuditDO>
     */
    IPage<AccessAuditDO> listByPage(String keyword, String startTime, String endTime, IPage<AccessAuditDO> p);
}
