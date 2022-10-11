package com.yxproj.yxproject.common.contanst.handler;

import com.yxproj.yxproject.common.contanst.Util.WebContextUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Configuration
@Log4j2
public class GlobalRequestHandler implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initInterceptor()).addPathPatterns("/**")
                ;
    }

    private HandlerInterceptor initInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                interceptorHandler(request, handler);
                return true;
            }
        };
    }


    private void interceptorHandler(HttpServletRequest request, Object handler) throws Exception {

        //校验IP
        checkIP(request);
        //校验参数
        // checkParam(request);
        //校验token（token放在cookie和redis里，这是针对H5，如果是app可以放到response头里，用request头接收）
        // checkTokenAndRole(request, handler);
//        jwtAuthorization(request, handler);
        //语言国际化
        locale(request);
        //防重放
        //   antiReplay();
    }

    private boolean checkIP(HttpServletRequest request) {
//        if (request.getRequestURI().contains("test")){
//          throw new RuntimeException("网络异常");
//        }
        return true;
    }

    private void locale(HttpServletRequest request) {
        String method = request.getMethod();
        log.info(WebContextUtil.getRequest().getRequestURI());
//        String lang = request.getHeader(Constants.LANG);
//        LocaleContextHolder.setLocale(StringUtils.hasText(lang) ? Locale.SIMPLIFIED_CHINESE : Locale.forLanguageTag(lang));
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        WebMvcConfigurer.super.addReturnValueHandlers(handlers);
    }
}
