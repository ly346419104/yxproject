package com.yxproj.yxproject.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yxproj.yxproject.common.contanst.JsonResult;
import com.yxproj.yxproject.common.contanst.UncheckToken;
import com.yxproj.yxproject.vo.TestPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    RedisTemplate redisTemplate;
    @GetMapping("/test")
    @UncheckToken
    public JsonResult test() {
        redisTemplate.opsForValue().set("aa","aa");
        System.out.println(redisTemplate.opsForValue().get("aa"));
        Map<String, String> objectObjectHashMap = Maps.newHashMap();
        objectObjectHashMap.put("aaa", "test");
        return new JsonResult(HttpStatus.OK,"objectObjectHashMap",objectObjectHashMap);
    }
    @GetMapping("/aaa")
    public Map<String, String> aaa() {
        Map<String, String> objectObjectHashMap = Maps.newHashMap();
        objectObjectHashMap.put("aaa", "test");
        return objectObjectHashMap;
    }
    @PostMapping("/testPost")
    public JsonResult testPost( @RequestBody TestPost testPost) {

        //Object转Map
        Map map = JSONObject.parseObject(JSONObject.toJSONString(testPost), Map.class);
        return new JsonResult(HttpStatus.OK,"objectObjectHashMap",map);

    }
   /* @Autowired
    private IUserService iUserService;

    @PostMapping("add")
    public JsonResult add(@RequestBody UserVo userVo, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return new JsonResult(HttpStatus.BAD_REQUEST, bindResult.getFieldError().getDefaultMessage());
        }
        User user = BeanUtil.toBean(userVo, User.class);
        boolean save = iUserService.save(user);
        if (save) {
            return new JsonResult(HttpStatus.OK, "添加成功");
        } else {
            return new JsonResult(HttpStatus.BAD_REQUEST, "添加失败");

        }
    }

    @GetMapping("list")
    public JsonResult list(@RequestParam(value = "name", required = false) String name) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.hasText(name), User::getName, name);
        List<User> list = iUserService.list(lambdaQueryWrapper);
        return new JsonResult(HttpStatus.OK, "查询成功", list);
    }*/
}
