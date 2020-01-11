package com.wuxp.security.example.signature;

import com.wuxp.api.signature.AppInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MockAppInfo implements AppInfo {

    private String appId;

    private String appSecret;

}
