package com.yxproj.yxproject.common.contanst.Util.authrization;

public interface TokenManager {


    void LoginAsync(String token, String userId);

    /// <summary>
    /// 更新过期时间
    /// </summary>
    /// <param name="token"></param>
    /// <returns></returns>
    void UpdateTokenExpireDate(String token);


    void SetActionCount(String controlAction, String token);


    Integer GetActionCount(String controlAction, String token);


    /// <summary>
    /// 缓存当前用户所具有的权限
    /// </summary>
    /// <param name="permissions">权限数组</param>
    /// <param name="userId">用户id</param>
    /// <returns></returns>
    void SaveCurrentUserPermissionsAsync(String[] permissions, String userId);

    void SaveCurrentUserRolesAsync(String[] roles, String userId);


    String[] GetCurrentUserPermissionsAsync(String userId);


    Boolean ClearRedisData(String userId, String token);


    String[] GetCurrentUserRolesAsync(String userId);


    String GetUserIdAsync(String token);

    String GetTokeByUserIdAsync(String userId);


    /// <summary>
    /// 得到错误登录次数
    /// </summary>
    /// <param name="redisKey"></param>
    /// <param name="email"></param>
    /// <returns></returns>
    Integer GetErrorLoginTimesAsync(String redisKey, String email);

    /// <summary>
    /// 重置登录错误次数
    /// </summary>
    /// <param name="redisKey"></param>
    /// <param name="email"></param>
    void ResetErrorLogin(String redisKey, String email);

    /// <summary>
    /// 递增登录错误次数
    /// </summary>
    /// <param name="redisKey"></param>
    /// <param name="email"></param>
    void IncreaseErrorLoginAsync(String redisKey, String email);
}
