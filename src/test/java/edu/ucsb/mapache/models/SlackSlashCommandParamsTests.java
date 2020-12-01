package edu.ucsb.mapache.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import net.codebox.javabeantester.JavaBeanTester;


public class SlackSlashCommandParamsTests {

    @Test
    public void testGettersAndSetters() throws Exception {
        // See: https://github.com/codebox/javabean-tester
        // The classes that should NOT be tested with the Bean are the
        // ones that set and get List<> instances
        JavaBeanTester.test(SlackSlashCommandParams.class,"textParts");
    }
  
    @Test
    public void test_getTextParts() {
        SlackSlashCommandParams params = new SlackSlashCommandParams();
        params.setText("a b   c  \t d");
        String [] result = params.getTextParts();
        assertEquals(4, result.length);
        assertEquals("a",result[0]);
        assertEquals("b",result[1]);
        assertEquals("c",result[2]);
        assertEquals("d",result[3]);
    }

    @Test
    public void test_equalsSelf() throws Exception {
        SlackSlashCommandParams params = new SlackSlashCommandParams(1,10,10,new ArrayList<Course>());
        assertEquals(params, params);
    }

    @Test
    public void test_equalsAnother() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams(1,10,10,new ArrayList<Course>());
        SlackSlashCommandParams params2 = new SlackSlashCommandParams(1,10,10,new ArrayList<Course>());
        assertEquals(params1, params2);
    }

    @Test
    public void test_hashCode() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams(1,10,10,new ArrayList<Course>());
        SlackSlashCommandParams params2 = new SlackSlashCommandParams(1,10,10,new ArrayList<Course>());
        assertEquals(params1.hashCode(), params2.hashCode());
    }

    @Test
    public void test_toString() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams(1,10,10,new ArrayList<Course>());
        assertEquals("{ pageNumber='1', pageSize='10', total='10', classes='[]'}",params1.toString());
    }

}
