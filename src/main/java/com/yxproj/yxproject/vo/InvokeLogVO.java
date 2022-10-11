package com.yxproj.yxproject.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 调用日志
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvokeLogVO implements Serializable {

    public static final int OPERATION_TYPE_INFO = 1;
    public static final int OPERATION_TYPE_WARNING = 2;
    public static final int OPERATION_TYPE_ERROR = 3;
    public static final int TYPE_LENGTH_REQUEST_PARAMETER = 8000;
    public static final int TYPE_LENGTH_DESCRIPTION = 2000;
    public static final int TYPE_LENGTH_OPERATION_RESULT = 8000;
    public static final int TYPE_LENGTH_ERROR_MESSAGE = 2000;
    public static final int TYPE_LENGTH_ERROR_STACK = 8000;

    private static final long serialVersionUID = -6992036355542632777L;


    private String id;

    private Boolean isDeleted;

    private String createUserId;

    private String deleteUserId;

    private Date createTime;

    private Date modifyTime;

    /**
     * 操作人的账号
     */
    private String username;

    /**
     * 操作人：比如小明
     */
    private String operator;

    /**
     * 类型：1信息、2警告、3错误
     */
    private Integer operationType;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParameter;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 操作结果
     */
    private String operationResult;

    private String clientIP;

    private String api;

    private String roleName;

    private String errorMessage;

    private String errorStack;

}