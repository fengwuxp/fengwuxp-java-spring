package com.wuxp.security.authenticate.scancode;

import lombok.Data;

/**
 * 扫码登录
 */
@Data
public class ScanCodeLoginProperties {


    /**
     * 登录处理url
     */
    private String loginProcessingUrl = "/scan_code/login";

    /**
     * 轮询登录状态url
     */
//    private String pollingProcessingUrl = "/scan_code/polling";


}
