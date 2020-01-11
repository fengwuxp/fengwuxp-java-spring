//package test.com.wuxp.security.example.codegen;
//
//
//import com.oak.codegen.builder.OakSpringCodegenBuilder;
//import com.wuxp.codegen.dragon.strategy.JavaPackageMapStrategy;
//import com.wuxp.codegen.enums.CodeRuntimePlatform;
//import com.wuxp.codegen.model.LanguageDescription;
//import org.junit.Test;
//
//import java.io.File;
//import java.nio.file.Paths;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//
//public class OakSpringCodeGeneratorTest {
//
//    @Test
//    public void testCodeGenApiByStater() {
//
//
//        //包名映射关系
//        Map<String, String> packageMap = new LinkedHashMap<>();
//
//        //实体所在的包
//        packageMap.put("com.wuxp.security.example.entities", "com.wuxp.example");
//        String language = LanguageDescription.JAVA_ANDROID.getTemplateDir();
//        String[] outPaths = {"codegen-result", language.toLowerCase(), "src"};
//
//        //要进行生成的源代码包名列表
//        String[] packagePaths = {"com.oaknt.codegen.feignexample.services"};
//
//        OakSpringCodegenBuilder.builder()
//                .build()
//                .codeRuntimePlatform(CodeRuntimePlatform.ANDROID)
//                .languageDescription(LanguageDescription.JAVA_ANDROID)
//                .packageMapStrategy(new JavaPackageMapStrategy(packageMap, "com.wuxp.example"))
//                .outPath(Paths.get(System.getProperty("user.dir")).resolveSibling(String.join(File.separator, outPaths)).toString())
//                .scanPackages(packagePaths)
//                .buildCodeGenerator()
//                .generate();
//
//    }
//}
