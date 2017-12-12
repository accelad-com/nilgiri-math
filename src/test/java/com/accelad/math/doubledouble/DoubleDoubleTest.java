package com.accelad.math.doubledouble;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static com.accelad.math.doubledouble.DoubleDouble.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class DoubleDoubleTest {

    @Test
    public void should_return_the_correct_value_when_given_value_is_powered_by_two_and_then_powered_by_three()
            throws Exception {
        DoubleDouble givenValue = DoubleDouble.fromString("1.2");
        DoubleDouble two = DoubleDouble.fromString("2");
        DoubleDouble givenValuePoweredByTwo = givenValue.pow(two);
        assertThat(givenValuePoweredByTwo, is(DoubleDouble.fromString("1.44")));

        DoubleDouble three = DoubleDouble.fromString("3");
        DoubleDouble givenValuePoweredByThree = givenValue.pow(three);
        assertThat(givenValuePoweredByThree, is(DoubleDouble.fromString("1.728")));
    }

    @Test
    public void should_return_nan_when_log_too_high() {
        double notComputableMaxValue = (Double.MAX_VALUE / DoubleDouble.SPLIT) * DoubleDouble.E.doubleValue();
        DoubleDouble givenValue = DoubleDouble.fromOneDouble(notComputableMaxValue);

        DoubleDouble log = givenValue.log();

        assertTrue(log.isNaN());
    }

    @Test
    public void should_return_value_when_log_the_max_value_acceptable() {
        double split = DoubleDouble.SPLIT + 1;
        double maxComputableValue = (Double.MAX_VALUE / split) * DoubleDouble.E.doubleValue();
        DoubleDouble givenValue = DoubleDouble.fromOneDouble(maxComputableValue);

        DoubleDouble log = givenValue.log();

        assertFalse(log.isNaN());
    }

    @Test
    public void should_return_nan_when_pow_too_high() {
        double notComputableMaxValue = (Double.MAX_VALUE / DoubleDouble.SPLIT) * DoubleDouble.E.doubleValue();
        DoubleDouble givenValue = DoubleDouble.fromOneDouble(notComputableMaxValue);

        DoubleDouble log = givenValue.pow(DoubleDouble.fromOneDouble(2));

        assertTrue(log.isNaN());
    }

    @Test
    public void should_return_value_when_pow_the_max_value_acceptable() {
        double split = DoubleDouble.SPLIT + 1;
        double maxComputableValue = (Double.MAX_VALUE / split) * DoubleDouble.E.doubleValue();
        DoubleDouble givenValue = DoubleDouble.fromOneDouble(maxComputableValue);

        DoubleDouble log = givenValue.pow(DoubleDouble.fromOneDouble(2));

        assertFalse(log.isNaN());
    }

    @Test(expected = ArithmeticException.class)
    public void should_throw_an_exception_when_computing_atan2_with_x_and_y_equals_zero() throws Exception {
        DoubleDouble x = DoubleDouble.ZERO;
        DoubleDouble y = DoubleDouble.ZERO;
        DoubleDouble.atan2(y, x);
    }

    @Test()
    public void should_return_positive_half_PI_when_computing_atan2_with_x_equals_zero_and_y_is_positive() throws Exception {
        DoubleDouble x = DoubleDouble.ZERO;
        DoubleDouble y = ONE;
        DoubleDouble result = DoubleDouble.atan2(y, x);

        assertThat(result, equalTo(DoubleDouble.PI_2));
    }

    @Test()
    public void should_return_negative_half_PI_when_computing_atan2_with_x_equals_zero_and_y_is_negative() throws Exception {
        DoubleDouble x = DoubleDouble.ZERO;
        DoubleDouble y = ONE.negate();
        DoubleDouble result = DoubleDouble.atan2(y, x);

        assertThat(result, equalTo(DoubleDouble.PI_2.negate()));
    }

    @Test()
    public void should_return_the_atan_of_y_divided_by_x_when_computing_atan2_with_x_is_positive() throws Exception {
        DoubleDouble x = ONE;
        DoubleDouble y = DoubleDouble.TWO;
        DoubleDouble result = DoubleDouble.atan2(y, x);
        DoubleDouble expected = y.divide(x).atan();

        assertThat(result, equalTo(expected));
    }

    @Test()
    public void should_return_PI_plus_the_atan_of_y_divided_by_x_when_computing_atan2_with_x_is_negative_and_y_is_positive() throws Exception {
        DoubleDouble x = ONE.negate();
        DoubleDouble y = DoubleDouble.TWO;
        DoubleDouble result = DoubleDouble.atan2(y, x);
        DoubleDouble expected = y.divide(x).atan().add(PI);

        assertThat(result, equalTo(expected));
    }

    @Test()
    public void should_return_the_atan_of_y_divided_by_x_plus_PI_when_computing_atan2_with_x_is_negative_and_y_is_zero() throws Exception {
        DoubleDouble x = ONE.negate();
        DoubleDouble y = DoubleDouble.ZERO;
        DoubleDouble result = DoubleDouble.atan2(y, x);
        DoubleDouble expected = y.divide(x).atan().add(PI);

        assertThat(result, equalTo(expected));
    }

    @Test()
    public void should_return_the_atan_of_y_divided_by_x_minus_pi_when_computing_atan2_with_x_is_negative_and_y_is_negative() throws Exception {
        DoubleDouble x = ONE.negate();
        DoubleDouble y = DoubleDouble.TWO.negate();
        DoubleDouble result = DoubleDouble.atan2(y, x);
        DoubleDouble expected = y.divide(x).atan().subtract(PI);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void test1() throws Exception {
        DoubleDouble dd = DoubleDouble.fromString("1");
        Assert.assertEquals(1d, dd.doubleValue(), 1e-12);
    }

    @Test
    public void test2() throws Exception {
        DoubleDouble dd = DoubleDouble.fromString("1.0");
        Assert.assertEquals(1d, dd.doubleValue(), 1e-12);
    }

    @Test
    public void test3() throws Exception {
        DoubleDouble dd = DoubleDouble.fromString("-1.0");
        Assert.assertEquals(-1d, dd.doubleValue(), 1e-12);
    }

    @Test
    public void test4() throws Exception {
        DoubleDouble dd = DoubleDouble.fromString("10");
        Assert.assertEquals(10d, dd.doubleValue(), 1e-12);
    }

    @Test
    public void test5() throws Exception {
        DoubleDouble dd = DoubleDouble.fromString("-10");
        Assert.assertEquals(-10d, dd.doubleValue(), 1e-12);
    }

    @Test
    public void test6() throws Exception {
        DoubleDouble dd = DoubleDouble.fromString("-10");
        Assert.assertEquals(-10d, dd.doubleValue(), 1e-12);
    }

    @Test
    public void testToString() throws Exception {
        DoubleDouble dd = DoubleDouble.fromOneDouble(4.75);
        Assert.assertEquals("4.75", dd.toString());
    }

    @Test
    public void should_return_the_correct_precise_string_representation_when_value_is_30_digits_precise() throws Exception {
        String expectedString = "0.12345678901234567890123456789";
        DoubleDouble parsedValue = DoubleDouble.fromString(expectedString);

        String returnedValuesAsString = parsedValue.toString();

        assertEquals(expectedString, returnedValuesAsString);
    }

    @Test
    public void testEquals() {
        DoubleDouble first = DoubleDouble.fromOneDouble(4.75);
        DoubleDouble second = DoubleDouble.fromOneDouble(4.75);

        assertTrue(first.equals(second));

        first = DoubleDouble.fromOneDouble(4.75458763958447);
        second = DoubleDouble.fromOneDouble(4.75458763958436);

        assertFalse(first.equals(second));
    }

    @Test
    public void should_return_equals_when_there_is_no_difference_with_30_digit_precise_number() {
        DoubleDouble first = DoubleDouble.fromString("0.12345678901234567890123456789");
        DoubleDouble second = DoubleDouble.fromString("0.12345678901234567890123456789");

        assertEquals(first, second);
    }


    @Test
    public void should_return_not_equals_when_there_is_one_digit_difference_with_30_digit_precise_number() {
        DoubleDouble first = DoubleDouble.fromString("0.12345678901234567890123456789");
        DoubleDouble second = DoubleDouble.fromString("0.12345678901234567890123456788");

        assertNotEquals(first, second);
    }

    @Test
    public void should_return_not_equals_when_values_are_very_low() {
        DoubleDouble first = DoubleDouble.fromString("1E-20");
        DoubleDouble second = DoubleDouble.fromString("1E-21");

        assertNotEquals(first, second);
    }

    @Test
    public void should_return_equals_when_equality_in_the_expected_precision_range_is_ok() {
        DoubleDouble first = DoubleDouble.fromTwoDouble(4.0, 1E-32);
        DoubleDouble second = DoubleDouble.fromTwoDouble(4.0, 1E-33);

        assertEquals(first, second);
    }

    @Test
    public void should_return_equals_when_both_are_zero() {
        DoubleDouble first = ZERO;
        DoubleDouble second = ZERO;

        assertEquals(first, second);
    }

    @Test
    public void should_not_return_equal_when_one_is_zero_and_the_other_is_low() {
        DoubleDouble first = ZERO;
        DoubleDouble second = DoubleDouble.fromString("1E-13");

        assertNotEquals(first, second);
    }

    @Test
    public void testParse() throws Exception {
        Map<String, DoubleDouble> map = new HashMap<>();
        map.put("1E14", DoubleDouble.fromOneDouble(1E14));
        map.put("-1E17", DoubleDouble.fromOneDouble(-1E17));
        map.put(" -1E17", DoubleDouble.fromOneDouble(-1E17));
        map.put(" 1E14", DoubleDouble.fromOneDouble(1E14));
        map.put("+1E14", DoubleDouble.fromOneDouble(1E14));
        map.put("1e14", DoubleDouble.fromOneDouble(1E14));
        map.put("-1e17", DoubleDouble.fromOneDouble(-1E17));
        map.put(" -1e17", DoubleDouble.fromOneDouble(-1E17));
        map.put(" 1e14", DoubleDouble.fromOneDouble(1E14));
        map.put("+1e14", DoubleDouble.fromOneDouble(1E14));
        map.put("1.0000000399999998E-4", DoubleDouble.fromTwoDouble(1.0000000399999998E-4, 0.4743564752683E-20));
        map.put("3.93460376843724", DoubleDouble.fromTwoDouble(3.93460376843724, -1.3440350972814486E-16));
        map.put("1.0000000299999998E-4", DoubleDouble.fromTwoDouble(1.0000000299999998E-4, 7.477619529328194E-22));
        map.put("3.9346037077899485", DoubleDouble.fromTwoDouble(3.9346037077899485, -1.2721925158985238E-18));
        map.put("1.0000000199999998E-4", DoubleDouble.fromTwoDouble(1.0000000199999998E-4, -3.248040846817162E-21));
        map.put("3.934603647142657", DoubleDouble.fromTwoDouble(3.934603647142657, 1.3185912469634786E-16));
        map.put("1.0000000099999999E-4", DoubleDouble.fromTwoDouble(1.0000000099999999E-4, 2.7561563534328564E-21));
        map.put("3.9346035864953652", DoubleDouble.fromTwoDouble(3.9346035864953652, -3.50095580914058E-17));


        for (Entry<String, DoubleDouble> entry : map.entrySet()) {
            DoubleDouble actual = DoubleDouble.fromString(entry.getKey());
            DoubleDouble expected = entry.getValue();
            String message = entry.getKey() + " is not equal to " + entry.getValue();
            assertEquals(message, expected, actual);
        }
    }

    @Test
    public void should_parse_5000_string_in_less_than_200_000_ms() {
        int size = 5000;
        String strArray[] = new String[size];
        for (int i = 0; i < size; i++) {
            strArray[i] = Integer.valueOf(i).toString();
        }
        DoubleDouble array[] = new DoubleDouble[size];
        long startTime = getTime();
        for (int i = 0; i < size; i++) {
            array[i] = DoubleDouble.fromString(strArray[i]);
        }
        long endTime = getTime();
        long elapsedTimeInMillis = endTime - startTime;
        System.out.println("elapsedTime : " + elapsedTimeInMillis / 1e9 + " s");
    }

    private long getTime() {
        return System.nanoTime();
    }
}
