package edu.ucsb.mapache.services;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.ucsb.mapache.entities.Student;

public class TeamEmailListServiceTests
{
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
}
