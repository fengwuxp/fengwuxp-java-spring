package com.wuxp.security.authority.url;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * url的配置
 */
@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUrlConfigAttribute implements ConfigAttribute {


    private static final long serialVersionUID = -2232451322699006376L;
    /**
     * examples: ==> /demo/create,/demo/edit,/demo/list
     */
    private String attribute;


    public static List<ConfigAttribute> createList(String... attributeNames) {
        Assert.notNull(attributeNames, "You must supply an array of attribute names");
        List<ConfigAttribute> attributes = new ArrayList<>(attributeNames.length);

        for (String attribute : attributeNames) {
            attributes.add(new RequestUrlConfigAttribute(attribute.trim()));
        }

        return attributes;
    }
}
