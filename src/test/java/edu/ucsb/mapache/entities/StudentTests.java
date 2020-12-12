package edu.ucsb.mapache.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class StudentTests {
  @Test
  public void testStudent_notEqualNull() throws Exception {
    Student student = new Student(1L, "a@gmail.com", "team1");
    assertNotEquals(student, null);
  }

  @Test
  public void testStudent_notEqualAnotherClass() throws Exception {
    Student student = new Student(1L, "a@gmail.com", "team1");
    assertNotEquals(student, new Object());
  }

  @Test
  public void testStudent_equalsSelf() throws Exception {
    Student student = new Student(1L, "a@gmail.com", "team1");
    assertEquals(student, student);
  }

  @Test
  public void testStudent_toString() throws Exception {
    Student student = new Student(1L, "a@gmail.com", "team1");
    assertEquals(student.toString(), String.format("Student[ id=%d, email=%s, teamName=%s]", 1L, "a@gmail.com", "team1"));
  }
  @Test
  public void testStudent_equalsSelf_With_Comparison_to_Other_Student_Objects() throws Exception {
    Student student = new Student(1L, "email", "team");
    Student student2 = new Student(2L, "email", "team2");
    Student student3 = new Student(3L, "email3", "team3");
    assertEquals(student, student);
    assertEquals(student2, student2);
    assertEquals(student3, student3);
    assertNotEquals(student, student3);
    assertNotEquals(student, student2);
    assertNotEquals(student3, student);
    assertEquals(student.getEmail(), student2.getEmail());
    assertNotEquals(student.getId(), student2.getId());
    assertNotEquals(student3.getTeamName(), student.getTeamName());
  }

  @Test
  public void testStudent_getTeamName() throws Exception {
    Student student = new Student(1L, "email", "team");
    assertEquals(student.getTeamName(), "team");
  }

  @Test
  public void testStudent_getEmail() throws Exception {
    Student student = new Student(1L, "email", "team");
    assertEquals(student.getEmail(), "email");
  }

  @Test
  public void testStudent_getId() throws Exception {
    Student student = new Student(1L, "email", "team");
    assertEquals(student.getId(), 1L);
  }

  @Test
  public void testStudent_setTeamName() throws Exception {
    Student student = new Student(1L, "email", "team");
    student.setTeamName("team2");
    assertEquals(student.getTeamName(), "team2");
  }

  @Test
  public void testStudent_setEmail() throws Exception {
    Student student = new Student(1L, "email", "team");
    student.setEmail("email2");
    assertEquals(student.getEmail(), "email2");
  }

  @Test
  public void testStudent_setId() throws Exception {
    Student student = new Student(1L, "email", "team");
    student.setId(2L);
    assertEquals(student.getId(), 2L);
  }

}