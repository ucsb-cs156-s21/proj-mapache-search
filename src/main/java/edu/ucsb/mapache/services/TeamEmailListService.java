package edu.ucsb.mapache.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.repositories.StudentRepository;

@Service
public class TeamEmailListService {
  @Autowired
  private StudentRepository studentRepository;

  public String formatTeamEmails(List<Student> students) {
    String emailList = "";
    for (Student s : students) {
      emailList += s.getEmail() + "\n";
    }
    return emailList;
  }
  public String getTeamEmails(String teamName) {
    List <Student> studentsInTeam = studentRepository.findByTeamName(teamName);
    String emailString = this.formatTeamEmails(studentsInTeam);
    if(studentsInTeam.isEmpty() || studentsInTeam == null || emailString == null) {
      return "Team Not Found!";
    }
    return emailString;
  }
}
