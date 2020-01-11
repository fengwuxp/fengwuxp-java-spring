package com.wuxp.api.signature;


import com.wuxp.api.ApiBaseReq;

import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;
import java.util.Map;

public class InternalApiSignatureRequest extends ApiBaseReq {

    private static final String APP_ID_HEADER_KEY = "Api-App-Id";
    private static final String NONCE_STR_HEADER_KEY = "Api-nonce-str";
    private static final String APP_SIGN_HEADER_KEY = "Api-Signature";
    private static final String TIME_STAMP_HEADER_KEY = "Api-Time-Stamp";

    private Map<String, Object> apiSignatureValues;

    public InternalApiSignatureRequest() {
    }

    public InternalApiSignatureRequest(HttpServletRequest httpServletRequest) {
        this.appId = httpServletRequest.getHeader(APP_ID_HEADER_KEY);
        this.nonceStr = httpServletRequest.getHeader(NONCE_STR_HEADER_KEY);
        this.apiSignature = httpServletRequest.getHeader(APP_SIGN_HEADER_KEY);
        this.timeStamp = Long.parseLong(httpServletRequest.getHeader(TIME_STAMP_HEADER_KEY));
    }

    @Override
    @Transient
    public Map<String, Object> getApiSignatureValues() {
        return apiSignatureValues;
    }

    public void setApiSignatureValues(Map<String, Object> apiSignatureValues) {
        this.apiSignatureValues = apiSignatureValues;
    }
}
