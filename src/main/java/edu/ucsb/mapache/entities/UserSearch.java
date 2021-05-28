package edu.ucsb.mapache.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class UserSearch {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String UserID;
  @Column(nullable = false)
  private String searchTerm;
  @Column(nullable = false)
  private String timestamp;

  public Search(Long id, String UserID, String searchTerm, String timestamp){
      this.id = id;
      this.UserID =UserID;
      this.searchTerm = searchTerm;
      this.timestamp=timestamp;
  }



  }

