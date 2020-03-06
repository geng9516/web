package jp.smartcompany.job.controller;

import jp.smartcompany.job.common.GlobalResponse;
import jp.smartcompany.job.enums.ErrorMessage;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 400错误码控制器
 * @author xiao wenpeng
 */
@Controller
public class SysErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("error")
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GlobalResponse error() {
        return GlobalResponse.error(
                ErrorMessage.NOT_FOUND_ERROR
        );
    }
}
