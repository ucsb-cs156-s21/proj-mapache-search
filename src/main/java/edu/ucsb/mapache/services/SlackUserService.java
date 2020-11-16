package edu.ucsb.mapache.services;

import edu.ucsb.mapache.documents.SlackUser;
import edu.ucsb.mapache.repositories.SlackUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.List;

@Service
public class SlackUserService {
    @Autowired
    private SlackUserRepository slackUserRepository;

    public boolean isMember(String email){
        List<SlackUser> slackUsers =  slackUserRepository.findByEmail(email);
        return slackUsers.size() != 0;
    }
}
