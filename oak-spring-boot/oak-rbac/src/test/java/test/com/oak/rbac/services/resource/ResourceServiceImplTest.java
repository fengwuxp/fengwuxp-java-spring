package test.com.oak.rbac.services.resource;

import com.oak.rbac.enums.ResourceType;
import com.oak.rbac.services.permission.req.CreatePermissionReq;
import com.oak.rbac.services.resource.ResourceService;
import com.oak.rbac.services.resource.req.CreateResourceReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.rbac.OakApplicationTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ResourceServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>1月 13, 2020</pre>
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
//@Transactional(rollbackFor = {Throwable.class})
@Slf4j
public class ResourceServiceImplTest {

    @Autowired
    private ResourceService resourceService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createResource(CreateResourceReq req)
     */
    @Test
    public void testCreateResource() throws Exception {

        CreateResourceReq req = new CreateResourceReq();
        req.setCode("example");
        req.setName("用例");
        req.setType(ResourceType.URL);
        req.setModuleCode("default");
        req.setModuleName("默认");

        String[][] actions = new String[][]{
                new String[]{"创建", "/example/crate"},
                new String[]{"修改", "/example/update"},
                new String[]{"查询", "/example/query"},
                new String[]{"删除", "/example/delete"},
                new String[]{"查看", "example/detail"}
        };


        req.setPermissions(Arrays.stream(actions).map(action -> {
            CreatePermissionReq createPermissionReq = new CreatePermissionReq();
            createPermissionReq.setName(action[0]);
            createPermissionReq.setRemark(action[1]);
            createPermissionReq.setValue(action[0]);
            createPermissionReq.setResourceId(req.getCode());

            return createPermissionReq;
        }).toArray(CreatePermissionReq[]::new));
        resourceService.createResource(req);

    }

    /**
     * Method: deleteResource(DeleteResourceReq req)
     */
    @Test
    public void testDeleteResource() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: queryResource(QueryResourceReq req)
     */
    @Test
    public void testQueryResource() throws Exception {
//TODO: Test goes here...
    }


}
