package edu.ucsb.mapache.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class SlackUserProfileTests {

  @Test
  public void test_toString() {
    SlackUserProfile userProfile = new SlackUserProfile("test@test.com");
    assertEquals("SlackUserProfile{email='test@test.com'}", userProfile.toString());
  }

  @Test
  public void test_getAndSetEmail() {
    SlackUserProfile userProfile = new SlackUserProfile();
    userProfile.setEmail("new@email.com");
    assertEquals("new@email.com", userProfile.getEmail());
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
  public void testAdmin_equalsSelf() {
    SlackUserProfile userProfile = new SlackUserProfile();
    assertEquals(userProfile, userProfile);
  }

}
