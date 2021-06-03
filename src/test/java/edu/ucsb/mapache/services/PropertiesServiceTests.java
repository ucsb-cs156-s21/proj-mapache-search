package edu.ucsb.mapache.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.beans.factory.annotation.Autowired;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesServiceTests
{
  @Autowired
  private PropertiesService propertiesService;

  @BeforeEach
  public void setUp() {
    ReflectionTestUtils.setField(propertiesService, "namespace", "https://proj-mapache-search.herokuapp.com");
  }
    
  @Test
  public void test_getNamespace() {
      String actual = propertiesService.getNamespace();
      assertEquals("https://proj-mapache-search.herokuapp.com", actual);
  }
}