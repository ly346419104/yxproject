package com.yxproj.yxproject.service;

import com.yxproj.yxproject.vo.PageQuery;
import com.yxproj.yxproject.vo.ReviewForm;

import java.util.Date;
import java.util.List;

public interface ReviewService {
// 完成topNScoreAvgStarPois方法实现统计在一段时间内各个维度得分的平均分最高的前N名门店及其平均分（平均分精确到小数位2位）
//
//注意：
//
//1、一段时间内的评价表单可能比较多，一次查询可能查不完所有的评价表单
//2、评价维度可能不止3个，需要能够支持维度扩充但不需要变更代码
/////**
// * 统计在一段时间内各个维度得分的平均分最高的前N名门店及其平均分（平均分精确到小数位2位）
// * 注意
// * 1、一段时间内的评价表单可能比较多，一次查询可能查不完所有的评价表单
// * 2、评价维度可能不止3个，需要能够支持维度扩充但不需要变更代码
 /**
     * 分页查询门店评价表单详情
     * @param poiId
     * @param begin
     * @param end
     * @return
 */
 List<ReviewForm> queryReviewForms(Integer poiId, Date begin, Date end, PageQuery pageQuery);

/**
     * 计算时间周期内的某门店的评价总数量
     * @param poiId
     * @param begin 开始时间
     * @param end 结束时间
     * @return
 */
 Integer countReviewForms(Integer poiId, Date begin, Date end);

/**
 * 分页查询评价表单详情
 * @param begin
 * @param end
 * @return
 */
List<ReviewForm> queryReviewForms(Date begin, Date end, PageQuery pageQuery);

/**
 * 计算时间周期内的所有评价总数量
 * @param begin 开始时间
 * @param end 结束时间
 * @return
 */
Integer countReviewForms(Date begin, Date end);
}



