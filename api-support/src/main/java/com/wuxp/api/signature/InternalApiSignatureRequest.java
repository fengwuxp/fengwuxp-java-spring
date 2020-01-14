package com.wuxp.api.signature;


import com.wuxp.api.ApiBaseReq;

import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;
import java.util.Map;

public class InternalApiSignatureRequest extends ApiBaseReq {

//    private static final String APP_ID_HEADER_KEY = "Api-App-Id";
//    private static final String NONCE_STR_HEADER_KEY = "Api-nonce-str";
//    private static final String APP_SIGN_HEADER_KEY = "Api-Signature";
//    private static final String TIME_STAMP_HEADER_KEY = "Api-Time-Stamp";

    private static final String APP_ID_NAME_KEY = "appId";
    private static final String NONCE_STR_NONCE_STR_KEY = "nonceStr";
    private static final String APP_SIGN_TIME_STAMP_KEY = "timeStamp";
    private static final String TIME_STAMP_API_SIGNATURE_KEY = "apiSignature";

    private Map<String, Object> apiSignatureValues;

    public InternalApiSignatureRequest() {
    }

    public InternalApiSignatureRequest(HttpServletRequest httpServletRequest) {
        this.appId = httpServletRequest.getParameter(APP_ID_NAME_KEY);
        this.nonceStr = httpServletRequest.getParameter(NONCE_STR_NONCE_STR_KEY);
        this.apiSignature = httpServletRequest.getParameter(APP_SIGN_TIME_STAMP_KEY);
        this.timeStamp = Long.parseLong(httpServletRequest.getParameter(TIME_STAMP_API_SIGNATURE_KEY));
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
