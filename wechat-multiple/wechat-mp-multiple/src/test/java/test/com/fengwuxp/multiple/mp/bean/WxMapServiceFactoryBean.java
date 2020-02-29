//package test.com.fengwuxp.multiple.mp.bean;
//
//import com.fengwuxp.multiple.mp.WxMpServiceManager;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import me.chanjar.weixin.mp.api.WxMpService;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//import org.springframework.beans.factory.FactoryBean;
//
//import java.lang.reflect.Proxy;
//
//@Slf4j
//@Setter
//public class WxMapServiceFactoryBean implements FactoryBean<WxMpService>, BeanFactoryAware {
//
//
//    protected BeanFactory beanFactory;
//
//
//    @Override
//    public WxMpService getObject() throws Exception {
////        Class<?>[] interfaces = {WxMpService.class};
////        final WxMpServiceManager wxMpServiceManager = beanFactory.getBean(WxMpServiceManager.class);
////        return (WxMpService) Proxy.newProxyInstance(WxMpService.class.getClassLoader(), interfaces, (proxy, method, args) -> {
////            // 获取委托的实现
////            WxMpService delegateService = wxMpServiceManager.getWxMpService();
////            return method.invoke(delegateService, args);
////        });
//        final WxMpServiceManager wxMpServiceManager = beanFactory.getBean(WxMpServiceManager.class);
//        return wxMpServiceManager.getWxMpService();
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return WxMpService.class;
//    }
//
//    @Override
//    public boolean isSingleton() {
//        return true;
//    }
//
//
//}
