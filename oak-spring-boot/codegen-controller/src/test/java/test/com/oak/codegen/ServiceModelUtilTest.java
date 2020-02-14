package test.com.oak.codegen;

import com.oak.codegen.controller.ServiceModelUtil;
import org.junit.Test;
import test.com.oak.codegen.entites.ExampleEntity;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ServiceModelUtilTest {


    @Test
    public void testCodegenService() throws Exception {
        String[] outPaths = {"codegen-result", "src", "main", "java"};
        String targetFilePath = Paths.get(System.getProperty("user.dir")).resolveSibling(String.join(File.separator, outPaths)).toString();

        File file = new File(targetFilePath);
        if (file.exists()) {
            file.delete();
        }

        String basePackageName = "com.oak.codegen.example";

        Map<String, Class> entityMapping = new HashMap<>();
        ServiceModelUtil.entity2ServiceModel(ExampleEntity.class, entityMapping, basePackageName, targetFilePath);
    }
}
