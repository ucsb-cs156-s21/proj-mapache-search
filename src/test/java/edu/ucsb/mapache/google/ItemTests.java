package edu.ucsb.mapache.google;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import java.io.IOException;

public class ItemTests {
    @Test
    public void test_to_string() throws Exception {
        Item item = new Item("test", "test", "test", "test", "test", "test", "test", "test", "test", "test");
        String expected = "{" +
        " kind='" + "test" + "'" +
        ", title='" + "test" + "'" +
        ", htmlTitle='" + "test" + "'" +
        ", link='" + "test" + "'" +
        ", displayLink='" + "test" + "'" +
        ", snippet='" + "test" + "'" +
        ", htmlSnippet='" + "test" + "'" +
        ", cacheId='" + "test" + "'" +
        ", formattedUrl='" + "test" + "'" +
        ", htmlFormattedUrl='" + "test" + "'" +
        "}";
        assertEquals(expected, item.toString());
    }

    @Test
    public void test_equals0() throws Exception {
        Item item0 = new Item("test", "test", "test", "test", "test", "test", "test", "test", "test", "test");
        Item item1 = new Item("test", "test", "test", "test", "test", "test", "test", "test", "test", "test");
        assertTrue(item0.equals(item1));
        assertTrue(item0.equals(item0));
    }

    @Test
    public void test_equals1() throws Exception {
        Item item0 = new Item("test", "test", "test", "test", "test", "test", "test", "test", "test", "test");
        Item item1 = new Item("fail", "fail", "fail", "fail", "fail", "fail", "fail", "fail", "fail", "fail");
        String item3 = "fail";
        assertFalse(item0.equals(item1));
        assertFalse(item0.equals(item3));
    }
}