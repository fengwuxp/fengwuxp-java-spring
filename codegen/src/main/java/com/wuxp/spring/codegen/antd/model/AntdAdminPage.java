package com.wuxp.spring.codegen.antd.model;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 页面生成 元数据
 */
@Data
@Accessors(chain = true)
public class AntdAdminPage {


    // 页面名称
    private String name;


}
