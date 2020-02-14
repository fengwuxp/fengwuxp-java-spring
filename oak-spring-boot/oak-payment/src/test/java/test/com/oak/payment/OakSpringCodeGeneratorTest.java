package test.com.oak.payment;


import com.oak.codegen.controller.ServiceModelUtil;
import com.oak.payment.entities.Payment;
import com.oak.payment.entities.PaymentOrder;
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

        Map<String, Class> entityMapping = new HashMap<>();

        //payment 生成
      //  entityMapping.put("payment", Payment.class);
      //  String basePackageName = "com.oak.payment.services.payment";
      //  ServiceModelUtil.entity2ServiceModel(Payment.class, entityMapping, basePackageName, targetFilePath);

        //paymentOrder 生成
        entityMapping.put("paymentorder", Payment.class);
        String basePackageName = "com.oak.payment.services.paymentorder";
        ServiceModelUtil.entity2ServiceModel(PaymentOrder.class, entityMapping, basePackageName, targetFilePath);


    }
}
