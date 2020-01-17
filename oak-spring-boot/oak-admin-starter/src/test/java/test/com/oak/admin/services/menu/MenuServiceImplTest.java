package test.com.oak.admin.services.menu;

import com.oak.admin.enums.MenuIAction;
import com.oak.admin.services.menu.MenuService;
import com.oak.admin.services.menu.req.CreateMenuReq;
import com.wuxp.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.admin.OakApplicationTest;

/**
 * MenuServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>1月 16, 2020</pre>
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class MenuServiceImplTest {

    @Autowired
    private MenuService menuService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: create(CreateMenuReq req)
     */
    @Test
    public void testCreate() throws Exception {
        CreateMenuReq req = new CreateMenuReq();
        req.setOrderCode(0);
        req.setAction(MenuIAction.CLOSE_CURRENT);
        req.setIcon("user");
        req.setName("测试菜单");
        req.setParam("{id:1,name:'2'}");
        req.setValue("/user/list");
        req.setLeaf(false);
        ApiResp<Long> resp = menuService.create(req);
        log.debug("测试创建菜单  {}", resp);
        Assert.assertTrue(resp.isSuccess());
    }

    /**
     * Method: edit(EditMenuReq req)
     */
    @Test
    public void testEdit() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: delete(DeleteMenuReq req)
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
     * Method: query(QueryMenuReq req)
     */
    @Test
    public void testQuery() throws Exception {
//TODO: Test goes here...
    }


}
