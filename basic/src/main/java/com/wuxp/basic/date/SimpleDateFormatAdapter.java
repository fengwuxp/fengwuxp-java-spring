package com.wuxp.basic.date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 多种时间格式的适配器
 *
 * @author wxup
 * @see WebMvcConfigurer#addFormatters(FormatterRegistry)
 */
@Slf4j
public class SimpleDateFormatAdapter extends SimpleDateFormat {
    private static final long serialVersionUID = 2002695469235850655L;

    private static final String[] PATTENS = new String[]{
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd",
            "yyyy-M-d H:m:s",
            "yyyy-MM-dd H:mm:ss",
            "yyyy-M-d H:mm:ss",
            "yyyy-M-d",
            "yyyy-MM",
            "yyyy-M",
    };


    public SimpleDateFormatAdapter() {
    }

    @Override
    public Date parse(String source) throws ParseException {
        if (!StringUtils.hasText(source)) {
            return null;
        }
        for (String pattern : PATTENS) {
            try {
                return parseDate(source, pattern);
            } catch (ParseException e) {
                if (log.isDebugEnabled()) {
                    log.debug("parse date", e);
                }
            }
        }

        throw new ParseException("Invalid  value '" + source + "'", -1);
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param pattern 使用第几个时间转换器
     * @return Date 日期
     */
    private Date parseDate(String dateStr, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dateStr);
    }
}
