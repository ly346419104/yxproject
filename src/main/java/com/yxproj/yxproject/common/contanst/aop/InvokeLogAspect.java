package com.yxproj.yxproject.common.contanst.aop;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yxproj.yxproject.common.contanst.IgnoreLog;
import com.yxproj.yxproject.common.contanst.JsonResult;
import com.yxproj.yxproject.common.contanst.Util.NetworkUtil;
import com.yxproj.yxproject.common.contanst.Util.WebContextUtil;
import com.yxproj.yxproject.vo.InvokeLogVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * <p>收集用户使用记录</p>
 *
 * <p>
 * 日志收集前有一定忽略策略 {@code UserLogIgnore} {@code USER_LOG_IGNORE_CHAIN}，
 * 该策略具有扩展性，具体查看 {@code UserLogIgnoreChain}
 * </p>
 *
 * @author shaokang_yuan
 * @see IInvokeLogIgnoreHandler
 * @see InvokeLogIgnoreChain
 * @see InvokeLogIgnoreAPIHandler
 * @see InvokeLogIgnoreAnnotationHandler
 * @see InvokeLogIgnore
 */
@Component
@Aspect
@RequiredArgsConstructor
@Log4j2
public class  InvokeLogAspect {

    /**
     * 忽略的策略们
     */
    private static final InvokeLogIgnoreChain USER_LOG_IGNORE_CHAIN;

    static {
        USER_LOG_IGNORE_CHAIN = new InvokeLogIgnoreChain(new InvokeLogIgnoreAnnotationHandler(),
                new InvokeLogIgnoreAPIHandler());
    }


    @Pointcut("execution(* com.yxproj.*.controller..*.*(..))")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        if (USER_LOG_IGNORE_CHAIN.check(proceedingJoinPoint)) {
//            return proceedingJoinPoint.proceed();
//        }
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method1 = signature.getMethod();
        Annotation[] declaredAnnotations = method1.getDeclaredAnnotations();

        for (int i = 0; i < declaredAnnotations.length; i++) {
            if (declaredAnnotations[i] instanceof IgnoreLog) {
                return null;
            }
        }
        Object proceed = null;
        InvokeLogVO invokeLog = InvokeLogVO.builder().build();
        invokeLog.setCreateTime(new Date());
        invokeLog.setIsDeleted(false);
//        CurrentUser currentUser = getCurrentUser();
//        invokeLog.setUsername(Optional.ofNullable(currentUser).map(CurrentUser::getUsername).orElse(null));
//        invokeLog.setOperator(Optional.ofNullable(currentUser).map(CurrentUser::getUsername).orElse(null));
//        invokeLog.setCreateUserId(Optional.ofNullable(currentUser).map(CurrentUser::getUserId).orElse(null));
        invokeLog.setApi(getApi());
        invokeLog.setRequestMethod(getRequestMethod());
//        invokeLog.setDescription(getDescription(proceedingJoinPoint));
        invokeLog.setRequestParameter(getRequestParameter(proceedingJoinPoint));
        invokeLog.setClientIP(getClientIP());
        JsonResult jsonResult = null;
        try {
            proceed = proceedingJoinPoint.proceed();
            if (proceed == null) return null;

            if (proceed instanceof JsonResult) {
                jsonResult = (JsonResult) proceed;
            }
            // 构造日志
            invokeLog.setOperationType(getOperationType(jsonResult));
            invokeLog.setOperationResult(jsonResult != null ? Objects.requireNonNullElse(jsonResult.getMsg() + JSON.toJSON(jsonResult.getData()), null) : null);

        } catch (Exception e) {
            log.error("[调用日志] 发生异常: [{}]", e.getMessage());
            String stack = Arrays.toString(e.getStackTrace());
            invokeLog.setErrorMessage(e.toString());
            invokeLog.setErrorStack(stack);
            invokeLog.setOperationType(InvokeLogVO.OPERATION_TYPE_ERROR);
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            String requestParameter = invokeLog.getRequestParameter();
            if (StringUtils.hasLength(requestParameter) && requestParameter.length() > InvokeLogVO.TYPE_LENGTH_REQUEST_PARAMETER) {
                invokeLog.setRequestParameter(requestParameter.substring(0, InvokeLogVO.TYPE_LENGTH_REQUEST_PARAMETER));
            }
            String description = invokeLog.getDescription();
            if (StringUtils.hasLength(description) && description.length() > InvokeLogVO.TYPE_LENGTH_DESCRIPTION) {
                invokeLog.setDescription(description.substring(0, InvokeLogVO.TYPE_LENGTH_DESCRIPTION));
            }
            String operationResult = invokeLog.getOperationResult();
            if (StringUtils.hasLength(operationResult) && operationResult.length() > InvokeLogVO.TYPE_LENGTH_OPERATION_RESULT) {
                invokeLog.setOperationResult(operationResult.substring(0, InvokeLogVO.TYPE_LENGTH_OPERATION_RESULT));
            }
            String errorMessage = invokeLog.getErrorMessage();
            if (StringUtils.hasLength(errorMessage) && errorMessage.length() > InvokeLogVO.TYPE_LENGTH_ERROR_MESSAGE) {
                invokeLog.setErrorMessage(errorMessage.substring(0, InvokeLogVO.TYPE_LENGTH_ERROR_MESSAGE));
            }
            String errorStack = invokeLog.getErrorStack();
            if (StringUtils.hasLength(errorStack) && errorStack.length() > InvokeLogVO.TYPE_LENGTH_ERROR_STACK) {
                invokeLog.setErrorStack(errorStack.substring(0, InvokeLogVO.TYPE_LENGTH_ERROR_STACK));
            }
            try {

                StringBuilder logBuilder = new StringBuilder();
                String method = getRequestMethod();
                logBuilder.append(method).append(",").append(getApi());
                if ("POST".equals(method)) {
                    String body = getRequestParameter(proceedingJoinPoint);
                    if (StringUtils.hasLength(getRequestParameter(proceedingJoinPoint))) {
                        logBuilder.append(",body=").append(body);
                    }
                }
                String resp = jsonResult != null ?
                        Objects.requireNonNullElse(jsonResult.getMsg() + JSON.toJSON(jsonResult.getData()), null) : null;
                logBuilder.append(",resp=").append(resp);
                log.info(logBuilder.toString());
//                invokeLogService.save(invokeLog);
            } catch (Exception e) {
                log.error("[调用日期] 存储时发生异常, message: {}", e.getMessage());
            }
        }
        return proceed;
    }

//    private String getDescription(ProceedingJoinPoint proceedingJoinPoint) {
//        String description = "";
//        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
//        Method method = signature.getMethod();
//        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
//        if (apiOperation != null) {
//            description = apiOperation.value();
//        } else {
////            GetMapping getMapping = method.getAnnotation(GetMapping.class);
////            PostMapping postMapping = method.getAnnotation(PostMapping.class);
//        }
//        // 不要控制器上的描述
////        Api api = AnnotationUtils.findAnnotation(proceedingJoinPoint.getThis().getClass(), Api.class);
////        if (api != null)
////            description = JSON.toJSONString(api.tags());
//        return description;
//    }

    /**
     * 获取当前请求的用户信息
     *
     * @return user
     */
//    private CurrentUser getCurrentUser() {
//        HttpServletRequest request = WebContextUtil.getRequest();
//        CurrentUser build = CurrentUser.builder().build();
//        if (request != null) {
//            String userId = request.getHeader(Parameter.USER_ID);
//            String username = request.getHeader(Parameter.USERNAME);
//            String enterpriseId = request.getHeader(Parameter.ENTERPRISE_ID);
//            String userEnterpriseType = request.getHeader(Parameter.USER_ENTERPRISE_TYPE);
//            build.setUserId(userId);
//            build.setUsername(username);
//            build.setEnterpriseId(enterpriseId);
//            build.setUserEnterpriseType(userEnterpriseType);
//        }
//        return build;
//    }

    /**
     * 获取当前用户的 ip 地址
     *
     * @return ip
     */
    private String getClientIP() {
        try {
            return NetworkUtil.getIpAddress(WebContextUtil.getRequest());
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取当前 HTTP 请求方法
     *
     * @return HTTP's method
     */
    private String getRequestMethod() {
        HttpServletRequest request = WebContextUtil.getRequest();
        return request.getMethod();
    }

    /**
     * 获取当前 HTTP 请求的参数
     *
     * @param proceedingJoinPoint
     * @return parameter
     */
    private String getRequestParameter(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] args = proceedingJoinPoint.getArgs();
        if (args == null || args.length == 0) return null;
        String[] names = ((CodeSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        HashMap<Object, Object> paramMap = Maps.newHashMap();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof InputStreamSource) {
                args[i] = null;
            } else if (args[i] instanceof ServletRequest) {
                args[i] = null;
            } else if (args[i] instanceof ServletResponse) {
                args[i] = null;
            } else {
                Object value = checkIfSensitiveData(names[i], args[i]);
                paramMap.put(names[i], value);
            }
        }
//        class t_class t_student name class subject score

//        select studentName,max(score) from student where subject="math" group by studentName
        try {
            return JSON.toJSONString(paramMap);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 检查是否为敏感数据
     * 先简单做，后面有时间可以做强大点
     *
     * @param name 变量名
     * @param arg  变量的值
     * @return arg
     */
    private Object checkIfSensitiveData(String name, Object arg) {
//        if (arg instanceof LoginModel) {
//            ((LoginModel) arg).setPassword("******");
//            return arg;
//        } else if (name.equalsIgnoreCase("password")) {
//            arg = "******";
//            return arg;
//        } else if (name.equalsIgnoreCase("confirmPassword")) {
//            arg = "******";
//            return arg;
//        } else if (name.equalsIgnoreCase("oldPassword")) {
//            arg = "******";
//            return arg;
//        }
        return arg;
    }

    /**
     * 获取当前的操作类型
     * 200 信息
     * 400 警告
     * 其他 错误
     *
     * @param jsonResult 响应结果对象
     * @return 操作类型
     */
    private Integer getOperationType(JsonResult jsonResult) {
        if (jsonResult == null) return InvokeLogVO.OPERATION_TYPE_ERROR;
        Integer statusCode = jsonResult.getStatusCode();
        if (statusCode == null) return InvokeLogVO.OPERATION_TYPE_ERROR;
        if (statusCode == 200) return InvokeLogVO.OPERATION_TYPE_INFO;
        if (statusCode >= 400 && statusCode < 500) return InvokeLogVO.OPERATION_TYPE_WARNING;
        return InvokeLogVO.OPERATION_TYPE_ERROR;
    }

    /**
     * 获取当前请求的接口地址
     *
     * @return /api/xxx
     */
    private String getApi() {
        return WebContextUtil.getRequest().getRequestURI();
    }

}
