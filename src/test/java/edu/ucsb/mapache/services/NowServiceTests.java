package edu.ucsb.mapache.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.repositories.StudentRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class NowServiceTests
{
  @Autowired
  private NowService nowService;

  @Test
  public void makeSureTheyDoNotCrash() {
      // not much else we can do
      nowService.now();
      nowService.currentTime();
  }
}
