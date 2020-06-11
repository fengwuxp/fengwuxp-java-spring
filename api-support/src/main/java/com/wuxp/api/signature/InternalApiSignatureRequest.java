package com.wuxp.api.signature;


import com.wuxp.api.AbstractApiReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.beans.Transient;
import java.util.Map;

/**
 * @author wxup
 */
@Data
@Accessors(chain = true)
public class InternalApiSignatureRequest implements ApiSignatureRequest {

    public static final String APP_ID_HEADER_KEY = "Api-App-Id";
    public static final String NONCE_STR_HEADER_KEY = "Api-Nonce-Str";
    public static final String APP_SIGN_HEADER_KEY = "Api-Signature";
    public static final String TIME_STAMP_HEADER_KEY = "Api-Time-Stamp";
    public static final String CHANNEL_CODE_HEADER_KEY = "Api-Channel-Code";


    @Schema(name = "name", description = "应用分配到的appId")
    @NotNull(message = "appId不能为空")
    private String appId;

    @Schema(name = "nonceStr", description = "用于签名的一次性随机字符串")
    @NotNull(message = "nonceStr不能为空")
    private String nonceStr;

    @Schema(name = "timeStamp", description = "请求时间戳")
    @NotNull(message = "timeStamp不能为空")
    private Long timeStamp;

    @Schema(name = "apiSignature", description = "接口签名")
    @NotNull(message = "apiSignature不能为空")
    private String apiSignature;

    @Schema(name = "channelCode", description = "渠道号")
    @NotNull(message = "渠道号不能为空")
    private String channelCode;


    private Map<String, Object> apiSignatureValues;

    public InternalApiSignatureRequest() {
    }

    public InternalApiSignatureRequest(HttpServletRequest httpServletRequest) {
        this.appId = httpServletRequest.getHeader(APP_ID_HEADER_KEY);
        this.nonceStr = httpServletRequest.getHeader(NONCE_STR_HEADER_KEY);
        this.apiSignature = httpServletRequest.getHeader(APP_SIGN_HEADER_KEY);
        String timeStamp = httpServletRequest.getHeader(TIME_STAMP_HEADER_KEY);
        if (StringUtils.hasText(timeStamp)) {
            this.timeStamp = Long.parseLong(timeStamp);
        }
        this.channelCode = httpServletRequest.getHeader(CHANNEL_CODE_HEADER_KEY);
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
