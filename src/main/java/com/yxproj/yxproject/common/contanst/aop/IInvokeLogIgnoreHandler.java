package com.yxproj.yxproject.common.contanst.aop;


import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 定义日志忽略处理器接口标准
 *
 * @author shaokang_yuan
 * @see InvokeLogIgnoreChain
 * @see InvokeLogIgnoreAPIHandler
 * @see InvokeLogIgnoreAnnotationHandler
 */
public interface IInvokeLogIgnoreHandler {

    /**
     * 检查当前切点是否忽略日志记录
     *
     * @param proceedingJoinPoint 切点对象
     * @return true 忽略，否则 false
     */
    boolean check(ProceedingJoinPoint proceedingJoinPoint);

}
