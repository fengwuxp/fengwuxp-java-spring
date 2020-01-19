package test.com.oak.organization.management.organization;

import com.github.javafaker.Faker;
import com.oak.organization.enums.OrganizationType;
import com.oak.organization.management.organization.OrganizationManagementService;
import com.oak.organization.management.organization.req.RegisterOrganizationReq;
import com.wuxp.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.organization.OakApplicationTest;

/**
 * OrganizationManagementServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>1月 19, 2020</pre>
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class OrganizationManagementServiceImplTest {

    @Autowired
    private OrganizationManagementService organizationManagementService;

    private Faker faker = new Faker();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: registerOrganization(RegisterOrganizationReq req)
     */
    @Test
    public void testRegisterOrganization() throws Exception {

        RegisterOrganizationReq req = new RegisterOrganizationReq();
        req.setAddress(faker.address().streetAddress())
                .setContacts(faker.name().name())
                .setMobilePhone(RandomStringUtils.randomAlphanumeric(11))
                .setOrganizationType(OrganizationType.PLATFORM)
                .setAreaId("350102")
                .setRemark("测试")
                .setPassword("543210")
                .setAreaName("鼓楼区")
                .setName(faker.name().name());
        ApiResp<Long> resp = organizationManagementService.registerOrganization(req);

        Assert.assertTrue(resp.getMessage(), resp.isSuccess());

    }


}
