package test.com.wuxp.security.example;

import com.oak.codegen.OakFeignTypescriptCodegenBuilder;
import com.wuxp.codegen.dragon.strategy.TypescriptPackageMapStrategy;
import com.wuxp.codegen.model.CommonCodeGenClassMeta;
import com.wuxp.codegen.model.LanguageDescription;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 测试swagger 生成  typescript的 feign api sdk
 */
@Slf4j
public class SwaggerFeignSdkCodegenTypescriptTest {


    private PathMatcher pathMatcher = new AntPathMatcher();

    @Test
    public void testAnt() {


        log.debug("{}", pathMatcher.match("com.wuxp.security.**.controller**", "com.wuxp.security.example.controller.simple"));
        log.debug("{}", pathMatcher.isPattern("com.wuxp.security.example.**.controller"));


    }

    @Test
    public void testCodeGenTypescriptApiByStater() {

        //设置基础数据类型的映射关系
        Map<Class<?>, CommonCodeGenClassMeta> baseTypeMapping = new HashMap<>();

        //包名映射关系
        Map<String, String> packageMap = new LinkedHashMap<>();

        //控制器的包所在
//        packageMap.put("com.wuxp.security.example", "");
        packageMap.put("com.wuxp.security.**.controller", "{0}services");
        packageMap.put("com.wuxp.security.example", "");
//        packageMap.put("com.wuxp.security.example.**.evt", "evt");
//        packageMap.put("com.wuxp.security.example.**.domain", "domain");
//        packageMap.put("com.wuxp.security.example.**.resp", "resp");
//        packageMap.put("com.wuxp.security.example.**.enums", "enums");
        //其他类（DTO、VO等）所在的包
//        packageMap.put("com.wuxp.security.example.example", "");

        String language = LanguageDescription.TYPESCRIPT.getName();
        String[] outPaths = {"codegen-result", language.toLowerCase(), "src", "api"};

        //要进行生成的源代码包名列表
        String[] packagePaths = {"com.wuxp.security.example.controller.simple"};


        Map<String, Object> classNameTransformers = new HashMap<>();

        OakFeignTypescriptCodegenBuilder.builder()
                .baseTypeMapping(baseTypeMapping)
                .languageDescription(LanguageDescription.TYPESCRIPT)
                .packageMapStrategy(new TypescriptPackageMapStrategy(packageMap, classNameTransformers))
                .outPath(Paths.get(System.getProperty("user.dir")).resolveSibling(String.join(File.separator, outPaths)).toString())
                .scanPackages(packagePaths)
                .isDeletedOutputDirectory(true)
                .buildCodeGenerator()
                .generate();

    }


}
