//package test.com.fengwuxp.multiple.mp.bean;
//
//import lombok.extern.slf4j.Slf4j;
//import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
//import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Slf4j
//public class WxMpSessionScopeServiceImpl extends WxMpServiceImpl implements InitializingBean {
//
//    public WxMpSessionScopeServiceImpl() {
//
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (requestAttributes == null) {
//            throw new RuntimeException("requestAttributes is null");
//        }
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        WxMpDefaultConfigImpl wxConfigProvider = new WxMpDefaultConfigImpl();
//        String appId = request.getParameter("appId");
//        wxConfigProvider.setAppId(appId);
//        log.info("创建wx map service {}", appId);
//        this.setWxMpConfigStorage(wxConfigProvider);
//
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        log.info("afterPropertiesSet wx map service");
//    }
//}
