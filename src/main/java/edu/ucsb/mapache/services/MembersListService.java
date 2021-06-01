package edu.ucsb.mapache.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucsb.mapache.documents.SlackUser;
import edu.ucsb.mapache.repositories.SlackUserRepository;
import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.repositories.StudentRepository;

@Service
public class MembersListService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private SlackUserRepository slackUserRepository;
    
    public String getListOfMembers(String teamName) {
        List<Student> studentsAll = studentRepository.findByTeamName(teamName);
        List<String> emailList = new String<List>();
        for (Student s : studentsAll) {
            studentsAll.add(s.getEmail());
        }
        List<String> outputList = new String<List>();
        for(String x: emailList){
            List<SlackUser> user = slackUserRepository.findByEmail(x);
            outputList.add(user.get(0).getProfile().getReal_name());
        }
        
        java.util.Collections.sort(outputList);
        String output = "";
        for (String s : outputList) {
             output += s + "\n";
        }
        
        return output;
  }
}
