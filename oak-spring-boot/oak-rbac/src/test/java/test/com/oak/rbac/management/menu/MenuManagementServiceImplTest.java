package test.com.oak.rbac.management.menu;

import com.github.javafaker.Faker;
import com.oak.rbac.enums.MenuIAction;
import com.oak.rbac.enums.MenuShowType;
import com.oak.rbac.management.menu.MenuManagementService;
import com.oak.rbac.management.menu.req.AddMenuInfoReq;
import com.oak.rbac.management.menu.req.DelMenuInfoReq;
import com.oak.rbac.management.menu.req.QueryMenuInfoReq;
import com.oak.rbac.management.menu.req.UpdateMenuInfoReq;
import com.oak.rbac.services.menu.info.MenuInfo;
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

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class MenuManagementServiceImplTest {
    @Autowired
    private MenuManagementService menuManagementService;

    private Faker faker = new Faker();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testAddMenuInfo() throws Exception {
        AddMenuInfoReq addMenuInfoReq = new AddMenuInfoReq();
        addMenuInfoReq.setAction(MenuIAction.OPEN_LINK_BLANK)
                .setName("新增菜单名1")
                .setLeaf(Boolean.TRUE)
                .setShowType(MenuShowType.ALWAYS)
                .setRemark("新增备注1")
                .setIcon("新增图标1");
        ApiResp<Long> addMenuInfo = menuManagementService.addMenuInfo(addMenuInfoReq);
        Assert.assertTrue(addMenuInfo.getMessage(), addMenuInfo.isSuccess());

    }


    @Test
    public void testDelMenuInfo() throws Exception {
        DelMenuInfoReq delMenuInfoReq = new DelMenuInfoReq();
        delMenuInfoReq.setId(Long.valueOf(1));
        ApiResp<Void> delMenuInfo = menuManagementService.delMenuInfo(delMenuInfoReq);
        Assert.assertTrue(delMenuInfo.getMessage(), delMenuInfo.isSuccess());
    }


    @Test
    public void testEditMenuInfo() throws Exception {
        UpdateMenuInfoReq updateMenuInfoReq = new UpdateMenuInfoReq();
        updateMenuInfoReq.setId(Long.valueOf(1))
                .setAction(MenuIAction.OPEN_LINK_BLANK)
                .setName("修改后的菜单名")
                .setEnable(Boolean.FALSE)
                .setRemark("修改后的备注")
                .setIcon("editIcon");
        ApiResp<Void> editMenuInfo = menuManagementService.updateMenuInfo(updateMenuInfoReq);
        Assert.assertTrue(editMenuInfo.getMessage(), editMenuInfo.isSuccess());
    }


    @Test
    public void testQueryMenuInfo() throws Exception {
        QueryMenuInfoReq queryMenuInfoReq = new QueryMenuInfoReq();
        Pagination<MenuInfo> getMenuInfos = menuManagementService.getMenuInfo(queryMenuInfoReq);
        System.out.println(getMenuInfos);
    }


}
