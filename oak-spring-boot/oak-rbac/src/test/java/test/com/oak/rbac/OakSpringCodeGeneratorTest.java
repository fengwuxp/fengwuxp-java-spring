package test.com.oak.rbac;


import com.oak.codegen.ServiceModelUtil;
import com.oak.rbac.entities.Menu;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

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

        String basePackageName = "com.oak.rbac.services.menu";

        Map<String, Class> entityMapping = new HashMap<>();
        ServiceModelUtil.entity2ServiceModel(Menu.class, entityMapping, basePackageName, targetFilePath);
    }

    @Test
    public void testAntMatch() {
        PathMatcher antPathMatcher = new AntPathMatcher();

        boolean match = antPathMatcher.match("/src/pages/**/*.less", "/src/pages/demo/js/style.less");
        log.debug("{}", match);
    }
}
