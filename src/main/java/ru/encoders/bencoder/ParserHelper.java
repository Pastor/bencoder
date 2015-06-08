package ru.encoders.bencoder;

import java.io.IOException;

final class ParserHelper {

    static long parseNumber(PeekReader reader) throws IOException {
        return parseNumber(reader, -1);
    }

    static long parseNumber(PeekReader reader, int stop) throws IOException {
        long result = 0;
        boolean neg = false;
        while (reader.notEof() && reader.peek() != stop) {
            int d = reader.peek() - '0';
            if (d >= 0 && d <= 9) {
                result = (result * 10) + d;
            } else if (reader.peek() == '-') {
                neg = true;
            } else {
                throw new IOException("Illegal number format");
            }
            reader.next();
        }

        if (neg) {
            result *= -1;
        }
        return result;
    }
}
