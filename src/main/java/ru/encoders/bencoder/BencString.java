package ru.encoders.bencoder;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

final class BencString implements BencElement<String> {
    private final static Charset DEFAULT = Charset.forName("utf8");
    private final byte[] value;
    private SoftReference<String> view;

    private BencString(byte[] value) {
        this.value = value;
        this.view = new SoftReference<>(create(value));
    }

    BencString(String text) {
        this(text.getBytes(DEFAULT));
    }

    @Override
    public synchronized String getValue() {
        if (view.get() == null)
            view = new SoftReference<>(create(value));
        return view.get();
    }

    static String create(byte[] array) {
        return new String(array, DEFAULT);
    }

    static BencString read(PeekReader reader) throws IOException {
        long size = ParserHelper.parseNumber(reader, ':');
        ByteBuffer buffer = ByteBuffer.allocate((int) size);
        while (reader.notEof() && size > 0) {
            buffer.put((byte) reader.next());
            --size;
        }
        reader.next();
        return new BencString(buffer.array());
    }

    @Override
    public String toString() {
        return value.length + ":" + getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BencString that = (BencString) o;
        return Arrays.equals(value, that.value);

    }

    @Override
    public int hashCode() {
        return value != null ? Arrays.hashCode(value) : 0;
    }
}
