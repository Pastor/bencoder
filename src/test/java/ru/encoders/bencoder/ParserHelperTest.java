package ru.encoders.bencoder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class ParserHelperTest {

    @Test
    public void testParseNumber() throws Exception {
        long number = ParserHelper.parseNumber(new PeekReader("10"));
        assertEquals(number, 10);
    }

    @Test
    public void testParseNegativeNumber() throws Exception {
        long number = ParserHelper.parseNumber(new PeekReader("-10"));
        assertEquals(number, -10);
    }

    @Test
    public void testParseNumberWithStop() throws Exception {
        long number = ParserHelper.parseNumber(new PeekReader("2:100"), ':');
        assertEquals(number, 2);
    }

    @Test
    public void testParseNegativeNumberWithStop() throws Exception {
        long number = ParserHelper.parseNumber(new PeekReader("-92f100"), 'f');
        assertEquals(number, -92);
    }
}