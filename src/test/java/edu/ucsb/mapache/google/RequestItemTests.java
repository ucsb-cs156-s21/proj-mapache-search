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
        String[] strArg = {"test", "test", "test", "test", "test", "test", "test"};
        int[] intArg = {42, 42};
        RequestItem requestItem1;
        for(int i=0; i<9; i++){
            if(i<7){
                strArg[i] = "fail";
                requestItem1 = new RequestItem(strArg[0], strArg[1], strArg[2], intArg[0], intArg[1], strArg[3], strArg[4], strArg[5], strArg[6]);
                strArg[i] = "test";
            }
            else{
                intArg[i-7] = 41;
                requestItem1 = new RequestItem(strArg[0], strArg[1], strArg[2], intArg[0], intArg[1], strArg[3], strArg[4], strArg[5], strArg[6]);
                intArg[i-7] = 42;
            }
            
            assertFalse(requestItem0.equals(requestItem1));

        }
        String requestItem3 = "fail";
        assertFalse(requestItem0.equals(requestItem3));
    }
}