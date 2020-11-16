package edu.ucsb.mapache.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.ucsb.mapache.documents.SlackUser;
import edu.ucsb.mapache.repositories.SlackUserRepository;

@ExtendWith(SpringExtension.class)
public class SlackUserServiceTest {
  @Mock
  SlackUserRepository slackUserRepository;

  @InjectMocks
  SlackUserService slackUserService;

  @Test
  public void test_isMember_returnsFalse_whenNoMatchingSlackUserFound() {
    assertEquals(false, slackUserService.isMember("bogus@email.com"));
  }

  @Test
  public void test_isMember_returnsTrue_whenAtLeastOneMatchingSlackUserFound() {
    List<SlackUser> slackUsers = new ArrayList<SlackUser>();
    slackUsers.add(new SlackUser());
    when(slackUserRepository.findByEmail("member@email.com")).thenReturn(slackUsers);
    assertEquals(true, slackUserService.isMember("member@email.com"));
  }
}
