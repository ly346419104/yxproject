package com.yxproj.yxproject.common.contanst.aop;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 忽略处理执行链
 *
 * @author shaokang_yuan
 * @see IInvokeLogIgnoreHandler
 */
public class InvokeLogIgnoreChain {

    private final List<IInvokeLogIgnoreHandler> handlerList = new ArrayList<>();

    public InvokeLogIgnoreChain(IInvokeLogIgnoreHandler... handlers) {
        handlerList.addAll(Arrays.asList(handlers));
    }

    public void add(IInvokeLogIgnoreHandler chain) {
        this.handlerList.add(chain);
    }

    public boolean check(ProceedingJoinPoint proceedingJoinPoint) {
        for (IInvokeLogIgnoreHandler chain : handlerList) {
            if (chain.check(proceedingJoinPoint)) return true;
        }
        return false;
    }

}
