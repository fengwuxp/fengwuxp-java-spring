package com.wuxp.miniprogram.services.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wuxp.miniprogram.services.bo.WxResultBaseBO;
import com.wuxp.miniprogram.services.dto.CodeCommitDTO;
import com.wuxp.miniprogram.services.dto.OnekeyUploadCodeDTO;
import com.wuxp.miniprogram.services.dto.SubmitAuditMessageDTO;
import com.wuxp.miniprogram.services.service.IWechatOpenService;
import com.wuxp.miniprogram.services.utils.BeanWrap;
import com.wuxp.miniprogram.services.utils.WxOpenUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.error.WxMaErrorMsgEnum;
import me.chanjar.weixin.open.api.WxOpenMaService;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.bean.message.WxOpenMaSubmitAuditMessage;
import me.chanjar.weixin.open.bean.result.WxOpenMaSubmitAuditResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname WechatOpenServiceImpl
 * @Description TODO
 * @Date 2020/3/17 19:42
 * @Created by 44487
 */

@Service
public class WechatOpenServiceImpl implements IWechatOpenService {

    private static final Logger log = LoggerFactory.getLogger(WechatOpenServiceImpl.class);
    @Autowired
    private WxOpenService wxOpenService;

    public WechatOpenServiceImpl() {
    }

    @Override
    public String getAccessTokenByAppId(String appId) throws WxErrorException {
        return this.wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(appId, false);
    }

    @Override
    public WxOpenMaSubmitAuditResult oneKeyUploadCode(OnekeyUploadCodeDTO onekeyUploadCode) throws WxErrorException {
        WxOpenMaService wxOpenMaService = WxOpenUtils.getWxOpenMaService(onekeyUploadCode.getAppid());
        wxOpenMaService.modifyDomain("set", onekeyUploadCode.getRequestdomain(), onekeyUploadCode.getWsrequestdomain(), onekeyUploadCode.getUploaddomain(), onekeyUploadCode.getDownloaddomain());
        wxOpenMaService.setWebViewDomain("set", onekeyUploadCode.getWebviewdomain());
        CodeCommitDTO codeCommitDTO = onekeyUploadCode.getCodeCommit();
        Map<String, Object> parmas = new HashMap();
        parmas.put("template_id", codeCommitDTO.getTemplateId());
        JSONObject jsonObject = JSON.parseObject(codeCommitDTO.getExtJson());
        jsonObject.put("extAppid", onekeyUploadCode.getAppid());
        parmas.put("ext_json", JSON.toJSONString(jsonObject));
        parmas.put("user_version", codeCommitDTO.getUserVersion());
        parmas.put("user_desc", codeCommitDTO.getUserDesc());
        String response = wxOpenMaService.post("https://api.weixin.qq.com/wxa/commit", JSON.toJSONString(parmas));
        log.info("上传小程序代码", response);
        WxResultBaseBO wxresultbasebo = (WxResultBaseBO) BeanWrap.toBean(response, WxResultBaseBO.class);
        if (wxresultbasebo.getErrcode() != 0) {
            String msg = WxMaErrorMsgEnum.findMsgByCode(wxresultbasebo.getErrcode());
            throw new RuntimeException(msg);
        } else {
            SubmitAuditMessageDTO submitAuditMessageDTO = onekeyUploadCode.getSubmitAuditMessage();
            WxOpenMaSubmitAuditMessage auditMessage = new WxOpenMaSubmitAuditMessage();
            auditMessage.setItemList(submitAuditMessageDTO.getItemList());
            return wxOpenMaService.submitAudit(auditMessage);
        }
    }

}
