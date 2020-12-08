package edu.ucsb.mapache.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class SlackUserProfileTests {

  @Test
  public void test_constructor() {
    SlackUserProfile userProfile = new SlackUserProfile("test@test.com", "real", "display", "name");
    String expected = "SlackUserProfile{email='test@test.com', real_name='real', display_name" + 
          "='display', name='name'}";
    assertEquals(expected, userProfile.toString());
  }


  @Test
  public void test_toString() {
    SlackUserProfile userProfile = new SlackUserProfile("test@test.com");
    String expected = "SlackUserProfile{email='test@test.com', real_name='null', display_name" + 
          "='null', name='null'}";
    assertEquals(expected, userProfile.toString());
  }

  @Test
  public void test_getAndSetEmail() {
    SlackUserProfile userProfile = new SlackUserProfile();
    userProfile.setEmail("new@email.com");
    assertEquals("new@email.com", userProfile.getEmail());
  }

  @Test
  public void test_getAndSetRealName() {
    SlackUserProfile userProfile = new SlackUserProfile("test@test.com");
    userProfile.setReal_name("real");
    assertEquals("real", userProfile.getReal_name());
  }

  @Test
  public void test_getAndSetDisplayName() {
    SlackUserProfile userProfile = new SlackUserProfile("test@test.com");
    userProfile.setDisplay_name("display");
    assertEquals("display", userProfile.getDisplay_name());
  }

  @Test
  public void test_getAndSetName() {
    SlackUserProfile userProfile = new SlackUserProfile("test@test.com");
    userProfile.setName("name");
    assertEquals("name", userProfile.getName());
  }


  @Test
  public void test_hashCode_matchesOnSameContent() {
    assertEquals(new SlackUserProfile().hashCode(), new SlackUserProfile().hashCode());
  }

  @Test
  public void testAdmin_notEqualNull() {
    assertNotEquals(new SlackUserProfile(), null);
  }

  @Test
  public void testAdmin_notEqualDifferentClass() {
    assertNotEquals(new SlackUserProfile(), new Object());
  }

  @Test
  public void testAdmin_equalsCopy() {
    assertEquals(new SlackUserProfile(), new SlackUserProfile());
  }

  @Test
  public void testIdenticalUsersEqual() {
    SlackUserProfile userProfile1 = new SlackUserProfile("test@test.com", "real", "display", "name");
    SlackUserProfile userProfile2 = new SlackUserProfile("test@test.com", "real", "display", "name");
    assertEquals(userProfile1, userProfile2);
  }

  @Test void testDifferentUsersNotEqual() {
    SlackUserProfile userProfile1 = new SlackUserProfile("test@test.com", "fake", "display", "name");
    SlackUserProfile userProfile2 = new SlackUserProfile("test@test.com", "real", "display", "name");
    assertNotEquals(userProfile1, userProfile2);
  }


  @Test
  public void testAdmin_equalsSelf() {
    SlackUserProfile userProfile = new SlackUserProfile();
    assertEquals(userProfile, userProfile);
  }

}
