package test.com.oak.rbac.services.user;

import com.github.javafaker.Faker;
import com.oak.rbac.entities.OakAdminUser;
import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.info.OakAdminUserInfo;
import com.oak.rbac.services.user.req.CreateOakAdminUserReq;
import com.oak.rbac.services.user.req.QueryOakAdminUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.rbac.OakApplicationTest;

/**
 * OakAdminUserServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>2月 5, 2020</pre>
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
//@Transactional(rollbackFor = {Throwable.class})
@Slf4j
public class OakAdminUserServiceImplTest {

    @Autowired
    private OakAdminUserService oakAdminUserService;


    private Faker faker = new Faker();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: create(CreateOakAdminUserReq req)
     */
    @Test
    public void testCreate() throws Exception {
        CreateOakAdminUserReq req = new CreateOakAdminUserReq();
        req.setMobilePhone("12345678901")
                .setRoot(true)
                .setUserName("admin")
                .setName(faker.name().fullName())
                .setPassword("123456")
                .setNickName(faker.name().name())
                .setOrderCode(1);
        ApiResp<OakAdminUser> resp = oakAdminUserService.create(req);
        Assert.assertTrue(resp.getMessage(), resp.isSuccess());
    }

    /**
     * Method: edit(EditOakAdminUserReq req)
     */
    @Test
    public void testEdit() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: delete(DeleteOakAdminUserReq req)
     */
    @Test
    public void testDelete() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: findById(Long id)
     */
    @Test
    public void testFindById() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: query(QueryOakAdminUserReq req)
     */
    @Test
    public void testQuery() throws Exception {
        QueryOakAdminUserReq req = new QueryOakAdminUserReq();
        req.setUserName("admin");
        Pagination<OakAdminUserInfo> pagination = oakAdminUserService.query(req);
        Assert.assertFalse(pagination.isEmpty());
    }


}
