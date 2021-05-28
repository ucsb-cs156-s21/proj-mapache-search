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
      students.add(new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1"));
      students.add(new Student(2L, "Laurie", "Del Player", "ldelplaya@ucsb.edu", "7pm", "s21-7pm-3"));
     // students.add(new Student(3L, "email3", "team1"));
      String expectedSring = "cgaucho@ucsb.edu\nldelplaya@ucsb.edu\n";
      TeamEmailListService teamEmailListService = new TeamEmailListService();
      assertEquals(expectedSring, teamEmailListService.formatTeamEmails(students));
  }
  @Test void test_getEmailsStringFromTeamname() {
    String teamName = "team";
    List<Student> students = new ArrayList<Student>();
    students.add(new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1"));
    students.add(new Student(2L, "Laurie", "Del Player", "ldelplaya@ucsb.edu", "7pm", "s21-7pm-3"));
    //students.add(new Student(3L, "email3", "team1"));
    when(mockStudentRepository.findByTeam(teamName)).thenReturn(students);
    assertEquals(teamEmailListService.formatTeamEmails(students), teamEmailListService.getEmailsStringFromTeamname(teamName));
  }
  @Test void test_getEmailsStringFromTeamname_emptyCase() {
    String teamName = "team";
    List<Student> students = new ArrayList<Student>();
    when(mockStudentRepository.findByTeam(teamName)).thenReturn(students);
    assertEquals("Team Not Found!",  teamEmailListService.getEmailsStringFromTeamname(teamName));
  }
}
