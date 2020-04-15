package jp.smartcompany.job.modules.base.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.modules.base.BaseBean;
import jp.smartcompany.job.modules.base.pojo.bo.PluggableBO;
import jp.smartcompany.job.modules.tmg.vacation.VacationBean;
import jp.smartcompany.job.modules.tmg.vacation.service.VacationService;
import jp.smartcompany.job.modules.tmg.work.WorkBean;
import jp.smartcompany.job.modules.tmg.work.service.WorkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author XiaoWenpeng
 */
@Service(BaseBean.Service.PLUGGABLE)
public class PluggableService {

    public String execute() {
        List<String> classNames = CollUtil.newArrayList();
        classNames.add(WorkBean.Service.work);
        classNames.add(VacationBean.Service.vacation);
        // 初始化参数
        PluggableBO pluggableBO = new PluggableBO();
        pluggableBO.setParam1("p1");
        pluggableBO.setParam2("p2");
        pluggableBO.setParam3(1);
        String errorMessage = null;
        for (String className : classNames) {
            errorMessage = executeTask(className,pluggableBO);
        }
        return errorMessage;
    }

    @Transactional(rollbackFor = GlobalException.class)
    public String executeTask(String className,PluggableBO pluggableBO) {
        String errorMessage;
        // 执行勤务service里的方法
        if (StrUtil.equals(WorkBean.Service.work,className)) {
            WorkService workService = SpringUtil.getBean(className);
            errorMessage = workService.execute(pluggableBO);
        // 执行休假service里的方法
        } else if (StrUtil.equals(VacationBean.Service.vacation,className)) {
            VacationService vacationService = SpringUtil.getBean(className);
            errorMessage = vacationService.execute(pluggableBO);
        // 执行没有报错的情况没有错误信息
        } else {
           errorMessage = null;
        }
        return errorMessage;
    }

}
