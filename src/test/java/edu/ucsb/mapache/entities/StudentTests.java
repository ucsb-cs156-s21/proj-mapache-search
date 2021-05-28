package edu.ucsb.mapache.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class StudentTests {
  @Test
  public void testStudent_notEqualNull() throws Exception {
   Student student = new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    assertNotEquals(student, null);
  }

  @Test
  public void testStudent_notEqualAnotherClass() throws Exception {
   Student student = new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    assertNotEquals(student, new Object());
  }

  @Test
  public void testStudent_equalsSelf() throws Exception {
    Student student = new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
   
    assertEquals(student, student);
  }

  @Test
  public void testStudent_toString() throws Exception {
    Student student = new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    assertEquals(student.toString(), String.format("Student[id=%d, email=%s, first=%s, last=%s, section=%s,team=%s]", 1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1"));
  }
  @Test
  public void testStudent_equalsSelf_With_Comparison_to_Other_Student_Objects() throws Exception {
    Student student = new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    Student student2 = new Student(2L, "Laurie", "Del Player", "ldelplaya@ucsb.edu", "7pm", "s21-7pm-3");
    Student student3 = new Student(3L, "Yuyuan", "Wang", "yuyugnnnnn@ucsb.edu", "7pm", "s21-7pm-4");
    assertEquals(student, student);
    assertEquals(student2, student2);
    assertEquals(student3, student3);
    assertNotEquals(student, student3);
    assertNotEquals(student, student2);
    assertNotEquals(student3, student);
    assertNotEquals(student.getEmail(), student2.getEmail());
    assertNotEquals(student.getId(), student2.getId());
    assertNotEquals(student3.getTeam(), student.getTeam());
    assertEquals(student2.getSection(), student3.getSection());
  }


  @Test
  public void testStudent_getId() throws Exception {
    Student student = new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    assertEquals(student.getId(), 1L);
  }

  @Test
  public void testStudent_getFirst() throws Exception {
    Student student = new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    assertEquals(student.getFirst(), "Chris");
  }
  @Test
  public void testStudent_getLast() throws Exception {
    Student student = new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    assertEquals(student.getLast(), "Gaucho");
  }

  @Test
  public void testStudent_getEmail() throws Exception {
    Student student = new Student(1L,"Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    assertEquals(student.getEmail(), "cgaucho@ucsb.edu");
  }

  @Test
  public void testStudent_getSection() throws Exception {
    Student student = new Student(1L,"Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    assertEquals(student.getSection(), "5pm");
  }

  @Test
  public void testStudent_getTeam() throws Exception {
    Student student = new Student(1L,"Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    assertEquals(student.getTeam(), "s21-5pm-1");
  }
  
  


  
  @Test
  public void testStudent_setFirst() throws Exception {
    Student student = new Student(1L, "Laurie", "Del Player", "ldelplaya@ucsb.edu", "7pm", "s21-7pm-3");
    student.setFirst("Chris");
    assertEquals(student.getFirst(), "Chris");
  }
  @Test
  public void testStudent_setLast() throws Exception {
    Student student = new Student(1L, "Laurie", "Del Player", "ldelplaya@ucsb.edu", "7pm", "s21-7pm-3");
    student.setLast("Player");
    assertEquals(student.getLast(), "Player");
  }
  @Test
  public void testStudent_setSection() throws Exception {
    Student student = new Student(1L, "Laurie", "Del Player", "ldelplaya@ucsb.edu", "7pm", "s21-7pm-3");
    student.setSection("6pm");
    assertEquals(student.getSection(), "6pm");
  }
  @Test
  public void testStudent_setTeam() throws Exception {
    Student student = new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    student.setTeam("team2");
    assertEquals(student.getTeam(), "team2");
  }

  @Test
  public void testStudent_setEmail() throws Exception {
    Student student = new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    student.setEmail("email2");
    assertEquals(student.getEmail(), "email2");
  }

  @Test
  public void testStudent_setId() throws Exception {
    Student student = new Student(1L, "Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1");
    student.setId(2L);
    assertEquals(student.getId(), 2L);
  }

}