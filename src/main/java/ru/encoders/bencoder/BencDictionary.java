package ru.encoders.bencoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

final class BencDictionary implements BencElement<Map<BencString, BencElement<?>>> {
    private final Map<BencString, BencElement<?>> value = new HashMap<>();

    @Override
    public Map<BencString, BencElement<?>> getValue() {
        return value;
    }

    BencElement<?> get(String key) {
        return value.get(new BencString(key));
    }

    static BencDictionary read(PeekReader reader) throws IOException {
        BencDictionary dict = new BencDictionary();
        if (reader.peek() != 'd')
            throw new IOException("Expected symbol 'd'");
        reader.next();
        while (reader.notEof() && reader.peek() != 'e') {
            BencString key = BencString.read(reader);
            BencElement<?> value = BencReader.read(reader);
            dict.value.put(key, value);
        }
        if (reader.peek() != 'e')
            throw new IOException("Expected symbol 'e'");
        reader.next();
        return dict;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("d");
        for (Map.Entry<BencString, BencElement<?>> entry : value.entrySet()) {
            sb.append(entry.getKey().toString()).append(entry.getValue().toString());
        }
        sb.append("e");
        return sb.toString();
    }
}
