package jp.smartcompany.boot.advice;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author xiao wenpeng
 */
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BaseControllerAdvice {

    private final TimedCache<String,Object> timedCache;

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        HttpSession session =  ContextUtil.getHttpRequest().getSession();
        // 如果tmgreferlist里不存在系统时间，则设置一个默认时间
        Date sysDate = (Date)session.getAttribute(TmgReferList.SESSION_KEY_SYSDATE);
        if (sysDate==null){
            session.setAttribute(TmgReferList.SESSION_KEY_SYSDATE, DateUtil.date());
        }
        String navKey = Constant.TOP_NAVS+"_"+ session.getId();
        model.addAttribute(Constant.TOP_NAVS, timedCache.get(navKey,false));
    }

}
