package com.yxproj.yxproject.common.contanst.Util.authrization;

import com.yxproj.yxproject.common.contanst.Util.reids.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("TokenManagerImpl")
public class TokenManagerImpl implements TokenManager {
    private static String TOKEN_PREFIX = "Api.User.Token.";
    private static String USERID_PREFIX = "Api.User.UserId.";
    private static String USERPERMISSIONS_PREFIX = "Api.User.Permissions.";
    private static String USERROLES_PREFIX = "Api.User.Roles.";
    @Autowired
    private RedisHelper redisHelper;

    public void LoginAsync(String token, String userId) {
        redisHelper.writeString(TOKEN_PREFIX + token, userId, 30l);
        redisHelper.writeString(USERID_PREFIX + userId, token, 30l);// 正向、反向关系都保存，这样保证一个token只能登陆一次
    }

    /// <summary>
    /// 更新过期时间
    /// </summary>
    /// <param name="token"></param>
    /// <returns></returns>
    public void UpdateTokenExpireDate(String token) {
        var userId = GetUserIdAsync(token);
        redisHelper.hexpire(TOKEN_PREFIX + token, 30l);
        redisHelper.hexpire(USERID_PREFIX + userId, 30l);
        redisHelper.hexpire(USERPERMISSIONS_PREFIX + userId, 30l);
        redisHelper.hexpire(USERROLES_PREFIX + userId, 30l);

    }

    public void SetActionCount(String controlAction, String token) {
        var value = redisHelper.readValue(controlAction + token);
        Integer numDid = 1;
        if (!StringUtils.hasText(value)) {
            numDid += Integer.parseInt(value);
        }
        redisHelper.writeString(controlAction + token, numDid.toString(), 20);
    }

    public Integer GetActionCount(String controlAction, String token) {
        Integer returnvalue = 0;
        var value = redisHelper.readValue(controlAction + token);
        if (value != null && !value.isEmpty()) {
            returnvalue = Integer.parseInt(value);
        }

        return returnvalue;

    }

    /// <summary>
    /// 缓存当前用户所具有的权限
    /// </summary>
    /// <param name="permissions">权限数组</param>
    /// <param name="userId">用户id</param>
    /// <returns></returns>
    public void SaveCurrentUserPermissionsAsync(String[] permissions, String userId) {
        redisHelper.writeObject(USERPERMISSIONS_PREFIX + userId, permissions,
                30l);
    }

    public void SaveCurrentUserRolesAsync(String[] roles, String userId) {
        redisHelper.writeObject(USERROLES_PREFIX + userId, roles, 30l);
    }

    public String[] GetCurrentUserPermissionsAsync(String userId) {

        return (String[]) redisHelper.readValue(USERPERMISSIONS_PREFIX + userId, String[].class);

    }

    public Boolean ClearRedisData(String userId, String token) {
        redisHelper.delete(USERPERMISSIONS_PREFIX + userId);
        redisHelper.delete(USERROLES_PREFIX + userId);
        redisHelper.delete(TOKEN_PREFIX + token);
        redisHelper.delete(USERID_PREFIX + userId);
        return true;
    }

    public String[] GetCurrentUserRolesAsync(String userId) {

        return (String[]) redisHelper.readValue(USERROLES_PREFIX + userId, String[].class);

    }

    public String GetUserIdAsync(String token) {
        String userId = redisHelper.readValue(TOKEN_PREFIX + token);
        if (userId == null) {
            return null;
        }
        String revertToken = redisHelper.readValue(USERID_PREFIX + userId);
        if (!revertToken.equals(token))// 如果反向查的token不一样，说明这个token已经过期了
        {
            return null;
        }
        return userId;
    }

    public String GetTokeByUserIdAsync(String userId) {
        String token = redisHelper.readValue(USERID_PREFIX + userId);
        if (!StringUtils.hasText(token)) {
            return null;
        }
        String revertuserId = redisHelper.readValue(TOKEN_PREFIX + token);
        if (revertuserId != userId)// 如果反向查的token不一样，说明这个token已经过期了
        {
            return null;
        }
        return token;
    }

    /// <summary>
    /// 得到错误登录次数
    /// </summary>
    /// <param name="redisKey"></param>
    /// <param name="email"></param>
    /// <returns></returns>
    public Integer GetErrorLoginTimesAsync(String redisKey, String email) {
        String key = redisKey + email;
        String count = redisHelper.readValue(key);
        if (!StringUtils.hasText(count)) {
            return 0;
        }
        return Integer.parseInt(count);
    }

    /// <summary>
    /// 重置登录错误次数
    /// </summary>
    /// <param name="redisKey"></param>
    /// <param name="email"></param>
    public void ResetErrorLogin(String redisKey, String email) {
        String key = redisKey + email;
        redisHelper.delete(key);
    }

    /// <summary>
    /// 递增登录错误次数
    /// </summary>
    /// <param name="redisKey"></param>
    /// <param name="email"></param>
    public void IncreaseErrorLoginAsync(String redisKey, String email) {
        String key = redisKey + email;
        String count = redisHelper.readValue(key);
        if (!StringUtils.hasText(count)) {
            redisHelper.writeString(key, "0", 30l);
        }
        Integer count1 = Integer.parseInt(count) + 1;
        redisHelper.writeString(key, count1.toString(), 30l);
    }
}
