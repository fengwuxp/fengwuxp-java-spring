package test.com.wuxp.security.example;

import com.oak.api.model.ApiBaseQueryReq;
import com.oak.api.model.ApiBaseReq;
import com.oak.api.model.PageInfo;
import com.oak.api.model.QueryReq;
import com.wuxp.api.AbstractApiReq;
import com.wuxp.api.ApiRequest;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.DefaultRestfulApiRespImpl;
import com.wuxp.api.signature.ApiSignatureRequest;
import com.wuxp.codegen.dragon.strategy.TypescriptPackageMapStrategy;
import com.wuxp.codegen.model.CommonCodeGenClassMeta;
import com.wuxp.codegen.model.LanguageDescription;
import com.wuxp.codegen.model.languages.dart.DartClassMeta;
import com.wuxp.codegen.swagger3.builder.Swagger3FeignDartCodegenBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

/**
 * 测试swagger 生成  dart的 feign api sdk
 */
@Slf4j
public class SwaggerFeignSdkCodegenDartTest {


    @Test
    public void testCodeGenDartApiSdk() {

        //设置基础数据类型的映射关系
        Map<Class<?>, CommonCodeGenClassMeta> baseTypeMapping = new HashMap<>();

        //自定义的类型映射
        Map<Class<?>, Class<?>[]> customTypeMapping = new HashMap<>();

        customTypeMapping.put(Pagination.class, new Class[]{PageInfo.class});
//        customTypeMapping.put(ApiResp.class, new Class[]{DefaultRestfulApiRespImpl.class});

        //包名映射关系
        Map<String, String> packageMap = new LinkedHashMap<>();

        //控制器的包所在
        packageMap.put("com.wuxp.security.**.controller", "clients");
        packageMap.put("com.oak.**.req", "req");
        packageMap.put("com.oak.**.info", "info");
        packageMap.put("com.oak.enums", "enums");
        packageMap.put("com.oak.api.model", "model");


        String language = LanguageDescription.DART.getName();
        String[] outPaths = {"codegen-result", language.toLowerCase(), "src", "feign"};

        //要进行生成的源代码包名列表
        String[] packagePaths = {"com.wuxp.security.example.controller.cms"};
        ;


        Map<String, Object> classNameTransformers = new HashMap<>();
        Set<String> ignorePackages = new HashSet<>();
        ignorePackages.add("org.springframework.");
        Map<Class<?>, List<String>> ignoreFields = new HashMap<Class<?>, List<String>>() {{
            put(ApiBaseQueryReq.class, Arrays.asList("orderByArr", "join"));
            put(PageInfo.class, Arrays.asList("empty"));
            put(DefaultRestfulApiRespImpl.class, Arrays.asList("httpStatus"));
        }};

        Map<DartClassMeta, List<String>> typeAlias = new HashMap<DartClassMeta, List<String>>() {{
            put(DartClassMeta.BUILT_LIST, Arrays.asList("PageInfo"));
        }};

        Swagger3FeignDartCodegenBuilder.builder()
                .ignoreFields(ignoreFields)
                .typeAlias(typeAlias)
                .baseTypeMapping(baseTypeMapping)
                .customJavaTypeMapping(customTypeMapping)
                .packageMapStrategy(new TypescriptPackageMapStrategy(packageMap, classNameTransformers, "FeignClient"))
                .outPath(Paths.get(System.getProperty("user.dir")).resolveSibling(String.join(File.separator, outPaths)).toString())
                .scanPackages(packagePaths)
                .includeClasses(new Class[]{PageInfo.class})
                .ignoreClasses(new Class[]{
                        ApiResp.class,
                        ApiRequest.class,
                        AbstractApiReq.class,
                        ApiSignatureRequest.class,
//                        ApiBaseQueryReq.class,
                        ApiBaseReq.class,
                        QueryReq.class
                })
                .ignorePackages(ignorePackages)
                .isDeletedOutputDirectory(true)
                .buildCodeGenerator()
                .generate();

    }


}
