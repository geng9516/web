package jp.smartcompany.boot.advice;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.enums.ErrorMessage;
import jp.smartcompany.boot.util.SysUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author xiao wenpeng
 */
@RestControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ExceptionAdvice {

    @Value("${spring.profiles.active}")
    private String env;

    @ExceptionHandler(GlobalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GlobalResponse systemException(GlobalException e) {
        printStackTrace(e);
        return GlobalResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public GlobalResponse methodNotSupportedException(HttpServletRequest req,HttpRequestMethodNotSupportedException e) {
        printStackTrace(e);
        String msg = StrUtil.format(ErrorMessage.METHOD_NOT_ALLOWED_ERROR.msg(), req.getMethod());
        return GlobalResponse.error(ErrorMessage.METHOD_NOT_ALLOWED_ERROR.code(), msg);
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalResponse paramEmptyOrTypeException(Exception e) {
        printStackTrace(e);
        return GlobalResponse.error(ErrorMessage.BAD_REQUEST_ERROR);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class
    })
    public GlobalResponse httpMessageNotReadableException(HttpMessageNotReadableException e) {
        printStackTrace(e);
        return GlobalResponse.error(ErrorMessage.BAD_REQUEST_ERROR);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BindException.class,
            ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalResponse validException(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            return convertError(((MethodArgumentNotValidException) e).getBindingResult());
        } else if (e instanceof BindException) {
            return convertError(((BindException) e).getBindingResult());
        } else if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
            String message = "";
            for (ConstraintViolation<?> constraint:constraintViolations) {
                if (StrUtil.isNotBlank(constraint.getMessageTemplate())) {
                    message = constraint.getMessageTemplate();
                    break;
                }
            }
            return message!=null ?GlobalResponse.error(HttpStatus.BAD_REQUEST.value(),message):GlobalResponse.error();
        } else {
            return GlobalResponse.error();
        }
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public GlobalResponse httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return GlobalResponse.error(ErrorMessage.CONTENT_TYPE_SUPPORTED_ERROR);
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalResponse arrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e) {
        printStackTrace(e);
        return GlobalResponse.error(ErrorMessage.OUT_OF_BOUNDARY);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalResponse nullPointerException(NullPointerException e) {
        printStackTrace(e);
        return GlobalResponse.error("NPE異常");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GlobalResponse unknownAccountException(UsernameNotFoundException e) {
        printStackTrace(e);
        return GlobalResponse.error(HttpStatus.UNAUTHORIZED.value(),"システム処理中にエラーが発生しました、システム管理者にお問い合わせください");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object handleException(NoHandlerFoundException e, HttpServletRequest req) {
        printStackTrace(e);
        Object o;
        if (SysUtil.isAjaxRequest(req)) {
            o = GlobalResponse.error(HttpStatus.NOT_FOUND.value(),ErrorMessage.NOT_FOUND_ERROR.msg());
        } else {
            o = new ModelAndView();
            ((ModelAndView)o).setViewName("404");
            ((ModelAndView)o).addObject("error",ErrorMessage.NOT_FOUND_ERROR);
        }
        return o;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleException(Exception e,HttpServletRequest req) {
        printStackTrace(e);
        Object o;
        if (SysUtil.isAjaxRequest(req)) {
            o = GlobalResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),ErrorMessage.SERVER_INTERNAL_ERROR.msg());
        } else {
            o = new ModelAndView();
            ((ModelAndView)o).setViewName("500");
            if (!StrUtil.equals(env, "prod")){
                e.printStackTrace();
                ((ModelAndView)o).addObject("error",e.getMessage());
            } else {
                ((ModelAndView)o).addObject("error",ErrorMessage.SERVER_INTERNAL_ERROR);
            }

        }
        return o;
    }

    private GlobalResponse convertError(Errors error) {
        GlobalResponse r = GlobalResponse.error();
        if (error.hasErrors()) {
            String msg = "";
            if (error.hasGlobalErrors()) {
                msg = error.getGlobalErrors().get(0).getDefaultMessage();
            }
            if (error.hasFieldErrors()) {
                msg = error.getFieldErrors().get(0).getDefaultMessage();
            }
            r.put(Constant.MSG, msg);
        }
        r.put(Constant.CODE, HttpStatus.BAD_REQUEST.value());
        return r;
    }

    private void printStackTrace(Exception e) {
        if (!StrUtil.equals(env, "prod")){
            e.printStackTrace();
        }
    }

}