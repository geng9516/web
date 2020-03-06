package jp.smartcompany.job.advice;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.common.Constant;
import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.common.GlobalResponse;
import jp.smartcompany.job.enums.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(UnknownAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GlobalResponse unknownAccountException() {
      return GlobalResponse.error(ErrorMessage.USER_NOT_EXIST);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GlobalResponse incorrectCredentialsException() {
        return GlobalResponse.error(ErrorMessage.PASSWORD_INVALID);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GlobalResponse authorizationException() {
        return GlobalResponse.error(ErrorMessage.AUTH_NOT_ENOUGH);
    }

    @ExceptionHandler(UnknownSessionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GlobalResponse unknownSessionException() {
        return GlobalResponse.error(ErrorMessage.SESSION_EXPIRE);
    }

    @ExceptionHandler(GlobalException.class)
    public GlobalResponse systemException(GlobalException e) {
        return GlobalResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public GlobalResponse methodNotSupportedException(HttpServletRequest req) {
        String msg = StrUtil.format(ErrorMessage.METHOD_NOT_ALLOWED_ERROR.msg(), req.getMethod());
        return GlobalResponse.error(ErrorMessage.METHOD_NOT_ALLOWED_ERROR.code(), msg);
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalResponse paramEmptyOrTypeException() {
        return GlobalResponse.error(ErrorMessage.BAD_REQUEST_ERROR);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class
    })
    public GlobalResponse httpMessageNotReadableException() {
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

    @ExceptionHandler({
            HttpMediaTypeNotSupportedException.class
    })
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public GlobalResponse httpMediaTypeNotSupportedException() {
        return GlobalResponse.error(ErrorMessage.CONTENT_TYPE_SUPPORTED_ERROR);
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalResponse arrayIndexOutOfBoundsException() {
        return GlobalResponse.error(ErrorMessage.OUT_OF_BOUNDARY);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalResponse nullPointerException() {
        return GlobalResponse.error(ErrorMessage.NPC_EXCEPTION);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GlobalResponse handleException(Exception e) {
        boolean isTestEnv = StrUtil.equals(Constant.Env.DEV, env) || StrUtil.equals(Constant.Env.TEST, env);
        if (isTestEnv) {
            String message = e.getMessage();
            if (StrUtil.isNotBlank(message)) {
                return GlobalResponse.error(message);
            } else {
                return GlobalResponse.error(ErrorMessage.SERVER_INTERNAL_ERROR);
            }
        } else {
            return GlobalResponse.error(ErrorMessage.SERVER_INTERNAL_ERROR);
        }
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

}