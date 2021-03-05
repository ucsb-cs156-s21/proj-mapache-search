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

public class RequestItemTests {
    @Test
    public void test_to_string() throws Exception {
        RequestItem requestItem = new RequestItem("test", "test", "test", 42, 42, "test", "test", "test", "test");
        String expected = "{" +
        " title='" + "test" + "'" +
        ", totalResults='" + "test" + "'" +
        ", searchTerms='" + "test" + "'" +
        ", count='" + 42 + "'" +
        ", startIndex='" + 42 + "'" +
        ", inputEncoding='" + "test" + "'" +
        ", outputEncoding='" + "test" + "'" +
        ", safe='" + "test" + "'" +
        ", cx='" + "test" + "'" +
        "}";
        assertEquals(expected, requestItem.toString());
    }

    @Test
    public void test_equals0() throws Exception {
        RequestItem requestItem0 = new RequestItem("test", "test", "test", 42, 42, "test", "test", "test", "test");
        RequestItem requestItem1 = new RequestItem("test", "test", "test", 42, 42, "test", "test", "test", "test");
        assertTrue(requestItem0.equals(requestItem1));
        assertTrue(requestItem0.equals(requestItem0));
    }

    @Test
    public void test_equals1() throws Exception {
        RequestItem requestItem0 = new RequestItem("test", "test", "test", 42, 42, "test", "test", "test", "test");
        RequestItem requestItem1 = new RequestItem("fail", "fail", "fail", 41, 41, "fail", "fail", "fail", "fail");
        String requestItem3 = "fail";
        assertFalse(requestItem0.equals(requestItem1));
        assertFalse(requestItem0.equals(requestItem3));
    }
}