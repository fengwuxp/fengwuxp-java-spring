package test.com.wuxp.security.example.codegen;


import com.oak.codegen.ServiceModelUtil;
import com.wuxp.security.example.entities.ExampleEntity;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class OakSpringCodeGeneratorTest {

    @Test
    public void testCodeGenApiByStater() throws Exception {


        String basePackageName = "test.com.wuxp.security.example.codebuild";
        Map<String, Class> entityMapping = new HashMap<>();
        ServiceModelUtil.entity2ServiceModel(ExampleEntity.class, entityMapping, basePackageName, "d:\\temp");

    }
}
