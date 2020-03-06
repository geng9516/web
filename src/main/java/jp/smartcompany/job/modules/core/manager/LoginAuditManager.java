package jp.smartcompany.job.modules.core.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.LoginAuditDO;

/**
 * @author Xiao Wenpeng
 */
public interface LoginAuditManager extends IService<LoginAuditDO> {

    /**
     * 根据参数查询登录日志
     * @param keyword 查询关键字
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param p 分页参数
     * @return IPage<LoginAuditDO>
     */
    IPage<LoginAuditDO> listByPage(String keyword, String startTime,String endTime,IPage<LoginAuditDO> p);

}
