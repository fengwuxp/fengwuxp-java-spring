package test.com.oak.rbac;



import com.oak.codegen.ServiceModelUtil;
import com.oak.rbac.entities.OakAdminUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OakSpringCodeGeneratorTest {

    @Test
    public void testCodegenService() throws Exception {
        String[] outPaths = {"codegen-result", "src", "main", "java"};
        String targetFilePath = Paths.get(System.getProperty("user.dir")).resolveSibling(String.join(File.separator, outPaths)).toString();

        File file = new File(targetFilePath);
        if (file.exists()) {
            boolean delete = file.delete();
            log.debug("删除原有的输出目录{}", delete);
        }

        String basePackageName = "com.oak.rbac.services.user";

        Map<String, Class> entityMapping = new HashMap<>();
        ServiceModelUtil.entity2ServiceModel(OakAdminUser.class, entityMapping, basePackageName, targetFilePath);
    }
}