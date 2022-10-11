package com.yxproj.yxproject.controller;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.yxproj.yxproject.common.contanst.JsonResult;
import com.yxproj.yxproject.vo.TestPost;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/test")
    public JsonResult test() {
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
}
