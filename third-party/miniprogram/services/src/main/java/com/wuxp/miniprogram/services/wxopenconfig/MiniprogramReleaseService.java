package com.wuxp.miniprogram.services.wxopenconfig;

import com.alibaba.fastjson.JSON;
import com.oak.organization.miniprogram.services.organizationminiprogramconfig.info.OrganizationMiniProgramConfigInfo;
import com.vma.wechatopen.gateway.config.WxOpenConfiguration;
import com.vma.wechatopen.gateway.dto.wechat.mini.code.CodeCommitDTO;
import com.vma.wechatopen.gateway.dto.wechat.mini.code.OnekeyUploadCodeDTO;
import com.vma.wechatopen.gateway.dto.wechat.mini.code.SubmitAuditMessageDTO;
import com.vma.wechatopen.service.business.service.IWechatOpenService;
import com.vma.wechatopen.service.utils.WxOpenUtils;
import com.wuxp.api.ApiResp;
import com.wuxp.api.context.InjectField;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.miniprogram.services.constant.WxOpenConfigConstant;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.OrganizationMiniProgramConfigService;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.QueryOrganizationMiniProgramConfigReq;
import com.wuxp.miniprogram.services.wxopenconfig.dto.MiniProgramQueryAuditResultDto;
import com.wuxp.miniprogram.services.wxopenconfig.info.WxOpenConfigInfo;
import com.wuxp.miniprogram.services.wxopenconfig.req.EditWxOpenConfigReq;
import com.wuxp.miniprogram.services.wxopenconfig.req.QueryWxOpenConfigReq;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenMaService;
import me.chanjar.weixin.open.bean.ma.WxOpenMaCategory;
import me.chanjar.weixin.open.bean.ma.WxOpenMaSubmitAudit;
import me.chanjar.weixin.open.bean.result.WxOpenMaCategoryListResult;
import me.chanjar.weixin.open.bean.result.WxOpenMaQueryAuditResult;
import me.chanjar.weixin.open.bean.result.WxOpenMaSubmitAuditResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Classname MiniprogramRelase
 * @Description 微信小程序第三方发布
 * @Date 2020/3/2 17:35
 * @Created by 44487
 */
@Service
@Slf4j
public class MiniprogramReleaseService {

    @Autowired
    private WxOpenConfigService wxOpenConfigService;

    @Autowired
    private OrganizationMiniProgramConfigService organizationMiniProgramConfigService;

    @Autowired
    private IWechatOpenService wechatOpenService;

    /**
     * 发布小程序
     * @param organizationId
     * @return
     */
    public ApiResp<String> miniProgramRelease( @InjectField(value = WxOpenConfigConstant.WXCONFIG_ORGANIZATION_ID_KEY) String organizationId ) throws WxErrorException {

        // 获取单个小程序配置对象
        ApiResp<WxOpenConfigInfo> wxOpenConfigInfoApiResp = getWxOpenConfigInfoById(organizationId);
        WxOpenConfigInfo wxOpenConfigInfo = null;
        if ( !wxOpenConfigInfoApiResp.isSuccess() ){
            return RestfulApiRespFactory.error(wxOpenConfigInfoApiResp.getMessage());
        }else {
            wxOpenConfigInfo = wxOpenConfigInfoApiResp.getData();
        }

        ApiResp< OrganizationMiniProgramConfigInfo> organizationMiniProgramConfigInfoApiResp = getOrganizationMiniProgramConfigInfoById( organizationId);
        OrganizationMiniProgramConfigInfo organizationMiniProgramConfigInfo = null;
        if( !organizationMiniProgramConfigInfoApiResp.isSuccess() ){
            return RestfulApiRespFactory.error(organizationMiniProgramConfigInfoApiResp.getMessage());
        }else {
            organizationMiniProgramConfigInfo = organizationMiniProgramConfigInfoApiResp.getData();
        }

        //设置唯一值
        WxOpenConfiguration.setDomain(organizationId);

        String templateId = organizationMiniProgramConfigInfo.getMiniProgramTemplateId();
        String extJson = organizationMiniProgramConfigInfo.getMiniProgramExtJson();
        String userVersion = organizationMiniProgramConfigInfo.getMiniProgramUserVersion();
        String userDesc = organizationMiniProgramConfigInfo.getMiniProgramUserDesc();
        String itemList = organizationMiniProgramConfigInfo.getMiniProgramItemList();

        List<String> requestDomainList = StringUtils.isBlank(organizationMiniProgramConfigInfo.getMiniProgramRequestdomain())?new ArrayList<>() : Arrays.asList(organizationMiniProgramConfigInfo.getMiniProgramRequestdomain().split(","));
        List<String> wsRequestDomainList = StringUtils.isBlank(organizationMiniProgramConfigInfo.getMiniProgramWsrequestdomain())?new ArrayList<>() : Arrays.asList(organizationMiniProgramConfigInfo.getMiniProgramWsrequestdomain().split(","));
        List<String> uploadDomainList = StringUtils.isBlank(organizationMiniProgramConfigInfo.getMiniProgramUploaddomain())?new ArrayList<>() : Arrays.asList(organizationMiniProgramConfigInfo.getMiniProgramUploaddomain().split(","));
        List<String> downDomainList = StringUtils.isBlank(organizationMiniProgramConfigInfo.getMiniProgramDownloaddomain())?new ArrayList<>() : Arrays.asList(organizationMiniProgramConfigInfo.getMiniProgramDownloaddomain().split(","));
        List<String> webviewDomainList = StringUtils.isBlank(organizationMiniProgramConfigInfo.getMiniProgramWebviewdomain())?new ArrayList<>() : Arrays.asList(organizationMiniProgramConfigInfo.getMiniProgramWebviewdomain().split(","));

        //构造“一键上传”对象
        OnekeyUploadCodeDTO onekeyUploadCodeDTO = new OnekeyUploadCodeDTO();
        onekeyUploadCodeDTO.setAppid(wxOpenConfigInfo.getMiniProgramAppId());
        onekeyUploadCodeDTO.setRequestdomain(requestDomainList);
        onekeyUploadCodeDTO.setWsrequestdomain(wsRequestDomainList);
        onekeyUploadCodeDTO.setUploaddomain(uploadDomainList);
        onekeyUploadCodeDTO.setDownloaddomain(downDomainList);
        onekeyUploadCodeDTO.setWebviewdomain(webviewDomainList);

        CodeCommitDTO codeCommitDTO = new CodeCommitDTO();
        codeCommitDTO.setAppid(wxOpenConfigInfo.getMiniProgramAppId());
        codeCommitDTO.setTemplateId(Long.valueOf(templateId));
        codeCommitDTO.setUserDesc(userDesc);
        codeCommitDTO.setUserVersion(userVersion);
        codeCommitDTO.setExtJson(extJson);

        onekeyUploadCodeDTO.setCodeCommit(codeCommitDTO);

        SubmitAuditMessageDTO submitAuditMessageDTO = new SubmitAuditMessageDTO();
        submitAuditMessageDTO.setAppid(wxOpenConfigInfo.getMiniProgramAppId());
        submitAuditMessageDTO.setItemList( JSON.parseArray(itemList, WxOpenMaSubmitAudit.class) );
        WxOpenMaCategoryListResult wxOpenMaCategoryListResult = WxOpenUtils.getWxOpenMaService(wxOpenConfigInfo.getMiniProgramAppId()).getCategoryList();
        if (wxOpenMaCategoryListResult.getCategoryList() == null && wxOpenMaCategoryListResult.getCategoryList().size() == 0) {
            return  RestfulApiRespFactory.error( "小程序类目信息未配置");
        }
        List<WxOpenMaCategory> wxOpenMaCategories = wxOpenMaCategoryListResult.getCategoryList();
        WxOpenMaCategory wxOpenMaCategory = wxOpenMaCategories.get(0);
        List<WxOpenMaSubmitAudit> submitAuditList = submitAuditMessageDTO.getItemList();
        WxOpenMaSubmitAudit wxOpenMaSubmitAudit = submitAuditList.get(0);
        wxOpenMaSubmitAudit.setFirstClass(wxOpenMaCategory.getFirstClass());
        wxOpenMaSubmitAudit.setFirstId(wxOpenMaCategory.getFirstId());
        wxOpenMaSubmitAudit.setSecondClass(wxOpenMaCategory.getSecondClass());
        wxOpenMaSubmitAudit.setSecondId(wxOpenMaCategory.getSecondId());

        onekeyUploadCodeDTO.setSubmitAuditMessage(submitAuditMessageDTO);

        try {
            WxOpenMaSubmitAuditResult auditResult = wechatOpenService.oneKeyUploadCode(onekeyUploadCodeDTO);

            EditWxOpenConfigReq editWxOpenConfigReq = new EditWxOpenConfigReq();
            editWxOpenConfigReq.setId(wxOpenConfigInfo.getId()).setMiniProgramAuditId(String.valueOf( auditResult.getAuditId()));
            wxOpenConfigService.edit(editWxOpenConfigReq);

            return RestfulApiRespFactory.ok("小程序发布成功");

        } catch (Exception e) {

            return RestfulApiRespFactory.error(e.getMessage());
        }
    }

    /**
     * 获取WxOpenConfig对象
     * @param organizationId
     * @return
     */
    public ApiResp<WxOpenConfigInfo> getWxOpenConfigInfoById( String organizationId ){

        QueryWxOpenConfigReq queryWxOpenConfigReq = new QueryWxOpenConfigReq();
        queryWxOpenConfigReq.setOrganizationId( Long.valueOf( organizationId ));

        // 获取配置对象
        WxOpenConfigInfo wxOpenConfigInfo = wxOpenConfigService.query(queryWxOpenConfigReq).getFirst();

        if( wxOpenConfigInfo == null ){
            return RestfulApiRespFactory.error("小程序配置内容为空");
        }else {
            return RestfulApiRespFactory.ok(wxOpenConfigInfo);
        }
    }

    /**
     * 获取服务商第三方配置
     * @param organizationId
     * @return
     */
    public ApiResp<OrganizationMiniProgramConfigInfo> getOrganizationMiniProgramConfigInfoById( String organizationId ){

        QueryOrganizationMiniProgramConfigReq queryOrganizationMiniProgramConfigReq = new QueryOrganizationMiniProgramConfigReq();
        queryOrganizationMiniProgramConfigReq.setOrganizationId( Long.valueOf(  organizationId));

        OrganizationMiniProgramConfigInfo organizationMiniProgramConfigInfo = organizationMiniProgramConfigService.query(queryOrganizationMiniProgramConfigReq).getFirst();

        if(  organizationMiniProgramConfigInfo == null ){
            return RestfulApiRespFactory.error("服务商第三方小程序发布配置内容为空");
        }else {
            return RestfulApiRespFactory.ok(organizationMiniProgramConfigInfo);
        }

    }

    /**
     * 指定一个服务商查询小程序审核结果
     * @param organizationId
     * @return
     */
    public ApiResp<MiniProgramQueryAuditResultDto> queryMiniProgramAuditResult(@InjectField(value = WxOpenConfigConstant.WXCONFIG_ORGANIZATION_ID_KEY) String organizationId ) throws WxErrorException, WxErrorException {

        ApiResp<WxOpenConfigInfo> wxOpenConfigInfoApiResp = getWxOpenConfigInfoById(organizationId);
        if ( !wxOpenConfigInfoApiResp.isSuccess() ){
            return RestfulApiRespFactory.error(wxOpenConfigInfoApiResp.getMessage());
        }
        WxOpenConfigInfo wxOpenConfigInfo = wxOpenConfigInfoApiResp.getData();

        if( StringUtils.isBlank( wxOpenConfigInfo.getMiniProgramAppId() ) ){
            return RestfulApiRespFactory.error("查询失败，小程序APPID为空");
        }
        if ( StringUtils.isBlank(wxOpenConfigInfo.getMiniProgramAuditId()) ) {
            return RestfulApiRespFactory.error("小程序未提交审核，小程序AuditID为空");
        }

        WxOpenMaService wxOpenMaService = WxOpenUtils.getWxOpenMaService(wxOpenConfigInfo.getMiniProgramAppId());
        WxOpenMaQueryAuditResult wxOpenMaQueryAuditResult = wxOpenMaService.getAuditStatus( Long.valueOf( wxOpenConfigInfo.getMiniProgramAuditId()));

        return RestfulApiRespFactory.ok( MiniProgramQueryAuditResultDto.convertFrom(wxOpenMaQueryAuditResult) );

    }

    /**
     * 指定一个服务商查询最新一次提交的审核状态
     * @return
     */
    public ApiResp<MiniProgramQueryAuditResultDto> queryMiniProgramAuditResultLastSubmit(@InjectField(value = WxOpenConfigConstant.WXCONFIG_ORGANIZATION_ID_KEY) String organizationId) throws WxErrorException {

        ApiResp<WxOpenConfigInfo> wxOpenConfigInfoApiResp = getWxOpenConfigInfoById(organizationId);
        if ( !wxOpenConfigInfoApiResp.isSuccess() ){
            return RestfulApiRespFactory.error(wxOpenConfigInfoApiResp.getMessage());
        }
        WxOpenConfigInfo wxOpenConfigInfo = wxOpenConfigInfoApiResp.getData();

        if( StringUtils.isBlank( wxOpenConfigInfo.getMiniProgramAppId() ) ){
            return RestfulApiRespFactory.error("查询失败，小程序APPID为空");
        }

        WxOpenMaService wxOpenMaService = WxOpenUtils.getWxOpenMaService(wxOpenConfigInfo.getMiniProgramAppId());
        WxOpenMaQueryAuditResult wxOpenMaQueryAuditResult = wxOpenMaService.getLatestAuditStatus();

        return RestfulApiRespFactory.ok( MiniProgramQueryAuditResultDto.convertFrom(wxOpenMaQueryAuditResult) );
    }





}
