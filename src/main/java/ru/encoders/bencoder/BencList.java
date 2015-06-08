package ru.encoders.bencoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class BencList implements BencElement<List<BencElement<?>>> {
    private final List<BencElement<?>> value = new ArrayList<>();

    @Override
    public List<BencElement<?>> getValue() {
        return value;
    }

    static BencList read(PeekReader reader) throws IOException {
        BencList list = new BencList();
        if (reader.peek() != 'l')
            throw new IOException("Expected symbol 'l'");
        reader.next();
        while (reader.notEof() && reader.peek() != 'e') {
            list.value.add(BencReader.read(reader));
        }
        if (reader.peek() != 'e')
            throw new IOException("Expected symbol 'e'");
        reader.next();
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("l");
        for (BencElement<?> element : value)
            sb.append(element.toString());
        sb.append("e");
        return sb.toString();
    }
}
