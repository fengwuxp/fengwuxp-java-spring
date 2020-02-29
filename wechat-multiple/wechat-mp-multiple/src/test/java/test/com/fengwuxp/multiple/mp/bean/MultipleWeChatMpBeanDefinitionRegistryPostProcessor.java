//package test.com.fengwuxp.multiple.mp.bean;
//
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import me.chanjar.weixin.mp.api.WxMpService;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.web.context.request.RequestAttributes.REFERENCE_SESSION;
//
//
///**
// * 用于注入WxMpService的 代理实现
// */
//@Slf4j
//@Setter
//public class MultipleWeChatMpBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
//
//
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//
//        Class<WxMpService> wxMpServiceClass = WxMpService.class;
//        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(wxMpServiceClass);
//        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//
//        //在这里，我们可以给该对象的属性注入对应的实例。
//        //比如mybatis，就在这里注入了dataSource和sqlSessionFactory，
//        // 注意，如果采用definition.getPropertyValues()方式的话，
//        // 类似definition.getPropertyValues().add("interfaceType", beanClazz);
//        // 则要求在FactoryBean提供setter方法，否则会注入失败
//        // 如果采用definition.getConstructorArgumentValues()，
//        // 则FactoryBean中需要提供包含该属性的构造方法，否则会注入失败
////        definition.getConstructorArgumentValues().addGenericArgumentValue(wxMpServiceClass);
//
//        //注意，这里的BeanClass是生成Bean实例的工厂，不是Bean本身。
//        // FactoryBean是一种特殊的Bean，其返回的对象不是指定类的一个实例，
//        // 其返回的是该工厂Bean的getObject方法所返回的对象。
//        definition.setBeanClass(WxMapServiceFactoryBean.class);
////        definition.setScope(WebApplicationContext.SCOPE_SESSION);
////        definition.setScope(WebApplicationContext.SCOPE_REQUEST);
//        //这里采用的是byType方式注入，类似的还有byName等
//        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
//        registry.registerBeanDefinition(wxMpServiceClass.getSimpleName(), definition);
//    }
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//
//    }
//
//
//}
