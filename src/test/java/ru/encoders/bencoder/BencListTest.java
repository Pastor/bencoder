package ru.encoders.bencoder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class BencListTest {

    @Test
    public void testReadEmpty() throws Exception {
        BencList list = BencList.read(new PeekReader("le"));
        assertEquals(list.getValue().size(), 0);
    }

    @Test
    public void testReadStringList() throws Exception {
        BencList list = BencList.read(new PeekReader("l2:00e"));
        assertEquals(list.getValue().size(), 1);
        assertEquals(list.getValue().get(0).toString(), "2:00");
        assertEquals(list.toString(), "l2:00e");
    }

    @Test
    public void testReadObjectList() throws Exception {
        BencList list = BencList.read(new PeekReader("l2:00i-20e3:000i4ee"));
        assertEquals(list.getValue().size(), 4);
        assertEquals(list.getValue().get(0).toString(), "2:00");
        assertEquals(list.getValue().get(1).toString(), "i-20e");
        assertEquals(list.getValue().get(2).toString(), "3:000");
        assertEquals(list.getValue().get(3).toString(), "i4e");
    }
}