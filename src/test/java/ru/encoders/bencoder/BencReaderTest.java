package ru.encoders.bencoder;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public final class BencReaderTest {

    @Test
    public void testToNumber() throws Exception {
        Long result = BencReader.toObject("i43e", Long.class);
        assertEquals((long) result, (long) 43);
    }

    @Test
    public void testToString() throws Exception {
        String result = BencReader.toObject("3:boo", String.class);
        assertEquals(result, "boo");
    }

    @Test
    public void testToObject() throws Exception {
        Entity entity = BencReader.toObject("d6:numberi21e4:name2:00e", Entity.class);
        assertEquals(entity.name, "00");
        assertEquals(entity.number, 21);
    }

    @Test
    public void testToObjectConvert() throws Exception {
        String text = "d6:numberi21e4:name2:00e";
        Entity entity = BencReader.toObject(text, Entity.class);
        String result = BencReader.toString(entity);
        assertEquals(text, result);
    }

    @Test
    public void testToList() throws Exception {
        Object[] list = BencReader.toObject("li43e2:00e", Object[].class);
        assertEquals(list.length, 2);
    }

    @Test
    public void testToListInserted() throws Exception {
        EntityList el = new EntityList();
        el.list = new Entity[]{
                new Entity()
        };
        el.list[0].name = "Name1";
        el.list[0].number = 1000;
        String element = BencReader.toString(el);
        EntityList list = BencReader.toObject(element, EntityList.class);
        assertEquals(list, el);
    }

    public static final class EntityList {
        private Entity[] list;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EntityList that = (EntityList) o;
            return Arrays.equals(list, that.list);
        }
    }

    public static final class Entity {
        private int number;
        private String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entity entity = (Entity) o;
            return number == entity.number && !(name != null ? !name.equals(entity.name) : entity.name != null);

        }
    }
}