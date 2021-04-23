package test.com.wuxp.security.jwt;

import com.wuxp.security.jwt.JwtProperties;
import com.wuxp.security.jwt.JwtTokenPair;
import com.wuxp.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class JwtTokenProviderTest {


    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();


    @Before
    public void before() throws Exception {
        JwtProperties properties = new JwtProperties();
//        properties.setExpireTimeout(Duration.ofSeconds(1));
        jwtTokenProvider.setJwtProperties(properties);
        jwtTokenProvider.afterPropertiesSet();
    }

    @Test
    public void codec() throws Exception {
        File privateFile = ResourceUtils.getFile("classpath:rsa-private.pem");
        File publicFile = ResourceUtils.getFile("classpath:rsa-public.pem");
        PrivateKey privateKey = PemUtils.readPrivateKeyFromFile(privateFile.getPath(), "RSA");
        PublicKey publicKey = PemUtils.readPublicKeyFromFile(publicFile.getPath(), "RSA");


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 1000);

        String token = Jwts.builder()
                .signWith(privateKey)
                .setSubject("test")
                .setIssuer("test")
                .setIssuedAt(new Date())
                .setAudience("张三")
                .claim("id", 1000)
                .setExpiration(calendar.getTime())
                .compact();
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(publicKey)
                .parseClaimsJws(token);
        int id = claimsJws.getBody().get("id", Integer.class);
        Assert.assertEquals(1000, id);
        Assert.assertNotNull(claimsJws);
    }

    @Test
    public void jwtTest() throws Exception {

        JwtTokenPair jwtTokenPair = jwtTokenProvider.jwtTokenPair("1");
        Thread.sleep(2000);

        JwtTokenPair.JwtTokenPayLoad accessToken = jwtTokenPair.getAccessToken();
        String token = accessToken.getToken();
        Jws<Claims> parse = jwtTokenProvider.parse(token);

        Claims body = parse.getBody();
        log.debug(body.getAudience());

        Assert.assertTrue("检查失败", jwtTokenProvider.check(token));
        Assert.assertTrue("检查失败", jwtTokenProvider.check(token));

    }


}
