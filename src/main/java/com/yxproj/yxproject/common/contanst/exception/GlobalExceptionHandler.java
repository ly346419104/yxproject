package com.yxproj.yxproject.common.contanst.exception;

import com.yxproj.yxproject.common.contanst.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(Throwable.class)
    public JsonResult handlerException(Throwable throwable) {
        logger.error("全局异常捕获", throwable);

        JsonResult response = new JsonResult();
        response.setMsg("系统异常");
        response.setStatusCode(ExceptionCode.SYSTEM_ERROR.getCode());

        return getError(throwable, response);
    }

    private JsonResult getError(Throwable throwable, JsonResult response) {

        if (throwable instanceof ServiceException) {
            response.setStatusCode(((ServiceException) throwable).getCode());
            response.setMsg(throwable.getMessage());
        }

        return response;
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonResult handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        logger.error("could_not_read_json...", e);
        return new JsonResult(HttpStatus.BAD_REQUEST, "could_not_read_json ", "");

    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public JsonResult handleValidationException(MethodArgumentNotValidException e) {
        logger.error("parameter_validation_exception...", e);
        String message = "";
        BindingResult result = e.getBindingResult();
        //组装校验错误信息
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            StringBuffer errorMsgBuffer = new StringBuffer();
            for (ObjectError error : list) {
                if (error instanceof FieldError) {
                    FieldError errorMessage = (FieldError) error;
                    errorMsgBuffer = errorMsgBuffer.append(errorMessage.getField() + " : " + errorMessage.getDefaultMessage() + ",");
                }
            }
            //返回信息格式处理
            message = errorMsgBuffer.toString().substring(0, errorMsgBuffer.length() - 1);
        }
        return new JsonResult(HttpStatus.BAD_REQUEST, message, "");
    }

    /**
     * 405 - Method Not Allowed銆侶ttpRequestMethodNotSupportedException
     * 鏄疭ervletException鐨勫瓙绫?,闇?瑕丼ervlet API鏀寔
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JsonResult handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        logger.error("request_method_not_supported...", e);

        return new JsonResult(HttpStatus.METHOD_NOT_ALLOWED, "request_method_not_supported ", "");
    }

    /**
     * 415 - Unsupported Media Type銆侶ttpMediaTypeNotSupportedException
     * 鏄疭ervletException鐨勫瓙绫?,闇?瑕丼ervlet API鏀寔
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public JsonResult handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("content_type_not_supported...", e);

        return new JsonResult(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "content_type_not_supported ", "");
    }


    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public JsonResult handleException(Exception e) {
        logger.error("Internal Server Error...", e);
        var code = ((ServiceException) e).getCode();
        switch (code) {
            case 3002:
                return new JsonResult(HttpStatus.FORBIDDEN, e.getMessage(), null);
            default:
                return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error ", e.getMessage());
        }
    }



}
