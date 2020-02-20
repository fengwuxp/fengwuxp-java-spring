package com.oak.member.management.member;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.SelectDao;
import com.oak.api.entities.system.ClientChannel;
import com.oak.api.entities.system.E_ClientChannel;
import com.oak.api.enums.ClientType;
import com.oak.api.helper.SettingValueHelper;
import com.oak.member.cache.MemberSessionCacheHelper;
import com.oak.member.constant.MemberCacheKeyConstant;
import com.oak.member.entities.*;
import com.oak.member.enums.*;
import com.oak.member.helper.SnHelper;
import com.oak.member.helper.WxMaHelper;
import com.oak.member.management.member.info.AccountInfo;
import com.oak.member.management.member.info.CheckMobilePhoneAndOpenIdInfo;
import com.oak.member.management.member.info.MemberLoginInfo;
import com.oak.member.management.member.req.*;
import com.oak.member.services.account.MemberAccountService;
import com.oak.member.services.account.info.MemberAccountInfo;
import com.oak.member.services.account.req.CreateMemberAccountReq;
import com.oak.member.services.account.req.EditMemberAccountReq;
import com.oak.member.services.accountlog.MemberAccountLogService;
import com.oak.member.services.accountlog.req.CreateMemberAccountLogReq;
import com.oak.member.services.member.MemberService;
import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.member.req.CheckMemberReq;
import com.oak.member.services.member.req.CreateMemberReq;
import com.oak.member.services.open.MemberOpenService;
import com.oak.member.services.open.req.CheckBindOpenReq;
import com.oak.member.services.open.req.CreateMemberOpenReq;
import com.oak.member.services.secure.MemberSecureService;
import com.oak.member.services.secure.info.LoginFail;
import com.oak.member.services.secure.req.CreateMemberSecureReq;
import com.oak.member.services.token.MemberTokenService;
import com.oak.member.services.token.info.MemberTokenInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.jwt.JwtTokenPair;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.oak.member.constant.MemberCacheKeyConstant.LOGIN_REFRESH_TOKEN;
import static com.oak.member.constant.MemberCacheKeyConstant.LOGIN_TOKEN_VALID_HOUR;

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

    @Autowired
    private MemberAccountLogService memberAccountLogService;

    @Autowired
    private SettingValueHelper settingValueHelper;

    @Autowired
    private MemberTokenService tokenService;

    @Autowired(required=true)
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberSessionCacheHelper memberSessionCacheHelper;


    @Transactional(rollbackFor = Exception.class)
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
        if (!req.getNotPassword() && !StringUtils.hasText(req.getLoginPassword())) {
            return RestfulApiRespFactory.error("登陆密码不能为空");
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
        if (!rsp.isSuccess()) {
            return RestfulApiRespFactory.error(rsp.getMessage());
        }
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
        ApiResp<Long> accountRsp = accountService.create(accountReq);
        AssertThrow.assertResp(accountRsp);

        //会员安全信息
        CreateMemberSecureReq secureReq = new CreateMemberSecureReq();
        if (StringUtils.isEmpty(req.getLoginPassword())) {
            req.setLoginPassword(SnHelper.makeRandom(6));
        }
        secureReq.setId(memberId)
                .setLoginPassword(req.getLoginPassword())
                .setLoginPwdUpdateTime(new Date());
        ApiResp<Long> secureRsp = secureService.create(secureReq);
        AssertThrow.assertResp(secureRsp);

        if (req.getOpenType() != null) {
            //会员绑定信息
            CreateMemberOpenReq openReq = new CreateMemberOpenReq();
            openReq.setMemberId(memberId)
                    .setOpenId(req.getOpenId())
                    .setOpenType(req.getOpenType())
                    .setUnionId(req.getUnionId());
            ApiResp<Long> openRsp = openService.create(openReq);
            AssertThrow.assertResp(openRsp);
        }

        return RestfulApiRespFactory.ok(memberId);
    }

    @Override
    public ApiResp<Long> registerFromWx(RegisterMemberFromWxReq req) {
        ApiResp<WxMpOAuth2AccessToken> tokenResp = WxMaHelper.getOAuth2AccessToken(req.getCode());
        if (!tokenResp.isSuccess()) {
            return RestfulApiRespFactory.error(tokenResp.getMessage());
        }
        ApiResp<WxMpUser> userResp = WxMaHelper.getWxMpUserInfo(req.getCode());
        if (!userResp.isSuccess()) {
            return RestfulApiRespFactory.error(userResp.getMessage());
        }
        RegisterMemberReq registerMemberReq = new RegisterMemberReq();
        BeanUtils.copyProperties(req, registerMemberReq);
        registerMemberReq.setClientType(ClientType.MOBILE)
                .setOpenType(OpenType.WEIXIN)
                .setOpenId(userResp.getData().getOpenId())
                .setUserName(userResp.getData().getNickname())
                .setAvatarUrl(userResp.getData().getHeadImgUrl())
                .setNickName(userResp.getData().getNickname())
                .setUnionId(userResp.getData().getUnionId())
                .setGender(formatUserGender(userResp.getData().getSex()))
                .setNotPassword(Boolean.TRUE)
                .setMobileAuth(Boolean.FALSE)
                .setMemberType("会员")
                .setVerify(MemberVerifyStatus.APPROVED);

        return register(registerMemberReq);
    }

    @Override
    public ApiResp<Long> registerFromWxMa(RegisterMemberFromWxMaReq req) {
        //获取用户SessionKey和openId
        ApiResp<WxMaJscode2SessionResult> sessionResult = WxMaHelper.getWxMaSessionInfo(req.getCode());
        if (!sessionResult.isSuccess()) {
            return RestfulApiRespFactory.error(sessionResult.getMessage());
        }
        //解密用户信息
        ApiResp<WxMaUserInfo> userInfo = WxMaHelper.getWxMaUserInfo(sessionResult.getData().getSessionKey(), req.getUserEncryptedData(), req.getUserIvStr());
        //解密用户手机号
        ApiResp<WxMaPhoneNumberInfo> phoneNumberInfo = WxMaHelper.getWxMaPhoneNoInfo(sessionResult.getData().getSessionKey(), req.getEncryptedData(), req.getIvStr());

        RegisterMemberReq registerReq = new RegisterMemberReq();
        BeanUtils.copyProperties(req, registerReq);
        registerReq.setOpenType(OpenType.WEIXIN_MA)
                .setUserName(userInfo.getData().getNickName())
                .setOpenId(sessionResult.getData().getOpenid())
                .setUnionId(userInfo.getData().getUnionId())
                .setMobilePhone(phoneNumberInfo.getData().getPhoneNumber())
                .setNotPassword(Boolean.TRUE)
                .setMobileAuth(Boolean.FALSE)
                .setMemberType("会员")
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
        return RestfulApiRespFactory.created(accountInfo);
    }

    @Override
    public ApiResp<MemberLoginInfo> login(MemberLoginReq req) {
        MemberLoginInfo loginInfo = new MemberLoginInfo();
        //检查参数
        if (LoginModel.PASSWORD.equals(req.getLoginModel())) {
            if (StringUtils.isEmpty(req.getUserName()) && StringUtils.isEmpty(req.getMobilePhone())) {
                return RestfulApiRespFactory.error("登录账号或手机号没有获取到");
            }
        } else if (LoginModel.OPEN_ID.equals(req.getLoginModel())) {
            if (req.getOpenType() == null || StringUtils.isEmpty(req.getOpenId())) {
                return RestfulApiRespFactory.error("OPENID为空");
            }
        } else if (LoginModel.REFRESH_TOKEN.equals(req.getLoginModel())) {
            if (StringUtils.isEmpty(req.getToken())) {
                return RestfulApiRespFactory.error("TOKEN为空");
            }
        }

        //查找用户
        ApiResp<MemberInfo> checkResp = checkLoginMember(req);
        AssertThrow.assertResp(checkResp);

        //检查用户状态
        MemberInfo memberInfo = checkResp.getData();
        if (!memberInfo.getEnable()) {
            return RestfulApiRespFactory.error("用户已被禁用");
        } else if (!MemberVerifyStatus.APPROVED.equals(memberInfo.getVerify())) {
            return RestfulApiRespFactory.error("用户未审核");
        }

        if (!StringUtils.isEmpty(req.getMemberType())
                && !req.getMemberType().equals(memberInfo.getMemberType())) {
            return RestfulApiRespFactory.error("登录角色不一致，拒绝登录");
        }

        BeanUtils.copyProperties(memberInfo, loginInfo);

        //获取TOKEN
        MemberToken memberToken = saveLogin(req.getLoginModel(), memberInfo, req.getRemoteIp());
        loginInfo.setToken(memberToken.getToken());
        //TOKEN有效秒数
        loginInfo.setTokenExpireTimeSeconds((memberToken.getExpirationDate().getTime() - System.currentTimeMillis()) / 1000);

        memberSessionCacheHelper.join(memberToken.getToken(), loginInfo);
        return RestfulApiRespFactory.ok(loginInfo);
    }

    @Override
    public ApiResp checkPassword(CheckPasswordReq req) {
        MemberSecure memberSecure = jpaDao.find(MemberSecure.class, req.getUid());

        if (memberSecure == null) {
            return RestfulApiRespFactory.error("用户不存在");
        }

        boolean isCheck = settingValueHelper.getSettingValue(MemberCacheKeyConstant.MEMBER_LOGIN_FAIL_CHECK, false);
        if (isCheck) {
            //失败阀值控制
            String checkLoginFail = checkLoginFail(memberSecure.getId());
            if (checkLoginFail != null && !checkLoginFail.isEmpty()) {
                return RestfulApiRespFactory.error(checkLoginFail);
            }
        }

        try {

            if (!req.getPassword().equals(memberSecure.getLoginPassword())) {

                String msg = "登录密码错误！";
                if (isCheck) {
                    Integer count = secureService.addLoginFail(memberSecure.getId());
                    if (count <= 0) {
                        msg = "抱歉，您登录失败已达到5次，账号将被锁定1小时！";
                    } else if (count < 4) {
                        msg = "登录密码错误，今天还可尝试" + count + "次将被锁定！";
                    }
                }
                return RestfulApiRespFactory.error(msg);
            }
            secureService.resetLoginFail(req.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RestfulApiRespFactory.ok();
    }

    private ApiResp<MemberInfo> checkLoginMember(MemberLoginReq evt) {
        MemberInfo memberInfo = null;
        if (LoginModel.PASSWORD.equals(evt.getLoginModel())) {
            SelectDao<Member> memberSelectDao = jpaDao.selectFrom(Member.class);
            if (!StringUtils.isEmpty(evt.getMobilePhone())) {
                memberSelectDao.appendWhere(!StringUtils.isEmpty(evt.getMobilePhone()), "mobilePhone=?", evt.getMobilePhone());
            } else {
                memberSelectDao.appendWhere("(userName=? or mobilePhone=? or email=? or no=?)",
                        evt.getUserName(), evt.getUserName(), evt.getUserName(), evt.getUserName());
            }
            memberInfo = memberSelectDao
                    .findOne(MemberInfo.class);

            if (memberInfo == null) {
                return RestfulApiRespFactory.error("用户不存在");
            }

            //密码登陆
            ApiResp<String> checkResp = checkPassword(
                    new CheckPasswordReq(memberInfo.getId(), evt.getPassword()));
            AssertThrow.assertResp(checkResp);
        } else if (LoginModel.OPEN_ID.equals(evt.getLoginModel())) {
            List<OpenType> wxOpenTypes = new ArrayList<>();
            wxOpenTypes.add(OpenType.WEIXIN);
            wxOpenTypes.add(OpenType.WEIXIN_MA);
            wxOpenTypes.add(OpenType.WEIXIN_OPEN);
            //第三方快捷登录
            SelectDao<MemberOpen> selectDao = jpaDao.selectFrom(MemberOpen.class)
                    .appendWhere(!StringUtils.isEmpty(evt.getOpenId()) && !StringUtils.isEmpty(evt.getUnionId()),
                            "( openId = ? or unionId = ?)", evt.getOpenId(), evt.getUnionId())
                    .eq("openId", evt.getOpenId())
                    .eq("unionId", evt.getUnionId());

            if (wxOpenTypes.contains(evt.getOpenType())) {
                selectDao.in("openType", wxOpenTypes.toArray());
            } else {
                selectDao.eq("openType", evt.getOpenType());
            }

            MemberOpen memberOpen = selectDao.findOne();

            if (memberOpen == null) {
                return RestfulApiRespFactory.error("未绑定第三方平台");
            }

            if (memberOpen.getExpirationDate() != null
                    && memberOpen.getExpirationDate().getTime() < System.currentTimeMillis()) {
                return RestfulApiRespFactory.error("第三方平台登录凭证已失效");
            }

            // 同步openId和unionId
            if (!StringUtils.isEmpty(evt.getOpenId())) {
                int update = jpaDao.updateTo(MemberOpen.class)
                        .eq("memberId", memberOpen.getMemberId())
                        .isNull("openId")
                        .eq("openType", evt.getOpenType())
                        .appendColumn("openId", evt.getOpenId())
                        .appendColumn("updateTime", new Date())
                        .update();
                log.debug("【" + memberOpen.getOpenType().getDesc() + "】" +
                        "[" + update + "]更新openId=" + evt.getOpenId());
            }

            if (!StringUtils.isEmpty(evt.getUnionId())) {
                int update = jpaDao.updateTo(MemberOpen.class)
                        .eq("memberId", memberOpen.getMemberId())
                        .isNull("unionId")
                        .eq("openType", evt.getOpenType())
                        .appendColumn("unionId", evt.getUnionId())
                        .appendColumn("updateTime", new Date())
                        .update();
                log.debug("【" + memberOpen.getOpenType().getDesc() + "】" +
                        "[" + update + "]更新openId=" + evt.getOpenId());
            }

            memberInfo = memberService.findById(memberOpen.getMemberId());
        } else if (LoginModel.REFRESH_TOKEN.equals(evt.getLoginModel())) {
            MemberToken memberToken = jpaDao.selectFrom(MemberToken.class)
                    .eq("token", evt.getToken()).findOne();

            if (memberToken == null
                    || (memberToken.getExpirationDate() != null
                    && memberToken.getExpirationDate().getTime() < System.currentTimeMillis())) {

                return RestfulApiRespFactory.error("登录凭证不存在或已过期");
            }

            memberInfo = memberService.findById(memberToken.getId());
        }

        if (memberInfo == null) {
            return RestfulApiRespFactory.error("用户不存在");
        }

        memberInfo.setMemberSecureInfo(null);
        return RestfulApiRespFactory.ok(memberInfo);
    }

    private String checkLoginFail(Long uid) {
        LoginFail loginFail = secureService.findLoginFail(uid);
        long currentMillis = System.currentTimeMillis();
        if (loginFail != null) {

            if (loginFail.getFailTime() != null && loginFail.getFailTime() < currentMillis - (24 * 3600 * 1000L)) {
                //密码输错时间超过一小时
                loginFail.setFailTime(null);
                loginFail.setCount(0);

                secureService.saveLoginFail(uid, loginFail);
            } else {

                if (loginFail.getLockTime() != null && loginFail.getCount() >= 5) {
                    //在一小时内
                    if (loginFail.getLockTime() > currentMillis - (3600 * 1000L)) {
                        return "抱歉，您登录失败已达到5次，账号将被锁定1小时！请稍候再试";
                    } else {
                        loginFail.setLockTime(null);
                        loginFail.setCount(0);
                        secureService.saveLoginFail(uid, loginFail);
                    }
                }
            }
        }
        return null;
    }

    public Gender formatUserGender(Integer wxSex) {
        switch (wxSex) {
            case 1:
                return Gender.MAN;
            case 2:
                return Gender.WOMEN;
            default:
                return Gender.SECRET;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResp<Void> recharge(RechargeReq req) {
        MemberAccountInfo accountInfo = accountService.findById(req.getId());
        EditMemberAccountReq editMemberAccountReq = new EditMemberAccountReq(accountInfo.getId());
        editMemberAccountReq.setMoney(accountInfo.getMoney() + req.getAmount());
        ApiResp<Void> editResp = accountService.edit(editMemberAccountReq);
        AssertThrow.assertFalse(editResp.getMessage(), editResp.isSuccess());
        //会员账户信息日志入库
        CreateMemberAccountLogReq accountLogReq = new CreateMemberAccountLogReq();
        accountLogReq.setMemberId(accountInfo.getId())
                .setMoney(req.getAmount())
                .setCurrMoney(editMemberAccountReq.getMoney())
                .setFrozenMoney(0)
                .setCurrFrozenMoney(accountInfo.getFrozenMoney())
                .setStatus(AccountStatus.AVAILABLE)
                .setDescription("充值余额")
                .setOrderSn(req.getOrderSn())
                .setType(AccountLogType.RECHARGE.name());
        memberAccountLogService.create(accountLogReq);
        return RestfulApiRespFactory.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResp<Void> deductMoney(DeductMoneyReq req) {
        MemberAccountInfo accountInfo = accountService.findById(req.getId());
        AssertThrow.assertTrue("余额不足", accountInfo.getMoney() < req.getAmount());
        EditMemberAccountReq editMemberAccountReq = new EditMemberAccountReq(accountInfo.getId());
        editMemberAccountReq.setMoney(accountInfo.getMoney() - req.getAmount());
        ApiResp<Void> editResp = accountService.edit(editMemberAccountReq);
        AssertThrow.assertFalse(editResp.getMessage(), editResp.isSuccess());
        //会员账户信息日志入库
        CreateMemberAccountLogReq accountLogReq = new CreateMemberAccountLogReq();
        accountLogReq.setMemberId(accountInfo.getId())
                .setMoney(-req.getAmount())
                .setCurrMoney(editMemberAccountReq.getMoney())
                .setFrozenMoney(0)
                .setCurrFrozenMoney(accountInfo.getFrozenMoney())
                .setStatus(AccountStatus.AVAILABLE)
                .setDescription(req.getReason())
                .setOrderSn(req.getOrderSn())
                .setType(AccountLogType.DEDUCT.name());
        memberAccountLogService.create(accountLogReq);
        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> freezeMoney(FreezeMoneyReq req) {
        MemberAccountInfo accountInfo = accountService.findById(req.getId());
        AssertThrow.assertTrue("余额不足", accountInfo.getMoney() < req.getAmount());
        EditMemberAccountReq editMemberAccountReq = new EditMemberAccountReq(accountInfo.getId());
        editMemberAccountReq.setMoney(accountInfo.getMoney() - req.getAmount())
                .setFrozenMoney(accountInfo.getFrozenMoney() + req.getAmount());
        ApiResp<Void> editResp = accountService.edit(editMemberAccountReq);
        AssertThrow.assertFalse(editResp.getMessage(), editResp.isSuccess());
        //会员账户信息日志入库
        CreateMemberAccountLogReq accountLogReq = new CreateMemberAccountLogReq();
        accountLogReq.setMemberId(accountInfo.getId())
                .setMoney(-req.getAmount())
                .setCurrMoney(editMemberAccountReq.getMoney())
                .setFrozenMoney(req.getAmount())
                .setCurrFrozenMoney(editMemberAccountReq.getFrozenMoney())
                .setStatus(AccountStatus.AVAILABLE)
                .setDescription(req.getReason())
                .setOrderSn(req.getOrderSn())
                .setType(AccountLogType.FREEZE.name());
        memberAccountLogService.create(accountLogReq);
        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> unfreezeMoney(UnfreezeMoneyReq req) {
        MemberAccountInfo accountInfo = accountService.findById(req.getId());
        AssertThrow.assertTrue("冻结余额不足", accountInfo.getFrozenMoney() < req.getAmount());
        EditMemberAccountReq editMemberAccountReq = new EditMemberAccountReq(accountInfo.getId());
        editMemberAccountReq.setMoney(accountInfo.getMoney() + req.getAmount())
                .setFrozenMoney(accountInfo.getFrozenMoney() - req.getAmount());
        ApiResp<Void> editResp = accountService.edit(editMemberAccountReq);
        AssertThrow.assertFalse(editResp.getMessage(), editResp.isSuccess());
        //会员账户信息日志入库
        CreateMemberAccountLogReq accountLogReq = new CreateMemberAccountLogReq();
        accountLogReq.setMemberId(accountInfo.getId())
                .setMoney(req.getAmount())
                .setCurrMoney(editMemberAccountReq.getMoney())
                .setFrozenMoney(-req.getAmount())
                .setCurrFrozenMoney(editMemberAccountReq.getFrozenMoney())
                .setStatus(AccountStatus.AVAILABLE)
                .setDescription(req.getReason())
                .setOrderSn(req.getOrderSn())
                .setType(AccountLogType.UNFREEZE.name());
        memberAccountLogService.create(accountLogReq);
        return RestfulApiRespFactory.ok();
    }



    private MemberToken saveLogin(LoginModel loginModel, MemberInfo memberInfo, String ip) {
        //记录用户日志
        MemberLog log = new MemberLog();
        log.setMemberId(memberInfo.getId());
        log.setType( loginModel.name() + " 登录");
        log.setOperator(memberInfo.getUserName() == null ? memberInfo.getMobilePhone() : memberInfo.getUserName());
        log.setOperatingTime(new Date());
        log.setShowName(!StringUtils.isEmpty(memberInfo.getNickName()) ? memberInfo.getNickName() : memberInfo.getUserName());
        log.setDescription("登录成功");
        log.setIp(ip);
        jpaDao.create(log);

        //更新最近一次上线时间
        jpaDao.updateTo(MemberSecure.class)
                .appendColumn(E_MemberSecure.lastLoginFailLockTime, new Date())
                .eq(E_MemberSecure.id, memberInfo.getId())
                .update();

        //生成token
        String token = jwtTokenProvider.generateAccessToken(memberInfo.getId() + memberInfo.getNo() + memberInfo.getRegDateTime().getTime() + System.currentTimeMillis())
                .getToken();

        MemberToken memberToken = jpaDao.find(MemberToken.class, memberInfo.getId());
        boolean isNew = false;
        String oldToken = null;
        if (memberToken == null) {
            memberToken = new MemberToken();
            memberToken.setId(memberInfo.getId()).setToken(token);
            isNew = true;
        } else {
            oldToken = memberToken.getToken();
        }
        if (settingValueHelper.getSettingValue(LOGIN_REFRESH_TOKEN, false)) {
            memberToken.setToken(token);
        }
        memberToken.setLoginTime(new Date());

        //有效小时数
        Integer tokenHour = Integer.valueOf(settingValueHelper.getSettingValue(LOGIN_TOKEN_VALID_HOUR, 24));
        memberToken.setExpirationDate(new Date(memberToken.getLoginTime().getTime() + tokenHour * 3600 * 1000L));

        if (isNew) {
            jpaDao.create(memberToken);
        } else {
            jpaDao.save(memberToken);
        }
        return memberToken;
    }

    @Override
    public ApiResp<CheckMobilePhoneAndOpenIdInfo> checkMobilePhoneAndOpenIdWxMa(CheckMobilePhoneAndOpenIdWxMaReq req) {
        CheckMobilePhoneAndOpenIdInfo result = new CheckMobilePhoneAndOpenIdInfo();

        //解密数据得到手机号
        ApiResp<WxMaPhoneNumberInfo> getMobilePhoneResp = WxMaHelper.getWxMaPhoneNoInfo(req.getSessionKey(), req.getEncryptedData(), req.getIvStr());
        AssertThrow.assertResp(getMobilePhoneResp);

        String mobilePhone = getMobilePhoneResp.getData().getPhoneNumber();
        result.setMobilePhone(mobilePhone);

        //手机号是否已注册
        CheckMemberReq checkMemberEvt = new CheckMemberReq();
        checkMemberEvt.setMobilePhone(mobilePhone);
        ApiResp<Long> checkMember = memberService.checkMember(checkMemberEvt);
        result.setRegisted(checkMember.getData() != null);

        if (Boolean.TRUE.equals(result.getRegisted())) {
            Long memberId = checkMember.getData();
            CheckBindOpenReq checkBindOpenEvt = new CheckBindOpenReq();
            checkBindOpenEvt.setOpenType(OpenType.WEIXIN_MA);
            checkBindOpenEvt.setMemberId(memberId);
            ApiResp<Boolean> checkBindOpenResp = openService.checkBindOpen(checkBindOpenEvt);
            result.setMobBindOpen(checkBindOpenResp.getData());
        }

        //是否已绑定OPEIN
        CheckBindOpenReq checkBindOpenEvt = new CheckBindOpenReq();
        BeanUtils.copyProperties(req, checkBindOpenEvt);
        checkBindOpenEvt.setOpenType(OpenType.WEIXIN_MA);
        ApiResp<Boolean> checkBindOpenResp = openService.checkBindOpen(checkBindOpenEvt);
        result.setBindOpen(checkBindOpenResp.getData());

        return RestfulApiRespFactory.ok(result);
    }

    @Override
    public ApiResp<MemberTokenInfo> refreshMemberToken(RefreshMemberTokenReq req) {
        MemberToken memberToken = jpaDao.selectFrom(MemberToken.class)
                .eq("token", req.getToken()).findOne();

        if (memberToken == null
                || (memberToken.getExpirationDate() != null
                && memberToken.getExpirationDate().getTime() < System.currentTimeMillis())) {

            return RestfulApiRespFactory.error("登录凭证不存在或已过期");
        }

        MemberInfo memberInfo = memberService.findById(memberToken.getId());

        //生成token
        JwtTokenPair.JwtTokenPayLoad token = jwtTokenProvider.generateAccessToken(memberToken.getId() + memberInfo.getUserName()
                + memberInfo.getRegDateTime().getTime() + System.currentTimeMillis());
        if (settingValueHelper.getSettingValue(LOGIN_REFRESH_TOKEN, false).equals(true)) {
            memberToken.setToken(token.getToken());
        }
        memberToken.setLoginTime(new Date());

        //有效小时数
        Integer tokenHour = Integer.valueOf(settingValueHelper.getSettingValue(LOGIN_TOKEN_VALID_HOUR, 24));
        memberToken.setExpirationDate(new Date(memberToken.getLoginTime().getTime() + tokenHour * 3600 * 1000L));
        jpaDao.save(memberToken);

        MemberTokenInfo memberTokenInfo = new MemberTokenInfo();
        BeanUtils.copyProperties(memberToken, memberTokenInfo);

        return RestfulApiRespFactory.ok(memberTokenInfo);
    }
}
