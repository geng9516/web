package jp.smartcompany.job.controller;

import cn.hutool.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Xiao Wenpeng
 * 异常控制器
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysErrorController implements ErrorController {

    private final HttpServletResponse response;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("error")
    public String notFound() {
        if (response.getStatus() == HttpStatus.HTTP_NOT_FOUND) {
            return "404";
        } else {
            return "500";
        }
    }
}

