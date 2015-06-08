package ru.encoders.bencoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

final class PeekReader extends InputStream {
    private final InputStream reader;
    private int peek = -1;

    PeekReader(InputStream reader) {
        this.reader = reader;
        next();
    }

    PeekReader(String text) {
        this(new ByteArrayInputStream(text.getBytes()));
    }

    int peek() {
        return peek;
    }

    int next() {
        try {
            return read();
        } catch (IOException ex) {
            peek = -1;
            return peek;
        }
    }

    boolean notEof() {
        return peek != -1;
    }

    @Override
    public int read() throws IOException {
        peek = reader.read();
        return peek;
    }

    @Override
    public void close() throws IOException {
        reader.close();
        peek = -1;
    }

    @Override
    public String toString() {
        return "Current: '" + (char) peek + "'[" + peek + "]";
    }
}
