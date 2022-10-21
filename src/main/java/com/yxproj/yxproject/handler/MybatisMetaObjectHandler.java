package com.yxproj.yxproject.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        if (getFieldValByName("id", metaObject) == null)
            setFieldValByName("id", UUID.randomUUID().toString(), metaObject);
        setFieldValByName("creationTime", new Date(), metaObject);
        setFieldValByName("modifyTime", new Date(), metaObject);
//        setFieldValByName(getFieldUpdateTime(List.of(metaObject.getGetterNames())), new Date(), metaObject);
//        setFieldValByName(getFieldUpdateTime(List.of(metaObject.getGetterNames())), new Date(), metaObject);
        setFieldValByName(getFieldDelete(List.of(metaObject.getGetterNames())), false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
//        setFieldValByName(getFieldUpdateTime(List.of(metaObject.getGetterNames())), new Date(), metaObject);
        setFieldValByName("modifyTime", new Date(), metaObject);
    }

    private Object get(String key, MetaObject metaObject) {
        try {
            return metaObject.getValue(key);
        } catch (Exception e) {
            return null;
        }
    }

    private String getFieldDelete(List<String> getterNames) {
        if (getterNames.contains("isDeleted")) {
            return "isDeleted";
        } else if (getterNames.contains("is_deleted")) {
            return "is_deleted";
        } else {
            return "isDeleted";
        }
    }

    private String getFieldUpdateTime(List<String> getterNames) {
        if (getterNames.contains("modifyTime")) {
            return "modifyTime";
        } else if (getterNames.contains("modify_time")) {
            return "modify_time";
        } else {
            return "modifyTime";
        }
    }

    private String getFieldCreateTime(List<String> getterNames) {
        if (getterNames.contains("creationTime")) {
            return "creationTime";
        } else if (getterNames.contains("creation_time")) {
            return "creation_time";
        } else {
            return "creationTime";
        }
    }
}
