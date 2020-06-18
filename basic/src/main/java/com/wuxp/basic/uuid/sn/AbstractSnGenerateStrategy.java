package com.wuxp.basic.uuid.sn;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 抽象的sn生成策略
 * 按照 yyyyMMddHHmm+业务编码（2-5位）+5位的自增长的sn
 * <p>
 * 每一分钟最多支持生成10w个sn
 * {@link AbstractSnGenerateStrategy#MAX_SN_LENGTH}
 *
 * @author wxup
 */
public abstract class AbstractSnGenerateStrategy implements SnGenerateStrategy {


    private static final String ORDER_PREFIX_FORMATTER = "yyyyMMddHHmm";

    /**
     * 最大支持5个
     */
    private static final int MAX_SN_LENGTH = 5;

    private int maxSnLength = MAX_SN_LENGTH;

    public AbstractSnGenerateStrategy() {
    }

    public AbstractSnGenerateStrategy(int maxSnLength) {
        this.maxSnLength = maxSnLength;
    }

    @Override
    public String nextSn(SnType type) {
        String orderPrefix = this.getOrderPrefix();
        return orderPrefix + type.getCode() + this.getNextSn(orderPrefix, type);
    }

    /**
     * 生成下一个 id
     *
     * @param orderPrefix
     * @param type
     * @return
     */
    protected abstract String nextId(String orderPrefix, SnType type);

    /**
     * 生成 sn前缀
     *
     * @return
     */
    protected String getOrderPrefix() {
        return DateFormatUtils.format(new Date(), ORDER_PREFIX_FORMATTER);
    }

    protected String getNextSn(String orderPrefix, SnType type) {

        String nextId = nextId(orderPrefix, type);
        int len = this.maxSnLength - nextId.length();
        if (len == 0) {
            return nextId;
        }
        if (len < 0) {
            throw new RuntimeException("sn的 nextId超过的了10w ,nextId= " + nextId);
        }
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < len; i++) {
            builder.append(0);
        }
        builder.append(nextId);
        return builder.toString();
    }

}
