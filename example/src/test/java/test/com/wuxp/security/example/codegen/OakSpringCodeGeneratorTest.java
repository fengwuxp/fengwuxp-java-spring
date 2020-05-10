package test.com.wuxp.security.example.codegen;


import com.oak.cms.services.articleaction.req.QueryArticleActionReq;
import com.oak.codegen.controller.ServiceModelUtil;
import com.wuxp.api.ApiRequest;
import com.wuxp.security.example.entities.ExampleEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class OakSpringCodeGeneratorTest {

    @Test
    public void testCodeGenApiByStater() throws Exception {


        String[] outPaths = {"codegen-result", "src", "main", "java"};
        String targetFilePath = Paths.get(System.getProperty("user.dir")).resolveSibling(String.join(File.separator, outPaths)).toString();

        File file = new File(targetFilePath);
        if (file.exists()) {
            boolean delete = file.delete();
            log.debug("删除原有的输出目录{}", delete);
        }

        String basePackageName = "com.wuxp.security.example.services.simple";

        Map<String, Class> entityMapping = new HashMap<>();
//        entityMapping.put("organization", ExampleEntity.class);
        ServiceModelUtil.entity2ServiceModel(ExampleEntity.class, entityMapping, basePackageName, targetFilePath);

    }

    @Test
    public void testA(){
        String format = ApiRequest.class.isAssignableFrom(QueryArticleActionReq.class) + "";
        System.out.printf(format);
    }
}
