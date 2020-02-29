package com.wuxp.api.signature;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import static com.wuxp.api.ApiRequest.*;

/**
 * md5 验证签名
 */
@Slf4j
@Data
public class MD5ApiSignatureStrategy implements ApiSignatureStrategy, BeanFactoryAware, InitializingBean {

    private BeanFactory beanFactory;

    private AppInfoStore apiSignatureStore;

    public MD5ApiSignatureStrategy() {
    }

    public MD5ApiSignatureStrategy(AppInfoStore apiSignatureStore) {
        this.apiSignatureStore = apiSignatureStore;
    }


    @Override
    public void check(@NotNull ApiSignatureRequest request) throws ApiSignatureException {

        Map<String, Object> apiSignatureValues = request.getApiSignatureValues();
        String apiSignatureAppId = request.getAppId();
        AppInfo apiSignatureInfo = this.apiSignatureStore.getAppInfo(apiSignatureAppId);
        if (apiSignatureInfo == null) {
            throw new ApiSignatureException("无效的appId");
        }
        apiSignatureValues.put(APP_ID_KEY, apiSignatureAppId);
        apiSignatureValues.put(APP_SECRET_KEY, apiSignatureInfo.getAppSecret());
        apiSignatureValues.put(CHANNEL_CODE, request.getChannelCode());
        apiSignatureValues.put(NONCE_STR_KEY, request.getNonceStr());
        apiSignatureValues.put(TIME_STAMP, request.getTimeStamp());

        String sign = md5Utf8Text(genUrlStr(apiSignatureValues));
        String apiSignatureString = request.getApiSignature();
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

    private static String genUrlStr(Map<String, Object> data) {
        return genTextStr(data, true, "=", "&");
    }

    private static String genTextStr(Map<String, Object> data, boolean needKey, String keyValueDelim, String itemDelim) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        Map<String, Object> treeMap = new TreeMap<>(data);
        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            Object entryValue = entry.getValue();
            if (entryValue == null) {
                continue;
            }
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(itemDelim);
            }
            if (needKey) {
                sb.append(entry.getKey()).append(keyValueDelim);
            }
            sb.append(entryValue.toString());
        }


        return sb.toString();
    }

    private static String md5Utf8Text(String data) {
        return md5(data, "UTF-8");
    }


    private static String md5(String data, String encoding) {
        try {
            return DigestUtils.md5DigestAsHex(data.getBytes(encoding));
        } catch (UnsupportedEncodingException var3) {
            throw new RuntimeException(var3);
        }
    }
}
