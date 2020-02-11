package com.oak.member.management.member;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.levin.commons.dao.JpaDao;
import com.oak.api.entities.system.ClientChannel;
import com.oak.api.entities.system.E_ClientChannel;
import com.oak.api.enums.ClientType;
import com.oak.member.entities.E_Member;
import com.oak.member.entities.Member;
import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.MemberVerifyStatus;
import com.oak.member.enums.OpenType;
import com.oak.member.enums.VipGrade;
import com.oak.member.helper.WxMaHelper;
import com.oak.member.management.member.info.AccountInfo;
import com.oak.member.management.member.req.MemberAccountInfoReq;
import com.oak.member.management.member.req.RegisterMemberFromWxMaReq;
import com.oak.member.management.member.req.RegisterMemberReq;
import com.oak.member.services.account.MemberAccountService;
import com.oak.member.services.account.info.MemberAccountInfo;
import com.oak.member.services.account.req.CreateMemberAccountReq;
import com.oak.member.services.member.MemberService;
import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.member.req.CreateMemberReq;
import com.oak.member.services.open.MemberOpenService;
import com.oak.member.services.open.req.CreateMemberOpenReq;
import com.oak.member.services.secure.MemberSecureService;
import com.oak.member.services.secure.req.CreateMemberSecureReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author laiy
 * create at 2020-02-06 16:11
 * @Description
 */
@Slf4j
@Service
public class MemberManagementServiceImpl implements MemberManagementService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberAccountService accountService;

    @Autowired
    private MemberSecureService secureService;

    @Autowired
    private MemberOpenService openService;

    @Autowired
    private JpaDao jpaDao;


    @Override
    public ApiResp<Long> register(RegisterMemberReq req) {
        if (StringUtils.hasText(req.getMobilePhone())) {
            Long mobileCount = jpaDao.selectFrom(Member.class)
                    .eq(E_Member.mobilePhone, req.getMobilePhone())
                    .count();
            if (mobileCount > 0) {
                return RestfulApiRespFactory.error("该手机号已注册！");
            }
        }

        ClientChannel regSource = jpaDao.selectFrom(ClientChannel.class)
                .eq(E_ClientChannel.clientType, req.getClientType())
                .findOne();

        //注册会员
        CreateMemberReq createMemberReq = new CreateMemberReq();
        BeanUtils.copyProperties(req, createMemberReq);
        createMemberReq.setIdAuth(false)
                .setVerify(MemberVerifyStatus.PENDING)
                .setRegDateTime(new Date())
                .setRegSource(regSource.getCode())
                .setName(req.getNickName());
        ApiResp<Long> rsp = memberService.create(createMemberReq);
        Assert.isTrue(rsp.isSuccess(), rsp.getMessage());
        Long memberId = rsp.getData();
        //会员账号信息
        CreateMemberAccountReq accountReq = new CreateMemberAccountReq();
        accountReq.setId(memberId)
                .setMoney(0)
                .setFrozenMoney(0)
                .setFrozenCoupon(0)
                .setCoupon(0)
                .setPoints(0)
                .setFrozenPoints(0)
                .setStatus(AccountStatus.AVAILABLE)
                .setVipGrade(VipGrade.M_COMMON);
        rsp = accountService.create(accountReq);
        Assert.isTrue(rsp.isSuccess(), rsp.getMessage());

        //会员安全信息
        CreateMemberSecureReq secureReq = new CreateMemberSecureReq();
        secureReq.setId(memberId)
                .setLoginPassword(req.getLoginPassword())
                .setLoginPwdUpdateTime(new Date());
        rsp = secureService.create(secureReq);
        Assert.isTrue(rsp.isSuccess(), rsp.getMessage());

        if (req.getOpenType() != null) {
            //会员绑定信息
            CreateMemberOpenReq openReq = new CreateMemberOpenReq();
            openReq.setMemberId(memberId)
                    .setOpenId(req.getOpenId())
                    .setOpenType(req.getOpenType())
                    .setUnionId(req.getUnionId());
            rsp = openService.create(openReq);
            Assert.isTrue(rsp.isSuccess(), rsp.getMessage());
        }

        return RestfulApiRespFactory.ok(memberId);
    }

    @Override
    public ApiResp<Long> registerFromWxMa(RegisterMemberFromWxMaReq req) {
        //获取用户SessionKey和openId
        WxMaJscode2SessionResult sessionResult = WxMaHelper.getWxSessionInfo(req.getCode());
        if (sessionResult == null) {
            return RestfulApiRespFactory.error("错误");
        }
        //解密用户信息
        WxMaUserInfo userInfo = WxMaHelper.getWxUserInfo(sessionResult.getSessionKey(), req.getUserEncryptedData(), req.getUserIvStr());
        //解密用户手机号
        WxMaPhoneNumberInfo phoneNumberInfo = WxMaHelper.getPhoneNoInfo(sessionResult.getSessionKey(), req.getEncryptedData(), req.getIvStr());

        RegisterMemberReq registerReq = new RegisterMemberReq();
        BeanUtils.copyProperties(req, registerReq);
        registerReq.setOpenType(OpenType.WEIXIN_MA)
                .setOpenId(sessionResult.getOpenid())
                .setUnionId(userInfo.getUnionId())
                .setMobilePhone(phoneNumberInfo.getPhoneNumber())
                .setNotPassword(Boolean.FALSE)
                .setVerify(MemberVerifyStatus.APPROVED)
                .setClientType(ClientType.MOBILE);
        ApiResp<Long> rsp = register(registerReq);
        return rsp;
    }

    @Override
    public ApiResp<AccountInfo> getMemberInfo(MemberAccountInfoReq req) {
        //ApiResp<MemberAccountInfo> resp = RestfulApiRespFactory.created()
        AccountInfo accountInfo = new AccountInfo();
        MemberInfo member = memberService.findById(req.getId());
        if (member == null) {
            return RestfulApiRespFactory.error("账号不存在");
        }
        BeanUtils.copyProperties(member, accountInfo);
        MemberAccountInfo memberAccount = accountService.findById(member.getId());
        accountInfo.setMoney(memberAccount.getMoney())
                .setFrozenMoney(memberAccount.getFrozenMoney())
                .setCoupon(memberAccount.getFrozenCoupon())
                .setPoints(memberAccount.getPoints())
                .setFrozenPoints(memberAccount.getFrozenPoints())
                .setStatus(memberAccount.getStatus())
                .setVipGrade(memberAccount.getVipGrade());
        //TODO 月卡数据
        return RestfulApiRespFactory.created(accountInfo);
    }
}
