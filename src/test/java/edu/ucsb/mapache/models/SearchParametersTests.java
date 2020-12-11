package edu.ucsb.mapache.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import net.codebox.javabeantester.JavaBeanTester;

public class SearchParametersTests{
    @Test
    public void test_gettersAndSetters() throws Exception {
      // See: https://github.com/codebox/javabean-tester
      JavaBeanTester.test(SearchParameters.class);
    }
}
