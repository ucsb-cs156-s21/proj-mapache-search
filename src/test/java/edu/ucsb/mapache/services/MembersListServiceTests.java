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
import edu.ucsb.mapache.documents.SlackUser;
import edu.ucsb.mapache.documents.SlackUserProfile;
import edu.ucsb.mapache.repositories.SlackUserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MembersListServiceTests {

  @MockBean
  private StudentRepository mockStudentRepository;
  
  @MockBean
  private SlackUserRepository mockSlackUserRepository;

  @Autowired
  private MembersListService membersListService;

  @Test
  public void test_formatedMembersList() {             
      String teamname = "team1";
      String email = "email1";

        List<SlackUser> slackUsers = new ArrayList<SlackUser>();
    
        SlackUserProfile profileTest2 = new SlackUserProfile("email1","AName","AName","AName");
        slackUsers.add(new SlackUser("101", "AName", "AName",profileTest2));

        List<Student> students = new ArrayList<Student>();
        students.add(new Student(1L, "email1", "team1"));
                   
        String expectedSring = "AName\n";
        when(mockStudentRepository.findByTeamName(teamname)).thenReturn(students);
        when(mockSlackUserRepository.findByEmail(email)).thenReturn(slackUsers);
        assertEquals(expectedSring, membersListService.getListOfMembers(teamname));
                   
  }
}
