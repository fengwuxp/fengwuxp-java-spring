package com.wuxp.api.signature;

import com.wuxp.api.ApiRequest;

import java.beans.Transient;
import java.util.Collections;
import java.util.Map;

/**
 * 需要签名的接口的入参可以实现该接口
 */
public interface ApiSignatureRequest extends ApiRequest {


    /**
     * 获取api签名的键值对
     *
     * @return
     */
    @Transient
    default Map<String, Object> getApiSignatureValues() {
        return Collections.emptyMap();
    }

    /**
     * 获取签名内容
     *
     * @return
     */
    @Transient
    String getApiSignature();
}
