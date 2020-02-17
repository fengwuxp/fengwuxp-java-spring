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
    public void testFileAccess2() {

        for (int i = 0; i < 10; i++) {
            testFileAccess();
            log.info("==================>");
        }

    }

    @Test
    public void testFileAccess() {

        int count = 1; //Integer.MAX_VALUE;
        int max = count;
        TestDTO testDTO = new TestDTO();

        this.createConstStringVar(testDTO, count, max);
        this.filedTest(testDTO, count, max);
        this.setMethodTest(testDTO, count, max);
        this.asmFiledTest(testDTO, count, max);
        this.asmMethodTest(testDTO, count, max);
        this.reflectTest(testDTO, count, max);

    }

    private void createConstStringVar(TestDTO testDTO, int count, int max) {

        long begin = System.nanoTime();
        for (int i = 0; i < count; i++) {
            String s = i + "";
            s.toUpperCase();
        }
        long end = System.nanoTime();
        long o = end - begin;
        double l = o / count;
        log.info(" 创建常量 执行{}次，共使用时间 {},平均每次花费 {}", count, o, l);
    }

    private void filedTest(TestDTO testDTO, int count, int max) {

        List<Long> longs = new ArrayList<>(max);
        int size = max;
        while (size-- > 0) {
            long begin = System.nanoTime();
            for (int i = 0; i < count; i++) {
                testDTO.name = i + "";
            }
            long end = System.nanoTime();
            long o = end - begin;
            longs.add(o);
        }
        Long aLong = longs.stream().reduce((prv, next) -> prv + next).get();
        double totalCount = max * count;
        double o1 = aLong / totalCount;
        log.info(" java filed 执行{}次，共使用时间 {},平均每次花费 {}", totalCount, aLong, o1);
    }

    private void setMethodTest(TestDTO testDTO, int count, int max) {

        List<Long> longs = new ArrayList<>(max);
        int size = max;
        while (size-- > 0) {
            long begin = System.nanoTime();
            for (int i = 0; i < count; i++) {
                testDTO.setName(i + "");
            }
            long end = System.nanoTime();
            long o = end - begin;
            longs.add(o);
        }
        Long aLong = longs.stream().reduce((prv, next) -> prv + next).get();
        double totalCount = max * count;
        double o1 = aLong / totalCount;
        log.info(" java setMethod 执行{}次，共使用时间 {},平均每次花费 {}", totalCount, aLong, o1);
    }

    private void asmFiledTest(TestDTO testDTO, int count, int max) {

        List<Long> longs = new ArrayList<>(max);
        FieldAccess fieldAccess = FieldAccess.get(TestDTO.class);
        int size = max;
        while (size-- > 0) {
            long begin = System.nanoTime();
            for (int i = 0; i < count; i++) {
                fieldAccess.set(testDTO, "name", i + "");
            }
            long end = System.nanoTime();
            long o = end - begin;
            longs.add(o);
        }
        Long aLong = longs.stream().reduce((prv, next) -> prv + next).get();
        double totalCount = max * count;
        double o1 = aLong / totalCount;
        log.info("asm filed 执行{}次，共使用时间 {},平均每次花费 {}", totalCount, aLong, o1);
    }

    private void asmMethodTest(TestDTO testDTO, int count, int max) {

        List<Long> longs = new ArrayList<>(max);
        MethodAccess methodAccess = MethodAccess.get(TestDTO.class);
        int size = max;
        while (size-- > 0) {
            long begin = System.nanoTime();
            for (int i = 0; i < count; i++) {
                methodAccess.invoke(testDTO, "setName", i + "");
            }
            long end = System.nanoTime();
            long o = end - begin;
            longs.add(o);
        }
        Long aLong = longs.stream().reduce((prv, next) -> prv + next).get();
        double totalCount = max * count;
        double o1 = aLong / totalCount;
        log.info("asm set method 执行{}次，共使用时间 {},平均每次花费 {}", totalCount, aLong, o1);
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
        double totalCount = max * count;
        double o1 = aLong / totalCount;
        log.info("java reflect 执行{}次，共使用时间 {},平均每次花费 {}", totalCount, aLong, o1);
    }


    @Data
    public static class TestDTO {

        public String name;
    }
}
