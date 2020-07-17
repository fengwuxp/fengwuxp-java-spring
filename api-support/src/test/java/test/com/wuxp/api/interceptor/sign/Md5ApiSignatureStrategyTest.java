package test.com.wuxp.api.interceptor.sign;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static com.wuxp.api.ApiRequest.*;

public class Md5ApiSignatureStrategyTest {


    @Test
    public void testFiledSort() {

        Map<String, Object> apiSignatureValues = new HashMap<>();
        apiSignatureValues.put("username", "123");
        apiSignatureValues.put("password", "123");
        final List<Object[]> signature = new ArrayList<>(apiSignatureValues.size() + 5);
        apiSignatureValues.forEach((key, value) -> {
            signature.add(new Object[]{key, value});
        });
        signature.sort(Comparator.comparing(o -> (o[0]).toString()));
        signature.add(new Object[]{APP_ID_KEY, "1"});
        signature.add(new Object[]{APP_SECRET_KEY, "2"});
        signature.add(new Object[]{CHANNEL_CODE, "2"});
        signature.add(new Object[]{NONCE_STR_KEY, "3"});
        signature.add(new Object[]{TIME_STAMP, "3"});
        String sign = signature.stream().map((items) -> items[0] + "=" + items[1]).collect(Collectors.joining("&"));

        System.out.println(sign);
    }
}
