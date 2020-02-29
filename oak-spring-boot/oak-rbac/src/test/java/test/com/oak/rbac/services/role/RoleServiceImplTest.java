package test.com.oak.rbac.services.role;

import com.github.javafaker.Faker;
import com.oak.rbac.services.permission.PermissionService;
import com.oak.rbac.services.permission.info.PermissionInfo;
import com.oak.rbac.services.permission.req.QueryPermissionReq;
import com.oak.rbac.services.role.RoleService;
import com.oak.rbac.services.role.info.RoleInfo;
import com.oak.rbac.services.role.req.CreateRoleReq;
import com.oak.rbac.services.role.req.DeleteRoleReq;
import com.oak.rbac.services.role.req.EditRoleReq;
import com.oak.rbac.services.role.req.QueryRoleReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QuerySortType;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * RoleServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>1月 8, 2020</pre>
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
//@Transactional(rollbackFor = {Throwable.class})
@Slf4j
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    private Faker faker = new Faker();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createRole(CreateRoleReq req)
     */
    @Test
    public void testCreateRole() throws Exception {

        QueryPermissionReq queryPermissionReq = new QueryPermissionReq();
        Pagination<PermissionInfo> pagination = permissionService.queryPermission(queryPermissionReq);
        List<PermissionInfo> records = pagination.getRecords();

        CreateRoleReq req = new CreateRoleReq();
        req.setName(faker.name().title());
        req.setPermissionIds(records.stream().map(PermissionInfo::getId).toArray(Long[]::new));
        ApiResp<Long> resp = roleService.createRole(req);
//        Assert.assertTrue(resp.isSuccess());
        log.debug("测试创建角色{}", resp);
    }

    /**
     * Method: editRole(EditRoleReq req)
     */
    @Test
    public void testEditRole() throws Exception {

        QueryPermissionReq queryPermissionReq = new QueryPermissionReq();
        queryPermissionReq.setDefaultOrderById(QuerySortType.ASC);
        Pagination<PermissionInfo> pagination = permissionService.queryPermission(queryPermissionReq);
        List<PermissionInfo> records = pagination.getRecords();

        if (records.isEmpty()) {
            return;
        }


        QueryRoleReq req = new QueryRoleReq();
        req.setFetchPermission(true);
        RoleInfo roleInfo = roleService.queryRole(req).getFirst();
        if (roleInfo == null) {
            return;
        }
//        Assert.assertNotNull(roleInfo);
        EditRoleReq editRoleReq = new EditRoleReq();
        editRoleReq.setId(roleInfo.getId());
        editRoleReq.setName(faker.name().name());

        Set<PermissionInfo> permissions = roleInfo.getPermissions();
        List<PermissionInfo> permissionInfos = permissions.stream().filter(permissionInfo -> permissionInfo.getId() % 2 == 0).collect(Collectors.toList());
        permissionInfos.addAll(records);

        editRoleReq.setPermissionIds(permissionInfos.stream().map(PermissionInfo::getId).toArray(Long[]::new));
        ApiResp<Void> resp = roleService.editRole(editRoleReq);
        Assert.assertTrue(resp.isSuccess());
        log.debug("测试编辑角色{} , 权限个数：{}", resp, permissionInfos.size());

    }

    /**
     * Method: deleteRole(DeleteRoleReq req)
     */
    @Test
    public void testDeleteRole() throws Exception {

        DeleteRoleReq req = new DeleteRoleReq();
        req.setId(1L);
        ApiResp<Void> resp = roleService.deleteRole(req);
        log.debug("测试删除角色：{}", resp);
    }

    /**
     * Method: findRoleById(Long id)
     */
    @Test
    public void testFindRoleById() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: findRoleByName(String name)
     */
    @Test
    public void testFindRoleByName() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: queryRole(QueryRoleReq req)
     */
    @Test
    public void testQueryRole() throws Exception {
//TODO: Test goes here...
    }


}
