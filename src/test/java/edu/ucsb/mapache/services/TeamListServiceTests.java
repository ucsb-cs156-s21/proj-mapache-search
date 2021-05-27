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
public class TeamListServiceTests {

  @MockBean
  private StudentRepository mockStudentRepository;

  @Autowired
  private TeamListService teamListService;

  @Test
  public void test_formatedTeamList() {
      List<Student> students = new ArrayList<Student>();
      students.add(new Student(1L, "email", "team1"));
      students.add(new Student(2L, "email2", "team2"));
      students.add(new Student(3L, "email3", "team3"));
      String expectedSring = "team1\nteam2\nteam3\n";
      when(mockStudentRepository.findAll()).thenReturn(students);
      assertEquals(expectedSring, teamListService.getListOfTeams());
  }
}
