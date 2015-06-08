package ru.encoders.bencoder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class BencStringTest {

    @Test
    public void testRead() throws Exception {
        BencElement<String> read = BencString.read(new PeekReader("1:0"));
        assertEquals(read.getValue(), "0");
    }

    @Test
    public void testReadEmpty() throws Exception {
        BencElement<String> read = BencString.read(new PeekReader("0:"));
        assertEquals(read.getValue(), "");
    }

    @Test
    public void testReadEmptyWithTail() throws Exception {
        BencElement<String> read = BencString.read(new PeekReader("0:TAIL"));
        assertEquals(read.getValue(), "");
    }

    @Test
    public void testToString() throws Exception {
        BencElement<String> read = BencString.read(new PeekReader("1:0"));
        assertEquals(read.toString(), "1:0");
    }

    @Test
    public void testToStringGc() throws Exception {
        BencElement<String> read = BencString.read(new PeekReader("1:0"));
        assertEquals(read.toString(), "1:0");
        System.gc();
        assertEquals(read.toString(), "1:0");
    }
}