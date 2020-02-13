package test.com.oak.member.management;

import com.github.javafaker.Faker;
import com.levin.commons.dao.JpaDao;
import com.oak.api.entities.system.ClientChannel;
import com.oak.api.enums.ClientType;
import com.oak.member.enums.Gender;
import com.oak.member.management.member.MemberManagementService;
import com.oak.member.management.member.info.AccountInfo;
import com.oak.member.management.member.req.MemberAccountInfoReq;
import com.oak.member.management.member.req.RegisterMemberFromWxMaReq;
import com.oak.member.management.member.req.RegisterMemberFromWxReq;
import com.oak.member.management.member.req.RegisterMemberReq;
import com.wuxp.api.ApiResp;
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
import test.com.oak.member.OakApplicationTest;

import java.util.Date;

/**
 * @author laiy
 * create at 2020-02-10 10:42
 * @Description
 */
@RunWith(SpringRunner.class)
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(classes = {OakApplicationTest.class})
public class MemberManagementServiceTest {

    @Autowired
    private MemberManagementService memberManagementService;

    @Autowired
    private JpaDao jpaDao;

    private Faker faker = new Faker();

    @Before
    public void before() throws Exception {
        ClientChannel clientChannel = new ClientChannel();
        clientChannel.setClientType(ClientType.MOBILE);
        clientChannel.setCode("ycdfasd7845135fasdf");
        clientChannel.setCreateTime(new Date());
        clientChannel.setName(ClientType.MOBILE.getDesc());
        clientChannel.setOrderIndex(1);
        clientChannel.setEnabled(true);
        clientChannel.setUpdateTime(new Date());
        jpaDao.create(clientChannel);
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void test()  throws Exception {
        testRegister();
        getMemberAccountInfo();
    }

    @Test
    public void testRegister() throws Exception {

        RegisterMemberReq req = new RegisterMemberReq();
        req.setAddress(faker.address().streetAddress())
                .setNotPassword(true)
                .setMobilePhone("13107685221")
                .setMobileAuth(false)
                .setAreaId("350102")
                .setNickName("测试")
                .setUserName("测试")
                .setLoginPassword("543210")
                .setAreaName("鼓楼区")
                .setClientType(ClientType.MOBILE)
                .setAvatarUrl("ster");
        ApiResp<Long> resp = memberManagementService.register(req);

        Assert.assertTrue(resp.getMessage(), resp.isSuccess());
        System.out.println(resp);
    }

    @Test
    public void testRegisterFromWxMa() throws Exception {

        RegisterMemberFromWxMaReq req = new RegisterMemberFromWxMaReq();
        req.setAddress(faker.address().streetAddress())
                .setCode("013ue0n80j1NaD1TMTj802LJm80ue0nD")
                .setEncryptedData("UbW6iBKDSMWzsMs25hpS/TH/NjO/wvGdKwgk3fBeGvzmd//BdYHnHPO5X/XW5C5F7arK0RxoV95pNcsynpIuLmyQrmLuyS8F7RLZ+YtrSjZ8enaSY/tLn/nfAhVpS+Z/O//XOvYIH4hPveTFlhkYIlKF7SWZkqLri7ledeRwb15WsIdhVGDFVKozcHYuX0QxbK3shaomxxsAs5+FSjv5Jit3MB3H346ok0mrDfMFry9pzy7rq9OPDrBXw+4NkpMrPidrALksmu+HPfQcFoRoBCfDS2bg9aTH8EpAYz4aAoRTHisB2xRjQTeR7B/13v/pZ6ld5cs53CeY0Z/mL6dR1I3WlnTXNZfswKDkhw8H8srdlDV4s4hYmzFvikp05RxRgYgphG3UwnYCxR1AzK8epfWr4coSp5QOnHFoDlIFjiEtOKcvPlHgRPxRrgJPz48XgKqmgzYRDvOYpV49OD9xcw==")
                .setIvStr("eMqZjwO2iQbsPcu6770ErA==")
                .setNickName("测试")
                .setAvatarUrl("测试")
                .setAreaId("350102")
                .setGender(Gender.SECRET)
                .setUserEncryptedData("UbW6iBKDSMWzsMs25hpS/TH/NjO/wvGdKwgk3fBeGvzmd//BdYHnHPO5X/XW5C5F7arK0RxoV95pNcsynpIuLmyQrmLuyS8F7RLZ+YtrSjZ8enaSY/tLn/nfAhVpS+Z/O//XOvYIH4hPveTFlhkYIlKF7SWZkqLri7ledeRwb15WsIdhVGDFVKozcHYuX0QxbK3shaomxxsAs5+FSjv5Jit3MB3H346ok0mrDfMFry9pzy7rq9OPDrBXw+4NkpMrPidrALksmu+HPfQcFoRoBCfDS2bg9aTH8EpAYz4aAoRTHisB2xRjQTeR7B/13v/pZ6ld5cs53CeY0Z/mL6dR1I3WlnTXNZfswKDkhw8H8srdlDV4s4hYmzFvikp05RxRgYgphG3UwnYCxR1AzK8epfWr4coSp5QOnHFoDlIFjiEtOKcvPlHgRPxRrgJPz48XgKqmgzYRDvOYpV49OD9xcw==")
                .setUserIvStr("eMqZjwO2iQbsPcu6770ErA==")
                .setAreaName("鼓楼区")
                .setAvatarUrl("ster");
        ApiResp<Long> resp = memberManagementService.registerFromWxMa(req);

        Assert.assertTrue(resp.getMessage(), resp.isSuccess());

    }

    @Test
    public void testRegisterFromWx() throws Exception {

        RegisterMemberFromWxReq req = new RegisterMemberFromWxReq();
        req.setAddress(faker.address().streetAddress())
                .setCode("oPGID1ZEH5Rg5Xen3L-QAm11ts3w")
                .setAreaId("350102")
                .setAreaName("鼓楼区");
        ApiResp<Long> resp = memberManagementService.registerFromWx(req);

        Assert.assertTrue(resp.getMessage(), resp.isSuccess());

    }

    @Test
    public void getMemberAccountInfo() throws Exception {

        MemberAccountInfoReq req = new MemberAccountInfoReq();
        req.setId(1L);
        ApiResp<AccountInfo> resp = memberManagementService.getMemberInfo(req);

        Assert.assertTrue(resp.getMessage(), resp.isSuccess());
        System.out.println(resp.toString());
    }

}
