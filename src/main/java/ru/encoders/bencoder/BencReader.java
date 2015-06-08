package ru.encoders.bencoder;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BencReader {

    public static String toString(Object src) throws Exception {
        return toElement(src).toString();
    }

    private static BencElement<?> toElement(Object src) throws Exception {
        if (src instanceof Integer || src instanceof Long) {
            return new BencNumber(((Number) src).longValue());
        } else if (src instanceof String) {
            return new BencString((String) src);
        } else if (src.getClass().isArray()) {
            BencList list = new BencList();
            for (int i = 0; i < Array.getLength(src); ++i) {
                Object item = Array.get(src, i);
                list.getValue().add(toElement(item));
            }
            return list;
        } else if (src instanceof Map) {
            throw new Exception("Not implement yet");
        } else {
            BencDictionary dict = new BencDictionary();
            for (Field field : src.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                BencString key = new BencString(field.getName());
                BencElement<?> value = toElement(field.get(src));
                dict.getValue().put(key, value);
            }
            return dict;
        }
    }

    public static <E> E toObject(String text, Class<E> xclass) throws Exception {
        return toObject(BencReader.read(new PeekReader(text)), xclass);
    }

    @SuppressWarnings("unchecked")
    private static <E> E toObject(BencElement<?> element, Class<E> xclass) throws Exception {
        if (element instanceof BencNumber) {
            return (E) ((BencNumber) element).getValue();
        } else if (element instanceof BencString) {
            return (E) ((BencString) element).getValue();
        } else if (element instanceof BencList) {
            BencList list = (BencList) element;
            List<BencElement<?>> value = list.getValue();
            Object array = Array.newInstance(xclass.getComponentType(), value.size());
            for (int i = 0; i < value.size(); ++i) {
                Object object = toObject(value.get(i), xclass.getComponentType());
                Array.set(array, i, object);
            }
            return (E) array;
        } else if (element instanceof BencDictionary) {
            E object = xclass.newInstance();
            Map<BencString, BencElement<?>> map = ((BencDictionary) element).getValue();
            BeanWriter writer = new BeanWriter(object);
            for (Map.Entry<BencString, BencElement<?>> entry : map.entrySet()) {
                String name = entry.getKey().getValue();
                Object value = toObject(entry.getValue(), writer.getType(name));
                writer.write(name, value);
            }
            return object;
        }
        throw new Exception(element.toString());
    }

    private static final class BeanWriter {
        private final Map<String, Field> fields = new HashMap<>();
        private final Object object;

        private BeanWriter(Object object) {
            this.object = object;
            for (Field field : object.getClass().getDeclaredFields()) {
                fields.put(field.getName(), field);
            }
        }

        private Class<?> getType(String name) {
            return fields.get(name).getType();
        }

        private void write(String name, Object value)
                throws IllegalAccessException {
            if (fields.containsKey(name)) {
                Field field = fields.get(name);
                field.setAccessible(true);
                Class<?> type = field.getType();
                if (type.isPrimitive()) {
                    Number number = (Number) value;
                    if (type.equals(Integer.TYPE)) {
                        field.setInt(this.object, number.intValue());
                    } else if (type.equals(Long.TYPE)) {
                        field.setLong(this.object, number.longValue());
                    }
                } else if (value.getClass().equals(type)) {
                    field.set(this.object, value);
                }
            }
        }
    }


    static BencElement<?> read(PeekReader reader) throws IOException {
        switch (reader.peek()) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return BencString.read(reader);
            case 'i':
                return BencNumber.read(reader);
            case 'l':
                return BencList.read(reader);
            case 'd':
                return BencDictionary.read(reader);
            default:
                throw new IOException("");
        }
    }
}
