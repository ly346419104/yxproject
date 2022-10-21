package com.yxproj.yxproject.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity<U extends BaseEntity> {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    @TableField("is_deleted")
    private Boolean isDeleted;
    @TableField(value = "creation_time", fill = FieldFill.INSERT)
    private Date creationTime;
    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
    @TableField("creator_userId")
    private String creatorUserId;
    @TableField("deleter_userId")
    private String deleterUserId;

}



