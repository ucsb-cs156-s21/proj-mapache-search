package edu.ucsb.mapache.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class AppUserTests {

  

  @Test
  public void testAppUser_apiTokenAndClearApiToken() {
    AppUser user = new AppUser();
    user.setApiToken("TestTokenABC123");
    user.clearApiToken();
    assertEquals("invalid token", user.getApiToken());
  }

  @Test
  public void testAppUser_decrement () {
    AppUser user1 = new AppUser(1L, "test@ucsb.edu", "Test", "User");
    user1.setSearchRemain(5);
    user1.decrSearchRemain();
    assertEquals(4,user1.getSearchRemain());
    user1.decrSearchRemain(); user1.decrSearchRemain();
    assertEquals(2,user1.getSearchRemain());
  }
}
