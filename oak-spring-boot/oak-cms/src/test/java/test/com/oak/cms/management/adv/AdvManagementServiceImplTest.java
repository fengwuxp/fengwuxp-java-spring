package test.com.oak.cms.management.adv;

import com.github.javafaker.Faker;
import com.oak.cms.management.adv.AdvManagementService;
import com.oak.cms.management.adv.req.*;
import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.advposition.AdvPositionService;
import com.oak.cms.services.advposition.info.AdvPositionInfo;
import com.oak.cms.services.advposition.req.CreateAdvPositionReq;
import com.oak.cms.services.advposition.req.DeleteAdvPositionReq;
import com.oak.cms.services.advposition.req.EditAdvPositionReq;
import com.oak.cms.services.advposition.req.QueryAdvPositionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.cms.OakApplicationTest;

import java.util.Calendar;
import java.util.Date;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class AdvManagementServiceImplTest {
    @Autowired
    private AdvManagementService advManagementService;

    @Autowired
    private AdvPositionService advPositionService;
    private Faker faker = new Faker();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testAdvCreate() throws Exception {
        AddAdvInfoReq addAdvInfoReq = new AddAdvInfoReq();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -3);
        Date endTime = calendar.getTime();
        //设置参数暂时略过
        addAdvInfoReq.setTitle(faker.name().title())
                .setContent("测试广告1")
                .setStartDate(new Date())
                .setEndDate(endTime);
        ApiResp<Long> createAdvInfo = advManagementService.createAdv(addAdvInfoReq);
        Assert.assertTrue(createAdvInfo.getMessage(), createAdvInfo.isSuccess());
    }

    @Test
    public void testAdvDelete() throws Exception {
        DelAdvReq delAdvReq = new DelAdvReq();
        delAdvReq.setId(Long.valueOf(1));
        ApiResp<Void> deleteAdv = advManagementService.deleteAdvById(delAdvReq);
        Assert.assertTrue(deleteAdv.getMessage(), deleteAdv.isSuccess());

    }


    @Test
    public void testAdvEdit() throws Exception {

        UpdateAdvReq updateAdvReq = new UpdateAdvReq();
        updateAdvReq.setId(Long.valueOf(1))
                .setContent("编辑测试正文")
                .setTitle("修改标题");
        ApiResp<Void> editAdv = advManagementService.editAdv(updateAdvReq);
        Assert.assertTrue(editAdv.getMessage(), editAdv.isSuccess());
    }

    @Test
    public void testQueryAdv() throws Exception {
        QueryAdvInfoReq queryAdvInfoReq = new QueryAdvInfoReq();
        Pagination<AdvInfo> advPages = advManagementService.queryAdv(queryAdvInfoReq);
        System.out.println(advPages);
    }

    @Test
    public void testQueryAdvPosition() throws Exception {
        QueryAdvPositionInfoReq queryAdvPositionInfoReq = new QueryAdvPositionInfoReq();
        QueryAdvPositionReq queryAdvPositionReq = new QueryAdvPositionReq();
        BeanUtils.copyProperties(queryAdvPositionInfoReq, queryAdvPositionReq);
        Pagination<AdvPositionInfo> advPositionPages = advPositionService.query(queryAdvPositionReq);
        System.out.println(advPositionPages);
    }

    @Test
    public void testCreateAdvPosition() throws Exception {
        AddAdvPositionReq addAdvPositionReq = new AddAdvPositionReq();
        CreateAdvPositionReq createAdvPositionReq = new CreateAdvPositionReq();
        BeanUtils.copyProperties(addAdvPositionReq, createAdvPositionReq);
        ApiResp<Long> createAdvPosition = advPositionService.create(createAdvPositionReq);
        Assert.assertTrue(createAdvPosition.getMessage(), createAdvPosition.isSuccess());
    }

    @Test
    public void testDelAdvPosition() throws Exception {
        DelAdvPositionReq delAdvPositionReq = new DelAdvPositionReq();
        delAdvPositionReq.setId(Long.valueOf(1));
        DeleteAdvPositionReq deleteAdvPositionReq = new DeleteAdvPositionReq();
        BeanUtils.copyProperties(delAdvPositionReq, deleteAdvPositionReq);
        ApiResp<Void> delAdvPosition = advPositionService.delete(deleteAdvPositionReq);
        Assert.assertTrue(delAdvPosition.getMessage(), delAdvPosition.isSuccess());
    }

    @Test
    public void testEditAdvPosition() throws Exception {

        UpdateAdvPositionReq updateAdvPositionReq = new UpdateAdvPositionReq();
        updateAdvPositionReq.setTitle("修改广告位标题")
                .setContent("修改广告位标题");
        EditAdvPositionReq editAdvPositionReq = new EditAdvPositionReq();
        BeanUtils.copyProperties(updateAdvPositionReq, editAdvPositionReq);
        ApiResp<Void> editAdvPosition = advPositionService.edit(editAdvPositionReq);
        Assert.assertTrue(editAdvPosition.getMessage(), editAdvPosition.isSuccess());
    }

}
