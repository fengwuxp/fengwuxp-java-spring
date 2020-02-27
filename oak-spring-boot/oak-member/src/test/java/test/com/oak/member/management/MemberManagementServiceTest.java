package test.com.oak.member.management;

import com.github.javafaker.Faker;
import com.levin.commons.dao.JpaDao;
import com.oak.api.entities.system.ClientChannel;
import com.oak.api.enums.ClientType;
import com.oak.member.enums.Gender;
import com.oak.member.enums.LoginModel;
import com.oak.member.management.member.MemberManagementService;
import com.oak.member.management.member.info.AccountInfo;
import com.oak.member.management.member.info.MemberLoginInfo;
import com.oak.member.management.member.req.*;
import com.oak.member.services.member.info.MemberInfo;
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
        login();
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
                .setCode("0130jIF82biEnM009cH82sjtF820jIFO")
                .setEncryptedData("UttQML9Oc6asOeHPNMyjgvdZLEN2Gvk7HDtMrZUkeJrjb1E5n+KQEnoeptYitvQrQZeckck3ScpYN38zFYLAz6weYuMTxFfyIP7sujNMCTboXEi4OzJQKNS/mTOomjv7KYfnsEWCB9s4uYKSOFq+j0NpJTdPXnuRSNkVQOBLFp3aMnlzvF0cQ165Tnc/bHuiB6oOF5JQsBj0fiKU3hA9Xj227x4M93QkbJq0QhSK17P2eNRbEbRzMaMCpm+ejJTQ+nBfnbKCnF92iXEEN4R6hZUI2DgHXw5zwzYEzrXxIFNmRkHKqo+5vw5302oPYX8qfAO3Z6PgfMKH/rqfQROpJu+K9Fs/GUK27KmcXxESmamPLJOT9Vc/iwhyVQevQt1ZuvOS9GxXmUixkKOPb1uh/xomfvspGNDOacA55/Hbm3v6or3C46ygrG/84xORSvkQMczoHn+IT8UOn3V95q2X3g==")
                .setIvStr("qHLxwglbPqVbuzWuS2SE0g==")
                .setNickName("测试")
                .setAvatarUrl("测试")
                .setAreaId("350102")
                .setGender(Gender.SECRET)
                .setUserEncryptedData("")
                .setUserIvStr("qHLxwglbPqVbuzWuS2SE0g==")
                .setAreaName("鼓楼区")
                .setAvatarUrl("ster");
        ApiResp<Long> resp = memberManagementService.registerFromWxMa(req);

        Assert.assertTrue(resp.getMessage(), resp.isSuccess());

    }

    @Test
    public void testRegisterFromWx() throws Exception {

        RegisterMemberFromWxReq req = new RegisterMemberFromWxReq();
        req.setAddress(faker.address().streetAddress())
                .setAreaId("350102")
                .setAreaName("鼓楼区");
        ApiResp<MemberInfo> resp = memberManagementService.registerFromWx(req);

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

    public void login() {
        MemberLoginReq req = new MemberLoginReq();
        req.setLoginModel(LoginModel.PASSWORD);
        req.setMobilePhone("13107685221");
        req.setPassword("543210");
        ApiResp<MemberLoginInfo> resp = memberManagementService.login(req);
        Assert.assertTrue(resp.getMessage(), resp.isSuccess());
        System.out.println(resp.toString());
    }

}
