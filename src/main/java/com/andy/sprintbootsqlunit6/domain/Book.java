package com.andy.sprintbootsqlunit6.domain;

import com.andy.sprintbootsqlunit6.domain.DTO.BookDTO;
import com.andy.sprintbootsqlunit6.domain.DTO.Dto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Book implements Dto<BookDTO> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String title;

  private String synopsis;

  @ManyToOne
  private Category category;

  @ManyToMany(mappedBy = "loanedBooks")
  @JsonIgnore
  private List<Loan> loans;


  public Book(String title, String synopsis) {
    this.title = title;
    this.synopsis = synopsis;
  }

  public Book() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public List<Loan> getLoans() {
    return loans;
  }

  public void setLoans(List<Loan> loans) {
    this.loans = loans;
  }

  @JsonIgnore
  public BookDTO getDto() {
    BookDTO bookDTO = new BookDTO();
    bookDTO.setCategoryId(this.category.getId());
    bookDTO.setTitle(this.title);
    bookDTO.setSynopsis(this.synopsis);
    bookDTO.setId(this.id);
    bookDTO.setCategoryName(this.getCategory().getName());
    return bookDTO;
  }
}
