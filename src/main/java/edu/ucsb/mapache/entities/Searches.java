package edu.ucsb.mapache.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Search {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String searchTerm;
  @Column(nullable = false)
  private int count;

  public Search(){

  }

  public Search(Long id, String searchTerm, int count){
      this.id = id;
      this.searchTerm = searchTerm;
      this.count = count
  }

  public void setId(long id){
      this.id = id;
  }
  public void setSearchTerm(String s){
      this.searchTerm = s;
  }

  public void setCount(int c){
      this.count = c;
  }
  public void getId(){
      return id;
  }
  public void getSearchTerm(){
      return searchTerm;
  }

  public void getCount(){
      return count;
  }
}