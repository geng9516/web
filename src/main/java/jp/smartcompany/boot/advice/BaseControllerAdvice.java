package jp.smartcompany.boot.advice;

import cn.hutool.cache.impl.TimedCache;
import jp.smartcompany.boot.common.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

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
        model.addAttribute(Constant.TOP_NAVS, timedCache.get(Constant.TOP_NAVS,false));
    }

}
