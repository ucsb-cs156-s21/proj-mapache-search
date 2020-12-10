package edu.ucsb.mapache.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
public class AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String firstName;
  @Column(nullable = false)
  private String lastName;
<<<<<<< HEAD
  @Column(nullable = false, columnDefinition = "integer default 100") 
  private int searchRemain=100;
  @Column(nullable = false)
  private long time;
=======
>>>>>>> 6dca1329b74d108ad745acb32b232132ce3bb751
  // Added below instance variable to associate custom API token w/ an AppUser
  @Column(nullable = true)
  private String apiToken;

  public AppUser() {
    time=0;
  }

  public AppUser(Long id, String email, String firstName, String lastName) {
    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

<<<<<<< HEAD
  public int getSearchRemain(){
    return searchRemain;
  }

  public void setSearchRemain(int searchRemain){
    this.searchRemain=searchRemain;
  }

  public int decrSearchRemain(){
      this.searchRemain--;
      return this.searchRemain;
  }

  public long getTime(){
    return time;
  }

  public void setTime(long time){
    this.time=time;
  }
  @Override
  public String toString() {
    return String.format("AppUser[ id=%d, email=%s, firstName=%s, lastName=%s, searchRemain=%d]", id, email, firstName, lastName, searchRemain);
  }

  public String getApiToken() {
    return this.apiToken;
  }

  public void setApiToken(String apiToken) {
    this.apiToken = apiToken;
  }

=======
  // Getter for API Token
  public String getAPIToken() {
    return apiToken;
  }

  public AppUser(long id, String email, String firstName, String lastName, String apiToken) {
    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.apiToken = apiToken;
  }

  public String getApiToken() {
    return this.apiToken;
  }

  public void setApiToken(String apiToken) {
    this.apiToken = apiToken;
  }

  public AppUser id(long id) {
    this.id = id;
    return this;
  }

  public AppUser email(String email) {
    this.email = email;
    return this;
  }

  public AppUser firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public AppUser lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

>>>>>>> 6dca1329b74d108ad745acb32b232132ce3bb751
  public AppUser apiToken(String apiToken) {
    this.apiToken = apiToken;
    return this;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    AppUser user = (AppUser) o;
    EqualsBuilder builder = new EqualsBuilder();
    builder.append(id, user.getId()).append(email, user.getEmail()).append(firstName, user.getFirstName())
        .append(lastName, user.getLastName()).append(searchRemain,user.getSearchRemain());

    return builder.isEquals();
  }
}
