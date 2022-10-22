package com.yxproj.yxproject.common.contanst.handler;

import cn.hutool.crypto.SecureUtil;
import com.yxproj.yxproject.common.contanst.UncheckToken;
import com.yxproj.yxproject.common.contanst.Util.WebContextUtil;
import com.yxproj.yxproject.common.contanst.Util.authrization.TokenManager;
import com.yxproj.yxproject.common.contanst.Util.reids.RedisHelper;
import com.yxproj.yxproject.common.contanst.exception.ExceptionCode;
import com.yxproj.yxproject.common.contanst.exception.ServiceException;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Log4j2
public class GlobalRequestHandler implements WebMvcConfigurer {
//    @Value("${jwt.ignoreUrlList}")
//    public List<String> ignoreUrl;
    @Value("${jwt.token_prefix}")
    public String token_prefix;
    @Value("${jwt.jwtSecret}")
    public String secret;
//    @Value("${rememberMe.time}")
    private String rememberMeTime;
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private TokenManager tokenManager;
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
        jwtAuthorization(request, handler);
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
    private void    jwtAuthorization(HttpServletRequest request, Object handle) {
        if (!request.getMethod().equals("OPTIONS")) {
            if (handle instanceof HandlerMethod) {
                //排除UncheckToken注解的接口
                if (((HandlerMethod) handle).getMethod().isAnnotationPresent(UncheckToken.class)) {
                    return;
                }
                String type = request.getHeader("type");
                String token = request.getHeader("token");
                if (!StringUtils.hasText(token)) {
                    throw new ServiceException(ExceptionCode.AUTH_ERROR.getCode(), "token缺失");
                }
                // verify token
                Map<String, String> result = verifyJWT(token);
                if (result == null || result.size() == 0) {
                    throw new ServiceException(ExceptionCode.AUTH_ERROR.getCode(), "token错误");
                }
                //判断该token是否存在于缓存
                request.setAttribute("userId", result.get("userId"));
                request.setAttribute("userType", result.get("userType"));
                request.setAttribute("departmentId", result.get("departmentId"));
                String userNameOrPhone = result.get("userName");
                String key = SecureUtil.md5(type + userNameOrPhone);
                redisTemplate.opsForHash().hasKey(key,"token");
                Object redisToken = redisTemplate.opsForHash().get(key, "token");
                if (redisToken == null) {
                    throw new ServiceException(ExceptionCode.AUTH_ERROR.getCode(), "token错误");
                } else {
                    String redistoken = token_prefix + " " + redisToken;
                    if (!redistoken.equals(token)) {
                        throw new ServiceException(ExceptionCode.AUTH_ERROR.getCode(), "token错误");
                    }
                }
            }
        }
    }

    private Map<String, String> verifyJWT(String token) {
        try {
            if (!StringUtils.hasText(token)) {
                return null;
            }
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(token_prefix + " ", ""))
                    .getBody();
            Map<String, String> map = new HashMap<>();
            body.forEach((k, v) -> {
                if (v == null) {
                    map.put(k, null);
                } else {
                    map.put(k, v.toString());
                }
            });
            return map;
        } catch (Exception e) {
            return null;
        }
    }

}
