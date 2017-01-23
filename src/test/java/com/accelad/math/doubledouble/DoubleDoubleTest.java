package com.accelad.math.doubledouble;

import com.accelad.math.doubledouble.DoubleDouble;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map.Entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DoubleDoubleTest {

    @Test
    public void test1() throws Exception {
        DoubleDouble dd = new DoubleDouble("1");
        Assert.assertEquals(1d, dd.doubleValue(), 1e-12);
    }

    @Test
    public void test2() throws Exception {
        DoubleDouble dd = new DoubleDouble("1.0");
        Assert.assertEquals(1d, dd.doubleValue(), 1e-12);
    }

    @Test
    public void test3() throws Exception {
        DoubleDouble dd = new DoubleDouble("-1.0");
        Assert.assertEquals(-1d, dd.doubleValue(), 1e-12);
    }

    @Test
    public void test4() throws Exception {
        DoubleDouble dd = new DoubleDouble("10");
        Assert.assertEquals(10d, dd.doubleValue(), 1e-12);
    }

    @Test
    public void test5() throws Exception {
        DoubleDouble dd = new DoubleDouble("-10");
        Assert.assertEquals(-10d, dd.doubleValue(), 1e-12);
    }

    @Test
    public void test6() throws Exception {
        DoubleDouble dd = new DoubleDouble("-10");
        Assert.assertEquals(-10d, dd.doubleValue(), 1e-12);
    }

    @Test
    public void testToString() throws Exception {
        DoubleDouble dd = new DoubleDouble(4.75);
        Assert.assertEquals("4.75", dd.toString());
    }

    @Test
    public void testEquals() {
        DoubleDouble first = new DoubleDouble(4.75);
        DoubleDouble second = new DoubleDouble(4.75);

        assertTrue(first.equals(second));

        first = new DoubleDouble(4.75458763958447);
        second = new DoubleDouble(4.75458763958436);

        assertTrue(first.equals(second));
    }

    @Test
    public void testParse() throws Exception {
        ImmutableMap<String, DoubleDouble> testData = ImmutableMap.<String, DoubleDouble> builder()
                .put("1E14", new DoubleDouble(1E14))
                .put("-1E17", new DoubleDouble(-1E17))
                .put(" -1E17", new DoubleDouble(-1E17))
                .put(" 1E14", new DoubleDouble(1E14))
                .put("+1E14", new DoubleDouble(1E14))
                .put("1e14", new DoubleDouble(1E14))
                .put("-1e17", new DoubleDouble(-1E17))
                .put(" -1e17", new DoubleDouble(-1E17))
                .put(" 1e14", new DoubleDouble(1E14))
                .put("+1e14", new DoubleDouble(1E14))
                .put("1.0000000399999998E-4", new DoubleDouble(1.0000000399999998E-4))
                .put("3.93460376843724", new DoubleDouble(3.93460376843724))
                .put("1.0000000299999998E-4", new DoubleDouble(1.0000000299999998E-4))
                .put("3.9346037077899485", new DoubleDouble(3.9346037077899485))
                .put("1.0000000199999998E-4", new DoubleDouble(1.0000000199999998E-4))
                .put("3.934603647142657", new DoubleDouble(3.934603647142657))
                .put("1.0000000099999999E-4", new DoubleDouble(1.0000000099999999E-4))
                .put("3.9346035864953652", new DoubleDouble(3.9346035864953652))
                .build();

        for (Entry<String, DoubleDouble> entry : testData.entrySet()) {
            DoubleDouble actual = DoubleDouble.parse(entry.getKey());
            DoubleDouble expected = entry.getValue();
            String message = entry.getKey() + " is not equal to " + entry.getValue();
            assertEquals(message, expected, actual);
        }
    }
}
