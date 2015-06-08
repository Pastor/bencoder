package ru.encoders.bencoder;

import java.io.IOException;

final class BencNumber implements BencElement<Long> {
    private final long value;

    BencNumber(long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }

    static BencNumber read(PeekReader reader) throws IOException {
        if (reader.peek() != 'i')
            throw new IOException("Expected symbol 'i'");
        reader.next();
        long value = ParserHelper.parseNumber(reader, 'e');
        if (reader.peek() != 'e')
            throw new IOException("Expected symbol 'e'");
        reader.next();
        return new BencNumber(value);
    }

    @Override
    public String toString() {
        return "i" + value + "e";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BencNumber that = (BencNumber) o;
        return value == that.value;

    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }
}
