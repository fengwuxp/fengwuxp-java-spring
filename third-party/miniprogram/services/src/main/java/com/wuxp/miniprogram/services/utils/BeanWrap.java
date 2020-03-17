package com.wuxp.miniprogram.services.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Classname BeanWrap
 * @Description TODO
 * @Date 2020/3/17 19:50
 * @Created by 44487
 */
public class BeanWrap {

    public BeanWrap() {
    }

    public static <T> List<T> copyList(List sourceList, List<T> targetList, Class<? extends T> targetClass) {
        if (targetList == null) {
            targetList = new ArrayList();
        }

        if (sourceList != null && sourceList.size() > 0) {
            Iterator var3 = sourceList.iterator();

            while(var3.hasNext()) {
                Object o = var3.next();
                Object obj = null;

                try {
                    obj = targetClass.newInstance();
                } catch (IllegalAccessException | InstantiationException var7) {
                    var7.printStackTrace();
                }

                BeanUtils.copyProperties(o, obj);
                ((List)targetList).add(obj);
            }
        }

        return (List)targetList;
    }

    public static <T> List<T> copyList(List sourceList, Class<? extends T> targetClass) {
        return copyList(sourceList, new ArrayList(sourceList.size()), targetClass);
    }

    public static Object copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static <T> T copyProperties(Object source, T target, Class<? extends T> targetClass) {
        BeanUtils.copyProperties(source, target, targetClass);
        return target;
    }

    public static <T> T toJavaObject(String jsonStr, Class<T> clazz) {
        JSONObject json = (JSONObject)JSONObject.parse(jsonStr);
        return json.toJavaObject(clazz);
    }

    public static <T> T toBean(String jsonStr, Class<T> clazz) {
        return toBean(jsonStr, clazz, PropertyNamingStrategy.SNAKE_CASE);
    }

    public static <T> T toBean(String jsonStr, Class<T> clazz, PropertyNamingStrategy propertyNamingStrategy) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(propertyNamingStrategy);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return mapper.readValue(jsonStr, clazz);
        } catch (IOException var5) {
            var5.printStackTrace();
            return null;
        }
    }
}
