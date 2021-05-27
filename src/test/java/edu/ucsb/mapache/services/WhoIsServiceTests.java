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

import edu.ucsb.mapache.documents.SlackUser;
import edu.ucsb.mapache.documents.SlackUserProfile;
import edu.ucsb.mapache.repositories.SlackUserRepository;
import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.repositories.StudentRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WhoIsServiceTests
{

    @MockBean
    private StudentRepository mockStudentRepository;

    @MockBean
    private SlackUserRepository mockSlackUserRepository;

  @Autowired
  private WhoIsService whoIsService;

  @Test
  public void test_formatedOutput() {
      String email = "email";

      List<Student> students = new ArrayList<Student>();
      students.add(new Student(1L, "email", "team1"));

      String expectedSring = "team1";
      when(mockStudentRepository.findByEmail(email)).thenReturn(students);
      assertEquals(expectedSring, whoIsService.getOutput(email));
  }

}
