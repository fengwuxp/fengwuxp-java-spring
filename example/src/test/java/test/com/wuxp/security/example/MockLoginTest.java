package test.com.wuxp.security.example;

import com.wuxp.security.jwt.JwtTokenPair;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

//@ActiveProfiles("dev")
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {ExampleApplicationTest.class})
@Slf4j
public class MockLoginTest {

//    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testLogin() {

        Map<String, String> params = new HashMap<>();

        params.put("username", "admin");
        params.put("password", "123456");

        JwtTokenPair.JwtTokenPayLoad jwtTokenPayLoad = restTemplate.postForObject(
                "http://localhost:8090/api/login?username=admin&password=123456",
                params,
                JwtTokenPair.JwtTokenPayLoad.class);

        log.info("login result {}",jwtTokenPayLoad);

    }

    @Test
    public void testPasswordEncoder(){
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder("1122");

        boolean matches = passwordEncoder.matches("12345", "1111111111");
        log.info("matches {}",matches);
    }
}
