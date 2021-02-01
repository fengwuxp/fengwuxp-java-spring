package com.wuxp.api.version;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 接口版本条件匹配
 *
 * @author wuxp
 */
@Slf4j
public class ApiVersionRequestCondition implements RequestCondition<ApiVersionRequestCondition> {

    private static final String API_VERSION_HEADER_NAME = "X-Api-Version";

    /**
     * 用于匹配request中的版本号  v1 v2
     */
    private static final Pattern VERSION_PATTERN = Pattern.compile("/v(\\d+).*");

    /**
     * 保存当前的版本号
     */
    private final int version;

    /**
     * 保存所有接口的最大版本号
     */
    private static int maxVersion = 1;

    public ApiVersionRequestCondition(int version) {
        this.version = version;
    }

    /**
     * 上文的getMappingForMethod方法中是使用 类的Condition.combine(方法的condition)的结果
     * 确定一个方法的condition，所以偷懒的写法，直接返回参数的版本，可以保证方法优先，可以优化
     * 在condition中增加一个来源于类或者方法的标识，以此判断，优先整合方法的condition
     *
     * @param other api版本请求匹配条件
     * @return api版本请求匹配条件
     */
    @Override
    public ApiVersionRequestCondition combine(@Nullable() ApiVersionRequestCondition other) {
        if (other == null) {
            return new ApiVersionRequestCondition(maxVersion);
        }
        return new ApiVersionRequestCondition(other.version);
    }

    @Override
    public ApiVersionRequestCondition getMatchingCondition(HttpServletRequest request) {
        // 正则匹配请求的uri，看是否有版本号 v1
        String requestUri = request.getRequestURI();

        String versionNo = request.getHeader(API_VERSION_HEADER_NAME);
        if (!StringUtils.hasLength(versionNo)) {
            Matcher matcher = VERSION_PATTERN.matcher(requestUri);
            if (matcher.find()) {
                versionNo = matcher.group(1);
            }
        }
        if (StringUtils.hasLength(versionNo)) {
            int requestVersion = Integer.parseInt(versionNo);
            // 超过当前最大版本号或者低于最低的版本号均返回不匹配
            if (version <= maxVersion && requestVersion >= this.version) {
                return this;
            }
        }
        return new ApiVersionRequestCondition(maxVersion);
    }

    @Override
    public int compareTo(@Nullable() ApiVersionRequestCondition other, @Nullable() HttpServletRequest request) {
        // 以版本号大小判定优先级，越高越优先
        int otherVersion = maxVersion;
        if (other != null) {
            otherVersion = other.version;
        }
        return otherVersion - this.version;
    }

    public int getVersion() {
        return version;
    }

    public static void setMaxVersion(int maxVersion) {
        ApiVersionRequestCondition.maxVersion = maxVersion;
    }
}
