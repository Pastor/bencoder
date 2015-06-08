package ru.encoders.bencoder;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BencDictionaryTest.class,
        BencListTest.class,
        BencNumberTest.class,
        BencReaderTest.class,
        BencStringTest.class,
        ParserHelperTest.class,
        ReadTorrent.class
})
public final class TestSuit {
}
