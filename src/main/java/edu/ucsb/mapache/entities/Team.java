package edu.ucsb.mapache.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.opencsv.bean.CsvBindByPosition;

@Entity
public class Team {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  @CsvBindByPosition(position = 0)
  private String teamName;
  @Column(nullable = false)
  @CsvBindByPosition(position = 1)
  private String teamDescription;

  public Team(Long id, String teamName, String teamDescription) {
    this.teamName = teamName;
    this.teamDescription = teamDescription;
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public String getTeamDescription() {
    return teamDescription;
  }

  public void setTeamDescription(String teamDescription) {
    this.teamDescription = teamDescription;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Team other = (Team) obj;
    EqualsBuilder builder = new EqualsBuilder();
    builder.append(id, other.id).append(teamName, other.teamName).append(teamDescription, other.teamDescription);
    return builder.isEquals();
  }
  @Override
  public String toString() {
    return String.format("Team[id=%d, teamName=%s, teamDescription=%s]", id, teamName, teamDescription);
  }

}
