package com.yxproj.yxproject.common.contanst.Util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.yxproj.yxproject.common.contanst.JsonResult;
import com.yxproj.yxproject.vo.LoginModel;
import com.yxproj.yxproject.vo.ResponseResult;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Component
public class TokenUtil {

    @Value("${jwt.token.expire.time}")
    private String tokenExpireTime;
    @Value("${jwt.jwtSecret}")
    private String SECRET;

    @Value("${jwt.refresh.token.expire.time}")
    private long refreshTokenExpireTime;
    @Value("${jwt.token_prefix}")
    private String TOKEN_PREFIX ;

    private Map<String , String> map = new HashMap<>(2);

    /**
     * 固定的头
     */

    private static final String PHONE = "PHONE";
    private static final String PC = "PC";
    private static final String ZX = "ZX";




    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 生成token和refreshToken
     * @param phoneOrUserName
     * @param type
     * @return
     */
    public ResponseResult getToken(LoginModel customClaims, String phoneOrUserName, String type, String mac){
        //生成
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userName", customClaims.getUserName());
        claims.put("userId", customClaims.getUserId());
        claims.put("realName", customClaims.getName());
        claims.put("permissionIds",customClaims.getPermissions());
        claims.put("roleIds",customClaims.getRoleIds());
//        claims.put("userType", customClaims.getUserType());

        String refreshToken = UUID.randomUUID().toString().replaceAll("-","");
       // String prefix = this.getPrefix(type);
        //生成jwt
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MILLISECOND, Integer.parseInt(tokenExpireTime));
        Date d = c.getTime();
        String token = this.buildJWT(claims,d);
        //注释原因：暂不考虑app与pc同时登录，后期可考虑
       // String key = SecureUtil.md5(prefix + phoneOrUserName);
        //向hash中放入数值
//        redisTemplate.opsForHash().put(key,"token", token);
//        redisTemplate.opsForHash().put(key,"refreshToken", refreshToken);
//        //设置key过期时间
//        redisTemplate.expire(key,
//                refreshTokenExpireTime, TimeUnit.MILLISECONDS);
        String key = SecureUtil.md5(type + phoneOrUserName);

        redisTemplate.opsForHash().put(key,"token", token);
        redisTemplate.opsForHash().put(key,"refreshToken", refreshToken);
        //设置key过期时间
        redisTemplate.expire(key,
                refreshTokenExpireTime, TimeUnit.MILLISECONDS);
        if((("2").equals(type) || ("3").equals(type))  && mac != null){//pc端和座席端登陆
            redisTemplate.opsForHash().put(phoneOrUserName,"mac", mac);
        }else if(("1").equals(type)){// app登录
            redisTemplate.opsForHash().delete(phoneOrUserName,"mac");
        }
        ResponseResult result = new ResponseResult();
        result.setAccess_token(TOKEN_PREFIX + " " + token);
        result.setRefresh_token(refreshToken);
        result.setExpire_time(new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(d));
        result.setToken_type(TOKEN_PREFIX);
        result.setAccountUser(customClaims);

        return result;
    }



    /**
     * 刷新token
     * @param phoneOrUserName
     * @param type
     * @param refreshToken
     * @return
     */
    public JsonResult refreshToken(LoginModel customClaims,String phoneOrUserName, String type, String refreshToken,String mac){
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userName", customClaims.getUserName());
        claims.put("userId", customClaims.getUserId());
        claims.put("realName", customClaims.getName());
//        claims.put("enterpriseId", customClaims.getEnterpriseId());
//        claims.put("userEnterpriseType", customClaims.getUserEnterpriseType());
        String oldRefresh = (String) redisTemplate.opsForHash().get(phoneOrUserName, "refreshToken");
        if (StrUtil.isBlank(oldRefresh)){

            return new JsonResult(HttpStatus.UNAUTHORIZED,"refreshToken过期",null);
        }else {
            if (!oldRefresh.equals(refreshToken)){

                return new JsonResult(HttpStatus.UNAUTHORIZED,"refreshToken错误",null);

            }else {
                String oldmac = (String) redisTemplate.opsForHash().get(phoneOrUserName, "mac");
                if(oldmac != null && mac == null){
                    return new JsonResult(HttpStatus.UNAUTHORIZED,"refreshToken错误",null);
                }
                if(oldmac != null && !oldmac.equals(mac)){
                    return new JsonResult(HttpStatus.UNAUTHORIZED,"refreshToken错误",null);
                }
                //生成jwt
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.SECOND, Integer.parseInt(tokenExpireTime));
                Date d = c.getTime();
                String token = this.buildJWT(claims,d);
                redisTemplate.opsForHash().put(phoneOrUserName,"token", token);
                redisTemplate.opsForHash().put(phoneOrUserName,"refreshToken", refreshToken);
                redisTemplate.expire(phoneOrUserName,
                        refreshTokenExpireTime, TimeUnit.MILLISECONDS);

                ResponseResult json = new ResponseResult();
                json.setAccess_token(TOKEN_PREFIX + " " + token);
                json.setExpire_time(new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(d));
                json.setToken_type(TOKEN_PREFIX);
                json.setAccountUser(customClaims);

                return new JsonResult(HttpStatus.OK,"成功并返回数据", json);
            }
        }

    }

    /**
     * 删除key
     * @param phone
     * @param type
     */
    public boolean removeToken(String phone, String type){
//        String prefix = this.getPrefix(type);
//        String key = SecureUtil.md5(prefix + phone);
        return redisTemplate.delete(phone);
    }

    /**
     * 获取前缀
     * @param type 1 phone app端  2  pc端 3 坐席端
     * @return
     */
    private String getPrefix(String type){
        String prefix = null;
        if ("1".equals(type)){
            prefix =PHONE;
        }else if ("2".equals(type)){
            prefix = PC;
        }else if ("3".equals(type)){
            prefix =ZX;
        }
        return prefix;
    }

    /**
     * 生成jwt
     * @param claims 用户信息
     * @param d 过期时间
     * @return
     */
    private String buildJWT(Map<String, Object> claims ,Date d){

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setExpiration(d)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();


        return jwt;
    }
}
