package com.wuxp.api.signature;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wuxp.api.ApiRequest.*;

/**
 * md5 验证签名
 *
 * @author wxup
 */
@Slf4j
@Data
public class Md5ApiSignatureStrategy implements ApiSignatureStrategy, BeanFactoryAware, InitializingBean {

    private BeanFactory beanFactory;

    private AppInfoStore apiSignatureStore;

    public Md5ApiSignatureStrategy() {
    }

    public Md5ApiSignatureStrategy(AppInfoStore apiSignatureStore) {
        this.apiSignatureStore = apiSignatureStore;
    }


    @Override
    public void check(@NotNull ApiSignatureRequest request) throws ApiSignatureException {

        InternalApiSignatureRequest signatureRequest = (InternalApiSignatureRequest) request;


        String apiSignatureAppId = signatureRequest.getAppId();
        AppInfo apiSignatureInfo = this.apiSignatureStore.getAppInfo(apiSignatureAppId);
        if (apiSignatureInfo == null) {
            throw new ApiSignatureException("无效的appId");
        }

        Map<String, Object> apiSignatureValues = request.getApiSignatureValues();
        final List<Object[]> signature = new ArrayList<>(apiSignatureValues.size() + 5);
        apiSignatureValues.forEach((key, value) -> signature.add(new Object[]{key, value}));
        signature.sort(Comparator.comparing(o -> (o[0]).toString()));
        signature.add(new Object[]{APP_ID_KEY, apiSignatureAppId});
        signature.add(new Object[]{APP_SECRET_KEY, apiSignatureInfo.getAppSecret()});
        signature.add(new Object[]{CHANNEL_CODE, signatureRequest.getChannelCode()});
        signature.add(new Object[]{NONCE_STR_KEY, signatureRequest.getNonceStr()});
        signature.add(new Object[]{TIME_STAMP, signatureRequest.getTimeStamp()});

        String signText = signature.stream().map((items) -> items[0] + "=" + items[1]).collect(Collectors.joining("&"));
        if (log.isDebugEnabled()) {
            log.debug("签名字符串：{}", signText);
        }
        String sign = md5Utf8Text(signText);
        String apiSignatureString = signatureRequest.getApiSignature();
        if (!sign.equals(apiSignatureString)) {
            throw new ApiSignatureException("签名验证失败");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.apiSignatureStore == null) {
            this.apiSignatureStore = beanFactory.getBean(AppInfoStore.class);
        }
    }

    private static String md5Utf8Text(String data) {
        return md5(data, StandardCharsets.UTF_8.name());
    }

    private static String md5(String data, String encoding) {
        try {
            return DigestUtils.md5DigestAsHex(data.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
