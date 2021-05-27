package edu.ucsb.mapache.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucsb.mapache.documents.SlackUser;
import edu.ucsb.mapache.repositories.SlackUserRepository;
import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.repositories.StudentRepository;

@Service
public class WhoIsService {
  @Autowired
  private SlackUserRepository slackUserRepository;

  @Autowired
  private StudentRepository studentRepository;

  public String getOutput(String email) {
    String team = "";
    List<Student> stu = studentRepository.findByEmail(email);
    Student outputStudent = stu.get(0);
    team = outputStudent.getTeamName();

    return team;
  }
}
