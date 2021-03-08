package edu.ucsb.mapache.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class TeamTests {
  @Test
  public void testTeam_notEqualNull() throws Exception {
    Team student = new Team(1L, "team1", "team description");
    assertNotEquals(student, null);
  }

  @Test
  public void testTeam_notEqualAnotherClass() throws Exception {
    Team student = new Team(1L, "team1", "team description");
    assertNotEquals(student, new Object());
  }

  @Test
  public void testTeam_equalsSelf() throws Exception {
    Team student = new Team(1L, "team1", "team description");
    assertEquals(student, student);
  }

  @Test
  public void testTeam_toString() throws Exception {
    Team student = new Team(1L, "team1", "team description");
    assertEquals(student.toString(),  String.format("Team[id=%d, teamName=%s, teamDescription=%s]", 1L, "team1", "team description"));
  }
  @Test
  public void testTeam_equalsSelf_With_Comparison_to_Other_Team_Objects() throws Exception {
    Team student = new Team(1L, "team1", "team description");
    Team student2 = new Team(2L, "team2", "team description");
    Team student3 = new Team(3L, "team3", "team description3");
    assertEquals(student, student);
    assertEquals(student2, student2);
    assertEquals(student3, student3);
    assertNotEquals(student, student3);
    assertNotEquals(student, student2);
    assertNotEquals(student3, student);
    assertEquals(student.getTeamDescription(), student2.getTeamDescription());
    assertNotEquals(student.getId(), student2.getId());
    assertNotEquals(student3.getTeamName(), student.getTeamName());
  }

  @Test
  public void testTeam_getTeamName() throws Exception {
    Team student = new Team(1L, "team", "team description");
    assertEquals(student.getTeamName(), "team");
  }

  @Test
  public void testTeam_getTeamDescription() throws Exception {
    Team student = new Team(1L, "team", "team description");
    assertEquals(student.getTeamDescription(), "team description");
  }

  @Test
  public void testTeam_getId() throws Exception {
    Team student = new Team(1L, "team", "team description");
    assertEquals(student.getId(), 1L);
  }

  @Test
  public void testTeam_setTeamName() throws Exception {
    Team student = new Team(1L, "team", "team description");
    student.setTeamName("team2");
    assertEquals(student.getTeamName(), "team2");
  }

  @Test
  public void testTeam_setTeamDescription() throws Exception {
    Team student = new Team(1L, "team", "team description");
    student.setTeamDescription("team description1");
    assertEquals(student.getTeamDescription(), "team description1");
  }

  @Test
  public void testTeam_setId() throws Exception {
    Team student = new Team(1L, "team", "team description");
    student.setId(2L);
    assertEquals(student.getId(), 2L);
  }
}
