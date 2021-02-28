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
public class TeamEmailListServiceTests
{

  @MockBean
  private StudentRepository mockStudentRepository;
  @Autowired
  private TeamEmailListService teamEmailListService;
  @Test
  public void test_formatTeamEmails() {
      List<Student> students = new ArrayList<Student>();
      students.add(new Student(1L, "email", "team1"));
      students.add(new Student(2L, "email2", "team1"));
      students.add(new Student(3L, "email3", "team1"));
      String expectedSring = "email\nemail2\nemail3\n";
      TeamEmailListService teamEmailListService = new TeamEmailListService();
      assertEquals(expectedSring, teamEmailListService.formatTeamEmails(students));
  }
  @Test void test_getTeamEmails() {
    String teamName = "team";
    List<Student> students = new ArrayList<Student>();
    students.add(new Student(1L, "email", "team1"));
    students.add(new Student(2L, "email2", "team1"));
    students.add(new Student(3L, "email3", "team1"));
    when(mockStudentRepository.findByTeamName(teamName)).thenReturn(students);
    String expectedSring = "email\nemail2\nemail3\n";
    assertEquals(expectedSring, teamEmailListService.getTeamEmails(teamName));
  }
  @Test void test_getTeamEmails_emptyCase() {
    String teamName = "team";
    List<Student> students = new ArrayList<Student>();
    when(mockStudentRepository.findByTeamName(teamName)).thenReturn(students);
    assertEquals("Team Not Found!",  teamEmailListService.getTeamEmails(teamName));
  }
}
