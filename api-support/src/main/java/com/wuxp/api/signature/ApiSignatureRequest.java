package com.wuxp.api.signature;

import com.wuxp.api.ApiRequest;

import java.beans.Transient;
import java.util.Collections;
import java.util.Map;

/**
 * 需要签名的接口的入参可以实现该接口
 *
 * @author wxup
 */
public interface ApiSignatureRequest extends ApiRequest {

    String APP_SIGNATURE_KEY = "apiSignature";

    String INJECT_APP_SIGNATURE = "#apiSignature";


    /**
     * 获取api签名的键值对
     *
     * @return 签名的键值对
     */
    @Transient
    default Map<String, Object> getApiSignatureValues() {
        return Collections.emptyMap();
    }


}
