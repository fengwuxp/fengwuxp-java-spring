package test.com.wuxp.security.example.serivces;

import com.oak.api.services.app.AppAuthService;
import com.oak.api.services.app.req.CreateAppAuthAccountReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.wuxp.security.example.ExampleApplicationTest;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExampleApplicationTest.class})
@Slf4j
public class AppAuthServiceTest {

    @Autowired
    private AppAuthService appAuthService;

    @Test
    public void testJpaDao() {

        Long appAuthAccount = appAuthService.createAppAuthAccount(new CreateAppAuthAccountReq());
        log.info("{}", appAuthAccount);
    }
}
