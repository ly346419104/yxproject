package com.yxproj.yxproject.vo;

//评分维度
public enum ScoreType {
 ENV(1,"环境"),
 TASTE(2,"口味"),
 SERVICE(3,"服务");
    private int type;
    private String desc;

   ScoreType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

   public int getType() {
        return type;
    }

   public String getDesc() {
        return desc;
    }
}