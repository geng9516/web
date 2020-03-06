package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.job.common.Query;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.manager.AccessAuditManager;
import jp.smartcompany.job.modules.core.manager.ErrorAuditManager;
import jp.smartcompany.job.modules.core.manager.LoginAuditManager;
import jp.smartcompany.job.modules.core.manager.OperationAuditManager;
import jp.smartcompany.job.modules.core.pojo.entity.AccessAuditDO;
import jp.smartcompany.job.modules.core.pojo.entity.ErrorAuditDO;
import jp.smartcompany.job.modules.core.pojo.entity.LoginAuditDO;
import jp.smartcompany.job.modules.core.pojo.entity.OperationAuditDO;
import jp.smartcompany.job.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Service(CoreBean.Service.LOG)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogService {

    private final AccessAuditManager accessAuditManager;
    private final LoginAuditManager loginAuditManager;
    private final ErrorAuditManager errorAuditManager;
    private final OperationAuditManager operationAuditManager;

    /**
     * 登录日志查询
     * @param params 查询参数
     * @return PageUtil 分页工具类
     */
    public PageUtil listLoginLog(Map<String, Object> params) {
        String keyword = (String)params.get("keyword");
        String startTime =(String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        IPage<LoginAuditDO> p = new Query<LoginAuditDO>().getPage(params);
        IPage<LoginAuditDO> page = loginAuditManager.listByPage(keyword,startTime,endTime,p);
        return new PageUtil(page);
    }

    /**
     * 访问日志查询
     * @param params 查询参数
     * @return PageUtil 分页工具类
     */
    public PageUtil listAccessLog(Map<String, Object> params) {
        String keyword = (String)params.get("keyword");
        String startTime =(String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        IPage<AccessAuditDO> p = new Query<AccessAuditDO>().getPage(params);
        IPage<AccessAuditDO> page = accessAuditManager.listByPage(keyword,startTime,endTime,p);
        return new PageUtil(page);
    }

    /**
     * 访问错误日志
     * @param params 查询参数
     * @return PageUtil 分页工具类
     */
    public PageUtil listErrorLog(Map<String, Object> params) {
        String keyword = (String)params.get("keyword");
        String startTime =(String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        IPage<ErrorAuditDO> p = new Query<ErrorAuditDO>().getPage(params);
        IPage<ErrorAuditDO> page = errorAuditManager.listByPage(keyword,startTime,endTime,p);
        return new PageUtil(page);
    }

    /**
     * 访问操作日志
     * @param params 查询参数
     * @return PageUtil 分页工具类
     */
    public PageUtil listOperationLog(Map<String,Object> params) {
        String keyword = (String)params.get("keyword");
        String startTime =(String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        IPage<OperationAuditDO> p = new Query<OperationAuditDO>().getPage(params);
        IPage<OperationAuditDO> page = operationAuditManager.listByPage(keyword,startTime,endTime,p);
        return new PageUtil(page);
    }

}
