package jp.smartcompany.job.modules.base.service;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.job.modules.base.BaseBean;
import jp.smartcompany.job.modules.base.pojo.bo.PluggableBO;
import jp.smartcompany.job.modules.logic.vacation.VacationBean;
import jp.smartcompany.job.modules.logic.vacation.service.VacationService;
import jp.smartcompany.job.modules.logic.work.WorkBean;
import jp.smartcompany.job.modules.logic.work.service.WorkService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * @author XiaoWenpeng
 */
@Service(BaseBean.Service.PLUGGABLE)
public class PluggableService {

    public void execute(String className,String methodName,PluggableBO pluggableBO) {
        // 执行勤务service里的方法
        if (StrUtil.equals(WorkBean.Service.work,className)) {
            WorkService workService = SpringUtil.getBean(className);
            if (pluggableBO!=null) {
                ReflectUtil.invoke(workService, methodName, pluggableBO.getParam1(), pluggableBO.getParam2(), pluggableBO.getParam3());
            } else {
                ReflectUtil.invoke(workService, methodName);
            }
        // 执行休假service里的方法
        } else if (StrUtil.equals(VacationBean.Service.vacation,className)) {
            VacationService vacationService = SpringUtil.getBean(className);
            if (pluggableBO!=null) {
                ReflectUtil.invoke(vacationService, methodName, pluggableBO.getParam1(), pluggableBO.getParam2(), pluggableBO.getParam3());
            } else {
                ReflectUtil.invoke(vacationService, methodName);
            }
        }
    }

}
