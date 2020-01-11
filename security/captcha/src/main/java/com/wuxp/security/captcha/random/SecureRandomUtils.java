package com.wuxp.security.captcha.random;


import java.security.SecureRandom;

public final class SecureRandomUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    private SecureRandomUtils() {
    }


    /**
     * 产生两个数之间的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int randomNextInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    /**
     * 产生0-num的随机数,不包括num
     *
     * @param num 最大值
     * @return 随机数
     */
    public static int randomNextInt(int num) {
        return randomNextInt(0, num);
    }
}
