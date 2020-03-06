package jp.smartcompany.job.modules.core.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.OperationAuditDO;

/**
 * @author Xiao Wenpeng
 */
public interface OperationAuditManager extends IService<OperationAuditDO> {
    /**
     * 根据参数查询操作日志
     * @param keyword 查询关键字
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param p 分页参数
     * @return IPage<AccessAuditDO>
     */
    IPage<OperationAuditDO> listByPage(String keyword, String startTime, String endTime, IPage<OperationAuditDO> p);


}
