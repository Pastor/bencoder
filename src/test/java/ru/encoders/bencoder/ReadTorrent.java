package ru.encoders.bencoder;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public final class ReadTorrent {
    @Test
    public void testRead() throws Exception {
        test("/1.torrent");
        test("/2.torrent");
    }

    private static void test(String resource) throws Exception {
        try (InputStream is = ReadTorrent.class.getResourceAsStream(resource)) {
            try (PeekReader reader = new PeekReader(is)) {
                BencDictionary read = BencDictionary.read(reader);
                assertNotNull(read.get("info"));
                assertNotNull(read.get("publisher"));
                assertNotNull(read.get("encoding"));
                assertNotNull(read.get("comment"));
                assertEquals(read.get("publisher").getValue(), "rutracker.org");
            }
        }
    }
}
