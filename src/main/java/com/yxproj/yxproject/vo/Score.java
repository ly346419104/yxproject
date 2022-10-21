package com.yxproj.yxproject.vo;

//评分
public class Score {
    // 评价类型/纬度 如环境、口味等
    private Integer type;
    // 评价打几星，如5星好评是5
    private Integer star;

   public Integer getType() {
        return type;
    }

   public void setType(Integer type) {
        this.type = type;
    }

   public Integer getStar() {
        return star;
    }

   public void setStar(Integer star) {
        this.star = star;
    }
}