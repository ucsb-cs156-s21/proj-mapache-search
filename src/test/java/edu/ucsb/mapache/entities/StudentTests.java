package edu.ucsb.mapache.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class StudentTests {
  @Test
  public void testStudent_notEqualNull() throws Exception {
    Long id = 1L;
    Student student = new Student(id, "a@gmail.com", "team1");
    assertNotEquals(student, null);
  }

  @Test
  public void testStudent_notEqualAnotherClass() throws Exception {
    Long id = 1L;
    Student student = new Student(id, "a@gmail.com", "team1");
    assertNotEquals(student, new Object());
  }

  @Test
  public void testStudent_equalsSelf() throws Exception {
    Long id = 1L;
    Student student = new Student(id, "a@gmail.com", "team1");
    assertEquals(student, student);
  }

  @Test
  public void testStudent_toString() throws Exception {
    Long id = 1L;
    Student student = new Student(id, "a@gmail.com", "team1");
    assertEquals(student.toString(), String.format("Student[ id=%d, email=%s, teamName=%s]", id, "a@gmail.com", "team1"));
  }
  @Test
  public void testStudent_equalsSelf_With_Comparison_to_Other_Student_Objects() throws Exception {
    long id1 = 1L;
    long id2 = 2L;
    long id3 = 3L;
    Student student = new Student(id1, "email", "team");
    Student student2 = new Student(id2, "email", "team2");
    Student student3 = new Student(id3, "email3", "team3");
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
    long id = 1L;
    Student student = new Student(id, "email", "team");
    assertEquals(student.getTeamName(), "team");
  }

  @Test
  public void testStudent_getEmail() throws Exception {
    long id = 1L;
    Student student = new Student(id, "email", "team");
    assertEquals(student.getEmail(), "email");
  }

  @Test
  public void testStudent_getId() throws Exception {
    long id = 1L;
    Student student = new Student(id, "email", "team");
    assertEquals(student.getId(), 1L);
  }

  @Test
  public void testStudent_setTeamName() throws Exception {
    long id = 1L;
    Student student = new Student(id, "email", "team");
    student.setTeamName("team2");
    assertEquals(student.getTeamName(), "team2");
  }

  @Test
  public void testStudent_setEmail() throws Exception {
    long id = 1L;
    Student student = new Student(id, "email", "team");
    student.setEmail("email2");
    assertEquals(student.getEmail(), "email2");
  }

  @Test
  public void testStudent_setId() throws Exception {
    long id = 1L;
    Student student = new Student(id, "email", "team");
    long id2 = 2L;
    student.setId(id2);
    assertEquals(student.getId(), id2);
  }

}