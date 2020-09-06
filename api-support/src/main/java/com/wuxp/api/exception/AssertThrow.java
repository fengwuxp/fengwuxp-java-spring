package com.wuxp.api.exception;

import com.wuxp.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * 用于断言抛出异常
 * {@link BusinessExceptionFactory}
 * {@link #fail(Throwable, String, BusinessErrorCode)}
 *
 * @author wxup
 */
@Slf4j
public final class AssertThrow extends Assert {


    private static BusinessExceptionFactory BUSINESS_EXCEPTION_FACTORY = DefaultBusinessExceptionFactory.DEFAULT_EXCEPTION_FACTORY;

    private AssertThrow() {
    }


    public static void assertResp(ApiResp resp) {

        assertTrue(resp.getErrorMessage(), resp.isSuccess());
    }

    public static void assertResp(String message, ApiResp resp) {

        assertTrue(message, resp.isSuccess());
    }

    public static void assertNotNull(String message, Object condition) {
        if (condition == null) {
            fail(message);
        }
    }

    public static void assertNull(String message, Object condition) {
        if (condition != null) {
            fail(message);
        }
    }


    public static void assertTrue(String message, boolean condition) {
        if (!condition) {
            fail(message);
        }
    }

    static public void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

    /**
     * Fails a test with the given message.
     *
     * @see BusinessException
     */
    static public void fail() {
        fail(null);
    }

    /**
     * Fails a test with the given message.
     *
     * @param message the identifying message for the {@link BusinessException} (<code>null</code>
     *                okay)
     * @see BusinessException
     */
    static public void fail(String message) {
        fail(message, BUSINESS_EXCEPTION_FACTORY.getErrorCode());
    }

    /**
     * Fails a test with the given message.
     *
     * @param message   the identifying message for the {@link BusinessException} (<code>null</code>
     *                  okay)
     * @param errorCode business failure code
     * @see BusinessException
     */
    static void fail(String message, BusinessErrorCode errorCode) {
        fail(null, message, errorCode);
    }

    /**
     * Fails a test with the given message.
     *
     * @param cause     business failure code
     * @param message   the identifying message for the {@link BusinessException} (<code>null</code>
     *                  okay)
     * @param errorCode business failure code {@link BusinessErrorCode}
     * @see BusinessException
     */
    static void fail(Throwable cause, String message, BusinessErrorCode errorCode) {

        BUSINESS_EXCEPTION_FACTORY.factory(cause, message, errorCode);

    }

    /**
     * Asserts that two objects are equal. If they are not, an
     * {@link BusinessException} is thrown with the given message. If
     * <code>expected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message  the identifying message for the {@link BusinessException} (<code>null</code>
     *                 okay)
     * @param expected expected value
     * @param actual   actual value
     */
    static public void assertEquals(String message, Object expected,
                                    Object actual) {
        if (equalsRegardingNull(expected, actual)) {
            return;
        } else {
            failNotEquals(message, expected, actual);
        }
    }


    private static boolean equalsRegardingNull(Object expected, Object actual) {
        if (expected == null) {
            return actual == null;
        }

        return isEquals(expected, actual);
    }

    private static boolean isEquals(Object expected, Object actual) {
        return expected.equals(actual);
    }

    /**
     * Asserts that two objects are equal. If they are not, an
     * {@link BusinessException} without a message is thrown. If
     * <code>expected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param expected expected value
     * @param actual   the value to check against <code>expected</code>
     */
    static public void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two objects are <b>not</b> equals. If they are, an
     * {@link BusinessException} is thrown with the given message. If
     * <code>unexpected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message    the identifying message for the {@link BusinessException} (<code>null</code>
     *                   okay)
     * @param unexpected unexpected value to check
     * @param actual     the value to check against <code>unexpected</code>
     */
    static public void assertNotEquals(String message, Object unexpected,
                                       Object actual) {
        if (equalsRegardingNull(unexpected, actual)) {
            failEquals(message, actual);
        }
    }

    /**
     * Asserts that two objects are <b>not</b> equals. If they are, an
     * {@link BusinessException} without a message is thrown. If
     * <code>unexpected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param unexpected unexpected value to check
     * @param actual     the value to check against <code>unexpected</code>
     */
    static public void assertNotEquals(Object unexpected, Object actual) {
        assertNotEquals(null, unexpected, actual);
    }

    private static void failEquals(String message, Object actual) {
        String formatted = "Values should be different. ";
        if (message != null) {
            formatted = message + ". ";
        }

        formatted += "Actual: " + actual;
        fail(formatted);
    }

    /**
     * Asserts that two longs are <b>not</b> equals. If they are, an
     * {@link BusinessException} is thrown with the given message.
     *
     * @param message    the identifying message for the {@link BusinessException} (<code>null</code>
     *                   okay)
     * @param unexpected unexpected value to check
     * @param actual     the value to check against <code>unexpected</code>
     */
    static public void assertNotEquals(String message, long unexpected, long actual) {
        if (unexpected == actual) {
            failEquals(message, Long.valueOf(actual));
        }
    }

    /**
     * Asserts that two longs are <b>not</b> equals. If they are, an
     * {@link BusinessException} without a message is thrown.
     *
     * @param unexpected unexpected value to check
     * @param actual     the value to check against <code>unexpected</code>
     */
    static public void assertNotEquals(long unexpected, long actual) {
        assertNotEquals(null, unexpected, actual);
    }

    /**
     * Asserts that two doubles are <b>not</b> equal to within a positive delta.
     * If they are, an {@link BusinessException} is thrown with the given
     * message. If the unexpected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     * <code>assertNotEquals(Double.NaN, Double.NaN, *)</code> fails
     *
     * @param message    the identifying message for the {@link BusinessException} (<code>null</code>
     *                   okay)
     * @param unexpected unexpected value
     * @param actual     the value to check against <code>unexpected</code>
     * @param delta      the maximum delta between <code>unexpected</code> and
     *                   <code>actual</code> for which both numbers are still
     *                   considered equal.
     */
    static public void assertNotEquals(String message, double unexpected,
                                       double actual, double delta) {
        if (!doubleIsDifferent(unexpected, actual, delta)) {
            failEquals(message, Double.valueOf(actual));
        }
    }

    /**
     * Asserts that two doubles are <b>not</b> equal to within a positive delta.
     * If they are, an {@link BusinessException} is thrown. If the unexpected
     * value is infinity then the delta value is ignored.NaNs are considered
     * equal: <code>assertNotEquals(Double.NaN, Double.NaN, *)</code> fails
     *
     * @param unexpected unexpected value
     * @param actual     the value to check against <code>unexpected</code>
     * @param delta      the maximum delta between <code>unexpected</code> and
     *                   <code>actual</code> for which both numbers are still
     *                   considered equal.
     */
    static public void assertNotEquals(double unexpected, double actual, double delta) {
        assertNotEquals(null, unexpected, actual, delta);
    }

    /**
     * Asserts that two floats are <b>not</b> equal to within a positive delta.
     * If they are, an {@link BusinessException} is thrown. If the unexpected
     * value is infinity then the delta value is ignored.NaNs are considered
     * equal: <code>assertNotEquals(Float.NaN, Float.NaN, *)</code> fails
     *
     * @param unexpected unexpected value
     * @param actual     the value to check against <code>unexpected</code>
     * @param delta      the maximum delta between <code>unexpected</code> and
     *                   <code>actual</code> for which both numbers are still
     *                   considered equal.
     */
    static public void assertNotEquals(float unexpected, float actual, float delta) {
        assertNotEquals(null, unexpected, actual, delta);
    }

    static private void failNotEquals(String message,
                                      Object expected,
                                      Object actual) {
        if (!expected.equals(actual)) {
            fail(message);
        }
    }


    static private boolean doubleIsDifferent(double d1, double d2, double delta) {
        if (Double.compare(d1, d2) == 0) {
            return false;
        }
        if ((Math.abs(d1 - d2) <= delta)) {
            return false;
        }

        return true;
    }


    public static void setBusinessExceptionFactory(BusinessExceptionFactory<?, ?> businessExceptionFactory) {
        AssertThrow.BUSINESS_EXCEPTION_FACTORY = businessExceptionFactory;
    }
}
