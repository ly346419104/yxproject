package com.yxproj.yxproject.common.contanst.aop;

import com.google.common.collect.Lists;
import com.yxproj.yxproject.common.contanst.Util.WebContextUtil;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;

/**
 * @author shaokang_yuan
 */
public class InvokeLogIgnoreAPIHandler implements IInvokeLogIgnoreHandler {

    private static final List<String> IGNORE_API_LIST;

    static {
        IGNORE_API_LIST = Lists.newArrayList();
//        eg:
//        IGNORE_API_LIST.add("/api/user/log/page");
    }

    @Override
    public boolean check(ProceedingJoinPoint proceedingJoinPoint) {
        String requestURI = WebContextUtil.getRequest().getRequestURI();
        return IGNORE_API_LIST.contains(requestURI);
    }

}
