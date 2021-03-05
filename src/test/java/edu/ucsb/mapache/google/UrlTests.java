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

public class UrlTests {
    @Test
    public void test_to_string() throws Exception {
        Url url = new Url("test", "test");
        String expected = "{" +
        " type='" + "test" + "'" +
        ", template='" + "test" + "'" +
        "}";
        assertEquals(expected, url.toString());
    }

    @Test
    public void test_equals0() throws Exception {
        Url url0 = new Url("test", "test");
        Url url1 = new Url("test", "test");
        assertTrue(url0.equals(url1));
        assertTrue(url0.equals(url0));
    }

    @Test
    public void test_equals1() throws Exception {
        Url url0 = new Url("test", "test");
        Url url1 = new Url("test", "fail");
        String url3 = "fail";
        assertFalse(url0.equals(url1));
        assertFalse(url0.equals(url3));
    }
}