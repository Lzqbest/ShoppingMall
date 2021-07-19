package com.common.config;

import com.common.BaseException;
import com.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

/**
 * @Author Best-Traveler
 * @Date 2020/11/05
 * @Description
 **/
@Slf4j
@RestControllerAdvice
public class WebExceptionHandler {

    /**
     * 捕获自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public R handler(BaseException e) {
        if (!Objects.isNull(e)) {
            log.error("自定义异常！[{}]", e.getMessage());
            return R.fail(e.getStatus(), e.getMessage());
        }
        return R.fail();
    }

    /**
     * 捕获方法参数异常
     * TODO 仅对Json传参方式 有效
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handler(MethodArgumentNotValidException e) {
        return handlerNotValidException(e.getBindingResult());
    }

    /**
     * 获方法参数异常
     * 表单参数校验
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public R handle(BindException e){
        return handlerNotValidException(e.getBindingResult());
    }

    @ExceptionHandler(RuntimeException.class)
    public R handler(RuntimeException e) {
        if (!Objects.isNull(e)) {
            log.error("运行时异常！[{}]", e.getMessage());
            return R.fail(e.getMessage());
        }
        return R.fail();
    }
    private R handlerNotValidException(BindingResult result){
        List<FieldError> list = result.getFieldErrors();
        if (!CollectionUtils.isEmpty(list)) {
            String msg = list.get(0).getDefaultMessage();
            log.error("用户参数异常！[{}]", msg);
            return R.fail(msg);
        }
        return R.fail("传入参数异常！");
    }

}
