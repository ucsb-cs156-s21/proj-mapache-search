package edu.ucsb.mapache.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  @CsvBindByPosition(position = 0)
  private String first;

  @Column(nullable = false)
  @CsvBindByPosition(position = 1)
  private String last;

  @Column(nullable = false)
  @CsvBindByPosition(position = 2)
  private String email;

  @Column(nullable = false)
  @CsvBindByPosition(position = 3)
  private String section;

  @Column(nullable = false)
  @CsvBindByPosition(position = 4)
  private String team;

  public Student() {
  }

  public Student(Long id,  String first, String last, String email,String section, String team ) {
    this.id = id;
    
    this.first = first;
    this.last = last;
    this.email = email;
    this.section = section;
    this.team = team;
  }
  //1L,"Chris", "Gaucho", "cgaucho@ucsb.edu", "5pm", "s21-5pm-1"
  public Student(  String first, String last,String email, String section, String team ) {
    
    this.first = first;
    this.last = last;
    this.email = email;
    this.section = section;
    this.team = team;
  }

  @Override
  public String toString() {
    return String.format("Student[id=%d, first=%s, last=%s, email=%s, section=%s,team=%s]", id, first, last, email, section, team);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Student other = (Student) obj;
    EqualsBuilder builder = new EqualsBuilder();
    builder.append(id, other.id).append(email, other.email).append(first, other.first).append(last, other.last).append(section, other.section).append(team, other.team);
    return builder.isEquals();
  }
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getFirst() {
    return first;
  }
  public void setFirst(String first) {
    this.first = first;
  }
  public String getLast() {
    return last;
  }
  public void setLast(String last) {
    this.last = last;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getSection() {
    return section;
  }
  public void setSection(String section) {
    this.section = section;
  }
  public String getTeam() {
    return team;
  }
  public void setTeam(String team) {
    this.team = team;
  }
}