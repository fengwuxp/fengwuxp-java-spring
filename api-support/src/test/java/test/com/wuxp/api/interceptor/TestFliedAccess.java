package test.com.wuxp.api.interceptor;


import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestFliedAccess {


    @Test
    public void testFileAccess() {

        int count = 100000; //Integer.MAX_VALUE;
        int max = 100000;
        TestDTO testDTO = new TestDTO();

        this.asmTest(testDTO, count, max);
        this.reflectTest(testDTO, count, max);

    }

    private void asmTest(TestDTO testDTO, int count, int max) {

        List<Long> longs = new ArrayList<>(max);
        MethodAccess methodAccess = MethodAccess.get(TestDTO.class);
        FieldAccess fieldAccess = FieldAccess.get(TestDTO.class);
        int size = max;
        while (size-- > 0) {
            long begin = System.nanoTime();
            for (int i = 0; i < count; i++) {
//            methodAccess.invoke(testDTO, "setName", i + "");
                fieldAccess.set(testDTO, "name", i + "");
            }
            long end = System.nanoTime();
            long o = end - begin;
            longs.add(o);
        }
        Long aLong = longs.stream().reduce((prv, next) -> prv + next).get();
        long totalCount = max * count;
        double o1 = aLong / max * count;
        log.info("执行{}次，共使用时间 {},平均每次花费 {}", totalCount, aLong, o1);
    }

    private void reflectTest(TestDTO testDTO, int count, int max) {

        Field[] declaredFields = TestDTO.class.getDeclaredFields();
        try {
            Field.setAccessible(declaredFields, true);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        List<Long> longs = new ArrayList<>(max);
        int size = max;
        while (size-- > 0) {
            long begin = System.nanoTime();
            for (int i = 0; i < count; i++) {
                ReflectionUtils.setField(declaredFields[0], testDTO, i + "");
            }
            long end = System.nanoTime();
            long o = end - begin;
            longs.add(o);
        }
        Long aLong = longs.stream().reduce((prv, next) -> prv + next).get();
        long totalCount = max * count;
        double o1 = aLong / max * count;
        log.info("执行{}次，共使用时间 {},平均每次花费 {}", totalCount, aLong, o1);
    }


    @Data
    public static class TestDTO {

        protected String name;
    }
}
