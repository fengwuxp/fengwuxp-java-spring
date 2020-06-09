package com.wuxp.basic.uuid.sn;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 抽象的号生成策略
 * 按照 yyyyMMddHHmm+5位的自增长订单号
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
    public String uuid() {
        String orderPrefix = this.getOrderPrefix();
        return orderPrefix + this.getNextSn(orderPrefix);
    }

    /**
     * 生成下一个 id
     *
     * @param orderPrefix
     * @return
     */
    protected abstract String nextId(String orderPrefix);

    /**
     * 生成 sn前缀
     *
     * @return
     */
    protected String getOrderPrefix() {
        return DateFormatUtils.format(new Date(), ORDER_PREFIX_FORMATTER);
    }

    protected String getNextSn(String orderPrefix) {

        String nextId = nextId(orderPrefix);
        int len = this.maxSnLength - nextId.length();
        if (len == 0) {
            return nextId;
        }
        if (len < 0) {
            throw new RuntimeException("订单号的 nextId超过的了10w ,nextId= " + nextId);
        }
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < len; i++) {
            builder.append(0);
        }
        builder.append(nextId);
        return builder.toString();
    }

}
