package com.andy.sprintbootsqlunit6.domain.DTO;

import java.util.List;

public class CategoryDTO {

  private String name;

  private Integer id;

  private String description;

  private List<BookDTO> books;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<BookDTO> getBooks() {
    return books;
  }

  public void setBooks(List<BookDTO> books) {
    this.books = books;
  }

  public void addBook(BookDTO bookDTO) {
    this.books.add(bookDTO);
  }
}
