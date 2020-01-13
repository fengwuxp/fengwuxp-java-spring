package test.com.oak.rbac.services.permission;

import com.github.javafaker.Faker;
import com.levin.commons.dao.JpaDao;
import com.oak.rbac.entities.E_OakPermission;
import com.oak.rbac.entities.OakPermission;
import com.oak.rbac.services.permission.PermissionService;
import com.oak.rbac.services.permission.info.PermissionInfo;
import com.oak.rbac.services.permission.req.CreatePermissionReq;
import com.oak.rbac.services.permission.req.QueryPermissionReq;
import com.wuxp.api.model.Pagination;
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
import test.com.oak.rbac.OakApplicationTest;

import java.util.List;

/**
 * PermissionServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>1月 10, 2020</pre>
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
//@Transactional(rollbackFor = {Throwable.class})
@Slf4j
public class PermissionServiceImplTest {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private JpaDao jpaDao;

    private Faker faker = new Faker();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createPermission(CreatePermissionReq req)
     */
    @Test
    public void testCreatePermission() throws Exception {

//        for (int i = 0; i < 200; i++) {
//            CreatePermissionReq req = new CreatePermissionReq();
//            req.setName(faker.name().name());
//            req.setValue("/log/test");
//            req.setResourceId("log");
//            req.setOrderCode(0);
//            Long permissionId = permissionService.createPermission(req);
//
//            Assert.assertNotNull(permissionId);
//            log.debug("--测试创建权限-->{}", permissionId);
//        }

    }

    /**
     * Method: deletedPermission(DeletePermissionReq req)
     */
    @Test
    public void testDeletedPermission() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: queryPermission(QueryPermissionReq req)
     */
    @Test
    public void testQueryPermission() throws Exception {

        QueryPermissionReq req = new QueryPermissionReq();
        Pagination<PermissionInfo> pagination = permissionService.queryPermission(req);
        log.debug("测试查询权限数据:{}", pagination);
    }

    @Test
    public void testQueryPermissionByJpa() {
        List<OakPermission> permissionList = jpaDao.selectFrom(OakPermission.class, "e")
                .joinFetchSet(true, E_OakPermission.roles)
                .page(1, 10).find();
        log.debug("测试查询权限数据:{}", permissionList.size());
    }

}
