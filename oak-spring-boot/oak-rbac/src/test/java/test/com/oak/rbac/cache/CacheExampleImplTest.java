package test.com.oak.rbac.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import test.com.oak.rbac.OakApplicationTest;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CacheExampleImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>1月 14, 2020</pre>
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
//@Transactional(rollbackFor = {Throwable.class})
@Slf4j
public class CacheExampleImplTest {

    @Autowired
    private CacheExample cacheExample;


    private TypeConverter typeConverter = new SimpleTypeConverter();

    @Autowired
    FormattingConversionService formattingConversionService;

    @Autowired
    private ConversionService conversionService;

    @Value("${test.value}")
    private List<Integer> values;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getValue(String key)
     */
    @Test
    public void testGetValue() throws Exception {
        formattingConversionService.addFormatter(new DateFormatter());
        Date date = formattingConversionService.convert("2019-01-02", Date.class);
        log.info("{}", date);
        Integer i = formattingConversionService.convert("1", Integer.class);
        log.info("{}", i);
        Boolean aBoolean = formattingConversionService.convert("1", Boolean.class);
        log.info("{}", aBoolean);

        Integer[] integers = new Integer[0];
        Integer[] integers1 = formattingConversionService.convert("1,2", integers.getClass());
        log.info("{}", integers1);

        Field field = ReflectionUtils.findField(ExampleValueType.class, "longs");
        TypeDescriptor typeDescriptor = new TypeDescriptor(field);
        Object longs = formattingConversionService.convert("1,2", typeDescriptor);
        log.info("{}", longs);


    }

    /**
     * Method: getValueByCache(String key)
     */
    @Test
    public void testGetValueByCache() throws Exception {

        String test1 = cacheExample.getValueByCache("test");
        String test2 = cacheExample.getValueByCache("test");
        String test3 = cacheExample.getValueByCache("test");
        String test4 = cacheExample.getValue("test");
        String test5 = cacheExample.getValue("test");
        log.info("{}  {}  {}  {} {}", test1, test2, test3, test4, test5);


    }


}
