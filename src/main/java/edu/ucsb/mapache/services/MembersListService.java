package edu.ucsb.mapache.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.repositories.StudentRepository;

@Service
public class MembersListService {
    
    @Autowired
    private StudentRepository studentRepository;

    public String getListOfMembers() {
        

    }
}
