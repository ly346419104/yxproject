package com.yxproj.yxproject.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity<U extends BaseEntity> {
    @TableId(value = "id", type = IdType.AUTO)

    private Long id;
    @TableField("isdeleted")

    private Boolean isDeleted;
    @TableField(value = "creationtime", fill = FieldFill.INSERT)
    private Date creationTime;
    @TableField(value = "modifytime", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
    @TableField("creatorUserId")
    private String creatorUser;
    @TableField("deleterUserId")
    private String deleterUserId;
}



