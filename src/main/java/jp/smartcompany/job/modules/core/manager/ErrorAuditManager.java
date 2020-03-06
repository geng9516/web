package jp.smartcompany.job.modules.core.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.ErrorAuditDO;

/**
 * @author Xiao Wenpeng
 */
public interface ErrorAuditManager extends IService<ErrorAuditDO> {

    /**
     *　根据参数查询错误日志
     * @param keyword 关键字
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param p 查询参数
     * @return IPage<ErrorAuditDO>
     */
    IPage<ErrorAuditDO> listByPage(String keyword, String startTime,String endTime,IPage<ErrorAuditDO> p);

}
