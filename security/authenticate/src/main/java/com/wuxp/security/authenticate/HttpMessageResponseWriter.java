package com.wuxp.security.authenticate;

import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用于同一返回响应数据
 *
 * @author wxup
 */
public interface HttpMessageResponseWriter {


    /**
     * 响应返回 json数据
     *
     * @param response
     * @param data
     * @throws Exception
     */
    default void writeJson(HttpServletResponse response, Object data) {
        this.writeJsonString(response, JSON.toJSONString(data));
    }

    /**
     * 响应返回 json数据
     *
     * @param response
     * @param data
     * @throws Exception
     */
    default void writeJsonString(HttpServletResponse response, String data) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
