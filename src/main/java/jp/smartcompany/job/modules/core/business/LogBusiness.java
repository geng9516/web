package jp.smartcompany.job.modules.core.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.job.common.Query;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.service.AccessAuditService;
import jp.smartcompany.job.modules.core.service.ErrorAuditService;
import jp.smartcompany.job.modules.core.service.LoginAuditService;
import jp.smartcompany.job.modules.core.service.OperationAuditService;
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
@Service(CoreBean.Business.LOG)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogBusiness {

    private final AccessAuditService accessAuditService;
    private final LoginAuditService loginAuditService;
    private final ErrorAuditService errorAuditService;
    private final OperationAuditService operationAuditService;

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
        IPage<LoginAuditDO> page = loginAuditService.listByPage(keyword,startTime,endTime,p);
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
        IPage<AccessAuditDO> page = accessAuditService.listByPage(keyword,startTime,endTime,p);
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
        IPage<ErrorAuditDO> page = errorAuditService.listByPage(keyword,startTime,endTime,p);
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
        IPage<OperationAuditDO> page = operationAuditService.listByPage(keyword,startTime,endTime,p);
        return new PageUtil(page);
    }

}

