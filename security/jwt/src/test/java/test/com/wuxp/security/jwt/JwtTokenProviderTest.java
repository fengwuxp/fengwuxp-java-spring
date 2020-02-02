package test.com.wuxp.security.jwt;

import com.wuxp.security.jwt.JwtProperties;
import com.wuxp.security.jwt.JwtTokenPair;
import com.wuxp.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JwtTokenProviderTest {


    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Before
    public void before() {
        JwtProperties properties = new JwtProperties();
//        properties.setExpireTimeout(Duration.ofSeconds(1));
        jwtTokenProvider.setProperties(properties);
    }

    @Test
    public void jwtTest() throws Exception {

        JwtTokenPair jwtTokenPair = jwtTokenProvider.jwtTokenPair("1");

        Thread.sleep(2000);

        Jws<Claims> parse = jwtTokenProvider.parse(jwtTokenPair.getAccessToken());

        log.debug(parse.getBody().getAudience());

        Assert.assertTrue("检查失败", jwtTokenProvider.check(jwtTokenPair.getAccessToken()));
        Assert.assertTrue("检查失败", jwtTokenProvider.check(jwtTokenPair.getRefreshToken()));

    }

}
