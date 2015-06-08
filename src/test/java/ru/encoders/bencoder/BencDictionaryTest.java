package ru.encoders.bencoder;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public final class BencDictionaryTest {

    @Test
    public void testReadEmpty() throws Exception {
        BencElement<Map<BencString, BencElement<?>>> dict = BencDictionary.read(new PeekReader("de"));
        assertEquals(dict.getValue().size(), 0);
    }

    @Test
    public void testRead3() throws Exception {
        BencElement<Map<BencString, BencElement<?>>> dict = BencDictionary.read(new PeekReader("d3:bar4:spam3:fooi42ee"));
        assertEquals(dict.getValue().size(), 2);
        assertEquals(dict.getValue().get(new BencString("bar")).getValue(), new BencString("spam").getValue());
        assertEquals(dict.getValue().get(new BencString("foo")).getValue(), new BencNumber(42).getValue());
    }
}