package com.yxproj.yxproject.vo;

import java.util.List;

// 评价表单
public class ReviewForm {
    // 评价门店ID
    private Long poiId;
    // 提交评价的用户ID
    private Long userId;
    // 各纬度评分（如口味评分、服务评分、环境评分等）
    private List<Score> scores;
    // 推荐值（1-强烈推荐 2-一般  3-强烈不推荐）
    private Integer recommend;
    // 顾客评论文字描述
    private String comment;

   public Long getPoiId() {
        return poiId;
    }

   public void setPoiId(Long poiId) {
        this.poiId = poiId;
    }

   public Long getUserId() {
        return userId;
    }

   public void setUserId(Long userId) {
        this.userId = userId;
    }

   public List<Score> getScores() {
        return scores;
    }

   public void setScores(List<Score> scores) {
        this.scores = scores;
    }

   public Integer getRecommend() {
        return recommend;
    }

   public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

   public String getComment() {
        return comment;
    }

   public void setComment(String comment) {
        this.comment = comment;
    }
}
