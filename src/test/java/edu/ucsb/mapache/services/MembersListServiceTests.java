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
public class MembersListServiceTests {

  @MockBean
  private StudentRepository mockStudentRepository;

  @Autowired
  private MembersListService membersListService;

  @Test
  public void test_formatedMembersList() {
      List<Student> students = new ArrayList<Student>();
      students.add(new Student(aL, "email", "team1"));
      students.add(new Student(bL, "email2", "team1"));
      students.add(new Student(cL, "email3", "team1"));
      String expectedSring = "aL\nbL\ncL\n";
      when(mockStudentRepository.findAll()).thenReturn(students);
      assertEquals(expectedSring, membersListService.getListOfMembers(String team1);
  }
}
