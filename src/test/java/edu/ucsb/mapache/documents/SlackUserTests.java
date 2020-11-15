package edu.ucsb.mapache.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class SlackUserTests {

  @Test
  public void test_toString() {
    SlackUser user = new SlackUser("id1", "username", "real name", null);
    assertEquals("SlackUser{id='id1', name='username', real_name='real name', profile=null}", user.toString());
  }

  @Test
  public void test_getAndSetId() {
    SlackUser user = new SlackUser();
    user.setId("id1");
    assertEquals("id1", user.getId());
  }

  @Test
  public void test_getAndSetName() {
    SlackUser user = new SlackUser();
    user.setName("name");
    assertEquals("name", user.getName());
  }

  @Test
  public void test_getAndSetReal_name() {
    SlackUser user = new SlackUser();
    user.setReal_name("real name");
    assertEquals("real name", user.getReal_name());
  }

  @Test
  public void test_getAndSetProfile() {
    SlackUser user = new SlackUser();
    user.setProfile(new SlackUserProfile());
    assertEquals(new SlackUserProfile(), user.getProfile());
  }

  @Test
  public void test_hashCode_matchesOnSameContent() {
    assertEquals(new SlackUser().hashCode(), new SlackUser().hashCode());
  }

  @Test
  public void testAdmin_notEqualNull() {
    assertNotEquals(new SlackUser(), null);
  }

  @Test
  public void testAdmin_notEqualDifferentClass() {
    assertNotEquals(new SlackUser(), new Object());
  }

  @Test
  public void testAdmin_equalsCopy() {
    assertEquals(new SlackUser(), new SlackUser());
  }

  @Test
  public void testAdmin_equalsSelf() {
    SlackUser user = new SlackUser();
    assertEquals(user, user);
  }

}
