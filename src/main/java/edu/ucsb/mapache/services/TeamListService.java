package edu.ucsb.mapache.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.repositories.StudentRepository;

@Service
public class TeamListService {
  @Autowired
  private StudentRepository studentRepository;

  public String getListOfTeams() {
    List<Student> studentsAll = studentRepository.findAll();
    String teamList = "";
    for (Student s : studentsAll) {
      teamList += s.getTeam() + "\n";
    }
    return teamList;
  }

}
