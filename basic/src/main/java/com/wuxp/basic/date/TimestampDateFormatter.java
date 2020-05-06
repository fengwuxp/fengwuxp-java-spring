package com.wuxp.basic.date;

import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * 时间戳转时间 Formatter
 * @see WebMvcConfigurer#addFormatters(FormatterRegistry)
 * @author wxup
 */
public class TimestampDateFormatter implements Formatter<Date> {

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        if (StringUtils.hasText(text)) {
            return null;
        }
        return new Date(Long.parseLong(text));
    }

    @Override
    public String print(Date date, Locale locale) {
        if (date == null) {
            return null;
        }
        return Long.toString(date.getTime());
    }
}
