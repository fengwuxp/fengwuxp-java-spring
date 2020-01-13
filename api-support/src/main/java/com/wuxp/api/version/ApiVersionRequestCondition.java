package com.wuxp.api.version;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 接口版本条件匹配
 */
@Slf4j
public class ApiVersionRequestCondition implements RequestCondition<ApiVersionRequestCondition> {

    // 用于匹配request中的版本号  v1 v2
    private static final Pattern VERSION_PATTERN = Pattern.compile("/v(\\d+).*");

    // 保存当前的版本号
    private int version;

    // 保存所有接口的最大版本号
    private static int maxVersion = 1;

    public ApiVersionRequestCondition(int version) {
        this.version = version;
    }

    @Override
    public ApiVersionRequestCondition combine(ApiVersionRequestCondition other) {
        // 上文的getMappingForMethod方法中是使用 类的Condition.combine(方法的condition)的结果
        // 确定一个方法的condition，所以偷懒的写法，直接返回参数的版本，可以保证方法优先，可以优化
        // 在condition中增加一个来源于类或者方法的标识，以此判断，优先整合方法的condition
        return new ApiVersionRequestCondition(other.version);
    }

    @Override
    public ApiVersionRequestCondition getMatchingCondition(HttpServletRequest request) {
        // 正则匹配请求的uri，看是否有版本号 v1
        String requestURI = request.getRequestURI();
        Matcher matcher = VERSION_PATTERN.matcher(requestURI);
        if (matcher.find()) {
            String versionNo = matcher.group(1);
            int version = Integer.parseInt(versionNo);
            // 超过当前最大版本号或者低于最低的版本号均返回不匹配
            if (version <= maxVersion && version >= this.version) {
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionRequestCondition other, HttpServletRequest request) {
        // 以版本号大小判定优先级，越高越优先
        return other.version - this.version;
    }

    public int getVersion() {
        return version;
    }

    public static void setMaxVersion(int maxVersion) {
        ApiVersionRequestCondition.maxVersion = maxVersion;
    }
}
