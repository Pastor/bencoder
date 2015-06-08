package ru.encoders.bencoder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class BencNumberTest {

    @Test
    public void testReadZero() throws Exception {
        BencElement<Long> number = BencNumber.read(new PeekReader("i0e"));
        assertEquals(number.getValue().longValue(), 0);
    }

    @Test
    public void testReadNegative() throws Exception {
        BencElement<Long> number = BencNumber.read(new PeekReader("i-1e"));
        assertEquals(number.getValue().longValue(), -1);
    }

    @Test
    public void testReadPositive() throws Exception {
        BencElement<Long> number = BencNumber.read(new PeekReader("i1000e"));
        assertEquals(number.getValue().longValue(), 1000);
    }
}