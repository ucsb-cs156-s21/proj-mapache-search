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

public class QueriesTests {
    @Test
    public void test_to_string() throws Exception {
        List<RequestItem> r= new ArrayList<RequestItem>();
        r.add(new RequestItem("test", "test", "test", 42, 42, "test", "test", "test", "test"));
        Queries queries = new Queries(r, r);
        String expected = "{" +
        " request='" + r.toString() + "'" +
        ", nextPage='" + r.toString() + "'" +
        "}";
        assertEquals(expected, queries.toString());
    }

    @Test
    public void test_equals0() throws Exception {
        List<RequestItem> r= new ArrayList<RequestItem>();
        r.add(new RequestItem("test", "test", "test", 42, 42, "test", "test", "test", "test"));
        Queries queries0 = new Queries(r, r);
        Queries queries1 = new Queries(r, r);
        assertTrue(queries0.equals(queries1));
        assertTrue(queries0.equals(queries0));
    }

    @Test
    public void test_equals1() throws Exception {
        List<RequestItem> r= new ArrayList<RequestItem>();
        List<RequestItem> s= new ArrayList<RequestItem>();
        r.add(new RequestItem("test", "test", "test", 42, 42, "test", "test", "test", "test"));
        s.add(new RequestItem("fail", "test", "test", 42, 42, "test", "test", "test", "test"));
        Queries queries0 = new Queries(r, r);
        Queries queries1 = new Queries(s, s);
        String queries2 = "fail";
        assertFalse(queries0.equals(queries1));
        assertFalse(queries0.equals(queries2));
    }
}