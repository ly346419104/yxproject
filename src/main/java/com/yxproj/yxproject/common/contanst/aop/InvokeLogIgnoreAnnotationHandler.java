package com.yxproj.yxproject.common.contanst.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * 检查方法上是否标记有 {@code UserLogIgnore} 注解
 *
 * @author shaokang_yuan
 * @see InvokeLogIgnore
 */
public class InvokeLogIgnoreAnnotationHandler implements IInvokeLogIgnoreHandler {

    @Override
    public boolean check(ProceedingJoinPoint proceedingJoinPoint) {
        return AnnotationUtils.findAnnotation(
                ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod(),
                InvokeLogIgnore.class) != null;
    }

}
