package edu.ucsb.mapache.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
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
  @Column(nullable = false, columnDefinition = "integer default 100") 
  private int searchRemain=100;
  @Column(nullable = false)
  private long time;
  // Added below instance variable to associate custom API token w/ an AppUser
  //default is set to "invalid token"
  @Column(nullable = true)
  private String apiToken = "invalid token";

  public AppUser() {
    time=0;
  }

  public AppUser(Long id, String email, String firstName, String lastName) {
    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }


  public int decrSearchRemain(){
      this.searchRemain--;
      return this.searchRemain;
  }

  public void clearApiToken() {
    this.apiToken = "invalid token";
  }

}
