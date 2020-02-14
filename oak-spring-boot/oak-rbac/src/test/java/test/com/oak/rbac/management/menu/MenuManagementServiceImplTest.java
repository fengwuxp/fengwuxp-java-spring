package test.com.oak.rbac.management.menu;

import com.github.javafaker.Faker;
import com.oak.rbac.management.menu.MenuManagementService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.rbac.OakApplicationTest;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class MenuManagementServiceImplTest {
    @Autowired
    private MenuManagementService advManagementService;

    private Faker faker = new Faker();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


}
