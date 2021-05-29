package edu.ucsb.mapache.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.repositories.TeamRepository;

@Service
public class MembersListService {
    
    @Autowired
    private TeamRepository teamRepository;

    public String getListOfMembers() {
        List<Student> studentsAll = teamRepository.findByTeamName();
        String teamMembersList = "";
        for (Student s : studentsAll) {
            teamMembersList += s.getTeamName() + "\n";
    }
        return teamMembersList;
  }

    }
}
